package com.github.canglan.cm.test.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bairitan
 * @date 2019/12/9
 */
@FeignClient(value = "cm-test-1", path = "/1/test")
public interface Test1Feign {


  @RequestMapping(method = RequestMethod.GET, value = "/data")
  public String test();
}
