package com.github.canglan.cm.identity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.cfg.AppProperties;
import com.github.canglan.cm.common.service.impl.BaseServiceImpl;
import com.github.canglan.cm.common.util.collection.CollectionUtil;
import com.github.canglan.cm.identity.entity.IdUser;
import com.github.canglan.cm.identity.mapper.IdUserMapper;
import com.github.canglan.cm.identity.service.IUserService;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<IdUserMapper, IdUser> implements IUserService {

  @Override
  public List<IdUser> pageIdUser(PageWhere pageWhere, IdUser idUser) {
    QueryWrapper<IdUser> idUserWrapper = new QueryWrapper<>();
    pageWhere.startPage();
    return super.daoUtil.list(idUserWrapper);
  }


  @Override
  public List<IdUser> listQueryCondition(IdUser query) {
    QueryWrapper<IdUser> wrapper = new QueryWrapper<>();
    return super.daoUtil.list(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean saveBatch(List<IdUser> saveList) {
    if (CollectionUtil.isNotEmpty(saveList)) {
      List<List<IdUser>> partition = Lists.partition(saveList, AppProperties.BATCH_SAVE_SIZE);
      for (List<IdUser> list : partition) {
        super.baseMapper.saveBatch(list);
      }
    }
    return true;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean saveOrUpdateIdUser(IdUser idUser) {
    return super.daoUtil.saveOrUpdate(idUser);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean removeByIds(Set<String> idList) {
    return super.daoUtil.removeByIds(idList);
  }
}
