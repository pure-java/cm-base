package com.github.pure.cm.auth.server.headler.oauth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author : 陈欢
 * @date : 2020-07-27 16:22
 **/
@Configuration
public class ErrorMvcAutoConfig {

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    public BasicErrorController basicErrorController(ErrorAttributes errorAttributes,
                                                     ObjectProvider<ErrorViewResolver> errorViewResolvers) {
        return new CustomBasicErrorController(errorAttributes, this.serverProperties.getError(),
                errorViewResolvers.orderedStream().collect(Collectors.toList()));
    }
}
