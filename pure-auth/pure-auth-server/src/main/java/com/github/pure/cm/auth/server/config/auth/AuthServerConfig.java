package com.github.pure.cm.auth.server.config.auth;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

import com.github.pure.cm.auth.server.handler.CustomAccessDeniedHandler;
import com.github.pure.cm.auth.server.handler.CustomAuthPoint;
import com.github.pure.cm.auth.server.handler.OauthWebResponseExceptionTranslator;
import com.github.pure.cm.auth.server.model.dto.LoginUserVo;
import com.github.pure.cm.auth.server.service.impl.ClientDetailService;
import com.github.pure.cm.common.core.util.encry.RsaUtil;
import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariDataSource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

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
@ConditionalOnProperty(name = "pure.auth.type", havingValue = "shiro", matchIfMissing = false)
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private final RsaConfig rsaConfig;
    private final AuthenticationManager authenticationManager;
    private final RedisConnectionFactory redisConnectionFactory;
    private final CustomAuthPoint customAuthPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final HikariDataSource dataSource;
    private final PasswordEncoder passwordEncode;
    private final UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()") // 开放获取token 公钥 URL：/oauth/token_key

                .checkTokenAccess("permitAll()") // 开放校验tokenURL：/oauth/check_token
                // AuthenticationEntryPoint
                .authenticationEntryPoint(customAuthPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                // 允许使用 client 验证
                .allowFormAuthenticationForClients();

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
                .accessTokenConverter(accessTokenConverter())
                // 刷新token
                .reuseRefreshTokens(true)
                // 设置token存储
                .tokenStore(tokenStore())

                .exceptionTranslator(oauthWebResponseExceptionTranslator());
    }

    /**
     * 对 oauth 错误信息进行转换
     */
    @Bean
    public OauthWebResponseExceptionTranslator oauthWebResponseExceptionTranslator() {
        return new OauthWebResponseExceptionTranslator();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        // RSA非对称加密方式
        RsaUtil.RsaKey rsaKey = rsaConfig.getRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) rsaKey.getPublicKey();
        RSAPrivateKey privateKey = (RSAPrivateKey) rsaKey.getPrivateKey();

        CustomOauth2AccessToken auth2AccessToken = new CustomOauth2AccessToken();
        auth2AccessToken.setSigner(new RsaSigner(privateKey));
        auth2AccessToken.setVerifier(new RsaVerifier(publicKey));
        byte[] encode = java.util.Base64.getEncoder().encode(publicKey.getEncoded());

        String verifierKey = String.format("%s%s%s",
                "-----BEGIN PUBLIC KEY-----\n",
                new String(encode),
                "\n-----END PUBLIC KEY-----");

        auth2AccessToken.setVerifierKey(verifierKey);
        return auth2AccessToken;
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 自定义访问令牌，在访问令牌中添加一些自定义声明
     */
    static class CustomOauth2AccessToken extends JwtAccessTokenConverter {

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            DefaultOAuth2AccessToken resultOauth2Token = new DefaultOAuth2AccessToken(accessToken);
            log.debug(" result =  {}", resultOauth2Token);
            log.debug("oauth2token===>{}", authentication);
            log.debug("principal===>{}", authentication.getPrincipal());

            log.debug(" ref  = {}", resultOauth2Token.getRefreshToken());
            Map<String, Object> info = Maps.newLinkedHashMap(accessToken.getAdditionalInformation());
            String tokenId = resultOauth2Token.getValue();
            if (!info.containsKey(TOKEN_ID)) {
                info.put(TOKEN_ID, tokenId);
            }
            info.put("organization", authentication.getName() + randomAlphabetic(4));
            if (authentication.getUserAuthentication() != null && authentication.getUserAuthentication().getPrincipal() != null) {
                LoginUserVo user = (LoginUserVo) authentication.getUserAuthentication().getPrincipal();
                List<String> authorities = user
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                info.put("oid", user.getOid());
                info.put("username", user.getUsername());
                info.put("roles", authorities);
            }

            log.debug("info============>{}", info);
            resultOauth2Token.setAdditionalInformation(info);
            resultOauth2Token.setValue(encode(resultOauth2Token, authentication));
            return resultOauth2Token;
        }
    }
}
