package com.yhglobal.gongxiao.phjd.sales.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售订单表
 *
 * @author weizecheng
 * @date 2018/8/31 17:23
 */
public class SalesOrderItem implements Serializable {
    /**
     * 货品id
     */
    private Long salesOrderItemId;

    /**
     * 销售单号
     */
    private String salesOrderNo;

    /**
     * 商品销售状态
     */
    private Integer itemStatus;

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
    private Integer warehouseId;

    /**
     * 发货仓库名称
     */
    private String warehouseName;

    /**
     * 货币编码
     */
    private String currencyCode;


    /**
     * 挂牌价
     */
    private BigDecimal retailPrice;

    /**
     * 出货价
     */
    private BigDecimal wholesalePrice;

    /**
     * 总金额(折扣后的价格)
     */
    private BigDecimal totalOrderAmount;

    /**
     * 实际出货数量总件数
     */
    private Integer totalQuantity;

    /**
     * 京东SKU编码
     */
    private String jdSkuCode;

    /**
     * 京东指导价
     */
    private BigDecimal jdSalesGuidePrice;

    /**
     * 京东采购价
     */
    private BigDecimal jdPurchaseGuidePrice;



    /**
     * 京东订购数量
     */
    private Integer jdTotalQuantity;

    /**
     * 京东采购明细单号
     */
    private String jdPurchaseNo;

    /**
     * 京东缺货处理方式
     */
    private String jdBackorderProcessing;

    /**
     * 京东备注
     */
    private String jdComments;

    /**
     * 生成代垫(单价 )
     */
    private BigDecimal generatedPrepaid;

    /**
     * 应收毛利(单价)
     */
    private BigDecimal shouldReceiveGrossProfit;

    /**
     * 实收毛利(单价)
     */
    private BigDecimal receivedGrossProfit;

    /**
     * 使用的现金金额
     */
    private BigDecimal cashAmount;

    /**
     * 使用代垫金额
     */
    private BigDecimal usedPrepaidAmount;

    /**
     * 已送达的商品数量
     */
    private Integer deliveredQuantity;

    /**
     * 在途的商品数量
     */
    private Integer inTransitQuantity;

    /**
     * 未出库就取消的数量（未配送）
     */
    private Integer canceledQuantity;

    /**
     * 退货数量（等于已签收再退货数量）
     */
    private Integer returnedQuantity;

    /**
     * 剩余待预约发货的商品数量
     */
    private Integer unhandledQuantity;

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
    private Long dataVersion;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
    private Date createTime;

    /**
     *
     */
    private Date lastUpdateTime;

    /**
     * eas货品型号
     */
    private String easCode;

    /**
     * easId
     */
    private String entryId;

    /**
     * 缺货原因
     */
    private String shortageReason;

    /**
     * 操作日记
     */
    private String tracelog;

    /**
     * 类别名称
     */
    private String categoryName;
    /**
     * 类别Id
     */
    private String categoryId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 京东细目的序列号
     */
    private Integer jdCurrentRecordNumber;
    public Integer getJdCurrentRecordNumber() {
        return jdCurrentRecordNumber;
    }

    public void setJdCurrentRecordNumber(Integer jdCurrentRecordNumber) {
        this.jdCurrentRecordNumber = jdCurrentRecordNumber;
    }

    public Long getSalesOrderItemId() {
        return salesOrderItemId;
    }

