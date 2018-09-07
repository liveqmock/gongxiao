package com.yhglobal.gongxiao.payment.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.yhglobal.gongxiao.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用流水返回模型
 *
 * @author: 葛灿
 */
public class FrontPageFlow implements Serializable {

    /**
     * 流水号，把主键id+"" 放到该字段
     */
    private String flowNo;
    /**
     * 交易类型 收入/支出
     */

    private int type;

    private String typeStr;
    /**
     * 货币编码
     */
    private String currencyCode;
    /**
     * 交易金额 原有金额/1000.0
     */
    private double transactionAmount;

    private String transactionAmountStr;
    /**
     * 交易后账户余额 原有金额/100.0
     */
    private double amountAfterTransaction;

    private String amountAfterTransactionStr;
    /**
     * 创建时间
     */
    @JSONField(format="yyyy年MM月dd日 HH:mm:ss")
    private Date createTime;
    /**创建人*/
    private String createByName;
    /**
     * 备注
     */
    private String remark;

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public void setTransactionAmountStr(String transactionAmountStr) {
        this.transactionAmountStr = transactionAmountStr;
    }

    public void setAmountAfterTransactionStr(String amountAfterTransactionStr) {
        this.amountAfterTransactionStr = amountAfterTransactionStr;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getTypeStr() {
        return YhGlobalInoutFlowConstant.getMessageByCode(this.type);
    }
    public String getTransactionAmountStr(){
        return NumberFormat.format(this.transactionAmount,"#,##0.00");
    }

    public String getAmountAfterTransactionStr(){
        return NumberFormat.format(this.amountAfterTransaction,"#,##0.00");
    }


    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getAmountAfterTransaction() {
        return amountAfterTransaction;
    }

    public void setAmountAfterTransaction(double amountAfterTransaction) {
        this.amountAfterTransaction = amountAfterTransaction;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
