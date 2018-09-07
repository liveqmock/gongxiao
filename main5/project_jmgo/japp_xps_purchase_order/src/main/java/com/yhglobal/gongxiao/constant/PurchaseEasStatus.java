package com.yhglobal.gongxiao.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum PurchaseEasStatus {

    INIT((byte)1,"初始化"),

    SYN_FAIL((byte)2,"同步失败"),

    SYN_ING((byte)3,"正在同步"),

    SYN_COMPLETE((byte)4,"同步完成")

    ;

    private byte status;

    private String message;

    private volatile static Map<Byte, PurchaseEasStatus> mapping = null;

    PurchaseEasStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static PurchaseEasStatus getPurchaseStatusByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (PurchaseEasStatus purchaseEasStatus : PurchaseEasStatus.values()) {
                        mapping.put(purchaseEasStatus.getStatus(), purchaseEasStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
