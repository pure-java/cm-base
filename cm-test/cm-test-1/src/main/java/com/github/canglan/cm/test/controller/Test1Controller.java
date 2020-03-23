package com.github.canglan.cm.test.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.canglan.cm.common.core.exception.InternalApiException;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @date 2019/12/7
 */
@Slf4j
@RestController
@RequestMapping("/1/test")
public class Test1Controller {

  @RequestMapping("/data")
  public Result test() {
    return Result.success("test + " + DateUtil.newIns().asStr());
  }

  @PostMapping("/defExceptionTest")
  public Result defExceptionTest() throws InternalApiException {
    throw new InternalApiException("测试 默认抛出异常方式");
  }

  @PostMapping("/notBreakerTest")
  public Result notBreakerTest() throws InternalApiException {
    throw new InternalApiException("测试 不进入熔断，直接抛出异常");
  }

  @PostMapping("/fusingTest")
  public Result fusingTest(@RequestParam("str") String str) {
    try (Entry keepErrMsgTest = SphU.entry("keepErrMsgTest")) {
    } catch (BlockException e) {
      log.error("限流");
      return Result.success("fusingTest 限流了");
    }
    return Result.success("【 test1 正常，参数 " + str + "】");
  }

  @PostMapping("/keepErrMsgTest")
  public Result keepErrMsgTest(@RequestParam("str") String str) throws InternalApiException {

    throw new InternalApiException("抛出异常，调用端获取异常信息，并且进入熔断");
  }
}
