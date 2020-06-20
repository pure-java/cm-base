package com.github.pure.cm.common.data.model;

import lombok.Data;

/**
 * @since 2019/11/11
 */
@Data
public class UserInfo {

  /**
   * id
   */
  private Long oid;
  /**
   * 用户名
   */
  private String userName;
  /**
   * 密码
   */
  private String password;
  /**
   * 名称
   */
  private String userNick;
  /**
   * 描述
   */
  private String desc;

}
