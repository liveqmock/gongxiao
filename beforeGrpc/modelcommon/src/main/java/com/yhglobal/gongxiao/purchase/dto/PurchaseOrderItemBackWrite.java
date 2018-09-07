package com.yhglobal.gongxiao.purchase.dto;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 * @create: 2018-02-06 18:04
 **/
public class PurchaseOrderItemBackWrite implements Serializable {
    /**
     * 采购单号
     */
    private long purchaseItemId;
    /**
     * 唯一号
     */
    private String uniqueNumber;
    /**
     * 在途商品数量
     */
    private int inTransitQuantity;
    /**
     * 已入库的商品数量
     */
    private int inStockQuantity;

    /**
     * 已取消的数量
     */
    private int canceledQuantity;
    /**
     * 订单状态
     * 采购单状态 0:草稿未提交 10:在协商待越海确认 20:在协商待供应商确认 50待预约入库 60待发货 70已发货待入库 80部分入库 90已全部入库采购完成 91已取消
     * 0: 初始状态 10:部分预约入库  12:预约入库完成  16:实际入库完成
     */
    private int orderStatus;

    /**
     * 正在进行的入库通知单
     */
    private String ongoingInboundOrderInfo;

    public String getOngoingInboundOrderInfo() {
        return ongoingInboundOrderInfo;
    }

    public void setOngoingInboundOrderInfo(String ongoingInboundOrderInfo) {
        this.ongoingInboundOrderInfo = ongoingInboundOrderInfo;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public int getInTransitQuantity() {
        return inTransitQuantity;
    }

    public void setInTransitQuantity(int inTransitQuantity) {
        this.inTransitQuantity = inTransitQuantity;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public int getCanceledQuantity() {
        return canceledQuantity;
    }

    public void setCanceledQuantity(int canceledQuantity) {
        this.canceledQuantity = canceledQuantity;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
