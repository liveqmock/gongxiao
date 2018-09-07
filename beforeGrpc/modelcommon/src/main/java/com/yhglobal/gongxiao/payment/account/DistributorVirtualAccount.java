package com.yhglobal.gongxiao.payment.account;

import java.io.Serializable;
import java.util.Date;

/**
 * 经销商在越海供销平台的虚拟支付账户
 *
 * @Author: 葛灿
 * @Date:2018/1/30--17:48
 */
public class DistributorVirtualAccount implements Serializable {

    /**
     * 主键id
     */
    private long rowId;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 经销商Id
     */
    private long distributorId;
    /**
     * 经销商名称
     */
    private String distributorName;
    /**
     * 客户账户
     */
    private String clientAccount;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 账户状态 0:初始化未启用 1:正常使用 8:已冻结 9:已废弃
     */
    private int status;
    /**
     * 返利余额
     */
    private long totalCouponAmount;
    /**
     * 返利余额（浮点数）
     */
    private Double totalCouponAmountDouble;
    /**
     * 返利是否冻结
     */
    private boolean isCouponFrozen;
    /**
     * 代垫余额
     */
    private long totalPrepaidAmount;
    /**
     * 代垫余额（浮点数）
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
    /**
     * 暂扣返利金额
     */
    private long frozenCouponAmount;
    /**
     * 暂扣代垫金额
     */
    private long frozenPrepaidAmount;
    /**
     * 暂扣返利信息
     */
    private String frozenCouponInfo;
    /**
     * 暂扣代垫信息
     */
    private String frozenPrepaidInfo;

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
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

    public long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(long distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public Double getTotalCouponAmountDouble() {
        return totalCouponAmountDouble;
    }

    public void setTotalCouponAmountDouble(Double totalCouponAmountDouble) {
        this.totalCouponAmountDouble = totalCouponAmountDouble;
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

    public Double getTotalPrepaidAmountDouble() {
        return totalPrepaidAmountDouble;
    }

    public void setTotalPrepaidAmountDouble(Double totalPrepaidAmountDouble) {
        this.totalPrepaidAmountDouble = totalPrepaidAmountDouble;
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

    public long getFrozenCouponAmount() {
        return frozenCouponAmount;
    }

    public void setFrozenCouponAmount(long frozenCouponAmount) {
        this.frozenCouponAmount = frozenCouponAmount;
    }

    public long getFrozenPrepaidAmount() {
        return frozenPrepaidAmount;
    }

    public void setFrozenPrepaidAmount(long frozenPrepaidAmount) {
        this.frozenPrepaidAmount = frozenPrepaidAmount;
    }

    public String getFrozenCouponInfo() {
        return frozenCouponInfo;
    }

    public void setFrozenCouponInfo(String frozenCouponInfo) {
        this.frozenCouponInfo = frozenCouponInfo;
    }

    public String getFrozenPrepaidInfo() {
        return frozenPrepaidInfo;
    }

    public void setFrozenPrepaidInfo(String frozenPrepaidInfo) {
        this.frozenPrepaidInfo = frozenPrepaidInfo;
    }
}