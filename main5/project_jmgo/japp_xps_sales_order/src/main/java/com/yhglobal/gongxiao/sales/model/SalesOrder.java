package com.yhglobal.gongxiao.sales.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售订单
 *
 * @Author: 葛灿
 */
public class SalesOrder implements Serializable {

    /**
     * 主键id
     */
    private Long salesOrderId;
    /**
     * 销售单状态
     * 对应枚举类 SalesOrderStatusEnum
     */
    private Integer salesOrderStatus;
    /**
     * 结算方式
     * 对应枚举类，SalesOrderSettlementModeEnum
     */
    private Integer settlementMode;
    /**
     * 销售单号
     */
    private String salesOrderNo;
    /**
     * 销售合同号
     */
    private String salesContactNo;
    /**
     * 销售渠道: 1:供销WEB端
     */
    private Integer marketingChannel;
    /**
     * 支付渠道: 1:线下银行转账 2:支付宝 3:微信
     */
    private Integer paymentChannel;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 账期天数
     */
    private Integer paymentDays;
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
     * 经销商id
     */
    private Long distributorId;
    /**
     * 经销商名称
     */
    private String distributorName;
    /**
     * 物流方式 0:不详 1:自提 2:第三方物流
     */
    private Integer shippingMode;
    /**
     * 发货仓库id
     */
    private Long warehouseId;
    /**
     * 发货仓库
     */
    private String warehouse;
    /**
     * 收货公司
     */
    private String recipientCompany;
    /**
     * 经销商的收货地址
     */
    private String shippingAddress;
    /**
     * 省id
     */
    private String provinceId;
    /**
     * 省
     */
    private String provinceName;
    /**
     * 市id
     */
    private String cityId;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区id
     */
    private String districtId;
    /**
     * 区
     */
    private String districtName;
    /**
     * 收件人名字
     */
    private String recipientName;
    /**
     * 收件人手机号
     */
    private String recipientMobile;
    /**
     * 运费
     */
    private Long shippingExpense;
    /**
     * 运费(浮点数)
     */
    private Double shippingExpenseDouble;
    /**
     * 运费承担方 1:越海 2:品牌商 3:经销商
     */
    private Integer shippingExpensePaidBy;
    /**
     * 结算货币ID
     */
    private String currencyId;
    /**
     * 结算货币码
     */
    private String currencyCode;
    /**
     * 采购总金额
     */
    private Long totalOrderAmount;
    /**
     * 采购总金额 double
     */
    private String totalOrderAmountDouble;
    /**
     * 折扣额度
     */
    private Long discountAmount;
    /**
     * 折扣额度(浮点数)
     */
    private Double discountAmountDouble;
    /**
     * 使用的返利金额
     */
    private Long couponAmount;

    /**
     * 使用的代垫金额
     */
    private Long prepaidAmount;

    /**
     * 使用现金金额
     */
    private Long cashAmount;
    /**
     * 使用预付款金额
     */
    private long prestoredAmount;
    /**
     * 产品型号(JSON)
     * String转为Set<String>
     * 里面存放的是该订单涉及到的产品型号
     */
    private String itemsCode;
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
     * 现金流水号
     */
    private String cashFlowNo;
    /**
     * 返利流水号
     */
    private String couponFlowNo;
    /**
     * 代垫流水号
     */
    private String prepaidFlowNo;
    /**
     * 取消订单产生的逆向现金流水号
     */
    private String returnCashFlowNo;
    /**
     * 取消订单产生的逆向返利流水号
     */
    private String returnCouponFlowNo;
    /**
     * 取消订单产生的逆向代垫流水号
     */
    private String returnPrepaidFlowNo;

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
     * 创建人id
     */
    private Long createdById;
    /**
     * 创建人名称
     */
    private String createdByName;
    /**
     * 出库时间
     */
    private Date outBoundTime;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
    private Date createTime;
    /**
     * 订单处理时间
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
     * 下发仓库时间
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
     * 操作日记
     */
    private String tracelog;
    /**
     * 供应商支持金
     */
    private Long supplierDiscountAmount;

    /**
     * 越海代垫金
     */
    private Long yhDiscountAmount;
    /**
     * 低买高卖金额
     */
    private Long sellHighAmount;
    /**
     * 账期结算备注
     */
    private String creditRemark;

    private String easOrderNo;

    private String easOrderId;

    private String cashAmountDoubleStr;
    /**
     * 产生代垫
     */
    private long totalGeneratedPrepaid;
    /**
     * 同步eas状态
     */
    private int syncEas;

