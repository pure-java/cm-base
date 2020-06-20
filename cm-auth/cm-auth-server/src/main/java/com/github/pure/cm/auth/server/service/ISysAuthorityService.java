package com.github.pure.cm.auth.server.service;

import com.github.pure.cm.auth.server.model.entity.SysAuthority;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.common.data.base.IBaseService;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chenhuan
 * @since 2019-11-20
 */
public interface ISysAuthorityService extends IBaseService<SysAuthority> {

  /**
   * 分页查询
   *
   * @param pageWhere 分页条件
   * @param sysAuthority 查询条件
   * @return 分页查询结果
   */
  public List<SysAuthority> pageIdAuthority(PageWhere pageWhere, SysAuthority sysAuthority);

  /**
   * 根据条件查询
   *
   * @param query 查询条件
   * @return 查询结果
   */
  public List<SysAuthority> listQueryCondition(SysAuthority query);

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   * @return 添加结果
   */
  public boolean saveBatch(List<SysAuthority> saveList);

  /**
   * 添加或修改
   *
   * @param sysAuthority 添加或修改的对象
   * @return 添加或修改结果
   */
  public boolean saveOrUpdateIdAuthority(SysAuthority sysAuthority);

  /**
   * 根据ID批量删除
   *
   * @param idList 需要删除的ID集合,不应该有重复id
   * @return 删除结果
   */
  public boolean removeByIds(Set<String> idList);
}
