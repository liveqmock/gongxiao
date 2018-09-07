package com.yhglobal.gongxiao.base.model;

import com.yhglobal.gongxiao.common.ExcelField;

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
    @ExcelField(name = "确认状态")
    private String confirmStatusStr;

    @ExcelField(name = "销售单号")
    private String salesOrderNo;
    @ExcelField(name = "采购订单号")
    private String purchaseOrderNo;

    private Date createTime;
    @ExcelField(name = "应收金额")
    private BigDecimal estimatedGrossProfitAmount;
    @ExcelField(name = "未收金额")
    private BigDecimal toBeConfirmAmount;
    @ExcelField(name = "确认金额")
    private BigDecimal confirmedAmount;
    @ExcelField(name = "实收金额")
    private BigDecimal receivedAmount;

    private Date useDate;
    @ExcelField(name = "转入日期")
    private String useDateStr;
    @ExcelField(name = "确认流水号")
    private String flowNo;
    @ExcelField(name = "确认时间")
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
