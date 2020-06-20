package com.github.pure.cm.auth.server.config.headler;

import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.common.core.util.StringUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 登录成功分发token
 *
 * @author bairitan
 * @since 2019/11/20
 */
@Component
@Slf4j
public class TokenAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    super.onAuthenticationSuccess(request, response, authentication);

    log.debug("login  {}", JsonUtil.json(authentication));

    String token = getHeaderValue(request, HttpHeaders.AUTHORIZATION);

    if (StringUtil.isNotBlank(token)) {

    }

    // OAuth2Authentication oAuth2Authentication = new OAuth2Authentication( authentication);
    // OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
  }


  private String getHeaderValue(HttpServletRequest request, String headerName) {
    String token = request.getHeader(headerName);

    if (StringUtil.isBlank(token)) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals(headerName)) {
          token = cookie.getValue();
        }
      }
    }
    return token;
  }
}
