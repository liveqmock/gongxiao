package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;
import java.math.BigDecimal;

public class XPSMoveIssueBillEntryInfo implements Serializable{
	/**必填字段*/
	private int seq;//单据分录序号
	private String sourceBillID;//原单据ID
	private String sourceBillNumber;//来源单据编号
	private String sourceBillEntryID;//来源单据分录的id
	private int sourceBillEntrySeq;//来源单据分录序号
	private String parentID;//调拨出库单头
	private String stockTransferBillID;//调拨单头
	private String stockTransBillEntryID;//调拨单分录
	private String stockTransferBillNum;//调拨单单号
	private int stockTransferBillEntrySeq;//调拨单行号
	private String invUpdateTypeID;//更新类型
	private String materialID;//物料
	private String lot;//批次
	private String unitID;//计量单位
	private BigDecimal Qty;//数量
	private String warehouseID;//仓库

	/**非必填字段*/
	private BigDecimal asscoEfficient;//辅助计量单位换算系数
	private int baseStatus;//基本状态
	private BigDecimal associateQty;//未关联数量
	private String sourceBillTypeID;//来源单据类型
	private String assistPropertyID;//辅助属性
	private String baseUnitID;//基本计量单位
	private String assistUnitID;//辅助计量单位
	private String reasonCodeID;//原因代码
	private String StorageOrgUnitID;//库存组织
	private String companyOrgUnitID;//财务组织
	private String locationID;//库位
	private String stockerID;//仓管员
	private BigDecimal assistQty;//辅助数量
	private BigDecimal baseQty;//基本数量
	private BigDecimal reverseQty;//冲销数量
	private BigDecimal returnsQty;//退货数量
	private BigDecimal price;//单价
	private BigDecimal amount;//金额
	private BigDecimal unitStandardCost;//单位标准成本
	private BigDecimal standardCost;//标准成本
	private BigDecimal unitActualCost;//单位实际成本
	private BigDecimal actualCost;//实际成本
	private int ispresent;//是否赠品
	private String remark;//备注
	private BigDecimal reverseBaseQty;//冲销基本数量
	private BigDecimal returnBaseQty;//退货基本数量
	private BigDecimal baseUnitActualCost;//基本单位实际成本
	private BigDecimal totalInwareHsQty;//累计入库数量
	private BigDecimal canInwareHsBaseQty;//可入库基本数量
	private String projectID;//项目号
	private String trackNumber;//跟踪号
	private String customerID;//客户
	private String supplierID;//供应商
	private String storeTypeID;//仓库类型
	private String bizdate;//业务日期
	private String exp;//到期日期
	private String mfg;//生产日期
	private String reservationBilObjectID;//预留单据对象ID
	private String reservationID;//预留对象id
	private String accountViewinID;//入库会计科目
	private String accountViewOutID;//出库科目
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
	public int getSourceBillEntrySeq() {
		return sourceBillEntrySeq;
	}
	public void setSourceBillEntrySeq(int sourceBillEntrySeq) {
		this.sourceBillEntrySeq = sourceBillEntrySeq;
	}
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getStockTransferBillID() {
		return stockTransferBillID;
	}
	public void setStockTransferBillID(String stockTransferBillID) {
		this.stockTransferBillID = stockTransferBillID;
	}
	public String getStockTransBillEntryID() {
		return stockTransBillEntryID;
	}
	public void setStockTransBillEntryID(String stockTransBillEntryID) {
		this.stockTransBillEntryID = stockTransBillEntryID;
	}
	public String getStockTransferBillNum() {
		return stockTransferBillNum;
	}
	public void setStockTransferBillNum(String stockTransferBillNum) {
		this.stockTransferBillNum = stockTransferBillNum;
	}
	public int getStockTransferBillEntrySeq() {
		return stockTransferBillEntrySeq;
	}
	public void setStockTransferBillEntrySeq(int stockTransferBillEntrySeq) {
		this.stockTransferBillEntrySeq = stockTransferBillEntrySeq;
	}
	public String getInvUpdateTypeID() {
		return invUpdateTypeID;
	}
	public void setInvUpdateTypeID(String invUpdateTypeID) {
		this.invUpdateTypeID = invUpdateTypeID;
	}
	public String getMaterialID() {
		return materialID;
	}
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}
	public String getLot() {
		return lot;
	}
	public void setLot(String lot) {
		this.lot = lot;
	}
	public String getUnitID() {
		return unitID;
	}
	public void setUnitID(String unitID) {
		this.unitID = unitID;
	}
	public BigDecimal getQty() {
		return Qty;
	}
	public void setQty(BigDecimal qty) {
		Qty = qty;
	}
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public BigDecimal getAsscoEfficient() {
		return asscoEfficient;
	}
	public void setAsscoEfficient(BigDecimal asscoEfficient) {
		this.asscoEfficient = asscoEfficient;
	}
	public int getBaseStatus() {
		return baseStatus;
	}
	public void setBaseStatus(int baseStatus) {
		this.baseStatus = baseStatus;
	}
	public BigDecimal getAssociateQty() {
		return associateQty;
	}
	public void setAssociateQty(BigDecimal associateQty) {
		this.associateQty = associateQty;
	}
	public String getSourceBillTypeID() {
		return sourceBillTypeID;
	}
	public void setSourceBillTypeID(String sourceBillTypeID) {
		this.sourceBillTypeID = sourceBillTypeID;
	}
	public String getAssistPropertyID() {
		return assistPropertyID;
	}
	public void setAssistPropertyID(String assistPropertyID) {
		this.assistPropertyID = assistPropertyID;
	}
	public String getBaseUnitID() {
		return baseUnitID;
	}
	public void setBaseUnitID(String baseUnitID) {
		this.baseUnitID = baseUnitID;
	}
	public String getAssistUnitID() {
		return assistUnitID;
	}
	public void setAssistUnitID(String assistUnitID) {
		this.assistUnitID = assistUnitID;
	}
	public String getReasonCodeID() {
		return reasonCodeID;
	}
	public void setReasonCodeID(String reasonCodeID) {
		this.reasonCodeID = reasonCodeID;
	}
	public String getStorageOrgUnitID() {
		return StorageOrgUnitID;
	}
	public void setStorageOrgUnitID(String storageOrgUnitID) {
		StorageOrgUnitID = storageOrgUnitID;
	}
	public String getCompanyOrgUnitID() {
		return companyOrgUnitID;
	}
	public void setCompanyOrgUnitID(String companyOrgUnitID) {
		this.companyOrgUnitID = companyOrgUnitID;
	}
	public String getLocationID() {
		return locationID;
	}
	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}
	public String getStockerID() {
		return stockerID;
	}
	public void setStockerID(String stockerID) {
		this.stockerID = stockerID;
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
	public int getIspresent() {
		return ispresent;
	}
	public void setIspresent(int ispresent) {
		this.ispresent = ispresent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public BigDecimal getBaseUnitActualCost() {
		return baseUnitActualCost;
	}
	public void setBaseUnitActualCost(BigDecimal baseUnitActualCost) {
		this.baseUnitActualCost = baseUnitActualCost;
	}
	public BigDecimal getTotalInwareHsQty() {
		return totalInwareHsQty;
	}
	public void setTotalInwareHsQty(BigDecimal totalInwareHsQty) {
		this.totalInwareHsQty = totalInwareHsQty;
	}
	public BigDecimal getCanInwareHsBaseQty() {
		return canInwareHsBaseQty;
	}
	public void setCanInwareHsBaseQty(BigDecimal canInwareHsBaseQty) {
		this.canInwareHsBaseQty = canInwareHsBaseQty;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getTrackNumber() {
		return trackNumber;
	}
	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}
	public String getStoreTypeID() {
		return storeTypeID;
	}
	public void setStoreTypeID(String storeTypeID) {
		this.storeTypeID = storeTypeID;
	}
	public String getBizdate() {
		return bizdate;
	}
	public void setBizdate(String bizdate) {
		this.bizdate = bizdate;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getMfg() {
		return mfg;
	}
	public void setMfg(String mfg) {
		this.mfg = mfg;
	}
	public String getReservationBilObjectID() {
		return reservationBilObjectID;
	}
	public void setReservationBilObjectID(String reservationBilObjectID) {
		this.reservationBilObjectID = reservationBilObjectID;
	}
	public String getReservationID() {
		return reservationID;
	}
	public void setReservationID(String reservationID) {
		this.reservationID = reservationID;
	}
	public String getAccountViewinID() {
		return accountViewinID;
	}
	public void setAccountViewinID(String accountViewinID) {
		this.accountViewinID = accountViewinID;
	}
	public String getAccountViewOutID() {
		return accountViewOutID;
	}
	public void setAccountViewOutID(String accountViewOutID) {
		this.accountViewOutID = accountViewOutID;
	}
	
}
