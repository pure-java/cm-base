package com.github.canglan.cm.gate.gateway.feign;

import com.github.canglan.cm.auth.client.service.AuthService;
import com.github.canglan.cm.auth.common.RefreshToken;
import com.github.canglan.cm.gate.gateway.GatewayApplication;
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
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setClientId("test");
    refreshToken.setClientSecret("admin");
    refreshToken.setGrantType("refresh_token");
    refreshToken.setRefreshToken(
        "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiJjNzQ4NWI0Ni1jODVhLTQyNDgtODU1MC04NGYyYTA1MjYxMmMiLCJleHAiOjE1ODU2MjEyNzcsImF1dGhvcml0aWVzIjpbIm1hbmFnZXIiLCJhZG1pbiJdLCJqdGkiOiJkNzZiMDZjMy0zMmIxLTRhNmUtYmVmMy05MzNiN2IyYWU3OWYiLCJjbGllbnRfaWQiOiJ0ZXN0In0.AodzkB6J7PoDGg9Y7JbArE93I1DlZX1-YcAfj7ZtwfZo_-FqesdWujweGkn5VfK1xbvMk6-5EFUdHU8OHniL4EMvrRSYQT_rqJEyK3KE_4tyVB95DjwDuwmxEQnOoO2MLhJPNpMraSs3gSmYpPrPj-1Rvt4_Gw7MBl2rasGBu_I");
    Map<String, Object> objectMap = authProvider.refreshToken(refreshToken);
    System.out.println(objectMap);
  }

}
