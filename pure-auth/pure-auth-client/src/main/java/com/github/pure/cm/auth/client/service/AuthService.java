package com.github.pure.cm.auth.client.service;

import com.github.pure.cm.auth.client.dto.ReqJwtTokenParam;
import com.github.pure.cm.auth.client.exception.AuthClientException;
import com.github.pure.cm.auth.client.feign.AuthProvider;
import com.github.pure.cm.common.core.exception.ApiException;
import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.common.core.util.StringUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 权限服务类，提供权限操作
 *
 * @author bairitan
 * @since 2019/11/18
 */
@Service
@Slf4j
public class AuthService {

    private final JsonUtil jsonUtil = JsonUtil.singleInstance();
    @Autowired
    private AuthProvider authProvider;

    /**
     * 获取 jwt
     *
     * @param reqJwtTokenParam 客户端信息
     * @return 获取的 jwt 结果
     */
    public Map<String, Object> token(ReqJwtTokenParam reqJwtTokenParam) throws ApiException {
        Map<String, Object> client = reqJwtTokenParam.toMap();
        return authProvider.token(client);
    }

    /**
     * 检查 jwt token
     *
     * @param token token
     * @return 检查结果
     */
    public Map<String, Object> checkToken(String token) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("token", token);
        Map<String, Object> userDetails = authProvider.checkToken(map);
        log.debug(" checkToken = {}", userDetails);
        return userDetails;
    }

    /**
     * 验证token
     *
     * @param request webflux 请求
     * @return 验证结果
     */
    public boolean checkToken(ServerHttpRequest request) {
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return StringUtil.isNotBlank(authentication) && Objects.nonNull(this.checkToken(authentication));
    }

    /**
     * 验证token
     *
     * @param servletRequest web 请求
     * @return 验证结果
     */
    public boolean checkToken(HttpServletRequest servletRequest) {
        String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        return StringUtil.isNotBlank(authorization) && Objects.nonNull(this.checkToken(authorization));
    }

    /**
     * 刷新 jwt token
     *
     * @param reqJwtTokenParam 刷新用 token
     * @return 刷新之后新的jwt
     */
    public Map<String, Object> refreshToken(ReqJwtTokenParam reqJwtTokenParam) {
        return authProvider.checkToken(reqJwtTokenParam.toMap());
    }

    /**
     * 对 jwt token 进行解码
     *
     * @param token jwt
     * @return 解码后的结果
     */
    public Jwt decodeAndVerify(String token) {
        String publicKey = this.getPublicKey();
        return JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
    }

    /**
     * 获取公钥
     *
     * @return 公钥字符串
     */
    private String getPublicKey() {
        try {
            return cache.get("publicKey");
        } catch (ExecutionException e) {
            throw new AuthClientException("获取auth server 公钥失败", e);
        }
    }

    /**
     * 缓存公钥，一小时刷新一次
     */
    private LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(2)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                    String s = authProvider.publicTokenKey();
                    Map<String, String> stringStringMap = jsonUtil.jsonToMap(s, String.class, String.class);
                    return stringStringMap.get("value");
                }
            });

}
