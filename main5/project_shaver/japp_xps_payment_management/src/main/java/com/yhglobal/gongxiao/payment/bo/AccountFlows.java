package com.yhglobal.gongxiao.payment.bo;

import java.io.Serializable;

/**
 * BO业务模型
 * 账户流水模型
 * 包含了 现金、返利、代垫、预付 流水号
 *
 * @author: 葛灿
 */
public class AccountFlows implements Serializable{
    /**
     * 现金流水号
     */
    private String cashFlowNo;
    /**
     * 返利流水号
     */
    private String couponFlowNo;
    /**
     * 代垫流水号
     */
    private String prepaidFlowNo;
    /**
     * 预存流水号
     */
    private String prestoredFlowNo;

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

    public String getPrestoredFlowNo() {
        return prestoredFlowNo;
    }

    public void setPrestoredFlowNo(String prestoredFlowNo) {
        this.prestoredFlowNo = prestoredFlowNo;
    }
}
