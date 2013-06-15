package com.yy.calendar;

import java.util.ArrayList;
import java.util.List;

public class Week {

    private List<Day> days = new ArrayList<Day>();

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "CalendarWeek [days=" + days + "]";
    }
}
