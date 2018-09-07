package com.yhglobal.gongxiao.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * 定义YhGlobalInoutFlow中的常量
 * @author  王帅
 */
public enum YhGlobalInoutFlowConstant {

    /**
    * 账户类型 : 未定义
    * */
    ACCOUNT_TYPE_UNDIFINED(0, "未定义"),

    /**
     * 账户类型 : cash
     * */
    ACCOUNT_TYPE_CASH(1, "cash"),

    /**
     * 账户类型 : transportation
     * */
    ACCOUNT_TYPE_COUPON(2, "transportation"),

    /**
     * 账户类型 : coupon
     * */
    ACCOUNT_TYPE_PREPAID(3, "coupon"),

    /**
     * 账户类型 : 其他
     * */
    ACCOUNT_TYPE_OTHER(9, "其他"),

    /**
     * 账户类型 : 越海账户支出
     * */
    FLOW_TYPE_OUT(305, "账户支出"),
    /**
     * 账户类型 : 越海账户收入
     * */
    FLOW_TYPE_IN(306, "账户收入");

    int num;

    String meaning;

    YhGlobalInoutFlowConstant(int num, String meaning) {
        this.num = num;
        this.meaning = meaning;
    }

    public int getNum() {
        return num;
    }

    public String getMeaning() {
        return meaning;
    }

    private volatile static Map<Integer, String> mapping = null;

    public static String getMessageByCode(Integer code){
        if (mapping == null) {
            synchronized (YhGlobalInoutFlowConstant.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (YhGlobalInoutFlowConstant yhGlobalInoutFlowConstant : YhGlobalInoutFlowConstant.values()) {
                        mapping.put(yhGlobalInoutFlowConstant.num, yhGlobalInoutFlowConstant.getMeaning());
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
