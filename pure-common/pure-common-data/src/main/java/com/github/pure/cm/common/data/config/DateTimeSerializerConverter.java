package com.github.pure.cm.common.data.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.pure.cm.common.core.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * 时间转换器 ：并向spring注入
 *
 * @author 陈欢
 * @since 2020/7/2
 */
@Configuration
public class DateTimeSerializerConverter {
    private static String[] pattern =
            new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S",
                    "yyyy.MM.dd", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.S",
                    "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.S",
                    "HH:mm:ss", "HH:mm:ss.S"};

    @Configuration
    public static class DateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime date, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeObject(DateUtil.newIns(date).asDateMilli());
        }
    }

    @Configuration
    public static class DateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

            LocalDateTime targetDate = null;
            String originDate = p.getText();
            if (StringUtils.isNotEmpty(originDate)) {
                try {
                    long longDate = Long.parseLong(originDate.trim());
                    targetDate = DateUtil.newIns(longDate).asLocalDateTime();
                } catch (NumberFormatException e) {
                    try {
                        targetDate = DateUtil.newIns(DateUtils.parseDate(originDate, pattern)).asLocalDateTime();
                    } catch (ParseException pe) {
                        throw new IOException(String.format(
                                "'%s' can not convert to type 'java.util.Date',just support timestamp(type of long) and following date format(%s)",
                                originDate,
                                StringUtils.join(pattern, ",")));
                    }
                }
            }
            return targetDate;
        }

        @Override
        public Class<?> handledType() {
            return LocalDateTime.class;
        }
    }
}
