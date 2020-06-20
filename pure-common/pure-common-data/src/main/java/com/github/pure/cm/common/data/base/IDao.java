package com.github.pure.cm.common.data.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 陈欢
 * @since 2019/11/28
 */
public interface IDao<M, T> extends IService<T> {

  /**
   * 设置操作表的实体类
   *
   * @param tableClass 实体类类型
   */
  void setTableClass(Class<T> tableClass);

  /**
   * 设置mapper
   *
   * @param baseMapper 操作实体类的mapper
   */
  void setBaseMapper(M baseMapper);
}
