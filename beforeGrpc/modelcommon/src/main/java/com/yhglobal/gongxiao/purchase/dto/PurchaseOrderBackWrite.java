package com.yhglobal.gongxiao.purchase.dto;

import java.io.Serializable;

/**
 * 回写采购单
 *
 * @author: 陈浩
 * @create: 2018-02-06 17:05
 **/
public class PurchaseOrderBackWrite implements Serializable {
    /**
     * 采购单号
     */
    private String purchaseOrderNo;
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
     * 待处理的商品数量
     */
    private int unhandledQuantity;
    /**
     *正在进行的入库通知单(JSON)
     */
    private String ongoingInboundOrderInfo;
    /**
     *已完成的入库通知单(JSON)
     */
    private String finishedInboundOrderInfo;
    /**
     *订单状态
     * 采购单状态 0:草稿未提交(导入) 1:已提交  3:已付款 70:已发货待入库 80:部分入库 90:正常完成 91:异常完成,已处理 92:异常完成,未处理 99:取消
     */
    private int orderStatus;

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
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

    public int getUnhandledQuantity() {
        return unhandledQuantity;
    }

    public void setUnhandledQuantity(int unhandledQuantity) {
        this.unhandledQuantity = unhandledQuantity;
    }

    public String getOngoingInboundOrderInfo() {
        return ongoingInboundOrderInfo;
    }

    public void setOngoingInboundOrderInfo(String ongoingInboundOrderInfo) {
        this.ongoingInboundOrderInfo = ongoingInboundOrderInfo;
    }

    public String getFinishedInboundOrderInfo() {
        return finishedInboundOrderInfo;
    }

    public void setFinishedInboundOrderInfo(String finishedInboundOrderInfo) {
        this.finishedInboundOrderInfo = finishedInboundOrderInfo;
    }
}
