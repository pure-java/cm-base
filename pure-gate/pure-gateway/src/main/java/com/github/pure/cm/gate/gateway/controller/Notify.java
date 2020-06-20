package com.github.pure.cm.gate.gateway.controller;

/**
 * @author 陈欢
 * @since 2020/3/29
 */
public interface Notify {

  public void onreturn(String msg, Integer id);

  public void onthrow(Throwable ex, Integer id);
}
