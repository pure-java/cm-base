package com.github.canglan.cm.auth.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bairitan
 * @since 2019/11/20
 */

@ConfigurationProperties(prefix = "auth.client")
@Data
public class ClientAuthProperties {

  private String tokenHeader;
}
