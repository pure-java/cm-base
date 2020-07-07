package com.github.pure.cm.auth.client.filter;

import com.github.pure.cm.common.core.util.StringUtil;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 使用 feign 时将请求头 token 转发
 *
 * @author bairitan
 * @date 2019/12/9
 */
@Slf4j
//@Component
public class FeignAuthRequestInterceptor implements RequestInterceptor {

    public FeignAuthRequestInterceptor() {
        log.debug(" =================  初始化 feign 验证拦截器 =========================");
    }

    @Override
    public void apply(RequestTemplate template) {
        Request request = template.request();

        log.warn("feign 验证：{}", request.url());
        Collection<String> authList = request.headers().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authList)) {
            return;
        }
        ArrayList<Object> collection = new ArrayList<>(authList);

        log.debug("token = {}", collection);
        String authorization = String.valueOf(collection.get(0));
        if (StringUtil.isNotBlank(authorization)) {
            template.header(HttpHeaders.AUTHORIZATION, authorization);
        }
    }

}
