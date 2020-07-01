package com.github.pure.cm.auth.server.service;

import com.github.pure.cm.auth.server.model.entity.SysRole;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.common.data.base.IBaseService;
import java.util.List;
import java.util.Set;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public interface ISysRoleService extends IBaseService<SysRole> {

  /**
   * 分页查询
   *
   * @param pageWhere 分页条件
   * @param sysRole 查询条件
   * @return 分页查询结果
   */
  public List<SysRole> pageIdRole(PageWhere pageWhere, SysRole sysRole);

  List<SysRole> selectMenuAuth();
  /**
   * 根据条件查询
   *
   * @param query 查询条件
   * @return 查询结果
   */
  public List<SysRole> listQueryCondition(SysRole query);

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   * @return 添加结果
   */
  public boolean saveBatch(List<SysRole> saveList);

  /**
   * 添加或修改
   *
   * @param sysRole 添加或修改的对象
   * @return 添加或修改结果
   */
  public boolean saveOrUpdateIdRole(SysRole sysRole);

  /**
   * 根据ID批量删除
   *
   * @param idList 需要删除的ID集合,不应该有重复id
   * @return 删除结果
   */
  public boolean removeByIds(Set<String> idList);
}
