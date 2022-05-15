package io.okkio.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarUtil {
    private static Calendar calendar = Calendar.getInstance(CalendarUtil.getTimeZone());
    private static final String timeZone = "Asia/Ho_Chi_Minh";

    public static TimeZone getTimeZone() {
        return TimeZone.getTimeZone(timeZone);
    }


    public static Calendar getInstance() {
        return Calendar.getInstance(CalendarUtil.getTimeZone());
    }

    public static Date setDayStart(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
        return calendar.getTime();
    }

    public static Date setDayEnd(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getFirstDateInMonth(int deviate) {
        Calendar current = CalendarUtil.getInstance();
        current.setTime(new Date());
        current.set(Calendar.DATE, 1);
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MILLISECOND, 1);
        current.add(Calendar.MONTH, -deviate);  //set month ago to compare
        return current.getTime();
    }
}
