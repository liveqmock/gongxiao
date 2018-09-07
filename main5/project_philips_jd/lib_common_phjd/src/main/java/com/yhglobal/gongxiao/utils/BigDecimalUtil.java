package com.yhglobal.gongxiao.utils;

import com.yhglobal.gongxiao.constant.FXConstant;

import java.math.BigDecimal;
/**
 * BigDecimal工具类
 *
 * @auther weizecheng
 * @date 2018/8/24 14:55
 */
public class BigDecimalUtil {
    /**
     * 固定100
     */
    public static final BigDecimal HUNDRED =  new BigDecimal(FXConstant.HUNDRED);
    /**
     * 固定1000000
     */
    public static final BigDecimal MILLION =  new BigDecimal(FXConstant.MILLION);
    public static final int BIGDECIMAL_ZERO = 0;

    /**
     * 除以100
     * @param bigDecimal
     * @return
     */
    public static BigDecimal keepTwoBits(BigDecimal bigDecimal){
        return bigDecimal.divide(HUNDRED,2,BigDecimal.ROUND_HALF_UP );
    }


    public static BigDecimal keepSixBits(BigDecimal bigDecimal){
        return bigDecimal.divide(MILLION,6,BigDecimal.ROUND_HALF_UP );
    }

    public static BigDecimal keepSixBits(Long bigDecimal){
        return new BigDecimal(bigDecimal).divide(MILLION,6,BigDecimal.ROUND_HALF_UP );
    }

    public static BigDecimal divideHundred(Long amount){
        return new BigDecimal(amount).divide(HUNDRED,2,BigDecimal.ROUND_HALF_UP );
    }
    // 返回String 参数
    public static String keepTwoBitsToString(BigDecimal bigDecimal){
        return keepTwoBits(bigDecimal).toString();
    }

    /**
     * 乘法
     *
     * @param bigDecimal
     * @param count
     * @return
     */
    public static BigDecimal multiplication(BigDecimal bigDecimal,Integer count){
        return bigDecimal.multiply(new BigDecimal(count));
    }

    /**
     * 乘以100
     * @param bigDecimal
     * @return
     */
    public static BigDecimal multiplication(BigDecimal bigDecimal){
        return bigDecimal.multiply(HUNDRED);
    }


    public static Long multiplicationSix(BigDecimal bigDecimal){
        return bigDecimal.multiply(MILLION).longValue();
    }

}
