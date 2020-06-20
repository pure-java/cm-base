package com.github.pure.cm.auth.server;

import com.github.pure.cm.auth.server.model.dto.ClientInfoVo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApplication.class)
public class AuthServerApplicationTest {
  @Autowired
  private ClientRegistrationService clientDetailsService;

  @Test
  public void registerClient() {
    ClientInfoVo clientInfoVo = new ClientInfoVo();
    List<GrantedAuthority> grantedAuthority = AuthorityUtils.createAuthorityList("admin");
    HashMap<String, Object> additionalInformation = Maps.newHashMap();
    additionalInformation.put("test", "test");

    clientInfoVo
        .setClientId("test")
        .setClientSecret("test")
        .setScope(Sets.newHashSet("test"))
        .setAdditionalInformation(additionalInformation)
        .setAuthorities(grantedAuthority)
        .setRegisteredRedirectUri(Sets.newHashSet("test"))
        .setAuthorizedGrantTypes(Sets.newHashSet("all"))
        .setAutoApproveScopes(Sets.newHashSet("all")).
        setResourceIds(Sets.newHashSet("test"))
        .setAccessTokenValiditySeconds(1000)
        .setRefreshTokenValiditySeconds(1000);

    BaseClientDetails baseClientDetails = new BaseClientDetails();
    // baseClientDetails.setClientId("test");
    // baseClientDetails.setClientSecret("test");
    // baseClientDetails.setScope(Sets.newHashSet("test"));
    // baseClientDetails.setAdditionalInformation(additionalInformation);
    // baseClientDetails.setAuthorities(Lists.newArrayList(grantedAuthority));
    // baseClientDetails.setRegisteredRedirectUri(Sets.newHashSet("test"));
    // baseClientDetails.setAuthorizedGrantTypes(Sets.newHashSet("all"));
    // baseClientDetails.setAutoApproveScopes(Sets.newHashSet("all"));
    // baseClientDetails.setResourceIds(Sets.newHashSet("test"));
    // baseClientDetails.setAccessTokenValiditySeconds(1000);
    // baseClientDetails.setRefreshTokenValiditySeconds(1000);
    BeanUtils.copyProperties(clientInfoVo, baseClientDetails);
    System.out.println(clientDetailsService);
    System.out.println(baseClientDetails);
    clientDetailsService.addClientDetails(baseClientDetails);
  }
}