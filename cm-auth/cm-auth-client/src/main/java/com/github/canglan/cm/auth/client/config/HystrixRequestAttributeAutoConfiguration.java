package com.github.canglan.cm.auth.client.config;

/**
 * @author Reno Chou
 */
// @Configuration
// @ConditionalOnClass({Hystrix.class})
// @ConditionalOnProperty(value = "hystrix.propagate.request-attribute.enabled", matchIfMissing = false)
// @EnableConfigurationProperties(HystrixRequestAttributeProperties.class)
public class HystrixRequestAttributeAutoConfiguration {

  // @Bean
  // public RequestAttributeHystrixConcurrencyStrategy hystrixRequestAutoConfiguration() {
  //   return new RequestAttributeHystrixConcurrencyStrategy();
  // }
}