package com.github.pure.cm.auth.server.auth.config;

import com.github.pure.cm.auth.server.headler.OauthWebResponseExceptionTranslator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author 陈欢
 * @since 2020/6/28
 */
@Configuration
public class AuthBeanConfig {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 密码加解密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * redis
     */
    @Bean
    public RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(this.redisConnectionFactory);
    }

    /**
     * 对 oauth 错误信息进行转换
     */
    @Bean
    public OauthWebResponseExceptionTranslator oauthWebResponseExceptionTranslator() {
        return new OauthWebResponseExceptionTranslator();
    }

}
