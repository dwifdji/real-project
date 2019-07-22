package com._360pai.seimi.util;

import org.apache.http.client.utils.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author liqun.wang 2015-5-28 下午04:43:04
 */
public class DateUtil {

    public static DateFormat yyyyMM() {
        return new SimpleDateFormat("yyyyMM");
    }

    public static DateFormat yyyyMMdd() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    public static DateFormat yyyyMMddHr() {
        return new SimpleDateFormat("yyyy-MM-dd");
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

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(STYLE_1);
        return sdf.format(new Date());
    }

    public static long getMini(String curDate, String overtime) {
        return getMini(curDate, overtime, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    }

    public static long getMini(String curDate, String overtime, String format) {
        return getMini(curDate, overtime, format, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static long getMini(String curDate, String overtime, String format, Locale local) {
        try {
            System.out.println(curDate + " - " + overtime);
            DateTime start = new DateTime(new SimpleDateFormat(format, local).parse(curDate));
            DateTime end = new DateTime(new SimpleDateFormat(format, local).parse(overtime));
            return new Duration(end, start).getStandardMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long getDay(String curDate, String overtime, String format) {
        try {
            System.out.println(curDate + " - " + overtime);
            DateTime start = new DateTime(new SimpleDateFormat(format).parse(curDate));
            DateTime end = new DateTime(new SimpleDateFormat(format).parse(overtime));
            return new Duration(end, start).getStandardDays();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long getMini(Date curDate, Date overtime) {
        try {
            System.out.println(curDate + " - " + overtime);
            // Sun Apr 24 19:28:42 CST 2016 - Mon Apr 25 09:30:36 CST 2016
            DateTime start = new DateTime(new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).format(curDate));
            DateTime end = new DateTime(new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).format(overtime));
            return new Duration(end, start).getStandardMinutes();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getDealDate() {
        Date date = new Date();
        while (todayIsZM(date)) {
            date = lastDay(date, 1);
        }
        return yyyyMMddHr().format(date);
    }

    public static boolean todayIsZM(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }
    }

    public static String getTodayYmd() {
        Date now = new Date();
        return DateUtils.formatDate(now, NORM_DATE_PATTERN);
    }

    // 当前时间是否在 当天 16 点 到 16点半之间
    public static boolean between16to16Half() {
        Date now = new Date();
        String ymd = DateUtils.formatDate(now, NORM_DATE_PATTERN);
        Date d16 = parse(ymd + " 16:00:00");
        Date next6 = parse(ymd + " 16:30:00");
        if (now.getTime() >= d16.getTime() && now.getTime() <= next6.getTime()) {
            return true;
        }
        return false;
    }

    public static int getCurrentHour() {
        return DateTime.now().getHourOfDay();
    }

    public static String getYearWeek(String dateStr, String format) {
        DateTime dt2 = DateTimeFormat.forPattern(format).parseDateTime(dateStr);
        return "" + dt2.getYear() + frontCompWithZore(dt2.weekOfWeekyear().get(), 2);
    }

    public static String getYearMonth(String dateStr, String format) {
        DateTime dt2 = DateTimeFormat.forPattern(format).parseDateTime(dateStr);
        return "" + dt2.getYear() + frontCompWithZore(dt2.getMonthOfYear(), 2);
    }


    public static String getYearWeek(Date dateStr) {
        DateTime dt2 = new DateTime(dateStr);
        return "" + dt2.getYear() + frontCompWithZore(dt2.weekOfWeekyear().get(), 2);
    }

    public static String getYearMonth(Date dateStr) {
        return new SimpleDateFormat("yyyyMM").format(dateStr);
    }

    public static String getYear(Date date) {
        return new SimpleDateFormat("yyyy").format(date);
    }

    public static String getYearMonthDay(String dateStr) {
        DateTime dt2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(dateStr);
        return dt2.toString("yyyy-MM-dd");
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     *
     * @param sourceDate
     * @param formatLength
     * @return 重组后的数据
     */
    public static String frontCompWithZore(int sourceDate, int formatLength) {
        /*
         * 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
         */
        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }


    /**
     * 获取下一个月
     */
    public static String nextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
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
        return (calendarNow.get(Calendar.YEAR) - calendarBirth
                .get(Calendar.YEAR))
                * 12
                + calendarNow.get(Calendar.MONTH)
                - calendarBirth.get(Calendar.MONTH);
    }

    public static String change(String dateStr, String format1, String format2) {
        Date date = parse(dateStr, format1);
        return format(date, format2);
    }

    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

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

    public final static String yyyyMdd = "yyyy-M-dd";

    // Wed Apr 11 16:18:42 +0800 2012
    public final static String U_C_PATTERN = "EEE MMM dd HH:mm:ss Z yyyy";


    /**
     * 标准日期格式
     */
    public final static String NORM_DATE_MONTH = "yyyy-MM";

    public final static String MONTH_DATE_CHINA = "MM月dd日";
    public final static String MONTH_DATE_CHINA_SIGN = "M月d日";

    /**
     * 标准时间格式
     */
    public final static String NORM_TIME_PATTERN = "HH:mm:ss";
    public final static String NOT_SP_TIME_PATTERN = "HHmmss";

    public final static String YY_MM_DD = "YY-MM-dd";
    public final static String yyyyMMdd = "yyyyMMdd";

    public final static String STYLE_1 = "yyyy-MM-dd HH:mm:ss";

    public final static String STYLE_2 = "yyyy-MM-dd";

    public final static String STYLE_3 = "yyyyMMdd";

    public final static String STYLE_4 = "yyyyMMddhh";

    public final static String STYLE_5 = "yyyyMMddhhmmssSSS"; //获取到毫秒

    public final static String STYLE_6 = "yyyy年MM月dd日HH时mm分ss秒";

    public final static String STYLE_7 = "yyyy年MM月dd日HH时mm分";

    public final static String STYLE_8 = "yyyy年MM月dd日";

    public final static String STYLE_9 = "hhmmss";

    public final static String STYLE_10 = "yyyy-MM";   //格式化时间为月份

    public final static String STYLE_11 = "dd";

    public final static String STYLE_13 = "MM-dd";

    public final static String STYLE_12 = "MM";

    public final static String STYLE_14 = "yyyy";

    public final static String STYLE_15 = "yyyy-MM-dd HH:mm";

    public final static String STYLE_16 = "HH:mm";

    public final static String STYLE_17 = "yyyy-MM-dd+HH:mm:ss";


    /**
     * 标准日期时间格式
     */
    public final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 特殊日期时间格式
     */
    public final static String SPECIAL_DATETIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
    /**
     * HTTP头中日期时间格式
     */
    public final static String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return formatDateTime(new Date());
    }

    /**
     * 当前日期，格式 yyyy-MM-dd
     *
     * @return 当前日期的标准形式字符串
     */
    public static String today() {
        return formatDate(new Date());
    }

    // ------------------------------------ Format start
    // ----------------------------------------------

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

    public static String format(String str, String format) {
        DateTime dt = null;
        try {
            dt = new DateTime(new SimpleDateFormat(format, new Locale("ENGLISH", "CHINA")).parse(str));
            return dt.toString("yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDateTime(Date date) {
        return new SimpleDateFormat(NORM_DATETIME_PATTERN).format(date);
    }

    /**
     * 格式化为Http的标准日期格式
     *
     * @param date 被格式化的日期
     * @return HTTP标准形式日期字符串
     */
    public static String formatHttpDate(Date date) {
        return new SimpleDateFormat(HTTP_DATETIME_PATTERN, Locale.US)
                .format(date);
    }

    /**
     * 格式 yyyy-MM-dd
     *
     * @param date 被格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat(NORM_DATE_PATTERN).format(date);
    }


    /**
     *  功能说明：格式化日期
     *  chuanqi  2014-11-29
     *  @param time 被格式化的日期   fmtStyle  格式化前的样式  wantStyle 格式化后的样式
     *  @return String 格式化后的日期
     *  @throws  ParseException
     * 最后修改时间：
     * 修改人：chuanqi
     * 修改内容：
     * 修改注意点：
     */
    public static String formatDate(String time, String fmtStyle, String wantStyle) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fmtStyle);
            Date date = sdf.parse(time);
            sdf = new SimpleDateFormat(wantStyle);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }

    }

    public static String formatDate(Date date, String fmtStyle) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fmtStyle);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }

    }

    // ------------------------------------ Format end
    // ----------------------------------------------

    // ------------------------------------ Parse start
    // ----------------------------------------------

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
            log.error("Parse " + dateString + " with format " + format
                    + " error!", e);
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseDateTime(String dateString) {
        try {
            return new SimpleDateFormat(NORM_DATETIME_PATTERN)
                    .parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format "
                    + new SimpleDateFormat(NORM_DATETIME_PATTERN).toPattern()
                    + " error!", e);
        }
        return null;
    }


