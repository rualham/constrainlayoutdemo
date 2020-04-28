package com.android.sharedemo.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateUtil {
    //private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
    //private static SimpleDateFormat dateFormat_1 = new SimpleDateFormat("yyyy-MM-dd");
    //public static SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy年MM月dd日");

    // Grace style
    public static final String PATTERN_GRACE = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_GRACE_NORMAL = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_GRACE_SIMPLE = "yyyy/MM/dd";
    // Classical style
    public static final String PATTERN_CLASSICAL = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_CLASSICAL_NORMAL = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_CLASSICAL_SIMPLE = "yyyy-MM-dd";
    public static final String PATTERN_CLASSICAL_TWO = "yy-MM-dd";

    // Other style
    public static final String PATTERN_OTHER = "yyyy年MM月dd日";
    public static final String PATTERN_OTHER_SIMPLE = "yyyy年M月d日";

    private static SimpleDateFormat returnSimple(String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault());
    }

    /**
     * 根据指定格式将指定日期格式化成字符串
     *
     * @param date    指定日期
     * @param pattern 指定格式
     * @return 返回格式化后的字符串
     */
    public static String mFormat(Date date, String pattern) {
        return returnSimple(pattern).format(date);
    }

    /**
     * 根据指定格式将指定字符串解析成日期
     *
     * @param str     指定日期
     * @param pattern 指定格式
     * @return 返回解析后的日期
     */
    public static Date mParse(String str, String pattern) {
        try {
            return returnSimple(pattern).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //by zhaowei
    public static String showCurrentDate(Date date) {
        if (isToday(date))
            return "今天";
        else if (isYesterday(date))
            return "昨天 ";
        else
            //return dateFormat_1.format(date);
            return mFormat(date, PATTERN_CLASSICAL_SIMPLE);
    }

    public static String convertDateToString(long seconds, String format) {
        if (StringUtil.equalsNullOrEmpty(format)) {
            format = PATTERN_CLASSICAL_SIMPLE;
        }
        //SimpleDateFormat sdf = new SimpleDateFormat(format);
        //return sdf.format(new Date(seconds));
        return mFormat(new Date(seconds), format);
    }

    public static long convertStringToDate(String time) {
        Date date = mParse(time, PATTERN_CLASSICAL);
        return date != null ? date.getTime() : 0;
    }

    /**
     * 2016-07-14 lhw
     * yyyy.MM.dd HH:mm:ss ；yyyy-MM-dd HH:mm:ss ；yyyy/MM/dd HH:mm:ss
     */
    public static String toStringDateFormat(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        String mPattern = "yyyy.MM.dd";//
        try {
            if (dateStr.contains(".")) {
                mPattern = "yyyy.MM.dd";
            } else if (dateStr.contains("-")) {
                mPattern = "yyyy-MM-dd";
            } else if (dateStr.contains("/")) {
                mPattern = "yyyy/MM/dd";
            }
            SimpleDateFormat format = returnSimple(mPattern);
            String lastStr = format.format(format.parse(dateStr));
            if (lastStr.contains("-")) {
                lastStr = lastStr.replace("-", ".");
            }
            return lastStr;
        } catch (Exception e) {
            return "";
        }
    }

    public static Date toDate(String str) {
        String pattern;
        try {
            if (str.contains(".")) {
                if (str.contains(" ") && str.contains(":")) {
                    pattern = "yyyy.MM.dd HH:mm:ss";
                } else {
                    pattern = "yyyy.MM.dd";
                }
            } else if (str.contains("-")) {
                if (str.contains(" ") && str.contains(":")) {
                    pattern = "yyyy-MM-dd HH:mm:ss";
                } else {
                    pattern = "yyyy-MM-dd";
                }
            } else if (str.contains("/")) {
                if (str.contains(" ") && str.contains(":")) {
                    pattern = "yyyy/MM/dd HH:mm:ss";
                } else {
                    pattern = "yyyy/MM/dd";
                }
            } else {
                long milliseconds = Long.parseLong(str);
                return new Date(milliseconds * 1000);
            }
            return returnSimple(pattern).parse(str);
        } catch (Exception e) {
            return new Date();
        }
    }

    private static final long ONE_DAY_LONG = 24 * 60 * 60 * 1000;

    public static boolean isToday(Date date1) {
        return (date1.getTime() / ONE_DAY_LONG) == (System.currentTimeMillis() / ONE_DAY_LONG);
    }

    public static boolean isYesterday(Date date1) {
        return (date1.getTime() / ONE_DAY_LONG + 1) == (System.currentTimeMillis() / ONE_DAY_LONG);
    }

    public static String getCurrentDay(Date date) {
        //return dateFormat_1.format(date);
        return mFormat(date, PATTERN_CLASSICAL_SIMPLE);
    }

    public static String dataMonthDayYear(String data) {
        if (StringUtil.equalsNullOrEmpty(data)) {
            return "";
        }
        String year = data.substring(0, 4);
        String month = data.substring(5, 7);
        String day = data.substring(8, data.length());
        return month + "月" + day + "日    " + year + "年";
    }

    public static String dataShow(String data) {
        if (StringUtil.equalsNullOrEmpty(data)) {
            return "";
        }
        // String year = data.substring(0, 4);
        String month = data.substring(5, 7);
        String day = data.substring(8, data.length());
        return month + "月" + day + "日";
    }

    public static String dataShowNew(String date) {
        if (StringUtil.equalsNullOrEmpty(date)) {
            return "";
        }
        if (date.contains("-")) {
            String month = date.substring(5, 7);
            String day = date.substring(8, date.length());
            return month + "月" + day + "日";
        }
        return date;
    }

    public static String dataYearMonthDAY(String data) {
        if (StringUtil.equalsNullOrEmpty(data)) {
            return "";
        }
        String year = data.substring(0, 4);
        String month = data.substring(5, 7);
        String day = data.substring(8, data.length());
        return year + "年" + month + "月" + day + "日";
    }

    public static int todayBookNotice(Calendar drawCal) {
        Calendar todayCal = Calendar.getInstance();
        int year = drawCal.get(Calendar.YEAR);
        int month = drawCal.get(Calendar.MONTH);
        int day = drawCal.get(Calendar.DAY_OF_MONTH);
        int ty = todayCal.get(Calendar.YEAR);
        int tm = todayCal.get(Calendar.MONTH);
        int td = todayCal.get(Calendar.DAY_OF_MONTH);
        if (year == ty && month == tm && day == td) {
            return 0;
        }
        todayCal.add(Calendar.DAY_OF_MONTH, -1);
        ty = todayCal.get(Calendar.YEAR);
        tm = todayCal.get(Calendar.MONTH);
        td = todayCal.get(Calendar.DAY_OF_MONTH);
        if (year == ty && month == tm && day == td) {
            return -1;
        } else if (drawCal.compareTo(todayCal) < 0) {
            return -2;
        } else {
            todayCal.add(Calendar.DAY_OF_MONTH, 2);
            ty = todayCal.get(Calendar.YEAR);
            tm = todayCal.get(Calendar.MONTH);
            td = todayCal.get(Calendar.DAY_OF_MONTH);
            if (year == ty && month == tm && day == td) {
                return 1;
            } else if (drawCal.compareTo(todayCal) > 0) {
                return 2;
            }
        }
        return -2;
    }

    /**
     * 获取两个日期相差几天
     */
    public static long getSubtractDays(String startTime, String endTime, String format) {
        if (StringUtil.equalsNullOrEmpty(format)) {
            format = PATTERN_CLASSICAL_SIMPLE;
        }
        long day = 0;
        if (StringUtil.equalsNullOrEmpty(startTime) || StringUtil.equalsNullOrEmpty(endTime)) {
            return day;
        }
        SimpleDateFormat sd = returnSimple(format);
        try {
            //获得两个时间的毫秒时间差异
            long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / (1000 * 24 * 60 * 60);//计算差多少天
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static int todayBookNotice() {
        Calendar cal = Calendar.getInstance();
        int nowTime = cal.get(Calendar.HOUR_OF_DAY);
        return 17 - nowTime;
    }

    public static String tomorrowDate() {
        return tomorrowDate(false);
    }

    public static String tomorrowDate(boolean isNoChinaFormat) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        if (isNoChinaFormat) {
            //return dateFormat_1.format(date);
            return mFormat(date, PATTERN_CLASSICAL_SIMPLE);
        } else {
            //return dateFormat.format(date);
            return mFormat(date, PATTERN_OTHER_SIMPLE);
        }
    }

    public static String todayDate() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        //return dateFormat.format(date);
        return mFormat(date, PATTERN_OTHER_SIMPLE);
    }

    public static String yearMonthDay(int year, int month, int day) {
        String mMonth = month + 1 < 10 ? "0" + (month + 1) : Integer.toString(month + 1);
        String mDay = day < 10 ? "0" + day : day + "";
        return year + "-" + mMonth + "-" + mDay;
    }

    public static String getWeekValue(int week) {
        L.d("DateUtil getWeekValue :" + week);
        String weekText = "周";
        switch (week) {
            case 1:
                weekText = weekText + "日";
                break;
            case 2:
                weekText = weekText + "一";
                break;
            case 3:
                weekText = weekText + "二";
                break;
            case 4:
                weekText = weekText + "三";
                break;
            case 5:
                weekText = weekText + "四";
                break;
            case 6:
                weekText = weekText + "五";
                break;
            case 7:
                weekText = weekText + "六";
                break;
            default:
                weekText = weekText + String.valueOf(week);
                break;
        }
        return weekText;
    }

    /**
     * 获得今天的日期
     */
    public static String getCurrentDay() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return yearMonthDay(year, month, day);
    }

    /**
     * 获得明天的日期
     */
    public static String getTomorrowDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        //return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return mFormat(c.getTime(), PATTERN_CLASSICAL_SIMPLE);
    }

    /**
     * 获得当前的时间
     */
    public static String getCurrentTime() {
        //Date currentTime = new Date();
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //return df.format(currentTime);
        return mFormat(new Date(), PATTERN_CLASSICAL);
    }

    /**
     * 获得随机的时间在一个时间范围内的
     */
    public static String getRandomDate(String beginDate, String endDate) {
        Date randomDate = randomDate(beginDate, endDate);
        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //return format.format(randomDate);
        return mFormat(randomDate, PATTERN_CLASSICAL);
    }

    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = returnSimple(PATTERN_CLASSICAL);
            Date start = format.parse(beginDate);// 开始日期
            Date end = format.parse(endDate);// 结束日期
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtnn = begin + (long) (Math.random() * (end - begin));
        if (rtnn == begin || rtnn == end) {
            return random(begin, end);
        }
        return rtnn;
    }

    /**
     * 获得指定日期的前一天
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Date date = null;
        try {
            date = returnSimple(PATTERN_CLASSICAL_TWO).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day - 1);
            return returnSimple(PATTERN_CLASSICAL_SIMPLE).format(c.getTime());
        }
        return null;
    }

    /**
     * 获得指定日期的后一天
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Date date = null;
        try {
            date = returnSimple(PATTERN_CLASSICAL_TWO).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            return returnSimple(PATTERN_CLASSICAL_SIMPLE).format(c.getTime());
        }
        return null;
    }

    /**
     * 获得指定日期的后的第3天
     */
    public static String getSpecifiedDayThirdDay(String specifiedDay) {
        Date date = null;
        try {
            date = returnSimple(PATTERN_CLASSICAL_TWO).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 3);
            return returnSimple(PATTERN_CLASSICAL_SIMPLE).format(c.getTime());
        }
        return null;
    }

    /**
     * 获得指定日期是周几
     */
    public static String dayForWeek(String pTime) {
        Date tmpDate = null;
        try {
            tmpDate = returnSimple(PATTERN_CLASSICAL_SIMPLE).parse(pTime, new ParsePosition(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tmpDate != null) {
            Calendar mCal = Calendar.getInstance();
            Calendar cal = Calendar.getInstance();
            cal.setTime(tmpDate);
            if (compare_calendar(mCal, cal) == 0) {
                return "今天";
            }
            mCal.add(Calendar.DAY_OF_MONTH, 1);
            if (compare_calendar(mCal, cal) == 0) {
                return "明天";
            }
            mCal.add(Calendar.DAY_OF_MONTH, 1);
            if (compare_calendar(mCal, cal) == 0) {
                return "后天";
            }
            return getWeekValue(cal.get(Calendar.DAY_OF_WEEK));
        }
        return null;
    }

    public static String dayForHotelWeek(String pTime) {
        Date tmpDate = null;
        try {
            tmpDate = returnSimple(PATTERN_CLASSICAL_SIMPLE).parse(pTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (tmpDate != null) {
            Calendar mCal = Calendar.getInstance();
            Calendar cal = Calendar.getInstance();
            cal.setTime(tmpDate);
            if (compare_calendar(mCal, cal) == 0) {
                return "今天";
            }
            mCal.add(Calendar.DAY_OF_MONTH, 1);
            if (compare_calendar(mCal, cal) == 0) {
                return "明天";
            }
            return getWeekValue(cal.get(Calendar.DAY_OF_WEEK));
        }
        return null;
    }

    /**
     * 判断两个日期大小 第一个比第二个大返回(一在二后)-1，第二个比第一个大（一在二前）返回1，相等返回0
     */
    public static int compare_date(String date1, String date2) {
        DateFormat df = returnSimple(PATTERN_CLASSICAL_SIMPLE);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() == dt2.getTime()) {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断两个时间大小 第一个比第二个大返回(一在二后)-1，第二个比第一个大（一在二前）返回1，相等返回0
     */
    public static int compare_Time(String DATE1, String DATE2) {
        DateFormat df = returnSimple(PATTERN_CLASSICAL);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                L.d("dt1 在dt2后");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                L.d("dt1在dt2前");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static int compare_calendar(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return -1;
        }
        if ((cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                && (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH))) {
            return 0;
        }
        return -1;
    }

    /**
     * 获得指定日期的日
     */
    public static String getDay(String date) {
        Date myDate = null;
        try {
            myDate = returnSimple(PATTERN_CLASSICAL_SIMPLE).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (myDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(myDate);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String days;
            if (day < 10) {
                days = "0" + Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            } else {
                days = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
            }
            return days;
        }
        return null;
    }

    /**
     * 获得指定日期的月
     */
    public static String getMonth(String date) {
        Date myDate = null;
        try {
            myDate = returnSimple(PATTERN_CLASSICAL_SIMPLE).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (myDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(myDate);
            return Integer.toString(cal.get(Calendar.MONTH) + 1) + "月";
        }
        return null;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     */
    public static int dayForWeekOther(String pTime) {
        Date myDate = null;
        try {
            myDate = returnSimple(PATTERN_CLASSICAL_SIMPLE).parse(pTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (myDate != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(myDate);
            int dayForWeek;
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
            return dayForWeek;
        }
        return -1;
    }

    public static String getWeekValueOther(int week) {
        L.d("DateUtil getWeekValue :" + week);
        if (week <= 0) {
            return "";
        }
        String weekText = "周";
        switch (week) {
            case 1:
                weekText = weekText + "一";
                break;
            case 2:
                weekText = weekText + "二";
                break;
            case 3:
                weekText = weekText + "三";
                break;
            case 4:
                weekText = weekText + "四";
                break;
            case 5:
                weekText = weekText + "五";
                break;
            case 6:
                weekText = weekText + "六";
                break;
            case 7:
                weekText = weekText + "日";
                break;
            default:
                break;
        }
        return weekText;
    }

    public static Map<Integer, String> dateBellowKeyInteger(String date1, String date2) {
        HashMap<Integer, String> dateMap = new HashMap<Integer, String>();
        Calendar cal1 = getStringCal(date1);
        Calendar cal2 = getStringCal(date2);
        Calendar tempCal;
        if (cal1.after(cal2)) {
            tempCal = cal1;
            cal1 = cal2;
            cal2 = tempCal;
        }
        boolean flag = true;
        int i = 0;
        while (flag) {
            if (cal1.before(cal2) || compare_calendar(cal1, cal2) == 0) {
                dateMap.put(i, getCalString(cal1));
            } else {
                flag = false;
            }
            cal1.add(Calendar.DAY_OF_MONTH, 1);
            i++;
        }
        return dateMap;
    }

    public static long getSumDay(String checkInDate, String leaveDate) {
        long day = 0;
        DateFormat dateFormat = returnSimple(PATTERN_CLASSICAL_SIMPLE);
        try {
            day = (dateFormat.parse(leaveDate).getTime() - dateFormat.parse(checkInDate).getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static Map<String, String> datebellow(String date1, String date2) {
        Map<String, String> dateMap = new HashMap<>();
        Calendar cal1 = getStringCal(date1);
        Calendar cal2 = getStringCal(date2);
        Calendar tempCal;
        if (cal1.after(cal2)) {
            tempCal = cal1;
            cal1 = cal2;
            cal2 = tempCal;
        }
        boolean flag = true;
        while (flag) {
            L.d("DateUtil datebellow:" + getCalString(cal1) + ":" + cal1.before(cal2) + ":" +
                    (compare_calendar(cal1, cal2) == 0));
            if (cal1.before(cal2) || compare_calendar(cal1, cal2) == 0) {
                // zzf add 去除依赖com.gift.android.Utils.Constant.TRAIN_MAP
                final String TRAIN_MAP = "train";
                dateMap.put(getCalString(cal1), TRAIN_MAP);
            } else {
                flag = false;
            }
            cal1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateMap;
    }

    public static Calendar getStringCal(String date) {
        Calendar cal = Calendar.getInstance();
        if (TextUtils.isEmpty(date)) {
            return cal;
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, date.length()));
        cal.set(year, month - 1, day);
        return cal;
    }

    public static String getCalString(Calendar cal) {
        if (cal == null) {
            return "";
        }
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return year + "-" + (month >= 10 ? month : "0" + month) + "-" + (day >= 10 ? day : "0" + day);
    }

    private final static long yearLevelValue = 365 * 24 * 60 * 60 * 1000;
    private final static long monthLevelValue = 30 * 24 * 60 * 60 * 1000;
    private final static long dayLevelValue = 24 * 60 * 60 * 1000;
    private final static long hourLevelValue = 60 * 60 * 1000;
    private final static long minuteLevelValue = 60 * 1000;
    private final static long secondLevelValue = 1000;

    /**
     * ****计算出时间差中的 时、分、秒******
     */
    public static String[] getDifference(String nowTime, String targetTime) {//目标时间与当前时间差
        long period = Long.parseLong(targetTime) - Long.parseLong(nowTime);
        String[] result = getDifference(period);
        if (result == null) {
            result = new String[6];
        }
        return result;
    }

    private static String[] getDifference(long period) {//根据毫秒差计算时间差  
        String[] result = new String[6];
        //计算出时间差中的年、月、日、天、时、分、秒
        //int year = getYear(period) ;
        //int month = getMonth(period - year*yearLevelValue) ;
        //int day = getDay(period - year*yearLevelValue - month*monthLevelValue) ;
        int hour = getHour(period);
        int minute = getMinute(period - hour * hourLevelValue);
        int second = getSecond(period - hour * hourLevelValue - minute * minuteLevelValue);
        if (hour > 0 && hour < 10) {
            result[0] = "0";
            result[1] = Integer.toString(hour);
        } else {
            result[0] = Integer.toString(hour / 10);
            result[1] = Integer.toString(hour % 10);
        }
        if (minute > 0 && minute < 10) {
            result[2] = "0";
            result[3] = Integer.toString(minute);
        } else {
            result[2] = Integer.toString(minute / 10);
            result[3] = Integer.toString(minute % 10);
        }
        if (second > 0 && second < 10) {
            result[4] = "0";
            result[5] = Integer.toString(second);
        } else {
            result[4] = Integer.toString(second);
            result[5] = Integer.toString(second);
        }
        return result;
    }

    public static int getYear(long period) {
        return (int) (period / yearLevelValue);
    }

    public static int getMonth(long period) {
        return (int) (period / monthLevelValue);
    }

    public static int getDay(long period) {
        return (int) (period / dayLevelValue);
    }

    public static int getHour(long period) {
        return (int) (period / hourLevelValue);
    }

    public static int getMinute(long period) {
        return (int) (period / minuteLevelValue);
    }

    public static int getSecond(long period) {
        return (int) (period / secondLevelValue);
    }

    public static String getTime(long time) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //return format.format(new Date(time));
        return mFormat(new Date(time), PATTERN_CLASSICAL);
    }

    /**
     * 日期转换月日
     */
    public static String getFormatTimetoMD(String times) {
        String formatStr = "";
        try {
            String[] tmp = times.split("-");
            if (tmp.length >= 2) {
                formatStr = tmp[1] + "月" + tmp[2] + "日";
                L.d("getFormatTimetoMD:" + formatStr);
            }
        } catch (Exception e) {
            formatStr = times;
        }
        return formatStr;
    }

    //将秒数转为*天*小时*分*秒返回
    public static String formatDateTime(long mss) {
        String DateTimes;
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        long seconds = mss % 60;
        if (days > 0) {
            DateTimes = days + "天" + hours + "小时" + minutes + "分钟";
        } else if (hours > 0) {
            DateTimes = hours + "小时" + minutes + "分钟";
        } else if (minutes > 0) {
            DateTimes = minutes + "分钟";
        } else {
            DateTimes = seconds + "秒钟";
        }
        return DateTimes;
    }

    private static long mLastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }
}
