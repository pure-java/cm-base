package com.github.pure.cm.manage.account.feign;

import com.github.pure.cm.common.core.exception.BusinessException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 权限认证接口
 *
 * @author 陈欢
 * @since 2019/11/30
 */
@FeignClient(value = "${pure.security.auth-service-id:uaa-server}"/*, fallback = AuthProviderFail.class*/)
public interface AuthProvider {

    /**
     * 获取auth公钥
     *
     * @return rsa public
     */
    @GetMapping("/oauth/token_key")
    String publicTokenKey();

    /**
     * 验证to
     *
     * @param checkToken 参数
     * @return 返回检查结果
     */
    @PostMapping(value = "/oauth/check_token")
    Map<String, Object> checkToken(@RequestParam Map<String, Object> checkToken,
                                   @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorization
    );

    /**
     * 获取token 与 刷新token
     *
     * @param client    参数
     * @param basicAuth basic ：base64加密后的用户名
     * @return 返回结果
     */
    @PostMapping(value = "/oauth/token")
    Map<String, Object> token(@RequestParam Map<String, Object> client, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String basicAuth) throws BusinessException;

}
