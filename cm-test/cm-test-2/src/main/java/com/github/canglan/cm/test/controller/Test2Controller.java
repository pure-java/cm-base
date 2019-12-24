package com.github.canglan.cm.test.controller;

import com.github.canglan.cm.auth.client.service.AuthService;
import com.github.canglan.cm.common.core.exception.ApiException;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.date.DateUtil;
import com.github.canglan.cm.test.feign.KeepErrMsgTest;
import com.github.canglan.cm.test.feign.NotBreakerTest;
import com.github.canglan.cm.test.feign.defExceptionTest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @date 2019/12/7
 */
@RestController
@RequestMapping("/2/test")
public class Test2Controller {

  @Autowired
  private defExceptionTest defExceptionTest;
  @Autowired
  private NotBreakerTest notBreakerTest;
  @Autowired
  private KeepErrMsgTest keepErrMsgTest;
  @Autowired
  private AuthService authService;

  @PostMapping("/getUserInfo")
  public Result getUserInfo(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    Jwt jwt = authService.decodeAndVerify(token);
    // jwt.
    return Result.success();
  }

  @PostMapping("/callService")
  public Result callService(@RequestParam("testParam") String testParam) {
    return Result.success(" 调用时间 = " + DateUtil.newIns().asStr()).setData(testParam);
  }

  @PostMapping("/serviceCallService")
  public Result serviceCallService(@RequestParam("testParam") String testParam) {
    String s = defExceptionTest.test();
    return Result.success(" 调用时间 = " + DateUtil.newIns().asStr() + ", 内部服务调用结果 = " + s).setData(testParam);
  }

  @PostMapping("/defExceptionTest")
  public Result exceptionTest() throws ApiException {
    defExceptionTest.defExceptionTest();
    return Result.success();
  }

  @PostMapping("/keepErrMsgTest")
  public Result keepErrMsgTest() throws ApiException {
    keepErrMsgTest.keepErrMsgTest();
    return Result.success();
  }

  @PostMapping("/notBreakerTest")
  public Result notBreakerTest() throws ApiException {
    notBreakerTest.notBreakerTest();
    return Result.success();
  }
}
