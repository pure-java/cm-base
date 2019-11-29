package com.github.canglan.cm.auth.server.service;

import com.github.canglan.cm.cloud.vo.user.UserInfo;

/**
 * @author 陈欢
 * @since 2019/11/28
 */
public interface IAuthorityValidate {

  /**
   * 验证用户身份
   *
   * @param userName 用户名
   * @param password 密码
   * @return 验证成功用户名代表用户信息，失败返回null
   */
  public UserInfo validate(String userName, String password);

}
