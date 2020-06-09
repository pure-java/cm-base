package com.github.pure.cm.common.core.component;

import com.github.pure.cm.common.core.IgnoreToken;
import com.github.pure.cm.common.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 忽略权限验证接口
 *
 * @author 陈欢
 * @since 2020/6/5
 */
@Slf4j
@Component
public class IgnoreFilter implements ApplicationContextAware, WebFilter {
    ApplicationContext applicationContext;
    Set<String> urlSet = new HashSet<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String uri = request.getURI().getPath();
        log.info("请求url {}", uri);

        return chain.filter(exchange);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        Map<String, Object> restController = this.applicationContext.getBeansWithAnnotation(RestController.class);
        restController.values().forEach(bean -> {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            if(targetClass.getName().contains("UserAuthController")){
                System.out.println("UserAuthController");
            }
            if (Objects.isNull(AopUtils.getTargetClass(bean).getAnnotation(RestController.class))) {
                return;
            }
            RequestMapping mappingAnnotation = AopUtils.getTargetClass(bean).getAnnotation(RequestMapping.class);
            String[] value = mappingAnnotation.value();

            for (Method method : targetClass.getMethods()) {
                if (Objects.nonNull(method.getAnnotation(IgnoreToken.class))) {
                    Set<String> methodUrls = getMethodURIS(method);
                    if (ArrayUtil.isNotEmpty(value)) {
                        Arrays.stream(value)
                                .forEach(baseUrl -> methodUrls.forEach(url -> {
                                    if (!baseUrl.endsWith("/") && !url.startsWith("/")) {
                                        url = "/" + url;
                                    }
                                    urlSet.add(baseUrl + url);
                                }));
                    }
                }
            }
        });
    }

    private static Set<String> getMethodURIS(Method m) {
        Set<String> urls = new HashSet<>();
        String[] getUris = Optional.ofNullable(m.getAnnotation(GetMapping.class)).map(GetMapping::value).orElse(null);
        if (ArrayUtil.isNotEmpty(getUris)) {
            urls.addAll(Arrays.asList(getUris));
        }
        String[] postURIS = Optional.ofNullable(m.getAnnotation(PostMapping.class)).map(PostMapping::value).orElse(null);
        if (ArrayUtil.isNotEmpty(postURIS)) {
            urls.addAll(Arrays.asList(postURIS));
        }
        String[] requestURIS = Optional.ofNullable(m.getAnnotation(RequestMapping.class)).map(RequestMapping::value).orElse(null);
        if (ArrayUtil.isNotEmpty(requestURIS)) {
            urls.addAll(Arrays.asList(requestURIS));
        }

        String[] putURIS = Optional.ofNullable(m.getAnnotation(PutMapping.class)).map(PutMapping::value).orElse(null);
        if (ArrayUtil.isNotEmpty(putURIS)) {
            urls.addAll(Arrays.asList(putURIS));
        }
        String[] deleteURIS = Optional.ofNullable(m.getAnnotation(DeleteMapping.class)).map(DeleteMapping::value).orElse(null);
        if (Objects.nonNull(deleteURIS) && deleteURIS.length > 0) {
            urls.addAll(Arrays.asList(deleteURIS));
        }
        return urls;
    }

}
