package com.github.canglan.cm.auth.client;

import com.github.canglan.cm.auth.client.feign.IAuthValidateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthClientApplication.class)
public class AuthClientApplicationTest {

  @Autowired
  private IAuthValidateService authValidateService;

  @Test
  public void authValidateService() {
    BaseClientDetails clientDetails = new BaseClientDetails();
    clientDetails.setClientId("test");
    clientDetails.setClientSecret("test");
    authValidateService.registerClient(clientDetails);
  }
}