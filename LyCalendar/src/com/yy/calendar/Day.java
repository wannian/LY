package com.yy.calendar;

public class Day {
    public static final int FLAG_FILL = 1 << 0;
    public static final int FLAG_FILL_PREV = 1 << 1;
    public static final int FLAG_FILL_AFTER = 1 << 2;
    public static final int FLAG_FILL_EMPTY = 1 << 3;
    public static final Day EMPTY_DAY = new Day(FLAG_FILL_EMPTY);
    private int year;
    private int month;
    private int date;
    private int weekDay;
    private Lundar lundar;
    private int flag;

    public Day() {
        flag = FLAG_FILL;
    }

    public Day(int flag) {
        this.flag = flag;
    }

    public Lundar getLundar() {
        return lundar;
    }

    public void setLundar(Lundar lundar) {
        this.lundar = lundar;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    @Override
    public String toString() {
        return "Day [year=" + year + ", month=" + month + ", date=" + date + ", weekDay=" + weekDay + ", lundar=" + lundar + ", flag=" + flag + "]";
    }

}
