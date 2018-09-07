package com.yhglobal.gongxiao.payment.model;

import java.util.Date;

/**
 * 坚果高卖账户模型
 *
 * @author 葛灿
 */
public class JmgoSellHighAccount {

    /**主键id*/
    private Long accountId;
    /**项目id*/
    private Long projectId;
    /**项目名称*/
    private String projectName;
    /**状态 (暂未使用)*/
    private Byte status;
    /**货币编码*/
    private String currencyCode;
    /**账户余额*/
    private Long totalAmount;
    /**数据版本*/
    private Long dataVersion;
    /**创建时间*/
    private Date createTime;
    /**最后更新时间*/
    private Date lastUpdateTime;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
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
}
