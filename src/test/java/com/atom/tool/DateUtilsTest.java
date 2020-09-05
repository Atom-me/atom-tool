package com.atom.tool;

import com.atom.tool.core.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

/**
 * Auto-Generated: Do not modify it
 */
@SuppressWarnings("all")
@Slf4j
public class DateUtilsTest {

    @Test
    public void addDaysFromNow() {
        //tomorrow
        System.err.println(DateUtils.addDaysFromNow(1));
        //today
        System.err.println(DateUtils.addDaysFromNow(0));
        //yesterday
        System.err.println(DateUtils.addDaysFromNow(-1));
    }

    @Test
    public void addDaysFromSpecialDateToString() {
        System.err.println(DateUtils.addDaysFromSpecialDate("2020-09-01 12:23:33", 1));
        System.err.println(DateUtils.addDaysFromSpecialDate("", 1));
    }

    @Test
    public void addDaysFromSpecialDateToDate() {
        Date specialDate = DateUtils.strToDate("2020-09-01 12:23:33", "yyyy-MM-dd HH:mm:ss");
        System.err.println(DateUtils.addDaysFromSpecialDate(specialDate, 1));
        specialDate = null;
        System.err.println(DateUtils.addDaysFromSpecialDate(specialDate, 1));
    }

    @Test
    public void addDaysToDate() {
        System.err.println(DateUtils.addDaysToDate("2019-02-28 12:23:33", 1));
        //闰年 二月29天
        System.err.println(DateUtils.addDaysToDate("2020-02-28 12:23:33", 1));
        System.err.println(DateUtils.addDaysToDate("", 1));
    }

    @Test
    public void addMinutesFromNow() {
        System.err.println(new Date());
        System.err.println(DateUtils.addMinutesFromNow(10));
    }

    @Test
    public void addMinutesFromSpecDate() {
        Date specialDate = DateUtils.strToDate("2020-09-01 12:23:33", "yyyy-MM-dd HH:mm:ss");
        System.err.println(DateUtils.addMinutesFromSpecDate(specialDate, 10));
        System.err.println(DateUtils.addMinutesFromSpecDate(null, 10));
    }

    @Test
    public void addMinutesToDate() {
        System.err.println(DateUtils.addMinutesToDate("2019-02-28 23:53:33", -10));
        System.err.println(DateUtils.addMinutesToDate("2019-02-28 23:53:33", 0));
        System.err.println(DateUtils.addMinutesToDate("2019-02-28 23:53:33", 10));
        System.err.println(DateUtils.addMinutesToDate("", 10));
    }

    @Test
    public void addMinutesToString() {
        System.err.println(DateUtils.addMinutesToString("2019-02-28 23:53:33", -10));
        System.err.println(DateUtils.addMinutesToString("2019-02-28 23:53:33", 0));
        System.err.println(DateUtils.addMinutesToString("2019-02-28 23:53:33", 10));
        System.err.println(DateUtils.addMinutesToString("", 10));
    }

    @Test
    public void dateToString() {
        System.err.println(DateUtils.dateToString(new Date()));
        System.err.println(DateUtils.dateToString(null));
    }

    @Test
    public void strToDate() {
        System.err.println(DateUtils.strToDate("2019-02-28 23:53:33", "yyyy-MM-dd HH:mm:ss"));
        System.err.println(DateUtils.strToDate("2019:02:28***23:53:33", "yyyy:MM:dd***HH:mm:ss"));
        System.err.println(DateUtils.strToDate("2019:02:28*=====**23:53:=-33", "yyyy:MM:dd*=====**HH:mm:=-ss"));
    }

    @Test
    public void getTodayStartTimeStr() {
        System.err.println(DateUtils.getTodayStartTimeStr());
    }

    @Test
    public void getTodayEndTimeStr() {
        System.err.println(DateUtils.getTodayEndTimeStr());
    }

}

