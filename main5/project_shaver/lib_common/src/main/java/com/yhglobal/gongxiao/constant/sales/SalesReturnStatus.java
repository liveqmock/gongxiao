package com.yhglobal.gongxiao.constant.sales;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 14:43 2018/5/29
 */
public enum SalesReturnStatus {

    /**
     * 销售退货单状态 新建
     * */
    RETURN_ORDER_STATUS_NEW(1, "新建"),
    /**
     * 销售退货单状态 等待退货到仓
     * */
    RETURN_ORDER_STATUS_NOT_IN_STORAGE(2, "等待退货到仓"),
    /**
     * 销售退货单状态 退货完成
     * */
    RETURN_ORDER_STATUS_FINISH(3, "退货完成"),
    /**
     * 销售退货单状态 全部
     * */
    RETURN_ORDER_STATUS_ALL(0, "全部");


    int code;

    String message;

    SalesReturnStatus(int code, String message) {
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
                    for (SalesReturnStatus slesReturnStatus : SalesReturnStatus.values()) {
                        mapping.put(slesReturnStatus.code, slesReturnStatus.message);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
