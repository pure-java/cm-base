package com.github.pure.cm.auth.server.auth;

import com.github.pure.cm.auth.server.headler.CustomAccessDeniedHandler;
import com.github.pure.cm.auth.server.headler.CustomAuthPoint;
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
import org.springframework.security.config.http.SessionCreationPolicy;
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
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthPoint customAuthPoint;

    /**
     * 配置访问请求规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // 关闭表单登录
                .formLogin()
                // 身份验证成功成功处理器
                //.successHandler((request, response, authentication) -> {
                //
                //})
                // 身份验证失败处理器
                //.failureHandler((request, response, exception) -> {
                //    response.setContentType("application/json;charset=utf-8");
                //    response.getWriter().write(exception.getMessage());
                //})
                // 禁用表单登录
                .disable()
                // 由于使用token，不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 有 resourceServer 配置，这里不进行权限认证配置
        //.and()
        //.authorizeRequests()
        //// 所有url都需要权限认证
        //.anyRequest().authenticated();

        // 配置身份认证异常和权限认证异常处理器
        http.exceptionHandling(config -> config.accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(customAuthPoint));
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
