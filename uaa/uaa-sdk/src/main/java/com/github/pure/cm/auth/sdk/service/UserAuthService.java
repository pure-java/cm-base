package com.github.pure.cm.auth.sdk.service;

import com.github.pure.cm.auth.sdk.feign.AuthProvider;
import com.github.pure.cm.auth.sdk.properties.OAuth2ClientProperties;
import com.github.pure.cm.auth.sdk.vo.ReqJwtTokenParam;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>用户权限服务
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-07-31 11:33
 **/
@Service
@Slf4j
public class UserAuthService {
    @Autowired
    AuthService authService;

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    /**
     * 用户获取 jwt
     *
     * @param username 客户端信息
     * @param password 客户端信息
     * @return 获取的 jwt 结果
     */
    public String userToken(String username, String password) throws BusinessException {
        ReqJwtTokenParam reqJwtTokenParam =
                new ReqJwtTokenParam()
                        .setGrantType(oAuth2ClientProperties.getGrantType())
                        .setClientId(oAuth2ClientProperties.getClientId())
                        .setClientSecret(oAuth2ClientProperties.getClientSecret())
                        .setUsername(username)
                        .setPassword(password)
                        .setScope(oAuth2ClientProperties.getScope());
        return MapUtils.getString(authService.token(reqJwtTokenParam), "access_token");
    }
}
