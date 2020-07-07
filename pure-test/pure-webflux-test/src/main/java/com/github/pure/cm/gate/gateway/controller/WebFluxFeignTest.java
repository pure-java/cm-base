package com.github.pure.cm.gate.gateway.controller;

import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuGroup;
import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuItem;
import com.github.pure.cm.auth.sdk.core.annotation.AuthOption;
import com.github.pure.cm.auth.sdk.core.annotation.AuthResource;
import com.github.pure.cm.auth.sdk.core.annotation.AuthRole;
import com.github.pure.cm.common.core.exception.BusinessException;import com.github.pure.cm.common.core.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈欢
 * @since 2020/7/6
 */
@RestController
@RequestMapping("/webFlux/feignTest")
public class WebFluxFeignTest {
    @AuthOption(
            authCode = "sys_authority_list_all",
            menuGroup = {@AuthMenuGroup(groupName = "查询权限", groupCode = "sys_authority")},
            menuItems = {
                    @AuthMenuItem(name = "用户权限管理", code = "sys_authority_list", parentCode = "sys_authority"),
                    @AuthMenuItem(name = "用户权限查询", code = "sys_authority_select", parentCode = "sys_authority")
            },
            resources = {
                    @AuthResource(name = "权限列表", code = "sys_authority_list_page", menuItemCode = "sys_authority_list"),
                    @AuthResource(name = "权限列表", code = "sys_authority_select_page", menuItemCode = "sys_authority_select")
            }
    )
    @AuthRole(name = "角色12", code = "权限12")
    @PostMapping("/errorTest")
    public Result errorTest() {
        throw new BusinessException(501, "自定义");
        //return Result.success();
    }
}
