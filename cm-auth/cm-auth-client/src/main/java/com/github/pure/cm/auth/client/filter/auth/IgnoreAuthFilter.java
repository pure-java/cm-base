package com.github.pure.cm.auth.client.filter.auth;

import com.github.pure.cm.auth.client.annotation.IgnoreAuth;
import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.common.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.pattern.PathPatternParser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 忽略权限验证接口，并打印请求url与参数；支持spring mvc 与 spring webflux
 *
 * @author 陈欢
 * @since 2020/6/5
 */
@Slf4j
public class IgnoreAuthFilter implements ApplicationContextAware {

    private Set<String> ignoreAuthUrlSet = new HashSet<>();

    private final PathPatternParser pathPatternParser = new PathPatternParser();

    /**
     * 请求url 是否与 不需要进行身份认证的 url 匹配
     *
     * @param requestUrl 请求url
     * @return 是否不需要权限认证
     */
    protected boolean isIgnoreAuth(String requestUrl) {
        for (String ignoreUrl : ignoreAuthUrlSet) {
            if (pathPatternParser.parse(ignoreUrl).matches(PathContainer.parsePath(requestUrl))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 没有访问
     *
     * @return
     */
    protected Result<Object> unauthorized() {
        return Result.fail("没有访问权限").setCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> restController = applicationContext.getBeansWithAnnotation(RestController.class);
        restController.values().forEach(bean -> {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            if (Objects.isNull(AopUtils.getTargetClass(bean).getAnnotation(RestController.class))) {
                return;
            }
            RequestMapping mappingAnnotation = AopUtils.getTargetClass(bean).getAnnotation(RequestMapping.class);
            String[] value = mappingAnnotation.value();

            for (Method method : targetClass.getMethods()) {
                if (Objects.nonNull(method.getAnnotation(IgnoreAuth.class))) {
                    Set<String> methodUrls = getMethodURIS(method);
                    if (ArrayUtil.isNotEmpty(value)) {
                        Arrays.stream(value)
                                .forEach(baseUrl -> methodUrls.forEach(url -> {
                                    if (!baseUrl.endsWith("/") && !url.startsWith("/")) {
                                        url = "/" + url;
                                    }
                                    ignoreAuthUrlSet.add(baseUrl + url);
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
