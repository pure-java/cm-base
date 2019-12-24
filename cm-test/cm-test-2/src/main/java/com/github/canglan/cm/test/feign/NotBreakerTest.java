package com.github.canglan.cm.test.feign;

import com.github.canglan.cm.common.core.exception.ApiException;
import com.github.canglan.cm.common.core.exception.NotBreakerConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@FeignClient(contextId = "notBreakerTest", value = "cm-test-1", path = "/1/test", configuration = NotBreakerConfiguration.class)
public interface NotBreakerTest {

  @RequestMapping(method = RequestMethod.POST, value = "/notBreakerTest")
  public String notBreakerTest() throws ApiException;
}
