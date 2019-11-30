package com.github.canglan.cm.generator;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public class PackageConfig extends com.baomidou.mybatisplus.generator.config.PackageConfig {

  /**
   * controller 请求的前缀
   */
  private String requestMapperPrefix;

  public String getRequestMapperPrefix() {
    return requestMapperPrefix;
  }

  public PackageConfig setRequestMapperPrefix(String requestMapperPrefix) {
    this.requestMapperPrefix = requestMapperPrefix;
    return this;
  }
}
