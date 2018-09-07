package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * EAS销售订单
 *
 * @author: 陈浩
 **/
public class EasSaleOrder implements Serializable{
    private String baseStatus;
    /**创建人*/
    private String creator;
    /**创建时间*/
    private String createTime;
    /**业务日期（京东PO采购日期）*/
    private Date bizDate;
    /**订货客户*/
    private String orderCustomer;
    /**交货方式*/
    private String deliveryType;
    /**币别*/
    private String currency;
    /**付款方式*/
    private String paymentType;
    /**销售组织*/
    private String saleOrgUnit;
    /**是否含税*/
    private Integer isInTax;
    /**财务组织*/
    private String companyOrgUnit;
    /**内部销售*/
    private Integer isInnerSale;
    /**直运供应商*/
    private String directSupplier;
    /**采购组织*/
    private String purchaseOrgUnit;
    /**运输提前期*/
    private String transLeadTime;
    /**汇率*/
    private BigDecimal exchangeRate;
    /**现金折扣*/
    private String cashDiscount;
    /**结算方式*/
    private String settlementType;
    /**预售金额*/
    private BigDecimal prepayment;
    /**预收比率*/
    private BigDecimal prepaymentRate;
    /**销售组*/
    private String saleGroup;
    /**销售员*/
    private String salePerson;
    /**部门*/
    private String adminOrgUnit;
    /**金额*/
    private BigDecimal totalAmount;
    /**税额*/
    private BigDecimal totalTax;
    /**价税合计*/
    private BigDecimal totalTaxAmount;
    /**已收应收款*/
    private String preReceived;
    /**为预收款金额*/
    private String unPrereceivedAmount;
    /**送货地址*/
    private String sendAddress;
    /**是否系统单据*/
    private Integer isSysBill;
    /**折算方式*/
    private String convertMode;
    /**金额本位币合计*/
    private BigDecimal localTotalAmount;
    /**价税合计本位币合计*/
    private BigDecimal localTotalTaxAmount;
    /**联系电话*/
    private String customerPhone;
    /**联系人*/
    private String linkMan;
    /**变更人*/
    private String alterPerson;
    /**旧ID*/
    private String oldID;
    /**旧状态*/
    private String oldStatus;
    /**版本*/
    private String version;
    /**变更日期*/
    private String alterDate;
    /**销售方库存组织*/
    private String storageOrgUnit;
    /**已收预收款金额*/
    private String beenPaidPrepayment;
    /**收款条件*/
    private String receiveCondition;
    /**销售方仓库*/
    private String warehouse;
    /**结算方财务组织*/
    private String balanceCompanyOrgUnit;
    /**结算方销售组织*/
    private String balanceSaleOrgUnit;
    /**结算反库存组织*/
    private String balanceStorageOrgUnit;
    /**结算反仓库*/
    private String balanceWarehouse;
    /**商机*/
    private String business;
    /**CRM客户*/
    private String CRMCustomerID;
    /**原整单折扣金额*/
    private BigDecimal originalDiscountAmount;
    /**审批时间*/
    private Date auditTime;
    /**单据类型*/
    private String billType;
    private String bizType;
    /**编码*/
    private String number;
    /**审核人*/
    private String auditor;


    /***************************by xieli********************/
    /**客户订单号（京东PO采购单号）*/
    private String customerOrderNumber;
    /**收货地址*/
    private String receiverAddress;
    /**收货人姓名*/
    private String receiverName;
    /**电话*/
    private String receiverTel;
    /**目的地城市*/
    private String receiverCity;

