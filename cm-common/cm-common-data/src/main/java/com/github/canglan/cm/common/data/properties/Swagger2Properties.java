package com.github.canglan.cm.common.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 陈欢
 * @since 2019/11/21
 */
@ConfigurationProperties("swagger2")
public class Swagger2Properties {

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

  }
}
