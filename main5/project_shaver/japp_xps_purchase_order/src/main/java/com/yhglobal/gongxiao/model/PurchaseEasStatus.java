package com.yhglobal.gongxiao.model;

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

    PurchaseEasStatus(byte status, String message) {
        this.status = status;
        this.message = message;
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
