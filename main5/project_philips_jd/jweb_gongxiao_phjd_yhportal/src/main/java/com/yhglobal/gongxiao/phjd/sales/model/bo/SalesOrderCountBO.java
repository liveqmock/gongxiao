package com.yhglobal.gongxiao.phjd.sales.model.bo;

import java.io.Serializable;

/**
 * 订单 各状态的数量
 *
 * @author weizecheng
 * @date 2018/8/22 19:41
 */
public class SalesOrderCountBO implements Serializable {
    /**
     * 订单数量
     */
    private Integer count;

    /**
     * 订单状态
     */
    private Integer salesOrderStatus;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(Integer salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SalecOrderCountBO{");
        sb.append("count=").append(count);
        sb.append(", salesOrderStatus=").append(salesOrderStatus);
        sb.append('}');
        return sb.toString();
    }
}
