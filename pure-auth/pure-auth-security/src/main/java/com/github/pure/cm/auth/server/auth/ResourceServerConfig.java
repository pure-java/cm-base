package com.github.pure.cm.auth.server.auth;

import com.github.pure.cm.auth.server.headler.CustomAccessDeniedHandler;
import com.github.pure.cm.auth.server.headler.CustomAuthPoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * oauth资源服务器配置，只有配置该类后，才能使用请求头的方式进行验证；并且 ResourceServerConfigurerAdapter 会覆盖 WebSecurityConfigurerAdapter 的配置
 *
 * @author 陈欢
 * @since 2020/6/28
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private CustomAuthPoint customAuthPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        resources.tokenServices(defaultTokenServices);
        super.configure(resources);
    }

    /**
     * 限制所有方法都需要进行验证；并且配置认证失败或没权限时的异常处理
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").authenticated();

        // 配置身份认证异常和权限认证异常处理器
        http.exceptionHandling(config -> config.accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(customAuthPoint));
        super.configure(http);
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

}
