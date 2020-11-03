package com.ws.studyRecord.utils;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: wangshuo
 * @Date: 2020/10/20 17:44
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYYMM = "yyyyMM";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYY_DD_MM_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    /**
     * 获取日期格式：yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDateTime(date, YYYY_MM_DD);
    }

    /**
     * 获取日期时间格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDateTime(date, YYYY_DD_MM_HH_MM_SS);
    }

    /**
     * 获取指定形式的时间格式
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateTime = simpleDateFormat.format(date);
        return dateTime;
    }

    public static Date formatToDate(String dateStr, String format) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(dateStr);
    }


    /**
     * 获取当月季度
     *
     * @param date
     * @return
     */
    public static String getStringNowSeason(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "-Q" + (calendar.get(Calendar.MONTH) / 3 + 1);
    }

    /**
     * 获取某一季度的月份
     *
     * @param season
     * @return
     */
    public static List<String> getSeasonMonth(String season) {
        List<String> scoreTimes = Lists.newArrayList();
        String year = season.substring(0, 4);
        Integer num = Integer.valueOf(season.substring(6, 7));
        // 设置本年的季
        switch (num) {
            case 1:
                scoreTimes.add(year + "-01");
                scoreTimes.add(year + "-02");
                scoreTimes.add(year + "-03");
                break;
            case 2:
                scoreTimes.add(year + "-04");
                scoreTimes.add(year + "-05");
                scoreTimes.add(year + "-06");
                break;
            case 3:
                scoreTimes.add(year + "-07");
                scoreTimes.add(year + "-08");
                scoreTimes.add(year + "-09");
                break;
            case 4:
                scoreTimes.add(year + "-10");
                scoreTimes.add(year + "-11");
                scoreTimes.add(year + "-12");
                break;
        }
        return scoreTimes;
    }

    /**
     * 计算两个日期相差几周
     * endTime比 startTime 多的周数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int calcWeekOffset(Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0){
            dayOfWeek = 7;
        }
        int dayOffset = calcDayOffset(startTime, endTime);

        int weekOffset = dayOffset / 7;
        int a;
        if (dayOffset > 0) {
            a = (dayOffset % 7 + dayOfWeek > 7) ? 1 : 0;
        } else {
            a = (dayOfWeek + dayOffset % 7 < 1) ? -1 : 0;
        }
        weekOffset = weekOffset + a;
        return weekOffset;
    }

    /**
     * 求两个日期相差多少天
     * date2 比 date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int calcDayOffset(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {  //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
                    timeDistance += 366;
                } else {  //不是闰年

                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else { //不同年
            return day2 - day1;
        }
    }

    public static void main(String[] args) {

    }
}
