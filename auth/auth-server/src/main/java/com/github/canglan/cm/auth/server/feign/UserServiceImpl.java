package com.github.canglan.cm.auth.server.feign;

import com.github.canglan.cm.cloud.vo.user.UserInfo;
import com.github.canglan.cm.common.model.ResultMessage;
import org.springframework.stereotype.Component;

/**
 * @author 陈欢
 * @since 2019/11/29
 */
// @Component
public class UserServiceImpl implements IUserService {

  @Override
  public ResultMessage<UserInfo> validationUser(String userName, String password) {
    return ResultMessage.fail("认证用户服务出现异常！");
  }
}
