package com.yhglobal.gongxiao.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * EAS其他入库订单信息
 *
 * @author: 陈浩
 **/
public class EasOtherInWareOrder implements Serializable {
    private String controlUnitID;
    private String billType;
    private String baseStatus;
    private String creator;//创建人
    private Date createTime;//创建时间
    private Date bizDate;//业务日期
    private String bizType;//业务类型
    private String wms;//wms
    private String storageOrgUnit;//库存组织
    private String adminOrgUnit;//部门
    private String stocker;//库管员
    private String voucher;//凭证
    private double totalQty;//总数量
    private double totalAmount;//总金额
    private boolean fiVouchered;//是否生成凭证
    private double totalStandardCost;//总标准成本
    private double totalActualCost;//总实际成本
    private boolean isReversed;//是否冲销
    private String transactionType;//事务类型
    private boolean isInitBill;//是否是初始化单据
    private Integer month;//月
    private Integer day;//日
    private String costCenterOrgUnit;//成本中心
    private String sourceBillType;//来源单据类型
    private Integer year;//年
    private Integer period;//业务期间
    private String unitSource;//计量单位来源
    private String sourceBillId;//原始单据ID
    private String sourceFunction;//来源功能
    /*--联系人姓名--*/
    private String receiverName;
    /*--电话--*/
    private String receiverTel;
    /*--收货地址--*/
    private String receiverAddress;
    /*--目的地城市--*/
    private String receiverCity;
    private List<EasOtherInWareOrderItem> entries;

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

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getWms() {
        return wms;
    }

    public void setWms(String wms) {
        this.wms = wms;
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

    public String getUnitSource() {
        return unitSource;
    }

    public void setUnitSource(String unitSource) {
        this.unitSource = unitSource;
    }

    public String getSourceBillId() {
        return sourceBillId;
    }

    public void setSourceBillId(String sourceBillId) {
        this.sourceBillId = sourceBillId;
    }

    public String getSourceFunction() {
        return sourceFunction;
    }

    public void setSourceFunction(String sourceFunction) {
        this.sourceFunction = sourceFunction;
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

    public List<EasOtherInWareOrderItem> getEntries() {
        return entries;
    }

    public void setEntries(List<EasOtherInWareOrderItem> entries) {
        this.entries = entries;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getControlUnitID() {
        return controlUnitID;
    }

    public void setControlUnitID(String controlUnitID) {
        this.controlUnitID = controlUnitID;
    }
}
