package com.github.canglan.cm.identity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.canglan.cm.identity.entity.IdUser;
import com.github.canglan.cm.identity.service.IAuthorityValidate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author 陈欢
 * @since 2019/11/28
 */
@Service
public class AuthorityValidateImpl implements IAuthorityValidate {

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Override
  public IdUser validate(String userName, String password) {
    QueryWrapper<IdUser> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_name", userName);
    // IdUser one = super.daoUtil.getOne(queryWrapper);
    // if (one != null && encoder.matches(password, one.getPassword())) {

    // }
    return null;
  }
}
