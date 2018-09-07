package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Eas采购入库订单
 *
 * @author: 陈浩
 **/
public class EasPurchaseInboundOrder implements Serializable {
    private String supplier;//供应商
    private String currency;//币别
    private BigDecimal receiptAmount;//累计收款金额
    private BigDecimal exchangeRate;//汇率
    private String paymentType;//付款方式
    private String convertMode;//折算方式
    private Boolean isSysBill;//是否系统单据
    private Boolean isCentralBalance;//是否集中结算
    private BigDecimal totalLocalAmount;//总本位币金额
    private String purchaseType;//采购类别
    private Boolean isInTax;//是否含税
    private Boolean isPriceInTax;//是否价外税
    private String dischargeType;//冲回方式
    private Boolean isGenBizAP;//是否生成业务应付
    private Integer splitNumCount;//拆分单据次数
    private String billRelationOption;//整单关联算法
    private String priceSource;//价格来源
    private String storageOrgUnit;//库存组织
    private String adminOrgUnit;//部门
    private String stocker;//库管员
    private String voucher;//凭证
    private BigDecimal totalQty;//总数量
    private BigDecimal totalAmount;//总金额
    private Boolean fiVouchered;//是否生成凭证
    private BigDecimal totalStandardCost;//总标准成本
    private BigDecimal totalActualCost;//总实际成本
    private Boolean isReversed;//是否冲销
    private String transactionType;//事务类型
    private Boolean isInitBill;//是否是初始化单
    private Integer month;//月
    private Integer day;//日
    private String costCenterOrgUnit;//成本中心
    private Date auditTime;//审核时间
    private String baseStatus;//单据状态
    private String bizType;//业务类型
    private String sourceBillType;//来源单据类型
    private String billTypeID;//单据类型
    private Integer year;//业务年度
    private Integer period;//业务期间
    private String modifier;//修改人
    private Date modificationTime;//修改时间
    private String number;//单据编号
    private Date bizDate;//业务日期
    private String handlerID;//经手人
    private String description;//参考信息
    private Boolean hasEffected;//是否曾经生效
    private String auditorID;//审核人
    private String sourceBillID;//原始单据ID
    private String sourceFunction;//来源功能
    private Date createTime;//创建时间
    private String lastUpdateUser;//最后修改者
    private Date lastUpdateTime;//最后修改时间
    private String controlUnit;//控制单元
    private String iD;//ID
    private List<EasPurchaseInboundOrderItem> entries;

    private String creator;
    private String id;
    private String wms;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(BigDecimal receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getConvertMode() {
        return convertMode;
    }

    public void setConvertMode(String convertMode) {
        this.convertMode = convertMode;
    }

    public Boolean getSysBill() {
        return isSysBill;
    }

    public void setSysBill(Boolean sysBill) {
        isSysBill = sysBill;
    }

    public Boolean getCentralBalance() {
        return isCentralBalance;
    }

    public void setCentralBalance(Boolean centralBalance) {
        isCentralBalance = centralBalance;
    }

    public BigDecimal getTotalLocalAmount() {
        return totalLocalAmount;
    }

    public void setTotalLocalAmount(BigDecimal totalLocalAmount) {
        this.totalLocalAmount = totalLocalAmount;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Boolean getInTax() {
        return isInTax;
    }

    public void setInTax(Boolean inTax) {
        isInTax = inTax;
    }

    public Boolean getPriceInTax() {
        return isPriceInTax;
    }

    public void setPriceInTax(Boolean priceInTax) {
        isPriceInTax = priceInTax;
    }

    public String getDischargeType() {
        return dischargeType;
    }

    public void setDischargeType(String dischargeType) {
        this.dischargeType = dischargeType;
    }

    public Boolean getGenBizAP() {
        return isGenBizAP;
    }

    public void setGenBizAP(Boolean genBizAP) {
        isGenBizAP = genBizAP;
    }

    public Integer getSplitNumCount() {
        return splitNumCount;
    }

    public void setSplitNumCount(Integer splitNumCount) {
        this.splitNumCount = splitNumCount;
    }

    public String getBillRelationOption() {
        return billRelationOption;
    }

    public void setBillRelationOption(String billRelationOption) {
        this.billRelationOption = billRelationOption;
    }

    public String getPriceSource() {
        return priceSource;
    }

    public void setPriceSource(String priceSource) {
        this.priceSource = priceSource;
    }

    public String getStorageOrgUnit() {
        return storageOrgUnit;
    }

    public void setStorageOrgUnit(String storageOrgUnit) {
        this.storageOrgUnit = storageOrgUnit;
    }

    public String getAdminOrgUnit() {
        return adminOrgUnit;
    }

    public void setAdminOrgUnit(String adminOrgUnit) {
        this.adminOrgUnit = adminOrgUnit;
    }

    public String getStocker() {
        return stocker;
    }

    public void setStocker(String stocker) {
        this.stocker = stocker;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getFiVouchered() {
        return fiVouchered;
    }

    public void setFiVouchered(Boolean fiVouchered) {
        this.fiVouchered = fiVouchered;
    }

    public BigDecimal getTotalStandardCost() {
        return totalStandardCost;
    }

    public void setTotalStandardCost(BigDecimal totalStandardCost) {
        this.totalStandardCost = totalStandardCost;
    }

    public BigDecimal getTotalActualCost() {
        return totalActualCost;
    }

    public void setTotalActualCost(BigDecimal totalActualCost) {
        this.totalActualCost = totalActualCost;
    }

    public Boolean getReversed() {
        return isReversed;
    }

    public void setReversed(Boolean reversed) {
        isReversed = reversed;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Boolean getInitBill() {
        return isInitBill;
    }

    public void setInitBill(Boolean initBill) {
        isInitBill = initBill;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getCostCenterOrgUnit() {
        return costCenterOrgUnit;
    }

    public void setCostCenterOrgUnit(String costCenterOrgUnit) {
        this.costCenterOrgUnit = costCenterOrgUnit;
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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
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

    public String getHandlerID() {
        return handlerID;
    }

    public void setHandlerID(String handlerID) {
        this.handlerID = handlerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasEffected() {
        return hasEffected;
    }

    public void setHasEffected(Boolean hasEffected) {
        this.hasEffected = hasEffected;
    }

    public String getAuditorID() {
        return auditorID;
    }

    public void setAuditorID(String auditorID) {
        this.auditorID = auditorID;
    }

    public String getSourceBillID() {
        return sourceBillID;
    }

    public void setSourceBillID(String sourceBillID) {
        this.sourceBillID = sourceBillID;
    }

    public String getSourceFunction() {
        return sourceFunction;
    }

    public void setSourceFunction(String sourceFunction) {
        this.sourceFunction = sourceFunction;
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

    public List<EasPurchaseInboundOrderItem> getEntries() {
        return entries;
    }

    public void setEntries(List<EasPurchaseInboundOrderItem> entries) {
        this.entries = entries;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getWms() {
        return wms;
    }

    public void setWms(String wms) {
        this.wms = wms;
    }
}
