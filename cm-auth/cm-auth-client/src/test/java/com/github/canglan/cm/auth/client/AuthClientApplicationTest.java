package com.github.canglan.cm.auth.client;

import com.github.canglan.cm.auth.client.feign.IAuthValidateService;
import com.google.common.collect.Maps;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthClientApplication.class)
public class AuthClientApplicationTest {

  @Autowired
  private IAuthValidateService authValidateService;

  @Test
  public void authValidateService() {
    Map<String, String> map = Maps.newHashMap();
    map.put("grant_type", "password");
    map.put("client_id", "test");
    map.put("client_secret", "admin");
    map.put("username", "admin");
    map.put("password", "admin");
    OAuth2AccessToken s = authValidateService.token(map);
    System.out.println(s);

  }
}