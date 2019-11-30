package com.github.canglan.cm.auth.server.service;

import com.github.canglan.cm.auth.server.model.entity.SysMenu;
import com.github.canglan.cm.common.data.model.PageWhere;
import com.github.canglan.cm.common.data.base.IBaseService;
import java.util.List;
import java.util.Set;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public interface ISysMenuService extends IBaseService<SysMenu> {

  /**
   * 分页查询
   *
   * @param pageWhere 分页条件
   * @param sysMenu 查询条件
   * @return 分页查询结果
   */
  public List<SysMenu> pageIdMenu(PageWhere pageWhere, SysMenu sysMenu);

  /**
   * 根据条件查询
   *
   * @param query 查询条件
   * @return 查询结果
   */
  public List<SysMenu> listQueryCondition(SysMenu query);

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   * @return 添加结果
   */
  public boolean saveBatch(List<SysMenu> saveList);

  /**
   * 添加或修改
   *
   * @param sysMenu 添加或修改的对象
   * @return 添加或修改结果
   */
  public boolean saveOrUpdateIdMenu(SysMenu sysMenu);

  /**
   * 根据ID批量删除
   *
   * @param idList 需要删除的ID集合,不应该有重复id
   * @return 删除结果
   */
  public boolean removeByIds(Set<String> idList);
}
