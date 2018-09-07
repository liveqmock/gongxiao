package com.yhglobal.gongxiao.transportataion.eventnotification.sales.model;

/**
 * TMS系统 对创建运单请求的回复
 */

public class CreateDispatchOrderResponse {

    public boolean success;

    public String message;

    public String errorMsg;

    public String data;

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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CreateDispatchOrderResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
