package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.util.Date;

public class BatchImportPurchaseOrder implements Serializable {
    /**
     * 供应商订单号
     */
    private String supplierNumber;
    /**
     * 合同参考号
     */
    private String contractNumber;
    /**
     * 业务日期
     */
    private Date businessDate;
    /**
     * 采购方式
     */
    private String purchaseType;
    /**
     * 有效订单型号标识
     */
    private String status;
    /**
     * 型号
     */
    private String productCode;
    /**
     * 采购数量
     */
    private int purchaseNumber;
    /**
     * 每个型号对应的现金返点金额
     */
    private double cashAmount;
    /**
     * 每个型号对应的使用代垫金额
     */
    private double prepaidAmount;
    /**
     * 每个型号对应的使用返利金额
     */
    private double couponAmount;
    /**
     * 行项总净金额
     */
    private double totalAmount;

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public double getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(double prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }
}
