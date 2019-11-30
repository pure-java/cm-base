package com.github.canglan.cm.common.util.basic;

import java.math.BigDecimal;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * 计算辅助类
 *
 * @author bairitan
 * @since 2018-04-07 10:37
 **/
public class MathUtil {

  /**
   * 判断number是否是{@link Number}类型 <p>如果number == null也将返回false
   */
  public static boolean isZero(Object number) {
    if (!MathUtil.isNumber(number)) {
      return false;
    }
    return isZero(new BigDecimal(number.toString()));
  }

  /**
   * 参数是否为 0
   *
   * @return 如果decimal等于0或为null也将返回true
   */
  public static boolean isZero(BigDecimal decimal) {
    if (decimal == null) {
      return true;
    }
    if (decimal.compareTo(new BigDecimal(0)) == 0) {
      return true;
    }
    return false;
  }

  /**
   * 判断value是否是Number,不能判断以正号开头的字符串，如："+111"
   *
   * @param valueStr 判断字符
   * @return valueStr不为空且是Number时返回true
   */
  public static boolean isNumber(String valueStr) {
    if (StringUtils.isBlank(valueStr)) {
      return false;
    }
    if ("^-?[1-9]\\d*$".matches(valueStr)) {
      return true;
    }

    return "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$".matches(valueStr);
  }

  public static void main(String[] args) {
    String s = "2019";
    System.out.println(isNumber(s));
    s = "-2019.001";
    System.out.println(isNumber(s));
    s = "2019.0001";
    System.out.println(isNumber(s));
    s = "-2019。000";
    System.out.println(isNumber(s));
  }

  /**
   * 判断value是否是Number
   *
   * @return valueStr不为空且是Number时返回true
   */
  public static boolean isNumber(Object valueStr) {
    if (Objects.isNull(valueStr)) {
      return false;
    }
    if (valueStr instanceof Number) {
      return true;
    }
    return isNumber(valueStr.toString());
  }
  //  divide============================================

  /**
   * 除法
   *
   * @param dividend 被除数
   * @param divisor 除数
   * @return 结果，默认保留两位小数，并采用四舍五入
   */
  public static BigDecimal divide(Object dividend, Object divisor) {
    return divide(dividend, divisor, 2, BigDecimal.ROUND_HALF_UP);
  }

  /**
   * 除法
   *
   * @param dividend 被除数
   * @param divisor 除数
   * @param scale 保留小数位数
   * @return 结果，采用四舍五入
   */
  public static BigDecimal divide(Object dividend, Object divisor, int scale) {
    return divide(dividend, divisor, scale, BigDecimal.ROUND_HALF_UP);
  }

  /**
   * 除法
   *
   * @param dividend 被除数
   * @param divisor 除数
   * @param scale 保留小数位数
   * @param roundingMode 舍入模式
   * @return 计算结果
   */
  public static BigDecimal divide(Object dividend, Object divisor, int scale, int roundingMode) {
    if (!MathUtil.isNumber(dividend)) {
      throw new RuntimeException("dividend is not number");
    }
    if (!MathUtil.isNumber(divisor)) {
      throw new RuntimeException("divisor is not number");
    }
    BigDecimal dividendBigDecimal = new BigDecimal(DataUtil.toStr(dividend));
    BigDecimal divisorBigDecimal = new BigDecimal(DataUtil.toStr(divisor));
    return dividendBigDecimal.divide(divisorBigDecimal, scale, roundingMode);
  }

  // multiply ==========================================================

  /**
   * 两个数字相乘
   *
   * @param multiplicand 被乘数
   * @param multiplier 乘数
   */
  public static BigDecimal multiply(Object multiplicand, Object multiplier) {
    if (!MathUtil.isNumber(multiplicand)) {
      throw new RuntimeException("dividend is not number");
    }
    if (!MathUtil.isNumber(multiplier)) {
      throw new RuntimeException("divisor is not number");
    }
    BigDecimal multiplicandBigDecimal = new BigDecimal(DataUtil.toStr(multiplicand));
    BigDecimal multiplierBigDecimal = new BigDecimal(DataUtil.toStr(multiplier));
    return multiplicandBigDecimal.multiply(multiplierBigDecimal);
  }