    /**
     * 应收毛利
     */
    private long shouldReceiveGrossProfit;
    /**
     * 实收毛利
     */
    private long receivedGrossProfit;
    /**
     * 坚果平台优惠金额
     */
    private long discountAmountJmgo;

    public long getDiscountAmountJmgo() {
        return discountAmountJmgo;
    }

    public void setDiscountAmountJmgo(long discountAmountJmgo) {
        this.discountAmountJmgo = discountAmountJmgo;
    }

    public long getShouldReceiveGrossProfit() {
        return shouldReceiveGrossProfit;
    }

    public void setShouldReceiveGrossProfit(long shouldReceiveGrossProfit) {
        this.shouldReceiveGrossProfit = shouldReceiveGrossProfit;
    }

    public long getReceivedGrossProfit() {
        return receivedGrossProfit;
    }

    public void setReceivedGrossProfit(long receivedGrossProfit) {
        this.receivedGrossProfit = receivedGrossProfit;
    }

    public int getSyncEas() {
        return syncEas;
    }

    public void setSyncEas(int syncEas) {
        this.syncEas = syncEas;
    }

    public long getTotalGeneratedPrepaid() {
        return totalGeneratedPrepaid;
    }

    public void setTotalGeneratedPrepaid(long totalGeneratedPrepaid) {
        this.totalGeneratedPrepaid = totalGeneratedPrepaid;
    }

    public String getCashAmountDoubleStr() {
        return cashAmountDoubleStr;
    }

