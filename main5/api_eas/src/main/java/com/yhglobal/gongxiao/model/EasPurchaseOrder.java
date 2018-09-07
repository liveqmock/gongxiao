package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * eas方需要的采购订单
 *
 * @author: 陈浩
 **/
public class EasPurchaseOrder implements Serializable{
    private String purchaseOrgUnit;	//采购组织 1
    private String purchaseGroup;	//采购组
    private String purchasePerson;	//采购员
    private String supplier;	//供应商 1
    private String supplierAddress;	//供应商地址
    private String supplierOrderNumber;	//供应商订单号
    private String saleOrgUnit;//	销售组织
    private String saleOrder;//	销售订单号
    private Boolean isDirectSend;//	直运
    private String paymentType;//	付款方式 1
    private String settlementType;//	结算方式
    private String cashDiscount;//	现金折扣
    private String currency;//	币别
    private BigDecimal exchangeRate;//	汇率
    private BigDecimal prepaymentRate;//	预付款比率
    private BigDecimal prepayment;//	预付款
    private BigDecimal prepaid;//	已付预付款
    private Date prepaymentDate;//	预付日期
    private Boolean supplierConFirm;//	供应商确认
    private BigDecimal invoicedAmount;//	累计开票金额
    private BigDecimal paidAmount;//	累计付款金额
    private Boolean isInnerSale;//	内部采购
    private String adminOrgUnit;//	所属部门
    private BigDecimal totalAmount;//	金额
    private BigDecimal totalTax;//	税额
    private BigDecimal totalTaxAmount;//	价税合计
    private BigDecimal unPrepaidAmount;//	未付预付款
    private Boolean isSysBill;//	是否系统单据
    private String convertMode;//	折算方式
    private BigDecimal localTotalAmount;//	金额本位币合计
    private BigDecimal localTotalTaxAmount;//	价税合计本位币
    private Boolean isCentralBalance;//	是否集中结算
    private String storageOrgUnit;//	库存组织
    private String companyOrgUnit;//	财务组织
    private Boolean isInTax;//	是否含税
    private Boolean isQuicken;//	加急
    private String alterPerson;//	变更人
    private Integer version;//	版本
    private String oldID;//	原ID
    private String oldStatus;//	旧的状态值
    private Date alterDate;//	变更时间
    private Boolean isPriceInTax;//	是否价外税
    private String paymentCondition;//	付款条件
    private String warehouse;	//仓库
    private Boolean isApprovedMaterial;//	按照货源清单过滤物料
    private Boolean isMatched;//	已匹配预付
    private Date auditTime;//	审核时间
    private String baseStatus;//	单据状态
    private String bizType;//	业务类型
    private String sourceBillType;//	来源单据类型
    private String billTypeID;//	单据类型
    private Integer year;//	业务年度
    private Integer period;//	业务期间
    private String modiFier;//	修改人
    private Date modiFicationTime;//	修改时间
    private String number;//	单据编号
    private Date bizDate;//	业务日期
    private String handler;//	经手人
    private String description;//	参考信息
    private Boolean hasEFFected;//	是否曾经生效
    private String auditor;//	审核人
    private String sourceBill;//	原始单据ID
    private String sourceFunction;//	来源功能
    private String creator;//	创建者
    private Date createTime;//	创建时间
    private String lastUpdateUser;//	最后修改者
    private Date lastUpdateTime;//	最后修改时间
    private String controlUnit;//	控制单元
    private String ID;//	ID
    /**分录**/
    public List<EasPurchaseOrderItem> entries;

    public String getPurchaseOrgUnit() {
        return purchaseOrgUnit;
    }

    public void setPurchaseOrgUnit(String purchaseOrgUnit) {
        this.purchaseOrgUnit = purchaseOrgUnit;
    }

    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    public String getPurchasePerson() {
        return purchasePerson;
    }

