package com.github.pure.cm.auth.server.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 默认实现 FilterSecurityInterceptor
 * @author 陈欢
 * @since 2020/6/29
 */
@Slf4j
//@Component
public class AccessSecurityInterceptor extends FilterSecurityInterceptor implements Filter {

    // 注入前面的两个实例
    private final AccessSecurityMetadataSource accessSecurityMetadataSource;
    private final ApiAccessDecisionManager accessDecisionManagerImpl;

    @Autowired
    public AccessSecurityInterceptor(AccessSecurityMetadataSource accessSecurityMetadataSource, ApiAccessDecisionManager accessDecisionManagerImpl) {
        this.accessSecurityMetadataSource = accessSecurityMetadataSource;
        this.accessDecisionManagerImpl = accessDecisionManagerImpl;
        super.setAccessDecisionManager(accessDecisionManagerImpl);
        super.setObserveOncePerRequest(false);
    }

    /**
     * 初始化时将自定义的DecisionManager，注入到父类AbstractSecurityInterceptor中
     */
    @PostConstruct
    public void initSetManager() {
        super.setAccessDecisionManager(accessDecisionManagerImpl);
    }

    /**
     * 重写父类AbstractSecurityInterceptor，获取到自定义MetadataSource的方法
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.accessSecurityMetadataSource;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[自定义过滤器]: {}", " LoginSecurityInterceptor.doFilter()");
        super.doFilter(request,response,chain);
        //FilterInvocation fi = new FilterInvocation(request, response, chain);
        //// 调用父类的 beforeInvocation ==> accessDecisionManager.decide(..)
        //InterceptorStatusToken token = super.beforeInvocation(fi);
        //try {
        //    fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        //} finally {
        //    // 调用父类的 afterInvocation ==> afterInvocationManager.decide(..)
        //    super.afterInvocation(token, null);
        //}
    }

    /**
     * 向父类提供要处理的安全对象类型，因为父类被调用的方法参数类型大多是Object，框架需要保证传递进去的安全对象类型相同
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}