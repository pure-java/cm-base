package com.github.pure.cm.auth.server.mapper;

import com.github.pure.cm.auth.server.model.entity.SysRole;
import com.github.pure.cm.common.data.base.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   */
  public void saveBatch(@Param("saveList") List<SysRole> saveList);
}
