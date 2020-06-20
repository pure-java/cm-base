package com.github.pure.cm.gate.gateway.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author 陈欢
 * @since 2020/3/29
 */
@Service(value = "notifyImpl")
public class NotifyImpl implements Notify {

  public Map<Integer, String> ret = new HashMap<Integer, String>();
  public Map<Integer, Throwable> errors = new HashMap<Integer, Throwable>();

  @Override
  public void onreturn(String msg, Integer id) {
    System.out.println("onreturn:" + msg);
    ret.put(id, msg);
  }

  @Override
  public void onthrow(Throwable ex, Integer id) {
    errors.put(id, ex);
  }
}
