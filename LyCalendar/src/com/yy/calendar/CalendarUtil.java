package com.yy.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarUtil {

    /**
     * 是否为闰年
     * 
     * @param year 年
     * @return true:是闰年；false:不是闰年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static List<Week> generateWeeks(int year, int month) {
        List<Week> weeks = new ArrayList<Week>();

        // 离1900年1月1日总共的天数
        int sumdays = sumYearDay(year) + sumMonthDay(year, month);

        int weekDay = generateWeekDay(sumdays, 1);
        prevFill(weeks, year, month, weekDay);
        weekDay = fill(weeks, year, month, weekDay, sumdays);
        if (weekDay != 6) {
            afterFill(weeks, year, month, weekDay);
        }
        return weeks;
    }

    public static DayState generateDays(int year, int month) {
        Logging.i(String.format("generateDays %s/%s", year, month));
        List<Day> days = new ArrayList<Day>();
        List<Week> weeks = generateWeeks(year, month);
        for (Week week : weeks) {
            days.addAll(week.getDays());
        }
        return new DayState(days, weeks.size());
    }

    public static class DayState {
        public List<Day> days;
        public int rowSize;

        public DayState(List<Day> days2, int size) {
            days = days2;
            rowSize = size;
        }
    }

    private static void prevFill(List<Week> weeks, int year, int month, int weekDay) {
        if (weekDay == 0) {
            return;
        }
        Week week = new Week();
        weeks.add(week);
        int preYear = year;
        int preMonth = month - 1;
        if (month == 1) {
            preYear--;
            preMonth = 12;
        }
        Day day = null;
        int monthDay = dayOfMonth(preYear, preMonth);
        for (int j = 0; j < weekDay; j++) {
            day = new Day();
            day.setYear(preYear);
            day.setMonth(preMonth);
            day.setDate(monthDay - weekDay + 1 + j);
            day.setWeekDay(j);
            day.setFlag(Day.FLAG_FILL_PREV);
            day.setLundar(new Lundar(generateCalendar(day)));
            week.getDays().add(day);
        }
    }

    public static int fill(List<Week> weeks, int year, int month, int weekDay, int sumdays) {
        Week week = null;
        if (!weeks.isEmpty()) {
            week = weeks.get(weeks.size() - 1);
        }
        Day day = null;
        int monthDay = dayOfMonth(year, month);
        for (int i = 1; i <= monthDay; i++) {
            // 计算当月每日是星期几
            weekDay = generateWeekDay(sumdays, i);
            if (weekDay == 0) {
                week = new Week();
                weeks.add(week);
            }
            day = new Day();
            day.setYear(year);
            day.setMonth(month);
            day.setDate(i);
            day.setWeekDay(weekDay);
            day.setLundar(new Lundar(generateCalendar(day)));
            week.getDays().add(day);
        }
        return weekDay;
    }

    private static void afterFill(List<Week> weeks, int year, int month, int weekDay) {
        Week week = weeks.get(weeks.size() - 1);
        int nextYear = year;
        int nextMonth = month + 1;
        if (month == 12) {
            nextYear++;
            nextMonth = 1;
        }
        Day day = null;
        for (int j = weekDay + 1, i = 1; j <= 6; j++, i++) {
            day = new Day();
            day.setYear(nextYear);
            day.setMonth(nextMonth);
            day.setDate(i);
            day.setWeekDay(j);
            day.setFlag(Day.FLAG_FILL_AFTER);
            day.setLundar(new Lundar(generateCalendar(day)));
            week.getDays().add(day);
        }
    }

    private static Calendar generateCalendar(Day day) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, day.getYear());
        ca.set(Calendar.MONTH, day.getMonth() - 1);
        ca.set(Calendar.DATE, day.getDate());
        return ca;
    }

    /**
     * 计算是星期几
     * 
     * @param sumdays 1900到现在的总天数
     * @param day 当天日期
     * @return 星期几，0:星期日，6:星期六.
     */
    public static int generateWeekDay(int sumdays, int day) {
        return (day + sumdays) % 7;
    }

    /**
     * 计算所输入年份的1月1日到1900年1月1日的总天数
     * 
     * @param year 当前年份
     * @return 总天数
     */
    public static int sumYearDay(int year) {
        int yearday = 0;
        for (int i = 1900; i < year; i++) {
            if (isLeapYear(i)) {
                yearday += 366;
            } else {
                yearday += 365;
            }
        }
        return yearday;
    }

    /**
     * 计算所输入月份 到 所输入年份1月1号的天数
     * 
     * @param year 输入年份
     * @param month 输入月份
     * @return 总天数
     */
    public static int sumMonthDay(int year, int month) {
        int monthday = 0;
        for (int n = 1; n < month; n++) {
            monthday += dayOfMonth(year, n);
        }
        return monthday;
    }

    /**
     * 当月有多少天
     * 
     * @param year 年
     * @param month 月
     * @return 天数
     */
    public static int dayOfMonth(int year, int month) {
        int day = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            day = 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        } else if (month == 2 && isLeapYear(year)) {
            day = 29;
        } else {
            day = 28;
        }
        return day;
    }

    /**
     * 是否是当天
     * 
     * @param day 日期
     * @return true:当天；false：不是
     */
    public static boolean isToday(Day day) {
        if (day == null) {
            return false;
        }
        Calendar ca = Calendar.getInstance();
        return ca.get(Calendar.YEAR) == day.getYear() && (ca.get(Calendar.MONTH) + 1) == day.getMonth() && ca.get(Calendar.DATE) == day.getDate();
    }

    public static boolean isCurrentMonth(Calendar ca1, Calendar ca2) {
        return ca1.get(Calendar.YEAR) == ca2.get(Calendar.YEAR) && ca1.get(Calendar.MONTH) == ca2.get(Calendar.MONTH);
    }
}
