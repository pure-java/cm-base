package com.github.pure.cm.auth.sdk.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 陈欢
 * @since 2020/7/6
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRole {
    /**
     * 角色名称
     */
    String name();

    /**
     * 角色 权限code；如果没有带有 ROLE_ 前缀，会自动添加一个 ROLE_ 前缀
     */
    String authCode();
}