    /**
     * 格式yyyy/MM/dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseSpecialDateTime(String dateString) {
        try {
            return new SimpleDateFormat(SPECIAL_DATETIME_PATTERN)
                    .parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format "
                    + new SimpleDateFormat(NORM_DATETIME_PATTERN).toPattern()
                    + " error!", e);
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd
     *
     * @param dateString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat(NORM_DATE_PATTERN).parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format "
                    + NORM_DATE_PATTERN + " error!", e);
        }
        return null;
    }

    /**
     * 格式HH:mm:ss
     *
     * @param
     * @return 日期对象
     */
    public static Date parseTime(String timeString) {
        try {
            return new SimpleDateFormat(NORM_TIME_PATTERN).parse(timeString);
        } catch (ParseException e) {
            log.error("Parse " + timeString + " with format "
                    + NORM_TIME_PATTERN + " error!", e);
        }
        return null;
    }

    /**
     * 格式：<br>
     * 1、yyyy-MM-dd HH:mm:ss<br>
     * 2、yyyy-MM-dd<br>
     * 3、HH:mm:ss>
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parse(String dateStr) {
        int length = dateStr.length();
        try {
            if (length == DateUtil.NORM_DATETIME_PATTERN.length()) {
                return parseDateTime(dateStr);
            } else if (length == DateUtil.NORM_DATE_PATTERN.length()) {
                return parseDate(dateStr);
            } else if (length == DateUtil.NORM_TIME_PATTERN.length()) {
                return parseTime(dateStr);
            }
        } catch (Exception e) {
            log.error("Parse " + dateStr + " with format normal error!", e);
        }
        return null;
    }

    /**
     * 昨天
     *
     * @return 昨天
     */
    public static Date yesterday() {
        return offsiteDate(new Date(), Calendar.DAY_OF_YEAR, -1);
    }


