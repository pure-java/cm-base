package com.github.pure.cm.common.core.support;

import com.github.pure.cm.auth.jdk.core.vo.AuthEntry;
import com.github.pure.cm.common.core.annotation.AuthMenuGroup;
import com.github.pure.cm.common.core.annotation.AuthMenuItem;
import com.github.pure.cm.common.core.annotation.AuthOption;
import com.github.pure.cm.common.core.annotation.AuthResource;
import com.github.pure.cm.common.core.annotation.AuthRole;
import com.github.pure.cm.common.core.vo.AuthMenuGroupVo;
import com.github.pure.cm.common.core.vo.AuthMenuItemVo;
import com.github.pure.cm.common.core.vo.AuthResourceVo;
import com.github.pure.cm.common.core.vo.AuthRoleVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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


        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();


        handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
            AuthRole authRole = handlerMethod.getBeanType().getAnnotation(AuthRole.class);
            AuthRoleVo authRoleVo = null;
            if (Objects.nonNull(authRole)) {
                authRoleVo = AuthRoleVo.builder().code(authRole.code()).name(authRole.name()).build();
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

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> controllerBeans = applicationContext.getBeansWithAnnotation(RestController.class);

        // 所有权限
        controllerBeans.forEach((name, bean) -> {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            if (Objects.isNull(targetClass.getAnnotation(RestController.class))) {
                return;
            }

            RequestMapping mappingAnnotation = AnnotatedElementUtils.getMergedAnnotation(targetClass, RequestMapping.class);

            // 菜单组
            AuthRole authRole = AnnotatedElementUtils.getMergedAnnotation(targetClass, AuthRole.class);
            if (Objects.isNull(authRole)) {
            }

            // 类路径
            // 如果数组长度为0，给予默认值
            Set<String> baseUrls = Sets.newHashSet(mappingAnnotation.value().length != 0 ? mappingAnnotation.value() : new String[]{""});

            Method[] methods = targetClass.getMethods();


            for (Method method : methods) {


                //Set<String> methodUrlSet = getUrl(baseUrls, method);
                //log.info("{}", methodUrlSet);
            }
        });
    }

    private List<AuthEntry> parseAuth(AuthOption authOption) {
        List<AuthEntry> authEntries = Lists.newArrayList();
        if (Objects.isNull(authOption)) {
            return authEntries;
        }
        AuthMenuGroup[] authGroups = authOption.group();
        for (AuthMenuGroup authGroup : authGroups) {
            authEntries.add(
                    AuthEntry.builder()
                            .name(authGroup.name())
                            .code(authGroup.code())
                            .parentCode(authGroup.parentCode())
                            .type(AuthTypeEnum.MENU).build()
            );
        }
        AuthResource[] authResources = authOption.resources();
        for (AuthResource authResource : authResources) {
            authEntries.add(
                    AuthEntry.builder()
                            .name(authResource.name())
                            .code(authResource.code())
                            .parentCode(authResource.groupCode())
                            .type(AuthTypeEnum.BUTTON).build()
            );
        }
        return authEntries;
    }

    /**
     * 获取方法组装而成的请求url
     *
     * @param baseUrls 类的url
     * @param method   方法
     */
    private Set<String> getUrl(Set<String> baseUrls, Method method) {
        // 一个方法组装而成的url
        Set<String> urls = Sets.newHashSet();

        AnnotationAttributes methodRequest = AnnotatedElementUtils.getMergedAnnotationAttributes(method, RequestMapping.class);
        if (Objects.isNull(methodRequest)) {
            return urls;
        }

        // 方法路径
        // 如果数组长度为0，给予默认url
        Set<String> reqUriSet = Sets.newHashSet(methodRequest.getStringArray("value").length != 0 ? methodRequest.getStringArray("value") : new String[]{""});

        baseUrls.forEach(baseUrl ->
                reqUriSet.forEach(reqUri -> {
                    StringBuilder baseUrlBuilder = new StringBuilder(baseUrl);
                    // 添加前缀
                    if (!baseUrlBuilder.toString().startsWith("/")) {
                        baseUrlBuilder.insert(0, "/");
                    }

                    if (baseUrlBuilder.toString().endsWith("/") && reqUri.startsWith("/")) {
                        baseUrlBuilder.append(reqUri, 1, reqUri.length() + 1);

                    } else if (!baseUrlBuilder.toString().endsWith("/") && !reqUri.startsWith("/")) {
                        baseUrlBuilder.append("/").append(reqUri);

                    } else {
                        baseUrlBuilder.append(reqUri);
                    }
                    urls.add(baseUrlBuilder.toString());
                }));
        return urls;
    }

}
