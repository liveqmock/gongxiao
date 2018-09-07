package com.yhglobal.gongxiao.constant.sales;

/**
 * 销售单-销售渠道 枚举类
 * @author: 葛灿
 */
public enum SalesMarketingChannelEnum {

    WEB(1,"供销WEB端")

    ;
    private int status;

    private String message;

    SalesMarketingChannelEnum(int status, String message) {
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
