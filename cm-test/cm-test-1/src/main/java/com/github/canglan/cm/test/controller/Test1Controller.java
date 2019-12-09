package com.github.canglan.cm.test.controller;

import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.common.core.util.date.DateUtil;
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
}
