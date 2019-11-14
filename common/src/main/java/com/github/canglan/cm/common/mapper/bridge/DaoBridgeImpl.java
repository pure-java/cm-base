package com.github.canglan.cm.common.mapper.bridge;

import ch.qos.logback.core.util.CloseUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.canglan.cm.common.mapper.IDaoBridge;
import com.github.canglan.cm.common.mapper.BaseMapper;
import com.github.canglan.cm.common.util.collection.CollectionUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * service是适配器，提供基本功能
 * <p>
 * 该类中的方法都是进行入参判断，需要添加方法也请检查入参是否满足条件 <br>
 *
 * 如果入参的对象为空或集合为空，则直接返回false或空内容结果
 * </p>
 *
 * @author 陈欢
 * @date 2018-06-07 18:50
 **/
public class DaoBridgeImpl<T, M extends BaseMapper<T>> implements
    IDaoBridge<T, M> {

  protected Log log = LogFactory.getLog(getClass());

  /**
   * 实体类类名称
   */
  private Class<T> tableClass;

  public DaoBridgeImpl(Class<T> tableClass) {
    this.tableClass = tableClass;
  }

  @Override
  public void setSupperMapper(M supperMapper) {
    this.baseMapper = supperMapper;
  }

  // =================复制开始===============

  protected M baseMapper;


  @Override
  public M getBaseMapper() {
    return baseMapper;
  }

  /**
   * 判断数据库操作是否成功
   *
   * @param result 数据库操作返回影响条数
   * @return boolean
   */
  protected boolean retBool(Integer result) {
    return SqlHelper.retBool(result);
  }

  protected Class<T> currentModelClass() {
    return this.tableClass;
  }

  /**
   * 批量操作 SqlSession
   */
  protected SqlSession sqlSessionBatch() {
    return SqlHelper.sqlSessionBatch(currentModelClass());
  }

  /**
   * 释放sqlSession
   *
   * @param sqlSession session
   */
  protected void closeSqlSession(SqlSession sqlSession) {
    SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
  }

  /**
   * 获取 SqlStatement
   *
   * @param sqlMethod ignore
   * @return ignore
   */
  protected String sqlStatement(SqlMethod sqlMethod) {
    return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
  }

  @Override
  public boolean save(T entity) {
    return Objects.nonNull(entity) && retBool(baseMapper.insert(entity));
  }

  /**
   * 批量插入
   *
   * @param entityList ignore
   * @param batchSize ignore
   * @return ignore
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveBatch(Collection<T> entityList, int batchSize) {
    if (CollectionUtil.isEmpty(entityList)) {
      return false;
    }

    String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
    SqlSession batchSqlSession = null;
    try {
      batchSqlSession = sqlSessionBatch();
      int i = 0;
      for (T anEntityList : entityList) {
        batchSqlSession.insert(sqlStatement, anEntityList);
        if (i >= 1 && i % batchSize == 0) {
          batchSqlSession.flushStatements();
        }
        i++;
      }

      batchSqlSession.flushStatements();

      // 提交事务
      batchSqlSession.commit();
    } catch (Exception e) {
      if (batchSqlSession != null) {
        batchSqlSession.rollback();
      }
    } finally {
      CloseUtil.closeQuietly(batchSqlSession);
    }
    return true;
  }

  /**
   * TableId 注解存在更新记录，否插入一条记录
   *
   * @param entity 实体对象
   * @return boolean
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveOrUpdate(T entity) {
    if (null != entity) {
      Class<?> cls = entity.getClass();
      TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
      Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
      String keyProperty = tableInfo.getKeyProperty();
      Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
      Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
      return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity) : updateById(entity);
    }
    return false;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
    if (CollectionUtil.isEmpty(entityList)) {
      return false;
    }

    Class<?> cls = currentModelClass();
    TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
    Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
    String keyProperty = tableInfo.getKeyProperty();
    Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
    SqlSession batchSqlSession = null;
    try {
      // 获取session
      batchSqlSession = sqlSessionBatch();
      int i = 0;
      for (T entity : entityList) {
        Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
        if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
          batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
        } else {
          MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
          param.put(Constants.ENTITY, entity);
          batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
        }
        // 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
        if (i >= 1 && i % batchSize == 0) {
          batchSqlSession.flushStatements();
        }
        i++;
      }
      batchSqlSession.flushStatements();

      // 提交事务
      batchSqlSession.commit();
    } catch (Exception e) {
      if (batchSqlSession != null) {
        batchSqlSession.rollback();
      }
    } finally {
      CloseUtil.closeQuietly(batchSqlSession);
    }
    return true;
  }

  @Override
  public boolean removeById(Serializable id) {
    return SqlHelper.retBool(baseMapper.deleteById(id));
  }

  @Override
  public boolean removeByMap(Map<String, Object> columnMap) {
    Assert.notEmpty(columnMap, "error: columnMap must not be empty");
    return SqlHelper.retBool(baseMapper.deleteByMap(columnMap));
  }

  @Override
  public boolean remove(Wrapper<T> wrapper) {
    return SqlHelper.retBool(baseMapper.delete(wrapper));
  }

  @Override
  public boolean removeByIds(Collection<? extends Serializable> idList) {
    return SqlHelper.retBool(baseMapper.deleteBatchIds(idList));
  }

  @Override
  public boolean updateById(T entity) {
    return retBool(baseMapper.updateById(entity));
  }

  @Override
  public boolean update(T entity, Wrapper<T> updateWrapper) {
    return retBool(baseMapper.update(entity, updateWrapper));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean updateBatchById(Collection<T> entityList, int batchSize) {
    if (CollectionUtil.isEmpty(entityList)) {
      return false;
    }

    String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
    SqlSession batchSqlSession = null;
    try {
      // 获取session
      batchSqlSession = sqlSessionBatch();
      int i = 0;
      for (T anEntityList : entityList) {
        MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
        param.put(Constants.ENTITY, anEntityList);
        batchSqlSession.update(sqlStatement, param);
        if (i >= 1 && i % batchSize == 0) {
          batchSqlSession.flushStatements();
        }
        i++;
      }
      batchSqlSession.flushStatements();
      // 提交事务
      batchSqlSession.commit();
    } catch (Exception e) {
      if (batchSqlSession != null) {
        batchSqlSession.rollback();
      }
    } finally {
      CloseUtil.closeQuietly(batchSqlSession);
    }
    return true;
  }

  @Override
  public T getById(Serializable id) {
    return baseMapper.selectById(id);
  }

  @Override
  public Collection<T> listByIds(Collection<? extends Serializable> idList) {
    return baseMapper.selectBatchIds(idList);
  }

  @Override
  public Collection<T> listByMap(Map<String, Object> columnMap) {
    return baseMapper.selectByMap(columnMap);
  }

  @Override
  public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
    if (throwEx) {
      return baseMapper.selectOne(queryWrapper);
    }
    return SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
  }

  @Override
  public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
    return SqlHelper.getObject(log, baseMapper.selectMaps(queryWrapper));
  }

  @Override
  public int count(Wrapper<T> queryWrapper) {
    return SqlHelper.retCount(baseMapper.selectCount(queryWrapper));
  }

  @Override
  public List<T> list(Wrapper<T> queryWrapper) {
    return baseMapper.selectList(queryWrapper);
  }

  @Override
  public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
    return baseMapper.selectPage(page, queryWrapper);
  }

  @Override
  public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
    return baseMapper.selectMaps(queryWrapper);
  }

  @Override
  public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
    return baseMapper.selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
  }

  @Override
  public IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
    return baseMapper.selectMapsPage(page, queryWrapper);
  }

  @Override
  public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
    return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
  }

}