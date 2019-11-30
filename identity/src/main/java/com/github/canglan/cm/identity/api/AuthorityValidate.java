package com.github.canglan.cm.identity.api;

import com.github.canglan.cm.cloud.vo.user.UserInfo;
import com.github.canglan.cm.common.model.Result;
import com.github.canglan.cm.identity.service.IAuthorityValidate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限验证相关
 *
 * @author 陈欢
 * @since 2019/11/28
 */
@Api(tags = "")
@RestController
@RequestMapping("/api/authority/")
public class AuthorityValidate {

  @Autowired
  private IAuthorityValidate authorityValidate;

  @ApiOperation(value = "验证用户", notes = "根据用户名与密码进行验证")
  @PostMapping(value = "validate")
  public Result<UserInfo> validate(String userName, String password) {
    return Result.success(authorityValidate.validate(userName, password));
  }
}
