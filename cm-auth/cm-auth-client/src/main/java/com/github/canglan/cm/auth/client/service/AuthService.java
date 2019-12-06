package com.github.canglan.cm.auth.client.service;

import com.github.canglan.cm.auth.client.exception.AuthClientException;
import com.github.canglan.cm.auth.client.feign.AuthProvider;
import com.github.canglan.cm.auth.common.RefreshToken;
import com.github.canglan.cm.common.core.util.JacksonUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Service;

/**
 * @author bairitan
 * @since 2019/11/18
 */
@Service
@Slf4j
public class AuthService {

  private final JacksonUtil jacksonUtil = JacksonUtil.newInstance();
  @Autowired
  private AuthProvider authProvider;
  private LoadingCache<String, String> cache = CacheBuilder.newBuilder()
      .maximumSize(1000)
      .expireAfterWrite(60, TimeUnit.MINUTES)
      .build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
          String s = authProvider.publicTokenKey();
          Map<String, String> stringStringMap = jacksonUtil.jsonToMap(s, String.class, String.class);
          return stringStringMap.get("value");
        }
      });

  /**
   * 获取公钥
   */
  private String getPublicKey() {
    try {
      return cache.get("publicKey");
    } catch (ExecutionException e) {
      throw new AuthClientException("获取auth server 公钥失败", e);
    }
  }

  public Map<String, Object> checkToken(String token) {
    Map<String, Object> map = Maps.newHashMap();
    map.put("token", token);
    // JwtHelper.
    Map<String, Object> userDetails = authProvider.checkToken(map);
    log.debug(" checkToken = {}", userDetails);
    return userDetails;
  }

  public Map<String, Object> refreshToken(RefreshToken refreshToken) {
    Map<String, Object> objectMap = JacksonUtil.newInstance().jsonToMap(JacksonUtil.json(refreshToken));
    return authProvider.checkToken(objectMap);
  }

  public Jwt decodeAndVerify(String token) {
    String publicKey = this.getPublicKey();
    log.debug("public Key = {}", publicKey);
    return JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
  }
}
