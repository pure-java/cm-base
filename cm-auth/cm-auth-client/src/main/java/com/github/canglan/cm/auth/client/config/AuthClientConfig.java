package com.github.canglan.cm.auth.client.config;

import com.github.canglan.cm.auth.client.exception.AuthClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.StringUtils;

/**
 * @author bairitan
 * @since 2019/11/14
 */
@Configuration
@EnableResourceServer
@EnableConfigurationProperties(AuthResourceProperties.class)
public class AuthClientConfig extends ResourceServerConfigurerAdapter {

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
  @Autowired
  private AuthResourceProperties authResourceProperties;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    String name = authResourceProperties.getResourceId();
    if (!StringUtils.hasText(name)) {
      throw new AuthClientException("application name is null");
    }
    resources.resourceId(name).stateless(true);
    resources.tokenServices(defaultTokenServices());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/order/*").permitAll()
        .anyRequest().authenticated();
  }

  /**
   * 创建一个默认的资源服务token
   */
  @Bean
  public ResourceServerTokenServices defaultTokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    // 使用自定义的Token转换器
    defaultTokenServices.setTokenEnhancer(accessTokenConverter());
    // 使用自定义的tokenStore
    defaultTokenServices.setTokenStore(tokenStore());
    return defaultTokenServices;
  }

  @Bean
  public TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
    accessTokenConverter.setSigningKey("SigningKey");
    return accessTokenConverter;
  }

}
