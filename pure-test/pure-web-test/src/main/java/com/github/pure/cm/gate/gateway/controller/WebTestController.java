package com.github.pure.cm.gate.gateway.controller;

import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户验证控制类
 *
 * @author bairitan
 * @date 2019/12/9
 */
@Slf4j
@RestController
@RequestMapping("/web/test")
public class WebTestController {

    /**
     * 登录，暂时放在网关，可以放到其他模块，但是需要为登录模块提供客户端授权
     *
     * @return 登录结果
     */
    @PostMapping("/login")
    //@IgnoreAuth
    public Result<Map<String, Object>> login() throws BusinessException {
        Map<String, Object> token;
        try {
            //LoginUserVo.builder().
            int i = 1 / 0;
            //token = authService.token(reqJwtTokenParam);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return Result.success();
    }
}
