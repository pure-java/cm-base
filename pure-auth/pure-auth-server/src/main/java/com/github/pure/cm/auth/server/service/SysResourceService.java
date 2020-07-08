package com.github.pure.cm.auth.server.service;

import com.github.pure.cm.auth.server.model.entity.SysResource;
import com.github.pure.cm.common.data.base.IBaseService;
import com.github.pure.cm.common.data.model.PageWhere;
import com.github.pure.cm.model.auth.vo.AuthRegisterVo;

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
public interface SysResourceService extends IBaseService<SysResource> {

    /**
     * 分页查询
     *
     * @param pageWhere   分页条件
     * @param sysResource 查询条件
     * @return 分页查询结果
     */
    List<SysResource> pageIdAuthority(PageWhere pageWhere, SysResource sysResource);

    /**
     * 客户端权限注册
     *
     * @param authRegisterVo 注册内容
     */
    boolean registerAuth(AuthRegisterVo authRegisterVo);

    /**
     * 批量添加
     *
     * @param saveList 添加内容
     * @return 添加结果
     */
    boolean saveBatch(List<SysResource> saveList);

    /**
     * 添加或修改
     *
     * @param sysResource 添加或修改的对象
     * @return 添加或修改结果
     */
    boolean saveOrUpdateIdAuthority(SysResource sysResource);

    /**
     * 根据ID批量删除
     *
     * @param idList 需要删除的ID集合,不应该有重复id
     * @return 删除结果
     */
    boolean removeByIds(Set<String> idList);
}
