package com.github.pure.cm.auth.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pure.cm.auth.server.model.entity.SysMenu;
import com.github.pure.cm.auth.server.mapper.SysMenuMapper;
import com.github.pure.cm.auth.server.service.ISysMenuService;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.common.data.constants.DatabaseConstants;
import com.github.pure.cm.common.data.base.BaseServiceImpl;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

  @Override
  public List<SysMenu> pageIdMenu(PageWhere pageWhere, SysMenu sysMenu) {
    QueryWrapper<SysMenu> idMenuWrapper = new QueryWrapper<>();
    pageWhere.startPage();
    return super.daoUtil.list(idMenuWrapper);
  }

  @Override
  public List<SysMenu> listQueryCondition(SysMenu query) {
    QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
    return super.daoUtil.list(wrapper);
  }

  @Override
  // @Transactional(rollbackFor = Exception.class)
  public boolean saveBatch(List<SysMenu> saveList) {
    // if (CollectionUtil.isNotEmpty(saveList)) {
    //   List<List<SysMenu>> partition = Lists.partition(saveList, AppConstants.BATCH_SAVE_SIZE);
    //   for (List<SysMenu> list : partition) {
    //     super.baseMapper.saveBatch(list);
    //   }
    // }
    return super.daoUtil.saveBatch(saveList, DatabaseConstants.BATCH_SAVE_SIZE);
  }


  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean saveOrUpdateIdMenu(SysMenu sysMenu) {
    return super.daoUtil.saveOrUpdate(sysMenu);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean removeByIds(Set<String> idList) {
    return super.daoUtil.removeByIds(idList);
  }
}
