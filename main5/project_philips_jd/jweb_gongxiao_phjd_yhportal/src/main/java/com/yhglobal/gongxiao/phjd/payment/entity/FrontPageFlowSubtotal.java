package com.yhglobal.gongxiao.phjd.payment.entity;


import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;

/**
 * 流水页面点击“查询详情” 对应的模型
 *
 * @author: 葛灿
 */
public class FrontPageFlowSubtotal implements Serializable {
    /**
     * 收入条数
     */
    private int incomeQuantity = 0;
    /**
     * 支出条数
     */
    private int expenditureQuantity = 0;
    /**
     * 收入金额
     */
    private double incomeAmount = 0.0;
    /**
     * 支出金额
     */
    private double expenditureAmount = 0.0;

    public String getIncomeAmountStr(){
        return NumberFormat.format(this.incomeAmount,"#,##0.00");
    }
    public String getExpenditureAmountStr(){
        return NumberFormat.format(this.expenditureAmount,"#,##0.00");
    }
    public int getIncomeQuantity() {
        return incomeQuantity;
    }

    public void setIncomeQuantity(int incomeQuantity) {
        this.incomeQuantity = incomeQuantity;
    }

    public int getExpenditureQuantity() {
        return expenditureQuantity;
    }

    public void setExpenditureQuantity(int expenditureQuantity) {
        this.expenditureQuantity = expenditureQuantity;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public double getExpenditureAmount() {
        return expenditureAmount;
    }

    public void setExpenditureAmount(double expenditureAmount) {
        this.expenditureAmount = expenditureAmount;
    }
}
