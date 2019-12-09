package com.github.canglan.cm.test.controller;

import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.date.DateUtil;
import com.github.canglan.cm.test.feign.Test1Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @date 2019/12/7
 */
@RestController
@RequestMapping("/2/test")
public class Test2Controller {

  @Autowired
  private Test1Feign test1Feign;

  @RequestMapping("/data")
  public Result test() {
    String s = "调用 test 1 " + test1Feign.test();
    return Result.success(s + " = " + DateUtil.newIns().asStr());
  }
}
