package com.github.pure.cm.auth.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pure.cm.auth.server.model.entity.SysAuthority;
import com.github.pure.cm.auth.server.mapper.SysAuthorityMapper;
import com.github.pure.cm.auth.server.service.ISysAuthorityService;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.common.data.constants.DatabaseConstants;
import com.github.pure.cm.common.data.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenhuan
 * @since 2019-11-20
 */
@Service
public class SysAuthorityServiceImpl extends BaseServiceImpl<SysAuthorityMapper, SysAuthority> implements ISysAuthorityService {

  @Override
  public List<SysAuthority> pageIdAuthority(PageWhere pageWhere, SysAuthority sysAuthority) {
    QueryWrapper<SysAuthority> idAuthorityWrapper = new QueryWrapper<>();
    pageWhere.startPage();
    return super.daoUtil.list(idAuthorityWrapper);
  }

  @Override
  public List<SysAuthority> listQueryCondition(SysAuthority query) {
    QueryWrapper<SysAuthority> wrapper = new QueryWrapper<>();
    return super.daoUtil.list(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public boolean saveBatch(List<SysAuthority> saveList) {
    if (CollectionUtils.isNotEmpty(saveList)) {
      List<List<SysAuthority>> partition = Lists.partition(saveList, DatabaseConstants.BATCH_SAVE_SIZE);
      for (List<SysAuthority> list : partition) {
        super.baseMapper.saveBatch(list);
      }
    }
    return true;
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public boolean saveOrUpdateIdAuthority(SysAuthority sysAuthority) {
    return super.daoUtil.saveOrUpdate(sysAuthority);
  }

  @Override
  @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
  public boolean removeByIds(Set<String> idList) {
    return super.daoUtil.removeByIds(idList);
  }
}
