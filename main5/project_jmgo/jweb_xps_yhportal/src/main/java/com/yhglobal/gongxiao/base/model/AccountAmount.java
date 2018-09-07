package com.yhglobal.gongxiao.base.model;





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
