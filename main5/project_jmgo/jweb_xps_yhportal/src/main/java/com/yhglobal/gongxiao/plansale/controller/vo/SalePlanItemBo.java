package com.yhglobal.gongxiao.plansale.controller.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *  预销维护界面
 * @author: 陈浩
 * @create: 2018-03-14 19:03
 **/
public class SalePlanItemBo implements Serializable {
    private Long itemId;
    /**
     * 计划销售明细单号
     */
    private String salesPlanNo;
    /**
     * 客户ID
     */
    private String customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 分配数量(可售数量)
     */
    private int onSaleQuantity;
    /**
     * 销售占用
     */
    private int saleOccupationQuantity;
    /**
     * 已售数量
     */
    private int soldQuantity;
    /**
     * 客户可下单数量(未分配数量)
     */
    private int unallocatedQuantity;
    /**
     * 货币编码
     */
    private String currencyCode;
    /**
     * 货币名称
     */
    private String currencyName;
    /**
     * 指导价
     */
    private String  guidePrice;
    /**
     * 品牌商支持点
     */
    private String brandPrepaidDiscount;
    /**
     * YH折扣点
     */
    private String yhPrepaidDiscount;
    /**
     * 出货价
     */
    private String wholesalePrice;
    /**
     * 有效期起
     */
    private Date startTime;
    /**
     * 有效期止
     */
    private Date endTime;
    /**
     * 最后修改时间
     */
    private Date lastUpdateTime;
    /**
     *状态0正常 1 废弃
     */
    private int itemStatus;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getSalesPlanNo() {
        return salesPlanNo;
    }

    public void setSalesPlanNo(String salesPlanNo) {
        this.salesPlanNo = salesPlanNo;
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

    public int getOnSaleQuantity() {
        return onSaleQuantity;
    }

    public void setOnSaleQuantity(int onSaleQuantity) {
        this.onSaleQuantity = onSaleQuantity;
    }

    public int getSaleOccupationQuantity() {
        return saleOccupationQuantity;
    }

    public void setSaleOccupationQuantity(int saleOccupationQuantity) {
        this.saleOccupationQuantity = saleOccupationQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public int getUnallocatedQuantity() {
        return unallocatedQuantity;
    }

    public void setUnallocatedQuantity(int unallocatedQuantity) {
        this.unallocatedQuantity = unallocatedQuantity;
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

    public String getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getBrandPrepaidDiscount() {
        return brandPrepaidDiscount;
    }

    public void setBrandPrepaidDiscount(String brandPrepaidDiscount) {
        this.brandPrepaidDiscount = brandPrepaidDiscount;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }
}
