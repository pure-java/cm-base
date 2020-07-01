package com.github.pure.cm.common.data.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

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
    @JsonSerialize(using = DateTimeSerializer.class)
    protected Date addDateTime;

    /**
     * 记录最近一次编辑该记录的时间
     */
    @ApiModelProperty(hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    @JsonSerialize(using = DateTimeSerializer.class)
    protected Date optDateTime;

    public T setAddDateTime(Date addDateTime) {
        this.addDateTime = addDateTime;
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