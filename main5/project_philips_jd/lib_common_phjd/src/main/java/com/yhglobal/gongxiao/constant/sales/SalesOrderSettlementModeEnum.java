package com.yhglobal.gongxiao.constant.sales;

/**
 * 销售订单结算枚举类
 *
 * @author weizecheng
 * @date 2018/8/29 17:30
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


    private Integer status;

    private String information;

    SalesOrderSettlementModeEnum(Integer status, String information) {
        this.status = status;
        this.information = information;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public static SalesOrderSettlementModeEnum getEnum(Integer code){
        for (SalesOrderSettlementModeEnum status : SalesOrderSettlementModeEnum.values()) {
            if (status.getStatus().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
