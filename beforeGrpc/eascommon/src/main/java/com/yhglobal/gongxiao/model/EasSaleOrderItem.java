package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EAS销售订单货品信息
 *
 * @author: 陈浩
 **/
public class EasSaleOrderItem implements Serializable{

    private String baseStatus;

    private int isPresent;
    /**物料*/
    private String material;
    /**计量单位*/
    private String unit;
    /**备注*/
    private String remark;
    /**数量*/
    private BigDecimal qty;
    /**单价*/
    private BigDecimal price;
    /**含税单价*/
    private BigDecimal taxPrice;
    /**税率*/
    private BigDecimal taxRate;
    /**发货日期*/
    private Date sendDate;
    /**库存组织*/
    private String storageOrgUnit;
    /**财务组织*/
    private String companyOrgUnit;
    /**仓库*/
    private String warehouse;
    /**折扣额*/
    private BigDecimal discount;
    /**基本单位数量*/
    private BigDecimal baseQty;
    /**辅助单位数量*/
    private BigDecimal assistQty;
    /**减价比例*/
    private BigDecimal cheapRate;
    /**折扣条件*/
    private String discountCondition;
    /**折扣方式*/
    private String discountType;
    /**折扣金额*/
    private BigDecimal discountAmount;
    /**金额*/
    private BigDecimal amount;
    /**本位币金额*/
    private BigDecimal localAmount;
    /**实际单价*/
    private BigDecimal actualPrice;
    /**实际含税单价*/
    private BigDecimal actualTaxPrice;
    /**税额*/
    private BigDecimal tax;
    /**价税合计*/
    private BigDecimal taxAmount;
    /**交货日期*/
    private Date deliveryDate;
    /**发货超交比率*/
    private BigDecimal sendOverRate;
    /**累计出库数量*/
    private BigDecimal totalIssueQty;
    /**累计退货申请数量*/
    private BigDecimal totalReturnedQty;
    /**累计应收数量*/
    private BigDecimal totalInvoicedQty;
    /**累计通知发货数量*/
    private BigDecimal totalShippingQty;
    /**累计收款金额*/
    private BigDecimal totalReceivedAmount;
    /**基本累计出库数量*/
    private BigDecimal totalIssueBaseQty;
    /**基本累计退货数量*/
    private BigDecimal totalReBaseQty;
    /**基本累计应收数量*/
    private BigDecimal tolInvoidBaseQty;
    /**基本累计通知发货数量*/
    private BigDecimal totalShipBaseQty;
    /**基本累计未退货数量*/
    private BigDecimal totalUnReturnBaseQty;
    /**基本累计未出库数量*/
    private BigDecimal totalUnIssueBaseQty;
    /**基本累计未通知发货数量*/
    private BigDecimal totalUnShipBaseQty;
    /**累计未出库数量*/
    private BigDecimal totalUnIssueQty;
    /**已锁库数量*/
    private BigDecimal lockQty;
    /**已锁库基本单位数量*/
    private BigDecimal lockBaseQty;
    /**锁库辅助单位数量*/
    private BigDecimal lockAssistQty;
    /**本位币税额*/
    private BigDecimal localTax;
    /**加税合计本位币*/
    private BigDecimal localTaxAmount;
    /**已定货数量*/
    private BigDecimal orderedQty;
    /**未订货数量*/
    private BigDecimal unOrderedQty;
    /**预收比率*/
    private BigDecimal prepaymentRate;
    /**预收款金额*/
    private String prepayment;
    /**已收预收款*/
    private String preReceived;
    /**未收预收款金额*/
    private BigDecimal unPrereceivedAmount;
    /**不控制数量*/
    private String quantityUnCtrl;
    /**不控制时间*/
    private String timeUnCtrl;
    /**交货天数*/
    private BigDecimal deliveryDateQty;
    /**原因*/
    private String reason;
    /**累计开票金额*/
    private BigDecimal totalInvoicedAmt;
    /**累计应收金额*/
    private BigDecimal totalArAmount;
    /**原id*/
    private String version;
    /**累计冲销数量*/
    private BigDecimal totalReversedQty;
    /**基本累计冲销数量*/
    private BigDecimal totalReversedBaseQty;
    /**安排发货数量*/
    private BigDecimal planDeliveryQty;
    /**累计退库数量*/
    private BigDecimal totalCancellingStockQty;
    /**累计退货需补货数量*/
    private BigDecimal totalSupplyStockQty;
    /**送货客户*/
    private String deliveryCustomer;
    /**收款客户*/
    private String paymentCustomer;
    /**应收客户*/
    private String receiveCustomer;
    /**送货地址*/
    private String sendAddress;
    /**协同单据编号*/
    private String netOrderBillNumber;
    /**累计生产数量*/
    private BigDecimal totalProductQty;
    /**累计单位生产数*/
    private BigDecimal totalBaseunProductQty;
    /**匹配金额*/
    private BigDecimal matchedAmount;
    /**项目号*/
    private String project;
    /**跟踪号*/
    private String trackNumber;
    /**退货安排发运基本数量*/
    private BigDecimal returnPlanDeliveryBaseQty;
    /**销售组织*/
    private String saleOrgUnit;
    /**业务日期*/
    private Date bizDate;
    /**物料组*/
    private String materialGroup;
    /**供货方式*/
    private String supplyMode;
    /**累计调拨数量*/
    private BigDecimal totalTransferQty;
    /**累计调拨基本数量*/
    private BigDecimal totalTransferBaseQty;
    /**可调拨数量*/
    private BigDecimal totalUnTransferQty;
    /**可调拨基本数量*/
    private BigDecimal totalUntransferBaseQty;
    /**供货组织*/
    private String proStorageOrgUnit;
    /**基本计量单位*/
    private String baseUnit;
    /**辅助计量单位*/
    private String assistUnit;

