package com.github.canglan.cm.common.data.constants;

/**
 * @author chenhuan
 */
public enum StatusCode {
  /**
   * 空的。使用
   */
  EMPTY;

  /**
   * 没有权限
   */
  public static final int NO_PERMISSION = -2;

  /**
   * 失败
   */
  public static final int FAIL = 0;
  /**
   * 成功
   */
  public static final int SUCCESS = 200;
  /**
   * 数据验证错误
   */
  public static final int VALID = 2;
  /**
   * 系统错误
   */
  public static final int SYSTEM_ERROR = 500;
  /**
   * 未登陆
   */
  public static final int NO_LOGIN = 501;

}
