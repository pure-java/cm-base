package com.github.canglan.cm.identity.config;

import com.github.canglan.cm.common.component.AutoFillAdapt;
import com.github.canglan.cm.common.entity.SysUser;
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
  protected SysUser getCurrUser() {
    // LoginUser userDetails = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // if (userDetails != null) {
    //   return new SysUser().setUserName(userDetails.getUsername()).setOid(userDetails.getOid());
    // }
    // log.warn("anonymous login");
    return new SysUser().setOid(0L).setUserName("anonymous");
  }
}
