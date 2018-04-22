package com.hy.lang.mercury.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtils {
    public static String yyyyMMdd = "yyyyMMdd";

    /**
     * 与当前时间相比，小于30min
     *
     * @param time
     * @return
     */
    public static boolean inSessionTime(Date time) {
        long time_l = time.getTime();
        long now_l = new Date().getTime();
        if ((now_l - time_l) <= 30 * 60 * 1000) {
            return true;
        }
        return false;
    }

    public static Date getLimitDate(Date activateDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activateDate);
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    public static String[] getDaysOfMonth() {
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance();
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.get(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = String.valueOf(year) + "/" + month + "/" + i;
            //System.out.println(aDate);
            list.add(aDate);
        }
        String[] strings = new String[list.size()];

        list.toArray(strings);
        return strings;
    }

    public static void main(String[] args) {
        System.out.println(getDaysOfMonth());
    }

    public static Date formatDate(Long ms) {
        if (ms == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        return calendar.getTime();
    }

    public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";


    public static String dateToString(Date source, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }

    public static Date format(String ot, String temp) {
        SimpleDateFormat sdf = new SimpleDateFormat(temp);
        Date date = null;
        try {
            date = sdf.parse(ot);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
