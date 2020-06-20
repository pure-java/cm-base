package com.github.pure.cm.common.data.model;

import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 查询结果返回类
 *
 * @author admin
 * @create 2017-07-19 16:35
 **/
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PageResult<T> {

  /**
   * 数据
   */
  private List<T> data;
  /**
   * 总行数
   */
  private Integer total;

  public PageResult() {
  }

  public PageResult(Integer total, List<T> rows) {
    this.setData(rows);
    this.setTotal(total);
  }

  public PageResult(Long total, List<T> rows) {
    this.setData(rows);
    this.setTotal(total.intValue());
  }

  public static <T> PageResult<T> of(Integer total, List<T> data) {
    return new PageResult<T>(total, data);
  }

  public static <T> PageResult<T> of(PageInfo<T> pageInfo) {
    return new PageResult<T>(pageInfo.getTotal(), pageInfo.getList());
  }

  public static <T> PageResult<T> of(List<T> data) {
    return PageResult.of(PageInfo.of(data));
  }


}
