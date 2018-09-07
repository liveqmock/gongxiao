package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EAS采购入库明细
 *
 * @author: 陈浩
 **/
public class EasPurchaseInboundOrderItem implements Serializable{
    private String parentID;//采购入库单头
    private BigDecimal writtenOffQty;//已核销数量
    private BigDecimal writtenOffAmount;//已核销金额
    private BigDecimal unWriteOffQty;//未核销数量
    private BigDecimal unWriteOffAmount;//未核销金额
    private BigDecimal receiveAmount;//累计收款金额
    private String purOrderNumber;//核心单据号
    private Integer purOrderEntrySeq;//核心单据行行号
    private BigDecimal taxRate;//税率
    private BigDecimal tax;//税额
    private BigDecimal localTax;//本位币税额
    private BigDecimal localPrice;//本位币单价
    private BigDecimal localAmount;//本位币金额
    private BigDecimal drewQty;//已开票数量
    private BigDecimal imputedCost;//应计费用
    private BigDecimal writtenOffBaseQty;//已核销基本数量
    private BigDecimal unWriteOffBaseQty;//未核销基本数量
    private String purOrderID;//核心单据ID
    private String purOrderEntryID;//核心单据行ID
    private String coreBillTypeID;//核心单据类型
    private BigDecimal unReturnedBaseQty;//可退货基本数量
    private BigDecimal orderPrice;//订单单价
    private BigDecimal taxPrice;//含税单价
    private BigDecimal actualPrice;//实际单价
    private String purchaseOrgUnit;//采购组织
    private String purchaseGroup;//采购组
    private String purchasePerson;//采购员
    private BigDecimal drewBaseQty;//已开票基本数量
    private BigDecimal totalMoveQty;//累计调拨数量
    private Boolean isRequestToReceived;//申请组织是否等于收货组织
    private Boolean isFullWriteOff;//是否完全核销
    private BigDecimal canDirectReqQty;//可直拨数量
    private BigDecimal canDirectReqBaseQty;//可直拨基本数量
    private String balanceSupplier;//结算供应商
    private Boolean isCenterBalance;//集中结算
    private Boolean isBetweenCompanySend;//跨公司收货
    private Boolean hasSameCou;//申请公司是否等于收货公司
    private String receiveStorageOrgUnit;//收货库存组织
    private BigDecimal purchaseCost;//采购成本
    private BigDecimal purchaseFee;//采购费用
    private BigDecimal unitPurchaseCost;//单位采购成本
    private BigDecimal materialCost;//材料成本
    private BigDecimal unitMaterialCost;//单位材料成本
    private BigDecimal sCWrittenOffQty;//委外已核销数量
    private BigDecimal sCWrittenOffBaseQty;//委外已核销基本数量
    private BigDecimal sCUnWrittenOffQty;//委外未核销数量
    private BigDecimal sCUnWrittenOffBaseQty;//委外未核销基本数量
    private String dosingType;//投料方式
    private String productTaskNumber;//生产任务单
    private String supplierLotNo;//供应商批次号
    private BigDecimal taxAmount;//价税合计
    private BigDecimal localTaxAmount;//本位币价税合计
    private BigDecimal actualTaxPrice;//实际含税单价
    private BigDecimal discountRate;//折扣率
    private BigDecimal discountAmount;//折扣
    private String contractNumber;//合同号
    private String motherEntryID;//手工拆分后母单ID
    private String customer;//客户
    private String outWarehouse;//转出仓库
    private String outLocation;//转出库位
    private BigDecimal price;//单价
    private BigDecimal amount;//金额
    private Date bizDate;//业务日期
    private String storageOrgUnit;//库存组织
    private String companyOrgUnit;//财务组织
    private String warehouse;//仓库
    private String location;//库位
    private String stocker;//仓管员
    private String lot;//批次
    private BigDecimal qty;//数量
    private BigDecimal assistQty;//辅助数量
    private BigDecimal baseQty;//基本单位数量
    private BigDecimal reverseQty;//冲销数量
    private BigDecimal returnsQty;//退货数量
    private BigDecimal unitStandardCost;//单位标准成本
    private BigDecimal standardCost;//标准成本
    private BigDecimal unitActualCost;//单位实际成本
    private BigDecimal actualCost;//实际成本
    private Boolean isPresent;//是否赠品
    private Date mfg;//生产日期
    private Date exp;//到期日期
    private BigDecimal reverseBaseQty;//冲销基本数量
    private BigDecimal returnBaseQty;//退货基本数量
    private String project;//项目号
    private String trackNumber;//跟踪号
    private String material;//物料
    private String assistProperty;//辅助属性
    private String unit;//计量单位
    private String sourceBillID;//源单据Id
    private String sourceBillNumber;//来源单据编号
    private String sourceBillEntryID;//来源单据分录的Id
    private Integer sourceBillEntrySeq;//来源单据分录序号
    private BigDecimal assCoefficient;//辅助计量单位换算系数
    private String baseStatus;//基本状态
    private BigDecimal associateQty;//未关联数量
    private String sourceBillType;//来源单据类型
    private String baseUnit;//基本计量单位
    private String assistUnit;//辅助计量单位
    private String remark;//备注
    private String reasonCode;//原因代码
    private Integer seq;//单据分录序列号
    private String iD;//ID
    private String id;
    private String InvUpdateType;//更新方式

