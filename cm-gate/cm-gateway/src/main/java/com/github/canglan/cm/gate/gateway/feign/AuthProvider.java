package com.github.canglan.cm.gate.gateway.feign;

import com.github.canglan.cm.gate.gateway.model.UserDetails;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bairitan
 * @date 2019/12/4
 */
@FeignClient(name = "auth-server")
public interface AuthProvider {

  /**
   * 验证to   *
   *
   * @param token token
   * @return 返回结果
   */
  @PostMapping(value = "/oauth/check_token")
  public Map<String, Object> checkToken(@RequestParam(name = "token") String token);


}
