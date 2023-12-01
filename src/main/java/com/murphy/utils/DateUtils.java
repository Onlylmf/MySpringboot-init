package com.murphy.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Log4j2
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM", "yyyyMMddHHmmss", "yyyyMMdd", "yyyyMM", "yyyy"};

    /**
     * 用这个方法将字符串转成Date
     *
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr) throws ParseException {
        return parseDate(dateStr, parsePatterns);
    }

    /**
     * 将时间转换成 默认格式 "yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将时间转换成用户定义的格式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {

        return DateFormatUtils.format(date, pattern);
    }

    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    public static Date defaultTime() {
        Calendar instance = Calendar.getInstance();
        instance.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        return instance.getTime();
    }

    /**
     * @param date 日期
     * @return 年的部分 2019年的话 返回19
     */
    public static byte[] getTimeByteArray(Date date) {
        byte[] array = {};
        Integer year = DateUtil.year(date);
        year -= 2000;
        array = ArrayUtils.add(array, year.byteValue());// 添加1位年
        Integer month = DateUtil.month(date) + 1;
        array = ArrayUtils.add(array, month.byteValue());// 添加1位月
        Integer day = DateUtil.dayOfMonth(date);
        array = ArrayUtils.add(array, day.byteValue());// 添加1位日
        Integer hour = DateUtil.hour(date, true);
        array = ArrayUtils.add(array, hour.byteValue());// 添加1位时
        Integer minute = DateUtil.minute(date);
        array = ArrayUtils.add(array, minute.byteValue());// 添加1位分
        Integer second = DateUtil.second(date);
        array = ArrayUtils.add(array, second.byteValue());// 添加1位秒
        Integer dayOfWeek = DateUtil.dayOfWeek(date);
        array = ArrayUtils.add(array, dayOfWeek.byteValue());// 添加1位秒
        return array;
    }

    public static Date byteArrayToTime(byte[] date) {
        if (!(date.length == 7))
            return defaultTime();
        Calendar instance = Calendar.getInstance();

        instance.set(2000 + date[0], date[1] - 1, date[2], date[3], date[4], date[5]);
        return instance.getTime();
    }

    public static Date date() {
        return new Date();
    }

//    public static Date utcDate() {
//        String timeZone = LoginUserUtil.getTimeZone();
//        if (timeZone != null) {
//            return utcDate(timeZone);
//        }
//        return new Date();
//    }

//    public static Date deptUtcDate(String deptId) {
//        try {
//            return utcDate(SpringContextUtils.getBean(SysDeptService.class).selectByPrimaryKey(deptId).getConfig().getTimeZone());
//        } catch (Exception e) {
//            log.info(e);
//            return new Date();
//        }
//    }

    public static Date utcDate(String timeZone) {
        String date = getDate("yyyy-MM-dd HH:mm:ss", timeZone);
        try {
            return parseDate(date);
        } catch (ParseException e) {
            log.info(e);
        }

        return new Date();
    }

    public static String getDate(String pattern, String timeZone) {
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        return DateFormatUtils.format(new Date(), pattern, tz);
    }

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return DateUtil.now();
    }

    /**
     * @return : Date
     * @Description: 获取一天的开头
     */
    public static Date getStartOfDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.MINUTE, 0);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.SECOND, 0);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.MILLISECOND, 0);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }

    public static Date getFirstDayOfMouth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.MINUTE, 0);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.SECOND, 0);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.MILLISECOND, 0);//设置为1号,当前日期既为本月第一天
        return c.getTime();
    }
    


    public static Date getLastDayOfMouth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);//加一个月
        c.set(Calendar.DAY_OF_MONTH, 0);//上个月的最后一天
        c.set(Calendar.HOUR_OF_DAY, 23);//23
        c.set(Calendar.MINUTE, 59);//59
        c.set(Calendar.SECOND, 59);//59
        c.set(Calendar.MILLISECOND, 999);//59
        return c.getTime();
    }

    /**
     * 判断两个日期相差的天数<br>
     *
     * <pre>
     * 有时候我们计算相差天数的时候需要忽略时分秒。
     * 比如：2016-02-01 23:59:59和2016-02-02 00:00:00相差一秒
     * 如果isReset为<code>false</code>相差天数为0。
     * 如果isReset为<code>true</code>相差天数将被计算为1
     * </pre>
     *
     * @param suspendTime 起始日期
     * @param date   结束日期
     * @param b   是否重置时间为起始时间
     * @return 日期差
     * @since 3.0.1
     */
    public static long betweenDay(Date suspendTime, Date date, boolean b) {

        return DateUtil.betweenDay(suspendTime, date, b);
    }

    /**
     * 将日期字符串转换为{@link DateTime}对象，格式：<br>
     * <ol>
     * <li>yyyy-MM-dd HH:mm:ss</li>
     * <li>yyyy/MM/dd HH:mm:ss</li>
     * <li>yyyy.MM.dd HH:mm:ss</li>
     * <li>yyyy年MM月dd日 HH时mm分ss秒</li>
     * <li>yyyy-MM-dd</li>
     * <li>yyyy/MM/dd</li>
     * <li>yyyy.MM.dd</li>
     * <li>HH:mm:ss</li>
     * <li>HH时mm分ss秒</li>
     * <li>yyyy-MM-dd HH:mm</li>
     * <li>yyyy-MM-dd HH:mm:ss.SSS</li>
     * <li>yyyyMMddHHmmss</li>
     * <li>yyyyMMddHHmmssSSS</li>
     * <li>yyyyMMdd</li>
     * </ol>
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parse(String dateStr) {
		return DateUtil.parse(dateStr).toJdkDate();
	}

    /**
     * 获取日期对应的年份
     */
    public static int getYearByDate(Date date) {
        return DateUtil.year(date);
    }

    public static int getMonthByDate(Date date) {
        return DateUtil.month(date) + 1;
    }


    public static String buildDateFormat(String dateFormat) {
        switch (dateFormat) {
            case "year":
                return "%Y";
            case "month":
                return "%Y%m";
            case "day":
                return "%Y%m%d";
            case "weekend":
                return "%Y%u";
            default:
                return "%Y%m%d";
        }
    }
}
