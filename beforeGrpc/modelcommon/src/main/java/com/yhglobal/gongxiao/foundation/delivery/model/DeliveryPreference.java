package com.yhglobal.gongxiao.foundation.delivery.model;

import java.io.Serializable;
import java.util.Date;

public class DeliveryPreference implements Serializable{
    private Long preferenceId;

    private Long targetProviceId;

    private String targetProviceName;

    private Byte preferenceStatus;

    private Integer preferredWarehouseId;

    private String preferredWarehouseName;

    private Date createTime;

    private Date lastUpdateTime;

    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public Long getTargetProviceId() {
        return targetProviceId;
    }

    public void setTargetProviceId(Long targetProviceId) {
        this.targetProviceId = targetProviceId;
    }

    public String getTargetProviceName() {
        return targetProviceName;
    }

    public void setTargetProviceName(String targetProviceName) {
        this.targetProviceName = targetProviceName == null ? null : targetProviceName.trim();
    }

    public Byte getPreferenceStatus() {
        return preferenceStatus;
    }

    public void setPreferenceStatus(Byte preferenceStatus) {
        this.preferenceStatus = preferenceStatus;
    }

    public Integer getPreferredWarehouseId() {
        return preferredWarehouseId;
    }

    public void setPreferredWarehouseId(Integer preferredWarehouseId) {
        this.preferredWarehouseId = preferredWarehouseId;
    }

    public String getPreferredWarehouseName() {
        return preferredWarehouseName;
    }

    public void setPreferredWarehouseName(String preferredWarehouseName) {
        this.preferredWarehouseName = preferredWarehouseName == null ? null : preferredWarehouseName.trim();
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