package com.huadingcloudpackage.www.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 文 件 名：BigDecimalUtils
 * 描   述：计算工具类
 */
public class BigDecimalUtils {


    // 除法运算默认精度
    private static final int DEF_DIV_SCALE = 10;

    private BigDecimalUtils() {

    }

    /**
     * 精确加法
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 精确加法
     */
    public static double add(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 精确减法
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 精确减法
     */
    public static double sub(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 精确乘法
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 精确乘法
     */
    public static double mul(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 精确除法 使用默认精度
     */
    public static double div(double value1, double value2) throws IllegalAccessException {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 精确除法 使用默认精度
     */
    public static double div(String value1, String value2) throws IllegalAccessException {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 精确除法
     *
     * @param scale 精度
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 精确除法
     *
     * @param scale 精度
     */
    public static double div(String value1, String value2, int scale) throws IllegalAccessException {
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static double round(double v, int scale) throws IllegalAccessException {
        return div(v, 1, scale);
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static double round(String v, int scale) {
        try {
            return div(v, "1", scale);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0.00;
    }

    /**
     * 比较大小
     */
    public static int compare(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return -1;
        }
        //这里做要是做差比较  eg：b1=1，b2=1   =》  b1-b2<0 返回-1 说明 b1<b2

        return b1.compareTo(b2);
    }

    /**
     * 保留两位小数
     *
     * @param dNum 传入的任意double数保留两位小数
     * @return 返回保留两位小数后的值
     */
    public static String keepTwoDecimalPlaces(double dNum) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(dNum);
    }

    /**
     * 去除数字中的无效0
     */
    public static String removeInvalidZero(String numStr) {
        BigDecimal a = new BigDecimal(numStr);
        //去除小数点后无效的0
        String num = a.stripTrailingZeros().toPlainString();//对于0.0和0.00无效
        num = Double.valueOf(num) == 0 ? "0" : num;
        return num;

    }

}
