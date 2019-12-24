package com.github.canglan.cm.common.core.model;

import java.io.Serializable;
import lombok.Getter;
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
@ToString
@Accessors(chain = true)
public class ExceptionResult<T> extends Result implements Serializable {

  private static final long serialVersionUID = 2235085689160152768L;

  private String stackTrace;

}
