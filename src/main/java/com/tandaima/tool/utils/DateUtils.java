package com.tandaima.tool.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public final class DateUtils extends org.apache.commons.lang3.time.DateUtils{

    public final static String YMD_STR = "yyyy-MM-dd";
    public final static String YMD_HMS_STR = "yyyy-MM-dd HH:mm:ss";
    public final static String HMS_STR = "HH:mm:ss";
    public static final String YYYY = "yyyy";
    public static final String MM_DD = "MM-dd";

    public final static SimpleDateFormat HMS = new SimpleDateFormat("HH:mm:ss");
    public final static SimpleDateFormat YMD_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat YMD_HM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static SimpleDateFormat YMD = new SimpleDateFormat(YMD_STR);
    public final static SimpleDateFormat YMD_OR = new SimpleDateFormat("yyyyMMdd");
    public final static SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
    public final static SimpleDateFormat YMDHMS_SSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public final static SimpleDateFormat YMD_SSS = new SimpleDateFormat("yyyyMMddSSS");

    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    public static String dateTimeNow(){
        return YMDHMS.format(new Date());
    }

    public static String dateTimeNowYMDSSS(){
        return YMD_SSS.format(new Date());
    }
    public static String dateTimeNowSSS(){
        return YMDHMS_SSS.format(new Date());
    }
    public static String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }
    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor)
    {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor)
    {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }
    /**
     * 字符串转换日期
     * @param format 时间格式
     * @param date 时间字符串
     * @return 日期
     */
    public static Date parse(SimpleDateFormat format,String date){
        try {
            return format.parse(date);
        } catch (ParseException | NumberFormatException e) {
            return new Date();
        }
    }
    /**
     * 日期转换字符串
     * @param format 时间格式
     * @param date 时间
     * @return 日期字符串
     */
    public static String format(SimpleDateFormat format,Date date){
        return date == null?"":format.format(date);
    }

    /**
     * 计算两个时间差天数
     * @param endDate 当前时间
     * @param nowDate 计算的时间
     * @return 天数
     */
    public static long getDateDay(Date endDate, Date nowDate){
        long nd = 1000 * 24 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        return diff / nd;
    }
    /**
     * 获取两个时间中的每一天
     * @param  bigtimeDate 开始时间
     * @param endTimeDate 结束时间
     * @return
     * @throws ParseException
     */
   public static String[] getDays(Date bigtimeDate, Date endTimeDate) throws ParseException {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       String bigtimeStr=simpleDateFormat.format(bigtimeDate);
       String endTimeStr=simpleDateFormat.format(endTimeDate);
        Date bigtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bigtimeStr + " 00:00:00");
        Date endtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStr + " 00:00:00");
        //定义一个接受时间的集合
        List<Date> lDate = new ArrayList<>();
        lDate.add(bigtime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(bigtime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endtime);
        // 测试此日期是否在指定日期之后
        while (endtime.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        String[]datas = new String[lDate.size()];
        for(int i=0;i<lDate.size();i++)
        {

            datas[i]=new SimpleDateFormat("yyyy-MM-dd").format(lDate.get(i));
        }
        return datas;
    }
    /**
     * 计算两个时间差小时数
     * @param endDate 当前时间
     * @param nowDate 计算的时间
     * @return 小时数
     */
    public static long getDateHour(Date endDate, Date nowDate){
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        if(day > 0){
            return hour+(day*24);
        }
        return hour;
    }
    /**
     * 计算两个时间差分钟数
     * @param endDate 当前时间
     * @param nowDate 计算的时间
     * @return 分钟数
     */
    public static long getDateMin(Date endDate, Date nowDate){
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        if(day > 0){
            min = min+(day*24*60);
        }
        if(hour > 0){
            min = min + (hour*60);
        }
        return min;
    }

    /**
     * 计算当前时间距离当天凌晨的时间(秒)
     * 当天晚上23：59：59  -  就当前时间  = 秒
     * @throws ParseException
     */
    @SneakyThrows
    public static Integer timeDifference(){
        return Math.toIntExact((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 23:59:59").getTime() - new Date().getTime())/1000);
    }

    /**
     * 验证日期字符串是否合法
     * @param str 日期字符串
     * @param format 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
     * @return true合法/false不合法
     */
    public static boolean isValidDate(String str,SimpleDateFormat format) {
        boolean convertSuccess = true;
        try {
            if(StringUtils.isEmpty(str)){
                return false;
            }
            //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            //如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 判断是不是同一天
     * @param day1 时间1
     * @param day2 时间2
     * @return true是同一天/false不是同一天
     */
    public static boolean isSameDay(Date day1, Date day2) {
        if(day1 == null || day2 == null){
            return false;
        }
        return YMD.format(day1).equals(YMD.format(day2));
    }

    /**
     * 获取日期加多少天的字符串
     * @param date 日期
     * @param day 天（正数为加，负数为减）
     * @return 新增日期
     */
    public static String getDateAddDay(Date date,int day){
        //new一个Calendar类,把Date放进去
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //此时的日期为明天的日期,要实现昨天,日期应该减二
        calendar.add(Calendar.DATE, day);
        return YMD.format(calendar.getTime());
    }
    /**
     * 获取日期加多少天的Date有时分秒
     * @param date 日期
     * @param day 天（正数为加，负数为减）
     * @return 新增日期
     */
    public static Date getDateAddDayResult(Date date,int day){
        //new一个Calendar类,把Date放进去
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //此时的日期为明天的日期,要实现昨天,日期应该减二
        calendar.add(Calendar.DATE, day);
      String dateTime=  YMD_HMS.format(calendar.getTime());
        return parse(YMD_HMS,dateTime);
    }

    /**
     * 判断当前日期是否大于传递日期
     * 比对格式 2022-01-12
     * @param date 日期
     * @return true大于/false小于
     */
    public static boolean isGtNowYmd(Date date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YMD_STR, Locale.CHINESE);
        LocalDate myDate = LocalDate.parse(YMD.format(date), formatter);
        LocalDate nowDate = LocalDate.now();
        return nowDate.isAfter(myDate);
    }
    /**
     * 判断当前日期是否小于传递日期
     * 比对格式 2022-01-12
     * @param date 日期
     * @return true大于/false小于
     */
    public static boolean isLtNowYmd(Date date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YMD_STR, Locale.CHINESE);
        LocalDate myDate = LocalDate.parse(YMD.format(date), formatter);
        LocalDate nowDate = LocalDate.now();
        return myDate.isAfter(nowDate);
    }

    /**
     * 判断当前日期是否小于传递日期
     * 比对格式 2022-01-12
     * @return true大于/false小于
     */
    public static boolean isStartGtEnd(Date startDate,String endDateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YMD_STR, Locale.CHINESE);
        LocalDate myDate = LocalDate.parse(YMD.format(startDate), formatter);
        LocalDate nowDate = LocalDate.parse(endDateStr, formatter);
        return myDate.isAfter(nowDate);
    }

    /**
     * 判断当前日期是否大于传递日期
     * @param date 日期
     * @return true大于/false小于
     */
    public static boolean isGtNowYmdHms(Date date){
        Date nowDate = new Date();
        return nowDate.getTime() > date.getTime();
    }
    /**
     * 给时间date加上天数，小时，分钟
     * @param date   需要加的时间
     * @param day    要加的天数
     * @param hour   要加的小时
     * @param minute 要加的分钟
     * @param second 要加的秒数
     * 返回date
     */
    public static Date addDateTime(Date date, int day, int hour, int minute,int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);// 24小时制,加天
        cal.add(Calendar.HOUR, hour);// 24小时制 ,加小时
        cal.add(Calendar.MINUTE, minute);// 24小时制,加分钟
        cal.add(Calendar.SECOND, second);// 24小时制,加秒数
        date = cal.getTime();
        return date;
    }

    /**
     * 给时间date加上天数，小时，分钟
     * @param date   需要加的时间
     * @param day    要加的天数
     * 返回date
     */
    public static Date addDateDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);// 24小时制,加天
        date = cal.getTime();
        return date;
    }

    /**
     * 给时间date加上天数，小时，分钟
     * @param date   需要加的时间
     * @param hour    要加的小时
     * 返回date
     */
    public static Date addDateHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制,加小时
        date = cal.getTime();
        return date;
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    /***
     * 获取时分秒
     */
    public static  Date getStartTime(Date time,boolean isstart)
    {
        try {
            if(StringUtils.isNull(time))
            {
                return null;
            }

            if(isstart)
            {

                return   YMD_HMS.parse(YMD.format(time)+ " 00:00:00");
            }else
            {
                return    YMD_HMS.parse(YMD.format(time)+ " 23:59:59");
            }
        }catch (Exception e)
        {
            return null;
        }
    }
    public static DateRange getMonthRange(Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        DateRange.setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateRange.setMaxTime(endCalendar);

        return new DateRange(startCalendar.getTime(), endCalendar.getTime());
    }

