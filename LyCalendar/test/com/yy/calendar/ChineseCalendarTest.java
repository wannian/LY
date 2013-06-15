package com.yy.calendar;

import org.junit.Test;

public class ChineseCalendarTest {

    @Test
    public void testCalendarSolarToLundar() throws Exception {
        System.out.println(ChineseCalendar.sCalendarSolarToLundar(2013, 1, 8));
    }

}
