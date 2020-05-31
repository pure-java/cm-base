package com.github.pure.cm.auth.server.config;

import com.github.pure.cm.auth.server.model.entity.SysUser;
import com.github.pure.cm.auth.server.model.dto.LoginUser;
import com.github.pure.cm.auth.server.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 本地 用户服务
 *
 * @author 陈欢
 * @since 2019/11/30
 */
@Configuration
public class LocalUserDetailsService implements UserDetailsService {

  @Autowired
  private ISysUserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 通过用户名获取用户信息
    SysUser userByUserName = userService.getUserByUserName(username);
    if (userByUserName != null) {
      //创建 spring security 安全用户和对应的权限（从数据库查找）
      return new LoginUser(userByUserName.getUserName(), userByUserName.getPassword(),
          AuthorityUtils.createAuthorityList("admin", "manager"));
    } else {
      throw new UsernameNotFoundException("用户[" + username + "]不存在");
    }
  }
}
