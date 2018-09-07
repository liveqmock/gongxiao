package com.yhglobal.gongxiao.payment.project.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.yhglobal.gongxiao.common.ExcelField;
import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 王帅
 */
public class YhglobalCouponLedgerItem implements Serializable {
    // private byte couponStatus;,yl.purchaseOrderNo sourcePurchaseOrderNo ,yl.createTime createTime ,yl.couponType,yl.currencyCode,yl.estimatedCouponAmount,
    //   yl.toBeConfirmAmount,yw.confirmCurrencyCode,yw.confirmAmount,yw.receivedCurrencyCode,yw.receiptAmount,
//    yw.accountType,yw.createTime,yw.flowNo
    private Long purchaseCouponLedgerId;
    @ExcelField(name = "品牌商订单号")
    private String supplierOrderNo;

    private byte couponStatus; //返利确认状态
    @ExcelField(name = "确认状态")
    private String couponStatusString;
    @ExcelField(name = "采购单号")
    private String purchaseOrderNo;  // 采购单号
    @ExcelField(name = "生成日期")
    @JSONField(format="yyyy年MM月dd日")
    private Date ylCreateTime;  // 返利创建时间
    private byte couponType; // 返利类型
    @ExcelField(name = "返利类型")
    private String couponTypeString;
    @ExcelField(name = "应收币种")
    private String ylCurrencyCode; // 返利应收货币码
    private Long estimatedCouponAmount;  // 应收返利额度
    private Double estimatedCouponAmountDouble;  // 给前台展示的应收返利额度浮点数据
    private Long toBeConfirmAmount;  // 待确认额度
    private Double toBeConfirmAmountDouble;  // 待确认额度的浮点数
    @ExcelField(name = "确认币种")
    private String confirmCurrencyCode;  // 确认货比码
    private Long confirmAmount;   // 确认额度
    private Double confirmAmountDouble;  // 确认额度浮点数
    @ExcelField(name = "使用币种")
    private String receivedCurrencyCode;  // 实收货币码
    private Long receiptAmount;  // 实收额度
    private Double receiptAmountDouble;  // 实收额度浮点数
    private Integer accountType;  // 使用账户类型
    @ExcelField(name = "使用账户")
    private String accountTypeString;
    @ExcelField(name = "确认时间")
    @JSONField(format="yyyy年MM月dd日 HH:mm")
    private Date ywCreateTime;  // 记录创建时间
    @ExcelField(name = "确认流水")
    private String flowNo;  // 流水号

    @ExcelField(name = "应收金额")
    private String estimatedCouponAmountStr;
    @ExcelField(name = "未收金额")
    private String toBeConfirmAmountStr;
    @ExcelField(name = "确认金额")
    private String confirmAmountStr;
    @ExcelField(name = "实收金额")
    private String receiptAmountStr;
    @ExcelField(name = "飞利浦单据号")
    private String philipDocumentNo;

    public String getPhilipDocumentNo() {
        return philipDocumentNo;
    }

    public void setPhilipDocumentNo(String philipDocumentNo) {
        this.philipDocumentNo = philipDocumentNo;
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo;
    }

    public void setEstimatedCouponAmountStr(String estimatedCouponAmountStr) {
        this.estimatedCouponAmountStr = estimatedCouponAmountStr;
    }

    public void setToBeConfirmAmountStr(String toBeConfirmAmountStr) {
        this.toBeConfirmAmountStr = toBeConfirmAmountStr;
    }

    public void setConfirmAmountStr(String confirmAmountStr) {
        this.confirmAmountStr = confirmAmountStr;
    }

    public void setReceiptAmountStr(String receiptAmountStr) {
        this.receiptAmountStr = receiptAmountStr;
    }

    public String getEstimatedCouponAmountStr(){   // 应收
        return NumberFormat.format(this.estimatedCouponAmountDouble,"#,##0.00");
    }
    public String getConfirmAmountStr(){   // 确认
        return NumberFormat.format(this.confirmAmountDouble,"#,##0.00");
    }
    public String getToBeConfirmAmountStr() {   // 未收
        return NumberFormat.format(this.toBeConfirmAmountDouble,"#,##0.00");
    }
    public String getReceiptAmountStr(){   // 实收
        return NumberFormat.format(this.receiptAmountDouble,"#,##0.00");
    }

    public String getAccountTypeString() {
        return accountTypeString;
    }

    public void setAccountTypeString(String accountTypeString) {
        this.accountTypeString = accountTypeString;
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

    public Long getPurchaseCouponLedgerId() {
        return purchaseCouponLedgerId;
    }

    public void setPurchaseCouponLedgerId(Long purchaseCouponLedgerId) {
        this.purchaseCouponLedgerId = purchaseCouponLedgerId;
    }

    public byte getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(byte couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public Date getYlCreateTime() {
        return ylCreateTime;
    }

    public void setYlCreateTime(Date ylCreateTime) {
        this.ylCreateTime = ylCreateTime;
    }

    public byte getCouponType() {
        return couponType;
    }

    public void setCouponType(byte couponType) {
        this.couponType = couponType;
    }

    public String getYlCurrencyCode() {
        return ylCurrencyCode;
    }

    public void setYlCurrencyCode(String ylCurrencyCode) {
        this.ylCurrencyCode = ylCurrencyCode;
    }

    public Long getEstimatedCouponAmount() {
        return estimatedCouponAmount;
    }

    public void setEstimatedCouponAmount(Long estimatedCouponAmount) {
        this.estimatedCouponAmount = estimatedCouponAmount;
    }

    public Double getEstimatedCouponAmountDouble() {
        return estimatedCouponAmountDouble;
    }

    public void setEstimatedCouponAmountDouble(Double estimatedCouponAmountDouble) {
        this.estimatedCouponAmountDouble = estimatedCouponAmountDouble;
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

    public String getConfirmCurrencyCode() {
        return confirmCurrencyCode;
    }

    public void setConfirmCurrencyCode(String confirmCurrencyCode) {
        this.confirmCurrencyCode = confirmCurrencyCode;
    }

    public Long getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(Long confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public Double getConfirmAmountDouble() {
        return confirmAmountDouble;
    }

    public void setConfirmAmountDouble(Double confirmAmountDouble) {
        this.confirmAmountDouble = confirmAmountDouble;
    }

    public String getReceivedCurrencyCode() {
        return receivedCurrencyCode;
    }

    public void setReceivedCurrencyCode(String receivedCurrencyCode) {
        this.receivedCurrencyCode = receivedCurrencyCode;
    }

    public Long getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Long receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Double getReceiptAmountDouble() {
        return receiptAmountDouble;
    }

    public void setReceiptAmountDouble(Double receiptAmountDouble) {
        this.receiptAmountDouble = receiptAmountDouble;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Date getYwCreateTime() {
        return ywCreateTime;
    }

    public void setYwCreateTime(Date ywCreateTime) {
        this.ywCreateTime = ywCreateTime;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }
}
