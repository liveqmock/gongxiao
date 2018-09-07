package com.yhglobal.gongxiao.sales.model;

/**
 * 销售支付渠道枚举
 *
 * @author: 葛灿
 */
public enum SalesPaymentChannelEnum {

    OFFLINE_BANK(1, "线下银行支付"),
    ALIPAY(2, "支付宝"),
    WECHATPAY(3, "微信");
    private int status;

    private String message;

    SalesPaymentChannelEnum(int status, String message) {
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
