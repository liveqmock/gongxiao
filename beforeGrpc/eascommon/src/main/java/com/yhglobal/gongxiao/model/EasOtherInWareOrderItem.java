package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * EAS其他入库货品信息
 *
 * @author: 陈浩
 **/
public class EasOtherInWareOrderItem implements Serializable {
    private boolean present;
    private String storageOrgUnitID;
    private String storeStatusID;
    private String storeTypeID;
    private String customer;//客户
    private String warehouse;//仓库
    private String lot;//批次
    private String material;//物料
    private double price;//单价
    private double qty;//数量
    private String remark;//备注
    private String stocker;//库管员
    private String supplier;//供应商
    private String unit;//计量单位
    private String location;//库位
    private String wmsEntryID;//wmsEntryID
    private String costObject;//成本对象
    private String costItem;//成本项目
    private String costObjectSuite;//成本对象组
    private double amount;//金额
    private Date bizDate;//业务日期
    private String companyOrgUnit;//财务组织
    private double assistQty;//辅助数量
    private double baseQty;//基本数量
    private double reverseQty;//冲销数量
    private double returnsQty;//退货数量
    private double unitStandardCost;//单位标准成本
    private double standardCost;//标准成本
    private double unitActualCost;//单位实际成本
    private double actualCost;//实际成本
    private Date mfg;//生产日期
    private Date exp;//到期日期
    private double reverseBaseQty;//冲销基本数量
    private double returnsBaseQty;//退货基本数量
    private String project;//项目号
    private String trackNumber;//跟踪号
    private String invUpdateType;//更新号
    private String accountViewIn;//入库会计科目
    private String accountViewOut;//出库科目
    private String assistProperty;//辅助属性
    private String sourceBillId;//来源单据ID
    private String sourceBillNumber;//来源单据编码
    private String sourceBillEntryId;//来源单据分录ID
    private Integer sourceBillEntrySeq;//来源单据分录序号
    private double assCoefficient;//辅助计量单位换算数
    private String baseStatus;//基本状态
    private double associateQty;//未关联数量
    private String sourceBillType;//来源单据类型
    private String baseUnit;//基本计量单位
    private String assistUnit;//辅助计量单位
    private String reasonCode;//原因代码 
    private String reservationBillObjectId;//预留单据对象id
    private String reservationId;//预留对象ID


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStocker() {
        return stocker;
    }

    public void setStocker(String stocker) {
        this.stocker = stocker;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWmsEntryID() {
        return wmsEntryID;
    }

    public void setWmsEntryID(String wmsEntryID) {
        this.wmsEntryID = wmsEntryID;
    }

    public String getCostObject() {
        return costObject;
    }

    public void setCostObject(String costObject) {
        this.costObject = costObject;
    }

    public String getCostItem() {
        return costItem;
    }

    public void setCostItem(String costItem) {
        this.costItem = costItem;
    }

    public String getCostObjectSuite() {
        return costObjectSuite;
    }

    public void setCostObjectSuite(String costObjectSuite) {
        this.costObjectSuite = costObjectSuite;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getCompanyOrgUnit() {
        return companyOrgUnit;
    }

    public void setCompanyOrgUnit(String companyOrgUnit) {
        this.companyOrgUnit = companyOrgUnit;
    }

    public double getAssistQty() {
        return assistQty;
    }

    public void setAssistQty(double assistQty) {
        this.assistQty = assistQty;
    }

    public double getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(double baseQty) {
        this.baseQty = baseQty;
    }

    public double getReverseQty() {
        return reverseQty;
    }

    public void setReverseQty(double reverseQty) {
        this.reverseQty = reverseQty;
    }

    public double getReturnsQty() {
        return returnsQty;
    }

    public void setReturnsQty(double returnsQty) {
        this.returnsQty = returnsQty;
    }

    public double getUnitStandardCost() {
        return unitStandardCost;
    }

    public void setUnitStandardCost(double unitStandardCost) {
        this.unitStandardCost = unitStandardCost;
    }

    public double getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(double standardCost) {
        this.standardCost = standardCost;
    }

    public double getUnitActualCost() {
        return unitActualCost;
    }

    public void setUnitActualCost(double unitActualCost) {
        this.unitActualCost = unitActualCost;
    }

    public double getActualCost() {
        return actualCost;
    }

    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
    }

    public Date getMfg() {
        return mfg;
    }

    public void setMfg(Date mfg) {
        this.mfg = mfg;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public double getReverseBaseQty() {
        return reverseBaseQty;
    }

    public void setReverseBaseQty(double reverseBaseQty) {
        this.reverseBaseQty = reverseBaseQty;
    }

    public double getReturnsBaseQty() {
        return returnsBaseQty;
    }

    public void setReturnsBaseQty(double returnsBaseQty) {
        this.returnsBaseQty = returnsBaseQty;
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

    public String getInvUpdateType() {
        return invUpdateType;
    }

    public void setInvUpdateType(String invUpdateType) {
        this.invUpdateType = invUpdateType;
    }

    public String getAccountViewIn() {
        return accountViewIn;
    }

    public void setAccountViewIn(String accountViewIn) {
        this.accountViewIn = accountViewIn;
    }

    public String getAccountViewOut() {
        return accountViewOut;
    }

    public void setAccountViewOut(String accountViewOut) {
        this.accountViewOut = accountViewOut;
    }

    public String getAssistProperty() {
        return assistProperty;
    }

    public void setAssistProperty(String assistProperty) {
        this.assistProperty = assistProperty;
    }

    public String getSourceBillId() {
        return sourceBillId;
    }

    public void setSourceBillId(String sourceBillId) {
        this.sourceBillId = sourceBillId;
    }

    public String getSourceBillNumber() {
        return sourceBillNumber;
    }

    public void setSourceBillNumber(String sourceBillNumber) {
        this.sourceBillNumber = sourceBillNumber;
    }

    public String getSourceBillEntryId() {
        return sourceBillEntryId;
    }

    public void setSourceBillEntryId(String sourceBillEntryId) {
        this.sourceBillEntryId = sourceBillEntryId;
    }

    public Integer getSourceBillEntrySeq() {
        return sourceBillEntrySeq;
    }

    public void setSourceBillEntrySeq(Integer sourceBillEntrySeq) {
        this.sourceBillEntrySeq = sourceBillEntrySeq;
    }

    public double getAssCoefficient() {
        return assCoefficient;
    }

    public void setAssCoefficient(double assCoefficient) {
        this.assCoefficient = assCoefficient;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public double getAssociateQty() {
        return associateQty;
    }

    public void setAssociateQty(double associateQty) {
        this.associateQty = associateQty;
    }

    public String getSourceBillType() {
        return sourceBillType;
    }

    public void setSourceBillType(String sourceBillType) {
        this.sourceBillType = sourceBillType;
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

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReservationBillObjectId() {
        return reservationBillObjectId;
    }

    public void setReservationBillObjectId(String reservationBillObjectId) {
        this.reservationBillObjectId = reservationBillObjectId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getStoreTypeID() {
        return storeTypeID;
    }

    public void setStoreTypeID(String storeTypeID) {
        this.storeTypeID = storeTypeID;
    }

    public String getStoreStatusID() {
        return storeStatusID;
    }

    public void setStoreStatusID(String storeStatusID) {
        this.storeStatusID = storeStatusID;
    }

    public String getStorageOrgUnitID() {
        return storageOrgUnitID;
    }

    public void setStorageOrgUnitID(String storageOrgUnitID) {
        this.storageOrgUnitID = storageOrgUnitID;
    }

    public boolean isPresent() {

        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
