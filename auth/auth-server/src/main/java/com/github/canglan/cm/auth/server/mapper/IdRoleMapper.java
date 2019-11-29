package com.github.canglan.cm.auth.server.mapper;

import com.github.canglan.cm.auth.server.entity.IdRole;
import com.github.canglan.cm.common.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public interface IdRoleMapper extends BaseMapper<IdRole> {

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   */
  public void saveBatch(@Param("saveList") List<IdRole> saveList);
}
