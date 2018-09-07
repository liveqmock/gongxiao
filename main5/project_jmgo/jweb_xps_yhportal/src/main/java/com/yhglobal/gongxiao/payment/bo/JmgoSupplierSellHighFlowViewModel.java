package com.yhglobal.gongxiao.payment.bo;

import com.yhglobal.gongxiao.constant.YhGlobalInoutFlowConstant;

import java.util.Date;

/**
 * @author 葛灿
 */
public class JmgoSupplierSellHighFlowViewModel {

    /**流水号*/
    private String flowNo;
    /**流水类型*/
    private int type;
    /**流水类型*/
     private String typeStr;
    /**货币编码*/
    private String currencyCode;
    /**交易金额*/
    private String transactionAmount;
    /**交易后账户余额*/
    private String amountAfterTransaction;
    /**交易时间*/
    private String transactionTime;
    /**创建时间*/
    private Date createTime;
    /**创建人*/
    private String createByName;
    /**经销商名称*/
    private String distributorName;
    /**备注*/
    private String remark;

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getAmountAfterTransaction() {
        return amountAfterTransaction;
    }

    public void setAmountAfterTransaction(String amountAfterTransaction) {
        this.amountAfterTransaction = amountAfterTransaction;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTypeStr() {
        return YhGlobalInoutFlowConstant.getMessageByCode(this.type);
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
