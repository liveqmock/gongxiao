package com.yhglobal.gongxiao.warehousemanagement.customermodel;


import java.io.Serializable;

public class InstockOrder implements Serializable{
    private String id;
    private String record;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
