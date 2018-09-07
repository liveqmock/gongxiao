package com.yhglobal.gongxiao.payment.model;

/**
 * 销售收款--确认状态
 *
 * @author: 葛灿
 */
public enum CashConfirmStatusEnum {
    /**
     * 未确认
     */
    UNCONFIRM(1, "未确认"),
    /**
     * 部分确认
     */
    PART_CONFIRM(2, "部分确认"),
    /**
     * 已确认
     */
    CONFIRM(3, "已确认");
    private int status;

    private String message;

    CashConfirmStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
