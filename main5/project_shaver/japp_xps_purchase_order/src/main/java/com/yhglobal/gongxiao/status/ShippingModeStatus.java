package com.yhglobal.gongxiao.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum  ShippingModeStatus {

    SELF_COLLECT_PRODUCT((byte) 1, "自提"),

    OHTER_PARTY_LOGISTIC((byte) 2, "第三方物流")
    ;

    private byte status;

    private String message;

    private volatile static Map<Byte, ShippingModeStatus> mapping = null;


    ShippingModeStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ShippingModeStatus getErrorCodeByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (ShippingModeStatus shippingModeStatus : mapping.values()) {
                        mapping.put(shippingModeStatus.status, shippingModeStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
