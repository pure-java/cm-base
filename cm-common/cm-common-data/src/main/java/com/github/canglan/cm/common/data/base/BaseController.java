package com.github.canglan.cm.common.data.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * controller的父类
 *
 * @author bairitan
 * @create 2018-02-01 11:12
 **/
@Slf4j
public abstract class BaseController<S extends IBaseService> {

  @Autowired
  protected S service;
}
