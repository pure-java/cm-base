package com.github.canglan.cm.common.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 返回操作信息
 *
 * @author admin
 * @date 2017-07-19 15:43
 **/
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ResultMessage<T> implements Serializable, IModel {

  private static final long serialVersionUID = 2235085689160152768L;

  /**
   * 状态码: {@link StatusCode}
   */
  private int code;
  /**
   * 提示信息
   */
  private String message;

  /**
   * 数据
   * 操作成功才应该有数据
   */
  private T data;
  /**
   * 操作状态,没有权限、没登陆、操作失败 = false，成功 = true
   */
  private Boolean status;

  private ResultMessage(int code, String message, T data, boolean status) {
    this.setCode(code);
    this.setMessage(message);
    this.setData(data);
    this.setStatus(status);
  }

  public ResultMessage() {
  }
  // newIns ==========================

  /**
   * @param status this.code = status ? StatusCode.SUCCESS : StatusCode.FAIL;
   */
  public static <T> ResultMessage<T> newInstance(boolean status) {
    return newInstance(status ? StatusCode.SUCCESS : StatusCode.FAIL, null, null, status);
  }


  public static <T> ResultMessage<T> newInstance(int code, String message, T data, Boolean status) {
    return new ResultMessage<T>(code, message, data, status);
  }


  public static <T> ResultMessage<T> newIns(int code, String message, T data) {
    return new ResultMessage<T>(code, message, data, true);
  }

  // success  ====================================

  /**
   * 成功 <p> status = true
   */
  public static <T> ResultMessage<T> success() {
    return success(null);
  }

  /**
   * 失败 <p> 状态默认 = false,message = null
   */
  public static <T> ResultMessage<T> success(String message) {
    return newInstance(StatusCode.SUCCESS, message, null, true);
  }

  /**
   * 成功 <p> 状态默认 = true,message = null
   */
  public static <T> ResultMessage<T> success(T data) {
    return newInstance(StatusCode.SUCCESS, null, data, true);
  }

  // fail  ====================================

  /**
   * 失败 <p> 状态默认 = false,message = null
   */
  public static <T> ResultMessage<T> fail() {
    return fail(null);
  }

  /**
   * 失败 <p> 状态默认 = false,message = null
   */
  public static <T> ResultMessage<T> fail(T data) {
    return newInstance(StatusCode.SUCCESS, null, data, false);
  }

  /**
   * 失败
   *
   * @param message 提示信息
   */
  public static <T> ResultMessage<T> fail(String message) {
    ResultMessage<T> tResultMessage = newInstance(StatusCode.SUCCESS, message, null, false);
    tResultMessage.setCode(StatusCode.SYSTEM_ERROR);
    return tResultMessage;
  }

  // setter =================

  public ResultMessage<T> setCode(int code) {
    this.code = code;
    if (code != StatusCode.SUCCESS) {
      this.status = false;
    } else {
      this.status = true;
    }
    return this;
  }

}
