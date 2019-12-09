package com.github.canglan.cm.auth.client.config;

import com.github.canglan.cm.auth.client.concurrency.RequestAttributeHystrixConcurrencyStrategy;
import com.github.canglan.cm.auth.client.properties.HystrixRequestAttributeProperties;
import com.netflix.hystrix.Hystrix;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Reno Chou
 */
@Configuration
@ConditionalOnClass({Hystrix.class})
@ConditionalOnProperty(value = "hystrix.propagate.request-attribute.enabled", matchIfMissing = false)
@EnableConfigurationProperties(HystrixRequestAttributeProperties.class)
public class HystrixRequestAttributeAutoConfiguration {
  @Bean
  public RequestAttributeHystrixConcurrencyStrategy hystrixRequestAutoConfiguration() {
    return new RequestAttributeHystrixConcurrencyStrategy();
  }
}