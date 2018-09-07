package com.yhglobal.gongxiao.eas;

import java.io.Serializable;

/**
 * 销售订单信息
 *
 * @author: 陈浩
 **/
public class SaleOrder implements Serializable{

    private String projectId; //项目ID

    private String customerId; //客户ID

    private String currencyCode; //货币编码

    private double totalTaxAmount; //含税总额

    private double totalTax; //税额

    private int number ;



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }
}
