package com.github.canglan.cm.identity.service;

import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.service.IBaseService;
import com.github.canglan.cm.identity.entity.IdMenu;
import java.util.List;
import java.util.Set;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public interface IMenuService extends IBaseService<IdMenu> {

  /**
   * 分页查询
   *
   * @param pageWhere 分页条件
   * @param idMenu 查询条件
   * @return 分页查询结果
   */
  public List<IdMenu> pageIdMenu(PageWhere pageWhere, IdMenu idMenu);

  /**
   * 根据条件查询
   *
   * @param query 查询条件
   * @return 查询结果
   */
  public List<IdMenu> listQueryCondition(IdMenu query);

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   * @return 添加结果
   */
  public boolean saveBatch(List<IdMenu> saveList);
  public boolean updateBatch(List<IdMenu> saveList);

  /**
   * 添加或修改
   *
   * @param idMenu 添加或修改的对象
   * @return 添加或修改结果
   */
  public boolean saveOrUpdateIdMenu(IdMenu idMenu);

  /**
   * 根据ID批量删除
   *
   * @param idList 需要删除的ID集合,不应该有重复id
   * @return 删除结果
   */
  public boolean removeByIds(Set<String> idList);
}
