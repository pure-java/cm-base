package com.github.canglan.cm.identity.service.impl;

import com.github.canglan.cm.identity.IdentityApplication;
import com.github.canglan.cm.identity.entity.IdMenu;
import com.github.canglan.cm.identity.service.IMenuService;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IdentityApplication.class)
public class AuthorityValidateImplTest {

  @Autowired
  private IMenuService menuService;

  @Test
  public void batchSave() {
    List<IdMenu> idMenus1 = menuService.listQueryCondition(new IdMenu());
    for (IdMenu idMenu : idMenus1) {
      idMenu.setTitle("ssssssssa");
    }
    menuService.updateBatch(idMenus1);


    List<IdMenu> idMenus = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      IdMenu idMenu = new IdMenu();
      idMenu.setPid(0L);
      idMenu.setOrderNum(1);
      idMenu.setTitle(i + "sssss");
      idMenus.add(idMenu);
    }
  }
}