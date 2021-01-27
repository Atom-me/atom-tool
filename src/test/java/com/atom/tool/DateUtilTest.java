package com.atom.tool;

import com.atom.tool.core.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Auto-Generated: Do not modify it
 */
@SuppressWarnings("all")
@Slf4j
public class DateUtilTest {

    @Test
    public void addDaysFromNow() {
        //tomorrow
        System.err.println(DateUtil.addDaysFromNow(1));
        //today
        System.err.println(DateUtil.addDaysFromNow(0));
        //yesterday
        System.err.println(DateUtil.addDaysFromNow(-1));
    }

    @Test
    public void addDaysFromSpecialDateToString() {
        System.err.println(DateUtil.addDaysFromSpecialDate("2020-09-01 12:23:33", 1));
        System.err.println(DateUtil.addDaysFromSpecialDate("", 1));
    }

    @Test
    public void addDaysFromSpecialDateToDate() {
        Date specialDate = DateUtil.strToDate("2020-09-01 12:23:33", "yyyy-MM-dd HH:mm:ss");
        System.err.println(DateUtil.addDaysFromSpecialDate(specialDate, 1));
        specialDate = null;
        System.err.println(DateUtil.addDaysFromSpecialDate(specialDate, 1));
    }

    @Test
    public void addDaysToDate() {
        System.err.println(DateUtil.addDaysToDate("2019-02-28 12:23:33", 1));
        //闰年 二月29天
        System.err.println(DateUtil.addDaysToDate("2020-02-28 12:23:33", 1));
        System.err.println(DateUtil.addDaysToDate("", 1));
    }

    @Test
    public void addMinutesFromNow() {
        System.err.println(new Date());
        System.err.println(DateUtil.addMinutesFromNow(10));
    }

    @Test
    public void addMinutesFromSpecDate() {
        Date specialDate = DateUtil.strToDate("2020-09-01 12:23:33", "yyyy-MM-dd HH:mm:ss");
        System.err.println(DateUtil.addMinutesFromSpecDate(specialDate, 10));
        System.err.println(DateUtil.addMinutesFromSpecDate(null, 10));
    }

    @Test
    public void addMinutesToDate() {
        System.err.println(DateUtil.addMinutesToDate("2019-02-28 23:53:33", -10));
        System.err.println(DateUtil.addMinutesToDate("2019-02-28 23:53:33", 0));
        System.err.println(DateUtil.addMinutesToDate("2019-02-28 23:53:33", 10));
        System.err.println(DateUtil.addMinutesToDate("", 10));
    }

    @Test
    public void addMinutesToString() {
        System.err.println(DateUtil.addMinutesToString("2019-02-28 23:53:33", -10));
        System.err.println(DateUtil.addMinutesToString("2019-02-28 23:53:33", 0));
        System.err.println(DateUtil.addMinutesToString("2019-02-28 23:53:33", 10));
        System.err.println(DateUtil.addMinutesToString("", 10));
    }

    @Test
    public void dateToString() {
        System.err.println(DateUtil.dateToString(new Date()));
        System.err.println(DateUtil.dateToString(null));
    }

    @Test
    public void strToDate() {
        System.err.println(DateUtil.strToDate("2019-02-28 23:53:33", "yyyy-MM-dd HH:mm:ss"));
        System.err.println(DateUtil.strToDate("2019:02:28***23:53:33", "yyyy:MM:dd***HH:mm:ss"));
        System.err.println(DateUtil.strToDate("2019:02:28*=====**23:53:=-33", "yyyy:MM:dd*=====**HH:mm:=-ss"));
    }

    @Test
    public void getTodayStartTimeStr() {
        System.err.println(DateUtil.getTodayStartTimeStr());
    }

    @Test
    public void getTodayEndTimeStr() {
        System.err.println(DateUtil.getTodayEndTimeStr());
    }

    @Test
    public void asDate() {
        System.err.println(DateUtil.asDate(LocalDate.now()));
        //java.lang.IllegalArgumentException: java.lang.ArithmeticException: long overflow
//        System.err.println(DateUtils.asDate(LocalDate.MIN));
        //java.lang.IllegalArgumentException: java.lang.ArithmeticException: long overflow
//        System.err.println(DateUtils.asDate(LocalDate.MIN));


        System.err.println(DateUtil.asDate(LocalDateTime.now()));
        //java.lang.IllegalArgumentException: java.lang.ArithmeticException: long overflow
//        System.err.println(DateUtils.asDate(LocalDateTime.MAX));
        //java.lang.IllegalArgumentException: java.lang.ArithmeticException: long overflow
//        System.err.println(DateUtils.asDate(LocalDateTime.MIN));


        System.err.println(DateUtil.asDate(LocalDate.now().minusDays(9).atStartOfDay()));
        //Sat Feb 29 00:00:00 CST 2020
        System.err.println(DateUtil.asDate(LocalDate.of(2020, 3, 9).minusDays(9).atStartOfDay()));
        //Sun Feb 28 00:00:00 CST 2021
        System.err.println(DateUtil.asDate(LocalDate.of(2021, 3, 9).minusDays(9).atStartOfDay()));


        LocalDateTime localDateTime = null;
        System.err.println(DateUtil.asDate(localDateTime));


        LocalDate localDate = null;
        System.err.println(DateUtil.asDate(localDate));


    }

    @Test
    public void asLocalDate() {
        LocalDate localDate = DateUtil.asLocalDate(new Date());
        //闰年判断 isLeapYear
        System.err.println(localDate.isLeapYear());
        System.err.println(DateUtil.asLocalDate(new Date()));
        System.err.println(DateUtil.asLocalDate(null));
    }

    @Test
    public void asLocalDateTime() {
        System.err.println(DateUtil.asLocalDateTime(new Date()));
    }

    @Test
    public void formatDateWithCustomizePattern() {
        //2020-09-17
        System.err.println(DateUtil.formatDateWithCustomizePattern(new Date(), "yyyy-MM-dd"));
        System.err.println(DateUtil.formatDateWithCustomizePattern(new Date(), "yyyy----MM---dd"));
        //2020----09---17
        System.err.println(DateUtil.formatDateWithCustomizePattern(new Date(), "yyyy:::MM:::dd"));
        //20:::09:::17
        System.err.println(DateUtil.formatDateWithCustomizePattern(new Date(), "yy:::MM:::dd"));
        //19-11-15
        System.err.println(DateUtil.formatDateWithCustomizePattern(new Date(2019, 10, 15), "yy-MM-dd"));
    }

}

