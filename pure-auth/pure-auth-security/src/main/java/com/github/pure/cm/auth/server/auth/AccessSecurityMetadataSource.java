package com.github.pure.cm.auth.server.auth;

import com.github.pure.cm.auth.server.mapper.SysResourceMapper;
import com.github.pure.cm.auth.server.mapper.SysRoleMapper;
import com.github.pure.cm.auth.server.model.entity.SysRole;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.prepost.PrePostAnnotationSecurityMetadataSource;
import org.springframework.security.access.prepost.PrePostInvocationAttributeFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈欢
 * @since 2020/6/29
 */
@Slf4j
//@Component
public class AccessSecurityMetadataSource extends PrePostAnnotationSecurityMetadataSource {

    // key 是url+method ， value 是对应url资源的角色列表
    private static Map<String, Collection<ConfigAttribute>> permissionMap;

    // 从数据库取出权限数据，代码略
    private SysRoleMapper authRoleMapper;
    private SysResourceMapper sysResourceMapper;

    public AccessSecurityMetadataSource(PrePostInvocationAttributeFactory attributeFactory) {
        super(attributeFactory);
    }


    /**
     * 在Web服务器启动时，缓存系统中的所有权限映射。<br>
     * 被{@link PostConstruct}修饰的方法会在服务器加载Servlet的时候运行(构造器之后,init()之前) <br/>
     */
    @PostConstruct
    private void loadResourceDefine() {
        permissionMap = new LinkedHashMap<>();
        // 查询所有权限
        List<SysRole> sysRoles = authRoleMapper.selectRoleResource();

        Map<String, List<String>> urlExpressionMap = Maps.newHashMap();

        //sysRoles.stream()
        //        .filter(role -> CollectionUtils.isNotEmpty(role.getSysResourceList()))
        //        .forEach(role -> {
        //            role
        //                    .getSysResourceList()
        //                    .forEach(resource -> urlExpressionMap.put()));
        //        });

        //urlExpressionMap.forEach((key, value) -> {
        //    permissionMap.put(key, value.stream().map(SecurityConfig::new).collect(Collectors.toList()));
        //});

        //// 需要鉴权的url资源，@needAuth标志
        //List<SysAuthority> permissionList = sysAuthorityMapper.selectList(new QueryWrapper<>());
        //for (SysAuthority permission : permissionList) {
        //    String url = permission.get();
        //    String method = permission.getMethod();
        //    String[] roles = permission.getRoleList().split(",");
        //    log.info("{} - {}", url, method);
        //    AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, method);
        //
        //    Collection<ConfigAttribute> attributes = new ArrayList<>();
        //    for (String role : roles) {
        //        attributes.add(new SecurityConfig(role));
        //    }
        //    // 占位符，需要权限才能访问的资源 都需要添加一个占位符，保证value不是空的
        //    attributes.add(new SecurityConfig("@needAuth"));
        //    permissionMap.put(requestMatcher, attributes);
        //}
        //
        //// 公共的url资源 & 系统接口的url资源，value为null
        //List<SysPermissionDO> publicList = sysAuthorityMapper.queryPublicPermission();
        //for (SysPermissionDO permission : publicList) {
        //    String url = permission.getUrl();
        //    String method = permission.getMethod();
        //    AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher(url, "*".equals(method) ? null : method);
        //    // value为空时不做鉴权，相当于所有人都可以访问该资源URL
        //    permissionMap.put(requestMatcher, null);
        //}
        //
        //// 多余的url资源， @noAuth，所有人都无法访问
        //Collection<ConfigAttribute> attributes = new ArrayList<>();
        //attributes.add(new SecurityConfig("@noAuth"));
        //permissionMap.put(new AntPathRequestMatcher("/**", null), attributes);

        log.info("[全局权限映射集合初始化]: {}", permissionMap.toString());
    }

    /**
     * 鉴权时会被AbstractSecurityInterceptor.beforeInvocation()调用，根据URL找到对应需要的权限
     *
     * @param object 安全对象类型 FilterInvocation.class
     */
    //@Override
    //public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    //    log.info("[资源被访问：根据URL找到权限配置]: {}\n {}", object, permissionMap);
    //
    //    if (permissionMap == null) {
    //        loadResourceDefine();
    //    }
    //    final HttpServletRequest request = ((FilterInvocation) object).getRequest();
    //    for (Map.Entry<String, Collection<ConfigAttribute>> entry : permissionMap.entrySet()) {
    //
    //    }
    //    return null;
    //}

    /**
     * 用于被AbstractSecurityInterceptor调用，返回所有的 Collection<ConfigAttribute> ，以筛选出不符合要求的attribute
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    ///**
    // * 用于被AbstractSecurityInterceptor调用，验证指定的安全对象类型是否被MetadataSource支持
    // */
    //@Override
    //public boolean supports(Class<?> clazz) {
    //    return true;
    //}

    @Override
    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
        Collection<ConfigAttribute> attributes = super.getAttributes(method, targetClass);
        return attributes;
    }
}
