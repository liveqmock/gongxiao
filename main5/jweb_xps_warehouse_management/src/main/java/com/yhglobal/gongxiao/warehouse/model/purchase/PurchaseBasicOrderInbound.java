package com.yhglobal.gongxiao.warehouse.model.purchase;

import java.io.Serializable;

/**
 * 采购入库单
 *
 * @author: 陈浩
 **/
public class PurchaseBasicOrderInbound implements Serializable {
    private String projectId;
    private String supplierCode;
    private int number;
    private double totalTaxAmount; //含税金额
    private double totalTax; //税额
    private String id; //采购订单的ID
    private String purchaseOrderNum;//采购单号
    private double discount; //折扣率

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseOrderNum() {
        return purchaseOrderNum;
    }

    public void setPurchaseOrderNum(String purchaseOrderNum) {
        this.purchaseOrderNum = purchaseOrderNum;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
