package com.yhglobal.gongxiao;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 基础Model
 * @Author: LUOYI
 * @Date: Created in 17:13 2018/3/21
 */
public class BaseModel implements Serializable {
    private long createdById;
    private Long dataVersion;
    private String tracelog;
    private Date createTime;
    private Date lastUpdateTime;
    private String createdByName;

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
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
