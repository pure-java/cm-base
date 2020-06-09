package com.github.pure.cm.gate.gateway.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pure.cm.auth.client.dto.ReqJwtTokenParam;
import com.github.pure.cm.auth.client.properties.OAuth2ClientProperties;
import com.github.pure.cm.auth.client.service.AuthService;
import com.github.pure.cm.common.core.IgnoreToken;
import com.github.pure.cm.common.core.exception.ApiException;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
   * @param userInfo 账号
   * @return 登录结果
   */
  @PostMapping("/login")
  @SentinelResource(value = "/user/auth/login")
  @IgnoreToken
  public Result login(@RequestBody ReqJwtTokenParam userInfo) throws BusinessException {
    ReqJwtTokenParam reqJwtTokenParam = new ReqJwtTokenParam();
    reqJwtTokenParam
        .setGrantType(auth2ClientProperties.getGrantType())
        .setClientId(auth2ClientProperties.getClientId())
        .setClientSecret(auth2ClientProperties.getClientSecret())
        .setUsername(userInfo.getUsername())
        .setPassword(userInfo.getPassword())
        .setScope(auth2ClientProperties.getScope());
    Map<String, Object> token = null;
    try {
      token = authService.token(reqJwtTokenParam);
      //throw new ApiException();
    } catch (ApiException e) {
      throw new BusinessException();
    }
    log.debug("login result = {}", token);
    return Result.success(token);
  }
}
