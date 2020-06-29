package com.github.pure.cm.auth.common.dto;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author 陈欢
 * @since 2019/11/21
 */
public class LoginUserVo extends User implements Serializable {

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
