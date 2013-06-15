package com.yy.calendar;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CalendarTest {

    @Test
    public void testGenerate() throws Exception {
        int year = 2013;
        int month = 1;
        List<Week> weeks = CalendarUtil.generateWeeks(year, month);
        System.out.println(weeks.toString());

        year = 2013;
        month = 2;
        weeks = CalendarUtil.generateWeeks(year, month);
        System.out.println(weeks.toString());
    }

    @Test
    public void testIsLeapYear() throws Exception {
        int year = 2013;
        boolean result = CalendarUtil.isLeapYear(year);
        Assert.assertEquals(result, false);

        year = 2012;
        result = CalendarUtil.isLeapYear(year);
        Assert.assertEquals(result, true);

        year = 2000;
        result = CalendarUtil.isLeapYear(year);
        Assert.assertEquals(result, true);

        year = 1950;
        result = CalendarUtil.isLeapYear(year);
        Assert.assertEquals(result, false);
    }

    @Test
    public void testSumYearDay() throws Exception {
        int year = 1901;
        int result = CalendarUtil.sumYearDay(year);
        Assert.assertEquals(result, 365);

        year = 1902;
        result = CalendarUtil.sumYearDay(year);
        Assert.assertEquals(result, 365 * 2);

        year = 1950;
        result = CalendarUtil.sumYearDay(year);
        Assert.assertEquals(result, 18262);

        year = 2000;
        result = CalendarUtil.sumYearDay(year);
        Assert.assertEquals(result, 36524);

        year = 2010;
        result = CalendarUtil.sumYearDay(year);
        Assert.assertEquals(result, 40177);
    }

    @Test
    public void testSumMonthDay() throws Exception {
        int year = 1901;
        int month = 2;
        int result = CalendarUtil.sumMonthDay(year, month);
        Assert.assertEquals(result, 31);

        year = 2013;
        month = 4;
        result = CalendarUtil.sumMonthDay(year, month);
        Assert.assertEquals(result, 31 + 28 + 31);

        year = 2012;
        month = 3;
        result = CalendarUtil.sumMonthDay(year, month);
        Assert.assertEquals(result, 31 + 29);
    }

    @Test
    public void testDayOfMonth() throws Exception {
        int year = 1901;
        int month = 2;
        int result = CalendarUtil.dayOfMonth(year, month);
        Assert.assertEquals(result, 28);

        year = 1958;
        month = 12;
        result = CalendarUtil.dayOfMonth(year, month);
        Assert.assertEquals(result, 31);

        year = 1989;
        month = 9;
        result = CalendarUtil.dayOfMonth(year, month);
        Assert.assertEquals(result, 30);

        year = 2000;
        month = 2;
        result = CalendarUtil.dayOfMonth(year, month);
        Assert.assertEquals(result, 29);

        year = 2013;
        month = 4;
        result = CalendarUtil.dayOfMonth(year, month);
        Assert.assertEquals(result, 30);
    }
}
