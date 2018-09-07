package com.yhglobal.gongxiao.eas.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售出库单
 *
 * @author: 陈浩
 **/
public class SaleOutOrder implements Serializable {

    private String projectId;   //项目ID

    private String customerId; //客户ID

    private String currencyId; //货币编码

    private double totalTaxAmount; //总金额

    private double taxAmount;//税额

    private int totalQuantity; //总数量

    private String id;

    private String easSalesOrderNumber;

    private int number;

    private Date businessDate;

    public String getEasSalesOrderNumber() {
        return easSalesOrderNumber;
    }

    public void setEasSalesOrderNumber(String easSalesOrderNumber) {
        this.easSalesOrderNumber = easSalesOrderNumber;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
