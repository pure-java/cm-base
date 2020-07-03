package com.github.pure.cm.common.core.auth.support;

import com.github.pure.cm.common.core.auth.annotation.Auth;
import com.github.pure.cm.common.core.auth.annotation.AuthGroup;
import com.github.pure.cm.common.core.auth.annotation.AuthResource;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class AuthHandlerComponent implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> controllerBeans = applicationContext.getBeansWithAnnotation(RestController.class);

        // 所有权限
        List<AuthEntry> allAuthEntries = Lists.newArrayList();
        controllerBeans.forEach((name, bean) -> {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            if (Objects.isNull(targetClass.getAnnotation(RestController.class))) {
                return;
            }

            RequestMapping mappingAnnotation = AnnotatedElementUtils.getMergedAnnotation(targetClass, RequestMapping.class);
            AuthGroup authGroupAnnotation = AnnotatedElementUtils.getMergedAnnotation(targetClass, AuthGroup.class);
            if (Objects.isNull(mappingAnnotation)) {
                return;
            }

            // 类路径
            // 如果数组长度为0，给予默认值
            Set<String> baseUrls = Sets.newHashSet(mappingAnnotation.value().length != 0 ? mappingAnnotation.value() : new String[]{""});

            Method[] methods = targetClass.getMethods();


            for (Method method : methods) {
                allAuthEntries.addAll(parseAuth(AnnotationUtils.getAnnotation(method, Auth.class)));

                AuthGroup methodAuthGroup = AnnotationUtils.getAnnotation(method, AuthGroup.class);
                if (Objects.nonNull(methodAuthGroup)) {
                    allAuthEntries.add(
                            AuthEntry.builder()
                                    .name(methodAuthGroup.name())
                                    .code(methodAuthGroup.code())
                                    .parentCode(methodAuthGroup.parentCode())
                                    .type(AuthTypeEnum.MENU)
                                    .build()
                    );
                }

                AuthResource authResource = AnnotationUtils.getAnnotation(method, AuthResource.class);
                if (Objects.nonNull(authResource)) {
                    allAuthEntries.add(
                            AuthEntry.builder()
                                    .name(authResource.name())
                                    .code(authResource.code())
                                    .parentCode(authResource.groupCode())
                                    .type(AuthTypeEnum.BUTTON)
                                    .build()
                    );
                }

                Set<String> methodUrlSet = getUrl(baseUrls, method);
                log.info("{}", methodUrlSet);
            }
        });
        log.info("{}", allAuthEntries);
    }

    private List<AuthEntry> parseAuth(Auth auth) {
        List<AuthEntry> authEntries = Lists.newArrayList();
        if (Objects.isNull(auth)) {
            return authEntries;
        }
        AuthGroup[] authGroups = auth.group();
        for (AuthGroup authGroup : authGroups) {
            authEntries.add(
                    AuthEntry.builder()
                            .name(authGroup.name())
                            .code(authGroup.code())
                            .parentCode(authGroup.parentCode())
                            .type(AuthTypeEnum.MENU).build()
            );
        }
        AuthResource[] authResources = auth.resources();
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
