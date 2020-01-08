package com.github.canglan.cm.test.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.canglan.cm.common.core.model.Result;
import com.github.canglan.cm.test.feign.SentinelTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @date 2020/1/7
 */
@RestController
@RequestMapping("/sentinelTest")
public class SentinelController {

  @Autowired
  private SentinelTest sentinelTest;

  /**
   * 使用硬编码方式
   */
  @PostMapping("/sphuNtTrace")
  public Result sphuNtTrace() {
    return sentinelTest.sphuNtTrace();
  }

  /**
   * 使用硬编码方式
   */
  @RequestMapping("/sphoNtTrace")
  public Result sphoNtTrace() {
    return sentinelTest.sphoNtTrace();
  }

  /**
   * 使用注解方式
   */
  @RequestMapping("/blockSentinelResource")
  public Result blockSentinelResource() {
    return sentinelTest.blockSentinelResource();
  }

}
