package io.okkio.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
@Slf4j
public class DateUtil extends DateUtils {

    /**
     * To Date
     *
     * @param source
     * @param format
     * @return
     */
    public static Date toDate(String source, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date toDate(Long longTime) {
        if (longTime == null) {
            return null;
        }
        return new Date(longTime);
    }

    public static String convertFormatICODate(String startDate, String endDate) {
        // 2021-09-23 -> 23 SEP 2021
        DateTimeFormatter formatStartDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);;
        LocalDate dateStart = LocalDate.parse(startDate, formatStartDate);
        LocalDate dateEnd = LocalDate.parse(endDate, formatStartDate);

        String monthStart = dateStart.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();
        String startTime = dateStart.getDayOfMonth() + " " + monthStart;

        String monthEnd = dateEnd.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();
        String endTime = dateEnd.getDayOfMonth() + " " + monthEnd + " " + dateEnd.getYear();

        return startTime + " - " + endTime;
    }

    public static String toString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(CalendarUtil.getTimeZone());
        return sdf.format(date);
    }

    public static boolean isBeforeDate(Date min, Date max) {
        return min.before(max);
    }

    public static boolean isAfterDate(Date min, Date max) {
        return min.after(max);
    }

    public static boolean isEqualDate(Date min, Date max) {
        return min.equals(max);
    }

    public static boolean isBeforeDateOrEqual(Date min, Date max) {
        return min.before(max) || min.equals(max);
    }

    public static boolean isAfterDateOrEqual(Date min, Date max) {
        return min.after(max) || min.equals(max);
    }

    public static Date newEndTimeDate() {
        return CalendarUtil.setDayEnd(new Date());
    }

}
