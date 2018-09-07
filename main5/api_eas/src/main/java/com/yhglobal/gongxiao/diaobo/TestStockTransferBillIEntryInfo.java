package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;
import java.math.BigDecimal;

public class TestStockTransferBillIEntryInfo implements Serializable{
	//单据分录序列号
	private int seq;
	//原单据ID
	private String sourceBillID;
	//来源单据编号
	private String sourceBillNumber;
	//来源单据分录的ID
	private String sourceBillEntryID;
	//来源单据分录的序号
	private String sourceBillEntrySeq;
	//辅助计量单位换算系数
	private BigDecimal asscoefficient;
	//基本状态
	private int baseStatus;
	//未关联数量
	private BigDecimal associateQty;
	//来源单据类型
	private String sourceBillTypeID;
	//辅助属性
	private String assistpropertyID;
	//物料
	private String materialID;
	//计量单位
	private String unitID;
	//基本计量单位
	private String baseUnitID;
	//辅助计量单位
	private String assistUnitID;
	//原因代码
	private String reasonCodeID;
	//库存调拨单头
	private String parentID;
	//调出仓库
	private String issueWarehouseID;
	//调入仓库
	private String receiptWarehouseID;
	//批次
	private String lot;
	//数量
	private BigDecimal qty;
	//辅助单位数量
	private BigDecimal assistQty;
	//基本单位数量
	private BigDecimal baseQty;
	//计划调入日期
	private String receiptPlanDate;
	//单价
	private BigDecimal price;
	//金额
	private BigDecimal amount;
	//累计出库数量
	private int issueQty;
	//备注
	private String remark;
	//赠品
	private int isPresent;
	//税率
	private BigDecimal taxrate;
	//含税单价
	private BigDecimal taxPrice;
	//价税合计
	private BigDecimal taxAmount;
	//累计应收数量
	private int saleinvoiccQty;
	//累计应付数量
	private int purinvoiceQty;
	//税额
	private BigDecimal tax;
	//累计出库基本数量
	private int issueBaseQty;
	//累计入库数量
	private int receiptBaseQty;
	//累计应收基本数量
	private int saleinvoiceBaseQty;
	//累计应付基本数量
	private int purinvoiceBaseQty;
	//出库位关联基本数量
	private int unissueBaseQty;
	//调整代码原因
	private String reason;
	//累计应付金额
	private BigDecimal pruinvoiceAmount;
	//累计应收金额
	private BigDecimal saleinvoiceAmount;
	//累计付款金额
	private BigDecimal totalAccountPayable;
	//累计收款金额
	private BigDecimal totalAccountReceivable;
	//调出库位
	private String issueLocationID;
	//调入库位
	private String receiptLocationID;
	//发货库存组织
	private String issueStorageOrgUnitID;
	//发货财务组织
	private String issueCompanyOrgUnitID;
	//发货方销售组织
	private String issueSaleOrgUnitID;
	//收货库存组织
	private String receiveStorageOrgUnitID;
	//收货财务组织
	private String receiveCompanyOrgUnitID;
	//供应关系
	private String supplyrelation;
	//供应方销售组织
	private String supplySaleOrgUnitID;
	//需求方销售组织
	private String requireSaleOrgUnitID;
	//累计通知发货数量
	private int totalIssueQty;
	//结算成本价
	private BigDecimal balancecostPrice;
	//结算加成比率
	private BigDecimal balancecostRate;
	//实际单价
	private BigDecimal actualPrice;
	//折扣方式
	private int discountType;
	//折扣率
	private BigDecimal discountRate;
	//实际含税单价
	private BigDecimal actualTaxPrice;
	//本位币金额
	private BigDecimal localAmount;
	//本位币税额
	private BigDecimal localTax;
	//本位币价税合计
	private BigDecimal localTaxAmount;
	//折扣额
	private BigDecimal discountAmoumt;
	//本位币折扣额
	private BigDecimal localDiscountAmount;
	//生产日期
	private String mfg;
	//到期日期
	private String exp;
	//应收未关联基本数量
	private int arassociateBaseQty;
	//应付未关联基本数量
	private int apassociateBaseQty;
	//安排发运数量
	private int totalPlandQty;
	//计划调出日期
	private String issuePlanDate;
	//备料仓库
	private String manuwarehouseID;
	//不控制数量
	private int quarityUnctrl;
	//数量超出比率
	private BigDecimal quarityOverRate;
	//数量短板比率
	private BigDecimal quqrutyArreRate;
	//项目号
	private String projectID;
	//跟踪号
	private String trackNumberID;
	//库存类型
	private String storeTypeID;
	//业务日期
	private String bizDate;
	//需求转移
	private int ismrpcal;
	//客户
	private String customerID;
	//供应商
	private String supplierID;
	//预留单据对象ID
	private String reservationBillObjectID;
	//预留对象ID
	private String reservationID;
	//库存状态
	private String storestateID;
	//价格是否来自销售取价
	private int isSalePrice;
	
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
	public String getSourceBillEntrySeq() {
		return sourceBillEntrySeq;
	}
	public void setSourceBillEntrySeq(String sourceBillEntrySeq) {
		this.sourceBillEntrySeq = sourceBillEntrySeq;
	}
	public BigDecimal getAsscoefficient() {
		return asscoefficient;
	}
	public void setAsscoefficient(BigDecimal asscoefficient) {
		this.asscoefficient = asscoefficient;
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
	public String getAssistpropertyID() {
		return assistpropertyID;
	}
	public void setAssistpropertyID(String assistpropertyID) {
		this.assistpropertyID = assistpropertyID;
	}
	public String getMaterialID() {
		return materialID;
	}
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}
	public String getUnitID() {
		return unitID;
	}
	public void setUnitID(String unitID) {
		this.unitID = unitID;
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
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getIssueWarehouseID() {
		return issueWarehouseID;
	}
	public void setIssueWarehouseID(String issueWarehouseID) {
		this.issueWarehouseID = issueWarehouseID;
	}
	public String getReceiptWarehouseID() {
		return receiptWarehouseID;
	}
	public void setReceiptWarehouseID(String receiptWarehouseID) {
		this.receiptWarehouseID = receiptWarehouseID;
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
	public String getReceiptPlanDate() {
		return receiptPlanDate;
	}
	public void setReceiptPlanDate(String receiptPlanDate) {
		this.receiptPlanDate = receiptPlanDate;
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
	public int getIssueQty() {
		return issueQty;
	}
	public void setIssueQty(int issueQty) {
		this.issueQty = issueQty;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int isPresent() {
		return isPresent;
	}
	public void setPresent(int isPresent) {
		this.isPresent = isPresent;
	}
	public BigDecimal getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}
	public BigDecimal getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	public int getSaleinvoiccQty() {
		return saleinvoiccQty;
	}
	public void setSaleinvoiccQty(int saleinvoiccQty) {
		this.saleinvoiccQty = saleinvoiccQty;
	}
	public int getPurinvoiceQty() {
		return purinvoiceQty;
	}
	public void setPurinvoiceQty(int purinvoiceQty) {
		this.purinvoiceQty = purinvoiceQty;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public int getIssueBaseQty() {
		return issueBaseQty;
	}
	public void setIssueBaseQty(int issueBaseQty) {
		this.issueBaseQty = issueBaseQty;
	}
	public int getReceiptBaseQty() {
		return receiptBaseQty;
	}
	public void setReceiptBaseQty(int receiptBaseQty) {
		this.receiptBaseQty = receiptBaseQty;
	}
	public int getSaleinvoiceBaseQty() {
		return saleinvoiceBaseQty;
	}
	public void setSaleinvoiceBaseQty(int saleinvoiceBaseQty) {
		this.saleinvoiceBaseQty = saleinvoiceBaseQty;
	}
	public int getPurinvoiceBaseQty() {
		return purinvoiceBaseQty;
	}
	public void setPurinvoiceBaseQty(int purinvoiceBaseQty) {
		this.purinvoiceBaseQty = purinvoiceBaseQty;
	}
	public int getUnissueBaseQty() {
		return unissueBaseQty;
	}
	public void setUnissueBaseQty(int unissueBaseQty) {
		this.unissueBaseQty = unissueBaseQty;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public BigDecimal getPruinvoiceAmount() {
		return pruinvoiceAmount;
	}
	public void setPruinvoiceAmount(BigDecimal pruinvoiceAmount) {
		this.pruinvoiceAmount = pruinvoiceAmount;
	}
	public BigDecimal getSaleinvoiceAmount() {
		return saleinvoiceAmount;
	}
	public void setSaleinvoiceAmount(BigDecimal saleinvoiceAmount) {
		this.saleinvoiceAmount = saleinvoiceAmount;
	}
	public BigDecimal getTotalAccountPayable() {
		return totalAccountPayable;
	}
	public void setTotalAccountPayable(BigDecimal totalAccountPayable) {
		this.totalAccountPayable = totalAccountPayable;
	}
	public BigDecimal getTotalAccountReceivable() {
		return totalAccountReceivable;
	}
	public void setTotalAccountReceivable(BigDecimal totalAccountReceivable) {
		this.totalAccountReceivable = totalAccountReceivable;
	}
	public String getIssueLocationID() {
		return issueLocationID;
	}
	public void setIssueLocationID(String issueLocationID) {
		this.issueLocationID = issueLocationID;
	}
	public String getReceiptLocationID() {
		return receiptLocationID;
	}
	public void setReceiptLocationID(String receiptLocationID) {
		this.receiptLocationID = receiptLocationID;
	}
	public int getIsPresent() {
		return isPresent;
	}
	public void setIsPresent(int isPresent) {
		this.isPresent = isPresent;
	}
	public String getIssueStorageOrgUnitID() {
		return issueStorageOrgUnitID;
	}
	public void setIssueStorageOrgUnitID(String issueStorageOrgUnitID) {
		this.issueStorageOrgUnitID = issueStorageOrgUnitID;
	}
	public String getIssueCompanyOrgUnitID() {
		return issueCompanyOrgUnitID;
	}
	public void setIssueCompanyOrgUnitID(String issueCompanyOrgUnitID) {
		this.issueCompanyOrgUnitID = issueCompanyOrgUnitID;
	}
	public String getIssueSaleOrgUnitID() {
		return issueSaleOrgUnitID;
	}
	public void setIssueSaleOrgUnitID(String issueSaleOrgUnitID) {
		this.issueSaleOrgUnitID = issueSaleOrgUnitID;
	}
	public String getReceiveStorageOrgUnitID() {
		return receiveStorageOrgUnitID;
	}
	public void setReceiveStorageOrgUnitID(String receiveStorageOrgUnitID) {
		this.receiveStorageOrgUnitID = receiveStorageOrgUnitID;
	}
	public String getReceiveCompanyOrgUnitID() {
		return receiveCompanyOrgUnitID;
	}
	public void setReceiveCompanyOrgUnitID(String receiveCompanyOrgUnitID) {
		this.receiveCompanyOrgUnitID = receiveCompanyOrgUnitID;
	}
	public String getSupplyrelation() {
		return supplyrelation;
	}
	public void setSupplyrelation(String supplyrelation) {
		this.supplyrelation = supplyrelation;
	}
	public String getSupplySaleOrgUnitID() {
		return supplySaleOrgUnitID;
	}
	public void setSupplySaleOrgUnitID(String supplySaleOrgUnitID) {
		this.supplySaleOrgUnitID = supplySaleOrgUnitID;
	}
	public String getRequireSaleOrgUnitID() {
		return requireSaleOrgUnitID;
	}
	public void setRequireSaleOrgUnitID(String requireSaleOrgUnitID) {
		this.requireSaleOrgUnitID = requireSaleOrgUnitID;
	}
	public int getTotalIssueQty() {
		return totalIssueQty;
	}
	public void setTotalIssueQty(int totalIssueQty) {
		this.totalIssueQty = totalIssueQty;
	}
	public BigDecimal getBalancecostPrice() {
		return balancecostPrice;
	}
	public void setBalancecostPrice(BigDecimal balancecostPrice) {
		this.balancecostPrice = balancecostPrice;
	}
	public BigDecimal getBalancecostRate() {
		return balancecostRate;
	}
	public void setBalancecostRate(BigDecimal balancecostRate) {
		this.balancecostRate = balancecostRate;
	}
	public BigDecimal getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}
	public int getDiscountType() {
		return discountType;
	}
	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}
	public BigDecimal getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}
	public BigDecimal getActualTaxPrice() {
		return actualTaxPrice;
	}
	public void setActualTaxPrice(BigDecimal actualTaxPrice) {
		this.actualTaxPrice = actualTaxPrice;
	}
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	public BigDecimal getLocalTax() {
		return localTax;
	}
	public void setLocalTax(BigDecimal localTax) {
		this.localTax = localTax;
	}
	public BigDecimal getLocalTaxAmount() {
		return localTaxAmount;
	}
	public void setLocalTaxAmount(BigDecimal localTaxAmount) {
		this.localTaxAmount = localTaxAmount;
	}
	public BigDecimal getDiscountAmoumt() {
		return discountAmoumt;
	}
	public void setDiscountAmoumt(BigDecimal discountAmoumt) {
		this.discountAmoumt = discountAmoumt;
	}
	public BigDecimal getLocalDiscountAmount() {
		return localDiscountAmount;
	}
	public void setLocalDiscountAmount(BigDecimal localDiscountAmount) {
		this.localDiscountAmount = localDiscountAmount;
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
	public int getArassociateBaseQty() {
		return arassociateBaseQty;
	}
	public void setArassociateBaseQty(int arassociateBaseQty) {
		this.arassociateBaseQty = arassociateBaseQty;
	}
	public int getApassociateBaseQty() {
		return apassociateBaseQty;
	}
	public void setApassociateBaseQty(int apassociateBaseQty) {
		this.apassociateBaseQty = apassociateBaseQty;
	}
	public int getTotalPlandQty() {
		return totalPlandQty;
	}
	public void setTotalPlandQty(int totalPlandQty) {
		this.totalPlandQty = totalPlandQty;
	}
	public String getIssuePlanDate() {
		return issuePlanDate;
	}
	public void setIssuePlanDate(String issuePlanDate) {
		this.issuePlanDate = issuePlanDate;
	}
	public String getManuwarehouseID() {
		return manuwarehouseID;
	}
	public void setManuwarehouseID(String manuwarehouseID) {
		this.manuwarehouseID = manuwarehouseID;
	}
	public int getQuarityUnctrl() {
		return quarityUnctrl;
	}
	public void setQuarityUnctrl(int quarityUnctrl) {
		this.quarityUnctrl = quarityUnctrl;
	}
	public BigDecimal getQuarityOverRate() {
		return quarityOverRate;
	}
	public void setQuarityOverRate(BigDecimal quarityOverRate) {
		this.quarityOverRate = quarityOverRate;
	}
	public BigDecimal getQuqrutyArreRate() {
		return quqrutyArreRate;
	}
	public void setQuqrutyArreRate(BigDecimal quqrutyArreRate) {
		this.quqrutyArreRate = quqrutyArreRate;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getTrackNumberID() {
		return trackNumberID;
	}
	public void setTrackNumberID(String trackNumberID) {
		this.trackNumberID = trackNumberID;
	}
	public String getStoreTypeID() {
		return storeTypeID;
	}
	public void setStoreTypeID(String storeTypeID) {
		this.storeTypeID = storeTypeID;
	}
	public String getBizDate() {
		return bizDate;
	}
	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}
	public int getIsmrpcal() {
		return ismrpcal;
	}
	public void setIsmrpcal(int ismrpcal) {
		this.ismrpcal = ismrpcal;
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
	public String getReservationBillObjectID() {
		return reservationBillObjectID;
	}
	public void setReservationBillObjectID(String reservationBillObjectID) {
		this.reservationBillObjectID = reservationBillObjectID;
	}
	public String getReservationID() {
		return reservationID;
	}
	public void setReservationID(String reservationID) {
		this.reservationID = reservationID;
	}
	public String getStorestateID() {
		return storestateID;
	}
	public void setStorestateID(String storestateID) {
		this.storestateID = storestateID;
	}
	public int getIsSalePrice() {
		return isSalePrice;
	}
	public void setIsSalePrice(int isSalePrice) {
		this.isSalePrice = isSalePrice;
	}
	
}
