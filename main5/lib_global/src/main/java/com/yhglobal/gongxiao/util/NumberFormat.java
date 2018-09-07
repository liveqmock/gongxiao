package com.yhglobal.gongxiao.util;

import java.text.DecimalFormat;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 17:06 2018/5/18
 */
public class NumberFormat {

    public static String format(Double value,String pattern){
        if(value == null){
            return "--";
        }else if(value == 0){
            return "0";
        }else{
            return new DecimalFormat(pattern).format(value);
        }
    }
}
