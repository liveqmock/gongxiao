package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售出库单头
 *
 * @author: 陈浩
 **/
public class EasSaleOutOrder implements Serializable {
    private String controlUnit;
    private String billType;
    private String baseStatus; //单据状态
    private String transactionType;
    private String storageOrgUnit;
    private Date actBizDate;
    private String paymentType;
    private BigDecimal exchangeRate;
    private String currency;
    private String id;//创建人
    private String creator;//创建人
    private Date createTime;//创建时间
    private BigDecimal taxRate;//税率
    private BigDecimal tax;//税额
    private BigDecimal taxPrice;//含税单价
    private BigDecimal actualPrice;//实际单价
    private String saleOrgUnitID;//销售组织
    private String saleGroupID;//销售组
    private String salePersonID;//销售员
    private BigDecimal salePrice;//单价
    private String discountType;//折扣方式
    private BigDecimal discountAmount;//折扣额
    private BigDecimal discount;//单位折扣率
    private BigDecimal price;//实际含税单价
    private BigDecimal amount;//价税合计
    private BigDecimal nonTaxAmount;//金额
    private Date bizDate;//业务日期
    private String stockerID;//仓管员
    private BigDecimal qty;//数量
    private String remark;//备注
    private String wms;
    private String customer;//客户
    private String adminOrgUnit;//部门
    private BigDecimal totalQty;//总数量
    private boolean isSysBill;//是否系统单据
    private boolean isGenBizAR;//是否生成业务应收
    private boolean isInTax;//是否含税
    private BigDecimal totalLocalAmount;//价税总合计本位币
    private String priceSource;//价格来源
    private boolean isWriteOffVouched;//是否生成核销记录凭证
    private String voucher;//凭证
    private BigDecimal totalAmount;//总金额
    private boolean isInitBill;//是否初始化单据
    private Integer month;//月
    private Integer day;//日
    private String costCenterOrgUnit;//成本中心
    private String auditTime;//审批时间
    private String bizType;//业务类型
    private String sourceBillType;//单据来源类型
    private Integer year;//业务年度
    private Integer period;//业务期间
    public List<EasSaleOutOrderItem> entries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getSaleOrgUnitID() {
        return saleOrgUnitID;
    }

    public void setSaleOrgUnitID(String saleOrgUnitID) {
        this.saleOrgUnitID = saleOrgUnitID;
    }

    public String getSaleGroupID() {
        return saleGroupID;
    }

    public void setSaleGroupID(String saleGroupID) {
        this.saleGroupID = saleGroupID;
    }

    public String getSalePersonID() {
        return salePersonID;
    }

    public void setSalePersonID(String salePersonID) {
        this.salePersonID = salePersonID;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getNonTaxAmount() {
        return nonTaxAmount;
    }

    public void setNonTaxAmount(BigDecimal nonTaxAmount) {
        this.nonTaxAmount = nonTaxAmount;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getStockerID() {
        return stockerID;
    }

    public void setStockerID(String stockerID) {
        this.stockerID = stockerID;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWms() {
        return wms;
    }

    public void setWms(String wms) {
        this.wms = wms;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAdminOrgUnit() {
        return adminOrgUnit;
    }

    public void setAdminOrgUnit(String adminOrgUnit) {
        this.adminOrgUnit = adminOrgUnit;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public boolean isSysBill() {
        return isSysBill;
    }

    public void setSysBill(boolean sysBill) {
        isSysBill = sysBill;
    }

    public boolean isGenBizAR() {
        return isGenBizAR;
    }

    public void setGenBizAR(boolean genBizAR) {
        isGenBizAR = genBizAR;
    }

    public boolean isInTax() {
        return isInTax;
    }

    public void setInTax(boolean inTax) {
        isInTax = inTax;
    }

    public BigDecimal getTotalLocalAmount() {
        return totalLocalAmount;
    }

    public void setTotalLocalAmount(BigDecimal totalLocalAmount) {
        this.totalLocalAmount = totalLocalAmount;
    }

    public String getPriceSource() {
        return priceSource;
    }

    public void setPriceSource(String priceSource) {
        this.priceSource = priceSource;
    }

    public boolean isWriteOffVouched() {
        return isWriteOffVouched;
    }

    public void setWriteOffVouched(boolean writeOffVouched) {
        isWriteOffVouched = writeOffVouched;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isInitBill() {
        return isInitBill;
    }

    public void setInitBill(boolean initBill) {
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

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
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

    public List<EasSaleOutOrderItem> getEntries() {
        return entries;
    }

    public void setEntries(List<EasSaleOutOrderItem> entries) {
        this.entries = entries;
    }



    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }



    public Date getActBizDate() {
        return actBizDate;
    }

    public void setActBizDate(Date actBizDate) {
        this.actBizDate = actBizDate;
    }


    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public String getControlUnit() {
        return controlUnit;
    }

    public void setControlUnit(String controlUnit) {
        this.controlUnit = controlUnit;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStorageOrgUnit() {
        return storageOrgUnit;
    }

    public void setStorageOrgUnit(String storageOrgUnit) {
        this.storageOrgUnit = storageOrgUnit;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

