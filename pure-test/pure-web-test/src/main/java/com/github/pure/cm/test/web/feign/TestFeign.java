package com.github.pure.cm.test.web.feign;

import com.github.pure.cm.common.core.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 陈欢
 * @since 2020/7/3
 */
@FeignClient(name = "pure-webflux-test")
public interface TestFeign {
    @PostMapping("webFlux/feignTest/errorTest")
    public Result selectMenuAuth();
}
