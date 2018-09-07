package com.yhglobal.gongxiao.sales.model.dto;

/**
 * 销售合同号索引
 *
 * @author 葛灿
 */
public class SalesContractOrderIndex {
    /**
     * 主键id
     */
    private long rowId;
    /**
     * 项目id
     */
    private long projectId;
    /**
     * 索引号
     */
    private int indexNo;
    /**
     * 上次更新的日期
     * yyyyMMdd格式
     */
    private String lastUpdateDate;
    /**
     * 数据版本
     */
    private long dataVersion;

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
        this.dataVersion = dataVersion;
    }
}
