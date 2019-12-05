package com.github.canglan.cm.gate.gateway.properties;

import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author bairitan
 * @date 2019/12/4
 */
@Configuration
@ConfigurationProperties("gate.authentication")
public class AuthProperties {

  private String ignoreUrls = "/oauth";

  /**
   * 是否是忽略验证路径
   */
  public boolean ignoreAuthentication(String url) {
    return Stream.of(this.ignoreUrls.split(",")).anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
  }
}
