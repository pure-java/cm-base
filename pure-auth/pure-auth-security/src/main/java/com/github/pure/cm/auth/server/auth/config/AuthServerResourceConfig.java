package com.github.pure.cm.auth.server.auth.config;

import com.github.pure.cm.auth.server.headler.AuthFailPoint;
import com.github.pure.cm.auth.server.headler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务器的资源
 * <br>
 * oauth资源服务器配置，只有配置该类后，才能使用请求头的方式进行验证；并且 ResourceServerConfigurerAdapter 会覆盖 WebSecurityConfigurerAdapter 的配置
 *
 * @author 陈欢
 * @since 2020/6/28
 */
@Configuration
@EnableResourceServer
public class AuthServerResourceConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthFailPoint authFailPoint;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        resources.tokenServices(defaultTokenServices);
        // 配置身份认证异常和权限认证异常处理器
        resources.authenticationEntryPoint(authFailPoint).accessDeniedHandler(customAccessDeniedHandler);
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
        http.exceptionHandling(config ->
                config
                        .authenticationEntryPoint(authFailPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
        );
        super.configure(http);
    }

}
