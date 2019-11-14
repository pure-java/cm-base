package com.github.canglan.cm.cloud.vo.user;

import lombok.Data;

/**
 * @date 2019/11/11
 */
@Data
public class UserInfo {

  /**
   * id
   */
  private String id;
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
  private String name;
  /**
   * 描述
   */
  private String desc;

}
