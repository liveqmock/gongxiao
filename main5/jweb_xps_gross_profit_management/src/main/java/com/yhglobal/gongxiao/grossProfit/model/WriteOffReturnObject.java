package com.yhglobal.gongxiao.grossProfit.model;

/**
 * 核销返回对象
 *
 * @author 葛灿
 */
public class WriteOffReturnObject {
    /**
     * 交易是否成功
     */
    private boolean success;
    private int returnCode;
    /**
     * 流水号
     */
    private String flowNo;
    /**
     * 交易前余额
     */
    private long amountBeforeTrans;
    /**
     * 交易后余额
     */
    private long amountAfterTrans;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public long getAmountBeforeTrans() {
        return amountBeforeTrans;
    }

    public void setAmountBeforeTrans(long amountBeforeTrans) {
        this.amountBeforeTrans = amountBeforeTrans;
    }

    public long getAmountAfterTrans() {
        return amountAfterTrans;
    }

    public void setAmountAfterTrans(long amountAfterTrans) {
        this.amountAfterTrans = amountAfterTrans;
    }
}