    public void setPurchasePerson(String purchasePerson) {
        this.purchasePerson = purchasePerson;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierOrderNumber() {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(String supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public String getSaleOrgUnit() {
        return saleOrgUnit;
    }

    public void setSaleOrgUnit(String saleOrgUnit) {
        this.saleOrgUnit = saleOrgUnit;
    }

    public String getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(String saleOrder) {
        this.saleOrder = saleOrder;
    }

    public Boolean getDirectSend() {
        return isDirectSend;
    }

    public void setDirectSend(Boolean directSend) {
        isDirectSend = directSend;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getCashDiscount() {
        return cashDiscount;
    }

    public void setCashDiscount(String cashDiscount) {
        this.cashDiscount = cashDiscount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getPrepaymentRate() {
        return prepaymentRate;
    }

    public void setPrepaymentRate(BigDecimal prepaymentRate) {
        this.prepaymentRate = prepaymentRate;
    }

    public BigDecimal getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(BigDecimal prepayment) {
        this.prepayment = prepayment;
    }

    public BigDecimal getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(BigDecimal prepaid) {
        this.prepaid = prepaid;
    }

    public Date getPrepaymentDate() {
        return prepaymentDate;
    }

    public void setPrepaymentDate(Date prepaymentDate) {
        this.prepaymentDate = prepaymentDate;
    }

    public Boolean getSupplierConFirm() {
        return supplierConFirm;
    }

    public void setSupplierConFirm(Boolean supplierConFirm) {
        this.supplierConFirm = supplierConFirm;
    }

    public BigDecimal getInvoicedAmount() {
        return invoicedAmount;
    }

    public void setInvoicedAmount(BigDecimal invoicedAmount) {
        this.invoicedAmount = invoicedAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Boolean getInnerSale() {
        return isInnerSale;
    }

    public void setInnerSale(Boolean innerSale) {
        isInnerSale = innerSale;
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

    public BigDecimal getUnPrepaidAmount() {
        return unPrepaidAmount;
    }

    public void setUnPrepaidAmount(BigDecimal unPrepaidAmount) {
        this.unPrepaidAmount = unPrepaidAmount;
    }

    public Boolean getSysBill() {
        return isSysBill;
    }

    public void setSysBill(Boolean sysBill) {
        isSysBill = sysBill;
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

    public Boolean getCentralBalance() {
        return isCentralBalance;
    }

    public void setCentralBalance(Boolean centralBalance) {
        isCentralBalance = centralBalance;
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

    public Boolean getInTax() {
        return isInTax;
    }

    public void setInTax(Boolean inTax) {
        isInTax = inTax;
    }

    public Boolean getQuicken() {
        return isQuicken;
    }

    public void setQuicken(Boolean quicken) {
        isQuicken = quicken;
    }

    public String getAlterPerson() {
        return alterPerson;
    }

    public void setAlterPerson(String alterPerson) {
        this.alterPerson = alterPerson;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Date getAlterDate() {
        return alterDate;
    }

    public void setAlterDate(Date alterDate) {
        this.alterDate = alterDate;
    }

    public Boolean getPriceInTax() {
        return isPriceInTax;
    }

    public void setPriceInTax(Boolean priceInTax) {
        isPriceInTax = priceInTax;
    }

    public String getPaymentCondition() {
        return paymentCondition;
    }

    public void setPaymentCondition(String paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Boolean getApprovedMaterial() {
        return isApprovedMaterial;
    }

    public void setApprovedMaterial(Boolean approvedMaterial) {
        isApprovedMaterial = approvedMaterial;
    }

    public Boolean getMatched() {
        return isMatched;
    }

    public void setMatched(Boolean matched) {
        isMatched = matched;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getSourceBillType() {
        return sourceBillType;
    }

    public void setSourceBillType(String sourceBillType) {
        this.sourceBillType = sourceBillType;
    }

    public String getBillTypeID() {
        return billTypeID;
    }

    public void setBillTypeID(String billTypeID) {
        this.billTypeID = billTypeID;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getModiFier() {
        return modiFier;
    }

    public void setModiFier(String modiFier) {
        this.modiFier = modiFier;
    }

    public Date getModiFicationTime() {
        return modiFicationTime;
    }

    public void setModiFicationTime(Date modiFicationTime) {
        this.modiFicationTime = modiFicationTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasEFFected() {
        return hasEFFected;
    }

    public void setHasEFFected(Boolean hasEFFected) {
        this.hasEFFected = hasEFFected;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getSourceBill() {
        return sourceBill;
    }

    public void setSourceBill(String sourceBill) {
        this.sourceBill = sourceBill;
    }

    public String getSourceFunction() {
        return sourceFunction;
    }

    public void setSourceFunction(String sourceFunction) {
        this.sourceFunction = sourceFunction;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getControlUnit() {
        return controlUnit;
    }

    public void setControlUnit(String controlUnit) {
        this.controlUnit = controlUnit;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<EasPurchaseOrderItem> getEntries() {
        return entries;
    }

    public void setEntries(List<EasPurchaseOrderItem> entries) {
        this.entries = entries;
    }
}
