package com.github.pure.cm.auth.server.security;

import com.github.pure.cm.auth.resource.support.AuthIgnoreHandler;
import com.github.pure.cm.auth.server.headler.AuthFailPoint;
import com.github.pure.cm.auth.server.headler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

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
    private RedisTokenStore tokenStore;

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;

    @Autowired
    private AuthFailPoint authFailPoint;

    @Autowired
    private AuthIgnoreHandler authIgnoreHandler;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        resources.tokenServices(defaultTokenServices);
        // 配置身份认证异常和权限认证异常处理器
        resources.authenticationEntryPoint(authFailPoint).accessDeniedHandler(customAccessDeniedHandler);
    }

    /**
     * 限制所有方法都需要进行验证；并且配置认证失败或没权限时的异常处理
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers(authIgnoreHandler.getAuthIgnoreUrl().toArray(new String[0]))
                .permitAll()

                .mvcMatchers(errorPath)//设置错误请求路径不需要权限
                .permitAll()

                // 除上面的url外将受到权限保护
                .antMatchers("/**")
                .authenticated()

                .and()

                // 配置身份认证异常和权限认证异常处理器
                .exceptionHandling()
                .authenticationEntryPoint(authFailPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
    }

}
