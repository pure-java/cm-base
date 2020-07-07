package com.github.pure.cm.auth.sdk.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义权限：从下往上定义： 资源 -> 菜单项 -> 菜单组<br>
 * 我们开发一般首先定义资源 {@link AuthResource}，指定菜单项 {@link AuthMenuItem}，通过菜单项指定菜单组 {@link AuthMenuGroup}，就可以定义该资源在系统中的层级关系。<br>
 * 如果菜单组或菜单项之间有层级关系，通过
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthOption {

    /**
     * 权限编码，应该加上服务名称前缀，如果没加，可以考虑自动添加前缀 _${project-name}_
     */
    String authCode();

    /**
     * 菜单项
     */
    AuthMenuItem[] menuItems() default {};

    /**
     * 权限资源
     */
    AuthResource[] resources() default {};

    /**
     * 菜单组
     */
    AuthMenuGroup[] menuGroup() default {};
}
