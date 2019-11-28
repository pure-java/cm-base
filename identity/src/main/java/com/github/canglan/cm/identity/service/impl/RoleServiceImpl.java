package com.github.canglan.cm.identity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.cfg.AppProperties;
import com.github.canglan.cm.common.service.impl.BaseServiceImpl;
import com.github.canglan.cm.common.util.collection.CollectionUtil;
import com.github.canglan.cm.identity.entity.IdRole;
import com.github.canglan.cm.identity.mapper.IdRoleMapper;
import com.github.canglan.cm.identity.service.IRoleService;
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
public class RoleServiceImpl extends BaseServiceImpl<IdRoleMapper, IdRole> implements IRoleService {

  @Override
  public List<IdRole> pageIdRole(PageWhere pageWhere, IdRole idRole){
    QueryWrapper<IdRole> idRoleWrapper=new QueryWrapper<>();
    pageWhere.startPage();
    return super.daoUtil.list(idRoleWrapper);
  }

  @Override
  public List<IdRole>listQueryCondition(IdRole query){
    QueryWrapper<IdRole>wrapper=new QueryWrapper<>();
    return super.daoUtil.list(wrapper);
  }

  @Override
  @Transactional(rollbackFor=Exception.class,propagation= Propagation.REQUIRED)
  public boolean saveBatch(List<IdRole>saveList){
    if(CollectionUtil.isNotEmpty(saveList)){
      List<List<IdRole>>partition= Lists.partition(saveList, AppProperties.BATCH_SAVE_SIZE);
      for(List<IdRole>list:partition){
        super.baseMapper.saveBatch(list);
      }
    }
    return true;
  }

  @Override
  @Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
  public boolean saveOrUpdateIdRole(IdRole idRole){
    return super.daoUtil.saveOrUpdate(idRole);
  }

  @Override
  @Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
  public boolean removeByIds(Set<String> idList){
    return super.daoUtil.removeByIds(idList);
  }
}
