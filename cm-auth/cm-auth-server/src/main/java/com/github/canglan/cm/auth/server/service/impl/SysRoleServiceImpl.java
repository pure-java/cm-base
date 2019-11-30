package com.github.canglan.cm.auth.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.canglan.cm.auth.server.model.entity.SysRole;
import com.github.canglan.cm.auth.server.mapper.SysRoleMapper;
import com.github.canglan.cm.auth.server.service.ISysRoleService;
import com.github.canglan.cm.common.data.model.PageWhere;
import com.github.canglan.cm.common.data.constants.AppConstants;
import com.github.canglan.cm.common.data.base.BaseServiceImpl;
import com.github.canglan.cm.common.util.collection.CollectionUtil;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

  @Override
  public List<SysRole> pageIdRole(PageWhere pageWhere, SysRole sysRole) {
    QueryWrapper<SysRole> idRoleWrapper = new QueryWrapper<>();
    pageWhere.startPage();
    return super.daoUtil.list(idRoleWrapper);
  }

  @Override
  public List<SysRole> listQueryCondition(SysRole query) {
    QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
    return super.daoUtil.list(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public boolean saveBatch(List<SysRole> saveList) {
    if (CollectionUtil.isNotEmpty(saveList)) {
      List<List<SysRole>> partition = Lists.partition(saveList, AppConstants.BATCH_SAVE_SIZE);
      for (List<SysRole> list : partition) {
        super.baseMapper.saveBatch(list);
      }
    }
    return true;
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public boolean saveOrUpdateIdRole(SysRole sysRole) {
    return super.daoUtil.saveOrUpdate(sysRole);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public boolean removeByIds(Set<String> idList) {
    return super.daoUtil.removeByIds(idList);
  }
}
