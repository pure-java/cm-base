package com.github.pure.cm.auth.sdk.core.support.reg;

import com.github.pure.cm.auth.sdk.core.support.AuthRegHandlerComponent;
import com.github.pure.cm.model.auth.vo.AuthVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
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
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebAuthHandlerComponent extends AuthRegHandlerComponent {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public Set<RequestMappingVO> getRequestMappingInfo(AuthVo authVo) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        return
                handlerMethods.entrySet().stream().map(entry -> {
                    String methodUrl = entry.getKey().getPatternsCondition().getPatterns().stream().findFirst().orElse("");
                    // bean类型
                    Class<?> beanType = entry.getValue().getBeanType();
                    Method method = entry.getValue().getMethod();

                    return RequestMappingVO.builder().beanType(beanType).methodUrl(methodUrl).method(method).build();

                }).collect(Collectors.toSet());
    }

}
