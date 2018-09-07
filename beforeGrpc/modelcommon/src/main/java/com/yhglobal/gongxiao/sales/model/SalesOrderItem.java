package com.yhglobal.gongxiao.sales.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售单商品表
 *
 * @Author: 葛灿
 */
public class SalesOrderItem implements Serializable {

    /**
     * 货品id
     */
    private Long salesOrderItemId;
    /**
     * eas货品编码
     */
    private String easCode;
    /**
     * entryId
     */
    private String entryId;
    /**
     * 商品销售状态
     */
    private int itemStatus;
    /**
     * 销售单号
     */
    private String salesOrderNo;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 发货仓库id
     */
    private Long warehouseId;
    /**
     * 发货仓库名称
     */
    private String warehouseName;
    /**
     * 货币id
     */
    private Long currencyId;
    /**
     * 货币编码
     */
    private String currencyCode;
    /**
     * 货币名称
     */
    private String currencyName;
    /**
     * 供应商折扣
     */
    private long supplierDiscountPercent;

    /**
     * 越海折扣
     */
    private long yhDiscountPercent;



    /**
     * 挂牌价
     */
    private long retailPrice;
    /**
     * 挂牌价(浮点数)
     */
    private Double retailPriceDouble;
    /**
     * 出货价
     */
    private long wholesalePrice;

    /**
     * 供应商支持金(单价)
     */
    private Long supplierDiscountAmount;

    /**
     * 越海支持金(单价)
     */
    private Long yhDiscountAmount;

    /**
     * 总金额(折扣后的价格)
     */
    private Long totalOrderAmount;

    /**
     * 使用的现金金额
     */
    private Long cashAmount;
    /**
     * 商品总件数
     */
    private int totalQuantity;
    /**
     * 已送达的商品数量
     */
    private int deliveredQuantity;
    /**
     * 在途的商品数量
     */
    private int inTransitQuantity;
    /**
     * 未出库就取消的数量（未配送）
     */
    private int canceledQuantity;
    /**
     * 退货数量（等于已签收再退货数量）
     */
    private int returnedQuantity;
    /**
     * 剩余待预约发货的商品数量
     */
    private int unhandledQuantity;
    /**
     * 正在进行的出库通知单(JSON)
     */
    private String ongoingOutboundOrderInfo;
    /**
     * 已完成的出库通知单(JSON)
     */
    private String finishedOutboundOrderInfo;
    /**
     * 数据版本
     */
    private long dataVersion;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    /**
     * 操作日记
     */
    private String tracelog;
    /**
     * 客户优惠金额
     */
    private long customerDiscountAmount;
    /**
     * 使用返利金额
     */
    private long usedCouponAmount;
    /**
     * 使用代垫金额
     */
    private long usedPrepaidAmount;

    /**
     * 销售指导价
     */
    private long salesGuidePrice;
    /**
     * 采购指导价
     */
    private long purchaseGuidePrice;
    /**
     * 高卖金额(单价)
     */
    private long sellHighAmount;
    /**
     * 产生的应收代垫(单价)
     */
    private long generatedPrepaid;

    public long getGeneratedPrepaid() {
        return generatedPrepaid;
    }

    public void setGeneratedPrepaid(long generatedPrepaid) {
        this.generatedPrepaid = generatedPrepaid;
    }

    public long getSellHighAmount() {
        return sellHighAmount;
    }

    public void setSellHighAmount(long sellHighAmount) {
        this.sellHighAmount = sellHighAmount;
    }

    public long getSalesGuidePrice() {
        return salesGuidePrice;
    }

    public void setSalesGuidePrice(long salesGuidePrice) {
        this.salesGuidePrice = salesGuidePrice;
    }

    public long getPurchaseGuidePrice() {
        return purchaseGuidePrice;
    }

    public void setPurchaseGuidePrice(long purchaseGuidePrice) {
        this.purchaseGuidePrice = purchaseGuidePrice;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public long getCustomerDiscountAmount() {
        return customerDiscountAmount;
    }

    public void setCustomerDiscountAmount(long customerDiscountAmount) {
        this.customerDiscountAmount = customerDiscountAmount;
    }

    public long getUsedCouponAmount() {
        return usedCouponAmount;
    }

    public void setUsedCouponAmount(long usedCouponAmount) {
        this.usedCouponAmount = usedCouponAmount;
    }

    public long getUsedPrepaidAmount() {
        return usedPrepaidAmount;
    }

    public void setUsedPrepaidAmount(long usedPrepaidAmount) {
        this.usedPrepaidAmount = usedPrepaidAmount;
    }

    public Long getSalesOrderItemId() {
        return salesOrderItemId;
    }

    public void setSalesOrderItemId(Long salesOrderItemId) {
        this.salesOrderItemId = salesOrderItemId;
    }

    public int getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(int itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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


    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public long getSupplierDiscountPercent() {
        return supplierDiscountPercent;
    }

    public void setSupplierDiscountPercent(long supplierDiscountPercent) {
        this.supplierDiscountPercent = supplierDiscountPercent;
    }

    public long getYhDiscountPercent() {
        return yhDiscountPercent;
    }

    public void setYhDiscountPercent(long yhDiscountPercent) {
        this.yhDiscountPercent = yhDiscountPercent;
    }

    public long getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(long retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getRetailPriceDouble() {
        return retailPriceDouble;
    }

    public void setRetailPriceDouble(Double retailPriceDouble) {
        this.retailPriceDouble = retailPriceDouble;
    }

    public long getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(long wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Long getSupplierDiscountAmount() {
        return supplierDiscountAmount;
    }

    public void setSupplierDiscountAmount(Long supplierDiscountAmount) {
        this.supplierDiscountAmount = supplierDiscountAmount;
    }

    public Long getYhDiscountAmount() {
        return yhDiscountAmount;
    }

    public void setYhDiscountAmount(Long yhDiscountAmount) {
        this.yhDiscountAmount = yhDiscountAmount;
    }

    public Long getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(Long totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public Long getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Long cashAmount) {
        this.cashAmount = cashAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public int getInTransitQuantity() {
        return inTransitQuantity;
    }

    public void setInTransitQuantity(int inTransitQuantity) {
        this.inTransitQuantity = inTransitQuantity;
    }

    public int getCanceledQuantity() {
        return canceledQuantity;
    }

    public void setCanceledQuantity(int canceledQuantity) {
        this.canceledQuantity = canceledQuantity;
    }

    public int getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(int returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public int getUnhandledQuantity() {
        return unhandledQuantity;
    }

    public void setUnhandledQuantity(int unhandledQuantity) {
        this.unhandledQuantity = unhandledQuantity;
    }

    public String getOngoingOutboundOrderInfo() {
        return ongoingOutboundOrderInfo;
    }

    public void setOngoingOutboundOrderInfo(String ongoingOutboundOrderInfo) {
        this.ongoingOutboundOrderInfo = ongoingOutboundOrderInfo;
    }

    public String getFinishedOutboundOrderInfo() {
        return finishedOutboundOrderInfo;
    }

    public void setFinishedOutboundOrderInfo(String finishedOutboundOrderInfo) {
        this.finishedOutboundOrderInfo = finishedOutboundOrderInfo;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }
}