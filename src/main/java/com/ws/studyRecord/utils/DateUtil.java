package com.ws.studyRecord.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        return formatDateTime(date,YYYY_MM_DD);
    }

    /**
     * 获取日期时间格式：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateTime(Date date){
        return formatDateTime(date,YYYY_DD_MM_HH_MM_SS);
    }
    /**
     * 获取指定形式的时间格式
     * @param date
     * @param format
     * @return
     */
    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateTime = simpleDateFormat.format(date);
        return dateTime;
    }

    public static Date formatToDate(String dateStr,String format)throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(dateStr);
    }

    public static void main(String[] args) {

    }
}
