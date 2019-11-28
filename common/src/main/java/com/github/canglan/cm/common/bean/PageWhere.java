package com.github.canglan.cm.common.bean;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.canglan.cm.common.util.StringUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 分页数据
 *
 * @author admin
 * @create 2017-07-19 11:21
 **/
@Setter
@Getter
@Accessors(chain = true)
@ApiModel(value = "分页对象", description = "page页码,rows/limit 每一页行数,sort排序字段,order 排序规则 值为desc/asc")
public class PageWhere {

  @ApiModelProperty(value = "页码，从1开始", name = "page", dataType = "int", required = true)
  private Integer page = 1;

  @ApiModelProperty(value = "每一页行数", name = "rows", dataType = "int", required = true)
  private Integer pageSize = 20;

  @ApiModelProperty(value = "起始行", name = "startRow", dataType = "int", hidden = true)
  private int startRow;

  @ApiModelProperty(value = "末行", name = "endRow", dataType = "int", hidden = true)
  private int endRow;

  @ApiModelProperty(value = "排序field，排序字段", name = "sort", dataType = "string")
  private String sort;

  @ApiModelProperty(value = "排序规则", name = "order", dataType = "string", notes = "倒序 desc，正序 = asc")
  private String order = "desc";

  @ApiModelProperty(hidden = true)
  private static final Integer MAX_PAGE_SIZE = 500;


  public boolean isAsc() {
    return "ASC".equalsIgnoreCase(order);
  }

  /**
   * 设置默认查询字段，当查询规则为空时，默认设置为  <code>sortField</code> 倒序排序 <br>
   * 使用 mybatisPlus 进行查询，需要设置 {@link Wrapper}
   */
  public PageWhere defaultOrder(String sortField, String order) {
    boolean isBlank = StringUtil.isBlank(this.order) || StringUtil.isBlank(this.sort);
    if (isBlank || Objects.equals("oid", this.sort)) {
      this.sort = sortField;
      this.order = order;
    }
    return this;
  }

  /**
   * 获取页码
   */
  public Integer getPage() {
    return page;
  }

  /**
   * 获取每一页行数,如果pageSize 为空，则获取用户默认行数
   */
  public Integer getPageSize() {
    return getPageSize(20);
  }

  public Integer getPageSize(Integer integer) {
    if (integer == null) {
      pageSize = integer;
    }
    return pageSize;
  }

  public int getStartRow() {
    this.calculateStartAndEndRow();
    return startRow;
  }

  public int getEndRow() {
    this.calculateStartAndEndRow();
    return endRow;
  }

  /**
   * 设置排序字段与排序规则
   */
  @JsonIgnore
  public <T> PageWhere orderBy(QueryWrapper<T> wrapper) {
    wrapper.orderBy(true, this.isAsc(), this.sort);
    return this;
  }

  /**
   * 获取查询条件，设置了排序规则和排序字段
   */
  @JsonIgnore
  public <T> QueryWrapper<T> wrapper() {
    QueryWrapper<T> wrapper = new QueryWrapper<>();
    wrapper.orderBy(true, this.isAsc(), this.sort);
    return wrapper;
  }

  /**
   * 分页查询，page和pageSize必须设置
   */
  public PageWhere startPage() {
    Objects.requireNonNull(this.getPage(), "页码不能为空");
    Objects.requireNonNull(pageSize, "每页行数不能为空");
    // if (pageSize > this.MAX_PAGE_SIZE) {
    //   pageSize = this.MAX_PAGE_SIZE;
    // }
    PageHelper.startPage(this.getPage(), pageSize);
    return this;
  }

  /**
   * 计算起止行号
   */
  private void calculateStartAndEndRow() {
    this.startRow = this.page > 0 ? (this.page - 1) * this.pageSize : 0;
    this.endRow = this.startRow + this.pageSize * (this.page > 0 ? 1 : 0);
  }


  @Override
  public String toString() {
    return "PageWhere{" +
        "page=" + page +
        ", pageSize=" + pageSize +
        ", sort='" + sort + '\'' +
        ", order='" + order + '\'' +
        '}';
  }
}
