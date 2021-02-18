package com.huadingcloudpackage.www.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;


public class DateUtil {
    private static long minute = 1000 * 60;
    private static long hour = minute * 60;
    private static long day = hour * 24;
    private static long halfamonth = day * 15;
    private static long month = day * 30;


    public static String getAddTimeDesc(long comentTime, long addComentTime) {
        String result;
        long diffValue = addComentTime - comentTime;
        if (diffValue < 0) {
            //toast("结束日期不能小于开始日期！");
        }
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        if (monthC >= 1) {
            result = Integer.parseInt(monthC + "") + "月后";
            return result;
        } else if (weekC >= 1) {
            result = Integer.parseInt(weekC + "") + "周后";
            return result;
        } else if (dayC >= 1) {
            result = Integer.parseInt(dayC + "") + "天后";
            return result;
        } else if (hourC >= 1) {
            result = Integer.parseInt(hourC + "") + "小时后";
            return result;
        } else if (minC >= 1) {
            result = Integer.parseInt(minC + "") + "分钟后";
            return result;
        } else {
            result = "一分钟之内";
            return result;
        }

    }

    public static String getAddTimeDay(long comentTime, long addComentTime) {
        String result;
        long diffValue = addComentTime - comentTime;
        if (diffValue < 0) {
            //toast("结束日期不能小于开始日期！");
        }
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        if (dayC >= 1) {
            result = Integer.parseInt(dayC + "") + "天后";
            return result;
        } else if (hourC >= 1) {
            result = Integer.parseInt(hourC + "") + "小时后";
            return result;
        } else if (minC >= 1) {
            result = Integer.parseInt(minC + "") + "分钟后";
            return result;
        } else {
            result = "一分钟之内";
            return result;
        }

    }

    public static int getValidTime(long endTime, long nowTime) {
        int result;
        long diffValue = endTime - nowTime;
        if (diffValue <= 0) {
            //toast("结束日期不能小于开始日期！");
            return -1;
        }
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        return Integer.parseInt(monthC + "");
    }

    public static int getValidTime1(long diffValue) {
        if (diffValue <= 0) {
            //toast("结束日期不能小于开始日期！");
            return -1;
        }
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        return Integer.parseInt(monthC + "");
    }

    public static String getDateDiff(long dateTimeStamp) {
        String result;
        long now = new Date().getTime();
        long diffValue = now - dateTimeStamp;
        if (diffValue < 0) {
//toast("结束日期不能小于开始日期！");
        }
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        if (monthC >= 1) {
            try {
                result = longToString(dateTimeStamp, "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
                result = "";
            }
            return result;
        } else if (weekC >= 1) {
            result = Integer.parseInt(weekC + "") + "周前";
            return result;
        } else if (dayC >= 1) {
            result = Integer.parseInt(dayC + "") + "天前";
            return result;
        } else if (hourC >= 1) {
            result = Integer.parseInt(hourC + "") + "小时前";
            return result;
        } else if (minC >= 1) {
            result = Integer.parseInt(minC + "") + "分钟前";
            return result;
        } else {
            result = "刚刚发布";
            return result;
        }
    }

    public static String getanswerdate(long dateTimeStamp) {
        String result;
        long now = new Date().getTime();
        long diffValue = now - dateTimeStamp;
        if (diffValue < 0) {
//toast("结束日期不能小于开始日期！");
        }
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        if (monthC >= 1) {
            try {
                result = longToString(dateTimeStamp, "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
                result = "";
            }
            return result;
        } else if (weekC >= 1) {
            result = "回答于" + Integer.parseInt(weekC + "") + "周前";
            return result;
        } else if (dayC >= 1) {
            result = "回答于" + Integer.parseInt(dayC + "") + "天前";
            return result;
        } else if (hourC >= 1) {
            result = "回答于" + Integer.parseInt(hourC + "") + "小时前";
            return result;
        } else if (minC >= 1) {
            result = "回答于" + Integer.parseInt(minC + "") + "分钟前";
            return result;
        } else {
            result = "刚刚回答";
            return result;
        }
    }

    public static String getdate(long dateTimeStamp, String message) {
        String result;
        long now = new Date().getTime();
        long diffValue = -dateTimeStamp;
        if (diffValue < 0) {
//toast("结束日期不能小于开始日期！");
        }
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        if (monthC >= 1) {
            try {
                result = longToString(dateTimeStamp, "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
                result = "";
            }
            return result;
        } else if (weekC >= 1) {
            result = message + "于" + Integer.parseInt(weekC + "") + "周前";
            return result;
        } else if (dayC >= 1) {
            result = message + "于" + Integer.parseInt(dayC + "") + "天前";
            return result;
        } else if (hourC >= 1) {
            result = message + "于" + Integer.parseInt(hourC + "") + "小时前";
            return result;
        } else if (minC >= 1) {
            result = message + "于" + Integer.parseInt(minC + "") + "分钟前";
            return result;
        } else {
            result = "刚刚" + message;
            return result;
        }
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static String yuefen(long day) throws ParseException {

        Date date = longToDate(day, "yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        // 将日历设置为指定的时间
        calendar.setTime(date);

        // 这里要注意，月份是从0开始。
        int month = calendar.get(Calendar.MONTH);
        String yuefen;
        switch (month) {
            case 0:

                yuefen = "一月";
                break;
            case 1:

                yuefen = "二月";
                break;
            case 2:

                yuefen = "三月";
                break;
            case 3:

                yuefen = "四月";
                break;
            case 4:

                yuefen = "五月";
                break;

            case 5:

                yuefen = "六月";
                break;
            case 6:

                yuefen = "七月";
                break;
            case 7:

                yuefen = "八月";
                break;
            case 8:

                yuefen = "九月";
                break;
            case 9:

                yuefen = "十月";
                break;
            case 10:

                yuefen = "十一月";
                break;
            case 11:

                yuefen = "十二月";
                break;
            default:

                yuefen = "";
                break;

        }
        return yuefen;
    }


    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(long timeMs) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        int totalSeconds = (int) timeMs / 1000;
        int seconds = totalSeconds % 60;


        int minutes = (totalSeconds / 60) % 60;


        int hours = totalSeconds / 3600;


        mFormatBuilder.setLength(0);
        return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
//        if (hours > 0) {
//            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
//        } else {
//            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
//        }
    }

    public static String stringToString(String strTime, String formatType1, String formatType2)
            throws ParseException {
        String string = longToString(stringToLong(strTime, formatType1), formatType2);
        return string;
    }

    //将时间转换为时间戳
    public static String dateToStamp(String s) throws Exception {
        String res;//设置时间格式，将该时间格式的时间转换为时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long time = date.getTime();
        res = String.valueOf(time);
        return res;
    }

    //将时间戳转换为时间
    public static String stampToTime(String s) throws Exception {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        long lt = new Long(s);//将时间戳转换为时间
        Date date = new Date(lt);//将时间调整为yyyy-MM-dd HH:mm:ss时间样式
        res = simpleDateFormat.format(date);
        return res;
    }
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentYearMonthDay() {
        //得到long类型当前时间
        long l = System.currentTimeMillis();
//new日期对象
        Date date = new Date(l);
//转换提日期输出格式  //     "yyyy-MM-dd HH:mm:ss"这个根式不是固定的根据你的需要自己的调整;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String nyr = dateFormat.format(date);

        return nyr;
    }
}
