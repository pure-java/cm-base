package com.github.pure.cm.common.core.util.date;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.github.pure.cm.common.core.util.StringUtil;
import com.google.common.base.Preconditions;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

/**
 * 默认使用服务器设置时区
 *
 * @author bairitan
 * @since 2019/1/11
 */
public final class DateUtil implements Comparable<DateUtil> {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期时间 <p> yyyy-MM-dd HH:mm:ss SSS
     */
    public static final String DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss-SSS";
    /**
     * date yyyy-MM-dd
     */
    public static final String DATE = "yyyy-MM-dd";
    /**
     * HH-mm-ss
     */
    public static final String TIME = "HH-mm-ss";
    /**
     * 紧凑时间 / yyyyMMdd
     */
    public static final String COMPACT_DATE = "yyyyMMdd";
    public static final String COMPACT_DATE_LONG = "yyyyMMddHHmmss";
    private final long value;

    /**
     * 时区ID
     */
    private static final ZoneId zone = ZoneId.systemDefault();

    private DateUtil(long value) {
        this.value = value;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.newIns(LocalDateTime.now()).asStr(COMPACT_DATE));
    }

    /**
     * 初始化
     *
     * @param value 时间毫秒数
     */
    public static DateUtil newIns(long value) {
        checkArgument(value != 0, "时间戳不能为 0 ");
        return new DateUtil(value);
    }

    public static DateUtil newIns(Date date) {
        checkArgument(date != null, "时间不能为空");
        return new DateUtil(date.getTime());
    }

    /**
     * 初始化
     *
     * @param value 时间毫秒数
     */
    public static DateUtil newIns(String value, String pattern) {
        Preconditions.checkArgument(StringUtil.isNotBlank(value), "时间字符串不能为空");
        checkArgument(StringUtil.isNotBlank(pattern), "解析格式不能为空");

        // date time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime parse1 = formatter.parse(value, DateUtil::from);
        return newIns(parse1);
    }


    /**
     * 动态解析字符串
     */
    public static DateUtil newIns(String str) {
        checkArgument(StringUtils.isNotBlank(str), "时间字符串不能为空");
        try {
            Date parse = new StdDateFormat().parse(str);
            if (parse != null) {
                return newIns(parse.getTime());
            }
            return ofNull();
        } catch (ParseException e) {
            throw new RuntimeException("格式化时间出现错误", e);
        }
    }

    /**
     * 初始化，如果参数为空，返回当前时间
     *
     * @param localDateTime 时间
     */
    public static DateUtil newIns(LocalDateTime localDateTime) {
        checkArgument(localDateTime != null, "localDateTime 参数不能为空");

        return newIns(localDateTime.atZone(zone).toInstant().toEpochMilli());
    }

    /**
     * 初始化 返回当前时间 的 yyyy-MM-dd 00:00:00
     *
     * @param date 时间
     */
    public static DateUtil newIns(LocalDate date) {
        checkArgument(date != null, "localDateTime 参数不能为空");
        return newIns(date.atTime(0, 0, 0).atZone(zone).toInstant().toEpochMilli());
    }

    /**
     * 毫秒数为当前时间
     * 当前时间
     */
    public static DateUtil newIns() {
        return newIns(Instant.now().toEpochMilli());
    }

    /**
     * 空时间
     */
    private static DateUtil ofNull() {
        return new DateUtil(0);
    }

    // 计算=====================================

    // 天================================================

    /**
     * 天开始时间
     */
    public DateUtil dayStart() {
        return this.pipe(dateUtil -> DateUtil.newIns(dateUtil.asLocalDateTime().withHour(0).withMinute(0).withSecond(0)));
    }

    /**
     * 天结束时间
     */
    public DateUtil dayEnd() {
        return this.pipe(dateUtil -> DateUtil.newIns(dateUtil.asLocalDateTime().withHour(23).withMinute(59).withSecond(59)));
    }

    // 月 =============================================

    /**
     * 月起始时间
     */
    public DateUtil monthStart() {
        return this.pipe(dateUtil -> {
            LocalDateTime localDateTime = dateUtil.dayStart().asLocalDateTime();
            if (localDateTime == null) {
                return ofNull();
            }
            return DateUtil.newIns(localDateTime.withDayOfMonth(1));
        });
    }

    /**
     * 月的结束时间
     */
    public DateUtil monthEnd() {
        return this.pipe(dateUtil -> {
            LocalDateTime localDateTime = dateUtil.dayEnd().asLocalDateTime();
            if (localDateTime == null) {
                return ofNull();
            }
            return DateUtil.newIns(localDateTime.withDayOfMonth(localDateTime.toLocalDate().lengthOfMonth()));
        });
    }

    // 周 ===============================================


    /**
     * 周起始时间
     */
    public DateUtil weekStart() {
        return this.pipe(dateUtil -> {
            LocalDateTime localDateTime = dateUtil.dayStart().asLocalDateTime();
            if (localDateTime == null) {
                return ofNull();
            }
            DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
            return DateUtil.newIns(localDateTime.plusDays(-dayOfWeek.getValue() + 1));
        });
    }

    /**
     * 周结束时间
     */
    public DateUtil weekEnd() {
        return this.pipe(dateUtil -> {
            LocalDateTime localDateTime = dateUtil.dayEnd().asLocalDateTime();
            if (localDateTime == null) {
                return ofNull();
            }
            DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
            return DateUtil.newIns(localDateTime.plusDays(7 - dayOfWeek.getValue()));
        });
    }

    //  年 =======================================

    /**
     * 年起始时间
     */
    public DateUtil yearStart() {
        return this.pipe(dateUtil -> {
            LocalDateTime localDateTime = dateUtil.dayStart().asLocalDateTime();
            if (localDateTime == null) {
                return ofNull();
            }
            return DateUtil.newIns(localDateTime.plusDays(-localDateTime.getDayOfYear() + 1));
        });
        // return date.plusDays(-date.getDayOfYear() + 1);
    }

    /**
     * 年结束时间
     */
    public DateUtil yearEnd() {
        return this.pipe(dateUtil -> {
            LocalDateTime localDateTime = dateUtil.dayEnd().asLocalDateTime();
            if (localDateTime == null) {
                return ofNull();
            }
            LocalDate localDate = localDateTime.toLocalDate();
            LocalDate localDate1 = localDate.isLeapYear() ?
                    localDate.plusDays(366 - localDate.getDayOfYear())
                    : localDate.plusDays(365 - localDate.getDayOfYear());
            return DateUtil.newIns(LocalDateTime.of(localDate1, localDateTime.toLocalTime()));
        });
    }

    // 数学计算 =========================================

    /**
     * 根据时间符号进行加减时间
     *
     * @param add          计算数字
     * @param temporalUnit 计算的时间单位
     */
    public DateUtil plusDate(long add, ChronoUnit temporalUnit) {
        return this.pipe(dateUtil -> {
            LocalDateTime localDateTime = dateUtil.asLocalDateTime();
            if (localDateTime == null) {
                return ofNull();
            }
            return DateUtil.newIns(localDateTime.plus(add, temporalUnit));
        });
    }


    /**
     * 取得两个日期之间的天数
     *
     * <pre>
     * int res = newIns("2019-01-11").daysBetween(newIns("2019-01-30"));
     *  res == 19
     * </pre>
     *
     * @return this 到 t2 间的日数，如果 t2 在 this 之后，返回正数，否则返回负数
     */
    public Long daysBetween(DateUtil t2) {
        LocalDateTime localDateTime1 = this.asLocalDateTime();
        LocalDateTime localDateTime2 = t2.asLocalDateTime();
        if (localDateTime1 != null && localDateTime2 != null) {
            return ChronoUnit.DAYS.between(localDateTime1, localDateTime2);
        }
        return null;
    }


    /**
     * 根据单位字段比较两个日期
     *
     * @param t2            日期2
     * @param temporalField 单位字段，从{@link ChronoField}取值
     * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
     */
    public Integer compareDate(DateUtil t2, ChronoField temporalField) {
        LocalDateTime localDateTime1 = this.asLocalDateTime();
        LocalDateTime localDateTime2 = t2.asLocalDateTime();

        if (localDateTime1 == null && localDateTime2 == null) {
            return 0;
        }

        if (localDateTime1 == null || localDateTime2 == null) {
            return null;
        }
        int date = localDateTime1.get(temporalField);
        int other = localDateTime2.get(temporalField);
        return date - other;
    }


    /**
     * 两个时间毫秒数之差 <br>
     * this - t2 <br>
     * <p>
     * 两个时间有任意一个为空，返回空
     */
    public Long millisBetween(DateUtil t2) {
        if (t2 == null || t2.value == 0 || this.value == 0) {
            return null;
        }
        return this.value - t2.value;
    }

    // 转换====================================================

    /**
     * 根据时间获取毫秒数
     */
    public Long asDateMilli() {
        return this.value;
    }

    /**
     * 根据时间获取毫秒数
     */
    public static Long getDateMilli() {
        return DateUtil.newIns().value;
    }

    /**
     * 转换为 {@link Date} <br>
     * 如果为空值，则返回空
     *
     * @return {@link Date}
     */
    public Date asDate() {
        if (this.value == 0) {
            return null;
        }
        return new Date(this.value);
    }

    /**
     * 转换为 {@link Date} <br>
     *
     * @return {@link Date}
     */
    public static Date getDate() {
        return DateUtil.newIns().asDate();
    }

    /**
     * 转换为 {@link LocalDateTime} <br>
     * 如果为空值，则返回空
     *
     * @return LocalDateTime类型的时间
     */
    public LocalDateTime asLocalDateTime() {
        if (this.value == 0) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(this.value);
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 转换为 {@link LocalDateTime} <br>
     * 如果为空值，则返回空
     *
     * @return LocalDateTime类型的时间
     */
    public static LocalDateTime getLocalDateTime() {
        return DateUtil.newIns().asLocalDateTime();
    }

    /**
     * 获取日期字符串 <br>
     * 如果为空值，则返回空
     *
     * @param pattern 字符串格式
     */
    public String asStr(String pattern) {
        LocalDateTime localDateTime = this.asLocalDateTime();
        if (localDateTime == null) {
            return null;
        }
        // date time
        if (pattern.contains("HH") && pattern.contains("mm") && pattern.contains("ss")
                && pattern.contains("yyyy") && pattern.contains("MM") && pattern.contains("dd")) {
            return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
            // date
        } else if (pattern.contains("yyyy") && pattern.contains("MM") && pattern.contains("dd")) {
            return localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern(pattern));
        } else /*if (pattern.contains("HH") && pattern.contains("mm") && pattern.contains("ss")) */ {
            return localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern(pattern));
        }
    }

    /**
     * 获取日期字符串 <br>
     *
     * @param pattern 字符串格式
     */
    public static String getStr(String pattern) {
        return DateUtil.newIns().asStr(pattern);
    }

    /**
     * 获取日期字符串 <br>
     * 如果为空值，则返回空
     */
    public String asStr() {
        LocalDateTime localDateTime = this.asLocalDateTime();
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(DateUtil.DATE_TIME));
    }

    /**
     * 获取日期字符串 <br> {@link DateUtil#DATE_TIME}
     */
    public static String getStr() {
        return DateUtil.newIns().asStr();
    }


    /**
     * 是一个星期中的第几天(1-7)
     */
    public Integer asWeekIndex() {
        LocalDateTime localDateTime = this.asLocalDateTime();
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.getDayOfWeek().getValue();
    }

    /**
     * 获取本周是今年的第几周
     */
    public Integer getWeekOfYear() {
        LocalDateTime localDateTime = this.asLocalDateTime();
        if (localDateTime == null) {
            return null;
        }
        LocalDate date = localDateTime.toLocalDate();
        return date.getDayOfYear() % 7 == 0 ? date.getDayOfYear() / 7 : date.getDayOfYear() / 7 + 1;
    }

    private DateUtil pipe(Function<DateUtil, DateUtil> function) {
        if (this.value == 0) {
            return ofNull();
        }
        checkArgument(function != null, "Function  不能为空");
        return function.apply(this);
    }

    /**
     * 转换方式
     *
     * @param temporal 访问器
     * @return 返回LocalDateTime
     */
    private static LocalDateTime from(TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        LocalTime time = temporal.query(TemporalQueries.localTime());
        LocalDate date = temporal.query(TemporalQueries.localDate());

        if (time == null) {
            time = LocalTime.now();
        }
        if (date == null) {
            date = LocalDate.now();
        }
        return LocalDateTime.of(date, time);
    }

    @Override
    public int compareTo(DateUtil o) {
        return Long.compare(this.value, o.value);
    }

    @Override
    public String toString() {
        return this.asStr();
    }

}
