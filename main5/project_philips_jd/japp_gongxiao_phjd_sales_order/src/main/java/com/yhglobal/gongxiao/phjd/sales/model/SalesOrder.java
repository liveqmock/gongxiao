package com.yhglobal.gongxiao.phjd.sales.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

/**
 @Author weizecheng
 @Date 2018/8/20 15:58
 **/
public class SalesOrder implements Serializable {
    /**
     * 主键id
     */
    private Long salesOrderId;

    /**
     * 销售单状态 ：1-待审核 2-待收款 3-待发货 4-待出库 5-待签收 6-已签收  9-已驳回
     */
    private Integer salesOrderStatus;

    /**
     * 数据版本
     */
    private Long dataVersion;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 仓库id
     */
    private Long warehouseId;

    /**
     * 发货仓库
     */
    private String warehouse;

    /**
     * 品牌Id
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 供应商Id
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 京东SKU数量
     */
    private Integer jdSkuNumber;

    /**
     * 商品总件数
     */
    private Integer totalQuantity;

    /**
     * 商品实际采购数
     */
    private Integer totalCostQuantity;

    /**
     * 销售指导总价
     */
    private BigDecimal totalSalesAmount;

    /**
     * 采购总价
     */
    private BigDecimal totalOrderAmount;

    /**
     * 实际采购总价
     */
    private BigDecimal cashAmount;

    /**
     * 运费
     */
    private BigDecimal shippingExpense;

    /**
     * 运费承担方 1:越海 2:品牌商 3:经销商
     */
    private Integer shippingExpensePaidBy;

    /**
     * 结算货币码
     */
    private String currencyCode;

    /**
     * 使用的代垫金额
     */
    private BigDecimal prepaidAmount;

    /**
     * 越海代垫金
     */
    private BigDecimal yhDiscountAmount;

    /**
     * 品牌商支持金额
     */
    private BigDecimal supplierDiscountAmount;

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
     * 缺货数量
     */
    private Integer shortageReasonQuantity;

    /**
     * 创建人id
     */
    private Long createdById;

    /**
     * 创建人名称
     */
    private String createdByName;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
    private Date createTime;

    /**
     * 采购单时间
     */
    @JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
    private Date purchaseTime;

    /**
     * 出库时间
     */
    private Date outBoundTime;

    /**
     * 完成时间
     */
    private Date orderCheckTime;

    /**
     * 驳回时间
     */
    private Date rejectTime;

    /**
     * 付款时间
     */
    @JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
    private Date paidTime;

    /**
     * 通知仓库时间
     */
    private Date informWarehouseTime;

    /**
     * 签收时间
     */
    private Date signTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 结算模式
     */
    private Integer settlementMode;

    /**
     * 销售单号
     */
    private String salesOrderNo;

    /**
     * 备注信息
     */
    private String comments;

    /**
     * 销售渠道: 1:供销WEB端
     */
    private Integer marketingChannel;

    /**
     * 支付渠道: 1:线下银行转账 2:支付宝 3:微信
     */
    private Integer paymentChannel;

    /**
     * 账期天数
     */
    private Integer paymentDays;

    /**
     * 账期结算备注
     */
    private String creditRemark;

    /**
     * 现金流水号
     */
    private String cashFlowNo;

    /**
     * 代垫流水号
     */
    private String prepaidFlowNo;

    /**
     * 取消订单产生的逆向现金流水号
     */
    private String returnCashFlowNo;

    /**
     * 取消订单产生的逆向代垫流水号
     */
    private String returnPrepaidFlowNo;

    /**
     * eas单号
     */
    private String easOrderNo;

    /**
     * eas单号id
     */
    private String easOrderId;

    /**
     * 产生应收代垫
     */
    private BigDecimal totalGeneratedPrepaid;

    /**
     * 同步eas状态
     */
    private Integer syncEas;

    /**
     * 应收毛利
     */
    private BigDecimal shouldReceiveGrossProfit;

    /**
     * 实收毛利
     */
    private BigDecimal receivedGrossProfit;

    /**
     * 客户采购号
     */
    private String purchaseNo;

    /**
     * 订单属性
     */
    private String salesOrderAttribute;

    /**
     * 经销商id
     */
    private Long distributorId;

    /**
     * 经销商名称
     */
    private String distributorName;
    /**
     * 正在进行的出库通知单(JSON)
     */
    private String ongoingOutboundOrderInfo;

    /**
     * 已完成的出库通知单(JSON)
     */
    private String finishedOutboundOrderInfo;

    /**
     * 操作日记
     */
    private String tracelog;

    /**
     * 销售合同号
     */
    private String salesContactNo;

    private Date requiredArrivalTime;

