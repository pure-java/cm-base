package com.github.canglan.cm.test.feign;

import com.github.canglan.cm.common.core.exception.KeepErrMsgConfiguration;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.test.feign.SentinelFusingTest.EchoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bairitan
 * @date 2019/12/31
 */
@FeignClient(contextId = "SentinelTest", value = "cm-test-1", path = "/1/test", fallback = EchoServiceFallback.class, configuration = KeepErrMsgConfiguration.class)
public interface SentinelFusingTest {

  /**
   * 熔断测试
   */
  @PostMapping(path = "/fusingTest", headers = {"content-type=text/plain;charset=UTF-8"})
  public Result fusingTest(@RequestParam("str") String str);

  @PostMapping(path = "/keepErrMsgTest", headers = {"content-type=text/plain;charset=UTF-8"})
  public Result keepErrMsgTest(@RequestParam("str") String str);

  @Component
  class EchoServiceFallback implements SentinelFusingTest {

    @Override
    public Result fusingTest(String str) {
      return Result.success("【 test2 进入熔断测试，参数 " + str + "】");
    }

    @Override
    public Result keepErrMsgTest(String str) {
      return Result.success("【 test2 进入熔断测试 fusingExceptionTest ，参数 " + str + "】");
    }
  }
}
