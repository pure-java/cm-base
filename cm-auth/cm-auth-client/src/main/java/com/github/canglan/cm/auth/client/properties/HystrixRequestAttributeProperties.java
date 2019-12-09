package com.github.canglan.cm.auth.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Reno Chou
 */
@ConfigurationProperties("hystrix.propagate.request-attribute")
public class HystrixRequestAttributeProperties {

  /**
   * Enable Hystrix propagate http request and response. Defaults to false.
   */
  private boolean enabled = false;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}