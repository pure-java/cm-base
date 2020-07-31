package com.github.pure.cm.gate.gateway.api;

import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
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

  //@AuthOption(
  //    authCode = "sys_authority_list_all",
  //    menuGroup = {@AuthMenuGroup(groupName = "查询权限", groupCode = "sys_authority")},
  //    menuItems = {
  //        @AuthMenuItem(name = "用户权限管理", itemCode = "sys_authority_list", parentCode = "sys_authority"),
  //        @AuthMenuItem(name = "用户权限查询", itemCode = "_webflux_test_sys_authority_select", parentCode = "sys_authority")
  //    },
  //    resources = {
  //        @AuthResource(name = "权限列表", resCode = "sys_authority_list_page", menuItemCode = "sys_authority_list"),
  //        @AuthResource(name = "权限列表", resCode = "sys_authority_select_page", menuItemCode = "sys_authority_select")
  //    }
  //)
  @PostMapping("/errorTest")
  public Result errorTest() {
    int i = 1 / 0;
    return null;
  }

  //@Autowired
  //private AuthRegisterClient authRegisterClient;
  //
  //@PostMapping("/register")
  //public Result<Boolean> register(@RequestBody AuthRegisterVo authRegisterVo) {
  //  return authRegisterClient.registerAuth(authRegisterVo);
  //}
}
