package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * 调拨出库单单据头
 * */
public class XPSMoveIssueBillInfo implements Serializable {
	/**校验是否重复创建单据*/
	private String wms;
	/**必填字段*/
	private String creatorID;//创建者
	private String bizType;//业务类型
	private String bizDate;//业务日期
	private String createTime;//创建时间
	private String sourceBillID;//原始单据ID
	private String transactionTypeID;//事务类型
	private String issueStorageOrgUnitID;//调出库存组织
	private String receiptStorageOrgUnitID;//调入库存组织
	private String issueCompanyOrgUnitID;//调出财务组织
	private String receiptCompanyOrgUnitID;//调入财务组织
	private List<XPSMoveIssueBillEntryInfo> entrys;
	/**非必填字段*/
	private String adminOrgUnitID;//部门
	private String lastUpdateUserID;//最后修改者
	private String lastUpdateTime;//最后修改时间
	private String controlUnitID;//控制单元
	private String number;//单据编号
	private String handlerID;//经手人
	private String description;//参考信息
	private int hasEffected;//是否曾经生效
	private String auditorID;//审核人
	private String sourceFunction;//来源功能
	private String auditTime;//审核时间
	private String baseStatus;//单据状态
	private String sourceBillTypeID;//来源单据类型
	private String billType;//单据类型
	private int year;//业务年度
	private int period;//业务期间
	private String stockerID;//库管员
	private String vouceherID;//凭证
	private BigDecimal totalQty;//总数量
	private BigDecimal totalAmount;//总金额
	private int fivouchered;//是否生成凭证
	private BigDecimal totalStandardCost;//总标准成本
	private BigDecimal totalActualCost;//总实际成本
	private int isreversed;//是否冲销
	private int isinitBill;//是否初始化单
	private String modificationTime;//修改时间
	private String modifierID;//修改人
	private String storeTypeID;//库存类型
	private int month;//月
	private int day;//日
	private int isSysBill;//isSysBill
	private String costCenterOrgUnitID;//成本中心
	private String unitSource;//计量单位来源
	
	public String getWms() {
		return wms;
	}
	public void setWms(String wms) {
		this.wms = wms;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<XPSMoveIssueBillEntryInfo> getEntrys() {
		return entrys;
	}
	public void setEntrys(List<XPSMoveIssueBillEntryInfo> entrys) {
		this.entrys = entrys;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getBizDate() {
		return bizDate;
	}
	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}
	public String getTransactionTypeID() {
		return transactionTypeID;
	}
	public void setTransactionTypeID(String transactionTypeID) {
		this.transactionTypeID = transactionTypeID;
	}
	
	public String getIssueStorageOrgUnitID() {
		return issueStorageOrgUnitID;
	}
	public void setIssueStorageOrgUnitID(String issueStorageOrgUnitID) {
		this.issueStorageOrgUnitID = issueStorageOrgUnitID;
	}
	public String getReceiptStorageOrgUnitID() {
		return receiptStorageOrgUnitID;
	}
	public void setReceiptStorageOrgUnitID(String receiptStorageOrgUnitID) {
		this.receiptStorageOrgUnitID = receiptStorageOrgUnitID;
	}
	public String getIssueCompanyOrgUnitID() {
		return issueCompanyOrgUnitID;
	}
	public void setIssueCompanyOrgUnitID(String issueCompanyOrgUnitID) {
		this.issueCompanyOrgUnitID = issueCompanyOrgUnitID;
	}
	public String getReceiptCompanyOrgUnitID() {
		return receiptCompanyOrgUnitID;
	}
	public void setReceiptCompanyOrgUnitID(String receiptCompanyOrgUnitID) {
		this.receiptCompanyOrgUnitID = receiptCompanyOrgUnitID;
	}
	public String getAdminOrgUnitID() {
		return adminOrgUnitID;
	}
	public void setAdminOrgUnitID(String adminOrgUnitID) {
		this.adminOrgUnitID = adminOrgUnitID;
	}
	public String getLastUpdateUserID() {
		return lastUpdateUserID;
	}
	public void setLastUpdateUserID(String lastUpdateUserID) {
		this.lastUpdateUserID = lastUpdateUserID;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getControlUnitID() {
		return controlUnitID;
	}
	public void setControlUnitID(String controlUnitID) {
		this.controlUnitID = controlUnitID;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
	public int getHasEffected() {
		return hasEffected;
	}
	public void setHasEffected(int hasEffected) {
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
	public String getSourceBillTypeID() {
		return sourceBillTypeID;
	}
	public void setSourceBillTypeID(String sourceBillTypeID) {
		this.sourceBillTypeID = sourceBillTypeID;
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
	public String getStockerID() {
		return stockerID;
	}
	public void setStockerID(String stockerID) {
		this.stockerID = stockerID;
	}
	public String getVouceherID() {
		return vouceherID;
	}
	public void setVouceherID(String vouceherID) {
		this.vouceherID = vouceherID;
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
	public int getFivouchered() {
		return fivouchered;
	}
	public void setFivouchered(int fivouchered) {
		this.fivouchered = fivouchered;
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
	public int getIsreversed() {
		return isreversed;
	}
	public void setIsreversed(int isreversed) {
		this.isreversed = isreversed;
	}
	public int getIsinitBill() {
		return isinitBill;
	}
	public void setIsinitBill(int isinitBill) {
		this.isinitBill = isinitBill;
	}
	public String getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(String modificationTime) {
		this.modificationTime = modificationTime;
	}
	public String getModifierID() {
		return modifierID;
	}
	public void setModifierID(String modifierID) {
		this.modifierID = modifierID;
	}
	public String getStoreTypeID() {
		return storeTypeID;
	}
	public void setStoreTypeID(String storeTypeID) {
		this.storeTypeID = storeTypeID;
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
	public int getIsSysBill() {
		return isSysBill;
	}
	public void setIsSysBill(int isSysBill) {
		this.isSysBill = isSysBill;
	}
	public String getCostCenterOrgUnitID() {
		return costCenterOrgUnitID;
	}
	public void setCostCenterOrgUnitID(String costCenterOrgUnitID) {
		this.costCenterOrgUnitID = costCenterOrgUnitID;
	}
	public String getUnitSource() {
		return unitSource;
	}
	public void setUnitSource(String unitSource) {
		this.unitSource = unitSource;
	}
	
}
