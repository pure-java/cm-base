package com.github.pure.cm.auth.server.service;

import com.github.pure.cm.auth.server.model.dto.LoginUserVo;
import com.github.pure.cm.auth.server.model.entity.SysUser;
import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.common.core.util.StringUtil;import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
        SysUser sysUser = sysUserService.getUserAuthByUserName(username);
        if (sysUser != null) {

            Set<String> list = Sets.newHashSet();
            sysUser.getSysRoleList().forEach(role -> {
                if (StringUtil.isNotBlank(role.getCode())) {
                    list.add("ROLE_" + role.getCode());
                }
                role.getSysMenuList().forEach(menu -> {
                    menu.getSysAuthorities().forEach(auth -> list.add(auth.getExpression()));
                });
            });


            //创建 spring security 安全用户和对应的权限（从数据库查找）
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(list.toArray(new String[]{}));
            return new LoginUserVo(sysUser.getUserName(), sysUser.getPassword(), authorityList);
        } else {
            throw new UsernameNotFoundException("用户[" + username + "]不存在");
        }
    }

    public static void main(String[] args) {
        String s = "{ \"scope\": [\"all\"],\"client_id\": \"test\", \"client_secret\": \"$2a$10$Y31Y7qEwjVowsCEqTdPWieJwa7BVEavfUksfTXTRFAFn1bjKrMS.O\", \"authorized_grant_types\": [\"password\", \"authorization_code\"]}";
        BaseClientDetails baseClientDetails = JsonUtil.newIns().jsonToObject(s, BaseClientDetails.class);
        System.out.println(baseClientDetails);
    }
}
