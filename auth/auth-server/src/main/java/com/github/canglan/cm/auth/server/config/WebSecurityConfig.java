package com.github.canglan.cm.auth.server.config;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * spring security 认证
 *
 * @author bairitan
 * @date 2019/11/12
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception { // HTTP请求安全处理
    http.cors().disable()
        // 异常处理
        .exceptionHandling()
        .authenticationEntryPoint((request, response, e) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        // 配置验证
        .and()
        .authorizeRequests().antMatchers("/**").authenticated()

        .and().formLogin();
  }

  @Override
  public void configure(WebSecurity web) throws Exception { // WEB安全
    super.configure(web);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 身份验证管理生成器
    auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }
}
