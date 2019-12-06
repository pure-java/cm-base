package com.github.canglan.cm.auth.client;

import com.github.canglan.cm.auth.client.feign.AuthProvider;
import com.google.common.collect.Maps;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthClientApplication.class)
public class AuthClientApplicationTest {

  @Autowired
  private AuthProvider authValidateService;

  @Test
  public void authValidateService() {
    Map<String, Object> map = Maps.newHashMap();
    map.put("grant_type", "password");
    map.put("client_id", "test");
    map.put("client_secret", "admin");
    map.put("username", "admin");
    map.put("password", "admin");
    // Map<String, Object> s = authValidateService.token(map);
    // System.out.println(s);
  }
}