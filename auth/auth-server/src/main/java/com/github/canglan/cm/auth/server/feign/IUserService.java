package com.github.canglan.cm.auth.server.feign;

import com.github.canglan.cm.cloud.vo.user.UserInfo;
import com.github.canglan.cm.common.model.ResultMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@FeignClient(value = "identity"/*, fallback = UserServiceImpl.class*/)
public interface IUserService {

  /**
   * 验证用户
   *
   * @param userName 账号名
   * @param password 密码
   * @return 用户信息
   */
  @RequestMapping(value = "/api/authority/validate", method = RequestMethod.POST)
  public ResultMessage<UserInfo> validationUser(@RequestParam("userName") String userName,
      @RequestParam("password") String password);
}
