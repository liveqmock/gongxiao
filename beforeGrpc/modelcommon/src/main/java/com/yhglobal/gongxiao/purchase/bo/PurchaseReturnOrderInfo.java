package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 退货信息
 *
 * @author: 陈浩
 * @create: 2018-03-15 14:46
 **/
public class PurchaseReturnOrderInfo implements Serializable{
    /**
     * 单号
     */
    private String orderNumber;
    /**
     * 状态
     */
    private int orderStatus;
    /**
     * 客户信息
     */
    private String customerInfo;
    /**
     * 退货单类型
     */
    private int returnOrderType;
    /**
     * 物流相关信息
     */
    private String logisticInfo;
    /**
     * 退货单更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public int getReturnOrderType() {
        return returnOrderType;
    }

    public void setReturnOrderType(int returnOrderType) {
        this.returnOrderType = returnOrderType;
    }

    public String getLogisticInfo() {
        return logisticInfo;
    }

    public void setLogisticInfo(String logisticInfo) {
        this.logisticInfo = logisticInfo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
