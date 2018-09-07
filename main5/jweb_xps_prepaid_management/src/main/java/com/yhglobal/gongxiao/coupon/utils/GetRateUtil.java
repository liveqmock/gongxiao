package com.yhglobal.gongxiao.coupon.utils;

import com.yhglobal.gongxiao.constant.FXConstant;

/**
 * @author 王帅
 */
public class GetRateUtil {

    // 获取了百分比格式的比率
    public static String getRate(Long amount, Long amountTotal){
        if (amountTotal == 0){
            throw new RuntimeException("the amountTotal can not be 0");
        }
        Double amountDouble = amount / FXConstant.HUNDRED_DOUBLE;
        Double amountTotalDouble = amountTotal / FXConstant.HUNDRED_DOUBLE;
        Double d = amountDouble / amountTotalDouble * 100;
        // System.out.println(d);
        String result = String.format("%.2f", d) + "%";
        return result;
    }

    public static Double getRateDouble(Long amount, Long amountTotal){
        if (amountTotal == 0){
            throw new RuntimeException("the amountTotal can not be 0");
        }
        Double amountDouble = amount / FXConstant.HUNDRED_DOUBLE;
        Double amountTotalDouble = amountTotal / FXConstant.HUNDRED_DOUBLE;
        Double d = amountDouble / amountTotalDouble ;
        // System.out.println(d);
        String result = String.format("%.4f", d);
        return Double.parseDouble(result);
    }
}
