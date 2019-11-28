package com.github.canglan.cm.identity.service;

import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.identity.entity.IdAuthority;
import com.github.canglan.cm.common.service.IBaseService;
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
public interface IAuthorityService extends IBaseService<IdAuthority> {

  /**
   * 分页查询
   *
   * @param pageWhere 分页条件
   * @param idAuthority 查询条件
   * @return 分页查询结果
   */
  public List<IdAuthority> pageIdAuthority(PageWhere pageWhere, IdAuthority idAuthority);

  /**
   * 根据条件查询
   *
   * @param query 查询条件
   * @return 查询结果
   */
  public List<IdAuthority> listQueryCondition(IdAuthority query);

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   * @return 添加结果
   */
  public boolean saveBatch(List<IdAuthority> saveList);

  /**
   * 添加或修改
   *
   * @param idAuthority 添加或修改的对象
   * @return 添加或修改结果
   */
  public boolean saveOrUpdateIdAuthority(IdAuthority idAuthority);

  /**
   * 根据ID批量删除
   *
   * @param idList 需要删除的ID集合,不应该有重复id
   * @return 删除结果
   */
  public boolean removeByIds(Set<String> idList);
}
