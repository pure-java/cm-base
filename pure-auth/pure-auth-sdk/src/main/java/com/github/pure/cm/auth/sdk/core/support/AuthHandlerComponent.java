package com.github.pure.cm.auth.sdk.core.support;

import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuGroup;
import com.github.pure.cm.auth.sdk.core.annotation.AuthMenuItem;
import com.github.pure.cm.auth.sdk.core.annotation.AuthOption;
import com.github.pure.cm.auth.sdk.core.annotation.AuthResource;
import com.github.pure.cm.auth.sdk.core.annotation.AuthRole;
import com.github.pure.cm.common.util.JsonUtil;
import com.github.pure.cm.model.auth.vo.AuthMenuGroupVo;
import com.github.pure.cm.model.auth.vo.AuthMenuItemVo;
import com.github.pure.cm.model.auth.vo.AuthResourceVo;
import com.github.pure.cm.model.auth.vo.AuthRoleVo;
import com.github.pure.cm.model.auth.vo.AuthVo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author 陈欢
 * @since 2020/7/7
 */
@Slf4j
public abstract class AuthHandlerComponent implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * 权限前缀
     */
    @Value("_${spring.application.name}_")
    protected String applicationPrefix;
    /**
     * 项目名称
     */
    @Value("${spring.application.name}")
    protected String applicationName;
    /**
     * code
     */
    @Value("${spring.application.name}")
    protected String applicationCode;

    @Override
    @Async
    public void onApplicationEvent(ApplicationReadyEvent event) {
        AuthVo authVo = new AuthVo();
        Set<RequestMappingVO> parse = getRequestMappingInfo(authVo);
        parse.forEach(vo -> this.fillAuthVo(authVo, vo));
    }

    /**
     * 获取请求映射信息
     *
     * @param authVo
     * @return
     */
    protected abstract Set<RequestMappingVO> getRequestMappingInfo(AuthVo authVo);

    /**
     * 填充要注册的权限信息 authVo
     *
     * @param authVo
     * @param vo     请求
     */
    private void fillAuthVo(AuthVo authVo, RequestMappingVO vo) {
        String methodUrl = vo.getMethodUrl();
        Class<?> beanType = vo.getBeanType();
        Method method = vo.getMethod();
        {
            // 类上的角色注解
            AuthRole classAuthRole = beanType.getAnnotation(AuthRole.class);
            if (Objects.nonNull(classAuthRole)) {
                authVo.getAuthRoleVos().computeIfAbsent(classAuthRole.code(),
                        key -> AuthRoleVo.builder()
                                .serverName(this.applicationName)
                                .serverCode(this.applicationCode)
                                .code(this.convertAuthCode(classAuthRole.code()))
                                .name(classAuthRole.name())
                                .build()
                );
            }
        }
        // 解析
        {
            // 方法上的注解
            AuthRole methodRole = method.getAnnotation(AuthRole.class);
            if (Objects.nonNull(methodRole)) {
                authVo.getAuthRoleVos().computeIfAbsent(methodRole.code(),
                        key -> AuthRoleVo.builder()
                                .serverName(this.applicationName)
                                .serverCode(this.applicationCode)
                                .code(this.convertAuthCode(methodRole.code()))
                                .name(methodRole.name())
                                .build()
                );
            }

            AuthOption authOption = method.getAnnotation(AuthOption.class);
            if (Objects.isNull(authOption)) {
                return;
            }

            String optionAuthCode = this.convertAuthCode(authOption.authCode());

            // 处理权限组
            AuthMenuGroup[] groups = authOption.menuGroup();
            if (ArrayUtils.isNotEmpty(groups)) {
                for (AuthMenuGroup group : groups) {
                    putAuthMenuGroup(authVo.getAuthMenuGroupVos(), group);
                }
            }

            // 处理权限项
            AuthMenuItem[] items = authOption.menuItems();
            if (ArrayUtils.isNotEmpty(items)) {
                for (AuthMenuItem authMenuItem : items) {
                    putAuthMenuItem(authVo.getAuthMenuItemVos(), methodUrl, optionAuthCode, authMenuItem);
                }
            }

            // 处理资源
            AuthResource[] resources = authOption.resources();
            if (ArrayUtils.isNotEmpty(resources)) {
                for (AuthResource resource : resources) {
                    putAuthResource(authVo.getAuthResourceVos(), methodUrl, optionAuthCode, resource);
                }
            }
        }
        log.info("{}", JsonUtil.json(authVo));
    }

    /**
     * 添加菜单组权限
     */
    protected void putAuthMenuGroup(Map<String, AuthMenuGroupVo> authMenuGroupVos, AuthMenuGroup group) {
        String groupCode = this.convertAuthCode(group.groupCode());

        Assert.state(!authMenuGroupVos.containsKey(groupCode), String.format("@AuthMenuGroup.code()重复:`%s`", groupCode));

        authMenuGroupVos.put(groupCode,
                AuthMenuGroupVo.builder()
                        .serverName(this.applicationName)
                        .serverCode(this.applicationCode)
                        //.authRoleVos()
                        .code(groupCode)
                        .groupName(group.groupName())
                        .parentCode(this.convertAuthCode(group.parentGroupCode()))
                        .build());
    }

    /**
     * 添加权限菜单项
     *
     * @param authMenuItemVos 存储菜单项 map
     * @param methodUrl       方法url
     * @param optionAuthCode  操作码
     * @param authMenuItem    菜单项
     */
    private void putAuthMenuItem(Map<String, AuthMenuItemVo> authMenuItemVos, String methodUrl, String optionAuthCode, AuthMenuItem authMenuItem) {
        String menuCode = this.convertAuthCode(authMenuItem.code());

        Assert.state(!authMenuItemVos.containsKey(menuCode), String.format("@AuthMenuItem.code()重复:`%s`", menuCode));

        authMenuItemVos.put(menuCode,
                AuthMenuItemVo.builder()
                        .serverName(this.applicationName)
                        .serverCode(this.applicationCode)
                        // 拼接上 optionAuthCode
                        .code(menuCode + ";" + optionAuthCode)
                        .name(authMenuItem.name())
                        .parentCode(this.convertAuthCode(authMenuItem.parentCode()))
                        .url(methodUrl)
                        .build());
    }

    /**
     * 添加权限资源项
     *
     * @param authResourceVos 存储资源项 map
     * @param methodUrl       url
     * @param optionAuthCode
     * @param resource        资源
     */
    private void putAuthResource(Map<String, AuthResourceVo> authResourceVos, String methodUrl, String optionAuthCode, AuthResource resource) {
        String resourceCode = this.convertAuthCode(resource.code());

        Assert.state(!authResourceVos.containsKey(resourceCode), String.format("@AuthResource.code()重复:`%s`", resourceCode));

        authResourceVos.put(resourceCode, AuthResourceVo.builder()
                .serverName(this.applicationName)
                .serverCode(this.applicationCode)
                // 拼接上 optionAuthCode
                .code(resourceCode + ";" + optionAuthCode)
                .name(resource.name())
                .parentCode(this.convertAuthCode(resource.menuItemCode()))
                .url(methodUrl)
                .build());
    }

    /**
     * 对权限码进行转换
     */
    protected String convertAuthCode(String authCode) {
        // 转换为小写，并将 多个 中划线和下划线转换为一个下划线
        return authCode.toLowerCase().replaceAll("[-_]+", "_");
    }

    @Data
    @Builder
    public static class RequestMappingVO {
        /**
         * 请求url
         */
        private String methodUrl;

        /**
         * 请求类
         */
        private Class<?> beanType;

        /**
         * 方法
         */
        private Method method;
    }
}
