package com.yhglobal.gongxiao.payment.account;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商在越海供销平台的现金支付账户
 *
 * @Author: 葛灿
 * @Date:2018/1/30--17:48
 */
public class SupplierVirtualAccount implements Serializable {

    /**
     * 供应商Id
     */
    private long supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 账户状态 0:初始化未启用 1:正常使用 8:已冻结 9:已废弃
     */
    private int status;
    /**
     * 返利余额
     */
    private long couponTotalAmount;
    /**
     * 返利是否冻结
     */
    private boolean isCouponFrozen;
    /**
     * 代垫余额
     */
    private long prepaidTotalAmount;
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

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCouponTotalAmount() {
        return couponTotalAmount;
    }

    public void setCouponTotalAmount(long couponTotalAmount) {
        this.couponTotalAmount = couponTotalAmount;
    }

    public boolean isCouponFrozen() {
        return isCouponFrozen;
    }

    public void setCouponFrozen(boolean couponFrozen) {
        isCouponFrozen = couponFrozen;
    }

    public long getPrepaidTotalAmount() {
        return prepaidTotalAmount;
    }

    public void setPrepaidTotalAmount(long prepaidTotalAmount) {
        this.prepaidTotalAmount = prepaidTotalAmount;
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
}