package com.yhglobal.gongxiao.phjd.sales.model;

import java.io.Serializable;

/**
 * 销售合同号索引表 (如果引入缓存 这里可以考虑缓存 减少数据库压力)
 *
 * @author weizecheng
 * @date 2018/8/30 12:26
 */
public class SalesContractNumberIndex implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 索引号
     */
    private Integer indexNo;

    /**
     * 上次更新的日期
     */
    private String lastUpdateDate;

    /**
     * 数据版本
     */
    private Long dataVersion;

    /**
     * 项目id
     */
    private String projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 索引号
     * @return indexNo 索引号
     */
    public Integer getIndexNo() {
        return indexNo;
    }

    /**
     * 索引号
     * @param indexNo 索引号
     */
    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    /**
     * 上次更新的日期
     * @return lastUpdateDate 上次更新的日期
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * 上次更新的日期
     * @param lastUpdateDate 上次更新的日期
     */
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate == null ? null : lastUpdateDate.trim();
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
     * 项目id
     * @return projectId 项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目id
     * @param projectId 项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }
}