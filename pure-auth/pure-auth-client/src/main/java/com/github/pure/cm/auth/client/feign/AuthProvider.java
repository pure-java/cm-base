package com.github.pure.cm.auth.client.feign;

import java.util.Map;

import com.github.pure.cm.auth.client.feign.failback.AuthProviderFail;
import com.github.pure.cm.common.core.exception.ApiException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 权限认证接口
 *
 * @author 陈欢
 * @since 2019/11/30
 */
@FeignClient(value = "${security.auth-service-id:auth-server}"/*, fallback = AuthProviderFail.class*/)
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
    Map<String, Object> checkToken(@RequestParam Map<String, Object> checkToken);

    /**
     * 获取token 与 刷新token
     *
     * @param client 参数
     * @return 返回结果
     */
    @PostMapping(value = "/oauth/token")
    Map<String, Object> token(@RequestParam Map<String, Object> client) throws ApiException;

}
