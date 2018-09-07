package com.yhglobal.gongxiao.warehousemanagement.dto.wms;

import java.io.Serializable;

public class Result implements Serializable{
    private String orderNo;
    private boolean success;
    private String message;
    private String errorCode;
    private String errorMsg;
    private String id;

    public Result() {
    }

    public Result(String orderNo, boolean success, String message, String errorCode, String errorMsg, String id) {
        this.orderNo = orderNo;
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
