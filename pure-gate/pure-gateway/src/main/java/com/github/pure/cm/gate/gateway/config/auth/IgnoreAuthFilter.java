package com.github.pure.cm.gate.gateway.config.auth;

import com.github.pure.cm.auth.resource.support.AuthIgnoreHandler;
import com.github.pure.cm.common.core.constants.DefExceptionCode;
import com.github.pure.cm.common.core.constants.ExceptionCode;
import com.github.pure.cm.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 忽略权限验证接口，并打印请求url与参数；支持spring mvc 与 spring webflux；
 *
 * @author 陈欢
 * @since 2020/6/5
 */
@Slf4j
public class IgnoreAuthFilter {

    private final PathPatternParser pathPatternParser = new PathPatternParser();
    @Autowired
    AuthIgnoreHandler authIgnoreHandler;

    /**
     * 请求url 是否与 不需要进行身份认证的 url 匹配
     *
     * @param requestUrl 请求url
     * @return 是否不需要权限认证
     */
    protected boolean isIgnoreAuth(String requestUrl) {
        for (String ignoreUrl : authIgnoreHandler.getAuthIgnoreUrl()) {
            if (pathPatternParser.parse(ignoreUrl).matches(PathContainer.parsePath(requestUrl))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 没有访问
     */
    protected Result<Object> unauthorized() {
        return Result.fail(DefExceptionCode.AUTH_CLIENT_UNAUTHORIZED_10401);
    }

}
