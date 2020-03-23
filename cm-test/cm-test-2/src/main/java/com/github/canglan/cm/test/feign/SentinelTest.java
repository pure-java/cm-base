package com.github.canglan.cm.test.feign;

import com.github.canglan.cm.common.core.exception.NotBreakerConfiguration;
import com.github.canglan.cm.common.core.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author bairitan
 * @date 2020/1/7
 */
@FeignClient(contextId = "sentinelTest", value = "cm-test-1", path = "/1/sentinelTest", configuration = NotBreakerConfiguration.class)
public interface SentinelTest {

  /**
   * 使用硬编码方式
   */
  @PostMapping("/sphuNtTrace")
  public Result sphuNtTrace();

  /**
   * 使用硬编码方式
   */
  @PostMapping("/sphoNtTrace")
  public Result sphoNtTrace();

  /**
   * 使用注解方式
   */
  @PostMapping("/blockSentinelResource")
  public Result blockSentinelResource();

}
