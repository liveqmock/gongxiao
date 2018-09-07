package com.yhglobal.gongxiao.sales.model;

/**
 * 销售单结算方式 枚举类
 *
 * @author: 葛灿
 */
public enum SalesOrderSettlementModeEnum {
    /**
     * 先款后货
     */
    NORMAL(1, "先款后货"),
    /**
     * 信用付款
     */
    CREDIT(2, "信用付款");


    private int status;

    private String information;

    SalesOrderSettlementModeEnum(int status, String information) {
        this.status = status;
        this.information = information;
    }

    public int getStatus() {
        return status;
    }
}