    public Date getRequiredArrivalTime() {
        return requiredArrivalTime;
    }

    public void setRequiredArrivalTime(Date requiredArrivalTime) {
        this.requiredArrivalTime = requiredArrivalTime;
    }

    public String getSalesContactNo() {
        return salesContactNo;
    }

    public void setSalesContactNo(String salesContactNo) {
        this.salesContactNo = salesContactNo;
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

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }
    public Integer getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(Integer salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public Integer getShippingExpensePaidBy() {
        return shippingExpensePaidBy;
    }

    public void setShippingExpensePaidBy(Integer shippingExpensePaidBy) {
        this.shippingExpensePaidBy = shippingExpensePaidBy;
    }

    public Integer getMarketingChannel() {
        return marketingChannel;
    }

    public void setMarketingChannel(Integer marketingChannel) {
        this.marketingChannel = marketingChannel;
    }

    public Integer getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getSalesOrderAttribute() {
        return salesOrderAttribute;
    }

    public void setSalesOrderAttribute(String salesOrderAttribute) {
        this.salesOrderAttribute = salesOrderAttribute;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getJdSkuNumber() {
        return jdSkuNumber;
    }

    public void setJdSkuNumber(Integer jdSkuNumber) {
        this.jdSkuNumber = jdSkuNumber;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getTotalCostQuantity() {
        return totalCostQuantity;
    }

    public void setTotalCostQuantity(Integer totalCostQuantity) {
        this.totalCostQuantity = totalCostQuantity;
    }

    public BigDecimal getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(BigDecimal totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getShippingExpense() {
        return shippingExpense;
    }

    public void setShippingExpense(BigDecimal shippingExpense) {
        this.shippingExpense = shippingExpense;
    }



    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(BigDecimal prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public BigDecimal getYhDiscountAmount() {
        return yhDiscountAmount;
    }

    public void setYhDiscountAmount(BigDecimal yhDiscountAmount) {
        this.yhDiscountAmount = yhDiscountAmount;
    }

    public BigDecimal getSupplierDiscountAmount() {
        return supplierDiscountAmount;
    }

    public void setSupplierDiscountAmount(BigDecimal supplierDiscountAmount) {
        this.supplierDiscountAmount = supplierDiscountAmount;
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

    public Integer getShortageReasonQuantity() {
        return shortageReasonQuantity;
    }

    public void setShortageReasonQuantity(Integer shortageReasonQuantity) {
        this.shortageReasonQuantity = shortageReasonQuantity;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Date getOutBoundTime() {
        return outBoundTime;
    }

    public void setOutBoundTime(Date outBoundTime) {
        this.outBoundTime = outBoundTime;
    }

    public Date getOrderCheckTime() {
        return orderCheckTime;
    }

    public void setOrderCheckTime(Date orderCheckTime) {
        this.orderCheckTime = orderCheckTime;
    }

    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    public Date getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }

    public Date getInformWarehouseTime() {
        return informWarehouseTime;
    }

    public void setInformWarehouseTime(Date informWarehouseTime) {
        this.informWarehouseTime = informWarehouseTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getSettlementMode() {
        return settlementMode;
    }

    public void setSettlementMode(Integer settlementMode) {
        this.settlementMode = settlementMode;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

    public String getCreditRemark() {
        return creditRemark;
    }

    public void setCreditRemark(String creditRemark) {
        this.creditRemark = creditRemark;
    }

    public String getCashFlowNo() {
        return cashFlowNo;
    }

    public void setCashFlowNo(String cashFlowNo) {
        this.cashFlowNo = cashFlowNo;
    }

    public String getPrepaidFlowNo() {
        return prepaidFlowNo;
    }

    public void setPrepaidFlowNo(String prepaidFlowNo) {
        this.prepaidFlowNo = prepaidFlowNo;
    }

    public String getReturnCashFlowNo() {
        return returnCashFlowNo;
    }

    public void setReturnCashFlowNo(String returnCashFlowNo) {
        this.returnCashFlowNo = returnCashFlowNo;
    }

    public String getReturnPrepaidFlowNo() {
        return returnPrepaidFlowNo;
    }

    public void setReturnPrepaidFlowNo(String returnPrepaidFlowNo) {
        this.returnPrepaidFlowNo = returnPrepaidFlowNo;
    }

    public String getEasOrderNo() {
        return easOrderNo;
    }

    public void setEasOrderNo(String easOrderNo) {
        this.easOrderNo = easOrderNo;
    }

    public String getEasOrderId() {
        return easOrderId;
    }

    public void setEasOrderId(String easOrderId) {
        this.easOrderId = easOrderId;
    }

    public BigDecimal getTotalGeneratedPrepaid() {
        return totalGeneratedPrepaid;
    }

    public void setTotalGeneratedPrepaid(BigDecimal totalGeneratedPrepaid) {
        this.totalGeneratedPrepaid = totalGeneratedPrepaid;
    }

    public Integer getSyncEas() {
        return syncEas;
    }

    public void setSyncEas(Integer syncEas) {
        this.syncEas = syncEas;
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

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }


    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SalesOrder{");
        sb.append("salesOrderId=").append(salesOrderId);
        sb.append(", salesOrderStatus=").append(salesOrderStatus);
        sb.append(", dataVersion=").append(dataVersion);
        sb.append(", projectId=").append(projectId);
        sb.append(", projectName='").append(projectName).append('\'');
        sb.append(", warehouseId=").append(warehouseId);
        sb.append(", warehouse='").append(warehouse).append('\'');
        sb.append(", brandId=").append(brandId);
        sb.append(", brandName='").append(brandName).append('\'');
        sb.append(", supplierId=").append(supplierId);
        sb.append(", supplierName='").append(supplierName).append('\'');
        sb.append(", jdSkuNumber=").append(jdSkuNumber);
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", totalCostQuantity=").append(totalCostQuantity);
        sb.append(", totalSalesAmount=").append(totalSalesAmount);
        sb.append(", totalOrderAmount=").append(totalOrderAmount);
        sb.append(", cashAmount=").append(cashAmount);
        sb.append(", shippingExpense=").append(shippingExpense);
        sb.append(", shippingExpensePaidBy=").append(shippingExpensePaidBy);
        sb.append(", currencyCode='").append(currencyCode).append('\'');
        sb.append(", prepaidAmount=").append(prepaidAmount);
        sb.append(", yhDiscountAmount=").append(yhDiscountAmount);
        sb.append(", supplierDiscountAmount=").append(supplierDiscountAmount);
        sb.append(", deliveredQuantity=").append(deliveredQuantity);
        sb.append(", inTransitQuantity=").append(inTransitQuantity);
        sb.append(", canceledQuantity=").append(canceledQuantity);
        sb.append(", returnedQuantity=").append(returnedQuantity);
        sb.append(", unhandledQuantity=").append(unhandledQuantity);
        sb.append(", shortageReasonQuantity=").append(shortageReasonQuantity);
        sb.append(", createdById=").append(createdById);
        sb.append(", createdByName='").append(createdByName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", purchaseTime=").append(purchaseTime);
        sb.append(", outBoundTime=").append(outBoundTime);
        sb.append(", orderCheckTime=").append(orderCheckTime);
        sb.append(", rejectTime=").append(rejectTime);
        sb.append(", paidTime=").append(paidTime);
        sb.append(", informWarehouseTime=").append(informWarehouseTime);
        sb.append(", signTime=").append(signTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", settlementMode=").append(settlementMode);
        sb.append(", salesOrderNo='").append(salesOrderNo).append('\'');
        sb.append(", comments='").append(comments).append('\'');
        sb.append(", marketingChannel=").append(marketingChannel);
        sb.append(", paymentChannel=").append(paymentChannel);
        sb.append(", paymentDays=").append(paymentDays);
        sb.append(", creditRemark='").append(creditRemark).append('\'');
        sb.append(", cashFlowNo='").append(cashFlowNo).append('\'');
        sb.append(", prepaidFlowNo='").append(prepaidFlowNo).append('\'');
        sb.append(", returnCashFlowNo='").append(returnCashFlowNo).append('\'');
        sb.append(", returnPrepaidFlowNo='").append(returnPrepaidFlowNo).append('\'');
        sb.append(", easOrderNo='").append(easOrderNo).append('\'');
        sb.append(", easOrderId='").append(easOrderId).append('\'');
        sb.append(", totalGeneratedPrepaid=").append(totalGeneratedPrepaid);
        sb.append(", syncEas=").append(syncEas);
        sb.append(", shouldReceiveGrossProfit=").append(shouldReceiveGrossProfit);
        sb.append(", receivedGrossProfit=").append(receivedGrossProfit);
        sb.append(", purchaseNo='").append(purchaseNo).append('\'');
        sb.append(", salesOrderAttribute=").append(salesOrderAttribute);
        sb.append(", distributorId=").append(distributorId);
        sb.append(", distributorName='").append(distributorName).append('\'');
        sb.append(", ongoingOutboundOrderInfo='").append(ongoingOutboundOrderInfo).append('\'');
        sb.append(", finishedOutboundOrderInfo='").append(finishedOutboundOrderInfo).append('\'');
        sb.append(", tracelog='").append(tracelog).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
