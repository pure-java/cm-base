package com.github.canglan.cm.gate.gateway.service.impl;

import com.github.canglan.cm.gate.gateway.feign.AuthProvider;
import com.github.canglan.cm.gate.gateway.service.IAuthService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bairitan
 * @date 2019/12/4
 */
@Slf4j
@Service
public class AuthService implements IAuthService {

  @Autowired
  private AuthProvider authProvider;

  @Override
  public boolean checkToken(String token) {
    Map<String, Object> userDetails = authProvider.checkToken(token);
    log.debug(" checkToken = {}", userDetails);
    if (userDetails != null) {
      return true;
    }
    return false;
  }
}
