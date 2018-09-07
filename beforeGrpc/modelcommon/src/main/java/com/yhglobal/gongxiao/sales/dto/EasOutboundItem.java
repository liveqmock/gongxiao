package com.yhglobal.gongxiao.sales.dto;

import java.io.Serializable;

/**
 * eas出库单明细
 *
 * @author: 葛灿
 */
public class EasOutboundItem implements Serializable {

    /**
     * 供销平台商品编码
     */
    private String productCode;
    /**
     * 出库数量
     */
    private int quantity;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 仓库id
     */
    private String warehouseId;
    /**
     * 是否良品
     */
    private boolean goodProduct;
    /**是否赠品*/
    private boolean gift;

    public boolean isGift() {
        return gift;
    }

    public void setGift(boolean gift) {
        this.gift = gift;
    }

    public boolean isGoodProduct() {
        return goodProduct;
    }

    public void setGoodProduct(boolean goodProduct) {
        this.goodProduct = goodProduct;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public String toString() {
        return "EasOutboundItem{" +
                "productCode='" + productCode + '\'' +
                ", quantity=" + quantity +
                ", batchNo='" + batchNo + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", goodProduct=" + goodProduct +
                '}';
    }
}
