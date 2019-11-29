package com.github.canglan.cm.auth.server.service.impl;

import com.github.canglan.cm.auth.server.entity.IdUser;
import com.github.canglan.cm.auth.server.service.IAuthorityValidate;
import com.github.canglan.cm.auth.server.service.IUserService;
import com.github.canglan.cm.cloud.vo.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 权限验证相关
 *
 * @author 陈欢
 * @since 2019/11/28
 */
@Service
public class AuthorityValidateImpl implements IAuthorityValidate {

  @Autowired
  private IUserService userService;
  @Autowired
  @Lazy
  private PasswordEncoder encode;

  @Override
  public UserInfo validate(String userName, String password) {
    IdUser user = userService.getUserByUserName(userName);
    if (user == null) {
      return null;
    }
    if (!encode.matches(password, user.getPassword())) {
      return null;
    }
    // 清空密码
    user.setPassword(null);
    UserInfo userInfo = new UserInfo();
    userInfo.setUserNick(user.getUserNick());
    userInfo.setUserName(user.getUserName());
    userInfo.setOid(user.getOid());
    return userInfo;
  }
}
