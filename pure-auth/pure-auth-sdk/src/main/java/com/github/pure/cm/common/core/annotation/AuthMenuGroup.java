package com.github.pure.cm.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 陈欢
 * @since 2020/7/6
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthMenuGroup {
    /**
     * 菜单组 名称
     */
    String menuGroupName();

    /**
     * 菜单组 权限码
     */
    String menuGroupCode();

    /**
     * 父级权限码
     */
    String parentMenuGroupCode() default "";
}
