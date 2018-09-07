package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.util.List;

public class EasResult implements Serializable {

    private String id;//订单ID

    private String orderNumber;//订单号

    private boolean success;//成功

    private List<EasResultItem> entryList;//货品列表

    private String error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<EasResultItem> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<EasResultItem> entryList) {
        this.entryList = entryList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
