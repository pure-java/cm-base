package com.github.canglan.cm.identity.service;

import com.github.canglan.cm.common.bean.PageWhere;
import com.github.canglan.cm.common.service.IBaseService;
import com.github.canglan.cm.identity.entity.IdAuthority;
import com.github.canglan.cm.identity.entity.IdUser;
import java.util.List;
import java.util.Set;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public interface IUserService extends IBaseService<IdUser> {

  /**
   * 分页查询
   *
   * @param pageWhere 分页条件
   * @param idUser 查询条件
   * @return 分页查询结果
   */
  public List<IdUser> pageIdUser(PageWhere pageWhere, IdUser idUser);

  /**
   * 根据名称获取用户信息
   *
   * @param userName 用户名
   * @return 用户信息
   */
  public IdUser getUserByUserName(String userName);

  /**
   * 根据条件查询
   *
   * @param query 查询条件
   * @return 查询结果
   */
  public List<IdUser> listQueryCondition(IdUser query);

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   * @return 添加结果
   */
  public boolean saveBatch(List<IdUser> saveList);

  /**
   * 添加或修改
   *
   * @param idUser 添加或修改的对象
   * @return 添加或修改结果
   */
  public boolean saveOrUpdateIdUser(IdUser idUser);

  /**
   * 根据ID批量删除
   *
   * @param idList 需要删除的ID集合,不应该有重复id
   * @return 删除结果
   */
  public boolean removeByIds(Set<String> idList);
}
