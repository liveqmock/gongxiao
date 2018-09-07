package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * EAS其他出库货品信息
 *
 * @author: 陈浩
 **/
public class EasOtherOutWareOrderItem implements Serializable {
    private String storeStatusId;
    private String storeTypeID;
    /*--供应商--*/
    private String supplier;
    /*--客户--*/
    private String customer;
    /*--成本对象--*/
    private String costObject;
    /*--基本单位实际成本--*/
    private double baseUnitActualcost;
    /*--单价--*/
    private double price;
    /*--金额--*/
    private double amount;
    /*--业务日期--*/
    private Date bizDate;
    /*--库存组织--*/
    private String storageOrgUnit;
    /*--财务组织--*/
    private String companyOrgUnit;
    /*--仓库--*/
    private String warehouse;
    /*--库位--*/
    private String location;
    /*--仓管员--*/
    private String stocker;
    /*--批次--*/
    private String lot;
    /*--数量--*/
    private double qty;
    /*--辅助数量--*/
    private double assistQty;
    /*--基本数量--*/
    private double baseQty;
    /*--冲销数量--*/
    private double reverseQty;
    /*--退货数量--*/
    private double returnsQty;
    /*--单位标准成本--*/
    private double unitStandardCost;
    /*--标准成本--*/
    private double standardCost;
    /*--单位实际成本--*/
    private double unitActualCost;
    /*--实际成本--*/
    private double actualCost;
    /*--是否赠品--*/
    private boolean isPresent;
    /*--生成时间--*/
    private String mfg;
    /*--到期时间--*/
    private String exp;
    /*--冲销基本数量--*/
    private double reverseBaseQty;
    /*--退货基本数量--*/
    private double returnBaseQty;
    /*--项目号--*/
    private String project;
    /*--跟踪号--*/
    private String trackNumber;
    /*--更新类型--*/
    private String invUpdateType;
    /*--入库会计科目--*/
    private String accountViewIn;
    /*--出库科目--*/
    private String accountViewOut;
    /*--物料--*/
    private String material;
    /*--辅助属性--*/
    private String assistProperty;
    /*--计量单位--*/
    private String unit;
    /*--来源单据编码--*/
    private String sourceBillNumber;
    /*--来源单据id--*/
    private String sourceBillID;
    /*--来源单据分录id--*/
    private String sourceBillEntryId;
    /*--来源单据分录序列号--*/
    private int sourceBillEntrySeq;
    /*---辅助计量单位换算系数-*/
    private double assCoefficient;
    /*--基本状态--*/
    private String baseStatus;
    /*--未关联数量--*/
    private double associateQty;
    /*--来源单据类型--*/
    private String sourceBillType;
    /*--基本计量单位--*/
    private String baseUnit;
    /*--辅助计量单位--*/
    private String assistUnit;
    /*--备注--*/
    private String remark;
    /*--原因代码--*/
    private String reasonCode;
    /*----*/
    private int seq;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCostObject() {
        return costObject;
    }

    public void setCostObject(String costObject) {
        this.costObject = costObject;
    }

    public double getBaseUnitActualcost() {
        return baseUnitActualcost;
    }

    public void setBaseUnitActualcost(double baseUnitActualcost) {
        this.baseUnitActualcost = baseUnitActualcost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStocker() {
        return stocker;
    }

    public void setStocker(String stocker) {
        this.stocker = stocker;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
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

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public String getMfg() {
        return mfg;
    }

    public void setMfg(String mfg) {
        this.mfg = mfg;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public double getReverseBaseQty() {
        return reverseBaseQty;
    }

    public void setReverseBaseQty(double reverseBaseQty) {
        this.reverseBaseQty = reverseBaseQty;
    }

    public double getReturnBaseQty() {
        return returnBaseQty;
    }

    public void setReturnBaseQty(double returnBaseQty) {
        this.returnBaseQty = returnBaseQty;
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAssistProperty() {
        return assistProperty;
    }

    public void setAssistProperty(String assistProperty) {
        this.assistProperty = assistProperty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSourceBillNumber() {
        return sourceBillNumber;
    }

    public void setSourceBillNumber(String sourceBillNumber) {
        this.sourceBillNumber = sourceBillNumber;
    }

    public String getSourceBillID() {
        return sourceBillID;
    }

    public void setSourceBillID(String sourceBillID) {
        this.sourceBillID = sourceBillID;
    }

    public String getSourceBillEntryId() {
        return sourceBillEntryId;
    }

    public void setSourceBillEntryId(String sourceBillEntryId) {
        this.sourceBillEntryId = sourceBillEntryId;
    }

    public int getSourceBillEntrySeq() {
        return sourceBillEntrySeq;
    }

    public void setSourceBillEntrySeq(int sourceBillEntrySeq) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getStoreTypeID() {
        return storeTypeID;
    }

    public void setStoreTypeID(String storeTypeID) {
        this.storeTypeID = storeTypeID;
    }

    public String getStoreStatusId() {
        return storeStatusId;
    }

    public void setStoreStatusId(String storeStatusId) {
        this.storeStatusId = storeStatusId;
    }
}
