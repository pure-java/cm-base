package com.github.canglan.cm.common.data.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公共属性ID
 *
 * @author chenhuan
 * @since 2017-07-03 16:44
 **/
@Setter
@Getter
@ToString(callSuper = true)
public abstract class BaseDomOpt<FK extends Serializable, T extends BaseDom> extends BaseDom<FK, T> {

  /**
   * 添加该记录的用户ID
   */
  @ApiModelProperty(hidden = true)
  @TableField(fill = FieldFill.INSERT)
  protected Long addUserId;
  /**
   * 添加该记录的用户名
   */
  @ApiModelProperty(hidden = true)
  @TableField(fill = FieldFill.INSERT)
  protected String addUserName;

  /**
   * 该记录被添加的时间
   */
  @ApiModelProperty(hidden = true)
  @TableField(fill = FieldFill.INSERT)
  @JsonSerialize(using = DateTimeSerializer.class)
  protected Date addDateTime;
  /**
   * 最近修改该记录的用户ID
   */
  @ApiModelProperty(hidden = true)
  @TableField(fill = FieldFill.UPDATE)
  protected Long optUserId;
  /**
   * 最近操作该记录的用户名称
   */
  @ApiModelProperty(hidden = true)
  @TableField(fill = FieldFill.UPDATE)
  protected String optUserName;

  /**
   * 记录最近一次编辑该记录的时间
   */
  @ApiModelProperty(hidden = true)
  @TableField(fill = FieldFill.UPDATE)
  @JsonSerialize(using = DateTimeSerializer.class)
  protected Date optDateTime;

  public T setAddUserId(Long addUserId) {
    this.addUserId = addUserId;
    return (T) this;
  }

  public T setAddUserName(String addUserName) {
    this.addUserName = addUserName;
    return (T) this;
  }

  public T setAddDateTime(Date addDateTime) {
    this.addDateTime = addDateTime;
    return (T) this;
  }

  public T setOptUserId(Long optUserId) {
    this.optUserId = optUserId;
    return (T) this;
  }

  public T setOptUserName(String optUserName) {
    this.optUserName = optUserName;
    return (T) this;
  }

  public T setOptDateTime(Date optDateTime) {
    this.optDateTime = optDateTime;
    return (T) this;
  }

}

class DateTimeSerializer extends JsonSerializer<Date> {

  @Override
  public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeObject(date.getTime());
  }
}