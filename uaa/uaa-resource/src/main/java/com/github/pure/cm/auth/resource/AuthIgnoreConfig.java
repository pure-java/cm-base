package com.github.pure.cm.auth.resource;

import com.github.pure.cm.auth.resource.support.AuthIgnoreHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈欢
 * @since 2020/7/9
 */
@Configuration
public class AuthIgnoreConfig {

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public AuthIgnoreHandler webIgnoreHandler() {
        return new AuthIgnoreHandler.WebAuthIgnoreHandler();
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public AuthIgnoreHandler webFluxIgnoreHandler() {
        return new AuthIgnoreHandler.WebFluxAuthIgnoreHandler();
    }
}
