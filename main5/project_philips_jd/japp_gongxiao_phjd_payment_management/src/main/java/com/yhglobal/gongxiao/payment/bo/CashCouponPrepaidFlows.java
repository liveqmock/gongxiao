package com.yhglobal.gongxiao.payment.bo;

import java.io.Serializable;

/**
 * 现金、返利、代垫流水 返回结果对象
 *
 * @author: 葛灿
 */
public class CashCouponPrepaidFlows implements Serializable {
    /**现金流水号*/
    private String cashFlowNo;
    /**返利流水号*/
    private String couponFlowNo;
    /**代垫流水号*/
    private String prepaidFlowNo;

    public String getCashFlowNo() {
        return cashFlowNo;
    }

    public void setCashFlowNo(String cashFlowNo) {
        this.cashFlowNo = cashFlowNo;
    }

    public String getCouponFlowNo() {
        return couponFlowNo;
    }

    public void setCouponFlowNo(String couponFlowNo) {
        this.couponFlowNo = couponFlowNo;
    }

    public String getPrepaidFlowNo() {
        return prepaidFlowNo;
    }

    public void setPrepaidFlowNo(String prepaidFlowNo) {
        this.prepaidFlowNo = prepaidFlowNo;
    }
}
