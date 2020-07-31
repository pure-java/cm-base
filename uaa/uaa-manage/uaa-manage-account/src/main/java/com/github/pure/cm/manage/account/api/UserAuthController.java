package com.github.pure.cm.manage.account.api;

import com.github.pure.cm.auth.sdk.service.UserAuthService;
import com.github.pure.cm.common.core.constants.DefExceptionCode;
import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.encry.RsaUtil;
import com.github.pure.cm.common.core.util.encry.RsaUtil.RsaKey;
import com.github.pure.cm.auth.sdk.properties.OAuth2ClientProperties;
import com.github.pure.cm.auth.sdk.service.AuthService;
import com.github.pure.cm.auth.sdk.vo.ReqJwtTokenParam;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * 用户验证控制类
 *
 * @author bairitan
 * @date 2019/12/9
 */
@Slf4j
@RestController
@RequestMapping("/user/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService authService;

    /**
     * 登录，暂时放在网关，可以放到其他模块，但是需要为登录模块提供客户端授权
     *
     * @param userInfo 账号
     * @return 登录结果
     */
    @PostMapping("/login")
    public Mono<String> login(@RequestBody ReqJwtTokenParam userInfo) throws BusinessException {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        try {
            //username = RsaUtil.decryptBase64(userInfo.getUsername(), getPrivateKey());
            //password = RsaUtil.decryptBase64(userInfo.getPassword(), getPrivateKey());
            return Mono.just(authService.userToken(username, password));
        } catch (Exception e) {
            log.error("获取token发生错误", e);
            throw new BusinessException(DefExceptionCode.SYSTEM_ERROR_10500);
        }
    }

    /**
     * 获取加密 用户账号密码公钥<br>缓存，并且在一定时间内进行刷新
     */
    //@PostMapping("/publicKey")
    public Mono<Result<String>> publicKey() throws ExecutionException {
        Result<String> success = Result.success();
        byte[] encode = java.util.Base64.getEncoder().encode(getPublicKey().getEncoded());
        String verifierKey = String.format("%s%s%s",
                "-----BEGIN PUBLIC KEY-----",
                new String(encode),
                "-----END PUBLIC KEY-----");
        success.setData(verifierKey);
        System.out.println(new String(java.util.Base64.getEncoder().encode(getPrivateKey().getEncoded())));
        return Mono.just(success);
    }

    /**
     * 用户登录加密 rsa 秘钥对
     */
    private static final String LOGIN_RSA_KEY = "rsa_key";
    /**
     * 过期时间为一天的缓存
     */
    LoadingCache<String, RsaKey> logRsaCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(
            new CacheLoader<String, RsaKey>() {
                @Override
                public RsaKey load(String s) throws Exception {
                    if (LOGIN_RSA_KEY.equalsIgnoreCase(s)) {
                        return RsaUtil.getKey(1024);
                    }
                    return null;
                }
            });

    private PublicKey getPublicKey() throws ExecutionException {
        return logRsaCache.get(LOGIN_RSA_KEY).getPublicKey();
    }

    @SneakyThrows
    private PrivateKey getPrivateKey() {
        return logRsaCache.get(LOGIN_RSA_KEY).getPrivateKey();
    }

}
