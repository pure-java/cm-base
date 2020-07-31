package com.github.pure.cm.auth.sdk.core.support;

import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuItem;
import com.github.pure.cm.auth.sdk.core.annotation.AuthOption;
import com.github.pure.cm.auth.sdk.core.annotation.AuthResource;
import com.github.pure.cm.auth.sdk.core.annotation.AuthRole;
import com.github.pure.cm.auth.sdk.util.AuthUtil;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 服务权限
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-07-29 16:22
 **/
@Slf4j
@Aspect
@Component
public class AuthAspect {

    /**
     * 扫描权限
     */
    @Pointcut("@within(com.github.pure.cm.auth.sdk.core.annotation.AuthRole) || @annotation(com.github.pure.cm.auth.sdk.core.annotation.AuthOption)")
    public void scan() {

    }

    @Before("scan()")
    public void auth(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AuthOption authOption = signature.getMethod().getAnnotation(AuthOption.class);

        AuthRole authRole = joinPoint.getTarget().getClass().getAnnotation(AuthRole.class);

        Set<String> authCodeSet = Sets.newHashSet();

        if (Objects.nonNull(authRole)) {
            authCodeSet.add(AuthUtil.convertRoleCode(authRole.authCode()));
        }
        if (Objects.isNull(authOption)) {
            return;
        }

        AuthMenuItem[] authMenuItems = authOption.menuItems();
        for (AuthMenuItem authMenuItem : authMenuItems) {
            authCodeSet.add(AuthUtil.convertAuthCode(authMenuItem.itemCode()));
        }

        for (AuthResource resource : authOption.resources()) {
            authCodeSet.add(AuthUtil.convertAuthCode(resource.resCode()));
        }
        //log.error("jwt：{}", );
    }
}
