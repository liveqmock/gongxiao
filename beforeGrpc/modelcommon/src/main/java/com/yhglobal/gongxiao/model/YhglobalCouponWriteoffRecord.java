package com.yhglobal.gongxiao.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.yhglobal.gongxiao.util.NumberFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *  核销记录类 封装返利确认界面的核销数据
 *  @author  王帅
 * */
public class YhglobalCouponWriteoffRecord implements Serializable {

        private Long  confirmId;
      /**
       *  应收返利ID
       * */
      private long toReceiveCouponId;
    /**
     *  确认金额
     * */
      private long confirmAmount;
      private Double confirmAmountDouble;
      // 差额
      private Double differenceAmountDouble;
    /**
     *  实收金额
     * */
      private long receiptAmount;
    private Double receiptAmountDouble;
    /**
     *  记录的流水ID  关联流水表
     * */
      private String flowNo;
    /**
     *  创建时间
     * */
    @JSONField(format="yyyy年MM月dd日 HH:mm")
    private Date createTime;
    /**
     *  最新更新时间
     * */
      private Date lastUpdateTime;
    /**
     *  实收货币的货比码
     * */
      private String receivedCurrencyCode;
    /**
     *  确认的货币码
     * */
      private String confirmCurrencyCode;
    /**
     *  操作人ID
     * */
      private long createdById;
    /**
     *  操作人姓名
     * */
      private  String createdByName;
      /**
       * 是否回滚  1表示回滚
       * */
      private Integer isRollback;

      @JSONField(format="yyyy年MM月dd日")
      private Date useDate;

      private Long projectId;
      private Integer accountType;

    private Long dataVersion;
    private String accountTypeName;
    private String philipDocumentNo;

    public String getPhilipDocumentNo() {
        return philipDocumentNo;
    }

    public void setPhilipDocumentNo(String philipDocumentNo) {
        this.philipDocumentNo = philipDocumentNo;
    }

    public String getDifferenceAmountStr(){
        return NumberFormat.format(this.differenceAmountDouble,"#,##0.00");
    }
    public String getReceiptAmountStr(){
        return NumberFormat.format(this.receiptAmountDouble,"#,##0.00");
    }
    public String getConfirmAmountStr(){
        return NumberFormat.format(this.confirmAmountDouble,"#,##0.00");
    }


    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public Long getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(Long confirmId) {
        this.confirmId = confirmId;
    }

    public long getToReceiveCouponId() {
        return toReceiveCouponId;
    }

    public void setToReceiveCouponId(long toReceiveCouponId) {
        this.toReceiveCouponId = toReceiveCouponId;
    }

    public long getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(long confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public Double getConfirmAmountDouble() {
        return confirmAmountDouble;
    }

    public void setConfirmAmountDouble(Double confirmAmountDouble) {
        this.confirmAmountDouble = confirmAmountDouble;
    }

    public Double getDifferenceAmountDouble() {
        return differenceAmountDouble;
    }

    public void setDifferenceAmountDouble(Double differenceAmountDouble) {
        this.differenceAmountDouble = differenceAmountDouble;
    }

    public long getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(long receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Double getReceiptAmountDouble() {
        return receiptAmountDouble;
    }

    public void setReceiptAmountDouble(Double receiptAmountDouble) {
        this.receiptAmountDouble = receiptAmountDouble;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
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

    public String getReceivedCurrencyCode() {
        return receivedCurrencyCode;
    }

    public void setReceivedCurrencyCode(String receivedCurrencyCode) {
        this.receivedCurrencyCode = receivedCurrencyCode;
    }

    public String getConfirmCurrencyCode() {
        return confirmCurrencyCode;
    }

    public void setConfirmCurrencyCode(String confirmCurrencyCode) {
        this.confirmCurrencyCode = confirmCurrencyCode;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Integer getIsRollback() {
        return isRollback;
    }

    public void setIsRollback(Integer isRollback) {
        this.isRollback = isRollback;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }
}
