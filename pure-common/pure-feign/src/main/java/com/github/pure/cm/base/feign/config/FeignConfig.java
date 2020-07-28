package com.github.pure.cm.base.feign.config;

import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置 feign 消息转换器；因为当应用为 webflux 时，不会进行自动配置
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

    //@Bean
    //public Decoder feignDecoder() {
    //    // 设置编码器
    //    return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    //}

    @Bean
    public HttpMessageConverters feignHttpMessageConverter() {
        return new HttpMessageConverters(new JsonJackson2HttpMessageConverter());
    }

    public static class JsonJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        JsonJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            // 设置数据类型

            mediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE)); //关键
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
