package com.github.pure.cm.auth.server.service;

import com.github.pure.cm.auth.server.model.entity.SysUser;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.common.data.base.IBaseService;
import java.util.List;
import java.util.Set;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public interface ISysUserService extends IBaseService<SysUser> {

  /**
   * 分页查询
   *
   * @param pageWhere 分页条件
   * @param sysUser 查询条件
   * @return 分页查询结果
   */
  public List<SysUser> pageIdUser(PageWhere pageWhere, SysUser sysUser);

  /**
   * 根据名称获取用户信息
   *
   * @param userName 用户名
   * @return 用户信息
   */
  public SysUser getUserByUserName(String userName);

  /**
   * 根据条件查询
   *
   * @param query 查询条件
   * @return 查询结果
   */
  public List<SysUser> listQueryCondition(SysUser query);

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   * @return 添加结果
   */
  public boolean saveBatch(List<SysUser> saveList);

  /**
   * 添加或修改
   *
   * @param sysUser 添加或修改的对象
   * @return 添加或修改结果
   */
  public boolean saveOrUpdateIdUser(SysUser sysUser);

  /**
   * 根据ID批量删除
   *
   * @param idList 需要删除的ID集合,不应该有重复id
   * @return 删除结果
   */
  public boolean removeByIds(Set<String> idList);
}
