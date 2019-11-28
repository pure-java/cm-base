package com.github.canglan.cm.identity.service.impl;

import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.cfg.AppProperties;
import com.github.canglan.cm.common.util.collection.CollectionUtil;
import com.github.canglan.cm.identity.entity.IdAuthority;
import com.github.canglan.cm.identity.mapper.IdAuthorityMapper;
import com.github.canglan.cm.identity.service.IAuthorityService;
import com.github.canglan.cm.common.service.impl.BaseServiceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenhuan
 * @since 2019-11-20
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl<IdAuthorityMapper, IdAuthority>implements IAuthorityService {

  @Override
  public List<IdAuthority>pageIdAuthority(PageWhere pageWhere, IdAuthority idAuthority){
    QueryWrapper<IdAuthority> idAuthorityWrapper=new QueryWrapper<>();
    pageWhere.startPage();
    return super.daoUtil.list(idAuthorityWrapper);
  }

  @Override
  public List<IdAuthority>listQueryCondition(IdAuthority query){
    QueryWrapper<IdAuthority>wrapper=new QueryWrapper<>();
    return super.daoUtil.list(wrapper);
  }

  @Override
  @Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
  public boolean saveBatch(List<IdAuthority>saveList){
    if(CollectionUtil.isNotEmpty(saveList)){
      List<List<IdAuthority>>partition= Lists.partition(saveList, AppProperties.BATCH_SAVE_SIZE);
      for(List<IdAuthority>list:partition){
        super.baseMapper.saveBatch(list);
      }
    }
    return true;
  }

  @Override
  @Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
  public boolean saveOrUpdateIdAuthority(IdAuthority idAuthority){
    return super.daoUtil.saveOrUpdate(idAuthority);
  }

  @Override
  @Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
  public boolean removeByIds(Set<String> idList){
    return super.daoUtil.removeByIds(idList);
  }
}
