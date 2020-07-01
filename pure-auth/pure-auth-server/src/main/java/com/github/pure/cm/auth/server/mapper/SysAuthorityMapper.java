package com.github.pure.cm.auth.server.mapper;

import com.github.pure.cm.auth.server.model.entity.SysAuthority;
import com.github.pure.cm.common.data.base.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chenhuan
 * @since 2019-11-20
 */
@Mapper
public interface SysAuthorityMapper extends BaseMapper<SysAuthority> {

    /**
     * 批量添加
     *
     * @param saveList 添加内容
     */
    public void saveBatch(@Param("saveList") List<SysAuthority> saveList);
}
