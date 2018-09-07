package com.yhglobal.gongxiao.warehouseapi.model;

import java.io.Serializable;

/**
 * 创建调拨单请求
 */
public class CreateAllocateOrderRequest implements Serializable {
    private String companyout;
    private String companyentry;
    private String projectoutId;
    private String projectEntryId;
    private String warehouseOutId;
    private String warehouseEntryId;
    private String deliveraddress;
    private String receiveaddress;
    private String allocateway;
    private String yhbusiness_date;
    private String ask_date;
    private String dead_date;
    private String remask;
    private String itemsInfo;

    public String getCompanyout() {
        return companyout;
    }

    public void setCompanyout(String companyout) {
        this.companyout = companyout;
    }

    public String getCompanyentry() {
        return companyentry;
    }

    public void setCompanyentry(String companyentry) {
        this.companyentry = companyentry;
    }

    public String getProjectoutId() {
        return projectoutId;
    }

    public void setProjectoutId(String projectoutId) {
        this.projectoutId = projectoutId;
    }

    public String getProjectEntryId() {
        return projectEntryId;
    }

    public void setProjectEntryId(String projectEntryId) {
        this.projectEntryId = projectEntryId;
    }

    public String getWarehouseOutId() {
        return warehouseOutId;
    }

    public void setWarehouseOutId(String warehouseOutId) {
        this.warehouseOutId = warehouseOutId;
    }

    public String getWarehouseEntryId() {
        return warehouseEntryId;
    }

    public void setWarehouseEntryId(String warehouseEntryId) {
        this.warehouseEntryId = warehouseEntryId;
    }

    public String getDeliveraddress() {
        return deliveraddress;
    }

    public void setDeliveraddress(String deliveraddress) {
        this.deliveraddress = deliveraddress;
    }

    public String getReceiveaddress() {
        return receiveaddress;
    }

    public void setReceiveaddress(String receiveaddress) {
        this.receiveaddress = receiveaddress;
    }

    public String getAllocateway() {
        return allocateway;
    }

    public void setAllocateway(String allocateway) {
        this.allocateway = allocateway;
    }

    public String getYhbusiness_date() {
        return yhbusiness_date;
    }

    public void setYhbusiness_date(String yhbusiness_date) {
        this.yhbusiness_date = yhbusiness_date;
    }

    public String getAsk_date() {
        return ask_date;
    }

    public void setAsk_date(String ask_date) {
        this.ask_date = ask_date;
    }

    public String getDead_date() {
        return dead_date;
    }

    public void setDead_date(String dead_date) {
        this.dead_date = dead_date;
    }

    public String getRemask() {
        return remask;
    }

    public void setRemask(String remask) {
        this.remask = remask;
    }

    public String getItemsInfo() {
        return itemsInfo;
    }

    public void setItemsInfo(String itemsInfo) {
        this.itemsInfo = itemsInfo;
    }
}
