package com.yhglobal.gongxiao.sales.service;

import com.yhglobal.gongxiao.sales.model.SalesOrderItem;

import java.util.List;

/**
 * 销售单商品详情Service
 *
 * @Author: 葛灿
 */
public interface SalesOrderItemService {


//    /**
//     * 更改销售单中某个商品单详情
//     *
//     * @param id                      主键
//     * @param supplierDiscountPercent 品牌商支持折扣
//     * @param yhDiscountPercent       越海代垫百分比
//     * @return int
//     */
//    int updateSalesOrder(Long id, Integer supplierDiscountPercent, Integer yhDiscountPercent);

    /**
     * 批量插入
     *
     * @param orderNo 销售单号
     * @param list    批量商品对象
     * @return
     */
    int batchInsertSalesItems(String orderNo, List<SalesOrderItem> list);
}
