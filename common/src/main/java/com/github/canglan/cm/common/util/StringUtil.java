package com.github.canglan.cm.common.util;


import com.github.canglan.cm.common.util.basic.DataUtil;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串处理工具类
 *
 * @author admin
 * @date 2017-06-29 10:21
 **/
public class StringUtil {

  /**
   * 是否为空、空串、含有空格字符串{@link StringUtils#isBlank}
   *
   * @param charSequence 进行验证字符
   */
  public static boolean isBlank(Object charSequence) {
    return StringUtils.isBlank(DataUtil.toStr(charSequence, ""));
  }

  /**
   * 把前后空格删除，在判断是否为空、空串、含有空格字符串{@link StringUtils#isBlank}
   *
   * @param charSequence 进行验证字符
   */
  public static boolean isTrimBlank(Object charSequence) {
    return StringUtils.isBlank(StringUtils.trimToEmpty(DataUtil.toStr(charSequence, "")));
  }


  /**
   * 是否为空、空串、含有空格字符串{@link StringUtils#isNotBlank}
   */
  public static boolean isNotBlank(Object charSequence) {
    return !isBlank(charSequence);
  }

  /**
   * 需要不为空
   *
   * @param t 参数
   * @param <T> 类型
   * @return 不为空返回参数
   */
  public static <T> T requireNonBlank(T t) {
    return requireNonBlank(t, "参数为空或空串");
  }

  /**
   * 需要不为空
   *
   * @param t 参数
   * @param message 为空后提示
   * @param <T> 类型
   * @return 不为空返回参数
   */
  public static <T> T requireNonBlank(T t, String message) {
    if (StringUtil.isBlank(t)) {
      throw new RuntimeException(message);
    }
    return t;
  }

  /**
   * 所有的都不为空
   *
   * @return 其中一个为空，则返回false
   */
  public static boolean isAllNotBlank(Object... charSequence) {
    for (Object object : charSequence) {
      if (isBlank(object)) {
        return false;
      }
    }
    return true;
  }


  /**
   * value是否匹配正则表达式
   *
   * @param regex 正则
   * @param value 值
   * @param isTrim 是否需要trim
   */
  public static boolean regString(String regex, Object value, boolean isTrim) {
    if (isBlank(regex) || value == null) {
      return false;
    }
    String v = value.toString();
    if (isTrim) {
      v = v.trim();
    }
    return v.matches(regex);
  }

  /**
   * value是否匹配正则表达式
   */
  public static boolean regString(String regex, Object value) {
    return regString(regex, value, false);
  }

  /**
   * 将字符串转换为tClass类型的对象，只能转换基本包装类型
   */
  public static <T> T getTypeData(Class<T> tClass, String s) {
    if (tClass.isAssignableFrom(Long.class)) {
      return (T) (DataUtil.toLong(s, 0L));
    } else if (tClass.isAssignableFrom(Integer.class)) {
      return (T) (Integer.valueOf(s));
    } else if (tClass.isAssignableFrom(Short.class)) {
      return (T) (Short.valueOf(s));
    } else if (tClass.isAssignableFrom(Byte.class)) {
      return (T) (Byte.valueOf(s));
    } else if (tClass.isAssignableFrom(Character.class)) {
      return (T) (Character.valueOf(s.charAt(0)));
    } else if (tClass.isAssignableFrom(Boolean.class)) {
      return (T) (Boolean.valueOf(s));
    } else if (tClass.isAssignableFrom(Float.class)) {
      return (T) (Float.valueOf(s));
    } else if (tClass.isAssignableFrom(Double.class)) {
      return (T) (Double.valueOf(s));
    } else if (tClass.isAssignableFrom(String.class)) {
      return (T) (s);
    } else {
      throw new RuntimeException("只能转换基本类型包装类！");
    }
  }

  /**
   * 将id字符串转换为class类型的集合，使用,分隔字符
   *
   * @param oids id字符串
   * @param tClass 类型
   */
  public static <T> Set<T> convertStringToId(String oids, Class<T> tClass) {
    return convertStringToId(oids, tClass, "[,，]");
  }

  /**
   * 将id字符串转换为class类型的集合，使用【regex】分隔字符
   *
   * @param oids id字符串
   * @param tClass 类型
   * @param regex 分隔符
   */
  public static <T> Set<T> convertStringToId(String oids, Class<T> tClass, String regex) {
    if (isBlank(oids)) {
      return Collections.emptySet();
    }

    Set<T> oidList = Sets.newHashSetWithExpectedSize(20);
    String[] oidArr = oids.split(regex);
    for (String oid : oidArr) {
      if (StringUtil.isNotBlank(oid)) {
        oidList.add(getTypeData(tClass, oid));
      }
    }
    return oidList;
  }

