package com.github.canglan.cm.test.controller;

import com.github.canglan.cm.common.core.exception.InternalApiException;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.date.DateUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @date 2019/12/7
 */
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

  @PostMapping("/keepErrMsgTest")
  public Result keepErrMsgTest() throws InternalApiException {
    throw new InternalApiException("测试进入熔断，并保留异常信息");
  }

  @PostMapping("/notBreakerTest")
  public Result notBreakerTest() throws InternalApiException {
    throw new InternalApiException("测试 不进入熔断，直接抛出异常");
  }

}