    private String InvUpdateType;//更新方式

    public String getInvUpdateType() {
        return InvUpdateType;
    }

    public void setInvUpdateType(String invUpdateType) {
        InvUpdateType = invUpdateType;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getStorageOrgUnit() {
        return storageOrgUnit;
    }

    public void setStorageOrgUnit(String storageOrgUnit) {
        this.storageOrgUnit = storageOrgUnit;
    }

    public String getCompanyOrgUnit() {
        return companyOrgUnit;
    }

    public void setCompanyOrgUnit(String companyOrgUnit) {
        this.companyOrgUnit = companyOrgUnit;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(BigDecimal baseQty) {
        this.baseQty = baseQty;
    }

    public BigDecimal getAssistQty() {
        return assistQty;
    }

    public void setAssistQty(BigDecimal assistQty) {
        this.assistQty = assistQty;
    }

    public BigDecimal getCheapRate() {
        return cheapRate;
    }

    public void setCheapRate(BigDecimal cheapRate) {
        this.cheapRate = cheapRate;
    }

    public String getDiscountCondition() {
        return discountCondition;
    }

    public void setDiscountCondition(String discountCondition) {
        this.discountCondition = discountCondition;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getActualTaxPrice() {
        return actualTaxPrice;
    }

    public void setActualTaxPrice(BigDecimal actualTaxPrice) {
        this.actualTaxPrice = actualTaxPrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getSendOverRate() {
        return sendOverRate;
    }

    public void setSendOverRate(BigDecimal sendOverRate) {
        this.sendOverRate = sendOverRate;
    }

    public BigDecimal getTotalIssueQty() {
        return totalIssueQty;
    }

    public void setTotalIssueQty(BigDecimal totalIssueQty) {
        this.totalIssueQty = totalIssueQty;
    }

    public BigDecimal getTotalReturnedQty() {
        return totalReturnedQty;
    }

    public void setTotalReturnedQty(BigDecimal totalReturnedQty) {
        this.totalReturnedQty = totalReturnedQty;
    }

    public BigDecimal getTotalInvoicedQty() {
        return totalInvoicedQty;
    }

    public void setTotalInvoicedQty(BigDecimal totalInvoicedQty) {
        this.totalInvoicedQty = totalInvoicedQty;
    }

    public BigDecimal getTotalShippingQty() {
        return totalShippingQty;
    }

    public void setTotalShippingQty(BigDecimal totalShippingQty) {
        this.totalShippingQty = totalShippingQty;
    }

    public BigDecimal getTotalReceivedAmount() {
        return totalReceivedAmount;
    }

    public void setTotalReceivedAmount(BigDecimal totalReceivedAmount) {
        this.totalReceivedAmount = totalReceivedAmount;
    }

    public BigDecimal getTotalIssueBaseQty() {
        return totalIssueBaseQty;
    }

    public void setTotalIssueBaseQty(BigDecimal totalIssueBaseQty) {
        this.totalIssueBaseQty = totalIssueBaseQty;
    }

    public BigDecimal getTotalReBaseQty() {
        return totalReBaseQty;
    }

    public void setTotalReBaseQty(BigDecimal totalReBaseQty) {
        this.totalReBaseQty = totalReBaseQty;
    }

    public BigDecimal getTolInvoidBaseQty() {
        return tolInvoidBaseQty;
    }

    public void setTolInvoidBaseQty(BigDecimal tolInvoidBaseQty) {
        this.tolInvoidBaseQty = tolInvoidBaseQty;
    }

    public BigDecimal getTotalShipBaseQty() {
        return totalShipBaseQty;
    }

    public void setTotalShipBaseQty(BigDecimal totalShipBaseQty) {
        this.totalShipBaseQty = totalShipBaseQty;
    }

    public BigDecimal getTotalUnReturnBaseQty() {
        return totalUnReturnBaseQty;
    }

    public void setTotalUnReturnBaseQty(BigDecimal totalUnReturnBaseQty) {
        this.totalUnReturnBaseQty = totalUnReturnBaseQty;
    }

    public BigDecimal getTotalUnIssueBaseQty() {
        return totalUnIssueBaseQty;
    }

    public void setTotalUnIssueBaseQty(BigDecimal totalUnIssueBaseQty) {
        this.totalUnIssueBaseQty = totalUnIssueBaseQty;
    }

    public BigDecimal getTotalUnShipBaseQty() {
        return totalUnShipBaseQty;
    }

    public void setTotalUnShipBaseQty(BigDecimal totalUnShipBaseQty) {
        this.totalUnShipBaseQty = totalUnShipBaseQty;
    }

    public BigDecimal getTotalUnIssueQty() {
        return totalUnIssueQty;
    }

    public void setTotalUnIssueQty(BigDecimal totalUnIssueQty) {
        this.totalUnIssueQty = totalUnIssueQty;
    }

    public BigDecimal getLockQty() {
        return lockQty;
    }

    public void setLockQty(BigDecimal lockQty) {
        this.lockQty = lockQty;
    }

    public BigDecimal getLockBaseQty() {
        return lockBaseQty;
    }

    public void setLockBaseQty(BigDecimal lockBaseQty) {
        this.lockBaseQty = lockBaseQty;
    }

    public BigDecimal getLockAssistQty() {
        return lockAssistQty;
    }

    public void setLockAssistQty(BigDecimal lockAssistQty) {
        this.lockAssistQty = lockAssistQty;
    }

    public BigDecimal getLocalTax() {
        return localTax;
    }

    public void setLocalTax(BigDecimal localTax) {
        this.localTax = localTax;
    }

    public BigDecimal getLocalTaxAmount() {
        return localTaxAmount;
    }

    public void setLocalTaxAmount(BigDecimal localTaxAmount) {
        this.localTaxAmount = localTaxAmount;
    }

    public BigDecimal getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(BigDecimal orderedQty) {
        this.orderedQty = orderedQty;
    }

    public BigDecimal getUnOrderedQty() {
        return unOrderedQty;
    }

    public void setUnOrderedQty(BigDecimal unOrderedQty) {
        this.unOrderedQty = unOrderedQty;
    }

    public BigDecimal getPrepaymentRate() {
        return prepaymentRate;
    }

    public void setPrepaymentRate(BigDecimal prepaymentRate) {
        this.prepaymentRate = prepaymentRate;
    }

    public String getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(String prepayment) {
        this.prepayment = prepayment;
    }

    public String getPreReceived() {
        return preReceived;
    }

    public void setPreReceived(String preReceived) {
        this.preReceived = preReceived;
    }

    public BigDecimal getUnPrereceivedAmount() {
        return unPrereceivedAmount;
    }

    public void setUnPrereceivedAmount(BigDecimal unPrereceivedAmount) {
        this.unPrereceivedAmount = unPrereceivedAmount;
    }

    public String getQuantityUnCtrl() {
        return quantityUnCtrl;
    }

    public void setQuantityUnCtrl(String quantityUnCtrl) {
        this.quantityUnCtrl = quantityUnCtrl;
    }

    public String getTimeUnCtrl() {
        return timeUnCtrl;
    }

    public void setTimeUnCtrl(String timeUnCtrl) {
        this.timeUnCtrl = timeUnCtrl;
    }

    public BigDecimal getDeliveryDateQty() {
        return deliveryDateQty;
    }

    public void setDeliveryDateQty(BigDecimal deliveryDateQty) {
        this.deliveryDateQty = deliveryDateQty;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getTotalInvoicedAmt() {
        return totalInvoicedAmt;
    }

    public void setTotalInvoicedAmt(BigDecimal totalInvoicedAmt) {
        this.totalInvoicedAmt = totalInvoicedAmt;
    }

    public BigDecimal getTotalArAmount() {
        return totalArAmount;
    }

    public void setTotalArAmount(BigDecimal totalArAmount) {
        this.totalArAmount = totalArAmount;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BigDecimal getTotalReversedQty() {
        return totalReversedQty;
    }

    public void setTotalReversedQty(BigDecimal totalReversedQty) {
        this.totalReversedQty = totalReversedQty;
    }

    public BigDecimal getTotalReversedBaseQty() {
        return totalReversedBaseQty;
    }

    public void setTotalReversedBaseQty(BigDecimal totalReversedBaseQty) {
        this.totalReversedBaseQty = totalReversedBaseQty;
    }

    public BigDecimal getPlanDeliveryQty() {
        return planDeliveryQty;
    }

    public void setPlanDeliveryQty(BigDecimal planDeliveryQty) {
        this.planDeliveryQty = planDeliveryQty;
    }

    public BigDecimal getTotalCancellingStockQty() {
        return totalCancellingStockQty;
    }

    public void setTotalCancellingStockQty(BigDecimal totalCancellingStockQty) {
        this.totalCancellingStockQty = totalCancellingStockQty;
    }

    public BigDecimal getTotalSupplyStockQty() {
        return totalSupplyStockQty;
    }

    public void setTotalSupplyStockQty(BigDecimal totalSupplyStockQty) {
        this.totalSupplyStockQty = totalSupplyStockQty;
    }

    public String getDeliveryCustomer() {
        return deliveryCustomer;
    }

    public void setDeliveryCustomer(String deliveryCustomer) {
        this.deliveryCustomer = deliveryCustomer;
    }

    public String getPaymentCustomer() {
        return paymentCustomer;
    }

    public void setPaymentCustomer(String paymentCustomer) {
        this.paymentCustomer = paymentCustomer;
    }

    public String getReceiveCustomer() {
        return receiveCustomer;
    }

    public void setReceiveCustomer(String receiveCustomer) {
        this.receiveCustomer = receiveCustomer;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getNetOrderBillNumber() {
        return netOrderBillNumber;
    }

    public void setNetOrderBillNumber(String netOrderBillNumber) {
        this.netOrderBillNumber = netOrderBillNumber;
    }

    public BigDecimal getTotalProductQty() {
        return totalProductQty;
    }

    public void setTotalProductQty(BigDecimal totalProductQty) {
        this.totalProductQty = totalProductQty;
    }

    public BigDecimal getTotalBaseunProductQty() {
        return totalBaseunProductQty;
    }

    public void setTotalBaseunProductQty(BigDecimal totalBaseunProductQty) {
        this.totalBaseunProductQty = totalBaseunProductQty;
    }

    public BigDecimal getMatchedAmount() {
        return matchedAmount;
    }

    public void setMatchedAmount(BigDecimal matchedAmount) {
        this.matchedAmount = matchedAmount;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public BigDecimal getReturnPlanDeliveryBaseQty() {
        return returnPlanDeliveryBaseQty;
    }

    public void setReturnPlanDeliveryBaseQty(BigDecimal returnPlanDeliveryBaseQty) {
        this.returnPlanDeliveryBaseQty = returnPlanDeliveryBaseQty;
    }

    public String getSaleOrgUnit() {
        return saleOrgUnit;
    }

    public void setSaleOrgUnit(String saleOrgUnit) {
        this.saleOrgUnit = saleOrgUnit;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getMaterialGroup() {
        return materialGroup;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    public String getSupplyMode() {
        return supplyMode;
    }

    public void setSupplyMode(String supplyMode) {
        this.supplyMode = supplyMode;
    }

    public BigDecimal getTotalTransferQty() {
        return totalTransferQty;
    }

    public void setTotalTransferQty(BigDecimal totalTransferQty) {
        this.totalTransferQty = totalTransferQty;
    }

    public BigDecimal getTotalTransferBaseQty() {
        return totalTransferBaseQty;
    }

    public void setTotalTransferBaseQty(BigDecimal totalTransferBaseQty) {
        this.totalTransferBaseQty = totalTransferBaseQty;
    }

    public BigDecimal getTotalUnTransferQty() {
        return totalUnTransferQty;
    }

    public void setTotalUnTransferQty(BigDecimal totalUnTransferQty) {
        this.totalUnTransferQty = totalUnTransferQty;
    }

    public BigDecimal getTotalUntransferBaseQty() {
        return totalUntransferBaseQty;
    }

    public void setTotalUntransferBaseQty(BigDecimal totalUntransferBaseQty) {
        this.totalUntransferBaseQty = totalUntransferBaseQty;
    }

    public String getProStorageOrgUnit() {
        return proStorageOrgUnit;
    }

    public void setProStorageOrgUnit(String proStorageOrgUnit) {
        this.proStorageOrgUnit = proStorageOrgUnit;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getAssistUnit() {
        return assistUnit;
    }

    public void setAssistUnit(String assistUnit) {
        this.assistUnit = assistUnit;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }
}
