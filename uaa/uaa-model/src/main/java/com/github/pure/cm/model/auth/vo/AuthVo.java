package com.github.pure.cm.model.auth.vo;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * @author 陈欢
 * @since 2020/7/7
 */
@Getter
public class AuthVo {
    /**
     * 资源code -> 资源
     */
    Map<String, AuthResourceVo> authResourceVos = Maps.newHashMap();

    /**
     * 菜单项code -> 菜单项
     */
    Map<String, AuthMenuItemVo> authMenuItemVos = Maps.newHashMap();

    /**
     * 菜单组code -> 菜单组
     */
    Map<String, AuthMenuGroupVo> authMenuGroupVos = Maps.newHashMap();

    /**
     * 角色
     */
    Map<String, AuthRoleVo> authRoleVos = Maps.newHashMap();
}
