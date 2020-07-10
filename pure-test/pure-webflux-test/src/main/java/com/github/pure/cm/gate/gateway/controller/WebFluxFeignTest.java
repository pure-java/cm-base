package com.github.pure.cm.gate.gateway.controller;

import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuGroup;
import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuItem;
import com.github.pure.cm.auth.sdk.core.annotation.AuthOption;
import com.github.pure.cm.auth.sdk.core.annotation.AuthResource;
import com.github.pure.cm.auth.sdk.core.annotation.AuthRole;
import com.github.pure.cm.auth.sdk.core.feign.AuthRegisterClient;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.model.auth.vo.AuthRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈欢
 * @since 2020/7/6
 */
@RestController
@RequestMapping("/webFlux/feignTest")
@AuthRole(name = "角色12", authCode = "权限12")
public class WebFluxFeignTest {

    @AuthOption(
            authCode = "sys_authority_list_all",
            menuGroup = {@AuthMenuGroup(groupName = "查询权限", groupId = "sys_authority")},
            menuItems = {
                    @AuthMenuItem(name = "用户权限管理", itemId = "sys_authority_list", parentId = "sys_authority"),
                    @AuthMenuItem(name = "用户权限查询", itemId = "_webflux_test_sys_authority_select", parentId = "sys_authority")
            },
            resources = {
                    @AuthResource(name = "权限列表", resId = "sys_authority_list_page", menuItemId = "sys_authority_list"),
                    @AuthResource(name = "权限列表", resId = "sys_authority_select_page", menuItemId = "sys_authority_select")
            }
    )
    @PostMapping("/errorTest")
    public Result errorTest() {
        throw new BusinessException(501, "自定义");
    }

    @Autowired
    private AuthRegisterClient authRegisterClient;

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody AuthRegisterVo authRegisterVo) {
        return authRegisterClient.registerAuth(authRegisterVo);
    }
}
