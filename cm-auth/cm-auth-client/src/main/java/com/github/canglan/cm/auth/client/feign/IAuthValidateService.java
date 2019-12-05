package com.github.canglan.cm.auth.client.feign;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 陈欢
 * @since 2019/11/30
 */
@Component
@FeignClient(value = "${auth.serviceId}")
public interface IAuthValidateService {

  /**
   * 获取auth公钥
   *
   * @return rsa public
   */
  @GetMapping("/oauth/token_key")
  public String publicTokenKey();

  /**
   * password 认证
   */
  @PostMapping("/oauth/token")
  public OAuth2AccessToken token(@RequestParam Map<String, String> parameters);
}
