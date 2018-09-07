package com.yhglobal.gongxiao.constant.sales;

/**
 * 来源枚举类
 *
 * @author weizecheng
 * @date 2018/8/31 12:29
 */
public enum SalesMarketingChannelEnum {


    /**
     * 目前只有web端
     */
    WEB(1,"供销WEB端");


    private Integer status;

    private String message;

    SalesMarketingChannelEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
