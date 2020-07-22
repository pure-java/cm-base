package com.github.pure.cm.auth.sdk.core.support;

import com.github.pure.cm.model.auth.vo.AuthVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限处理器<br>
 * spring web 使用
 *
 * @author 陈欢
 * @since 2020/7/2
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxAuthHandlerComponent extends AuthHandlerComponent {
    @Autowired
    private RequestMappingHandlerMapping requestMapping;

    @Override
    public Set<RequestMappingVO> getRequestMappingInfo(AuthVo authVo) {
        Set<RequestMappingVO> mappingVOS = requestMapping
                .getHandlerMethods()
                .entrySet()
                .stream()
                .map(entry -> {
                    String methodUrl = entry.getKey().getPatternsCondition().getPatterns().stream().findFirst().map(PathPattern::getPatternString).orElse("");

                    return RequestMappingVO.builder()
                            .methodUrl(methodUrl)
                            .beanType(entry.getValue().getBeanType())
                            .method(entry.getValue().getMethod())
                            .build();
                }).collect(Collectors.toSet());
        return mappingVOS;
    }

}
