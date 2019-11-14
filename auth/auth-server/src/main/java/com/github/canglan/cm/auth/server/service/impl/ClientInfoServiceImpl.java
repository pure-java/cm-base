package com.github.canglan.cm.auth.server.service.impl;

import com.github.canglan.cm.auth.server.entity.ClientInfo;
import com.github.canglan.cm.auth.server.mapper.ClientInfoMapper;
import com.github.canglan.cm.auth.server.service.IClientInfoService;
import com.github.canglan.cm.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author bairitan
 * @date 2019/11/13
 */
@Service
public class ClientInfoServiceImpl extends BaseServiceImpl<ClientInfoMapper, ClientInfo> implements IClientInfoService {

  @Override
  public boolean insert(ClientInfo clientInfo) throws Exception {
    return this.daoBridge.save(clientInfo);
  }

  @Override
  public boolean updateById(ClientInfo clientInfo) throws Exception {
    return this.daoBridge.updateById(clientInfo);
  }
}
