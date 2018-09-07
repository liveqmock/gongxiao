package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * EAS其他出库订单信息
 *
 * @author: 陈浩
 **/
public class EasOtherOutWareOrder implements Serializable {
    private String controlUnitID;

    /*--库存组织--*/
    private String storageOrgUnit;
    /*--部门--*/
    private String adminOrgUnit;
    /*--仓管员--*/
    private String stocker;
    /*--凭证--*/
    private String voucher;
    /*--总数量--*/
    private double totalQty;
    /*--总金额--*/
    private double totalAmount;
    /*--是否生成凭证--*/
    private boolean fiVouchered;
    /*--总标准成本--*/
    private double totalStandardCost;
    /*--总实际成本--*/
    private double totalActualCost;
    /*--是否冲销--*/
    private boolean isReversed;
    /*--事务类型--*/
    private String transactionType;
    /*--是否是初始化单--*/
    private boolean isInitBill;
    /*--月--*/
    private int month;
    /*--日--*/
    private int day;
    /*--成本中心--*/
    private String costCenterOrgUnit;
    /*--审核时间--*/
    private String auditTime;
    /*--单据状态--*/
    private String baseStatus;
    /*--业务类型--*/
    private String bizType;
    /*--来源单据类型--*/
    private String sourceBillType;
    /*--单据类型--*/
    private String billType;
    /*--业务年度--*/
    private int year;
    /*--业务期间--*/
    private int period;
    /*--修改人--*/
    private String modifier;
    /*--修改时间--*/
    private String modificationTime;
    /*--计量单位来源--*/
    private String unitSource;
    /*--单据编码--*/
    private String number;
    /*--业务日期--*/
    private Date bizDate;
    /*--审核人--*/
    private String auditor;
    /*----*/
    private  String creator;//创建人
    /*----*/
    private  Date createTime;//创建时间
    /*----*/
    private  String wms;
    /*--联系人姓名--*/
    private String receiverName;
    /*--电话--*/
    private String receiverTel;
    /*--收货地址--*/
    private String receiverAddress;
    /*--目的地城市--*/
    private String receiverCity;

    /*----*/
    public List<EasOtherOutWareOrderItem> entries;

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

    public double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(double totalQty) {
        this.totalQty = totalQty;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isFiVouchered() {
        return fiVouchered;
    }

    public void setFiVouchered(boolean fiVouchered) {
        this.fiVouchered = fiVouchered;
    }

    public double getTotalStandardCost() {
        return totalStandardCost;
    }

    public void setTotalStandardCost(double totalStandardCost) {
        this.totalStandardCost = totalStandardCost;
    }

    public double getTotalActualCost() {
        return totalActualCost;
    }

    public void setTotalActualCost(double totalActualCost) {
        this.totalActualCost = totalActualCost;
    }

    public boolean isReversed() {
        return isReversed;
    }

    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isInitBill() {
        return isInitBill;
    }

    public void setInitBill(boolean initBill) {
        isInitBill = initBill;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getUnitSource() {
        return unitSource;
    }

    public void setUnitSource(String unitSource) {
        this.unitSource = unitSource;
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

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
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

    public String getWms() {
        return wms;
    }

    public void setWms(String wms) {
        this.wms = wms;
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

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public List<EasOtherOutWareOrderItem> getEntries() {
        return entries;
    }

    public void setEntries(List<EasOtherOutWareOrderItem> entries) {
        this.entries = entries;
    }

    public String getControlUnitID() {
        return controlUnitID;
    }

    public void setControlUnitID(String controlUnitID) {
        this.controlUnitID = controlUnitID;
    }
}
