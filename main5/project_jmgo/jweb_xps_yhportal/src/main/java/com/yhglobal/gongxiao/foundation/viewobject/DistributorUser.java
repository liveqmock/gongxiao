package com.yhglobal.gongxiao.foundation.viewobject;

import java.io.Serializable;
import java.util.Date;

public class DistributorUser implements Serializable {
    private Long distributorUserId;

    private Long distributorId;

    private String distributorName;

    private String account;

    private String password;

    private Byte priority;

    private String roleInfo;

    private Long createdById;

    private String createdByName;

    private Date createTime;

    private Date lastUpdateTime;

    private String tracelog;

    public Long getDistributorUserId() {
        return distributorUserId;
    }

    public void setDistributorUserId(Long distributorUserId) {
        this.distributorUserId = distributorUserId;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public String getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(String roleInfo) {
        this.roleInfo = roleInfo == null ? null : roleInfo.trim();
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName == null ? null : createdByName.trim();
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
}