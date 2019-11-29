package com.github.canglan.cm.auth.server.mapper;

import com.github.canglan.cm.auth.server.entity.IdAuthority;
import com.github.canglan.cm.common.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chenhuan
 * @since 2019-11-20
 */
public interface IdAuthorityMapper extends BaseMapper<IdAuthority> {

  /**
   * 批量添加
   *
   * @param saveList 添加内容
   */
  public void saveBatch(@Param("saveList") List<IdAuthority> saveList);
}
