package com.winback.arch.common.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by RuQ on 2018/8/24 10:32
 */
public class DateUtil {

    /**
     * 毫秒
     */
    public final static long MS = 1;
    /**
     * 每秒钟的毫秒数
     */
    public final static long SECOND_MS = MS * 1000;
    /**
     * 每分钟的毫秒数
     */
    public final static long MINUTE_MS = SECOND_MS * 60;
    /**
     * 每小时的毫秒数
     */
    public final static long HOUR_MS = MINUTE_MS * 60;
    /**
     * 每天的毫秒数
     */
    public final static long DAY_MS = HOUR_MS * 24;

    /**
     * 标准日期格式
     */
    public final static String NORM_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 新增日期格式
     */
    public final static String YYYY_DATE_PATTERN = "yyyy/MM/dd";

    public final static String MM_DATE_PATTERN = "MM/dd";


    public final static String XX_YMDHM_HR = "yyyy/MM/dd HH:mm";

    public final static String YEAY = "yyyy";

    /**
     * 标准日期格式
     */
    public final static String NORM_DATE_MONTH = "yyyy-MM";
    /**
     * 标准中文日期格式
     */
    public final static String NORM_CHINA_DATE_MONTH = "yy年-MM月";
    /**
     * 标准时间格式
     */
    public final static String NORM_TIME_PATTERN = "HH:mm:ss";
    /**
     * 标准日期时间格式
     */
    public final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * HTTP头中日期时间格式
     */
    public final static String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    public static final String FORMAT_All = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_LONG_NO_SPLIT = "yyyyMMddHHmmss";
    public static final String FORMAT_LONG_NO_SPLIT2 = "yyyyMMddHHmmssSSS";
    public static final String FORMAT_SHORT_NO_SPILT = "yyyyMMdd";
    public static final String WEEK[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static DateFormat yyyyMMdd() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    public static DateFormat yyyyMMddHHmmss() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    /**
     * 根据传入的日期返回今天是星期向几
     *
     * @return
     */
    public static String showTodayIs(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return WEEK[day - 1];
    }

    /**
     * 将一个日期转换成指定类型的字符串
     *
     * @param date   需要转换的日期
     * @param format 本次指定的格式化类型
     * @return
     */
    public static String formatDate2Str(Date date, String format) {
        if (date != null) {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        }
        return "";
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date, DateUtil.YEAY);
    }

