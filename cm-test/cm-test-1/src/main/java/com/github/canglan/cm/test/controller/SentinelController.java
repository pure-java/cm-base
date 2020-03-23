package com.github.canglan.cm.test.controller;

import com.alibaba.csp.sentinel.AsyncEntry;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.canglan.cm.common.core.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bairitan
 * @date 2020/1/7
 */
@RestController
@RequestMapping("/1/sentinelTest")
public class SentinelController {

  /**
   * 使用硬编码方式
   */
  @PostMapping("/sphoNtTrace")
  public Result sphoNtTrace() {
    if (SphO.entry("sphoNtTrace")) {
      // 务必保证finally会被执行
      try {
        // 被保护的业务逻辑
        return Result.success("正常返回 sphoNtTrace");
      } finally {
        SphO.exit();
      }
    } else {
      // 资源访问阻止，被限流或被降级
      // 进行相应的处理操作
      return Result.fail("进入熔断 sphoNtTrace");
    }
  }

  /**
   * 使用注解方式
   */
  @PostMapping("/blockSentinelResource")
  @SentinelResource(value = "blockSentinelResource", blockHandler = "blockHandlerForGetUser")
  public Result blockSentinelResource() {
    return Result.success("正常返回 blockSentinelResource");
  }

  public Result blockHandlerForGetUser(BlockException ex) {
    System.out.println(ex);
    return Result.fail("进入熔断 blockSentinelResource");
  }

  /**
   * 使用硬编码方式
   */
  @PostMapping("/sphuNtTrace")
  public Result sphuNtTrace() {
    this.someAsync();
    Entry entry = null;
    ContextUtil.enter("enterA", "Test1");
    try {
      entry = SphU.entry("SphutTrace1", 1);
      return Result.success("正常 SphutTrace  ");
    } catch (BlockException e) {
      return Result.fail("进入 SphutTrace 熔断 ");
    } catch (Exception e) {
      // 记录异常信息，使用注解方式则不需要手动记录异常信息
      Tracer.traceEntry(e, entry);
    } finally {
      if (entry != null) {
        entry.exit(1);
      }
    }
    ContextUtil.exit();

    ContextUtil.enter("enterB", "Test2");
    try {
      entry = SphU.entry("SphutTrace2", 1);
      return Result.success("正常 SphutTrace  ");
    } catch (BlockException e) {
      return Result.fail("进入 SphutTrace 熔断 ");
    } catch (Exception e) {
      // 记录异常信息，使用注解方式则不需要手动记录异常信息
      Tracer.traceEntry(e, entry);
    } finally {
      if (entry != null) {
        entry.exit(1);
      }
    }
    ContextUtil.exit();
    return Result.success();
  }

  public void someAsync() {
    ContextUtil.enter("someAsyncA", "someAsyncA");
    try {
      AsyncEntry entry = SphU.asyncEntry("someAsync");
      // Asynchronous invocation.
      new Thread(() -> {
        // 在异步回调中进行上下文变换，通过 AsyncEntry 的 getAsyncContext 方法获取异步 Context
        ContextUtil.runOnContext(entry.getAsyncContext(), () -> {
          try {
            // 此处嵌套正常的资源调用.
            handleResult();
          } finally {
            entry.exit();
          }
        });
      }).start();
    } catch (BlockException ex) {
      // Request blocked.
      // Handle the exception (e.g. retry or fallback).
    }
    ContextUtil.exit();
  }


  public void handleResult() {
    ContextUtil.enter("handleResultForAsync", "someAsync");
    Entry entry = null;
    try {
      entry = SphU.entry("handleResultForAsync");
      // Handle your result here.
    } catch (BlockException ex) {
      // Blocked for the result handler.
    } finally {
      if (entry != null) {
        entry.exit();
      }
    }
    ContextUtil.exit();
  }
}
