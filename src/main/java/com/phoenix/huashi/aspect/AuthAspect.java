package com.phoenix.huashi.aspect;

import com.phoenix.huashi.common.CommonConstants;
import com.phoenix.huashi.util.SessionUtils;
import com.phoenix.huashi.annotation.Auth;
import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.dto.SessionData;
import com.phoenix.huashi.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 权限控制
 * @author yannis
 * @version 2020/8/01 17:06
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Autowired
    SessionUtils sessionUtil;

    @Around("@annotation(com.phoenix.huashi.annotation.Auth)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        String sessionId = sessionUtil.getSessionId();
        SessionData sessionData = sessionUtil.getSessionData();

        if (!(sessionId.equals(CommonConstants.ADMIN1_SESSIONID) | sessionId.equals(CommonConstants.ADMIN2_SESSIONID) | sessionId.equals(CommonConstants.ADMIN3_SESSIONID))){
            AssertUtil.notNull(sessionData, CommonErrorCode.INVALID_SESSION);
        }


        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        Auth annotation = method.getAnnotation(Auth.class);

        //log
        log.error("------------");
        if (sessionData != null) log.error("operator: " + sessionData.getId());
        log.error("operation: " + method.getName());

        return joinPoint.proceed();
    }


}
