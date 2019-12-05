package com.github.canglan.cm.common.data.base;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 统一服务类超类，提供基本的数据库交互接口
 * <br>
 * 该类提供了一个基本的增删查改基础对象 daoUtil;
 *
 * 该类的 {@link #daoUtil}是通过创建实例，然后把 baseMapper 提供给 adapter
 *
 * @author 陈欢
 * @date 2017-11-05 17:08
 **/

public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDom> implements IBaseService<T>, InitializingBean,
    ApplicationContextAware {

  @Autowired
  protected M baseMapper;

  /**
   * 提供通用操作数据库工具
   */
  protected IDao<M, T> daoUtil;

  public BaseServiceImpl() {
    // 获取 实体类 的类名称
    Class<T> tableClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);

    daoUtil = new DaoImpl<>();
    daoUtil.setTableClass(tableClass);
  }

  @Override
  public void afterPropertiesSet() {
    daoUtil.setBaseMapper(baseMapper);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    DefaultListableBeanFactory autowireCapableBeanFactory =
        (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    autowireCapableBeanFactory.registerSingleton(getClass().getSimpleName() + "DaoUtil", daoUtil);
  }
}
