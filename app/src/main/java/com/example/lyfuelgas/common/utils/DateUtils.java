package com.example.lyfuelgas.common.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 2016/10/09
 *
 * @author chenglin
 * @since 1.0.0
 */

public final class DateUtils {
    private DateUtils() {
    }

    public static final String FORMAT_DATE_DAY = "yyyy-MM-dd";
    public static final String FORMAT_DATE_TIME_TOTAL = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期转换为时间戳
     * @param timers
     * @return
     */
    public static long timeToStamp(String timers) {
        if(TextUtils.isEmpty(timers)){
            return System.currentTimeMillis();
        }
        Date d = new Date();
        long timeStemp = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sf.parse(timers);// 日期转换为时间戳
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp;
    }

    public static long getTodayEndTime(String time){
        if(TextUtils.isEmpty(time)){
            return System.currentTimeMillis();
        }
        Date d = new Date();
        long timeStemp = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sf.parse(time);// 日期转换为时间戳
            d.setHours(23);
            d.setMinutes(59);
            d.setSeconds(59);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp;
    }

    public static String formatDate(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }



    /**
     * 格式化精确到时分秒
     * @param time
     * @return
     */
    public static String formatDateTotal(long time){
        if (time == 0) {
            return "";
        } else {
            return formatDate(time, FORMAT_DATE_TIME_TOTAL);
        }
    }


    /**
     * 获取日期
     * @param time
     * @return
     */
    public static String getStringDate(long time) {
        if (time == 0) {
            return "";
        } else {
            return formatDate(time, FORMAT_DATE_DAY);
        }
    }

}