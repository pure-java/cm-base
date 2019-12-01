package com.github.canglan.cm.auth.server.controller;

import com.github.canglan.cm.auth.server.model.dto.ClientInfo;
import com.github.canglan.cm.auth.server.service.ISysAuthorityValidate;
import com.github.canglan.cm.common.data.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @since 2019/11/19
 */
@RestController
@RequestMapping("jwt")
@Slf4j
public class AuthController {

  @Autowired
  private ClientDetailsService clientDetailsService;
  @Autowired
  private ISysAuthorityValidate userService;

  @PostMapping("/login")
  public Result login(String userName, String password) {
    log.debug("用户 {} 登录", userName);
    return Result.success(userService.validate(userName, password));
  }

  @PostMapping("/registerClient")
  public Result registerClient(ClientInfo clientInfo) {
    log.debug("客户端注册 {}", clientInfo);

    ClientDetails baseClientDetails = new BaseClientDetails();
    BeanUtils.copyProperties(clientInfo, baseClientDetails);
    ((JdbcClientDetailsService) clientDetailsService).addClientDetails(baseClientDetails);
    return Result.success();
  }

}
