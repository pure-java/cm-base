package com.github.canglan.cm.common.data.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 数据库操作辅助类，提供了对实体的基础增删改查功能<br>
 *
 * 如果使用该辅助类，出现未成功修改数据库数据，可能是该辅助类方法未被事务管理
 *
 * @author 陈欢
 * @since 2019/11/28
 */
public class DaoImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IDao<M, T> {

  /**
   * 实体类类名称
   */
  private Class<T> tableClass;

  @Override
  public void setTableClass(Class<T> tableClass) {
    this.tableClass = tableClass;
  }

  @Override
  protected Class<T> currentModelClass() {
    return tableClass;
  }

  @Override
  public M getBaseMapper() {
    return this.baseMapper;
  }

  @Override
  public void setBaseMapper(M baseMapper) {
    super.baseMapper = baseMapper;
  }
}
