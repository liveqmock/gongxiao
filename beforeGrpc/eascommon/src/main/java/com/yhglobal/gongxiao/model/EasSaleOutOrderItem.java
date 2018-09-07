package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售出库明细列表
 *
 * @author: 陈浩
 **/
public class EasSaleOutOrderItem implements Serializable{
    private BigDecimal writtenOffQty;//已核销数量
    private BigDecimal writtenOffAmount;//已核销金额
    private BigDecimal unWriteOffQty;//未核销数量
    private BigDecimal unWriteOffAmount;//未核销金额
    private String orderNumber;//客户订单号
    private String saleOrderNumber;//核心单据号
    private Integer saleOrderEntrySeq;//核心单据行行号
    private BigDecimal taxRate;//税率
    private BigDecimal tax;//税额
    private BigDecimal localTax;//本位币税额
    private BigDecimal localPrice;//本位币单价
    private BigDecimal drewQty;//已开票数量
    private BigDecimal writtenOffBaseQty;//已核销基本数量
    private BigDecimal unWriteOffBaseQty;//未核销基本数量
    private BigDecimal drewBaseQty;//已开票基本数量
    private String saleOrder;//核心单据ID
    private String saleOrderEntry;//核心单据行ID
    private String coreBillType;//核心单据类型
    private BigDecimal unReturnedBaseQty;//可退货基本数量
    private boolean isLocked;//是否锁库
    private String inventory;//库存台账ID
    private BigDecimal orderPrice;//订单单价
    private BigDecimal taxPrice;//含税单价
    private BigDecimal actualPrice;//实际单价
    private String saleOrgUnit;//销售组织
    private String saleGroup;//销售组
    private String salePerson;//销售员
    private BigDecimal undeliverQty;//待发可出库数量
    private BigDecimal undeliverBaseQty;//待发可出库基本数量
    private BigDecimal unInQty;//对方未入库数量
    private BigDecimal unInBaseQty;//对方未入库基本数量
    private String balanceCustomer;//应收客户
    private boolean isCenterBalance;//集中结算
    private boolean isBetweenCompanySend;//跨公司发货
    private BigDecimal totalInWarehsQty;//累计入库数量
    private BigDecimal baseUnitActualcost;//基本单位实际成本
    private String orderCustomer;//订货客户
    private String paymentCustomer;//收款客户
    private BigDecimal confirmQty;//确认签收数量
    private BigDecimal confirmBaseQty;//确认签收基本数量
    private BigDecimal associateBaseQty;//签收未关联基本数量
    private Date confirmDate;//确认签收时间
    private String sendAddress;//送货地址
    private String b2cBillType;//协同单据类型
    private String netOrderBill;//协同单据ID
    private String netOrderBillNumber;//协同单据编号
    private String netOrderBillEntry;//协同单据分录ID
    private boolean isSquareBalance;//四方结算
    private String contractNumber;//合同号
    private String supplier;//供应商
    private BigDecimal salePrice;//单价
    private String discountType;//折扣方式
    private BigDecimal discountAmount;//折扣额
    private BigDecimal discount;//单位折扣率
    private BigDecimal price;//实际含税单价
    private BigDecimal amount;//价税合计
    private BigDecimal nonTaxAmount;//金额
    private BigDecimal localNonTaxAmount;//金额本位币
    private BigDecimal localAmount;//价税合计本位币
    private BigDecimal unSettleQty;//未结算数量
    private BigDecimal unSettleBaseQty;//未结算基本数量
    private String curSettleBill;//本次结算记录ID
    private String curSettleBillEntry;//本次结算记录分录ID
    private BigDecimal curSettleQty;//本次结算数量
    private BigDecimal totalSettleQty;//累计结算数量
    private BigDecimal totalSettleBaseQty;//累计结算基本数量
    private Date bizDate;//业务日期
    private boolean isFullWriteOff;//是否完全核销
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
    private int isPresent;//是否赠品
    private Date mfg;//生产日期
    private Date exp;//到期日期
    private BigDecimal reverseBaseQty;//冲销基本数量
    private BigDecimal returnBaseQty;//退货基本数量
    private String project;//项目号
    private String trackNumber;//跟踪号
    private String material;//物料
    private String assistProperty;//辅助属性
    private String unit;//计量单位
    private String sourceBill;//源单据Id
    private String sourceBillNumber;//来源单据编号
    private String sourceBillEntry;//来源单据分录的Id
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
    private String Id;//ID
    private String wms;
    private Integer IsCompleted;
    private String InvUpdateType;

