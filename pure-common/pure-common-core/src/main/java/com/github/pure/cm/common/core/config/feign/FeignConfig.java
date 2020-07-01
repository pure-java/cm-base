package com.github.pure.cm.common.core.config.feign;

import feign.Logger;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置 feign 消息转换器；当应用为 webflux 时，不会进行自动配置
 *
 * @author 陈欢
 * @since 2020/6/29
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Decoder feignDecoder() {
        // 设置编码器
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }


    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new JsonJackson2HttpMessageConverter());
        return () -> httpMessageConverters;
    }

    public static class JsonJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        JsonJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            // 设置数据类型
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")); //关键
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
