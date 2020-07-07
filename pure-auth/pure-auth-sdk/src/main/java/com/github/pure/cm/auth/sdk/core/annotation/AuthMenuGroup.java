package com.github.pure.cm.auth.sdk.core.annotation;

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
    String groupName();

    /**
     * 菜单组 权限码，应该加上服务名称前缀，如果没加，可以考虑自动添加前缀 _${project-name}_
     */
    String groupCode();

    /**
     * 父级权限码，应该加上服务名称前缀，如果没加，可以考虑自动添加前缀 _${project-name}_
     */
    String parentGroupCode() default "";
}
