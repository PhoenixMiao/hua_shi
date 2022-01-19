package com.phoenix.huashi.service.Impl;

import com.phoenix.huashi.common.CommonConstants;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.config.YmlConfig;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.dto.WxSession;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.mapper.UserMapper;
import com.phoenix.huashi.service.UserService;
import com.phoenix.huashi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private YmlConfig ymlConfig;



    @Override
    public SessionData login(String code) {

        //shadow test
        if(CommonConstants.SHADOW_TEST.equals(code)){
            sessionUtils.setSessionId(CommonConstants.SHADOW_TEST);
            return new SessionData();
        }

        WxSession wxSession = Optional.ofNullable(
                getWxSessionByCode(code))
                .orElse(new WxSession());

        checkWxSession(wxSession);

        String sessionId = sessionUtils.generateSessionId();

        User user = User.builder()
                .openId(wxSession.getOpenId())
                .build();
        user = userMapper.selectOne(user);

        if(user != null){
            sessionUtils.setSessionId(user.getSessionId());
            return new SessionData(user);
        }


        user = User.builder()
                .createTime(TimeUtil.getCurrentTimestamp())
                .openId(wxSession.getOpenId())
                .unionId(wxSession.getUnionId())
                .sessionKey(wxSession.getSessionKey())
                .sessionId(sessionId)
                .build();

        Long userId = userMapper.newUser(user);

        userMapper.updateChuangNum("hs"+String.format("%08d", userId),userId);

        return new SessionData(user);
    }


    private WxSession getWxSessionByCode(String code){
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

    private void checkWxSession(WxSession wxSession){
        if(wxSession.getErrcode() != null) {
            AssertUtil.isFalse(-1 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_BUSY, wxSession.getErrmsg());
            AssertUtil.isFalse(40029 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_INVALID_CODE, wxSession.getErrmsg());
            AssertUtil.isFalse(45011 == wxSession.getErrcode(), CommonErrorCode.WX_LOGIN_FREQUENCY_REFUSED, wxSession.getErrmsg());
            AssertUtil.isTrue(wxSession.getErrcode() == 0, CommonErrorCode.WX_LOGIN_UNKNOWN_ERROR,wxSession.getErrmsg());
        }
    }



}