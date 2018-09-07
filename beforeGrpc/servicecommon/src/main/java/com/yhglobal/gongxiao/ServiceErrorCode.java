package com.yhglobal.gongxiao;

public enum  ServiceErrorCode {

    ;

    private int errorCode;

    private String message;

    ServiceErrorCode(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }


}
