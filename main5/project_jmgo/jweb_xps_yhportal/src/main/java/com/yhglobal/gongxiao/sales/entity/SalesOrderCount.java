package com.yhglobal.gongxiao.sales.entity;


import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;

/**
 * @author 葛灿
 */
public class SalesOrderCount {
    public Integer status;

    public Integer count;

    public String getStatusStr() {
        return SalesOrderStatusEnum.getMessage(this.status);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
