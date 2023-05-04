package com.fis.crm.commons;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public static String dateToString(Date date, SimpleDateFormat formatter) {
        if (date == null) {
            return null;
        }

        return formatter.format(date);
    }

    public static String dateToString(Date date, String formatter) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(formatter).format(date);
    }

    public static String dateToStringDateVN(Date date) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    public static Date stringToDate(String sDate, SimpleDateFormat formatter) {
        if (sDate == null) {
            return null;
        }

        Date date = null;

        try {
            date = formatter.parse(sDate);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return date;
    }

    public static Date getStartOfDay(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String sDate = formatter.format(d);
        Date dateStartOfDay = d;

        try {
            dateStartOfDay = formatter.parse(sDate);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return dateStartOfDay;
    }

    public static Date getEndOfDay(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String sDate = formatter.format(d);
        Date dateEndOfDay = d;
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            dateEndOfDay = formatter1.parse(sDate);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return dateEndOfDay;
    }

    public static Integer getIntCurrentDateByFormat(SimpleDateFormat formatter) {
        Date currentDate = new Date();
        Integer d = Integer.parseInt(formatter.format(currentDate));
        return d;
    }

    public static long getCurrentTimestamp() {
        Date currentDate = new Date();
        return currentDate.getTime();
    }

    public static String getCurrentDateTime() {
        Date dt = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);

        return currentTime;
    }

    public static String convertTimeDisplay(Date date) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return df.format(date);
    }

    public static String getSqlDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String sDate = sdf.format(date);

        return sDate;
    }

    public static Date addMinutes(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);

        return cal.getTime();
    }

    public static Date addMinutesToDate(Date beforeTime, int minutes) {
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }

    public static int compareDate(Date date1, Date date2) {
        if (date1.compareTo(date2) > 0) {
            logger.info("Date1 > Date2");
            return 0;
        } else if (date1.compareTo(date2) < 0) {
            logger.info("Date1 < Date2");
            return 1;
        } else if (date1.compareTo(date2) == 0) {
            logger.info("Date1 == Date2");
            return 2;
        }
        return 3;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    public static Date addMonths(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);

        return cal.getTime();
    }

    public static Date addYears(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);

        return cal.getTime();
    }

    public static Date convertSqlDateToDate(String sDate) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            date = sdf.parse(sDate);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        return date;
    }

    public static double getDiffDays(Date fromDate, Date toDate) {

        long diff = toDate.getTime() - fromDate.getTime();
        double diffDays = (double) diff / 86400000;

        return diffDays;
    }

    public static double getDiffHours(Date fromDate, Date toDate) {

        long diff = toDate.getTime() - fromDate.getTime();
        double diffHours = (double) diff / 3600000; // 60 * 60 * 1000

        return diffHours;
    }

    public static double getDiffMinutes(Date fromDate, Date toDate) {

        long diff = toDate.getTime() - fromDate.getTime();
        double diffMinutes = (double) diff / 60000; // 60 * 1000

        return diffMinutes;
    }

    public static double getDiffSeconds(Date fromDate, Date toDate) {

        long diff = toDate.getTime() - fromDate.getTime();
        double diffSeconds = (double) diff / 1000; // 1000

        return diffSeconds;
    }

    public static Long convertToTimeStamp(String strDateTime, SimpleDateFormat formatter) {

        if (StringUtils.isEmpty(strDateTime)) {
            return null;
        }

        Long timeStamp = null;

        try {
            Date date = formatter.parse(strDateTime);
            timeStamp = date.getTime();
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        return timeStamp;
    }

    public static String formatDate(String date) throws ParseException {

        Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String parsedDate = formatter.format(initDate);
        return parsedDate;
    }

    public static String formatDateVN(String date) throws ParseException {

        Date initDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String parsedDate = formatter.format(initDate);
        return parsedDate;
    }

    public static java.sql.Date convertInstantToDate(Instant iDate, int i) {
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, -1); // to get previous year add -1
        Date lastYear = cal.getTime();
        java.sql.Date sDate = null;
        if (iDate == null && i == 0) {
            iDate = lastYear.toInstant();
        } else if (iDate == null && i == 1) {
            iDate = today.toInstant();
        }

        if (iDate != null) {
            sDate = new java.sql.Date(Date.from(iDate).getTime());
        }
        return sDate;
    }
    public static Date convertToDate(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.parse(date);
        } catch (Exception ex) {
           ex.printStackTrace();
            return null;
        }
    }
}
