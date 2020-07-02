package com.github.pure.cm.common.core.config.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * spring redis 缓存管理器，用于配置spring缓存相关数据，只有开启了缓存功能 @EnableCaching ，该配置才会生效
 *
 * @author 陈欢
 * @see EnableCaching
 * @since 2020/7/1
 */
@Slf4j
@Configuration
public class SpringRedisCacheManager extends CachingConfigurerSupport {

    /**
     * 缓存前缀：项目名称为spring cache 的统一前缀<br>
     * cache:${spring.application.name}:${cacheKey}
     */
    @Value("cache:${spring.application.name}:")
    private String applicationName;

    /**
     * 配置spring cache 使用 json 序列化数据
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        //
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 保存类型的名称，不进行设置，将会导致不能反序列化
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .entryTtl(Duration.ofDays(1))
                .computePrefixWith(cacheName -> applicationName + cacheName + ":");

        return configuration;
    }
}
