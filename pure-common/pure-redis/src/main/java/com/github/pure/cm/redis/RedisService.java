package com.github.pure.cm.redis;

import com.github.pure.cm.common.core.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <pre>
 *
 * </pre>
 *
 * @since : 陈欢 2020-08-14 17:02
 **/
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * 是否包含key
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * redis加锁
     *
     * @param key          锁名称
     * @param milliseconds 加锁时间：毫秒数
     * @return 加锁ID: lock id
     */
    public String lock(String key, Long milliseconds) {
        long id = IdWorker.getId();
        return this.redisTemplate.execute((RedisCallback<String>) connection -> {
            Boolean set = connection.set(key.getBytes(StandardCharsets.UTF_8),
                    String.valueOf(id).getBytes(StandardCharsets.UTF_8),
                    Expiration.milliseconds(milliseconds),
                    RedisStringCommands.SetOption.SET_IF_ABSENT
            );

            return Objects.nonNull(set) && set ? String.valueOf(id) : null;
        });
    }

    /**
     * 是否包含key
     */
    public Boolean hasKey(String nameSpace, String key) {
        return redisTemplate.hasKey(getKey(nameSpace, key));
    }

    private String getKey(String nameSpace, String key) {
        return StringUtils.isEmpty(nameSpace) ? key : nameSpace + key;
    }
}
