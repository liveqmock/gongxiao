package com.yhglobal.gongxiao.warehousenotify.purchaseorder.controller.model;

import java.io.Serializable;

/**
 * 入库通知单回写
 *
 * @author: 陈浩
 **/
public class InboundNotificationBackItem implements Serializable{
    /**
     * 业务发起方货明细D
     */
    private String businessItemId;
    /**
     * 入库通知单明细ID
     */
    private String inboundNotificationItemId;
    /**
     * 货品code
     */
    private String productCode;
    /**
     *本次入库商品数量
     */
    private int inStockQuantity;
    /**
     * 次品数量
     */
    private int imperfectQuantity;

    /**
     *
     * @return
     */
    private String warehouseId;
    //批次
    private String batchNo;

    /**
     * 是否赠品 true 是 false 否
     */
    private boolean isGift;

    public boolean isGift() {
        return isGift;
    }

    public void setGift(boolean gift) {
        isGift = gift;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBusinessItemId() {
        return businessItemId;
    }

    public void setBusinessItemId(String businessItemId) {
        this.businessItemId = businessItemId;
    }

    public String getInboundNotificationItemId() {
        return inboundNotificationItemId;
    }

    public void setInboundNotificationItemId(String inboundNotificationItemId) {
        this.inboundNotificationItemId = inboundNotificationItemId;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

}
