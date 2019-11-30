package com.github.canglan.cm.auth.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用户权限认证配置
 *
 * @author bairitan
 * @since 2019/11/20
 */
@Data
@ConfigurationProperties(prefix = "auth.user")
public class UserAuthProperties {

  private String tokenHeader;
  public static String serviceName = "";
}
