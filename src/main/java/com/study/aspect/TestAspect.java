package com.study.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/3/6 15:28
 */
@Component
@Aspect
@Slf4j
public class TestAspect {

    @Around("execution(* com.study.controller.*.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        log.info("切片开始。。。");
        long startTime = System.currentTimeMillis();

        // 获取请求入参
        Object[] args = proceedingJoinPoint.getArgs();
        Arrays.stream(args).forEach(arg -> log.info("arg is {}", arg));

        // 获取相应
        Object response = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("请求:{}, 耗时{}ms", proceedingJoinPoint.getSignature(), (endTime - startTime));
        log.info("切片结束。。。");
        return response;
    }

    @Pointcut("execution(* com.study.controller.*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before() {
        log.info("aop before");
    }

    @After("pointcut()")
    public void after() {
        log.info("aop after");
    }

}
