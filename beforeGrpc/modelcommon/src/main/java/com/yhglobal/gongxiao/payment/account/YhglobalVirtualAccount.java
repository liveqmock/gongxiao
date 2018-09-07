package com.yhglobal.gongxiao.payment.account;

import java.io.Serializable;
import java.util.Date;

/**
 * 越海在各项目的虚拟支付账户
 *
 * @Author: 葛灿
 * @Date:2018/1/30--17:48
 */
public class YhglobalVirtualAccount implements Serializable {
    /**
     * 主键id
     */
    private long rowId;
    /**
     * 项目id
     */
    private long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 供应商Id
     */
    private long supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 账户状态 0:初始化未启用 1:正常使用 8:已冻结 9:已废弃
     */
    private int status;
    /**
     * 返利总额
     */
    private long totalCouponAmount;
    /**
     * 返利总额(浮点数)
     */
    private Double totalCouponAmountDouble;
    /**
     * 返利是否冻结
     */
    private boolean isCouponFrozen;
    /**
     * 代垫总额
     */
    private long totalPrepaidAmount;
    /**
     * 代垫总额(浮点数)
     */
    private Double totalPrepaidAmountDouble;
    /**
     * 代垫是否冻结
     */
    private boolean isPrepaidFrozen;
    /**
     * 数据版本
     */
    private long dataVersion;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTotalCouponAmount() {
        return totalCouponAmount;
    }

    public void setTotalCouponAmount(long totalCouponAmount) {
        this.totalCouponAmount = totalCouponAmount;
    }

    public boolean isCouponFrozen() {
        return isCouponFrozen;
    }

    public void setCouponFrozen(boolean couponFrozen) {
        isCouponFrozen = couponFrozen;
    }

    public long getTotalPrepaidAmount() {
        return totalPrepaidAmount;
    }

    public void setTotalPrepaidAmount(long totalPrepaidAmount) {
        this.totalPrepaidAmount = totalPrepaidAmount;
    }

    public boolean isPrepaidFrozen() {
        return isPrepaidFrozen;
    }

    public void setPrepaidFrozen(boolean prepaidFrozen) {
        isPrepaidFrozen = prepaidFrozen;
    }

    public long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(long dataVersion) {
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
        this.tracelog = tracelog;
    }

    public Double getTotalCouponAmountDouble() {
        return totalCouponAmountDouble;
    }

    public void setTotalCouponAmountDouble(Double totalCouponAmountDouble) {
        this.totalCouponAmountDouble = totalCouponAmountDouble;
    }

    public Double getTotalPrepaidAmountDouble() {
        return totalPrepaidAmountDouble;
    }

    public void setTotalPrepaidAmountDouble(Double totalPrepaidAmountDouble) {
        this.totalPrepaidAmountDouble = totalPrepaidAmountDouble;
    }
}