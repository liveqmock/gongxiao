package com.yhglobal.gongxiao.payment.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 应收状态
 * @author: LUOYI
 * @Date: Created in 18:47 2018/5/2
 */
public enum ReceiveStatus {
    /**
     * 应收状态 : 未确认
     * */
    RECEIVE_STATUS_UNCONFIRMED(1, "未确认"),
    /**
     * 应收状态 : 部分确认
     * */
    RECEIVE_STATUS_PARTIAL_CONFIRMED(2, "部分确认"),
    /**
     * 应收状态 : 完全确认
     * */
    RECEIVE_STATUS_COMPLETE_CONFIRMED (3, "完全确认");

    int code;

    String message;

    ReceiveStatus(int code, String message) {
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
            synchronized (ReceiveStatus.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (ReceiveStatus receiveStatus : ReceiveStatus.values()) {
                        mapping.put(receiveStatus.code, receiveStatus.getMessage());
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