    public String getInvUpdateType() {
        return InvUpdateType;
    }

    public void setInvUpdateType(String invUpdateType) {
        InvUpdateType = invUpdateType;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSaleOrderNumber() {
        return saleOrderNumber;
    }

    public void setSaleOrderNumber(String saleOrderNumber) {
        this.saleOrderNumber = saleOrderNumber;
    }

    public Integer getSaleOrderEntrySeq() {
        return saleOrderEntrySeq;
    }

    public void setSaleOrderEntrySeq(Integer saleOrderEntrySeq) {
        this.saleOrderEntrySeq = saleOrderEntrySeq;
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

    public BigDecimal getDrewQty() {
        return drewQty;
    }

    public void setDrewQty(BigDecimal drewQty) {
        this.drewQty = drewQty;
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

    public BigDecimal getDrewBaseQty() {
        return drewBaseQty;
    }

    public void setDrewBaseQty(BigDecimal drewBaseQty) {
        this.drewBaseQty = drewBaseQty;
    }

    public String getSaleOrder() {
        return saleOrder;
    }

    public void setSaleOrder(String saleOrder) {
        this.saleOrder = saleOrder;
    }

    public String getSaleOrderEntry() {
        return saleOrderEntry;
    }

    public void setSaleOrderEntry(String saleOrderEntry) {
        this.saleOrderEntry = saleOrderEntry;
    }

    public String getCoreBillType() {
        return coreBillType;
    }

    public void setCoreBillType(String coreBillType) {
        this.coreBillType = coreBillType;
    }

    public BigDecimal getUnReturnedBaseQty() {
        return unReturnedBaseQty;
    }

    public void setUnReturnedBaseQty(BigDecimal unReturnedBaseQty) {
        this.unReturnedBaseQty = unReturnedBaseQty;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
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

    public String getSaleOrgUnit() {
        return saleOrgUnit;
    }

    public void setSaleOrgUnit(String saleOrgUnit) {
        this.saleOrgUnit = saleOrgUnit;
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

    public BigDecimal getUndeliverQty() {
        return undeliverQty;
    }

    public void setUndeliverQty(BigDecimal undeliverQty) {
        this.undeliverQty = undeliverQty;
    }

    public BigDecimal getUndeliverBaseQty() {
        return undeliverBaseQty;
    }

    public void setUndeliverBaseQty(BigDecimal undeliverBaseQty) {
        this.undeliverBaseQty = undeliverBaseQty;
    }

    public BigDecimal getUnInQty() {
        return unInQty;
    }

    public void setUnInQty(BigDecimal unInQty) {
        this.unInQty = unInQty;
    }

    public BigDecimal getUnInBaseQty() {
        return unInBaseQty;
    }

    public void setUnInBaseQty(BigDecimal unInBaseQty) {
        this.unInBaseQty = unInBaseQty;
    }

    public String getBalanceCustomer() {
        return balanceCustomer;
    }

    public void setBalanceCustomer(String balanceCustomer) {
        this.balanceCustomer = balanceCustomer;
    }

    public boolean isCenterBalance() {
        return isCenterBalance;
    }

    public void setCenterBalance(boolean centerBalance) {
        isCenterBalance = centerBalance;
    }

    public boolean isBetweenCompanySend() {
        return isBetweenCompanySend;
    }

    public void setBetweenCompanySend(boolean betweenCompanySend) {
        isBetweenCompanySend = betweenCompanySend;
    }

    public BigDecimal getTotalInWarehsQty() {
        return totalInWarehsQty;
    }

    public void setTotalInWarehsQty(BigDecimal totalInWarehsQty) {
        this.totalInWarehsQty = totalInWarehsQty;
    }

    public BigDecimal getBaseUnitActualcost() {
        return baseUnitActualcost;
    }

    public void setBaseUnitActualcost(BigDecimal baseUnitActualcost) {
        this.baseUnitActualcost = baseUnitActualcost;
    }

    public String getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(String orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    public String getPaymentCustomer() {
        return paymentCustomer;
    }

    public void setPaymentCustomer(String paymentCustomer) {
        this.paymentCustomer = paymentCustomer;
    }

    public BigDecimal getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(BigDecimal confirmQty) {
        this.confirmQty = confirmQty;
    }

    public BigDecimal getConfirmBaseQty() {
        return confirmBaseQty;
    }

    public void setConfirmBaseQty(BigDecimal confirmBaseQty) {
        this.confirmBaseQty = confirmBaseQty;
    }

    public BigDecimal getAssociateBaseQty() {
        return associateBaseQty;
    }

    public void setAssociateBaseQty(BigDecimal associateBaseQty) {
        this.associateBaseQty = associateBaseQty;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getB2cBillType() {
        return b2cBillType;
    }

    public void setB2cBillType(String b2cBillType) {
        this.b2cBillType = b2cBillType;
    }

    public String getNetOrderBill() {
        return netOrderBill;
    }

    public void setNetOrderBill(String netOrderBill) {
        this.netOrderBill = netOrderBill;
    }

    public String getNetOrderBillNumber() {
        return netOrderBillNumber;
    }

    public void setNetOrderBillNumber(String netOrderBillNumber) {
        this.netOrderBillNumber = netOrderBillNumber;
    }

    public String getNetOrderBillEntry() {
        return netOrderBillEntry;
    }

    public void setNetOrderBillEntry(String netOrderBillEntry) {
        this.netOrderBillEntry = netOrderBillEntry;
    }

    public boolean isSquareBalance() {
        return isSquareBalance;
    }

    public void setSquareBalance(boolean squareBalance) {
        isSquareBalance = squareBalance;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    public BigDecimal getLocalNonTaxAmount() {
        return localNonTaxAmount;
    }

    public void setLocalNonTaxAmount(BigDecimal localNonTaxAmount) {
        this.localNonTaxAmount = localNonTaxAmount;
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }

    public BigDecimal getUnSettleQty() {
        return unSettleQty;
    }

    public void setUnSettleQty(BigDecimal unSettleQty) {
        this.unSettleQty = unSettleQty;
    }

    public BigDecimal getUnSettleBaseQty() {
        return unSettleBaseQty;
    }

    public void setUnSettleBaseQty(BigDecimal unSettleBaseQty) {
        this.unSettleBaseQty = unSettleBaseQty;
    }

    public String getCurSettleBill() {
        return curSettleBill;
    }

    public void setCurSettleBill(String curSettleBill) {
        this.curSettleBill = curSettleBill;
    }

    public String getCurSettleBillEntry() {
        return curSettleBillEntry;
    }

    public void setCurSettleBillEntry(String curSettleBillEntry) {
        this.curSettleBillEntry = curSettleBillEntry;
    }

    public BigDecimal getCurSettleQty() {
        return curSettleQty;
    }

    public void setCurSettleQty(BigDecimal curSettleQty) {
        this.curSettleQty = curSettleQty;
    }

    public BigDecimal getTotalSettleQty() {
        return totalSettleQty;
    }

    public void setTotalSettleQty(BigDecimal totalSettleQty) {
        this.totalSettleQty = totalSettleQty;
    }

    public BigDecimal getTotalSettleBaseQty() {
        return totalSettleBaseQty;
    }

    public void setTotalSettleBaseQty(BigDecimal totalSettleBaseQty) {
        this.totalSettleBaseQty = totalSettleBaseQty;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public boolean isFullWriteOff() {
        return isFullWriteOff;
    }

    public void setFullWriteOff(boolean fullWriteOff) {
        isFullWriteOff = fullWriteOff;
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

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
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

    public String getSourceBill() {
        return sourceBill;
    }

    public void setSourceBill(String sourceBill) {
        this.sourceBill = sourceBill;
    }

    public String getSourceBillNumber() {
        return sourceBillNumber;
    }

    public void setSourceBillNumber(String sourceBillNumber) {
        this.sourceBillNumber = sourceBillNumber;
    }

    public String getSourceBillEntry() {
        return sourceBillEntry;
    }

    public void setSourceBillEntry(String sourceBillEntry) {
        this.sourceBillEntry = sourceBillEntry;
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getWms() {
        return wms;
    }

    public void setWms(String wms) {
        this.wms = wms;
    }

    public Integer getIsCompleted() {
        return IsCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        IsCompleted = isCompleted;
    }
}
