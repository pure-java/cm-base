package com.github.canglan.cm.auth.client.interceptor;

import com.github.canglan.cm.common.core.util.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author bairitan
 * @date 2019/12/9
 */
@Slf4j
public class FeignAuthRequestInterceptor implements RequestInterceptor {

  public FeignAuthRequestInterceptor() {
    log.debug(" =================  初始化 feign 验证拦截器 =========================");
  }

  @Override
  public void apply(RequestTemplate template) {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (requestAttributes == null) {
      log.debug(" 请求为空 ===================== ");
      return;
    }
    HttpServletRequest request = requestAttributes.getRequest();
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtil.isNotBlank(authorization)) {
      template.header(HttpHeaders.AUTHORIZATION, authorization);
    }
  }

}