    private String wms;
    /**分录**/
    public List<EasSaleOrderItem> entries;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(String orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSaleOrgUnit() {
        return saleOrgUnit;
    }

    public void setSaleOrgUnit(String saleOrgUnit) {
        this.saleOrgUnit = saleOrgUnit;
    }

    public Integer getIsInTax() {
        return isInTax;
    }

    public void setIsInTax(Integer isInTax) {
        this.isInTax = isInTax;
    }

    public String getCompanyOrgUnit() {
        return companyOrgUnit;
    }

    public void setCompanyOrgUnit(String companyOrgUnit) {
        this.companyOrgUnit = companyOrgUnit;
    }

    public Integer getIsInnerSale() {
        return isInnerSale;
    }

    public void setIsInnerSale(Integer isInnerSale) {
        this.isInnerSale = isInnerSale;
    }

    public String getDirectSupplier() {
        return directSupplier;
    }

    public void setDirectSupplier(String directSupplier) {
        this.directSupplier = directSupplier;
    }

    public String getPurchaseOrgUnit() {
        return purchaseOrgUnit;
    }

    public void setPurchaseOrgUnit(String purchaseOrgUnit) {
        this.purchaseOrgUnit = purchaseOrgUnit;
    }

    public String getTransLeadTime() {
        return transLeadTime;
    }

    public void setTransLeadTime(String transLeadTime) {
        this.transLeadTime = transLeadTime;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getCashDiscount() {
        return cashDiscount;
    }

    public void setCashDiscount(String cashDiscount) {
        this.cashDiscount = cashDiscount;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public BigDecimal getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(BigDecimal prepayment) {
        this.prepayment = prepayment;
    }

    public BigDecimal getPrepaymentRate() {
        return prepaymentRate;
    }

    public void setPrepaymentRate(BigDecimal prepaymentRate) {
        this.prepaymentRate = prepaymentRate;
    }

    public String getSaleGroup() {
        return saleGroup;
    }

    public void setSaleGroup(String saleGroup) {
        this.saleGroup = saleGroup;
    }

    public String getSalePerson() {
        return salePerson;
    }

    public void setSalePerson(String salePerson) {
        this.salePerson = salePerson;
    }

    public String getAdminOrgUnit() {
        return adminOrgUnit;
    }

    public void setAdminOrgUnit(String adminOrgUnit) {
        this.adminOrgUnit = adminOrgUnit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getPreReceived() {
        return preReceived;
    }

    public void setPreReceived(String preReceived) {
        this.preReceived = preReceived;
    }

    public String getUnPrereceivedAmount() {
        return unPrereceivedAmount;
    }

    public void setUnPrereceivedAmount(String unPrereceivedAmount) {
        this.unPrereceivedAmount = unPrereceivedAmount;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public Integer getIsSysBill() {
        return isSysBill;
    }

    public void setIsSysBill(Integer isSysBill) {
        this.isSysBill = isSysBill;
    }

    public String getConvertMode() {
        return convertMode;
    }

    public void setConvertMode(String convertMode) {
        this.convertMode = convertMode;
    }

    public BigDecimal getLocalTotalAmount() {
        return localTotalAmount;
    }

    public void setLocalTotalAmount(BigDecimal localTotalAmount) {
        this.localTotalAmount = localTotalAmount;
    }

    public BigDecimal getLocalTotalTaxAmount() {
        return localTotalTaxAmount;
    }

    public void setLocalTotalTaxAmount(BigDecimal localTotalTaxAmount) {
        this.localTotalTaxAmount = localTotalTaxAmount;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getAlterPerson() {
        return alterPerson;
    }

    public void setAlterPerson(String alterPerson) {
        this.alterPerson = alterPerson;
    }

    public String getOldID() {
        return oldID;
    }

    public void setOldID(String oldID) {
        this.oldID = oldID;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAlterDate() {
        return alterDate;
    }

    public void setAlterDate(String alterDate) {
        this.alterDate = alterDate;
    }

    public String getStorageOrgUnit() {
        return storageOrgUnit;
    }

    public void setStorageOrgUnit(String storageOrgUnit) {
        this.storageOrgUnit = storageOrgUnit;
    }

    public String getBeenPaidPrepayment() {
        return beenPaidPrepayment;
    }

    public void setBeenPaidPrepayment(String beenPaidPrepayment) {
        this.beenPaidPrepayment = beenPaidPrepayment;
    }

    public String getReceiveCondition() {
        return receiveCondition;
    }

    public void setReceiveCondition(String receiveCondition) {
        this.receiveCondition = receiveCondition;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getBalanceCompanyOrgUnit() {
        return balanceCompanyOrgUnit;
    }

    public void setBalanceCompanyOrgUnit(String balanceCompanyOrgUnit) {
        this.balanceCompanyOrgUnit = balanceCompanyOrgUnit;
    }

    public String getBalanceSaleOrgUnit() {
        return balanceSaleOrgUnit;
    }

    public void setBalanceSaleOrgUnit(String balanceSaleOrgUnit) {
        this.balanceSaleOrgUnit = balanceSaleOrgUnit;
    }

    public String getBalanceStorageOrgUnit() {
        return balanceStorageOrgUnit;
    }

    public void setBalanceStorageOrgUnit(String balanceStorageOrgUnit) {
        this.balanceStorageOrgUnit = balanceStorageOrgUnit;
    }

    public String getBalanceWarehouse() {
        return balanceWarehouse;
    }

    public void setBalanceWarehouse(String balanceWarehouse) {
        this.balanceWarehouse = balanceWarehouse;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCRMCustomerID() {
        return CRMCustomerID;
    }

    public void setCRMCustomerID(String CRMCustomerID) {
        this.CRMCustomerID = CRMCustomerID;
    }

    public BigDecimal getOriginalDiscountAmount() {
        return originalDiscountAmount;
    }

    public void setOriginalDiscountAmount(BigDecimal originalDiscountAmount) {
        this.originalDiscountAmount = originalDiscountAmount;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    public void setCustomerOrderNumber(String customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getWms() {
        return wms;
    }

    public void setWms(String wms) {
        this.wms = wms;
    }

    public List<EasSaleOrderItem> getEntries() {
        return entries;
    }

    public void setEntries(List<EasSaleOrderItem> entries) {
        this.entries = entries;
    }
}
