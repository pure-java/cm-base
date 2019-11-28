package com.github.canglan.cm.auth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * spring security 认证
 *
 * @author bairitan
 * @since 2019/11/12
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler;
  @Autowired
  private TokenAuthenticationSuccessHandler tokenAuthenticationSuccessHandler;

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception { // HTTP请求安全处理
    http.csrf().disable();

    http.formLogin()
        .successHandler(tokenAuthenticationSuccessHandler)
        .failureHandler(tokenAuthenticationFailureHandler);

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
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();
    String pwd = passwordEncode.encode("123456");
    manager.createUser(User.withUsername("user_1").password(pwd).authorities("USER").build());
    manager.createUser(User.withUsername("user_2").password(pwd).authorities("USER").build());
    return manager;
    // #####################实际开发中在下面写从数据库获取数据###############################
    // return new UserDetailsService() {
    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // // 通过用户名获取用户信息
    // boolean isUserExist = false;
    // if (isUserExist) {
    // //创建 spring security 安全用户和对应的权限（从数据库查找）
    // User user = new User("username", "password",
    // AuthorityUtils.createAuthorityList("admin", "manager"));
    // return user;
    // } else {
    // throw new UsernameNotFoundException("用户[" + username + "]不存在");
    // }
    // }
    // };
  }

}
