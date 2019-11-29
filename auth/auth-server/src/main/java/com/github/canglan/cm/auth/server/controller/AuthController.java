package com.github.canglan.cm.auth.server.controller;

import com.github.canglan.cm.auth.server.service.IAuthorityValidate;
import com.github.canglan.cm.common.model.ResultMessage;
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
  private IAuthorityValidate userService;

  @PostMapping("/login")
  public ResultMessage login(String userName, String password) {
    log.debug("用户 {} 登录", userName);
    return ResultMessage.success(userService.validate(userName, password));
  }
}
