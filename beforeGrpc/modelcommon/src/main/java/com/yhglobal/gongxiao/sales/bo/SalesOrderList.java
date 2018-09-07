package com.yhglobal.gongxiao.sales.bo;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.sales.model.SalesOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
    private List<Map<String, Integer>> countMap;
    private int totalNumbers;

    public List<Map<String, Integer>> getCountMap() {
        return countMap;
    }

    public void setCountMap(List<Map<String, Integer>> countMap) {
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
