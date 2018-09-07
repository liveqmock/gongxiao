package com.yhglobal.gongxiao.constant.sales;

import java.util.LinkedList;
import java.util.List;

/**
 * 销售单状态 枚举类
 *
 * @author: 葛灿
 */
public enum SalesOrderStatusEnum {

    /**
     * 初始化/待审核
     */
    INIT(1, "待审核"),
    /**
     * 已审核/待收款
     */
    CHECKED(2, "待收款"),
    /**
     * 已收款/待下发仓库
     */
    PAID(3, "待下发仓库"),
    /**
     * 已下发仓库/待仓库处理
     */
    INFORMED(4, "待仓库处理"),
    /**
     * 已出库/待签收
     */
    OUTBOUND(5, "待签收"),
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

    /**
     * 通过状态获取文字描述信息
     *
     * @param code 状态编码
     * @return 描述信息
     */
    public static String getMessage(int code) {
        for (SalesOrderStatusEnum status : SalesOrderStatusEnum.values()) {
            if (status.getStatus() == code) {
                return status.getInformation();
            }
        }
        return null;
    }

    /**
     * 通过状态码获取枚举
     *
     * @param code 状态码
     * @return 枚举类
     */
    public static SalesOrderStatusEnum getEnum(int code) {
        for (SalesOrderStatusEnum status : SalesOrderStatusEnum.values()) {
            if (status.getStatus() == code) {
                return status;
            }
        }
        return null;
    }


}
