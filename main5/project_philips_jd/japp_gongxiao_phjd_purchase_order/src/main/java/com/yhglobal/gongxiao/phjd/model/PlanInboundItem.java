package com.yhglobal.gongxiao.phjd.model;

import java.io.Serializable;

/**
 * 预约入库货品信息
 *
 * @author: 陈浩
 * @create: 2018-02-07 11:12
 **/
public class PlanInboundItem implements Serializable {
    /**
     * 采购单货品id
     */
    private long purchaseItemId;
    /**
     * 品牌ID
     */
    private String brandId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品单位
     */
    private String productUnit;
    /**
     * 货币编码
     */
    private String currencyCode;
    /**
     * 货货币名称
     */
    private String currencyName;
    /**
     * 采购数量
     */
    private int purchaseNumber;
    /**
     * 剩余需入库数量
     */
    private int needInstockNumber;
    /**
     * 本次预约数量
     */
    private int planInstockNumber;
    /**
     * 指导价
     */
    private String guidePrice;
    /**
     * 成本价
     */
    private String costValue;
    /**
     * 备注
     */
    private String remark;


    private long requireInboundDate;

    public long getRequireInboundDate() {
        return requireInboundDate;
    }

    public void setRequireInboundDate(long requireInboundDate) {
        this.requireInboundDate = requireInboundDate;
    }

    public int getPlanInstockNumber() {
        return planInstockNumber;
    }

    public void setPlanInstockNumber(int planInstockNumber) {
        this.planInstockNumber = planInstockNumber;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getNeedInstockNumber() {
        return needInstockNumber;
    }

    public void setNeedInstockNumber(int needInstockNumber) {
        this.needInstockNumber = needInstockNumber;
    }

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getCostValue() {
        return costValue;
    }

    public void setCostValue(String costValue) {
        this.costValue = costValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}
