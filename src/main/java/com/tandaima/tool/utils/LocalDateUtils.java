package com.tandaima.tool.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public final class LocalDateUtils{

    public final static String YMD_STR = "yyyy-MM-dd";
    public final static String YMD_HMS_STR = "yyyy-MM-dd HH:mm:ss";
    public final static String HMS_STR = "HH:mm:ss";
    public static final String YYYY = "yyyy";
    public static final String MM_DD = "MM-dd";

    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前时间戳
     */
    public static Long getLocalTime(){
        // 将当前时间转为时间戳
        return getLocalTime(LocalDateTime.now());
    }

    /**
     * 获取当前时间戳
     */
    public static Long getLocalTime(LocalDateTime localDateTime){
        return localDateTime.toEpochSecond(ZoneOffset.ofHours(8));
    }

    /**
     * 时间戳转日期
     */
    public static LocalDateTime getLocalDateTime(Long dataTime){
        if(dataTime == null){
            return null;
        }
        // 将时间戳转为当前时间
        return LocalDateTime.ofEpochSecond(dataTime, 0, ZoneOffset.ofHours(8));
    }

    /**
     * 获取计算后的日期
     */
    public static LocalDateTime getLocalDateAddDay(int day){
        return LocalDateTime.now().plusDays(day);
    }

    /**
     * 获取计算后的时间戳
     */
    public static Long getLocalTimeAddDay(int day){
        return getLocalTime(LocalDateTime.now().plusDays(day));
    }

    /**
     * 获取计算后的时间戳
     */
    public static Long getLocalTimeAddMinute(int minute){
        return getLocalTime(LocalDateTime.now().plusMinutes(minute));
    }

    /**
     * 日期转换字符串
     */
    public static String dateFormat(LocalDateTime localDateTime,String formatStr){
        return localDateTime.format(DateTimeFormatter.ofPattern(formatStr));
    }

    /**
     * 获取计算后的时间戳
     */
    public static Long getLocalTimeAddHours(int hours){
        return getLocalTime(LocalDateTime.now().plusHours(hours));
    }

    /**
     * 到现在的时间相差分钟
     * @param endDate 截至时间
     * @return 相差分钟数
     */
    public static long betweenMinutes(LocalDateTime endDate){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now,endDate);
//        long days = duration.toDays(); //相差的天数
//        long hours = duration.toHours();//相差的小时数
        long minutes = duration.toMinutes();//相差的分钟数
        long millis = duration.toMillis();//相差毫秒数
//        long nanos = duration.toNanos();//相差的纳秒数
        long seconds =  millis/10000;//相差的秒数
        if(seconds >0){
            minutes ++;
        }
        if(minutes<=0){
            minutes = 0;
        }
        return minutes;
    }

    /**
     * 到现在的时间相差分钟
     * @param endDate 截至时间
     * @return 相差分钟数
     */
    public static long betweenSeconds(LocalDateTime endDate){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now,endDate);
        long millis = duration.toMillis();//相差毫秒数
        return millis/10000;//相差的秒数
    }
}
