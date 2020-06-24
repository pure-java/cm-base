package com.github.pure.cm.auth.server.auth;

import com.github.pure.cm.common.core.util.encry.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author bairitan
 * @since 2019/11/18
 */
@Configuration
@RefreshScope
public class RsaConfig {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${custom.config.auth-server.jwt-token-signer.public-key-redis}")
    private String publicKeyRedis;
    @Value("${custom.config.auth-server.jwt-token-signer.private-key-redis}")
    private String privateKeyRedis;

    public RsaUtil.RsaKey getRsaKey() {
        System.out.println(this.publicKeyRedis);
        System.out.println(this.privateKeyRedis);
        if (redisTemplate.hasKey(publicKeyRedis) && redisTemplate.hasKey(privateKeyRedis)) {
            String privateKey = redisTemplate.opsForValue().get(publicKeyRedis);
            String publicKey = redisTemplate.opsForValue().get(privateKeyRedis);
            RsaUtil.RsaKey rsaKey = new RsaUtil.RsaKey();
            rsaKey.setPrivateKey(RsaUtil.getPrivateKey(RsaUtil.decodeBase64(privateKey)));
            rsaKey.setPublicKey(RsaUtil.getPublicKey(RsaUtil.decodeBase64(publicKey)));
            return rsaKey;
        } else {
            RsaUtil.RsaKey key = RsaUtil.getKey(1024);
            redisTemplate.opsForValue().set(publicKeyRedis, RsaUtil.encodeBase64(key.getPrivateKey()));
            redisTemplate.opsForValue().set(privateKeyRedis, RsaUtil.encodeBase64(key.getPublicKey()));
            return key;
        }
    }

}
