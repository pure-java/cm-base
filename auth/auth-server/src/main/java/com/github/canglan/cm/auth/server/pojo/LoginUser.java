package com.github.canglan.cm.auth.server.pojo;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author 陈欢
 * @since 2019/11/21
 */
public class LoginUser extends User {

  private Long oid;

  public LoginUser(String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
      boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }

  public Long getOid() {
    return oid;
  }

  public LoginUser setOid(Long oid) {
    this.oid = oid;
    return this;
  }
}
