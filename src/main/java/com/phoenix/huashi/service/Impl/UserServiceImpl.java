package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.Page;
import com.phoenix.huashi.common.PageParam;
import com.phoenix.huashi.controller.request.GetBriefUserNameListRequest;
import com.phoenix.huashi.controller.request.GetListRequest;
import com.phoenix.huashi.controller.request.GetTeamListRequest;
import com.phoenix.huashi.controller.request.UpdateUserByChuangNumRequest;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.common.CommonConstants;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.config.YmlConfig;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.WxSession;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.enums.MemberTypeEnum;
import com.phoenix.huashi.mapper.MemberMapper;
import com.phoenix.huashi.mapper.RecruitProjectMapper;
import com.phoenix.huashi.mapper.UserMapper;
import com.phoenix.huashi.service.UserService;
import com.phoenix.huashi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private YmlConfig ymlConfig;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RecruitProjectMapper recruitProjectMapper;

    @Override
    public User getUserByChuangNum(String userChuangNum) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        return user;
    }


    @Override
    public Page<BriefProjectInformation> getBriefTeamList(GetTeamListRequest request, String userChuangNum) {
        List<Long> projectIdList=new ArrayList<>();
        if(request.getTeamType().equals(0))projectIdList=memberMapper.getTeamByChuangNumAndMemberType(userChuangNum,MemberTypeEnum.MEMBER.getDescription());
        else if(request.getTeamType().equals(1))projectIdList=memberMapper.getTeamByChuangNumAndMemberType(userChuangNum,MemberTypeEnum.CAPTAIN.getDescription());
        else if(request.getTeamType().equals(2))projectIdList=memberMapper.getTeamByChuangNum(userChuangNum);
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        List<BriefProjectInformation> briefProjectInformationList = new LinkedList<>();
        for (Long projectId : projectIdList) {
            RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(projectId);
            BriefProjectInformation briefProjectInformation = new BriefProjectInformation(recruitProject.getId(), recruitProject.getName(), recruitProject.getTag1(), recruitProject.getTag2(), recruitProject.getTag3(), recruitProject.getCaptainName(), recruitProject.getInstitute(), recruitProject.getStatus());
            briefProjectInformationList.add(briefProjectInformation);
        }
        return new Page(new PageInfo<>(briefProjectInformationList));
    }

    @Override
    public void updateUserByChuangNum(UpdateUserByChuangNumRequest updateUserByChuangNumRequest, String userChuangNum) {
        userMapper.updateUserByChuangNum(updateUserByChuangNumRequest.getNickname(), updateUserByChuangNumRequest.getPortrait(), updateUserByChuangNumRequest.getTelephone(), updateUserByChuangNumRequest.getSchool(), updateUserByChuangNumRequest.getDepartment(), updateUserByChuangNumRequest.getMajor(), updateUserByChuangNumRequest.getGrade(), updateUserByChuangNumRequest.getQQ(), updateUserByChuangNumRequest.getWechatNum(), updateUserByChuangNumRequest.getResume(), updateUserByChuangNumRequest.getAttachment(), userChuangNum);
    }

    @Override
    public Page<BriefUserName> searchBriefUserNameListByName(GetBriefUserNameListRequest searchBriefUserNameListRequest) {
        PageParam pageParam = searchBriefUserNameListRequest.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        List<BriefUserName> briefUserNameList = userMapper.searchBriefUserNameListByName(searchBriefUserNameListRequest.getName());
        return new Page(new PageInfo<>(briefUserNameList));
    }


    @Override
    public SessionData login(String code) {

        //shadow test
        if (CommonConstants.SHADOW_TEST.equals(code)) {
            sessionUtils.setSessionId(CommonConstants.SHADOW_TEST);
            return new SessionData();
        }

        WxSession wxSession = Optional.ofNullable(
                        getWxSessionByCode(code))
                .orElse(new WxSession());

        checkWxSession(wxSession);

        String sessionId = sessionUtils.generateSessionId();

        User user = userMapper.getUserByOpenId(wxSession.getOpenId());

        if (user != null) {
            sessionUtils.setSessionId(user.getSessionId());
            return new SessionData(user);
        }

        User user1 = new User(sessionId, wxSession.getOpenId(), wxSession.getUnionId(), wxSession.getSessionKey(), TimeUtil.getCurrentTimestamp(),"华实创赛用户");

//        User user1 = new User();
        userMapper.newUser(user1);

        long userId = user1.getId();

        userMapper.updateChuangNum("hs" + String.format("%08d", userId), userId);

        user1.setChuangNum("hs" + String.format("%08d",userId));
        user1.setId(userId);

        return new SessionData(user1);
    }


    private WxSession getWxSessionByCode(String code) {
        Map<String, String> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", ymlConfig.getAppId());
//        requestUrlParam.put("appid", "wx22fa1182d4e66c4a");
        //小程序secret
        requestUrlParam.put("secret", ymlConfig.getAppSecret());
//        requestUrlParam.put("secret", "200e82982f7ec2a2812fc3ae9f2d5f15");
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数：授权类型
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpUtil.get(CommonConstants.WX_SESSION_REQUEST_URL, requestUrlParam);
//        String result = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", requestUrlParam);

        return JsonUtil.toObject(result, WxSession.class);
    }

    private void checkWxSession(WxSession wxSession) {
        if (wxSession.getErrcode() != null) {
            AssertUtil.isFalse(-1 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_BUSY, wxSession.getErrmsg());
            AssertUtil.isFalse(40029 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_INVALID_CODE, wxSession.getErrmsg());
            AssertUtil.isFalse(45011 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_FREQUENCY_REFUSED, wxSession.getErrmsg());
            AssertUtil.isTrue(wxSession.getErrcode() == 0, CommonErrorCode.WX_LOGIN_UNKNOWN_ERROR, wxSession.getErrmsg());
        }
    }


}