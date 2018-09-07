package com.yhglobal.gongxiao.payment.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 10:39 2018/5/23
 */
public class FlowSubtotal implements Serializable {
    private Integer flowType;
    private Integer count;
    private BigDecimal amountCount;

    private Integer recordType;

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public BigDecimal getAmountCount() {
        return amountCount;
    }

    public void setAmountCount(BigDecimal amountCount) {
        this.amountCount = amountCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
