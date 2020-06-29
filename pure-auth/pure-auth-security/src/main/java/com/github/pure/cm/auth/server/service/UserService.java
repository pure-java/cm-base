package com.github.pure.cm.auth.server.service;

import com.github.pure.cm.auth.server.model.dto.LoginUserVo;
import com.github.pure.cm.auth.server.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 陈欢
 * @since 2020/6/28
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    ISysUserService sysUserService;

    /**
     * 根据用户名加载用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名获取用户信息
        SysUser userByUserName = sysUserService.getUserByUserName(username);
        if (userByUserName != null) {
            //创建 spring security 安全用户和对应的权限（从数据库查找）
            return new LoginUserVo(userByUserName.getUserName(), userByUserName.getPassword(),
                    AuthorityUtils.createAuthorityList("admin", "manager"));
        } else {
            throw new UsernameNotFoundException("用户[" + username + "]不存在");
        }
    }
}
