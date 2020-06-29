package com.github.pure.cm.auth.server.controller;

import com.github.pure.cm.common.core.exception.BusinessException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @since 2019/11/19
 */
@Slf4j
@RestController
@RequestMapping("authServer")
@Api(value = "权限服务", tags = "权限服务")
public class AuthServerController {

    //@Autowired
    //private TokenStore tokenStore;

    /**
     * 通过token获取用户信息
     *
     * @param token jwt token
     * @return 用户信息
     * @throws BusinessException 未找到用户
     */
    //@ApiOperation(value = "通过token获取用户信息")
    //@PostMapping("/getUser")
    //public Result<Object> getUser(@RequestParam("token") String token) throws BusinessException {
    //    OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
    //    Authentication userAuthentication;
    //    if (Objects.isNull(oAuth2Authentication) || Objects.isNull(userAuthentication = oAuth2Authentication.getUserAuthentication())) {
    //        throw new BusinessException("未找到用户");
    //    }
    //    return Result.success(userAuthentication.getPrincipal());
    //}

}
