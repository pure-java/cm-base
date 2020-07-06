package com.github.pure.cm.gate.gateway.controller;

import com.github.pure.cm.common.core.exception.BusinessException;
import com.github.pure.cm.common.core.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈欢
 * @since 2020/7/6
 */
@RestController
@RequestMapping("/webFlux/feignTest")
public class WebFluxFeignTest {

    @PostMapping("/errorTest")
    public Result errorTest() {
        throw new BusinessException(501, "自定义");

        //return Result.success();
    }
}
