package com.yhglobal.gongxiao.wmscomfirm.model.wms;

import java.io.Serializable;

public class WmsConfirmResponse implements Serializable{
    private boolean success;
    private String message;
    private String errorMsg;
    private String errorCode;

    public WmsConfirmResponse(boolean success, String message, String errorMsg, String errorCode) {
        this.success = success;
        this.message = message;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
