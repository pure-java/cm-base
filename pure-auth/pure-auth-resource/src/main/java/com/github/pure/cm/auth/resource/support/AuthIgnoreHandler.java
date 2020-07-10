package com.github.pure.cm.auth.resource.support;

import com.github.pure.cm.auth.resource.annoation.AuthIgnore;
import com.github.pure.cm.common.core.util.JsonUtil;
import com.github.pure.cm.common.core.util.collection.CollectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 鉴权忽略处理器
 *
 * @author 陈欢
 * @since 2020/7/9
 */
public abstract class AuthIgnoreHandler {

    /**
     * 获取使用 {@link AuthIgnore }注解的url
     */
    public abstract Set<String> getAuthIgnoreUrl();

    protected Set<String> authIgnoreUrlList;

    /**
     * web 忽略鉴权 url
     */
    @Slf4j
    public static class WebAuthIgnoreHandler extends AuthIgnoreHandler {
        @Autowired
        private RequestMappingHandlerMapping requestMappingHandlerMapping;

        @Override
        public Set<String> getAuthIgnoreUrl() {
            if (CollectionUtils.isEmpty(this.authIgnoreUrlList)) {
                this.authIgnoreUrlList = requestMappingHandlerMapping
                        .getHandlerMethods()
                        .entrySet()
                        .stream()
                        .flatMap((entry) -> {
                            AuthIgnore annotation = entry.getValue().getMethodAnnotation(AuthIgnore.class);
                            if (Objects.isNull(annotation)) {
                                return Stream.empty();
                            }
                            return new HashSet<>(entry.getKey().getPatternsCondition().getPatterns()).stream();
                        })
                        .collect(Collectors.toSet());
            }

            log.info("加载忽略权限认证url {}", JsonUtil.json(this.authIgnoreUrlList));
            return this.authIgnoreUrlList;
        }
    }

    /**
     * webFlux 忽略鉴权 url
     */
    @Slf4j
    public static class WebFluxAuthIgnoreHandler extends AuthIgnoreHandler {

        @Autowired
        private org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping requestMappingHandlerMapping;

        @Override
        public Set<String> getAuthIgnoreUrl() {
            if (CollectionUtils.isEmpty(this.authIgnoreUrlList)) {
                Set<String> collect = requestMappingHandlerMapping
                        .getHandlerMethods()
                        .entrySet()
                        .stream()
                        .flatMap((entry) -> {
                            AuthIgnore annotation = entry.getValue().getMethodAnnotation(AuthIgnore.class);
                            if (Objects.isNull(annotation)) {
                                return Stream.empty();
                            }
                            return entry.getKey().getPatternsCondition().getPatterns().stream().map(PathPattern::getPatternString).collect(Collectors.toSet()).stream();
                        })
                        .collect(Collectors.toSet());
            }
            log.info("加载忽略权限认证url {}", JsonUtil.json(this.authIgnoreUrlList));
            return this.authIgnoreUrlList;
        }
    }
}