    public static String getYesterdayString() {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, -1);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

    }


    public static String getNDaysAgoString(int n) {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, -n);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

    }

    public static String getNDaysAgoTimeString(int n) {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, -n);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());

    }

    public static String getTodayString() {

        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, 0);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

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
     * 上N个月
     *
     * @return 上N个月
     */
    public static Date lastMouth(int n) {
        return offsiteDate(new Date(), Calendar.MONTH, -n);
    }

    /**
     * 下N个月
     *
     * @return 上N个月
     */
    public static Date nextMouth(Date date, int n) {
        return offsiteDate(date, Calendar.MONTH, n);
    }

    /**
     * 上N个day
     *
     * @return 上N个Day
     */
    public static Date lastDay(Date date, int n) {
        return offsiteDate(date, Calendar.DATE, -n);
    }

    /**
     * 下N个day
     *
     * @return 上N个Day
     */
    public static Date nextDay(Date date, int n) {
        return offsiteDate(date, Calendar.DATE, n);
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

    public static String addOneDay(String dateStr, String format) {
        try {
            Date date = new SimpleDateFormat(format).parse(dateStr);
            Date curDate = offsiteDate(date, Calendar.DAY_OF_YEAR, 1);
            return new SimpleDateFormat(format).format(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------------------ Offset end
    // ----------------------------------------------

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
     * 计算每次流水的账单时间
     *
     * @param waterdate 流水时间
     * @param datetime  网页上的一次账单时间
     * @return 流水的账单时间
     */
    public static Date myRepay(String waterdate, String datetime) {

        try {
            if (null == datetime || "".equals(datetime)) {
                return null;
            }
            String date[] = datetime.split("-");
            String water[] = waterdate.split("-");
            String mydate;
            if (Integer.valueOf(date[2]) < Integer.valueOf(water[2])) {
                mydate = nextMonth(new SimpleDateFormat("yyyy-MM").parse(waterdate)) + "-" + date[2];
            } else {
                mydate = water[0] + "-" + water[1] + "-" + date[2];
            }
            return new SimpleDateFormat("yyyy-MM-dd").parse(mydate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NORM_DATETIME_PATTERN);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    public static void main(String[] args) {
//		String dateStr = "2015-11-25 00:00:00";
//		System.out.println(getYearMonthDay(dateStr));
//
//		Date date = new Date();
//
//		System.out.println(getYearWeek(date));
//		System.out.println(getYearMonth(date));
//
//		System.out.println(frontCompWithZore(1, 1));
//
//		System.out.println(getCurrentHour());
//
//
//		DateTime dt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2016-08-20 09:20:10");
//		System.out.println(dt.getHourOfDay());
//
//
//		String res = DateUtil.format(DateUtil.yesterday(), DateUtil.NORM_DATE_PATTERN);
//		System.out.println(res);

        System.out.println("args = " + new Date(Long.valueOf("1541642400000")));

        System.out.print(DateUtil.myRepay("2017-05-18", "2017-6-12"));
    }

}
