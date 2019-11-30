package com.github.canglan.cm.auth.server.config;

import feign.Logger;
import feign.codec.Decoder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 自定义 feign 配置，暂时不需要
 *
 * @author 陈欢
 * @since 2019/11/29
 */
// @SpringBootConfiguration
public class FeignByJacksonConfig {

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
    return new ObjectFactory<HttpMessageConverters>() {
      @Override
      public HttpMessageConverters getObject() throws BeansException {
        return httpMessageConverters;
      }
    };
  }

  public class JsonJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    JsonJackson2HttpMessageConverter() {
      List<MediaType> mediaTypes = new ArrayList<>();
      // 设置数据类型
      mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")); //关键
      setSupportedMediaTypes(mediaTypes);
    }
  }
}
