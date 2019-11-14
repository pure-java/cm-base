package com.github.canglan.cm.auth.server.service.impl;

import com.github.canglan.cm.auth.server.AuthServerApplication;
import com.github.canglan.cm.auth.server.entity.ClientInfo;
import com.github.canglan.cm.auth.server.mapper.ClientInfoMapper;
import com.github.canglan.cm.common.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServerApplication.class)
@Service
public class ClientInfoServiceImplTest extends BaseServiceImpl<ClientInfoMapper, ClientInfo> {

  @Autowired
  private ClientInfoMapper clientInfoService;

  @Test
  public void insertClient() throws Exception {
    ClientInfo clientInfo = new ClientInfo().setOid(20).setName("测试修改");
    Assert.assertEquals("修改成功", 1, clientInfoService.updateById(clientInfo));
    Assert.assertEquals("添加成功", 1, clientInfoService.insert(new ClientInfo().setName("测试添加")));
  }
}

