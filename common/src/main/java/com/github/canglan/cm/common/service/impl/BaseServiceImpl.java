package com.github.canglan.cm.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.github.canglan.cm.common.entity.BaseDom;
import com.github.canglan.cm.common.mapper.IDaoBridge;
import com.github.canglan.cm.common.mapper.BaseMapper;
import com.github.canglan.cm.common.mapper.bridge.DaoBridgeImpl;
import com.github.canglan.cm.common.service.IBaseService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 统一服务类超类，提供基本的数据库交互接口
 * <br>
 * 该类提供了一个基本的增删查改基础对象 daoBridge;
 *
 * 该类的 {@link #daoBridge}是通过创建实例，然后把 baseMapper 提供给 adapter
 *
 * @author 陈欢
 * @create 2017-11-05 17:08
 **/
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDom> implements IBaseService<T>, InitializingBean {

  @Autowired
  protected M baseMapper;
  protected IDaoBridge<T, M> daoBridge;

  public BaseServiceImpl() {
    // 使用该方法原因是，在批量修改添加时，mybatis-plus 提供的批量操作方法不能被spring事务管理，
    // 解决方法 1. 通过在下面创建ServiceImpl对象，然后把其加入spring事务管理中，
    // 2. 复制代码，在复制代码中添加事务提交代码

    // 获取 实体类 的类名称
    Class<T> tableClass = (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);

    daoBridge = new DaoBridgeImpl<T, M>(tableClass);
  }

  @Override
  public void afterPropertiesSet() {
    daoBridge.setSupperMapper(baseMapper);
  }

}
