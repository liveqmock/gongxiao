package com.yhglobal.gongxiao.payment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierUnitPriceDiscountFrozenAccount implements Serializable{
    /**
     * 供应商Id
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 账户状态 1:初始化未启用 2:正常使用 8:已冻结 9:已废弃
     */
    private Byte status;

    /**
     * 货币编码
     */
    private String currencyCode;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * 数据版本
     */
    private Long dataVersion;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date lastUpdateTime;

    /**
     * 操作日记
     */
    private String tracelog;

    /**
     * 供应商Id
     * @return supplierId 供应商Id
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * 供应商Id
     * @param supplierId 供应商Id
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 供应商名称
     * @return supplierName 供应商名称
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * 供应商名称
     * @param supplierName 供应商名称
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    /**
     * 供应商编码
     * @return supplierCode 供应商编码
     */
    public String getSupplierCode() {
        return supplierCode;
    }

    /**
     * 供应商编码
     * @param supplierCode 供应商编码
     */
    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    /**
     * 项目id
     * @return projectId 项目id
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * 项目id
     * @param projectId 项目id
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * 项目名称
     * @return projectName 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 项目名称
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * 账户状态 1:初始化未启用 2:正常使用 8:已冻结 9:已废弃
     * @return status 账户状态 1:初始化未启用 2:正常使用 8:已冻结 9:已废弃
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 账户状态 1:初始化未启用 2:正常使用 8:已冻结 9:已废弃
     * @param status 账户状态 1:初始化未启用 2:正常使用 8:已冻结 9:已废弃
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 货币编码
     * @return currencyCode 货币编码
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 货币编码
     * @param currencyCode 货币编码
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    /**
     * 冻结金额
     * @return frozenAmount 冻结金额
     */
    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    /**
     * 冻结金额
     * @param frozenAmount 冻结金额
     */
    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    /**
     * 数据版本
     * @return dataVersion 数据版本
     */
    public Long getDataVersion() {
        return dataVersion;
    }

    /**
     * 数据版本
     * @param dataVersion 数据版本
     */
    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    /**
     * 创建时间
     * @return createTime 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return lastUpdateTime 
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 
     * @param lastUpdateTime 
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * 操作日记
     * @return tracelog 操作日记
     */
    public String getTracelog() {
        return tracelog;
    }

    /**
     * 操作日记
     * @param tracelog 操作日记
     */
    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }
}