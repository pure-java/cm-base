package com.github.pure.cm.gate.gateway.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pure.cm.auth.client.dto.ReqJwtTokenParam;
import com.github.pure.cm.auth.client.properties.OAuth2ClientProperties;
import com.github.pure.cm.auth.client.service.AuthService;
import com.github.pure.cm.common.core.model.Result;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户验证控制类
 *
 * @author bairitan
 * @date 2019/12/9
 */
@Slf4j
@RestController
@RequestMapping("/user/auth")
public class UserAuthController {

  @Autowired
  private AuthService authService;
  @Autowired
  private OAuth2ClientProperties auth2ClientProperties;

  /**
   * 登录，暂时放在网关，可以放到其他模块，但是需要为登录模块提供客户端授权
   *
   * @param username 账号
   * @return 登录结果
   */
  @PostMapping("/login")
  @SentinelResource(value = "/user/auth/login")
  public Result login(ReqJwtTokenParam username) {
    ReqJwtTokenParam reqJwtTokenParam = new ReqJwtTokenParam();
    reqJwtTokenParam
        .setGrantType(auth2ClientProperties.getGrantType())
        .setClientId(auth2ClientProperties.getClientId())
        .setClientSecret(auth2ClientProperties.getClientSecret())
        .setUsername(username.getUsername())
        .setPassword(username.getPassword())
        .setScope(auth2ClientProperties.getScope());
    Map<String, Object> token = authService.token(reqJwtTokenParam);
    log.debug("login result = {}", token);
    return Result.success(token);
  }
}
