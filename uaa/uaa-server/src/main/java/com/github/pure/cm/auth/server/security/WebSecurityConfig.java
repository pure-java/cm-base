package com.github.pure.cm.auth.server.security;

import com.github.pure.cm.auth.server.headler.AuthFailPoint;
import com.github.pure.cm.auth.server.headler.CustomAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 认证
 *
 * @author bairitan
 * @since 2019/11/12
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private AuthFailPoint authFailPoint;

    /**
     * 配置访问请求规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // 关闭表单登录
                .formLogin()
                // 禁用表单登录
                .disable()
                // 由于使用token，不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 有 resourceServer 配置，这里不进行权限认证配置
                .and()
                .authorizeRequests()

                //// 所有url都需要权限认证
                .anyRequest().authenticated()
                // 设置 httpbasic 认证
                .and()
                .httpBasic(configurer -> {
                    configurer.authenticationEntryPoint(this.authFailPoint);
                });

        // 配置身份认证异常和权限认证异常处理器
        http.exceptionHandling(config -> config.accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(authFailPoint));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    /**
     * 身份认证相关配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
