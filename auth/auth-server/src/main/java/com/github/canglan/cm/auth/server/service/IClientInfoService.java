package com.github.canglan.cm.auth.server.service;

import com.github.canglan.cm.auth.server.entity.ClientInfo;
import com.github.canglan.cm.common.service.IBaseService;

/**
 * @author bairitan
 * @since 2019/11/13
 */
public interface IClientInfoService extends IBaseService<ClientInfo> {

  /**
   * 添加
   */
  public boolean insert(ClientInfo clientInfo) throws Exception;

  /**
   * 修改
   */
  public boolean updateById(ClientInfo clientInfo) throws Exception;
}
