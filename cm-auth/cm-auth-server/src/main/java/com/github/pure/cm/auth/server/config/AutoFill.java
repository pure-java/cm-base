package com.github.pure.cm.auth.server.config;

import com.github.pure.cm.common.data.component.AutoFillAdapt;
import com.github.pure.cm.common.data.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 陈欢
 * @since 2019/11/21
 */
@Component
@Slf4j
public class AutoFill extends AutoFillAdapt {

  @Override
  protected UserInfo getCurrUser() {
    // LoginUser userDetails = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // if (userDetails != null) {
    //   return new SysUser().setUserName(userDetails.getUsername()).setOid(userDetails.getOid());
    // }
    // log.warn("anonymous login");
    UserInfo userInfo = new UserInfo();
    userInfo.setUserName("anonymous");
    userInfo.setOid(0L);
    return userInfo;
  }
}
