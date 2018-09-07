package com.yhglobal.gongxiao.constant;

public class FXConstant {

    /**
     * FX对金额需要保留两位小数,金额存储到数据库*100倍
     */
    public static final long HUNDRED=100;
    /**
     * FX对金额需要保留两位小数,金额从数据库转出/100
     */
    public static final double HUNDRED_DOUBLE=100.0;
    /**
     * FX对局单价和比例需要保留6位小数,所以单价和比例存储到数据库*1000,000倍
     */
    public static final long MILLION=1000000;
    /**
     * FX对局单价和比例需要保留6位小数,所以单价和比例存储到数据库*1000,000倍
     */
    public static final double MILLION_DOUBLE=1000000.0;
    /**
     * 增值税比例
     */
    public static final double TAX_RATE= 16;

    /**
     * 价税合计比例
     */
    public static final double TAX_RATE_SUM = 1.16;
}
