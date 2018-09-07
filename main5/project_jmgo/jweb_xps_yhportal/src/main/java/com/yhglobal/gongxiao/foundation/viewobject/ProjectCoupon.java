package com.yhglobal.gongxiao.foundation.viewobject;

import java.io.Serializable;
import java.util.Date;

/**
 * t_project_coupon
 */
public class ProjectCoupon implements Serializable {

    private Long rowId;
    /**
     * 返利状态 0:初始化待启用 1:正常使用 7:已取消 8:已过期 9:已删除
     */
    private Byte Status;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 返利种类id
     */
    private Integer couponCategoryId;
    /**
     * 返利类型 0:未定义 1:月度返利 2:季度返利 3:年度返利 4:一次性现金返利
     */
    private Byte couponType;
    /**
     * 返利种类名称
     */
    private String couponCategoryName;
    /**
     * 返利比例（原返利比例乘以10000）
     */
    private Integer couponRatio;
    /**
     * 有效期起
     */
    private Date startDate;
    /**
     * 有效期止
     */
    private Date endDate;
    /**
     * createTime
     */
    private Date createTime;

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public Byte getStatus() {
        return Status;
    }

    public void setStatus(Byte status) {
        Status = status;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getCouponCategoryId() {
        return couponCategoryId;
    }

    public void setCouponCategoryId(Integer couponCategoryId) {
        this.couponCategoryId = couponCategoryId;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public String getCouponCategoryName() {
        return couponCategoryName;
    }

    public void setCouponCategoryName(String couponCategoryName) {
        this.couponCategoryName = couponCategoryName;
    }

    public Integer getCouponRatio() {
        return couponRatio;
    }

    public void setCouponRatio(Integer couponRatio) {
        this.couponRatio = couponRatio;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
