package com.yhglobal.gongxiao.sales.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.service.SalesOrderServiceInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * 销售单Service实现(模块内部调用)
 *
 * @Author: 葛灿
 */
@Service
public class SalesOrderServiceInternalImpl implements SalesOrderServiceInternal {

    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOrderItemDao salesOrderItemDao;

    @Override
    public void updateOrderQuantity(String salesOrderId) throws Exception {
        //找到销售单
        SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderId);
        //通过销售单号找到商品列表
        List<SalesOrderItem> salesOrderItemList = salesOrderItemDao.selectListBySalesOrderNo(salesOrderId);
        //总件数求和
        int totalQuantity = 0;
        int deliveredQuantity = 0;
        int inTransitQuantity = 0;
        int canceledQuantity = 0;
        int returnedQuantity = 0;
        int unhandledQuantity = 0;
        HashSet<String> onGoingOutboundOrdersSet = new HashSet<>();
        HashSet<String> finishedOutboundOrdersSet = new HashSet<>();
        for (SalesOrderItem record : salesOrderItemList) {
            totalQuantity += record.getTotalQuantity();
            deliveredQuantity += record.getDeliveredQuantity();
            inTransitQuantity += record.getInTransitQuantity();
            canceledQuantity += record.getCanceledQuantity();
            returnedQuantity += record.getReturnedQuantity();
            unhandledQuantity += record.getUnhandledQuantity();
            String ongoingOutboundOrderInfo = record.getOngoingOutboundOrderInfo();
            List<String> onGoingOutboundOrderNos = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
            for (String outboundOrderNo : onGoingOutboundOrderNos) {
                onGoingOutboundOrdersSet.add(outboundOrderNo);
            }
            String finishedOutboundOrderInfo = record.getFinishedOutboundOrderInfo();
            List<String> finishedOutBoundOrderNos = JSON.parseArray(finishedOutboundOrderInfo, String.class);
            for (String outboundOrderNo : finishedOutBoundOrderNos) {
                finishedOutboundOrdersSet.add(outboundOrderNo);
            }
        }
        //填入数据
        salesOrder.setTotalQuantity(totalQuantity);
        salesOrder.setDeliveredQuantity(deliveredQuantity);
        salesOrder.setInTransitQuantity(inTransitQuantity);
        salesOrder.setCanceledQuantity(canceledQuantity);
        salesOrder.setReturnedQuantity(returnedQuantity);
        salesOrder.setUnhandledQuantity(unhandledQuantity);
        salesOrder.setOngoingOutboundOrderInfo(JSON.toJSONString(onGoingOutboundOrdersSet));
        salesOrder.setFinishedOutboundOrderInfo(JSON.toJSONString(finishedOutboundOrdersSet));
        //更新数据库
        int update = salesOrderDao.update(salesOrder);
    }
}
