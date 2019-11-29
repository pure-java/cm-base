package com.github.canglan.cm.auth.server.config;

import com.alibaba.fastjson.JSON;
import com.github.canglan.cm.auth.common.AuthCommonConstants;
import com.github.canglan.cm.auth.common.RsaUtil.RsaKey;
import com.github.canglan.cm.auth.server.properties.ClientAuthProperties;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 配置认证服务
 *
 * @author bairitan
 * @since 2019/11/14
 */
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(ClientAuthProperties.class)
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  private RsaConfig rsaConfig;

  @Autowired
  public AuthServerConfig(RsaConfig rsaConfig) {
    this.rsaConfig = rsaConfig;
  }

  private static final int ACCESS_TOKEN_TIMER = 60 * 60 * 24;
  private static final int REFRESH_TOKEN_TIMER = 60 * 60 * 24 * 30;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // clients 用来配置客户端信息
    BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();

    clients.inMemory()
        .withClient("myapp")
        .resourceIds("order")
        // 设置密码模式并且开启刷新 token 端口
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("all")
        .authorities("ADMIN")
        // 客户端 secret 也需要进行加密
        .secret(passwordEncode.encode("lxapp"))
        .accessTokenValiditySeconds(ACCESS_TOKEN_TIMER)
        .refreshTokenValiditySeconds(REFRESH_TOKEN_TIMER);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    // security 配置令牌端点(Token Endpoint)的安全约束.
    // 开放/oauth/token_key
    security.tokenKeyAccess("permitAll()")
        // 开放/oauth/check_token
        .checkTokenAccess("isAuthenticated()")
        // 允许表单认证
        .allowFormAuthenticationForClients();
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    // endpoints 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
    endpoints.accessTokenConverter(accessTokenConverter());
    endpoints.tokenStore(tokenStore());
    endpoints.authenticationManager(this.authenticationManager);
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter() {
      @Override
      public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication().getName();
        // 得到用户名，去处理数据库可以拿到当前用户的信息和角色信息（需要传递到服务中用到的信息）
        final Map<String, Object> additionalInformation = new HashMap<>();
        // Map假装用户实体
        Map<String, String> userinfo = new HashMap<>();
        userinfo.put("id", "1");
        userinfo.put("username", userName);
        userinfo.put("qqnum", "438944209");
        userinfo.put("userFlag", "1");
        additionalInformation.put(AuthCommonConstants.REQUEST_USER_HEADER, JSON.toJSONString(userinfo));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return super.enhance(accessToken, authentication);
      }
    };

    // RSA非对称加密方式
    RsaKey rsaKey = rsaConfig.getRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) rsaKey.getPublicKey();
    RSAPrivateKey privateKey = (RSAPrivateKey) rsaKey.getPrivateKey();

    tokenConverter.setSigner(new RsaSigner(privateKey));

    tokenConverter.setVerifier(new RsaVerifier(publicKey));

    String verifierKey = String.format("%s%s%s",
        "-----BEGIN PUBLIC KEY-----\n", new String(Base64.encode(publicKey.getEncoded())), "\n-----END PUBLIC KEY-----");
    tokenConverter.setVerifierKey(verifierKey);

    return tokenConverter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

}
