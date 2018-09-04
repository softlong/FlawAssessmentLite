package com.softgrid.flawAssessmentLite.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static Date plusDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return calendar.getTime();
    }

    public static Date plusMins(Date date, int mins) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, mins);

        return calendar.getTime();
    }

    public static Date LocalDateToDate(LocalDate localDate) {
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);

        return date;
    }
}
