package com.phoenix.huashi.aspect;

import com.phoenix.huashi.annotation.Admin;
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

@Aspect
@Component
@Slf4j
public class AdminAspect {

    @Autowired
    SessionUtils sessionUtil;

    @Around("@annotation(com.phoenix.huashi.annotation.Admin)")
    public Object doAroundAdmin(ProceedingJoinPoint joinPoint) throws Throwable {

        String sessionId = sessionUtil.getSessionId();

        AssertUtil.notNull(sessionId, CommonErrorCode.INVALID_SESSION);
        AssertUtil.isTrue(sessionId.equals(CommonConstants.ADMIN1_SESSIONID)|sessionId.equals(CommonConstants.ADMIN1_SESSIONID)|sessionId.equals(CommonConstants.ADMIN1_SESSIONID),CommonErrorCode.USER_NOT_ADMIN);

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        Admin annotation = method.getAnnotation(Admin.class);

        //log
        log.error("------------");
        log.error("operator: " + sessionId);
        log.error("operation: " + method.getName());

        return joinPoint.proceed();
    }
}
