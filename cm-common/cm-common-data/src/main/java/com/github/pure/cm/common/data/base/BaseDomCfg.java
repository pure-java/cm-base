package com.github.pure.cm.common.data.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 实体bean的父类<br>
 * 主要用于管理添加时间、修改时间和删除标记 <p>
 * oid、addUserName、addDateTime、optUserName、optDateTime、delFlg
 *
 * @author chenhuan
 * @date 2017-06-30 22:54
 **/
@Setter
@Getter
@ToString(callSuper = true)
public abstract class BaseDomCfg<FK extends Serializable, T extends BaseDom> extends BaseDomOpt<FK, T> {

  private static final long serialVersionUID = -6597812076301104877L;
  /**
   * 数据有效标识 <br> 0-有效 1-无效 ， 查询后面会自动添加 delFlg = 0
   **/
  @ApiModelProperty(hidden = true)
  @TableField(value = "del_flg" , fill = FieldFill.INSERT)
  @TableLogic
  protected Integer delFlg;

  public T setDelFlg(Integer delFlg) {
    this.delFlg = delFlg;
    return (T) this;
  }

}
