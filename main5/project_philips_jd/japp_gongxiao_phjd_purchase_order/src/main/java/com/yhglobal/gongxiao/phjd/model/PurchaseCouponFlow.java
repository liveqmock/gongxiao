package com.yhglobal.gongxiao.phjd.model;

import java.io.Serializable;
import java.util.Date;

/**
 *  封装返利确认界面数据
 * */
public class PurchaseCouponFlow implements Serializable{
    /**
     * 流水号ID
     * */

    private Long purchaseCouponFlowId;
    // 供应商ID
    private long supplierId;
    // 项目ID
    private Long projectId;
    // 项目名称
    private String projectName;

    // 返利ID
    private Long purchaseCouponLedgerId;
    // 实收货币编码
    private String receivedCurrencyCode;
    // 确认返利额度
    private Long confirmedCouponAmount;
    private Double confirmedCouponAmountDouble;
    // 实收返利额度
    private Long receivedCouponAmount;
    private Double receivedCouponAmountDouble;
    // 差额
    private Long differenceAmount;
    private Double differenceAmountDouble;
    // 确认转入日期
    private Date transferDate;
    // 转入流水号
    private String transferFlowId;
    // 确认日期
    private String confirmDate;
    // 开始日期
    private Date startDate;
    // 结束日期
    private Date endDate;
    // 关联的采购单号
    private String sourcePurchaseOrderNo;
    //
    private Byte syncToCouponCentralFlag;
    // 返利中心流水ID
    private String couponCenterFlowId;
    // 数据版本
    private Long dataVersion;
    // 创建人ID
    private Long createdById;
    // 创建人名称
    private String createdByName;
    // 创建时间
    private Date createTime;
    // 最新更新时间
    private Date lastUpdateTime;

    /* 新增的字段 转入方式  和 差额调整方式  确认货币编码
    * */
    // 封装使用账户数据
    private String transferPattern;
    // 差额调整方式
    private String differenceAmountAdjustPattern;
    // 确认货币编码
    private String confirmCurrencyCode;
    // 返利类型
    private String couponType;
    // 确认状态
    private String confirmStatus;
    // 未收额度
    private long notReceivedAmount;
    private Double notReceivedAmountDouble;
    // 预计应收额度
    private long estimatedCouponAmount;
    private Double estimatedCouponAmountDouble;
    // 预计应收货币编码
    private String estimatedCurrencyCode;

    public Double getConfirmedCouponAmountDouble() {
        return confirmedCouponAmountDouble;
    }

    public void setConfirmedCouponAmountDouble(Double confirmedCouponAmountDouble) {
        this.confirmedCouponAmountDouble = confirmedCouponAmountDouble;
    }

    public Double getReceivedCouponAmountDouble() {
        return receivedCouponAmountDouble;
    }

    public void setReceivedCouponAmountDouble(Double receivedCouponAmountDouble) {
        this.receivedCouponAmountDouble = receivedCouponAmountDouble;
    }

    public Double getDifferenceAmountDouble() {
        return differenceAmountDouble;
    }

    public void setDifferenceAmountDouble(Double differenceAmountDouble) {
        this.differenceAmountDouble = differenceAmountDouble;
    }

    public Double getNotReceivedAmountDouble() {
        return notReceivedAmountDouble;
    }

    public void setNotReceivedAmountDouble(Double notReceivedAmountDouble) {
        this.notReceivedAmountDouble = notReceivedAmountDouble;
    }

    public Double getEstimatedCouponAmountDouble() {
        return estimatedCouponAmountDouble;
    }

