package com.github.pure.cm.gate.gateway.feign;

import com.github.pure.cm.auth.client.service.AuthService;
import com.github.pure.cm.auth.client.dto.ReqJwtTokenParam;
import com.github.pure.cm.gate.gateway.GatewayApplication;
import java.util.Map;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApplication.class)
public class AuthProviderTest {

  @Autowired
  private AuthService authProvider;

  @org.junit.Test
  public void refreshToken() {
    ReqJwtTokenParam reqJwtTokenParam = new ReqJwtTokenParam();
    reqJwtTokenParam.setClientId("test");
    reqJwtTokenParam.setClientSecret("admin");
    reqJwtTokenParam.setGrantType("refresh_token");
    reqJwtTokenParam.setRefreshToken(
        "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJyb2xlcyI6W10sImV4cCI6MTU5NDI0MDkxOSwianRpIjoiMGJhNDYwNWUtOTBjOC00ODBkLWEzNGEtNTk0ODYxNWE5ODYzIiwiY2xpZW50X2lkIjoidGVzdCIsInVzZXJuYW1lIjoiYWRtaW4ifQ.KVLsQfXL_wzqv81zjRcdhM12w9wV0xn2VJvnNHgU2lfrqnYhDqQGlBWBlHfDX-QMZSy3RSvhwB5wxMRzun3S8RqaHlGFc8AFg1e174UG8Vejksg1UNYxmdw3OxlxwjaWvGJE6C8cUkMoqy1dPTHRUPtuJCXgCMzX4et8jbTzR4k");
    Map<String, Object> objectMap = authProvider.refreshToken(reqJwtTokenParam);
    System.out.println(objectMap);
  }

}
