package com.github.canglan.cm.common.util.bean;


import com.github.canglan.cm.common.util.basic.DataUtil;
import com.github.canglan.cm.common.util.collection.CollectionUtil;
import com.github.canglan.cm.common.util.collection.MapUtil;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.cglib.beans.BeanMap;

/**
 * @author bairitan
 * @since 2018-01-01 0:01
 **/

public class BeanUtils {

  /**
   * 复制bean属性信息
   *
   * @param srcBean 源
   * @param targetBean 目标
   */
  public static <T> void copyProperties(T srcBean, T targetBean) {
    org.springframework.beans.BeanUtils.copyProperties(srcBean, targetBean);
  }

  /**
   * 复制bean属性信息
   *
   * @param srcBean 源
   * @return 复制的bean
   */
  public static <T> T copyProperties(T srcBean) {
    try {
      Object s = srcBean.getClass().newInstance();
      org.springframework.beans.BeanUtils.copyProperties(srcBean, s);
      return (T) s;
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将集合bean转换为集合map
   *
   * @param tCollection 进行转换集合bean
   * @param <T> 数据类型
   * @return list map
   */
  public static <T> List<Map<String, Object>> beanToMap(Collection<T> tCollection) {
    if (CollectionUtil.isEmpty(tCollection)) {
      return Lists.newArrayList();
    }
    List<Map<String, Object>> resultList = Lists.newArrayListWithCapacity(tCollection.size());
    for (T t : tCollection) {
      resultList.add(beanToMap(t));
    }
    return resultList;
  }

  /**
   * 将对象装换为map
   */
  public static <T> Map<String, Object> beanToMap(T bean) {
    Map<String, Object> map = MapUtil.newHashMap();
    if (bean != null) {
      BeanMap beanMap = BeanMap.create(bean);
      for (Object key : beanMap.keySet()) {
        map.put(DataUtil.toStr(key), beanMap.get(key));
      }
    }
    return map;
  }


  /**
   * 将map装换为javabean对象
   */
  public static <T> T mapToBean(Map<String, Object> map, T bean) {
    BeanMap beanMap = BeanMap.create(bean);
    beanMap.putAll(map);
    return bean;
  }

  /**
   * 将map装换为javabean对象
   */
  public static <T> T mapToBean(Map<String, Object> map, Class<T> tClass) throws Exception {
    T bean1 = tClass.newInstance();
    BeanMap beanMap = BeanMap.create(bean1);
    beanMap.putAll(map);
    return bean1;
  }

  /**
   * 将List<T>转换为List<Map<String, Object>>
   */
  public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
    List<Map<String, Object>> list = Lists.newArrayList();
    if (objList != null && objList.size() > 0) {
      Map<String, Object> map = null;
      T bean = null;
      for (int i = 0, size = objList.size(); i < size; i++) {
        bean = objList.get(i);
        map = beanToMap(bean);
        list.add(map);
      }
    }
    return list;
  }

  /**
   * 将List<Map<String,Object>>转换为List<T>
   */
  public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
      throws InstantiationException, IllegalAccessException {
    if (CollectionUtil.isEmpty(maps)) {
      return Lists.newArrayList();
    }
    List<T> list = Lists.newArrayList();
    for (Map<String, Object> map1 : maps) {
      T bean = clazz.newInstance();
      mapToBean(map1, bean);
      list.add(bean);
    }
    return list;
  }

}
