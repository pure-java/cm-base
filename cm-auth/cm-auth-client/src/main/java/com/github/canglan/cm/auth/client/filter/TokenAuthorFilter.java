package com.github.canglan.cm.auth.client.filter;

import com.github.canglan.cm.auth.client.service.AuthService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.jwt.Jwt;

/**
 * @author bairitan
 * @date 2019/12/7
 */
@Configuration
@Slf4j
public class TokenAuthorFilter implements Filter {

  @Autowired
  private AuthService authService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String header = req.getHeader(HttpHeaders.AUTHORIZATION);
    log.debug(" token header ========= {}", header);
    Jwt jwt = authService.decodeAndVerify(header);
    log.debug(" header jwt  =========== {}", jwt);
    chain.doFilter(req, response);
  }
}
