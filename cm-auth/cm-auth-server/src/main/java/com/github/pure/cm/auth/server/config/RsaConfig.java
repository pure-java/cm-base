package com.github.pure.cm.auth.server.config;

import static com.github.pure.cm.auth.common.RsaUtil.*;

import com.github.pure.cm.auth.common.RsaUtil.RsaKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author bairitan
 * @since 2019/11/18
 */
@Configuration
public class RsaConfig {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  private final static String REDIS_SERVER_PRIVATE_KEY = "ME:AUTH:SERVER:PRIVATE:KEY";
  private final static String REDIS_SERVER_PUBLIC_KEY = "ME:AUTH:SERVER:PUBLIC:KEY";

  public RsaKey getRsaKey() {
    if (redisTemplate.hasKey(REDIS_SERVER_PRIVATE_KEY) && redisTemplate.hasKey(REDIS_SERVER_PUBLIC_KEY)) {
      String privateKey = redisTemplate.opsForValue().get(REDIS_SERVER_PRIVATE_KEY);
      String publicKey = redisTemplate.opsForValue().get(REDIS_SERVER_PUBLIC_KEY);
      RsaKey rsaKey = new RsaKey();
      rsaKey.setPrivateKey(getPrivateKey(decodeBase64(privateKey)));
      rsaKey.setPublicKey(getPublicKey(decodeBase64(publicKey)));
      return rsaKey;
    } else {
      RsaKey key = getKey(1024);
      redisTemplate.opsForValue().set(REDIS_SERVER_PRIVATE_KEY, encodeBase64(key.getPrivateKey()));
      redisTemplate.opsForValue().set(REDIS_SERVER_PUBLIC_KEY, encodeBase64(key.getPublicKey()));
      return key;
    }
  }

}
