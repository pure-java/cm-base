package com.github.canglan.cm.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.canglan.cm.common.model.IModel;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @param <FK> 主键
 * @param <T> model 对象
 * @author bairitan
 */
@Setter
@Getter
@ToString
public abstract class BaseDom<FK extends Serializable, T extends BaseDom>
    implements BaseInterface<FK, T>, IModel {

  private static final long serialVersionUID = 4943817300823532768L;

  @TableId(value = "oid", type = IdType.AUTO)
  @ApiModelProperty(name = "主键", value = "oid", notes = "修改主键生成方式时必须填写")
  protected FK oid;

  // @Override
  // protected FK pkVal() {
  //   return this.oid;
  // }

  public T setOid(FK oid) {
    this.oid = oid;
    return (T) this;
  }

  @Override
  public Set<String> jsonRetain() {
    Set<String> hashSet = Sets.newHashSet();
    hashSet.add("oid");
    return hashSet;
  }
}
