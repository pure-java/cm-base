package com.github.canglan.cm.auth.server.controller;

import com.github.canglan.cm.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @since 2019/11/19
 */
@RestController
@RequestMapping("authServer")
@Slf4j
public class AuthServerController {

  @Autowired
  private TokenStore tokenStore;
  // @Autowired
  // private ClientDetailsService clientDetailsService;
  // @Autowired
  // private ISysAuthorityValidate userService;
  //
  // @PostMapping("/login")
  // public Result login(String userName, String password) {
  //   log.debug("用户 {} 登录", userName);
  //   return Result.success(userService.validate(userName, password));
  // }
  //
  // @PostMapping("/registerClient")
  // public Result registerClient(ClientInfo clientInfo) {
  //   log.debug("客户端注册 {}", clientInfo);
  //
  //   ClientDetails baseClientDetails = new BaseClientDetails();
  //   BeanUtils.copyProperties(clientInfo, baseClientDetails);
  //   ((JdbcClientDetailsService) clientDetailsService).addClientDetails(baseClientDetails);
  //   return Result.success();
  // }


  @PostMapping("/getUser")
  public Result getUser(@RequestParam("token") String token) {
    OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
    Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
    return Result.success(userAuthentication.getPrincipal());
  }
}
