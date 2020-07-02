package com.github.pure.cm.common.data.base.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.pure.cm.common.data.config.DateTimeSerializerConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础model
 * <br>
 * addDateTime、optDateTime
 *
 * @author chenhuan
 * @since 2017-07-03 16:44
 **/
@Setter
@Getter
@ToString(callSuper = true)
public abstract class BaseDomDate<FK extends Serializable, T extends BaseDom<FK, T>> extends BaseDom<FK, T> {

    /**
     * 该记录被添加的时间
     */
    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = DateTimeSerializerConverter.DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeSerializerConverter.DateTimeDeserializer.class)
    protected LocalDateTime addDateTime;

    /**
     * 记录最近一次编辑该记录的时间
     */
    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    @JsonSerialize(using = DateTimeSerializerConverter.DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeSerializerConverter.DateTimeDeserializer.class)
    protected LocalDateTime optDateTime;

    public T setAddDateTime(LocalDateTime addDateTime) {
        this.addDateTime = addDateTime;
        return (T) this;
    }

    public T setOptDateTime(LocalDateTime optDateTime) {
        this.optDateTime = optDateTime;
        return (T) this;
    }

}


