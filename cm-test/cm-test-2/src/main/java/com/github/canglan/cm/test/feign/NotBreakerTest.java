package com.github.canglan.cm.test.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@FeignClient(contextId = "notBreakerTest", value = "cm-test-1", path = "/1/test")
public interface NotBreakerTest {

  @RequestMapping(method = RequestMethod.POST, value = "/notBreakerTest")
  public String notBreakerTest() ;
}
