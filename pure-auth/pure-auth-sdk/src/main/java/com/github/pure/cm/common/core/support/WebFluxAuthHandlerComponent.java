package com.github.pure.cm.common.core.support;

import com.github.pure.cm.common.core.annotation.AuthMenuGroup;
import com.github.pure.cm.common.core.annotation.AuthMenuItem;
import com.github.pure.cm.common.core.annotation.AuthOption;
import com.github.pure.cm.common.core.annotation.AuthResource;
import com.github.pure.cm.common.core.annotation.AuthRole;
import com.github.pure.cm.common.core.vo.AuthMenuGroupVo;
import com.github.pure.cm.common.core.vo.AuthMenuItemVo;
import com.github.pure.cm.common.core.vo.AuthResourceVo;
import com.github.pure.cm.common.core.vo.AuthRoleVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Map;
import java.util.Objects;

/**
 * 权限处理器
 *
 * @author 陈欢
 * @since 2020/7/2
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxAuthHandlerComponent implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 资源code -> 资源
        Map<String, AuthResourceVo> authResourceVos = Maps.newHashMap();

        // 菜单项code -> 菜单项
        Map<String, AuthMenuItemVo> authMenuItemVos = Maps.newHashMap();

        // 菜单组code -> 菜单组
        Map<String, AuthMenuGroupVo> authMenuGroupVos = Maps.newHashMap();

        // 角色
        Map<String, AuthRoleVo> authRoleVos = Maps.newHashMap();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
            // 类上的角色注解
            AuthRole classAuthRole = handlerMethod.getBeanType().getAnnotation(AuthRole.class);
            if (Objects.nonNull(classAuthRole)) {
                authRoleVos.computeIfAbsent(classAuthRole.code(), key -> AuthRoleVo.builder().code(classAuthRole.code()).name(classAuthRole.name()).build());
            }

            // 方法上的注解
            AuthRole methodRole = handlerMethod.getMethodAnnotation(AuthRole.class);
            if (Objects.nonNull(methodRole)) {
                authRoleVos.computeIfAbsent(classAuthRole.code(), key -> AuthRoleVo.builder().code(methodRole.code()).name(methodRole.name()).build());
            }

            AuthOption authOption = handlerMethod.getMethodAnnotation(AuthOption.class);
            if (Objects.isNull(authOption)) {
                return;
            }

            String methodUrl = requestMappingInfo.getPatternsCondition().getPatterns().stream().map(PathPattern::getPatternString).findFirst().orElse("");

            String optionAuthCode = authOption.authCode();

            // 处理权限组
            AuthMenuGroup[] groups = authOption.menuGroup();
            if (ArrayUtils.isNotEmpty(groups)) {
                for (AuthMenuGroup group : groups) {
                    AuthMenuGroupVo authMenuGroupVo =
                            authMenuGroupVos
                                    .computeIfAbsent(group.menuGroupCode(), key -> AuthMenuGroupVo.builder()
                                            //.authRoleVos()
                                            .code(group.menuGroupCode())
                                            .groupName(group.menuGroupName())
                                            .parentCode(group.parentMenuGroupCode())
                                            .build());
                    Assert.state(Objects.isNull(authMenuGroupVo), String.format("菜单组code重复:%s - %s", handlerMethod.getBeanType(), handlerMethod.getMethod()));
                }
            }

            // 处理权限项
            AuthMenuItem[] items = authOption.menuItems();
            if (ArrayUtils.isNotEmpty(items)) {
                for (AuthMenuItem authMenuItem : items) {
                    AuthMenuItemVo menuItemVo =
                            authMenuItemVos
                                    .computeIfAbsent(authMenuItem.code(), key -> AuthMenuItemVo.builder()
                                            .code(authMenuItem.code())
                                            .name(authMenuItem.name())
                                            .parentCode(authMenuItem.parentCode())
                                            .url(methodUrl)
                                            .build());
                    Assert.state(Objects.isNull(menuItemVo), String.format("菜单项code重复:%s - %s", handlerMethod.getBeanType(), handlerMethod.getMethod()));
                }
            }

            // 处理资源
            AuthResource[] resources = authOption.resources();
            if (ArrayUtils.isNotEmpty(resources)) {
                for (AuthResource resource : resources) {
                    AuthResourceVo authResourceVo =
                            authResourceVos
                                    .computeIfAbsent(resource.code(), v -> AuthResourceVo.builder()
                                            .code(resource.code())
                                            .name(resource.name())
                                            .parentCode(resource.menuItemCode())
                                            .serverName(applicationName)
                                            .url(methodUrl)
                                            .build());

                    Assert.state(Objects.isNull(authResourceVo), String.format("资源code重复:%s - %s", handlerMethod.getBeanType(), handlerMethod.getMethod()));
                }
            }
        });
    }
}
