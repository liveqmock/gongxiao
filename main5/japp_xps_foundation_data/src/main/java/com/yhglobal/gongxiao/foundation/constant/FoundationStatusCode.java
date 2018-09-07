package com.yhglobal.gongxiao.foundation.constant;

/**
 * 基础资料公用的状态 大部分表都按照这个约定
 *
 * @author: 陈浩
 **/
public enum FoundationStatusCode {

    INIT(0,"初始化"),

    COMMIT(1,"已提交"),

    AUDIT(2,"已审核"),

    REJECT(3,"已驳回"),

    ABANDON(9,"废弃");

    private int statusCode;

    private String statusMsg;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    FoundationStatusCode(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

}
