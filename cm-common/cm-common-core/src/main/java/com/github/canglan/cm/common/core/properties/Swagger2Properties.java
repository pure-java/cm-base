package com.github.canglan.cm.common.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈欢
 * @since 2019/11/21
 */
@ConfigurationProperties(prefix = "swagger2")
@Data
@Configuration
public class Swagger2Properties {

  private boolean enable;

  private Swagger2ApiInfoBuilder info;

  public Swagger2ApiInfoBuilder getInfo() {
    return info;
  }

  public void setInfo(Swagger2ApiInfoBuilder info) {
    this.info = info;
  }

  @Data
  public static class Swagger2ApiInfoBuilder {

    private String title;
    private String desc;
    private String url;
    private String version;
    private String basePackage;

  }

}
