package com.github.canglan.cm.auth.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 陈欢
 * @since 2019/11/30
 */
@FeignClient(value = "${auth.serviceId}")
public interface IAuthValidateService {

  /**
   * 获取auth公钥
   *
   * @return rsa public
   */
  @GetMapping("/oauth/token_key")
  public String publicTokenKey();

  @PostMapping("/jwt/registerClient")
  public String registerClient(@RequestBody ClientDetails clientDetails);
}
