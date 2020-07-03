package com.github.pure.cm.common.core.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义权限
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * 权限组
     */
    AuthGroup[] group() default {};

    /**
     * 权限资源
     */
    AuthResource[] resources() default {};
}
