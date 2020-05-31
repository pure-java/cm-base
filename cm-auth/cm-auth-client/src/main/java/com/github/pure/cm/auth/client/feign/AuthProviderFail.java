package com.github.pure.cm.auth.client.feign;

import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author bairitan
 * @date 2019/12/23
 */
@Component
public class AuthProviderFail implements AuthProvider {

  @Override
  public String publicTokenKey() {
    return null;
  }

  @Override
  public Map<String, Object> checkToken(Map<String, Object> checkToken) {
    return null;
  }

  @Override
  public Map<String, Object> token(Map<String, Object> client) {
    return null;
  }
}