  /**
   * 所有字符串不为空字符串
   */
  public static boolean isAllNotBlank(String[] arr) {
    if (arr == null) {
      return false;
    }
    for (String s : arr) {
      if (StringUtil.isBlank(s)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 所有的都为空字符串
   */
  public static boolean isAllBlank(String[] arr) {
    if (arr == null) {
      return true;
    }
    for (String s : arr) {
      if (StringUtil.isNotBlank(s)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 任意一个不为空字符串
   */
  public static boolean isAnyNotBlank(String[] arr) {
    if (arr == null) {
      return false;
    }
    for (String s : arr) {
      if (StringUtil.isNotBlank(s)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 任意一个为空字符串
   */
  public static boolean isAnyBlank(String[] arr) {
    if (arr == null) {
      return true;
    }
    for (String s : arr) {
      if (StringUtil.isBlank(s)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 忽略大小写查找
   *
   * @param arr 目标数组
   * @param value 要查找的值
   * @return 在数组中的位置，如果为-1，则未找到
   */
  public static int indexOfIgnoreCase(String[] arr, String value) {
    return indexOfIgnoreCase(arr, value, 0);
  }

  /**
   * 忽略大小写查找
   *
   * @param arr 目标数组
   * @param value 要查找的值
   * @param startIndex 开始查找的下标
   * @return 在数组中的位置，如果为-1，则未找到
   */
  public static int indexOfIgnoreCase(String[] arr, String value, int startIndex) {
    for (int i = startIndex; i < arr.length; i++) {
      if (arr[i] != null && arr[i].equalsIgnoreCase(value)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 返向忽略大小写查找
   *
   * @param arr 目标数组
   * @param value 要查找的值
   * @return 在数组中的位置，如果为-1，则未找到
   */
  public static int lastIndexOfIgnoreCase(String[] arr, String value) {
    return lastIndexOfIgnoreCase(arr, value, arr.length);
  }

  /**
   * 返向忽略大小写查找
   *
   * @param arr 目标数组
   * @param value 要查找的值
   * @param endIndex 开始查找的下标位置
   * @return 在数组中的位置，如果为-1，则未找到
   */
  public static int lastIndexOfIgnoreCase(String[] arr, String value, int endIndex) {
    for (int i = endIndex; i >= 0; i++) {
      if (arr[i] != null && arr[i].equalsIgnoreCase(value)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 忽略大小写从 <code>dataArr</code> 数组中查找 <code>valueArr</code> 里面任意一个值
   *
   * @param dataArr 目标数组
   * @param valueArr 需要查找的数组
   * @return 查找到的下标，如果未查到，则返回-1
   */
  public static int indexOfAnyIgnoreCase(String[] dataArr, String[] valueArr) {
    return indexOfAnyIgnoreCase(dataArr, valueArr, 0);
  }

  /**
   * 忽略大小写从 <code>dataArr</code> 数组中查找 <code>valueArr</code> 里面任意一个值
   *
   * @param arr 目标数组
   * @param valueArr 需要查找的数组
   * @param startIndex 开始查询位置
   * @return 查找到的下标，如果未查到，则返回-1
   */
  public static <T> int indexOfAnyIgnoreCase(String[] arr, String[] valueArr, int startIndex) {
    for (int i = startIndex; i < arr.length; i++) {
      for (int j = 0; j < valueArr.length; j++) {
        if (arr[i] != null && arr[i].equalsIgnoreCase(valueArr[j])) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * 忽略大小写反向从 <code>dataArr</code> 数组中查找 <code>valueArr</code> 里面任意一个值
   *
   * @param dataArr 目标数组
   * @param valueArr 需要查找的数组
   * @return 查找到的下标，如果未查到，则返回-1
   */
  public static int lastIndexOfAnyIgnoreCase(String[] dataArr, String[] valueArr) {
    return lastIndexOfAnyIgnoreCase(dataArr, valueArr, dataArr.length - 1);
  }

  /**
   * 忽略大小写反向从 <code>dataArr</code> 数组中查找 <code>valueArr</code> 里面任意一个值
   *
   * @param dataArr 目标数组
   * @param valueArr 需要查找的数组
   * @param endIndex 结束索引
   * @return 查找到的下标，如果未查到，则返回-1
   */
  public static int lastIndexOfAnyIgnoreCase(String[] dataArr, String[] valueArr, int endIndex) {
    for (int i = endIndex; i >= 0; i--) {
      for (int j = 0; j < valueArr.length; j++) {
        if (dataArr[i] != null && dataArr[i].equalsIgnoreCase(valueArr[j])) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * dataArr中 包含所有 valueArr
   *
   * @param dataArr 目标数组
   * @param valueArr 要查找的数组
   * @param regExp 是否使用正则
   * @return 是否全部包含
   */
  public static boolean containsOfAll(String[] dataArr, String[] valueArr, boolean regExp) {
    return containsOfAll(dataArr, valueArr, 0, regExp, false);
  }


  /**
   * dataArr中 包含所有 valueArr
   *
   * @param dataArr 目标数组
   * @param valueArr 要查找的数组
   * @param startIndex 开始下标
   * @param regExp 是否使用正则方式查找
   * @param sensitive 是否大小写敏感
   */
  public static boolean containsOfAll(String[] dataArr, String[] valueArr, int startIndex, boolean regExp, boolean sensitive) {
    if (ArrayUtils.isEmpty(dataArr) || ArrayUtils.isEmpty(valueArr)) {
      return false;
    }
    // 全部转换为小写
    if (!sensitive && !regExp) {
      for (int i = 0; i < dataArr.length; i++) {
        dataArr[i] = dataArr[i].toLowerCase();
      }
      for (int i = 0; i < valueArr.length; i++) {
        valueArr[i] = valueArr[i].toLowerCase();
      }
    }

    boolean findAll = true;
    for (String value : valueArr) {
      Pattern pattern = null;
      if (regExp) {
        if (!sensitive) {
          pattern = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
        } else {
          pattern = Pattern.compile(value);
        }
      }
      boolean find = false;
      for (int i = startIndex; i < dataArr.length; i++) {
        String data = dataArr[i];
        if (regExp) {
          Matcher m = pattern.matcher(data);
          find = m.find();
        } else {
          find = data.contains(value);
        }
        if (find) {
          break;
        }
      }
      if (!find) {
        findAll = false;
        break;
      }
    }
    return findAll;
  }

  /**
   * 将数组内的所有内容转换为小写
   */
  public static void toAllLowerCase(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = arr[i].toLowerCase();
    }
  }

  /**
   * 把数组所有内容转换为小写，并且去掉前后空格
   */
  public static void toAllLowerCaseTrim(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] != null) {
        arr[i] = arr[i].trim().replaceAll("^\\xa0+|\\xa0+$", "").trim().toLowerCase();
      }
    }
  }

  /**
   * 把数组所有内容转换为大写
   */
  public static void toAllUpperCase(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = arr[i].toUpperCase();
    }
  }

  /**
   * 把数组所有内容转换为大写并且去掉前后空格
   */
  public static void toAllUpperCaseTrim(String[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = arr[i].trim().toUpperCase();
    }
  }

  /**
   * 把数组所有内容转换为小写 <br> 保存在新数组中
   */
  public static String[] toNewAllLowerCase(String[] arr) {
    String[] retArr = new String[arr.length];
    for (int i = 0; i < arr.length; i++) {
      retArr[i] = arr[i].toLowerCase();
    }
    return retArr;
  }

  /**
   * 把数组所有内容转换为小写并去掉前后空格 <br> 保存在新数组中
   */
  public static String[] toNewAllLowerCaseTrim(String[] arr) {
    String[] retArr = new String[arr.length];
    for (int i = 0; i < arr.length; i++) {
      retArr[i] = arr[i].trim().toLowerCase();
    }
    return retArr;
  }

  /**
   * 把数组所有内容转换为大写 <br> 保存在新数组中
   */
  public static String[] toNewAllUpperCase(String[] arr) {
    String[] retArr = new String[arr.length];
    for (int i = 0; i < arr.length; i++) {
      retArr[i] = arr[i].toUpperCase();
    }
    return retArr;
  }

  /**
   * 把数组所有内容转换为大写，并去掉前后空格 <br> 保存在新数组中
   */
  public static String[] toNewAllUpperCaseTrim(String[] arr) {
    String[] retArr = new String[arr.length];
    for (int i = 0; i < arr.length; i++) {
      retArr[i] = arr[i].trim().toUpperCase();
    }
    return retArr;
  }

  /**
   * 在字符串中找到字符串数组中的任意一个元素，返回字符串数组中的元素在字符串的索引位置，未找到返回-1
   *
   * @param subStr 标志数组
   * @param line 查找数据
   */
  public static int indexOfFlag(String[] subStr, String line) {
    for (int i = 0; i < subStr.length; i++) {
      if (subStr[i] != null && !"".equals(subStr[i])) {
        int index = line.indexOf(subStr[i]);
        if (index >= 0) {
          return index;
        }
      }
    }
    return -1;
  }

}
