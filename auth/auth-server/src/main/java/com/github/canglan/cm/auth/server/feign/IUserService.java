package com.github.canglan.cm.auth.server.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@FeignClient(value = "me-admin")
public interface IUserService {

  /**
   * 验证用户
   */
  @RequestMapping("/api/user/validation")
  public void validationUser();
}
