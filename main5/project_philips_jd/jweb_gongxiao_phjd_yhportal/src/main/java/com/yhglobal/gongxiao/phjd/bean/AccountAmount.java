package com.yhglobal.gongxiao.phjd.bean;

import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;

/**
 * 返利、代垫、现金账户余额
 *
 * @author: 葛灿
 */
public class AccountAmount implements Serializable {
    /**
     * 现金余额
     */
    private long cashAmount;
    /**
     * 返利余额
     */
    private long couponAmount;
    /**
     * 代垫余额
     */
    private long prepaidAmount;

    /****************************
     * 以下字段用于页面展示
     ****************************/

    /**
     * 现金余额
     */
    private double cashAmountDouble;
    /**
     * 返利余额
     */
    private double couponAmountDouble;
    /**
     * 代垫余额
     */
    private double prepaidAmountDouble;

    /**
     * 返利过账余额
     */
    private double couponBufferAmountDouble;
    /**
     * 代垫过账余额
     */
    private double prepaidBufferAmountDouble;

    private double frozenDiscountAccount;
    private double frozenPurchaseAccount;
    private double reservedDiscountAccount;
    private double reservedPurchaseAccount;
    private double reservedSalesDifferenceAccount;

    public String getCouponBufferAmountDoubleStr() {
        return NumberFormat.format(this.couponBufferAmountDouble, "#,##0.00");
    }
    public String getPrepaidBufferAmountDoubleStr() {
        return NumberFormat.format(this.prepaidBufferAmountDouble, "#,##0.00");
    }

    public double getFrozenDiscountAccount() {
        return frozenDiscountAccount;
    }
    public String getFrozenDiscountAccountStr() {
        return NumberFormat.format(this.frozenDiscountAccount, "#,##0.00");
    }
    public String getFrozenPurchaseAccountStr() {
        return NumberFormat.format(this.frozenPurchaseAccount, "#,##0.00");
    }
    public String getReservedDiscountAccountStr() {
        return NumberFormat.format(this.reservedDiscountAccount, "#,##0.00");
    }
    public String getReservedPurchaseAccountStr() {
        return NumberFormat.format(this.reservedPurchaseAccount, "#,##0.00");
    }
    public String getReservedSalesDifferenceAccountStr() {
        return NumberFormat.format(this.reservedSalesDifferenceAccount, "#,##0.00");
    }

    public double getCouponBufferAmountDouble() {
        return couponBufferAmountDouble;
    }

    public void setCouponBufferAmountDouble(double couponBufferAmountDouble) {
        this.couponBufferAmountDouble = couponBufferAmountDouble;
    }

    public double getPrepaidBufferAmountDouble() {
        return prepaidBufferAmountDouble;
    }

    public void setPrepaidBufferAmountDouble(double prepaidBufferAmountDouble) {
        this.prepaidBufferAmountDouble = prepaidBufferAmountDouble;
    }

    public void setFrozenDiscountAccount(double frozenDiscountAccount) {
        this.frozenDiscountAccount = frozenDiscountAccount;
    }

    public double getFrozenPurchaseAccount() {
        return frozenPurchaseAccount;
    }

    public void setFrozenPurchaseAccount(double frozenPurchaseAccount) {
        this.frozenPurchaseAccount = frozenPurchaseAccount;
    }

    public double getReservedDiscountAccount() {
        return reservedDiscountAccount;
    }

    public void setReservedDiscountAccount(double reservedDiscountAccount) {
        this.reservedDiscountAccount = reservedDiscountAccount;
    }

    public double getReservedPurchaseAccount() {
        return reservedPurchaseAccount;
    }

    public void setReservedPurchaseAccount(double reservedPurchaseAccount) {
        this.reservedPurchaseAccount = reservedPurchaseAccount;
    }

    public double getReservedSalesDifferenceAccount() {
        return reservedSalesDifferenceAccount;
    }

    public void setReservedSalesDifferenceAccount(double reservedSalesDifferenceAccount) {
        this.reservedSalesDifferenceAccount = reservedSalesDifferenceAccount;
    }

    public String getCashAmountDoubleStr() {
        return NumberFormat.format(this.cashAmountDouble, "#,##0.00");
    }

    public String getCouponAmountDoubleStr() {
        return NumberFormat.format(this.couponAmountDouble, "#,##0.00");
    }

    public String getPrepaidAmountDoubleStr() {
        return NumberFormat.format(this.prepaidAmountDouble, "#,##0.00");
    }

    public double getCashAmountDouble() {
        return cashAmountDouble;
    }

    public void setCashAmountDouble(double cashAmountDouble) {
        this.cashAmountDouble = cashAmountDouble;
    }

    public double getCouponAmountDouble() {
        return couponAmountDouble;
    }

    public void setCouponAmountDouble(double couponAmountDouble) {
        this.couponAmountDouble = couponAmountDouble;
    }

    public double getPrepaidAmountDouble() {
        return prepaidAmountDouble;
    }

    public void setPrepaidAmountDouble(double prepaidAmountDouble) {
        this.prepaidAmountDouble = prepaidAmountDouble;
    }

    public long getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(long cashAmount) {
        this.cashAmount = cashAmount;
    }

    public long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public long getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(long prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }
}
