package com.yhglobal.gongxiao.constant.sales;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 18:07 2018/7/18
 */
public enum SalesReturnOrderType {
    /**
     * 退货单类型:销售退货
     * */
    RETURN_ORDER_TYPE_SALES(1, "销售退货"),
    /**
     * 退货单类型:换货退货
     * */
    RETURN_ORDER_TYPE_EXCHANGE(2, "换货退货");


    int code;

    String message;

    SalesReturnOrderType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private volatile static Map<Integer, String> mapping = null;

    public static String getMessageByCode(Integer code){
        if (mapping == null) {
            synchronized (SalesReturnStatus.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (SalesReturnOrderType salesReturnOrderType : SalesReturnOrderType.values()) {
                        mapping.put(salesReturnOrderType.code, salesReturnOrderType.message);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
