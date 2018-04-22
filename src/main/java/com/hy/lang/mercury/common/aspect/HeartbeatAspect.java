package com.hy.lang.mercury.common.aspect;

import com.hy.lang.mercury.common.Constants;
import com.hy.lang.mercury.common.enums.HbTypeEnum;
import com.hy.lang.mercury.pojo.UserHeartbeat;
import com.hy.lang.mercury.service.UserAble;
import com.hy.lang.mercury.service.UserHbOpAble;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HeartbeatAspect {

    @Autowired
    private UserHbOpAble userHbOpService;
    @Autowired
    private UserAble userService;

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatAspect.class);

    @Pointcut("execution(public * com.hy.lang.mercury.resource.rest..*.*(..))")
    public void serviceAspect() {
    }

    @Around("serviceAspect()")
    public Object around(JoinPoint joinPoint) throws Throwable {
        if (logger.isInfoEnabled()) {
            logger.info("=== 入参：{};", joinPoint.getArgs());
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long userId = Constants.getUserId(request);
        //HttpSession session = request.getSession();
        //Long userId = (Long) session.getAttribute(Constants.USER_ID);


        Object[] param = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = methodSignature.getParameterNames();
        String onlineFlag = null;
        for (int i = 0; i < paramNames.length; i++) {
            String name = paramNames[i];
            if (StringUtils.isBlank(name)) {
                continue;
            }
            if (Constants.USER_ID.equals(name)) {
                Object tmp = param[i];
                if (tmp != null && tmp instanceof Long) {
                    userId = (Long) tmp;
                } else if (tmp != null && tmp instanceof String) {
                    if (StringUtils.isNumeric((String) tmp)) {
                        userId = Long.valueOf((String) tmp);
                    }
                }

            }
            if (Constants.ONLINE_FLAG.equals(name)) {
                onlineFlag = (String) param[i];
            }
        }
        if (inWhiteList(joinPoint.getSignature()) || userService.isOnline(userId) || (StringUtils.isBlank(onlineFlag) ? Constants.需要登录 : Constants.ONLINE_FLAG.equals(onlineFlag))) {
            if (logger.isInfoEnabled()) {
                logger.info("=== 调用方法：{}；session 未过期，online！", joinPoint.getSignature());
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("=== 调用方法：{}；session 过期，offline！", joinPoint.getSignature());
            }
            return "redirect:/";
            //return JSON.toJSONString(ResponseEntity.createByErrorCodeMessage(OpRespEnum.SESSION失效_需要登录.getCode(), OpRespEnum.SESSION失效_需要登录.name()));
        }
        Object result = ((ProceedingJoinPoint) joinPoint).proceed();

        heartbeatProcess(joinPoint, userId);
        return result;
    }

    private boolean inWhiteList(Signature signature) {
        logger.info(signature.getName());
        logger.info(signature.getDeclaringTypeName());
        if (
                (signature.getName() != null && "login".equals(signature.getName())
                        && signature.getDeclaringTypeName() != null && "com.hy.lang.mercury.resource.rest.UserResource".equals(signature.getDeclaringTypeName()))
                        || (signature.getName() != null && "loginValid".equals(signature.getName())
                        && signature.getDeclaringTypeName() != null && "com.hy.lang.mercury.resource.rest.UserResource".equals(signature.getDeclaringTypeName()))
                        || (signature.getName() != null && "send".equals(signature.getName())
                        && signature.getDeclaringTypeName() != null && "com.hy.lang.mercury.resource.rest.SmsResource".equals(signature.getDeclaringTypeName()))
                        || (signature.getName() != null && "list".equals(signature.getName())
                        && signature.getDeclaringTypeName() != null && "com.hy.lang.mercury.resource.rest.SmsResource".equals(signature.getDeclaringTypeName()))
                        || (signature.getName() != null && "add".equals(signature.getName())
                        && signature.getDeclaringTypeName() != null && "com.hy.lang.mercury.resource.rest.UserResource".equals(signature.getDeclaringTypeName()))
                        || (signature.getName() != null && "test".equals(signature.getName())
                        && signature.getDeclaringTypeName() != null && "com.hy.lang.mercury.resource.rest.SimResource".equals(signature.getDeclaringTypeName()))
                        || (signature.getName() != null && "importCsv".equals(signature.getName())
                        && signature.getDeclaringTypeName() != null && "com.hy.lang.mercury.resource.rest.SimResource".equals(signature.getDeclaringTypeName()))
                        || (signature.getName() != null && "getChildren".equals(signature.getName())
                        && signature.getDeclaringTypeName() != null && "com.hy.lang.mercury.resource.rest.UserResource".equals(signature.getDeclaringTypeName()))
                ) {
            return true;
        }
        return false;
    }

    private void heartbeatProcess(JoinPoint joinPoint, Long userId) {
        if (joinPoint.getSignature().toString().indexOf(Constants.LOGIN_OUT_URL) >= Constants.ZERO_INT) {
            //登出
            userHbOpService.insert(UserHeartbeat.buildEntity(joinPoint.getSignature().toString(), HbTypeEnum.登出, userId));
            if (logger.isInfoEnabled()) {
                logger.info("=== 调用方法：{}；心跳打点，登出", joinPoint.getSignature());
            }
        } else {
            //登入
            userHbOpService.insert(UserHeartbeat.buildEntity(joinPoint.getSignature().toString(), HbTypeEnum.登入, userId));
            if (logger.isInfoEnabled()) {
                logger.info("=== 调用方法：{}；心跳打点，登入", joinPoint.getSignature());
            }
        }


    }
}
