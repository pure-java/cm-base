package com.github.pure.cm.auth.server.config.auth;

import static com.github.pure.cm.auth.server.config.auth.RsaUtil.*;

import com.github.pure.cm.auth.server.config.auth.RsaUtil.RsaKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author bairitan
 * @since 2019/11/18
 */
@Configuration
@RefreshScope
//@ConfigurationProperties(prefix = "")
public class RsaConfig {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${custom.config.auth-server.jwt-token-signer.public-key-redis}")
    private String publicKeyRedis;
    @Value("${custom.config.auth-server.jwt-token-signer.private-key-redis}")
    private String privateKeyRedis;

    public RsaKey getRsaKey() {
        System.out.println(this.publicKeyRedis);
        System.out.println(this.privateKeyRedis);
        if (redisTemplate.hasKey(publicKeyRedis) && redisTemplate.hasKey(privateKeyRedis)) {
            String privateKey = redisTemplate.opsForValue().get(publicKeyRedis);
            String publicKey = redisTemplate.opsForValue().get(privateKeyRedis);
            RsaKey rsaKey = new RsaKey();
            rsaKey.setPrivateKey(getPrivateKey(decodeBase64(privateKey)));
            rsaKey.setPublicKey(getPublicKey(decodeBase64(publicKey)));
            return rsaKey;
        } else {
            RsaKey key = getKey(1024);
            redisTemplate.opsForValue().set(publicKeyRedis, encodeBase64(key.getPrivateKey()));
            redisTemplate.opsForValue().set(privateKeyRedis, encodeBase64(key.getPublicKey()));
            return key;
        }
    }

}
