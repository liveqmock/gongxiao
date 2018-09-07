package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;

/**
 * 新增入库单model
 * @author liukai
 */
public class CreateOutboundItemInfo implements Serializable {
    /**
     * 主键id
     */
    private String id;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 货品ID
     */
    private String productId;
    /**
     * 货品编码
     */
    private String productCode;
    /**
     * 货品名称
     */
    private String productName;
    /**
     * 指导价
     */
    private double guidePrice;
    /**
     * 采购价
     */
    private double purchasePrice;
    /**
     * 出库数量
     */
    private int quantity;
    /**
     * 出货单价
     */
    private double wholesalePriceDouble;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(double guidePrice) {
        this.guidePrice = guidePrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWholesalePriceDouble() {
        return wholesalePriceDouble;
    }

    public void setWholesalePriceDouble(double wholesalePriceDouble) {
        this.wholesalePriceDouble = wholesalePriceDouble;
    }
}