    public void setEstimatedCouponAmountDouble(Double estimatedCouponAmountDouble) {
        this.estimatedCouponAmountDouble = estimatedCouponAmountDouble;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getEstimatedCouponAmount() {
        return estimatedCouponAmount;
    }

    public void setEstimatedCouponAmount(long estimatedCouponAmount) {
        this.estimatedCouponAmount = estimatedCouponAmount;
    }

    public String getEstimatedCurrencyCode() {
        return estimatedCurrencyCode;
    }

    public void setEstimatedCurrencyCode(String estimatedCurrencyCode) {
        this.estimatedCurrencyCode = estimatedCurrencyCode;
    }

    public Long getPurchaseCouponFlowId() {
        return purchaseCouponFlowId;
    }

    public void setPurchaseCouponFlowId(Long purchaseCouponFlowId) {
        this.purchaseCouponFlowId = purchaseCouponFlowId;
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
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Long getPurchaseCouponLedgerId() {
        return purchaseCouponLedgerId;
    }

    public void setPurchaseCouponLedgerId(Long purchaseCouponLedgerId) {
        this.purchaseCouponLedgerId = purchaseCouponLedgerId;
    }

    public String getReceivedCurrencyCode() {
        return receivedCurrencyCode;
    }

    public void setReceivedCurrencyCode(String receivedCurrencyCode) {
        this.receivedCurrencyCode = receivedCurrencyCode == null ? null : receivedCurrencyCode.trim();
    }

    public Long getConfirmedCouponAmount() {
        return confirmedCouponAmount;
    }

    public void setConfirmedCouponAmount(Long confirmedCouponAmount) {
        this.confirmedCouponAmount = confirmedCouponAmount;
    }

    public Long getReceivedCouponAmount() {
        return receivedCouponAmount;
    }

    public void setReceivedCouponAmount(Long receivedCouponAmount) {
        this.receivedCouponAmount = receivedCouponAmount;
    }

    public Long getDifferenceAmount() {
        return differenceAmount;
    }

    public void setDifferenceAmount(Long differenceAmount) {
        this.differenceAmount = differenceAmount;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferFlowId() {
        return transferFlowId;
    }

    public void setTransferFlowId(String transferFlowId) {
        this.transferFlowId = transferFlowId == null ? null : transferFlowId.trim();
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
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

    public String getSourcePurchaseOrderNo() {
        return sourcePurchaseOrderNo;
    }

    public void setSourcePurchaseOrderNo(String sourcePurchaseOrderNo) {
        this.sourcePurchaseOrderNo = sourcePurchaseOrderNo == null ? null : sourcePurchaseOrderNo.trim();
    }

    public Byte getSyncToCouponCentralFlag() {
        return syncToCouponCentralFlag;
    }

    public void setSyncToCouponCentralFlag(Byte syncToCouponCentralFlag) {
        this.syncToCouponCentralFlag = syncToCouponCentralFlag;
    }

    public String getCouponCenterFlowId() {
        return couponCenterFlowId;
    }

    public void setCouponCenterFlowId(String couponCenterFlowId) {
        this.couponCenterFlowId = couponCenterFlowId == null ? null : couponCenterFlowId.trim();
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
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

    public String getTransferPattern() { return transferPattern; }

    public void setTransferPattern(String transferPattern) { this.transferPattern = transferPattern; }

    public String getDifferenceAmountAdjustPattern() { return differenceAmountAdjustPattern; }

    public void setDifferenceAmountAdjustPattern(String differenceAmountAdjustPattern) { this.differenceAmountAdjustPattern = differenceAmountAdjustPattern; }

    public String getConfirmCurrencyCode() { return confirmCurrencyCode; }

    public void setConfirmCurrencyCode(String confirmCurrencyCode) { this.confirmCurrencyCode = confirmCurrencyCode; }

    public String getCouponType() { return couponType; }

    public void setCouponType(String couponType) { this.couponType = couponType; }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public long getNotReceivedAmount() {
        return notReceivedAmount;
    }

    public void setNotReceivedAmount(long notReceivedAmount) {
        this.notReceivedAmount = notReceivedAmount;
    }

    @Override
    public String toString() {
        return "PurchaseCouponFlow{" +
                "purchaseCouponFlowId=" + purchaseCouponFlowId +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", purchaseCouponLedgerId=" + purchaseCouponLedgerId +
                ", receivedCurrencyCode='" + receivedCurrencyCode + '\'' +
                ", confirmedCouponAmount=" + confirmedCouponAmount +
                ", receivedCouponAmount=" + receivedCouponAmount +
                ", differenceAmount=" + differenceAmount +
                ", transferDate=" + transferDate +
                ", transferFlowId='" + transferFlowId + '\'' +
                ", confirmDate=" + confirmDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", sourcePurchaseOrderNo='" + sourcePurchaseOrderNo + '\'' +
                ", syncToCouponCentralFlag=" + syncToCouponCentralFlag +
                ", couponCenterFlowId='" + couponCenterFlowId + '\'' +
                ", dataVersion=" + dataVersion +
                ", createdById=" + createdById +
                ", createdByName='" + createdByName + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", transferPattern=" + transferPattern +
                ", differenceAmountAdjustPattern=" + differenceAmountAdjustPattern +
                ", confirmCurrencyCode=" + confirmCurrencyCode +
                ", confirmStatus=" + confirmStatus +
                '}';
    }
}