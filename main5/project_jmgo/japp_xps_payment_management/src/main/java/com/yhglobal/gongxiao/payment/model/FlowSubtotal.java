package com.yhglobal.gongxiao.payment.model;

import java.io.Serializable;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 10:39 2018/5/23
 */
public class FlowSubtotal implements Serializable {
    private Integer recordType;
    private Integer count;
    private Long amountCount;

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getAmountCount() {
        return amountCount;
    }

    public void setAmountCount(Long amountCount) {
        this.amountCount = amountCount;
    }
}
