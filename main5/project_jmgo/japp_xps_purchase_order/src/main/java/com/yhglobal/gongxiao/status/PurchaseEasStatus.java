package com.yhglobal.gongxiao.status;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public enum PurchaseEasStatus {

    SYN_EAS_FAILURE((byte) 1, "未成功推送给EAS"),

    SYN_EAS_SUCCESS((byte) 2, "成功推送给EAS"),

    SYN_EAS_TEMP((byte) 3, "锁定状态"),

    WAIT_HANDLE((byte)4,"待人工处理")
    ;

    private byte status;

    private String message;

    private volatile static Map<Byte, PurchaseEasStatus> mapping = null;


    PurchaseEasStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static PurchaseEasStatus getErrorCodeByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (PurchaseEasStatus purchaseEasStatus : PurchaseEasStatus.values()) {
                        mapping.put(purchaseEasStatus.status, purchaseEasStatus);
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
