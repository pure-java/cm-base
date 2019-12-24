package com.github.canglan.cm.test.feign;

import com.github.canglan.cm.common.core.exception.ApiException;
import com.github.canglan.cm.common.core.exception.KeepErrMsgConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@FeignClient(contextId = "keepErrMsgTest",value = "cm-test-1", path = "/1/test", configuration = KeepErrMsgConfiguration.class)
public interface KeepErrMsgTest {

  @RequestMapping(method = RequestMethod.POST, value = "/keepErrMsgTest")
  public String keepErrMsgTest() throws ApiException;
}
