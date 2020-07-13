package com.github.pure.cm.auth.sdk.core.annotation;

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
     * 资源项 ID ，应该加上服务名称前缀，如果没加，可以考虑自动添加前缀 _${project-code}_
     */
    String resId();

    /**
     * 父级：菜单项 ID，应该加上服务名称前缀，如果没加，可以考虑自动添加前缀 _${project-code}_
     */
    String menuItemId();

}