  // add ===================================

  /**
   * 将两个数相加
   */
  public static BigDecimal add(Object add1, Object add2) {
    validationIsNum(add1);
    validationIsNum(add2);
    BigDecimal dividendBigDecimal = new BigDecimal(DataUtil.toStr(add1));
    BigDecimal divisorBigDecimal = new BigDecimal(DataUtil.toStr(add2));
    return dividendBigDecimal.add(divisorBigDecimal);
  }

  //  subtraction===========================================

  /**
   * 减法
   *
   * @param minuend 被减数
   * @param subtrahend 减数
   * @return 结果
   */
  public static long subtractionToLong(Object minuend, Object subtrahend) {
    return subtraction(minuend, subtrahend).longValue();
  }

  /**
   * 减法
   *
   * @param minuend 被减数
   * @param subtrahend 减数
   * @return 结果
   */
  public static BigDecimal subtraction(Object minuend, Object subtrahend) {
    if (!MathUtil.isNumber(minuend)) {
      throw new RuntimeException("被减数不是number");
    }
    if (!MathUtil.isNumber(subtrahend)) {
      throw new RuntimeException("减数不是number");
    }
    BigDecimal minuendBigDecimal = new BigDecimal(DataUtil.toStr(minuend, "0"));
    BigDecimal subtrahendBigDecimal = new BigDecimal(DataUtil.toStr(subtrahend, "0"));
    return minuendBigDecimal.subtract(subtrahendBigDecimal);
  }

  //  =========================percent 百分比==========================

  /**
   * 获取百分比
   *
   * @param object 需要求百分比的数字
   */
  public static BigDecimal percent(Object object) {
    if (Objects.isNull(object)) {
      throw new RuntimeException("且百分比的对象不能为空");
    }
    return multiply(object, 100);
  }

  /**
   * 计算百分比,默认保留两位小数
   *
   * @param timelyCount 除数
   * @param currFaultCount 被除数
   * @return 结果 百分比
   */
  public static BigDecimal calcPercent(Integer timelyCount, Integer currFaultCount) {
    return calcPercent(timelyCount, currFaultCount, 2);
  }

  /**
   * 计算百分比
   *
   * @param timelyCount 除数
   * @param currFaultCount 被除数
   * @return 结果 百分比
   */
  public static BigDecimal calcPercent(Integer timelyCount, Integer currFaultCount, int scale) {
    return percent(divide(timelyCount, currFaultCount, scale));
  }

  // compare==============================================

  /**
   * 数字字符串进行比较
   * <pre>
   * 参数必须是数字类型
   * </pre>
   */
  public static int numberCompareTo(String s1, String s2) {
    validationIsNum(s1);
    validationIsNum(s2);
    if (Objects.equals(s1, s2)) {
      return 0;
    }
    return new BigDecimal(s1).compareTo(new BigDecimal(s2));
  }

  /**
   * 数字字符串进行比较，参数必须能转换为数字类型
   * <pre>
   *      MathUtil.numberCompareTo(0, 1) = -1
   *      MathUtil.numberCompareTo(0, 0) = 0
   *      MathUtil.numberCompareTo(1, 0) = 1
   * </pre>
   *
   * @return 比较结果 s1 > s2 返回正数 , s1 < s2 返回负数, s1 == s2  返回0
   */
  public static int numberCompareTo(Object s1, Object s2) {
    validationIsNum(s1);
    validationIsNum(s2);
    return new BigDecimal(DataUtil.toStr(s1)).compareTo(new BigDecimal(DataUtil.toStr(s2)));
  }

  /**
   * 验证参数是否为数字类型
   */
  private static void validationIsNum(Object num) {
    if (!MathUtil.isNumber(num)) {
      throw new RuntimeException(String.format("param [ %s ] is not number", num));
    }
  }
}
