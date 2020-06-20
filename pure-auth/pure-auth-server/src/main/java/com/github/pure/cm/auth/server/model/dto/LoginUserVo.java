package com.github.pure.cm.auth.server.model.dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author 陈欢
 * @since 2019/11/21
 */
public class LoginUserVo extends User {

  private Long oid;

  public LoginUserVo(String username, String password,
                     Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public LoginUserVo(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                     boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }

  public Long getOid() {
    return oid;
  }

  public LoginUserVo setOid(Long oid) {
    this.oid = oid;
    return this;
  }
}
