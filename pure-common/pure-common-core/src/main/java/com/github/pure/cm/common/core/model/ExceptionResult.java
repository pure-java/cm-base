package com.github.pure.cm.common.core.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 返回操作信息
 *
 * @author admin
 * @since 2017-07-19 15:43
 **/
@Setter
@Getter
@ToString(callSuper = true)
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
public class ExceptionResult<T> extends Result<T> implements Serializable {

    private static final long serialVersionUID = 2235085689160152768L;

    public ExceptionResult(int value, boolean b) {
        this.setCode(value);
        this.setStatus(b);
    }
}
