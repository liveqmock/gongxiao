package com.yhglobal.gongxiao.sales.bo;


import com.yhglobal.gongxiao.sales.constant.SalesReturnStatus;

import java.io.Serializable;

/**
 * @Description: 销售退货查询页统计
 * @author: LUOYI
 * @Date: Created in 15:09 2018/5/29
 */
public class SalesReturnClassificationCount implements Serializable {
    public Integer status;

    public Integer count;

    public String getStatusStr(){
        return SalesReturnStatus.getMessageByCode(this.status);
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

