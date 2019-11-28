package com.github.canglan.cm.common.util.basic;

import com.github.canglan.cm.common.util.StringUtil;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * 数据转换辅助类
 *
 * @author bairitan
 * @since 2017-09-24 10:35
 **/
public class DataUtil {

  /**
   * 获取数组的第几个对象
   *
   * @param t 数组
   * @param index 要进行获取的索引
   * @param defaultVal 索引处值为null，返回的值
   * @param <T> 类型
   * @return 如果 arr[index] == null 返回 defaultVal
   */
  public static <T> T getArrayToString(T[] t, int index, T defaultVal) {
    return t.length > index ? t[index] : defaultVal;
  }

  /**
   * 将集合转为字符串，如果集合为空，转为空字符串
   *
   * @param collection 集合
   * @return 转换后的字符串，如果集合为空，则转换为空串
   */
  public static <T> String toStr(Collection<T> collection) {
    return toStr(collection, "");
  }

  /**
   * 将对象转换为string
   *
   * @param data 要进行转换的对象
   * @return 如果data == null。返回空串
   */
  public static String toStr(Object data) {
    return toStr(data, "");
  }

  /**
   * 将对象转换为string
   *
   * @param data 要进行转换的对象
   * @param defaultVal 默认值
   * @return 如果data == null。返回defaultVal
   */
  public static String toStr(Object data, String defaultVal) {
    if (Objects.isNull(data)) {
      return defaultVal;
    }
    return String.valueOf(data);
  }


  /**
   * 将集合转为字符串,并把[]去掉，如果集合为空，转为空字符串
   *
   * @param collection 集合
   * @return 转换后的字符串，如，则转换为空串
   */
  public static <T> String strReplace(Collection<T> collection) {
    return toStr(collection, "").replaceAll("[\\]|\\[]*" , "");
  }


  public static Integer toInteger(Object o) {
    return toInteger(o, null);
  }

  public static Integer toInteger(Object o, Integer defaultVal) {
    if (Objects.nonNull(o) && StringUtil.isNotBlank(o.toString()) && MathUtil.isNumber(o)) {
      return Integer.valueOf(o.toString());
    }
    return defaultVal;
  }

  public static Long toLong(Object o) {
    return toLong(o, null);
  }

  public static Long toLong(Object o, Long defaultVal) {
    if (Objects.nonNull(o) && StringUtil.isNotBlank(o.toString()) && MathUtil.isNumber(o)) {
      return Long.valueOf(o.toString());
    }
    return defaultVal;
  }

  /**
   * 将数据转换为Boolean， o != null ? true : false
   *
   * @param o 要进行转换的值
   * @return o != null ? true : false
   */
  public static boolean toBoolean(Object o) {
    return toBoolean(o, false);
  }

  /**
   * 将对象转换为Boolean
   *
   * @param o 要进行转换的对象
   * @param defaultVal 为null 时的默认值
   * @return 返回转换结果，如果为null，则返回defaultVal
   */
  public static boolean toBoolean(Object o, Boolean defaultVal) {
    if (Objects.nonNull(o) && StringUtil.isNotBlank(o.toString())) {
      return Boolean.valueOf(o.toString());
    }
    return defaultVal;
  }

  public static Double toDouble(Object o) {
    return toDouble(o, 0.0);
  }

  public static Double toDouble(Object o, Double defaultVal) {
    if (Objects.nonNull(o) && StringUtil.isNotBlank(o.toString()) && MathUtil.isNumber(o)) {
      return Double.valueOf(o.toString());
    }
    return defaultVal;
  }

  public static Integer booleanToInteger(Boolean b) {
    return booleanToInteger(b, 0);
  }

  public static Integer booleanToInteger(Boolean b, Integer defVal) {
    if (b == null) {
      return defVal;
    }
    if (b) {
      return 1;
    }
    return 0;
  }

  public static Boolean integerToBoolean(Integer b) {
    return integerToBoolean(b, false);
  }

  public static Boolean integerToBoolean(Integer b, Boolean defVal) {
    if (b == null) {
      return defVal;
    }
    if (b == 0) {
      return false;
    }
    return true;
  }

  /**
   * 保留两位小数 o-必须是数字类型，否则抛出运行时异常
   */
  public static double round2(Object o) {
    if (MathUtil.isNumber(o)) {
      BigDecimal bigDecimal = new BigDecimal(o.toString());
      return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    throw new RuntimeException("o is not number, value = " + Objects.toString(o));
  }

}
