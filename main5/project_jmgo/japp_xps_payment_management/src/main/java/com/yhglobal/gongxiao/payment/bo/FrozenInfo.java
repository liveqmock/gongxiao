package com.yhglobal.gongxiao.payment.bo;

/**
 * 账户的暂扣信息
 *
 * @Author: 葛灿
 */
public class FrozenInfo {

    public FrozenInfo() {
        super();
    }

    public FrozenInfo(String salesOrderNo, long frozenAmount) {
        this.salesOrderNo = salesOrderNo;
        this.frozenAmount = frozenAmount;
    }

    /**
     * 关联的订单号
     */
    private String salesOrderNo;
    /**
     * 暂扣金额
     */
    private Long frozenAmount;


    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Long getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Long frozenAmount) {
        this.frozenAmount = frozenAmount;
    }
}
