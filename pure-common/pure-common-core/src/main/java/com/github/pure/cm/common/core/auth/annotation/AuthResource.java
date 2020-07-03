package com.github.pure.cm.common.core.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限选项
 *
 * @author 陈欢
 * @since 2020/7/2
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthResource {

    /**
     * 权限名称
     */
    String name();

    /**
     * 权限表达式
     */
    String code()/* default "*"*/;

    /**
     * 组名
     */
    String groupCode() default "root";

}
