package com.github.pure.cm.test.web.controller;

import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuGroup;
import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuItem;
import com.github.pure.cm.auth.sdk.core.annotation.AuthOption;
import com.github.pure.cm.auth.sdk.core.annotation.AuthResource;
import com.github.pure.cm.auth.sdk.core.annotation.AuthRole;
import com.github.pure.cm.auth.sdk.feign.AuthRegisterClient;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.model.auth.vo.AuthRegisterVo;
import com.github.pure.cm.test.web.feign.TestFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户验证控制类
 *
 * @author bairitan
 * @date 2019/12/9
 */
@Slf4j
@RestController
@AuthRole(name = "角色1", authCode = "权限1")
@RequestMapping("/web/test")
public class WebTestController {
    @Autowired
    TestFeign testFeign;

    /**
     * 登录，暂时放在网关，可以放到其他模块，但是需要为登录模块提供客户端授权
     *
     * @return 登录结果
     */
    @PostMapping("/login")
    @AuthOption(
            authCode = "sys_authority_list_all",
            menuGroup = {@AuthMenuGroup(groupName = "查询权限", groupCode = "sys_authority")},
            menuItems = {
                    @AuthMenuItem(name = "用户权限管理", itemCode = "sys_authority_list", parentCode = "sys_authority"),
                    @AuthMenuItem(name = "用户权限查询", itemCode = "sys_authority_select", parentCode = "sys_authority")
            },
            resources = {
                    @AuthResource(name = "权限列表", resCode = "sys_authority_list_page", menuItemCode = "sys_authority_list"),
                    @AuthResource(name = "权限列表", resCode = "sys_authority_select_page", menuItemCode = "sys_authority_select")
            }
    )
    public Result<Map<String, Object>> test() {
        return testFeign.selectMenuAuth();
    }

    @Autowired
    private AuthRegisterClient authRegisterClient;

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody AuthRegisterVo authRegisterVo) {
        return authRegisterClient.registerAuth(authRegisterVo);
    }
}
