package com.yhglobal.gongxiao.phjd.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王帅
 */
public class GrossProfitItem implements Serializable{
//    yl.confirmStatus, yl.salesOrderNo, yl.purchaseOrderNo, yl.createTime, yl.estimatedGrossProfitAmount, yl.toBeConfirmAmount, " +
//            "yw.confirmAmount, yw.receiptAmount, yw.createTime, yw.useDate, yw.flowNo
    private Long grossProfitId;
    private Integer confirmStatus;
    private String confirmStatusStr;

    private String salesOrderNo;
    private String purchaseOrderNo;

    private Date createTime;
    private BigDecimal estimatedGrossProfitAmount;
    private BigDecimal toBeConfirmAmount;
    private BigDecimal confirmedAmount;
    private BigDecimal receivedAmount;

    private Date useDate;
    private String useDateStr;
    private String flowNo;
    private String ywCreateTime;

    public String getUseDateStr() {
        return useDateStr;
    }

    public void setUseDateStr(String useDateStr) {
        this.useDateStr = useDateStr;
    }

    public Long getGrossProfitId() {
        return grossProfitId;
    }

    public void setGrossProfitId(Long grossProfitId) {
        this.grossProfitId = grossProfitId;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getConfirmStatusStr() {
        return confirmStatusStr;
    }

    public void setConfirmStatusStr(String confirmStatusStr) {
        this.confirmStatusStr = confirmStatusStr;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getEstimatedGrossProfitAmount() {
        return estimatedGrossProfitAmount;
    }

    public void setEstimatedGrossProfitAmount(BigDecimal estimatedGrossProfitAmount) {
        this.estimatedGrossProfitAmount = estimatedGrossProfitAmount;
    }

    public BigDecimal getToBeConfirmAmount() {
        return toBeConfirmAmount;
    }

    public void setToBeConfirmAmount(BigDecimal toBeConfirmAmount) {
        this.toBeConfirmAmount = toBeConfirmAmount;
    }

    public BigDecimal getConfirmedAmount() {
        return confirmedAmount;
    }

    public void setConfirmedAmount(BigDecimal confirmedAmount) {
        this.confirmedAmount = confirmedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getYwCreateTime() {
        return ywCreateTime;
    }

    public void setYwCreateTime(String ywCreateTime) {
        this.ywCreateTime = ywCreateTime;
    }
}
