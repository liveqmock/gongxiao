package com.yhglobal.gongxiao.sales.service.impl;


import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.service.SalesOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售单商品详情Service实现类
 *
 * @Author: 葛灿
 */
@Service
public class SalesOrderItemServiceImpl implements SalesOrderItemService {

    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalesOrderDao salesOrderDao;


    @Override
    public int batchInsertSalesItems(String orderNo, List<SalesOrderItem> list) {
        int rows = 0;
        //逐条存储
        for (SalesOrderItem record : list) {
            record.setSalesOrderNo(orderNo);
            rows += salesOrderItemDao.insert(record);
        }
        return rows;
    }

}
