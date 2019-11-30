package com.github.canglan.cm.auth.server.controller;

import com.github.canglan.cm.auth.server.service.ISysAuthorityValidate;
import com.github.canglan.cm.common.data.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @since 2019/11/19
 */
@RestController
@RequestMapping("jwt")
@Slf4j
public class AuthController {

  @Autowired
  private ISysAuthorityValidate userService;

  @PostMapping("/login")
  public Result login(String userName, String password) {
    log.debug("用户 {} 登录", userName);
    return Result.success(userService.validate(userName, password));
  }
}