    public void setSalesOrderItemId(Long salesOrderItemId) {
        this.salesOrderItemId = salesOrderItemId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
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

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getJdSkuCode() {
        return jdSkuCode;
    }

    public void setJdSkuCode(String jdSkuCode) {
        this.jdSkuCode = jdSkuCode;
    }

    public BigDecimal getJdSalesGuidePrice() {
        return jdSalesGuidePrice;
    }

    public void setJdSalesGuidePrice(BigDecimal jdSalesGuidePrice) {
        this.jdSalesGuidePrice = jdSalesGuidePrice;
    }

    public BigDecimal getJdPurchaseGuidePrice() {
        return jdPurchaseGuidePrice;
    }

    public void setJdPurchaseGuidePrice(BigDecimal jdPurchaseGuidePrice) {
        this.jdPurchaseGuidePrice = jdPurchaseGuidePrice;
    }


    public Integer getJdTotalQuantity() {
        return jdTotalQuantity;
    }

    public void setJdTotalQuantity(Integer jdTotalQuantity) {
        this.jdTotalQuantity = jdTotalQuantity;
    }

    public String getJdPurchaseNo() {
        return jdPurchaseNo;
    }

    public void setJdPurchaseNo(String jdPurchaseNo) {
        this.jdPurchaseNo = jdPurchaseNo;
    }

    public String getJdBackorderProcessing() {
        return jdBackorderProcessing;
    }

    public void setJdBackorderProcessing(String jdBackorderProcessing) {
        this.jdBackorderProcessing = jdBackorderProcessing;
    }

    public String getJdComments() {
        return jdComments;
    }

    public void setJdComments(String jdComments) {
        this.jdComments = jdComments;
    }

    public BigDecimal getGeneratedPrepaid() {
        return generatedPrepaid;
    }

    public void setGeneratedPrepaid(BigDecimal generatedPrepaid) {
        this.generatedPrepaid = generatedPrepaid;
    }

    public BigDecimal getShouldReceiveGrossProfit() {
        return shouldReceiveGrossProfit;
    }

    public void setShouldReceiveGrossProfit(BigDecimal shouldReceiveGrossProfit) {
        this.shouldReceiveGrossProfit = shouldReceiveGrossProfit;
    }

    public BigDecimal getReceivedGrossProfit() {
        return receivedGrossProfit;
    }

    public void setReceivedGrossProfit(BigDecimal receivedGrossProfit) {
        this.receivedGrossProfit = receivedGrossProfit;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getUsedPrepaidAmount() {
        return usedPrepaidAmount;
    }

    public void setUsedPrepaidAmount(BigDecimal usedPrepaidAmount) {
        this.usedPrepaidAmount = usedPrepaidAmount;
    }

    public Integer getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(Integer deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public Integer getInTransitQuantity() {
        return inTransitQuantity;
    }

    public void setInTransitQuantity(Integer inTransitQuantity) {
        this.inTransitQuantity = inTransitQuantity;
    }

    public Integer getCanceledQuantity() {
        return canceledQuantity;
    }

    public void setCanceledQuantity(Integer canceledQuantity) {
        this.canceledQuantity = canceledQuantity;
    }

    public Integer getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(Integer returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public Integer getUnhandledQuantity() {
        return unhandledQuantity;
    }

    public void setUnhandledQuantity(Integer unhandledQuantity) {
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

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getShortageReason() {
        return shortageReason;
    }

    public void setShortageReason(String shortageReason) {
        this.shortageReason = shortageReason;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SalesOrderItem{");
        sb.append("salesOrderItemId=").append(salesOrderItemId);
        sb.append(", salesOrderNo='").append(salesOrderNo).append('\'');
        sb.append(", itemStatus=").append(itemStatus);
        sb.append(", productId=").append(productId);
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", warehouseId=").append(warehouseId);
        sb.append(", warehouseName='").append(warehouseName).append('\'');
        sb.append(", currencyCode='").append(currencyCode).append('\'');
        sb.append(", retailPrice=").append(retailPrice);
        sb.append(", wholesalePrice=").append(wholesalePrice);
        sb.append(", totalOrderAmount=").append(totalOrderAmount);
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", jdSkuCode='").append(jdSkuCode).append('\'');
        sb.append(", jdSalesGuidePrice=").append(jdSalesGuidePrice);
        sb.append(", jdPurchaseGuidePrice=").append(jdPurchaseGuidePrice);
        sb.append(", jdTotalQuantity=").append(jdTotalQuantity);
        sb.append(", jdPurchaseNo='").append(jdPurchaseNo).append('\'');
        sb.append(", jdBackorderProcessing='").append(jdBackorderProcessing).append('\'');
        sb.append(", jdComments='").append(jdComments).append('\'');
        sb.append(", generatedPrepaid=").append(generatedPrepaid);
        sb.append(", shouldReceiveGrossProfit=").append(shouldReceiveGrossProfit);
        sb.append(", receivedGrossProfit=").append(receivedGrossProfit);
        sb.append(", cashAmount=").append(cashAmount);
        sb.append(", usedPrepaidAmount=").append(usedPrepaidAmount);
        sb.append(", deliveredQuantity=").append(deliveredQuantity);
        sb.append(", inTransitQuantity=").append(inTransitQuantity);
        sb.append(", canceledQuantity=").append(canceledQuantity);
        sb.append(", returnedQuantity=").append(returnedQuantity);
        sb.append(", unhandledQuantity=").append(unhandledQuantity);
        sb.append(", ongoingOutboundOrderInfo='").append(ongoingOutboundOrderInfo).append('\'');
        sb.append(", finishedOutboundOrderInfo='").append(finishedOutboundOrderInfo).append('\'');
        sb.append(", dataVersion=").append(dataVersion);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", easCode='").append(easCode).append('\'');
        sb.append(", entryId='").append(entryId).append('\'');
        sb.append(", shortageReason='").append(shortageReason).append('\'');
        sb.append(", tracelog='").append(tracelog).append('\'');
        sb.append(", categoryName='").append(categoryName).append('\'');
        sb.append(", categoryId='").append(categoryId).append('\'');
        sb.append(", jdCurrentRecordNumber=").append(jdCurrentRecordNumber);
        sb.append('}');
        return sb.toString();
    }
}
