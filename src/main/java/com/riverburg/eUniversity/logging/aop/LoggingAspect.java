package com.riverburg.eUniversity.logging.aop;

import com.riverburg.eUniversity.logging.service.LoggingService;
import com.riverburg.eUniversity.model.dto.response.base.BaseResponse;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@AllArgsConstructor
public class LoggingAspect {

    private final LoggingService loggingService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com..*..*ServiceImpl) ||" +
            "within(com..*..*RepositoryImpl))")
    public void componentPointcut() {

    }

    @Pointcut("within(com..*..*Controller)")
    public void restControllerPointcut() {

    }

    @Around("restControllerPointcut() &&" +
            "execution(* *(!@org.springframework.web.bind.annotation.RequestBody (*), ..))")
    public Object logRestControllerWithoutBody(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logger.info("===> " +
                        "method: {} " +
                        "path: {}",
                request.getMethod(),
                request.getRequestURI());

        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            loggingService.logError(request.getRequestURI(), request.getMethod(), null, ex);
            throw ex;
        }

        logger.info("<=== " +
                        "method: {} " +
                        "path: {} \n " +
                        "response: {}",
                request.getMethod(),
                request.getRequestURI(),
                result);

        try {
            loggingService.log(request.getRequestURI(), request.getMethod(), null, (ResponseEntity<BaseResponse<?>>) result);
        } catch (Throwable ex) {
            logger.error("Error while logging: " + ex.getMessage());
        }

        return result;
    }

    @Around("restControllerPointcut() &&" +
            "execution(* *(@org.springframework.web.bind.annotation.RequestBody (*), ..)) && " +
            "args(body, ..)")
    public Object logRestControllerWithBody(ProceedingJoinPoint joinPoint, Object body) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logger.info("===> " +
                        "method: {} " +
                        "path: {}",
                request.getMethod(),
                request.getRequestURI());

        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            loggingService.logError(request.getRequestURI(), request.getMethod(), body, ex);
            throw ex;
        }

        logger.info("<=== " +
                        "method: {} " +
                        "path: {} \n " +
                        "response: {}",
                request.getMethod(),
                request.getRequestURI(),
                result);

        try {
            loggingService.log(request.getRequestURI(), request.getMethod(), body, (ResponseEntity<BaseResponse<?>>) result);
        } catch (Throwable ex) {
            logger.error("Error while logging: " + ex.getMessage());
        }

        return result;
    }


    @Before("componentPointcut()")
    public void logComponentBefore(JoinPoint joinPoint) {
        String componentName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        try {
            var args = extractParameters(joinPoint);

            logger.info("===> " +
                            "component: {} " +
                            "method: {} \n" +
                            "args: {}",
                    componentName,
                    methodName,
                    args);
        } catch (Throwable ex) {
            logger.error("<=== Error {}.{}: {}", componentName, methodName, ex.getMessage());
        }
    }

    @AfterReturning(pointcut = "componentPointcut()", returning = "result")
    public void logComponentAfter(JoinPoint joinPoint, Object result) {
        String componentName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        logger.info("<=== " +
                        "component: {} " +
                        "method: {} \n" +
                        "args: {}",
                componentName,
                methodName,
                result);
    }

    private String extractParameters(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        String[] paramsNames = codeSignature.getParameterNames();
        Object[] paramsValues = joinPoint.getArgs();

        StringBuilder extractedParamsAsString = new StringBuilder();

        for (int i = 0; i < paramsNames.length; i++) {
            extractedParamsAsString.append(String.format("%s = %s", paramsNames[i], paramsValues[i]));
        }

        return extractedParamsAsString.toString();
    }
}
