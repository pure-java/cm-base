package com.github.canglan.cm.common.model;

import java.util.HashSet;
import java.util.Set;

/**
 * 用于配置model通用接口
 *
 * @author bairitan
 */
public interface IModel {

  /**
   * 转换为json时保留的字段
   *
   * @return json序列化时，需要序列化的字段
   */
  default public Set<String> jsonRetain() {
    return new HashSet<String>();
  }
}
