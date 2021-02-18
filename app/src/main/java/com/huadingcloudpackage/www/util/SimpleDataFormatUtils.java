package com.huadingcloudpackage.www.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Ocean on 2018/9/27.
 */

public class SimpleDataFormatUtils {


    /*
    * 格式化时间字符串 为 年月
    * */
    public static Date toDate(String str, String pattern) {
        Date date = null;
        if (!str.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /*
    * 格式化时间字符串 为 年月
    * */
    public static String dateFormatNeed(String str, String pattern) {
        if (!str.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = null;
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat(pattern);
            String a = format.format(date);
            return a;
        } else {
            return "";
        }
    }


    public static String getCurrentDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDay = format.format(date);
        return currentDay;
    }


    public static String dateFormatForYmdHms(String str) {
        if (str == null) {
            str = "";
        }
        if (!str.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date;
            try {
                date = format.parse(str);
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String a = format.format(date);
                return a;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }

        } else {
            return "";
        }
    }


    public static String formatDateToNeed(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String str = simpleDateFormat.format(date);
        return str;
    }

    //
    public static String dateFormatNeed(String str, String beforePattern, String afterPattern) {
        if (str == null) {
            str = "";
        }
        if (!str.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat(beforePattern);
            Date date = null;
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat(afterPattern);
            String a = format.format(date);
            return a;
        } else {
            return "";
        }
    }


    /*
  * 格式化长期医嘱的startTime 为 年月
  * */
    public static String dateFormatDrAdviceTime(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (!str.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = null;
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("yyyy-MM-dd");
            String a = format.format(date);
            return a;
        } else {
            return "";
        }
    }

    /*
     * 格式化长期医嘱的startTime 为 年月
     * */
    public static String dateFormatDrAdviceTime(String str, boolean onlyMd) {
        if (str == null) {
            str = "";
        }
        if (!str.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = null;
            try {
                date = format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            format = new SimpleDateFormat("MM-dd HH:mm");
            String a = format.format(date);
            return a;
        } else {
            return "";
        }
    }

    public static String DateToWeek(Date date) {
        try {
            String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
            int WEEKDAYS = 7;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayIndex < 1 || dayIndex > WEEKDAYS) {
                return "";
            }

            return WEEK[dayIndex - 1];
        } catch (Exception e) {
            return "";
        }

    }


    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return
     */
    public static List<String> getFromAndToDayList(String startTime, String endTime, String pattern) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }


    //得到两个时间的时间间隔
    public static String getTimeInterval(String startTime, String endTime,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return "error";
        }
        long times = endDate.getTime() - startDate.getTime();

        long days = times / (1000 * 60 * 60 * 24); //换算成天数

        long hours = (times - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //换算成小时

        long minutes = (times - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60); //换算成分钟

        String result;
       /* if (days > 0) {
            result = days + "天" + hours + "小时" + minutes + "分钟";
        } else */
        if (days > 0||hours > 0) {
            result = ((days * 24) + hours) + "小时";
            if (minutes > 0) {
                result += minutes + "分钟";
            }
        } else if (days == 0 && hours == 0 && minutes > 0) {
            result = minutes + "分钟";
        } else if (days == 0 && hours == 0 && minutes == 0) {
            result = "< 1分钟";
        } else {
            result = "时间有误";
        }
//        Logger.i("", "------==" + days + "==" + hours + "==" + minutes + "==结果：" + result);
        return result;
    }

}