//    public static void main(String[] args) throws ParseException {
//        DateRange daysRange = getDaysRange(new Date(), 1 - 1);
//
//        System.out.println(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", daysRange.getStartDate()));
//        System.out.println(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", daysRange.getEndDate()));
//    }

    public static DateRange getDaysRange(Date date, Integer days) {
        if (days == null || days < 0) days = 0;
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.add(Calendar.DAY_OF_YEAR, -days);
        DateRange.setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        DateRange.setMaxTime(endCalendar);

        return new DateRange(startCalendar.getTime(), endCalendar.getTime());
    }

    public static boolean isEffectiveDate(Date time, DateRange range) {
        if (time.getTime() == range.getStartDate().getTime() || time.getTime() == range.getEndDate().getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar begin = Calendar.getInstance();
        begin.setTime(range.getStartDate());

        Calendar end = Calendar.getInstance();
        end.setTime(range.getEndDate());
        return date.after(begin) && date.before(end);
    }

    @Data
    @AllArgsConstructor
    public static class DateRange {
        private Date startDate;
        private Date endDate;
        protected static void setMinTime(Calendar calendar){
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        protected static void setMaxTime(Calendar calendar){
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
            calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        }
    }

    /**
     * 获取到第二天分钟数
     * @param currentDate 时间
     * @return  到第二天分钟数
     */
    public static Integer getRemainSecondsOneDay(Date currentDate,ChronoUnit chronoUnit) {
        //使用plusDays加传入的时间加1天，将时分秒设置成0
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        //使用ChronoUnit.SECONDS.between方法，传入两个LocalDateTime对象即可得到相差的秒数
        long value = chronoUnit.between(currentDateTime, midnight)+1;
        return (int) value;
    }

    /**
     * 获取某个月第一天
     * @param month 第几个月(0本月/1上个月)
     * @return 某个月第一天
     */
    public static Date getMonthStart(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }

    /**
     * 获取某个月最后一天
     * @param monthParam 第一个月(0本月/1上个月)
     * @return 某个月最后一天
     */
    public static Date getMonthEnd(int monthParam){
        Calendar calendar = Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-monthParam);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND,59);
        return calendar.getTime();
    }

    /***
     * 判断时间是否是今天
     * @param date 时间
     * @return true是/false不是
     */
    public static Boolean judgeTimeIsToday(Date date) {
        if(date == null){
            return false;
        }
        return YMD.format(date).equals(YMD.format(new Date()));
    }

    /***
     * 判断时间是否是今天
     * @param date 时间
     * @return true是/false不是
     */
    public static Boolean judgeTimeIsToday(String date) {
        if(StringUtils.isEmpty(date)){
            return false;
        }
        return date.equals(YMD.format(new Date()));
    }
}
