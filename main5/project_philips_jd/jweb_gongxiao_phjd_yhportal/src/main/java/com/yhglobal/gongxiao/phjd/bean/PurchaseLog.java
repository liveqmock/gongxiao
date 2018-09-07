package com.yhglobal.gongxiao.phjd.bean;

/**
 * 货品日志
 * @author yuping.lin
 */
public class PurchaseLog {
    private String describe;       //描述
    private String operationTime;       //操作时间
    private String operator;       //操作人

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
