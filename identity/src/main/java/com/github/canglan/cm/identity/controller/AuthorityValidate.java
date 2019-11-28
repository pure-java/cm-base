package com.github.canglan.cm.identity.controller;

import com.github.canglan.cm.common.model.ResultMessage;
import com.github.canglan.cm.identity.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限验证
 *
 * @author 陈欢
 * @since 2019/11/28
 */
@Api(tags = "")
@RestController
@RequestMapping("/identity/idUser")
public class AuthorityValidate {

  @Autowired
  private IUserService iUserService;

  @PostMapping(value = "validate")
  public ResultMessage validate(String username, String password) {
    return ResultMessage.success();
  }
}
