package com.github.canglan.cm.auth.server.mapper;

import com.github.canglan.cm.auth.server.model.entity.SysMenu;
import com.github.canglan.cm.common.data.base.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author 陈欢
 * @since 2019/11/20
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   */
  public void saveBatch(@Param("saveList") List<SysMenu> saveList);
}
