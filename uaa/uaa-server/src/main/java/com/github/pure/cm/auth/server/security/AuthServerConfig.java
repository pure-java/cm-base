package com.github.pure.cm.auth.server.security;

import com.github.pure.cm.auth.server.headler.AuthFailPoint;
import com.github.pure.cm.auth.server.headler.CustomAccessDeniedHandler;
import com.github.pure.cm.auth.server.headler.OauthWebResponseExceptionTranslator;
import com.github.pure.cm.auth.server.service.ClientDetailService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 配置认证服务<br>
 * 必须强制使用构造注入需要的对象<br>
 * /oauth/token ： 指定身份认证的用户但是未使用记住我功能的用户都能访问。
 *
 * @author bairitan
 * @since 2019/11/14
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final JwtAccessTokenConverter jwtAccessTokenConverter;
    private final AuthenticationManager authenticationManager;
    private final AuthFailPoint authFailPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final HikariDataSource dataSource;
    private final PasswordEncoder passwordEncode;
    private final UserDetailsService userDetailsService;
    private final TokenStore tokenStore;
    private final OauthWebResponseExceptionTranslator oauthWebResponseExceptionTranslator;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                // 开放获取token 公钥 URL：/oauth/token_key
                .tokenKeyAccess("permitAll()")
                // 开放校验tokenURL：/oauth/check_token
                .checkTokenAccess("permitAll()")
                // 认证失败
                .authenticationEntryPoint(authFailPoint)
                // 没有权限
                .accessDeniedHandler(customAccessDeniedHandler)
                // 允许 客户端 表单验证
               ;// .allowFormAuthenticationForClients();

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 配置客户端信息查找器，以及加密方式<Br>
     * 不能使用 bean 注解进行诸如。不然会导致冲突
     */
    public ClientDetailsService clientDetailsService() {
        ClientDetailService clientDetailService = new ClientDetailService(this.dataSource);
        clientDetailService.setPasswordEncoder(this.passwordEncode);
        return clientDetailService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // endpoints 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
        endpoints
                .authenticationManager(this.authenticationManager)
                // 设置用户service
                .userDetailsService(this.userDetailsService)
                //  token 转换器
                .accessTokenConverter(jwtAccessTokenConverter)
                // 刷新token
                .reuseRefreshTokens(true)
                // 设置token存储
                .tokenStore(tokenStore)

                .exceptionTranslator(oauthWebResponseExceptionTranslator);
    }

}
