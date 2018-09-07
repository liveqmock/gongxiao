package com.yhglobal.gongxiao.foundation.distributor.model;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class FoundationSummary {
    /**
     * 经销商业务ID
     */
    private long distributorBusinessId;
    /**
     * 经销商用户ID
     */
    private long distributorUserId;
    /**
     * 客户EAS编码
     */
    private String distributorEasCode;
    /**
     * 经销商名称
     */
    private String distributorName;
    /**
     * 客户账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 操作人
     */
    private String operatePerson;
    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;
    /**
     * 状态
     */
    private byte recordStatus;

    public long getDistributorBusinessId() {
        return distributorBusinessId;
    }

    public void setDistributorBusinessId(long distributorBusinessId) {
        this.distributorBusinessId = distributorBusinessId;
    }

    public long getDistributorUserId() {
        return distributorUserId;
    }

    public void setDistributorUserId(long distributorUserId) {
        this.distributorUserId = distributorUserId;
    }

    public String getDistributorEasCode() {
        return distributorEasCode;
    }

    public void setDistributorEasCode(String distributorEasCode) {
        this.distributorEasCode = distributorEasCode;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOperatePerson() {
        return operatePerson;
    }

    public void setOperatePerson(String operatePerson) {
        this.operatePerson = operatePerson;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public byte getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(byte recordStatus) {
        this.recordStatus = recordStatus;
    }
}
