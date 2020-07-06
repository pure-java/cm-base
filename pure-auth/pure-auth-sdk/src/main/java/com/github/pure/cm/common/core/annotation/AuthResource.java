package com.github.pure.cm.common.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限选项
 *
 * @author 陈欢
 * @since 2020/7/2
 */
@Target(value = {})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthResource {

    /**
     * 权限名称
     */
    String name();

    /**
     * 权限码
     */
    String code();

    /**
     * 父级：菜单项code
     */
    String menuItemCode();

}
