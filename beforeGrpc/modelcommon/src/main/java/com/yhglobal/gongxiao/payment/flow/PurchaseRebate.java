package com.yhglobal.gongxiao.payment.flow;

import java.io.Serializable;

/**
 * 新建采购单生成采购返利金额
 *
 * @author: 陈浩
 * @create: 2018-02-05 12:28
 **/
public class PurchaseRebate implements Serializable {

    /**
     * 采购单号
     */
    private String purchaseOrderNumber;
    /**
     * 账户类型
     */
    private int accountType ;
    /**
     * 产生返利金额
     */
    private double amount;
    /**
     * 货币ID
     */
    private String currencyId;
    /**
     * 货币名称
     */
    private int currencyName;

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public int getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(int currencyName) {
        this.currencyName = currencyName;
    }
}
