package com.github.canglan.cm.test.feign;

import com.github.canglan.cm.common.core.exception.ApiException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bairitan
 * @date 2019/12/9
 */
@FeignClient(contextId = "defExceptionTest",value = "cm-test-1", path = "/1/test")
public interface defExceptionTest {

  @RequestMapping(method = RequestMethod.GET, value = "/data")
  public String test();

  @RequestMapping(method = RequestMethod.POST, value = "/defExceptionTest")
  public String defExceptionTest() throws ApiException;
}
