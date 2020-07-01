package com.github.pure.cm.auth.server.mapper;

import com.github.pure.cm.auth.server.model.entity.SysRole;
import com.github.pure.cm.common.data.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 批量添加
     *
     * @param saveList 添加内容
     */
    public void saveBatch(@Param("saveList") List<SysRole> saveList);


    /**
     * 查询所有角色的菜单 和 菜单的权限
     * 角色 -> 菜单 -> 权限
     *
     * @return 角色拥有的菜单和权限
     */
    public List<SysRole> selectMenuAuth();
}
