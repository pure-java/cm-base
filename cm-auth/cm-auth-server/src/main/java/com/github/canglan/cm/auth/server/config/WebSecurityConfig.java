package com.github.canglan.cm.auth.server.config;

import com.github.canglan.cm.auth.server.config.headler.TokenAuthenticationFailureHandler;
import com.github.canglan.cm.auth.server.config.headler.TokenAuthenticationSuccessHandler;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

  /**
   * 配置访问请求规则
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.formLogin()
        // 身份验证成功 成功处理器
        .successHandler(tokenAuthenticationSuccessHandler)
        // 身份验证 失败处理器
        .failureHandler(tokenAuthenticationFailureHandler);

    http.authorizeRequests()
        // .antMatchers("/jwt/**").permitAll()
        .antMatchers("/oauth/**").authenticated()
    /*.anyRequest().authenticated()*/;
  }

  /**
   * 配置应用程序 Filter链
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 身份验证管理生成器
    auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
  }

  /**
   * 以保证在刷新Token时能成功刷新
   */
  @Autowired
  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
    // 配置用户来源于数据库
    // 配置密码加密方式  BCryptPasswordEncoder，添加用户加密的时候也用这个加密
    auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
