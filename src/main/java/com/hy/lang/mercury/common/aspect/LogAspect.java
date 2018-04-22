package com.hy.lang.mercury.common.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.hy.lang.mercury.service..*.*(..))")
    public void serviceAspect() {

    }

    @Around("serviceAspect()")
    public Object around(JoinPoint joinPoint) throws Throwable {
        Object[] param = joinPoint.getArgs();
        try {
            if (logger.isInfoEnabled()) {
                logger.info("===> 调用方法：{}；输入参数：{}", joinPoint.getSignature()
                        , (param == null ? "null" : JSON.toJSONString(param)));
            }
        } catch (Exception e) {
            //
        }
        long start = System.currentTimeMillis();

        Object result = ((ProceedingJoinPoint) joinPoint).proceed();

        long end = System.currentTimeMillis();
        try {
            if (logger.isInfoEnabled()) {
                logger.info("<=== 调用方法：{}；输出参数:{}；耗时：{} ms", joinPoint.getSignature()
                        , (result == null ? "null" : JSON.toJSONString(result)), (end - start));
            }
        } catch (Exception e) {
            //
        }
        return result;
    }

}
