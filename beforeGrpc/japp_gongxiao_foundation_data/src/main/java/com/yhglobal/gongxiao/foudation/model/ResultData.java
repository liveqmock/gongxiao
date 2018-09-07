package com.yhglobal.gongxiao.foudation.model;

/**
 * 接口返回数据
 *
 * @author: 陈浩
 **/
public class ResultData {
    private boolean successful;
    private String exception ;
    private String data;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
