package com.phoenix.kuangjia.aspect;

import com.phoenix.kuangjia.common.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author yannis
 * @version 2020/8/1 18:46
 */
@Aspect
@Component
public class ResultAspect {


    @Around("execution(public * com.phoenix.kuangjia.controller.*.*(..))")
    public Result doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object object = proceedingJoinPoint.proceed();
        return Result.success(object);
    }


}
