package com.github.pure.cm.auth.server;

import com.github.pure.cm.auth.server.controller.SysUserController;
import com.github.pure.cm.auth.server.model.dto.ClientInfoVo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApplication.class)
@WebAppConfiguration
public class AuthServerApplicationTest {

  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;


  @Before
  public void initMockMvc() {
    // 根据controller进行初始化
    // mockMvc = MockMvcBuilders.standaloneSetup(new SysUserController()).build();
    // 根据web 上下文容器初始化
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void clientLogin() throws Exception {
    String url =  "http://localhost:10002/oauth/token";
    Map<String, String> params = new HashMap<>();
    params.put("grant_type", "password");
    params.put("scope", "all");
    params.put("client_id", "test");
    params.put("client_secret", "admin");
    params.put("username", "admin");
    params.put("password", "admin");

    LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
    params.forEach(valueMap::add);

    this.mockMvc.perform(MockMvcRequestBuilders.post(url).queryParams(valueMap))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lee"));
  }

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

    // BaseClientDetails baseClientDetails = new BaseClientDetails();
    // BeanUtils.copyProperties(clientInfoVo, baseClientDetails);
    // System.out.println(clientDetailsService);
    // System.out.println(baseClientDetails);
    // clientDetailsService.addClientDetails(baseClientDetails);
  }
}