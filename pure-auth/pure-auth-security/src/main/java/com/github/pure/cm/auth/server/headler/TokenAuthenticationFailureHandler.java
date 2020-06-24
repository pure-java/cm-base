package com.github.pure.cm.auth.server.headler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败
 *
 * @author chenhuan
 * @since 2019/11/20
 */
@Component
public class TokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException e) throws IOException {
    response.setContentType("application/json;charset=utf-8");
    response.getWriter().write(e.getMessage());
  }
}
