package com.atom.tool.core;


import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @author Atom
 */
public class DateUtils {

    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * str to date,
     * <p>
     * "2019-02-28 23:53:33", "yyyy-MM-dd HH:mm:ss"
     * "2019:02:28***23:53:33", "yyyy:MM:dd***HH:mm:ss"
     * "2019:02:28*=====**23:53:=-33", "yyyy:MM:dd*=====**HH:mm:=-ss"
     *
     * @param timeStr "2018-06-05 12:23:34" must match param pattern
     * @param pattern "yyyy-MM-dd HH:mm:ss" must match param timeStr
     * @return
     * @author Atom
     */
    public static Date strToDate(String timeStr, String pattern) {
        java.time.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(timeStr, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }


    /**
     * @param datetime "2017-05-06 23:45:33"
     * @param minutes  15
     * @return 2017-05-07 00:00:33
     * @author Atom
     */
    public static String addMinutesToString(String datetime, int minutes) {
        if (StringUtils.isBlank(datetime)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(datetime, dateTimeFormatter).plusMinutes(minutes);
        return dateTime.format(dateTimeFormatter);
    }


    /**
     * @param datetime "2017-05-06 23:45:33"
     * @param minutes  15
     * @return 2017-05-07 00:00:33
     * @author Atom
     */
    public static Date addMinutesToDate(String datetime, int minutes) {
        if (StringUtils.isBlank(datetime)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(datetime, dateTimeFormatter).plusMinutes(minutes);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取几天后的当前时间
     *
     * @param days
     * @return 2017-05-07 00:00:33
     * @author Atom
     */
    public static Date addDaysFromNow(int days) {
        return addDaysFromSpecialDate(new Date(), days);
    }

    /**
     * 获取从当前时间开始，几分钟后的时间
     *
     * @param minutes
     * @return 2017-05-07 00:00:33
     * @author Atom
     */
    public static Date addMinutesFromNow(int minutes) {
        return addMinutesFromSpecDate(new Date(), minutes);
    }

    /**
     * 获取从指定时间开始，几分钟后的时间
     *
     * @param minutes
     * @return 2017-05-07 00:00:33
     * @author Atom
     */
    public static Date addMinutesFromSpecDate(Date date, int minutes) {
        if (Objects.isNull(date)) {
            return null;
        }
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusMinutes(minutes);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * @param datetime "2017-05-06 23:45:33"
     * @param days     15
     * @return 2017-05-07 00:00:33
     * @author Atom
     */
    public static Date addDaysToDate(String datetime, int days) {
        if (StringUtils.isBlank(datetime)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(datetime, dateTimeFormatter).plusDays(days);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }


    /**
     * 给指定的时间添加天数
     * <p>
     * 返回字符串格式：yyyy-MM-dd HH:mm:ss
     *
     * @param datetime "2017-05-06 23:45:33"
     * @param days     15
     * @return 返回字符串格式：yyyy-MM-dd HH:mm:ss 2017-05-07 00:00:33
     * @author Atom
     */
    public static String addDaysFromSpecialDate(String datetime, int days) {
        if (StringUtils.isBlank(datetime)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);
        LocalDateTime dateTime = LocalDateTime.parse(datetime, dateTimeFormatter).plusDays(days);
        return dateTime.format(dateTimeFormatter);
    }

    /**
     * 给指定的时间添加天数
     * 返回 {@link Date} 格式
     *
     * @param days 要增加的天数
     * @return Date
     * @author Atom
     */
    public static Date addDaysFromSpecialDate(Date date, int days) {
        if (Objects.isNull(date)) {
            return null;
        }
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusDays(days);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * @param datetime "2017-05-06 23:45:33"
     * @return 2017-05-07 00:00:33
     * @author Atom
     */
    public static String dateToString(Date datetime) {
        if (Objects.isNull(datetime)) {
            return null;
        }
        LocalDateTime newDateTime = LocalDateTime.ofInstant(datetime.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);
        return dateTimeFormatter.format(newDateTime);
    }
}
