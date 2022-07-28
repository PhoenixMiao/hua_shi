package com.phoenix.huashi.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.phoenix.huashi.common.*;
import com.phoenix.huashi.controller.request.*;
import com.phoenix.huashi.controller.response.GetUserResponse;
import com.phoenix.huashi.dto.member.Experience;
import com.phoenix.huashi.dto.recruitproject.BriefProjectInformation;
import com.phoenix.huashi.dto.user.BriefUserName;
import com.phoenix.huashi.config.YmlConfig;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.WxSession;
import com.phoenix.huashi.entity.DisplayProject;
import com.phoenix.huashi.entity.Member;
import com.phoenix.huashi.entity.RecruitProject;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.enums.MemberTypeEnum;
import com.phoenix.huashi.mapper.MemberMapper;
import com.phoenix.huashi.mapper.RecruitProjectMapper;
import com.phoenix.huashi.mapper.UserMapper;
import com.phoenix.huashi.service.UserService;
import com.phoenix.huashi.util.*;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.phoenix.huashi.common.CommonConstants.COS_BUCKET_NAME;

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

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private COSClient cosClient;

    @Override
    public User getUserByChuangNum(String userChuangNum) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        return user;
    }


    @Override
    public Page<BriefProjectInformation> getBriefTeamList(GetTeamListRequest request, String userChuangNum) {
        List<Long> projectIdList = new ArrayList<>();
        if (request.getTeamType().equals(0))
            projectIdList = memberMapper.getTeamByChuangNumAndMemberType(userChuangNum, MemberTypeEnum.MEMBER.getDescription());
        else if (request.getTeamType().equals(1))
            projectIdList = memberMapper.getTeamByChuangNumAndMemberType(userChuangNum, MemberTypeEnum.CAPTAIN.getDescription());
        else if (request.getTeamType().equals(2))
            projectIdList = memberMapper.getTeamByChuangNum(userChuangNum);
        PageParam pageParam = request.getPageParam();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(), pageParam.getOrderBy());
        List<BriefProjectInformation> briefProjectInformationList = new LinkedList<>();
        for (Long projectId : projectIdList) {
            RecruitProject recruitProject = recruitProjectMapper.getRecruitProjectById(projectId);
            if(recruitProject!=null){
                BriefProjectInformation briefProjectInformation = new BriefProjectInformation(recruitProject.getId(), recruitProject.getName(), recruitProject.getTag1(), recruitProject.getTag2(), recruitProject.getTag3(), recruitProject.getCaptainName(), userMapper.getUserByChuangNum(recruitProject.getCaptainChuangNum()).getDepartment(), recruitProject.getStatus());
                briefProjectInformationList.add(briefProjectInformation);
            }
        }
        return new Page(new PageInfo<>(briefProjectInformationList));
    }

    @Override
    public void updateUserByChuangNum(UpdateUserByChuangNumRequest updateUserByChuangNumRequest, String userChuangNum) {
        userMapper.updateUserByChuangNum(updateUserByChuangNumRequest.getNickname(), updateUserByChuangNumRequest.getPortrait(), updateUserByChuangNumRequest.getTelephone(), updateUserByChuangNumRequest.getDepartment(), updateUserByChuangNumRequest.getMajor(), updateUserByChuangNumRequest.getGrade(), updateUserByChuangNumRequest.getQQ(), updateUserByChuangNumRequest.getWechatNum(), updateUserByChuangNumRequest.getResume(), updateUserByChuangNumRequest.getAttachment(), userChuangNum);
    }

    @Override
    public void fillUserInformation(FillUserInformationRequest fillUserInformationRequest, String userChuangNum) {
        User user = userMapper.getUserByChuangNum(userChuangNum);
        userMapper.fillUserInformation(userChuangNum, fillUserInformationRequest.getNickname(), fillUserInformationRequest.getName(), fillUserInformationRequest.getGender(), fillUserInformationRequest.getSchool(), fillUserInformationRequest.getDepartment(), fillUserInformationRequest.getMajor(), fillUserInformationRequest.getGrade(), fillUserInformationRequest.getTelephone(), fillUserInformationRequest.getQQ(), fillUserInformationRequest.getWechatNum(), fillUserInformationRequest.getPortrait(), fillUserInformationRequest.getResume(), fillUserInformationRequest.getAttachment(), fillUserInformationRequest.getStudentNumber());

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

        User user1 = new User(sessionId, wxSession.getOpenId(), wxSession.getUnionId(), wxSession.getSessionKey(), TimeUtil.getCurrentTimestamp(), "华实创赛用户");

//        User user1 = new User();
        userMapper.newUser(user1);

        long userId = user1.getId();

        userMapper.updateChuangNum("hs" + String.format("%08d", userId), userId);

        user1.setChuangNum("hs" + String.format("%08d", userId));
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

    @Override
    public String resumeUpload(String userChuangNum, MultipartFile file,String fileName){
        User user=userMapper.getUserByChuangNum(userChuangNum);
        if(user.getAttachment()!=null && user.getAttachment2()!=null && user.getAttachment3()!=null)throw new CommonException(CommonErrorCode.EXCEED_MAX_NUMBER);
        if(fileName==user.getAttachmentName() || fileName==user.getAttachment2Name() || fileName==user.getAttachment3Name())throw new CommonException(CommonErrorCode.WRONG_FILE_NAME);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        UploadResult uploadResult = null;
        String res = null;

        try {

            String name = file.getOriginalFilename();
//            String name=new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
            AssertUtil.notNull(name, CommonErrorCode.FILENAME_CAN_NOT_BE_NULL);
            String first=name.substring(0,name.lastIndexOf("."));
            String extension = name.substring(name.lastIndexOf("."));


            PutObjectRequest putObjectRequest = new PutObjectRequest(COS_BUCKET_NAME, user.getChuangNum() + "." + name, file.getInputStream(), objectMetadata);

            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            uploadResult = upload.waitForUploadResult();

            res =  cosClient.getObjectUrl(COS_BUCKET_NAME,user.getChuangNum()+name).toString();
            if(user.getAttachment()==null){
                user.setAttachment(res);
                user.setAttachmentName(fileName);
            }
            else if(user.getAttachment2()==null){
                user.setAttachment2(res);
                user.setAttachment2Name(fileName);
            }
            else if(user.getAttachment3()==null){
                user.setAttachment3(res);
                user.setAttachment3Name(fileName);
            }
            userMapper.updateByPrimaryKey(user);

        } catch (Exception e){
            //e.printStackTrace();
            throw new CommonException(CommonErrorCode.UPLOAD_FILE_FAIL);
        }


        // 确定本进程不再使用 transferManager 实例之后，关闭之
        // 详细代码参见本页：高级接口 -> 关闭 TransferManager

        return res;
    }


    @Override
    public List<Experience> getUserProjectExperience(String userChuangNum){
        return memberMapper.getMemberExperienceInRecruitProject(userChuangNum,-1);
    }

    @Override
    public String resumeDelete(String url,String chuangNum){
        User user=userMapper.selectOne(User.builder().chuangNum(chuangNum).build());
        if(user.getAttachment()==url)user.setAttachment(null);
        else if(user.getAttachment2()==url)user.setAttachment2(null);
        else if(user.getAttachment3()==url)user.setAttachment3(null);
        else throw new CommonException(CommonErrorCode.FILE_NOT_EXIST);
        userMapper.updateByPrimaryKey(user);
        cosClient.deleteObject(COS_BUCKET_NAME,url.substring(url.indexOf(chuangNum)));
        return "删除成功";
    }


}