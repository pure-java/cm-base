package com.github.canglan.cm.auth.client.feign;

import java.util.Map;

/**
 * @author bairitan
 * @date 2019/12/23
 */
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
