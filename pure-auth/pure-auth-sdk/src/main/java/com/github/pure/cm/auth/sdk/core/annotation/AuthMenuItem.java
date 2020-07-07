package com.github.pure.cm.auth.sdk.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单项
 *
 * @author 陈欢
 * @since 2020/7/6
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthMenuItem {
    /**
     * 菜单项名称
     */
    String name();

    /**
     * 菜单项权限码，应该加上服务名称前缀，如果没加，可以考虑自动添加前缀 _${project-name}_
     */
    String code();

    /**
     * 父级 权限码，应该加上服务名称前缀，如果没加，可以考虑自动添加前缀 _${project-name}_
     */
    String parentCode();

}
