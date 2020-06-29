package com.github.pure.cm.auth.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 自定义远程token验证，支持服务发现
 *
 * @author 陈欢
 * @since 2020/6/28
 */
//@Configuration
public class FeignRemoteTokenServices extends RemoteTokenServices {
    @Autowired
    AuthService authProvider;

    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        return this.tokenConverter.extractAuthentication(authProvider.checkToken(accessToken));
    }

    @Override
    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        super.setAccessTokenConverter(accessTokenConverter);
        this.tokenConverter = accessTokenConverter;
    }
}
