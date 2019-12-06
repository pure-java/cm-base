package com.github.canglan.cm.auth.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bairitan
 * @since 2019/11/14
 */
@Setter
@Getter
// @ConfigurationProperties(prefix = "auth.resource")
public class AuthResourceProperties {

  /**
   * 客户端ID（资源ID，不填写则是项目名称）
   */
  @Value("${id:spring.application.name}")
  private String resourceId;

  private String secret;

  private String tokenHeader;
}
