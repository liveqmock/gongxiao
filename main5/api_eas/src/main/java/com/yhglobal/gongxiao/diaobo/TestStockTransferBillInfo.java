package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * 库存调拨单信息
 */
public class TestStockTransferBillInfo implements Serializable{
	/**校验是否重复创建单据*/
	private String wms;
	//创建者
	private String creatorID;
	//创建时间
	private String createTime;
	//最后修改者
	private String lastUpdateUserID;
	//最后修改时间
	private String lastUpdateTime;
	//控制单元
	private String controlunitID;
	//业务日期
	private String bizDate;
	//经手人
	private String handlerID;
	//参考信息
	private String description;
	//是否曾经生效
	private int hasEffected;
	//审核人
	private String auditorID;
	//原始单据ID
	private String sourceBillID;
	//来源功能
	private String sourceFunction;
	//审核时间
	private String auditTime;
	//审核状态
	private int baseStatus;
	//业务类型
	private String bizType;
	//来源单据类型
	private String sourceBillType;
	//单据类型
	private String billType;
	//业务年度
	private int year;
	//业务期间
	private int period;
	//调出财务组织
	private String issueCompanyorgUnitID;
	//调入财务组织
	private String receIptCompanyorgUnitID;
	//调出库存组织
	private String issueStorageorgUnitID;
	//调入库存组织
	private String receIptStorageorgUnitID;
	//调出部门
	private String issueAdminorgUnitID;
	//调入部门
	private String receIptAdminorgUnitID;
	//是否发运
	private int isShipment;
	//修改人
	private String modifierID;
	//修改时间
	private String modificationTime;
	//币别
	private String currencyID;
	//汇率
	private BigDecimal exchangeRate;
	//初始化单
	private int isInitBill;
	//是否含税
	private int isIntax;
	//计量单位来源
	private int unitsource;
	//分录
	private List<TestStockTransferBillIEntryInfo> entrys;
	
	public String getWms() {
		return wms;
	}
	public void setWms(String wms) {
		this.wms = wms;
	}
	public List<TestStockTransferBillIEntryInfo> getEntrys() {
		return entrys;
	}
	public void setEntrys(List<TestStockTransferBillIEntryInfo> entrys) {
		this.entrys = entrys;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getControlunitID() {
		return controlunitID;
	}
	public void setControlunitID(String controlunitID) {
		this.controlunitID = controlunitID;
	}
	public String getBizDate() {
		return bizDate;
	}
	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
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
	public int getBaseStatus() {
		return baseStatus;
	}
	public void setBaseStatus(int baseStatus) {
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
	public String getIssueCompanyorgUnitID() {
		return issueCompanyorgUnitID;
	}
	public void setIssueCompanyorgUnitID(String issueCompanyorgUnitID) {
		this.issueCompanyorgUnitID = issueCompanyorgUnitID;
	}
	public String getReceIptCompanyorgUnitID() {
		return receIptCompanyorgUnitID;
	}
	public void setReceIptCompanyorgUnitID(String receIptCompanyorgUnitID) {
		this.receIptCompanyorgUnitID = receIptCompanyorgUnitID;
	}
	public String getIssueStorageorgUnitID() {
		return issueStorageorgUnitID;
	}
	public void setIssueStorageorgUnitID(String issueStorageorgUnitID) {
		this.issueStorageorgUnitID = issueStorageorgUnitID;
	}
	public String getReceIptStorageorgUnitID() {
		return receIptStorageorgUnitID;
	}
	public void setReceIptStorageorgUnitID(String receIptStorageorgUnitID) {
		this.receIptStorageorgUnitID = receIptStorageorgUnitID;
	}
	public String getIssueAdminorgUnitID() {
		return issueAdminorgUnitID;
	}
	public void setIssueAdminorgUnitID(String issueAdminorgUnitID) {
		this.issueAdminorgUnitID = issueAdminorgUnitID;
	}
	public String getReceIptAdminorgUnitID() {
		return receIptAdminorgUnitID;
	}
	public void setReceIptAdminorgUnitID(String receIptAdminorgUnitID) {
		this.receIptAdminorgUnitID = receIptAdminorgUnitID;
	}
	public int getIsShipment() {
		return isShipment;
	}
	public void setIsShipment(int isShipment) {
		this.isShipment = isShipment;
	}
	public String getModifierID() {
		return modifierID;
	}
	public void setModifierID(String modifierID) {
		this.modifierID = modifierID;
	}
	public String getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(String modificationTime) {
		this.modificationTime = modificationTime;
	}
	public String getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public int getIsInitBill() {
		return isInitBill;
	}
	public void setIsInitBill(int isInitBill) {
		this.isInitBill = isInitBill;
	}
	public int getIsIntax() {
		return isIntax;
	}
	public void setIsIntax(int isIntax) {
		this.isIntax = isIntax;
	}
	public int getUnitsource() {
		return unitsource;
	}
	public void setUnitsource(int unitsource) {
		this.unitsource = unitsource;
	}
	
	
}
