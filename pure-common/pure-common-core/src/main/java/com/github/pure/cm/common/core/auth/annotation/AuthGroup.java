package com.github.pure.cm.common.core.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限组：菜单组，如果使用在类上，则不会自动解析 请求url；放在方法上时，会自动解析请求url
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthGroup {

    /**
     * 权限名称：菜单名称
     */
    String name();

    /**
     * 资源权限表达式
     */
    String code();

    /**
     * 父级权限名称
     */
    String parentCode() default "root";

    /**
     * 权限
     */
    AuthResource[] authItems() default {};
}
