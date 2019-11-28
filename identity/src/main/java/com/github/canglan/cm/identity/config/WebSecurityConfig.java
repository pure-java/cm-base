package com.github.canglan.cm.identity.config;

import com.github.canglan.cm.identity.pojo.LoginUser;
import com.github.canglan.cm.identity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private IUserService userService;
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private PasswordEncoder encode;

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception { // HTTP请求安全处理
    http.csrf().disable();

    http.formLogin();

    http.authorizeRequests()
        // .antMatchers("/login", "/authentication/form").permitAll()
        .antMatchers("/oauth/**").permitAll()
        .anyRequest().authenticated();
  }

  @Override
  public void configure(WebSecurity web) throws Exception { // WEB安全
    super.configure(web);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 身份验证管理生成器
    auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }

  /**
   * 以保证在刷新Token时能成功刷新
   */
  @Autowired
  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
    // 配置用户来源于数据库
    // 配置密码加密方式  BCryptPasswordEncoder，添加用户加密的时候请也用这个加密
    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {

    // #####################从数据库查询数据###############################
    return username -> {
      // 通过用户名获取用户信息
      boolean isUserExist = true;
      // this.userService;
      if (isUserExist) {

        //创建 spring security 安全用户和对应的权限（从数据库查找）
        LoginUser user = new LoginUser("admin", encode.encode("admin"),
            AuthorityUtils.createAuthorityList("admin", "manager"));
        user.setOid(1110L);
        return user;
      } else {
        throw new UsernameNotFoundException("用户[" + username + "]不存在");
      }
    };
  }

}