package com.yhglobal.gongxiao.distributor.purchase.bean;

import java.io.Serializable;

/**
 * 订单可用帐户余额
 * @Description:
 * @author: LUOYI
 * @Date: Created in 11:26 2018/4/13
 */
public class DistributorAccountForOrder implements Serializable {
    private Double couponAmount;//可用返利
    private Double prepaidAmount;//可用代垫
    private Double cashAmount;//可用现金

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Double getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(Double prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public Double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Double cashAmount) {
        this.cashAmount = cashAmount;
    }
}
