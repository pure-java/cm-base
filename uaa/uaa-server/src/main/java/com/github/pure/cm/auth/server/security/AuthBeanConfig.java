package com.github.pure.cm.auth.server.security;

import com.github.pure.cm.auth.server.headler.OauthWebResponseExceptionTranslator;
import com.github.pure.cm.common.core.util.encry.RsaUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈欢
 * @since 2020/6/28
 */
@Slf4j
@Configuration
public class AuthBeanConfig {
    @Autowired
    RsaManager rsaManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 密码加解密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * redis
     */
    @Bean
    public RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(this.redisConnectionFactory);
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
        RsaUtil.RsaKey rsaKey = rsaManager.getRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) rsaKey.getPublicKey();
        RSAPrivateKey privateKey = (RSAPrivateKey) rsaKey.getPrivateKey();

        CustomOauth2AccessToken auth2AccessToken = new CustomOauth2AccessToken();
        auth2AccessToken.setSigner(new RsaSigner(privateKey));
        auth2AccessToken.setVerifier(new RsaVerifier(publicKey));
        byte[] encode = java.util.Base64.getEncoder().encode(publicKey.getEncoded());

        String verifierKey = String.format("%s%s%s", "-----BEGIN PUBLIC KEY-----\n", new String(encode), "\n-----END PUBLIC KEY-----");

        auth2AccessToken.setVerifierKey(verifierKey);
        return auth2AccessToken;
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
            if (authentication.getUserAuthentication() != null && authentication.getUserAuthentication().getPrincipal() != null) {
                User user = (User) authentication.getUserAuthentication().getPrincipal();
                List<String> authorities = user
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                //info.put("oid", user.getOid());
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
