package com.github.pure.cm.auth.sdk.core.feign;

import com.github.pure.cm.model.auth.vo.AuthRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 陈欢
 * @since 2020/7/8
 */
@FeignClient(name = "pure-auth-server")
public interface AuthRegisterClient {
    /**
     * 注册权限
     */
    @PostMapping("/authServer/registerAuth")
    Boolean registerAuth(@RequestBody AuthRegisterVo registerVo);
}
