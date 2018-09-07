package com.yhglobal.gongxiao.sales.service;

/**
 * 销售单Service(模块内部调用)
 *
 * @Author: 葛灿
 */
public interface SalesOrderServiceInternal {
    /**
     * 当销售单商品的各种数量发生改变（未处理数量、在途数量等）
     * 修改关联销售单的总数量
     *
     * @param salesOrderId 订单号
     * @return
     */
    void updateOrderQuantity(String salesOrderId) throws Exception;
}
