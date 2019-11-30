package com.github.canglan.cm.auth.server.config;

import com.github.canglan.cm.auth.server.properties.UserAuthProperties;

import com.github.canglan.cm.common.data.util.JacksonUtil;
import com.github.canglan.cm.common.util.StringUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 登录成功分发token
 *
 * @author bairitan
 * @since 2019/11/20
 */
@Component
@EnableConfigurationProperties(UserAuthProperties.class)
@Slf4j
public class TokenAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Autowired
  private UserAuthProperties userAuthProperties;
  @Autowired
  private AuthorizationServerTokenServices authorizationServerTokenServices;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    super.onAuthenticationSuccess(request, response, authentication);

    log.info("login  {}", JacksonUtil.json(authentication));

    String token = getHeaderValue(request, userAuthProperties.getTokenHeader());

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
