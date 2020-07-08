package com.github.pure.cm.base.web.aspect;

import com.github.pure.cm.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * controller aop：统一处理返回结果
 *
 * @author 陈欢
 * @since 2020/7/7
 */
@Slf4j
//@Aspect
//@Component
public class ControllerAspect {

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void controller() {
    }

    /**
     * 环绕处理
     */
    @Around(value = "controller()")
    public Result controllerAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        if (proceed instanceof Result) {
            return (Result) proceed;
        }
        return Result.success(proceed);
    }

}