    public String getInvUpdateType() {
        return InvUpdateType;
    }

    public void setInvUpdateType(String invUpdateType) {
        InvUpdateType = invUpdateType;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String wms;
    private int isCompleted;

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public BigDecimal getWrittenOffQty() {
        return writtenOffQty;
    }

    public void setWrittenOffQty(BigDecimal writtenOffQty) {
        this.writtenOffQty = writtenOffQty;
    }

    public BigDecimal getWrittenOffAmount() {
        return writtenOffAmount;
    }

    public void setWrittenOffAmount(BigDecimal writtenOffAmount) {
        this.writtenOffAmount = writtenOffAmount;
    }

    public BigDecimal getUnWriteOffQty() {
        return unWriteOffQty;
    }

    public void setUnWriteOffQty(BigDecimal unWriteOffQty) {
        this.unWriteOffQty = unWriteOffQty;
    }

    public BigDecimal getUnWriteOffAmount() {
        return unWriteOffAmount;
    }

    public void setUnWriteOffAmount(BigDecimal unWriteOffAmount) {
        this.unWriteOffAmount = unWriteOffAmount;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getPurOrderNumber() {
        return purOrderNumber;
    }

    public void setPurOrderNumber(String purOrderNumber) {
        this.purOrderNumber = purOrderNumber;
    }

    public Integer getPurOrderEntrySeq() {
        return purOrderEntrySeq;
    }

    public void setPurOrderEntrySeq(Integer purOrderEntrySeq) {
        this.purOrderEntrySeq = purOrderEntrySeq;
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

    public BigDecimal getLocalTax() {
        return localTax;
    }

    public void setLocalTax(BigDecimal localTax) {
        this.localTax = localTax;
    }

    public BigDecimal getLocalPrice() {
        return localPrice;
    }

    public void setLocalPrice(BigDecimal localPrice) {
        this.localPrice = localPrice;
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }

    public BigDecimal getDrewQty() {
        return drewQty;
    }

    public void setDrewQty(BigDecimal drewQty) {
        this.drewQty = drewQty;
    }

    public BigDecimal getImputedCost() {
        return imputedCost;
    }

    public void setImputedCost(BigDecimal imputedCost) {
        this.imputedCost = imputedCost;
    }

    public BigDecimal getWrittenOffBaseQty() {
        return writtenOffBaseQty;
    }

    public void setWrittenOffBaseQty(BigDecimal writtenOffBaseQty) {
        this.writtenOffBaseQty = writtenOffBaseQty;
    }

    public BigDecimal getUnWriteOffBaseQty() {
        return unWriteOffBaseQty;
    }

    public void setUnWriteOffBaseQty(BigDecimal unWriteOffBaseQty) {
        this.unWriteOffBaseQty = unWriteOffBaseQty;
    }

    public String getPurOrderID() {
        return purOrderID;
    }

    public void setPurOrderID(String purOrderID) {
        this.purOrderID = purOrderID;
    }

    public String getPurOrderEntryID() {
        return purOrderEntryID;
    }

    public void setPurOrderEntryID(String purOrderEntryID) {
        this.purOrderEntryID = purOrderEntryID;
    }

    public String getCoreBillTypeID() {
        return coreBillTypeID;
    }

    public void setCoreBillTypeID(String coreBillTypeID) {
        this.coreBillTypeID = coreBillTypeID;
    }

    public BigDecimal getUnReturnedBaseQty() {
        return unReturnedBaseQty;
    }

    public void setUnReturnedBaseQty(BigDecimal unReturnedBaseQty) {
        this.unReturnedBaseQty = unReturnedBaseQty;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
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

    public BigDecimal getDrewBaseQty() {
        return drewBaseQty;
    }

    public void setDrewBaseQty(BigDecimal drewBaseQty) {
        this.drewBaseQty = drewBaseQty;
    }

    public BigDecimal getTotalMoveQty() {
        return totalMoveQty;
    }

    public void setTotalMoveQty(BigDecimal totalMoveQty) {
        this.totalMoveQty = totalMoveQty;
    }

    public Boolean getRequestToReceived() {
        return isRequestToReceived;
    }

    public void setRequestToReceived(Boolean requestToReceived) {
        isRequestToReceived = requestToReceived;
    }

    public Boolean getFullWriteOff() {
        return isFullWriteOff;
    }

    public void setFullWriteOff(Boolean fullWriteOff) {
        isFullWriteOff = fullWriteOff;
    }

    public BigDecimal getCanDirectReqQty() {
        return canDirectReqQty;
    }

    public void setCanDirectReqQty(BigDecimal canDirectReqQty) {
        this.canDirectReqQty = canDirectReqQty;
    }

    public BigDecimal getCanDirectReqBaseQty() {
        return canDirectReqBaseQty;
    }

    public void setCanDirectReqBaseQty(BigDecimal canDirectReqBaseQty) {
        this.canDirectReqBaseQty = canDirectReqBaseQty;
    }

    public String getBalanceSupplier() {
        return balanceSupplier;
    }

    public void setBalanceSupplier(String balanceSupplier) {
        this.balanceSupplier = balanceSupplier;
    }

    public Boolean getCenterBalance() {
        return isCenterBalance;
    }

    public void setCenterBalance(Boolean centerBalance) {
        isCenterBalance = centerBalance;
    }

    public Boolean getBetweenCompanySend() {
        return isBetweenCompanySend;
    }

    public void setBetweenCompanySend(Boolean betweenCompanySend) {
        isBetweenCompanySend = betweenCompanySend;
    }

    public Boolean getHasSameCou() {
        return hasSameCou;
    }

    public void setHasSameCou(Boolean hasSameCou) {
        this.hasSameCou = hasSameCou;
    }

    public String getReceiveStorageOrgUnit() {
        return receiveStorageOrgUnit;
    }

    public void setReceiveStorageOrgUnit(String receiveStorageOrgUnit) {
        this.receiveStorageOrgUnit = receiveStorageOrgUnit;
    }

    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(BigDecimal purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public BigDecimal getPurchaseFee() {
        return purchaseFee;
    }

    public void setPurchaseFee(BigDecimal purchaseFee) {
        this.purchaseFee = purchaseFee;
    }

    public BigDecimal getUnitPurchaseCost() {
        return unitPurchaseCost;
    }

    public void setUnitPurchaseCost(BigDecimal unitPurchaseCost) {
        this.unitPurchaseCost = unitPurchaseCost;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getUnitMaterialCost() {
        return unitMaterialCost;
    }

    public void setUnitMaterialCost(BigDecimal unitMaterialCost) {
        this.unitMaterialCost = unitMaterialCost;
    }

    public BigDecimal getsCWrittenOffQty() {
        return sCWrittenOffQty;
    }

    public void setsCWrittenOffQty(BigDecimal sCWrittenOffQty) {
        this.sCWrittenOffQty = sCWrittenOffQty;
    }

    public BigDecimal getsCWrittenOffBaseQty() {
        return sCWrittenOffBaseQty;
    }

    public void setsCWrittenOffBaseQty(BigDecimal sCWrittenOffBaseQty) {
        this.sCWrittenOffBaseQty = sCWrittenOffBaseQty;
    }

    public BigDecimal getsCUnWrittenOffQty() {
        return sCUnWrittenOffQty;
    }

    public void setsCUnWrittenOffQty(BigDecimal sCUnWrittenOffQty) {
        this.sCUnWrittenOffQty = sCUnWrittenOffQty;
    }

    public BigDecimal getsCUnWrittenOffBaseQty() {
        return sCUnWrittenOffBaseQty;
    }

    public void setsCUnWrittenOffBaseQty(BigDecimal sCUnWrittenOffBaseQty) {
        this.sCUnWrittenOffBaseQty = sCUnWrittenOffBaseQty;
    }

    public String getDosingType() {
        return dosingType;
    }

    public void setDosingType(String dosingType) {
        this.dosingType = dosingType;
    }

    public String getProductTaskNumber() {
        return productTaskNumber;
    }

    public void setProductTaskNumber(String productTaskNumber) {
        this.productTaskNumber = productTaskNumber;
    }

    public String getSupplierLotNo() {
        return supplierLotNo;
    }

    public void setSupplierLotNo(String supplierLotNo) {
        this.supplierLotNo = supplierLotNo;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getLocalTaxAmount() {
        return localTaxAmount;
    }

    public void setLocalTaxAmount(BigDecimal localTaxAmount) {
        this.localTaxAmount = localTaxAmount;
    }

    public BigDecimal getActualTaxPrice() {
        return actualTaxPrice;
    }

    public void setActualTaxPrice(BigDecimal actualTaxPrice) {
        this.actualTaxPrice = actualTaxPrice;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getMotherEntryID() {
        return motherEntryID;
    }

    public void setMotherEntryID(String motherEntryID) {
        this.motherEntryID = motherEntryID;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOutWarehouse() {
        return outWarehouse;
    }

    public void setOutWarehouse(String outWarehouse) {
        this.outWarehouse = outWarehouse;
    }

    public String getOutLocation() {
        return outLocation;
    }

    public void setOutLocation(String outLocation) {
        this.outLocation = outLocation;
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

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getAssistQty() {
        return assistQty;
    }

    public void setAssistQty(BigDecimal assistQty) {
        this.assistQty = assistQty;
    }

    public BigDecimal getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(BigDecimal baseQty) {
        this.baseQty = baseQty;
    }

    public BigDecimal getReverseQty() {
        return reverseQty;
    }

    public void setReverseQty(BigDecimal reverseQty) {
        this.reverseQty = reverseQty;
    }

    public BigDecimal getReturnsQty() {
        return returnsQty;
    }

    public void setReturnsQty(BigDecimal returnsQty) {
        this.returnsQty = returnsQty;
    }

    public BigDecimal getUnitStandardCost() {
        return unitStandardCost;
    }

    public void setUnitStandardCost(BigDecimal unitStandardCost) {
        this.unitStandardCost = unitStandardCost;
    }

    public BigDecimal getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(BigDecimal standardCost) {
        this.standardCost = standardCost;
    }

    public BigDecimal getUnitActualCost() {
        return unitActualCost;
    }

    public void setUnitActualCost(BigDecimal unitActualCost) {
        this.unitActualCost = unitActualCost;
    }

    public BigDecimal getActualCost() {
        return actualCost;
    }

    public void setActualCost(BigDecimal actualCost) {
        this.actualCost = actualCost;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
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

    public BigDecimal getReverseBaseQty() {
        return reverseBaseQty;
    }

    public void setReverseBaseQty(BigDecimal reverseBaseQty) {
        this.reverseBaseQty = reverseBaseQty;
    }

    public BigDecimal getReturnBaseQty() {
        return returnBaseQty;
    }

    public void setReturnBaseQty(BigDecimal returnBaseQty) {
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

    public String getSourceBillID() {
        return sourceBillID;
    }

    public void setSourceBillID(String sourceBillID) {
        this.sourceBillID = sourceBillID;
    }

    public String getSourceBillNumber() {
        return sourceBillNumber;
    }

    public void setSourceBillNumber(String sourceBillNumber) {
        this.sourceBillNumber = sourceBillNumber;
    }

    public String getSourceBillEntryID() {
        return sourceBillEntryID;
    }

    public void setSourceBillEntryID(String sourceBillEntryID) {
        this.sourceBillEntryID = sourceBillEntryID;
    }

    public Integer getSourceBillEntrySeq() {
        return sourceBillEntrySeq;
    }

    public void setSourceBillEntrySeq(Integer sourceBillEntrySeq) {
        this.sourceBillEntrySeq = sourceBillEntrySeq;
    }

    public BigDecimal getAssCoefficient() {
        return assCoefficient;
    }

    public void setAssCoefficient(BigDecimal assCoefficient) {
        this.assCoefficient = assCoefficient;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public BigDecimal getAssociateQty() {
        return associateQty;
    }

    public void setAssociateQty(BigDecimal associateQty) {
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getWms() {
        return wms;
    }

    public void setWms(String wms) {
        this.wms = wms;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
}
