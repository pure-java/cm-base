package com.github.canglan.cm.common.util.collection;


import java.util.Collection;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 集合工厂
 *
 * @author chenhuan
 * @since 2017-07-06 10:00
 **/

public class CollectionUtil {


  private CollectionUtil() {
  }

  // empty =====================================

  /**
   * 集合为null或没有元素
   *
   * @param collection 集合
   * @return 判断结果
   */
  public static boolean isEmpty(Collection collection) {
    return CollectionUtils.isEmpty(collection);
  }

  /**
   * 集合不为空且集合存在元素，返回true
   * 如果集合为null或没有元素，返回false
   *
   * @param collection 集合
   * @return 判断结果
   */
  public static boolean isNotEmpty(Collection collection) {
    return CollectionUtils.isNotEmpty(collection);
  }


}