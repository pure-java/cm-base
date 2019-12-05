package com.github.canglan.cm.common.core.util;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组辅助类，继承 {@link ArrayUtils}
 *
 * @author
 */
public class ArrayUtil extends ArrayUtils {

  /**
   * 从 <code>dataArr</code> 数组中查找 <code>valueArr</code> 里面任意一个值
   *
   * @param dataArr 目标数组
   * @param valueArr 需要查找的数组
   * @param <T> 类型
   * @return 查找到的下标，如果未查到，则返回-1
   */
  public static <T> int indexOfAny(T[] dataArr, T[] valueArr) {
    return indexOfAny(dataArr, valueArr, 0);
  }

  /**
   * 从 <code>dataArr</code> 数组中查找 <code>valueArr</code> 里面任意一个值
   *
   * @param dataArr 目标数组
   * @param valueArr 需要查找的数组
   * @param startIndex 开始查找的索引位置
   * @param <T> 类型
   * @return 查找到的下标，如果未查到，则返回-1
   */
  public static <T> int indexOfAny(T[] dataArr, T[] valueArr, int startIndex) {
    for (int i = startIndex; i < dataArr.length; i++) {
      for (int j = 0; j < valueArr.length; j++) {
        if (dataArr[i] != null && dataArr[i].equals(valueArr[j])) {
          return i;
        }
      }
    }
    return -1;
  }


  /**
   * 反向从 <code>dataArr</code> 数组中查找 <code>valueArr</code> 里面任意一个值
   *
   * @param dataArr 目标数组
   * @param valueArr 需要查找的数组
   * @param <T> 类型
   * @return 查找到的下标，如果未查到，则返回-1
   */
  public static <T> int lastIndexOfAny(T[] dataArr, T[] valueArr) {
    return lastIndexOfAny(dataArr, valueArr, dataArr.length - 1);
  }

  /**
   * 反向从 <code>dataArr</code> 数组中查找 <code>valueArr</code> 里面任意一个值
   *
   * @param dataArr 目标数组
   * @param valueArr 需要查找的数组
   * @param endIndex 结束索引
   * @param <T> 类型
   * @return 查找到的下标，如果未查到，则返回-1
   */
  public static <T> int lastIndexOfAny(T[] dataArr, T[] valueArr, int endIndex) {
    for (int i = endIndex; i >= 0; i--) {
      for (int j = 0; j < valueArr.length; j++) {
        if (dataArr[i] != null && dataArr[i].equals(valueArr[j])) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * 合并数组
   *
   * @param dataArr 数组
   */
  public static <T> T[] mergeArrays(T[]... dataArr) {
    if (ArrayUtil.isNotEmpty(dataArr)) {
      List<T> res = Lists.newArrayList();
      for (T[] ts : dataArr) {
        Collections.addAll(res, ts);
      }
      return res.toArray(dataArr[0]);
    }

    return null;
  }

}
