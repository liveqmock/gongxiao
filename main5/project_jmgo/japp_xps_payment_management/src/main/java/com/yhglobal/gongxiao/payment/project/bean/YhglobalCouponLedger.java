package com.yhglobal.gongxiao.payment.project.bean;





import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;
import java.util.Date;

public class YhglobalCouponLedger implements Serializable {

    // 采购返利ID
    private Long purchaseCouponLedgerId;
    // 返利状态
    private Byte couponStatus;
    // 返利类型
    private Byte couponType;

    private String couponStatusString;
    private String couponTypeString;

    private String couponModel;
    // 返利率
    private Long couponRatio;
    // 项目ID
    private Long projectId;
    // 项目名称
    private String projectName;
    // 供应商ID
    private Long supplierId;
    // 供应商名称
    private String supplierName;
    // 应收货币编码
    private String currencyCode;
    // 品牌商订单号
    private String supplierOrderNo;
    // 采购单号
    private String purchaseOrderNo;
    // 采购时间
    private Date purchaseTime;
    // 预计返利额度
    private Long estimatedCouponAmount;
    private Double estimatedAmountDouble;
    // 预计上账时间
    private Date estimatedPostingDate;
    // 确认返利额度
    private Long confirmedCouponAmount;
    private Double confirmAmountDouble;
    private Long toBeConfirmAmount;//待确认金额
    private Double toBeConfirmAmountDouble;
    // 实收返利额度
    private Long receivedCouponAmount;
    private Double receiptAmountDouble;
    private Long dataVersion;
    private String confirmRecord; //核销的备注信息
    // 创建时间
    private Date createTime;
    // 最近更新时间
    private Date lastUpdateTime;

    private String tracelog;

    public String getEstimatedAmountDoubleStr(){
        return NumberFormat.format(this.estimatedAmountDouble,"#,##0.00");
    }
    public String getToBeConfirmAmountDoubleStr(){
        return NumberFormat.format(this.toBeConfirmAmountDouble,"#,##0.00");
    }

    public String getCouponStatusString() {
        return couponStatusString;
    }

    public void setCouponStatusString(String couponStatusString) {
        this.couponStatusString = couponStatusString;
    }

    public String getCouponTypeString() {
        return couponTypeString;
    }

    public void setCouponTypeString(String couponTypeString) {
        this.couponTypeString = couponTypeString;
    }

    public String getConfirmRecord() {
        return confirmRecord;
    }

    public void setConfirmRecord(String confirmRecord) {
        this.confirmRecord = confirmRecord;
    }

    public Double getEstimatedAmountDouble() {
        return estimatedAmountDouble;
    }

    public void setEstimatedAmountDouble(Double estimatedAmountDouble) {
        this.estimatedAmountDouble = estimatedAmountDouble;
    }

    public Double getConfirmAmountDouble() {
        return confirmAmountDouble;
    }

    public void setConfirmAmountDouble(Double confirmAmountDouble) {
        this.confirmAmountDouble = confirmAmountDouble;
    }

    public Long getToBeConfirmAmount() {
        return toBeConfirmAmount;
    }

    public void setToBeConfirmAmount(Long toBeConfirmAmount) {
        this.toBeConfirmAmount = toBeConfirmAmount;
    }

    public Double getToBeConfirmAmountDouble() {
        return toBeConfirmAmountDouble;
    }

    public void setToBeConfirmAmountDouble(Double toBeConfirmAmountDouble) {
        this.toBeConfirmAmountDouble = toBeConfirmAmountDouble;
    }

    public Double getReceiptAmountDouble() {
        return receiptAmountDouble;
    }

    public void setReceiptAmountDouble(Double receiptAmountDouble) {
        this.receiptAmountDouble = receiptAmountDouble;
    }

    public Long getPurchaseCouponLedgerId() {
        return purchaseCouponLedgerId;
    }

    public void setPurchaseCouponLedgerId(Long purchaseCouponLedgerId) {
        this.purchaseCouponLedgerId = purchaseCouponLedgerId;
    }

    public Byte getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Byte couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public String getCouponModel() {
        return couponModel;
    }

    public void setCouponModel(String couponModel) {
        this.couponModel = couponModel == null ? null : couponModel.trim();
    }

    public Long getCouponRatio() {
        return couponRatio;
    }

    public void setCouponRatio(Long couponRatio) {
        this.couponRatio = couponRatio;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo == null ? null : supplierOrderNo.trim();
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo == null ? null : purchaseOrderNo.trim();
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Long getEstimatedCouponAmount() {
        return estimatedCouponAmount;
    }

    public void setEstimatedCouponAmount(Long estimatedCouponAmount) {
        this.estimatedCouponAmount = estimatedCouponAmount;
    }

    public Date getEstimatedPostingDate() {
        return estimatedPostingDate;
    }

    public void setEstimatedPostingDate(Date estimatedPostingDate) {
        this.estimatedPostingDate = estimatedPostingDate;
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

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
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
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }

    @Override
    public String toString() {
        return "YhglobalCouponLedger{" +
                "purchaseCouponLedgerId=" + purchaseCouponLedgerId +
                ", couponStatus=" + couponStatus +
                ", couponType=" + couponType +
                ", couponModel='" + couponModel + '\'' +
                ", couponRatio=" + couponRatio +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", supplierOrderNo='" + supplierOrderNo + '\'' +
                ", purchaseOrderNo='" + purchaseOrderNo + '\'' +
                ", purchaseTime=" + purchaseTime +
                ", estimatedCouponAmount=" + estimatedCouponAmount +
                ", estimatedPostingDate=" + estimatedPostingDate +
                ", confirmedCouponAmount=" + confirmedCouponAmount +
                ", receivedCouponAmount=" + receivedCouponAmount +
                ", dataVersion=" + dataVersion +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", tracelog='" + tracelog + '\'' +
                '}';
    }
}