    /**
     * 比较两个日期相差的天数
     */
    public static int getMargin(String date1, String date2) {
        int margin;
        try {
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos1 = new ParsePosition(0);
            Date dt1 = new SimpleDateFormat("yyyyMMdd").parse(date1, pos);
            Date dt2 = new SimpleDateFormat("yyyyMMdd").parse(date2, pos1);
            long l = dt1.getTime() - dt2.getTime();
            margin = (int) (l / (24 * 60 * 60 * 1000));
            return margin;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 比较两个日期相差的天数
     */
    public static int getMargin(Date date1, Date date2) {
        int margin;
        try {
            long l = date1.getTime() - date2.getTime();
            margin = (int) (l / (24 * 60 * 60 * 1000));
            return margin;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }


    /**
     * 比较两个日期相差的分钟
     */
    public static int getMarginMin(Date date1, Date date2) {
        int margin;
        try {
            long l = date1.getTime() - date2.getTime();
            margin = (int) (l / (60 * 1000));
            return margin;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 比较两个日期相差的时间
     */
    public static long getMarginMilliseconds(Date nowTime, Date beginTime) {
        long time = nowTime.getTime();
        long time1 = beginTime.getTime();
        return time - time1;
    }

    /**
     * 比较两个日期相差的秒数
     */
    public static long getMarginSeconds(Date date1, Date date2) {
        long time = date1.getTime();
        long time1 = date2.getTime();
        return (time - time1) / 1000;
    }

    /**
     * （A年-B年）* 12 - B月+A月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonths(Date date1, Date date2) {
        Calendar calendarBirth = Calendar.getInstance();
        calendarBirth.setTime(date1);
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(date2);
        return (calendarNow.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)) * 12
                + calendarNow.get(Calendar.MONTH) - calendarBirth.get(Calendar.MONTH);
    }

    /**
     * 获取今天日期字符串
     *
     * @return
     */
    public static String getTodayString() {
        Date today = new Date();

        String result = null;

        result = new SimpleDateFormat("yyyyMMdd").format(today);
        return result;
    }

    /**
     * 获取今天日期
     */
    public static Date getToday() {
        String todayStr = getTodayString();

        Date today = null;

        try {
            today = new SimpleDateFormat("yyyyMMdd").parse(todayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return today;
    }

    /**
     * 获取昨天日期字符串
     *
     * @return
     */
    public static String getYestodayString() {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, -1);

        String result = null;

        result = new SimpleDateFormat("yyyyMMdd").format(c.getTime());

        return result;
    }

    /**
     * 获取今天日期
     */
    public static Date getYestoday() {
        String yesdayStr = getYestodayString();

        Date yesday = null;

        try {
            yesday = new SimpleDateFormat("yyyyMMdd").parse(yesdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return yesday;
    }

    public static Date getYestodayDate() {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, -1);

        return c.getTime();
    }

    /**
     * 获取某种格式日期字符串
     *
     * @param currentDate
     * @param formate
     * @return
     */
    public static String date2String(Date currentDate, String formate) {
        if (currentDate == null) {
            return "";
        }

        String result = null;
        SimpleDateFormat formatdater = new SimpleDateFormat(formate);
        result = formatdater.format(currentDate);
        return result;
    }

    public static long getMini(String curDate, String overtime) {
        try {
            System.out.println(curDate + " - " + overtime);
            DateTime start = new DateTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(curDate));
            DateTime end = new DateTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(overtime));
            return new Duration(end, start).getStandardMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long getMill(String curDate, String overtime) {
        try {
            System.out.println(curDate + " - " + overtime);
            DateTime start = new DateTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(curDate));
            DateTime end = new DateTime(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(overtime));
            return new Duration(end, start).getMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getNormDateStr(Date date) {
        return new SimpleDateFormat(NORM_DATETIME_PATTERN).format(date);
    }

    public static String formatNormDate(Date date) {
        return new SimpleDateFormat(NORM_DATE_PATTERN).format(date);
    }

    public static String formatYYYYDate(Date date) {
        return new SimpleDateFormat(YYYY_DATE_PATTERN).format(date);
    }

    public static String formatMMDDDate(Date date) {
        return new SimpleDateFormat(MM_DATE_PATTERN).format(date);
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(NORM_DATETIME_PATTERN);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }


    // 获取类似"10月上旬"这样的简要时间
    public static String getDateBriefStr(Date curDate) {
        LocalDate date = new LocalDate(curDate);
        StringBuilder sb = new StringBuilder();
        sb.append(date.getMonthOfYear()).append("月");
        if (date.getDayOfMonth() <= 10)
            sb.append("上旬");
        else if (date.getDayOfMonth() <= 20)
            sb.append("中旬");
        else
            sb.append("下旬");

        return sb.toString();
    }

    // 获取类似"10月上旬-10月中旬"这样的简要时间段
    public static String getDateBriefDuration(Date startDate, Date endDate) {
        if (startDate == null || endDate == null
                || startDate.equals(new Date(0)) || endDate.equals(new Date(0)))
            return "";

        StringBuilder sb = new StringBuilder();
        String sd = getDateBriefStr(startDate);
        String ed = getDateBriefStr(endDate);
        if (sd.equals(ed))
            return sd;

        sb.append(sd).append("-").append(ed);
        return sb.toString();
    }

    // 获取类似"10月上旬-10月中旬"这样的简要时间段
    public static String getDateBriefDuration(String startDate, String endDate) {
        if (startDate == null || endDate == null)
            return "";
        Date date1 = DateTime.parse(startDate).toDate();
        Date date2 = DateTime.parse(endDate).toDate();
        return getDateBriefDuration(date1, date2);
    }


    // ------------------------------------ Format start ----------------------------------------------

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }


    // ------------------------------------ Parse start ----------------------------------------------

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateString 特定格式的日期
     * @param format     格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date parse(String dateString, String format) {
        try {
            return (new SimpleDateFormat(format)).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------ Offset start ----------------------------------------------

    /**
     * 昨天
     *
     * @return 昨天
     */
    public static Date yesterday() {
        return offsiteDate(new Date(), Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * 上周
     *
     * @return 上周
     */
    public static Date lastWeek() {
        return offsiteDate(new Date(), Calendar.WEEK_OF_YEAR, -1);
    }

    /**
     * 上个月
     *
     * @return 上个月
     */
    public static Date lastMouth() {
        return offsiteDate(new Date(), Calendar.MONTH, -1);
    }

    /**
     * 上一分钟
     *
     * @return 一分钟
     */
    public static Date nextMinute() {
        return nextMinute(1);
    }

    /**
     * 几分钟
     *
     * @return 几分钟
     */
    public static Date nextMinute(int min) {
        return offsiteDate(new Date(), Calendar.MINUTE, min);
    }

    /**
     * 下N年
     *
     * @return N年
     */
    public static Date nextYear(int num) {
        return offsiteDate(new Date(), Calendar.YEAR, num);
    }

    /**
     * 下N天
     *
     * @return N天
     */
    public static Date nextDay(int num) {
        return offsiteDate(new Date(), Calendar.DAY_OF_YEAR, num);
    }

    /**
     * 下N月
     *
     * @return N个月
     */
    public static Date nextMouth(int num) {
        return offsiteDate(new Date(), Calendar.MONTH, num);
    }

    public static void main(String[] args) throws Exception {
//		Calendar c = Calendar.getInstance();
//
//		c.add(Calendar.DATE, -1);
//
//		System.out.println(getMini(new Date().toString(), c.getTime().toString()));
//
//		Date sdate = nextMinute();
//		System.out.println(format(sdate, "yyyy-MM-dd HH:mm:ss"));

//        Date date = nextMouth(1);
//        System.out.println(format(date, "yyyy-MM-dd HH:mm:ss"));
//
//        boolean validDate = isValidDate("2018-13-01");
//        System.out.println("validDate = " + validDate);
//        Date date1 = new Date();
//        Date date2 = DateUtil.nextDay(1);
//
//        long diff = DateUtil.diff(date1, date2, 86400000);
//        System.out.println(diff);

        String dateStr = "2018-11-22 1:21:28";
        String dateStr2 = "2018-11-30 1:21:27";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

//            System.out.println("两个日期的差距：" + differentDays(date,date2));
            System.out.println("两个日期的差距：" + differentDaysByMillisecond(date, date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取指定日期偏移指定时间后的时间
     *
     * @param date          基准日期
     * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
     * @param offsite       偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     */
    public static Date offsiteDate(Date date, int calendarField, int offsite) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, offsite);
        return cal.getTime();
    }
    // ------------------------------------ Offset end ----------------------------------------------

    /**
     * 判断两个日期相差的时长<br/>
     * 返回 minuend - subtrahend 的差
     *
     * @param subtrahend 减数日期
     * @param minuend    被减数日期
     * @param diffField  相差的选项：相差的天、小时
     * @return 日期差
     */
    public static long diff(Date subtrahend, Date minuend, long diffField) {
        long diff = minuend.getTime() - subtrahend.getTime();
        return diff / diffField;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：纳秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，纳秒
     */
    public static long spendNt(long preTime) {
        return System.nanoTime() - preTime;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：毫秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，毫秒
     */
    public static long spendMs(long preTime) {
        return System.currentTimeMillis() - preTime;
    }


    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }


    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    /**
     * 将字符数据格式化
     *
     * @param
     * @return
     */
    public static String formatStrDate(String strDate, int calendar) {

        Date date;

        SimpleDateFormat df = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);
        try {

            date = df.parse(strDate);

        } catch (Exception e) {

            try {
                SimpleDateFormat dt = new SimpleDateFormat(DateUtil.NORM_DATE_PATTERN);
                date = dt.parse(strDate);
            } catch (Exception ex) {
                date = new Date();
            }

        }

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        //当为月时要+1
        if (Calendar.MONTH == calendar) {

            return String.valueOf(cal.get(calendar) + 1);

        }

        return String.valueOf(cal.get(calendar));

    }


    /**
     * 获取系统时间
     */
    public static Date getSysTime() {

        return new Date();

    }

    /**
     * 获取给定日期最晚时刻
     */
    public static Date getEndDate(Date date) {
        String yyyyMMdd = DateUtil.format(date, DateUtil.NORM_DATE_PATTERN);
        Date parse = DateUtil.parse(yyyyMMdd + " 23:59:59", DateUtil.NORM_DATETIME_PATTERN);
        return parse;
    }

    /**
     * 获取给定日期最晚时刻
     */
    public static boolean isToday(Date date) {
        return formatNormDate(new Date()).equals(formatNormDate(date));
    }


    /**
     * 返回中文年月格式
     */
    public static String formatChinaMonth(Date date) {
        return DateUtil.format(date, DateUtil.NORM_CHINA_DATE_MONTH);
    }

}