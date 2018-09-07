package com.yhglobal.gongxiao.sales.model;

import com.yhglobal.gongxiao.payment.constant.ReceiveStatus;

/**
 * 销售单状态 枚举类
 *
 * @author: 葛灿
 */
public enum SalesOrderStatusEnum {
    /**
     * 未定义
     */
    UNDEFINED(0, "未定义"),
    /**
     * 初始化/待审核
     */
    INIT(1, "已下单"),
    /**
     * 已审核/待收款
     */
    CHECKED(2, "已审核"),
    /**
     * 已收款/待下发仓库
     */
    PAID(3, "已收款"),
    /**
     * 已下发仓库/待仓库处理
     */
    INFORMED(4, "已下发仓库"),
    /**
     * 已出库/待签收
     */
    OUTBOUND(5, "已出库"),
    /**
     * 已签收
     */
    SIGNED(6, "已签收"),
    /**
     * 已取消
     */
    CANCELED(8, "已取消"),
    /**
     * 已驳回
     */
    REJECTED(9, "已驳回");


    private int status;

    private String information;

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    SalesOrderStatusEnum(int status, String information) {
        this.status = status;
        this.information = information;
    }

    public int getStatus() {
        return status;
    }

    public static String getMessage(int code) {
        for (SalesOrderStatusEnum status : SalesOrderStatusEnum.values()) {
            if (status.getStatus() == code) {
                return status.getInformation();
            }
        }
        return null;
    }
}
