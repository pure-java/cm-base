package com.github.canglan.cm.identity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.cfg.AppProperties;
import com.github.canglan.cm.common.service.impl.BaseServiceImpl;
import com.github.canglan.cm.identity.entity.IdMenu;
import com.github.canglan.cm.identity.mapper.IdMenuMapper;
import com.github.canglan.cm.identity.service.IMenuService;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<IdMenuMapper, IdMenu> implements IMenuService {

  @Override
  public List<IdMenu> pageIdMenu(PageWhere pageWhere, IdMenu idMenu) {
    QueryWrapper<IdMenu> idMenuWrapper = new QueryWrapper<>();
    pageWhere.startPage();
    return super.daoUtil.list(idMenuWrapper);
  }

  @Override
  public List<IdMenu> listQueryCondition(IdMenu query) {
    QueryWrapper<IdMenu> wrapper = new QueryWrapper<>();
    return super.daoUtil.list(wrapper);
  }

  @Override
  // @Transactional(rollbackFor = Exception.class)
  public boolean saveBatch(List<IdMenu> saveList) {
    // if (CollectionUtil.isNotEmpty(saveList)) {
    //   List<List<IdMenu>> partition = Lists.partition(saveList, AppProperties.BATCH_SAVE_SIZE);
    //   for (List<IdMenu> list : partition) {
    //     super.baseMapper.saveBatch(list);
    //   }
    // }
    return super.daoUtil.saveBatch(saveList, AppProperties.BATCH_SAVE_SIZE);
  }

  @Override
  public boolean updateBatch(List<IdMenu> saveList) {
    return super.daoUtil.updateBatchById(saveList);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean saveOrUpdateIdMenu(IdMenu idMenu) {
    return super.daoUtil.saveOrUpdate(idMenu);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean removeByIds(Set<String> idList) {
    return super.daoUtil.removeByIds(idList);
  }
}
