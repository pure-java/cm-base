package com.github.pure.cm.common.data.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * controller的基础类；
 * S : service类型
 * T : model 类型
 * FK ： 主键类型
 *
 * @author bairitan
 * @date 2018-02-01 11:12
 **/
@Slf4j
public abstract class BaseController<S extends IBaseService> {

  /**
   * 服务
   */
  @Autowired
  protected S service;
}
