package com.yhglobal.gongxiao.util;

import java.text.DecimalFormat;
import java.util.Date;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.constant.FXConstant.MILLION;

public class DoubleScale {


    public final static int NUM_PRECISION_6DIGITS = 6;

    /**
     * 按四舍五入的规则 获得对应的值
     *
     * @param N 将要保留的小数位数
     * @param d 待按照N位小数进行四舍五入的数
     * @return
     */
    public static double getRoundUpValue(int N, double d) {
        double value = d;
        for (int i = 0; i < N; i++) {
            value *= 10;
        }
        value = Math.round(value);
        for (int i = 0; i < N; i++) {
            value /= 10;
        }
        return value;
    }

    /**
     * 按N位小数格式化输出
     *
     * @param N 将要保留的小数位数
     * @return 字符串格式的结果
     */
    public static String formatDouble(int N, double d) {
        String pattern = String.format("#0.%0" + N + "d", 0);
        if (Math.abs(d) < 1) {
            pattern = String.format("0.%0" + N + "d", 0);
        }

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(d);
    }

    /**
     * 小数变为原先的10^N倍,并进行四舍五入
     *
     * @param N
     * @param d
     * @return
     */
    public static long multiple(int N, double d) {
        double value = d;
        for (int i = 0; i < N; i++) {
            value *= 10;
        }
        long needValue = Math.round(value);
        return needValue;
    }

    /**
     * 保留两位小数
     *
     * @param longValue long类型的数值
     * @return 保留两位浮点数的字符串
     */
    public static String keepTwoBits(long longValue) {
        double doubleValue = 1.0 * longValue / HUNDRED;
        double roundUpValue = getRoundUpValue(2, doubleValue);
        return formatDouble(2, roundUpValue);
    }

    /**
     * 保留两位小数
     *
     * @param intValue int类型的数值
     * @return 保留两位浮点数的字符串
     */
    public static String keepTwoBits(int intValue) {
        double doubleValue = 1.0 * intValue / HUNDRED;
        double roundUpValue = getRoundUpValue(2, doubleValue);
        return formatDouble(2, roundUpValue);
    }

    /**
     * 保留六位小数
     *
     * @param longValue long类型的数值
     * @return 保留两位浮点数的字符串
     */
    public static String keepSixBits(long longValue) {
        double doubleValue = 1.0 * longValue / MILLION;
        double roundUpValue = getRoundUpValue(6, doubleValue);
        return formatDouble(6, roundUpValue);
    }

    /**
     * 保留六位小数
     *
     * @param intValue int类型的数值
     * @return 保留六位浮点数的字符串
     */
    public static String keepSixBits(int intValue) {
        double doubleValue = 1.0 * intValue / MILLION;
        double roundUpValue = getRoundUpValue(6, doubleValue);
        return formatDouble(6, roundUpValue);
    }

    /**
     * 变为原先的100倍,并四舍五入
     *
     * @param doubleValue
     * @return
     */
    public static long multipleHundred(double doubleValue) {
        long multiy = multiple(2, doubleValue);
        return multiy;
    }

    /**
     * 变为原先的1000000倍,并四舍五入
     *
     * @param doubleValue
     * @return
     */
    public static long multipleMillion(double doubleValue) {
        return multiple(6, doubleValue);
    }

    /**
     *
     * @param stringValue
     * @return
     */
    public static long multipleMillion(String  stringValue) {
        if (stringValue!=null && !"".equals(stringValue)){
            return multiple(6, Double.parseDouble(stringValue));
        }
        return 0;
    }


    public static void main(String[] args) throws InterruptedException {
//        double totalAmount1 = 250771.95;
//        double cashAmount1 = -2533.05;
//        double prepaidAmount1 = -41256;
//        double couponAmount1 = -136395;
//        int purchaseNumber1 = 3600;
//        double purchasePrice = (totalAmount1 - cashAmount1 - prepaidAmount1 - couponAmount1) * 1.16 / purchaseNumber1;
//        double scaleValue = getRoundUpValue(NUM_PRECISION_6DIGITS, purchasePrice);
//        System.out.println(formatDouble(NUM_PRECISION_6DIGITS, scaleValue));
//        System.out.println(Math.round(5.9999999999994*100));
//        System.out.println(formatDouble(6,0.666666666));;
        Date date = new Date(0);
        Thread.sleep(1000);
    }


}