    public void setCashAmountDoubleStr(String cashAmountDoubleStr) {
        this.cashAmountDoubleStr = cashAmountDoubleStr;
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

    public String getCreditRemark() {
        return creditRemark;
    }

    public void setCreditRemark(String creditRemark) {
        this.creditRemark = creditRemark;
    }

    public Long getSellHighAmount() {
        return sellHighAmount;
    }

    public void setSellHighAmount(Long sellHighAmount) {
        this.sellHighAmount = sellHighAmount;
    }

    public String getCashFlowNo() {
        return cashFlowNo;
    }

    public void setCashFlowNo(String cashFlowNo) {
        this.cashFlowNo = cashFlowNo;
    }

    public String getCouponFlowNo() {
        return couponFlowNo;
    }

    public void setCouponFlowNo(String couponFlowNo) {
        this.couponFlowNo = couponFlowNo;
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

    public String getReturnCouponFlowNo() {
        return returnCouponFlowNo;
    }

    public void setReturnCouponFlowNo(String returnCouponFlowNo) {
        this.returnCouponFlowNo = returnCouponFlowNo;
    }

    public String getReturnPrepaidFlowNo() {
        return returnPrepaidFlowNo;
    }

    public void setReturnPrepaidFlowNo(String returnPrepaidFlowNo) {
        this.returnPrepaidFlowNo = returnPrepaidFlowNo;
    }

    public long getPrestoredAmount() {
        return prestoredAmount;
    }

    public void setPrestoredAmount(long prestoredAmount) {
        this.prestoredAmount = prestoredAmount;
    }

    public String getTotalOrderAmountDouble() {
        return totalOrderAmountDouble;
    }

    public void setTotalOrderAmountDouble(String totalOrderAmountDouble) {
        this.totalOrderAmountDouble = totalOrderAmountDouble;
    }

    public String getItemsCode() {
        return itemsCode;
    }

    public void setItemsCode(String itemsCode) {
        this.itemsCode = itemsCode;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Integer getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(Integer salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getSalesContactNo() {
        return salesContactNo;
    }

    public void setSalesContactNo(String salesContactNo) {
        this.salesContactNo = salesContactNo;
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

    public Integer getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(Integer shippingMode) {
        this.shippingMode = shippingMode;
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

    public String getRecipientCompany() {
        return recipientCompany;
    }

    public void setRecipientCompany(String recipientCompany) {
        this.recipientCompany = recipientCompany;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientMobile() {
        return recipientMobile;
    }

    public void setRecipientMobile(String recipientMobile) {
        this.recipientMobile = recipientMobile;
    }

    public Long getShippingExpense() {
        return shippingExpense;
    }

    public void setShippingExpense(Long shippingExpense) {
        this.shippingExpense = shippingExpense;
    }

    public Double getShippingExpenseDouble() {
        return shippingExpenseDouble;
    }

    public void setShippingExpenseDouble(Double shippingExpenseDouble) {
        this.shippingExpenseDouble = shippingExpenseDouble;
    }

    public Integer getShippingExpensePaidBy() {
        return shippingExpensePaidBy;
    }

    public void setShippingExpensePaidBy(Integer shippingExpensePaidBy) {
        this.shippingExpensePaidBy = shippingExpensePaidBy;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(Long totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountAmountDouble() {
        return discountAmountDouble;
    }

    public void setDiscountAmountDouble(Double discountAmountDouble) {
        this.discountAmountDouble = discountAmountDouble;
    }

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(Long prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
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

    public Date getOutBoundTime() {
        return outBoundTime;
    }

    public void setOutBoundTime(Date outBoundTime) {
        this.outBoundTime = outBoundTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
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

    public Integer getSettlementMode() {
        return settlementMode;
    }

    public void setSettlementMode(Integer settlementMode) {
        this.settlementMode = settlementMode;
    }

    public Integer getPaymentDays() {
        return paymentDays;
    }

    public void setPaymentDays(Integer paymentDays) {
        this.paymentDays = paymentDays;
    }

//    @Override
//    public String toString() {
//        return salesOrderNo;
//    }


    @Override
    public String toString() {
        return "SalesOrder{" +
                "salesOrderId=" + salesOrderId +
                ", salesOrderStatus=" + salesOrderStatus +
                ", settlementMode=" + settlementMode +
                ", salesOrderNo='" + salesOrderNo + '\'' +
                ", salesContactNo='" + salesContactNo + '\'' +
                ", marketingChannel=" + marketingChannel +
                ", paymentChannel=" + paymentChannel +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", paymentDays=" + paymentDays +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", distributorId=" + distributorId +
                ", distributorName='" + distributorName + '\'' +
                ", shippingMode=" + shippingMode +
                ", warehouseId=" + warehouseId +
                ", warehouse='" + warehouse + '\'' +
                ", recipientCompany='" + recipientCompany + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", districtId='" + districtId + '\'' +
                ", districtName='" + districtName + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", recipientMobile='" + recipientMobile + '\'' +
                ", shippingExpense=" + shippingExpense +
                ", shippingExpenseDouble=" + shippingExpenseDouble +
                ", shippingExpensePaidBy=" + shippingExpensePaidBy +
                ", currencyId='" + currencyId + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", totalOrderAmount=" + totalOrderAmount +
                ", totalOrderAmountDouble='" + totalOrderAmountDouble + '\'' +
                ", discountAmount=" + discountAmount +
                ", discountAmountDouble=" + discountAmountDouble +
                ", couponAmount=" + couponAmount +
                ", prepaidAmount=" + prepaidAmount +
                ", cashAmount=" + cashAmount +
                ", prestoredAmount=" + prestoredAmount +
                ", itemsCode='" + itemsCode + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", deliveredQuantity=" + deliveredQuantity +
                ", inTransitQuantity=" + inTransitQuantity +
                ", canceledQuantity=" + canceledQuantity +
                ", returnedQuantity=" + returnedQuantity +
                ", unhandledQuantity=" + unhandledQuantity +
                ", cashFlowNo='" + cashFlowNo + '\'' +
                ", couponFlowNo='" + couponFlowNo + '\'' +
                ", prepaidFlowNo='" + prepaidFlowNo + '\'' +
                ", returnCashFlowNo='" + returnCashFlowNo + '\'' +
                ", returnCouponFlowNo='" + returnCouponFlowNo + '\'' +
                ", returnPrepaidFlowNo='" + returnPrepaidFlowNo + '\'' +
                ", ongoingOutboundOrderInfo='" + ongoingOutboundOrderInfo + '\'' +
                ", finishedOutboundOrderInfo='" + finishedOutboundOrderInfo + '\'' +
                ", dataVersion=" + dataVersion +
                ", createdById=" + createdById +
                ", createdByName='" + createdByName + '\'' +
                ", outBoundTime=" + outBoundTime +
                ", createTime=" + createTime +
                ", orderCheckTime=" + orderCheckTime +
                ", rejectTime=" + rejectTime +
                ", paidTime=" + paidTime +
                ", informWarehouseTime=" + informWarehouseTime +
                ", signTime=" + signTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", tracelog='" + tracelog + '\'' +
                ", supplierDiscountAmount=" + supplierDiscountAmount +
                ", yhDiscountAmount=" + yhDiscountAmount +
                ", sellHighAmount=" + sellHighAmount +
                ", creditRemark='" + creditRemark + '\'' +
                ", easOrderNo='" + easOrderNo + '\'' +
                ", easOrderId='" + easOrderId + '\'' +
                ", cashAmountDoubleStr='" + cashAmountDoubleStr + '\'' +
                ", totalGeneratedPrepaid=" + totalGeneratedPrepaid +
                ", syncEas=" + syncEas +
                '}';
    }
}