package com.github.canglan.cm.auth.client;

import com.github.canglan.cm.auth.client.exception.AuthClientException;
import com.github.canglan.cm.auth.common.AuthCommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * @author bairitan
 * @since 2019/11/18
 */
public class AuthUtils {

  public static String getReqUser(HttpServletRequest req) {
    String header = req.getHeader("Authorization");
    if (!StringUtils.hasText(header)) {
      return null;
    }
    String token = header.substring("bearer".length());
    Claims claims;
    try {
      claims = Jwts.parser().setSigningKey("SigningKey".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      throw new AuthClientException(e);
    }
    // 拿到当前用户
    return (String) claims.get(AuthCommonConstants.REQUEST_USER_HEADER);
  }
}
