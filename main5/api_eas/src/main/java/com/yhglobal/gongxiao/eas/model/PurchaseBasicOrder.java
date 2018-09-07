package com.yhglobal.gongxiao.eas.model;


import java.io.Serializable;
import java.util.Date;

/**
 * eas需要的采购基础单据
 *
 * @author: 陈浩
 **/
public class PurchaseBasicOrder implements Serializable {

    private String projectId; //项目编码
    private String supplierCode; //供应商编码
    private double totalTaxAmount; //价税合计
    private double totalTax; //税额
    private int number;//数量
    private String unit;
    private Date businessDate;
    private Date requireArrivalDate;

    public Date getRequireArrivalDate() {
        return requireArrivalDate;
    }

    public void setRequireArrivalDate(Date requireArrivalDate) {
        this.requireArrivalDate = requireArrivalDate;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

}
