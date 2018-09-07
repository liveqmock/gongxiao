package com.yhglobal.gongxiao.sales.model.bo;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.dto.SalesOrderCount;

import java.io.Serializable;
import java.util.List;

/**
 * 销售单信息BO对象
 * 用于 销售订单--订单管理 页面
 *
 * @Author: 葛灿
 */
public class SalesOrderList implements Serializable {

    /**
     * 销售单信息
     */
    private PageInfo<SalesOrder> salesOrders;
    /**
     * 返回数量map
     */
    private List<SalesOrderCount> countMap;
    private int totalNumbers;

    public List<SalesOrderCount> getCountMap() {
        return countMap;
    }

    public void setCountMap(List<SalesOrderCount> countMap) {
        this.countMap = countMap;
    }

    public PageInfo<SalesOrder> getSalesOrders() {
        return salesOrders;
    }

    public void setSalesOrders(PageInfo<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
    }

    public int getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(int totalNumbers) {
        this.totalNumbers = totalNumbers;
    }
}
