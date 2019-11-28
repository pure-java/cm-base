package com.github.canglan.cm.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 系统用户
 *
 * @author chenhuan
 * @create 2017-06-26 19:57
 **/
@Data
@Accessors(chain = true)
public class SysUser {

  private static final long serialVersionUID = 1L;

  private Long oid;
  /**
   * 账号
   **/
  private String userName;

}
