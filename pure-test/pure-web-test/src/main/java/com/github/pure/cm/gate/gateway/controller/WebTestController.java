package com.github.pure.cm.gate.gateway.controller;

import com.github.pure.cm.common.core.model.Result;
import com.github.pure.cm.gate.gateway.feign.TestFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    TestFeign testFeign;

    /**
     * 登录，暂时放在网关，可以放到其他模块，但是需要为登录模块提供客户端授权
     *
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login() {
        return testFeign.selectMenuAuth();
    }
}
