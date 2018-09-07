package com.yhglobal.gongxiao.payment.account;

import java.io.Serializable;
import java.util.Date;

/**
 * 经销商在越海供销平台的现金支付账户
 *
 * @Author: 葛灿
 */
public class DistributorCashAccount implements Serializable {

    /**
     * 主键id
     */
    private Long rowId;
    /**
     * 项目id
     */
    private long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 经销商Id
     */
    private Long distributorId;
    /**
     * 经销商名称
     */
    private String distributorName;
    /**
     * 客户账号
     */
    private String clientAccount;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 账户状态 0:初始化未启用 1:正常使用 8:已冻结 9:已废弃
     */
    private int status;
    /**
     * 现金余额
     */
    private Long totalCashAmount;
    /**
     * 暂扣现金
     */
    private Long frozenCashAmount;
    /**
     * 暂扣信息
     */
    private String frozenCashInfo;
    /**
     * 数据版本
     */
    private Long dataVersion;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    /**
     * 操作日记
     */
    private String tracelog;

    public Long getFrozenCashAmount() {
        return frozenCashAmount;
    }

    public void setFrozenCashAmount(Long frozenCashAmount) {
        this.frozenCashAmount = frozenCashAmount;
    }

    public String getFrozenCashInfo() {
        return frozenCashInfo;
    }

    public void setFrozenCashInfo(String frozenCashInfo) {
        this.frozenCashInfo = frozenCashInfo;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName == null ? null : distributorName.trim();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(Long totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}