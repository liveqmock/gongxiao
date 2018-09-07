package com.yhglobal.gongxiao.payment.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yhglobal.gongxiao.constant.payment.AccountType;

import java.util.Date;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 16:01 2018/7/17
 */
public class YhglobalPrepaidWriteoffRecord {
    private String flowCode;
    private Double confirmAmountDouble;
    private Double receiptAmountDouble;
    @JSONField(format="yyyy年MM月dd日")
    private Date useDate;
    private Integer accountType;
    private String philipNo;
    @JSONField(format="yyyy年MM月dd日 HH:mm")
    private Date createTime;
    private String createdByName;

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getAccountTypeStr(){
        return AccountType.getMessageByCode(this.accountType);
    }
    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public Double getConfirmAmountDouble() {
        return confirmAmountDouble;
    }

    public void setConfirmAmountDouble(Double confirmAmountDouble) {
        this.confirmAmountDouble = confirmAmountDouble;
    }

    public Double getReceiptAmountDouble() {
        return receiptAmountDouble;
    }

    public void setReceiptAmountDouble(Double receiptAmountDouble) {
        this.receiptAmountDouble = receiptAmountDouble;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getPhilipNo() {
        return philipNo;
    }

    public void setPhilipNo(String philipNo) {
        this.philipNo = philipNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
