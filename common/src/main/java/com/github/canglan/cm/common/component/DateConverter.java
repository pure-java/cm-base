package com.github.canglan.cm.common.component;

import com.github.canglan.cm.common.util.basic.MathUtil;
import com.github.canglan.cm.common.util.date.DateUtil;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 前端传到后端时间类型转换: 从long或long型字符串转换为Date
 *
 * 后端传输数据到前端：将date 类型转换为 Long型
 *
 * @author bairitan
 * @date 2019/1/10
 */
@Component
public class DateConverter {

  @Bean
  @Qualifier("stringToDateConverter")
  public Converter<String, Date> stringToDate() {
    return new StringToDateConverter();
  }

  @Bean
  @Qualifier("stringToLocalDateTimeConverter")
  public Converter<String, LocalDateTime> stringToLocalDateTime() {
    return new StringToLocalDateTimeConverter();
  }

  @Bean
  @Qualifier("longToDateConverter")
  public Converter<Long, Date> longToDate() {
    return new LongToDateConverter();
  }

  @Bean
  @Qualifier("longToDateConverter")
  public Converter<Long, LocalDateTime> longToLocalDateTime() {
    return new LongToLocalDateTimeConverter();
  }

  /**
   * 字符串转为时间
   */
  class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
      if (MathUtil.isNumber(source)) {
        return new Date(Long.valueOf(source));
      }
      return DateUtil.newIns(source).asDate();
    }
  }

  /**
   * 字符串转为 LocalDateTime
   */
  class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
      if (MathUtil.isNumber(source)) {
        return DateUtil.newIns(Long.valueOf(source)).asLocalDateTime();
      }
      return DateUtil.newIns(source).asLocalDateTime();
    }
  }

  /**
   * 数字转为时间
   */
  class LongToDateConverter implements Converter<Long, Date> {
    @Override
    public Date convert(Long source) {
      return new Date(source);
    }
  }

  /**
   * 数字转为LocalDateTime
   */
  class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {

    @Override
    public LocalDateTime convert(Long source) {
      return DateUtil.newIns(source).asLocalDateTime();
    }
  }

}
