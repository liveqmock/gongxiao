package com.yhglobal.gongxiao.foundation.viewobject;

import java.io.Serializable;
import java.util.Date;

public class DistributorShippingPreference implements Serializable {
    private String rowId;

    private Long distributorId;

    private String distributorName;

    private int defaultAddressId;

    private int lastSelectedAddressId;

    private Date createTime;

    private Date lastUpdateTime;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId == null ? null : rowId.trim();
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

    public int getDefaultAddressId() {
        return defaultAddressId;
    }

    public void setDefaultAddressId(int defaultAddressId) {
        this.defaultAddressId = defaultAddressId;
    }

    public int getLastSelectedAddressId() {
        return lastSelectedAddressId;
    }

    public void setLastSelectedAddressId(int lastSelectedAddressId) {
        this.lastSelectedAddressId = lastSelectedAddressId;
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