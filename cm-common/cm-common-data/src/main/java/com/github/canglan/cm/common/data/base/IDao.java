package com.github.canglan.cm.common.data.base;
    import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 陈欢
 * @since 2019/11/28
 */
public interface IDao<M, T> extends IService<T> {

  /**
   * 设置操作表的实体类
   */
  public void setTableClass(Class<T> tableClass);

  /**
   * 设置mapper
   */
  public void setBaseMapper(M baseMapper);
}
