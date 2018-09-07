package com.yhglobal.gongxiao.sales.model.plan.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户预售维护
 *
 * @author: 陈浩
 * @create: 2018-03-14 17:07
 **/
public class SaleItemEditInfo implements Serializable {
    private Long itemId;
    /**
     * 客户ID
     */
    private String customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 剩余销售总数
     */
    private int remainSaleAmount;
    /**
     * 客户可下单数
     */
    private int unallocatedQuantity;
    /**
     * 调整数量
     */
    private int adjustNumber;
    /**
     * 指导价
     */
    private String guidePrice;
    /**
     *品牌商单价支持
     */
    private String brandPrepaidUnit;
    /**
     * 品牌商支持点
     */
    private String  brandPrepaidDiscount;
    /**
     *YH单价代垫
     */
    private String yhPrepaidUnit;
    /**
     * YH折扣点
     */
    private String yhPrepaidDiscount;
    /**
     * 出货价
     */
    private String wholesalePrice;
    /**
     * 有效日期
     */
    private Date startTime;
    /**
     * 失效日期
     */
    private Date endTime;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getAdjustNumber() {
        return adjustNumber;
    }

    public void setAdjustNumber(int adjustNumber) {
        this.adjustNumber = adjustNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getRemainSaleAmount() {
        return remainSaleAmount;
    }

    public void setRemainSaleAmount(int remainSaleAmount) {
        this.remainSaleAmount = remainSaleAmount;
    }

    public int getUnallocatedQuantity() {
        return unallocatedQuantity;
    }

    public void setUnallocatedQuantity(int unallocatedQuantity) {
        this.unallocatedQuantity = unallocatedQuantity;
    }

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getBrandPrepaidUnit() {
        return brandPrepaidUnit;
    }

    public void setBrandPrepaidUnit(String brandPrepaidUnit) {
        this.brandPrepaidUnit = brandPrepaidUnit;
    }

    public String getBrandPrepaidDiscount() {
        return brandPrepaidDiscount;
    }

    public void setBrandPrepaidDiscount(String brandPrepaidDiscount) {
        this.brandPrepaidDiscount = brandPrepaidDiscount;
    }

    public String getYhPrepaidUnit() {
        return yhPrepaidUnit;
    }

    public void setYhPrepaidUnit(String yhPrepaidUnit) {
        this.yhPrepaidUnit = yhPrepaidUnit;
    }

    public String getYhPrepaidDiscount() {
        return yhPrepaidDiscount;
    }

    public void setYhPrepaidDiscount(String yhPrepaidDiscount) {
        this.yhPrepaidDiscount = yhPrepaidDiscount;
    }

    public String getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(String wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
