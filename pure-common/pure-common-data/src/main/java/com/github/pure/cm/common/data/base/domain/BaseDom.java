package com.github.pure.cm.common.data.base.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 基础model，只带有ID
 *
 * @param <FK> 主键
 * @param <T> 实体
 * @author bairitan
 */
@Setter
@Getter
@ToString
public abstract class BaseDom<FK extends Serializable, T extends BaseDom> implements IModel, Serializable {

  private static final long serialVersionUID = 4943817300823532768L;

  @TableId(value = "oid", type = IdType.AUTO)
  @ApiModelProperty(value = "主键", name = "oid", notes = "修改主键生成方式时必须填写")
  protected FK oid;

  public T setOid(FK oid) {
    this.oid = oid;
    return (T) this;
  }

}
