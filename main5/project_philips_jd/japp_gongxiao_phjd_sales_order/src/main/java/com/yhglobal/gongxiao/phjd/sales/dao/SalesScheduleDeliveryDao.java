package com.yhglobal.gongxiao.phjd.sales.dao;

import com.yhglobal.gongxiao.phjd.sales.dao.mapper.SalesScheduleDeliveryMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesScheduleDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 销售出库单
 *
 * @author weizecheng
 * @date 2018/8/29 11:33
 */
@Repository
public class SalesScheduleDeliveryDao {

    @Autowired
    private SalesScheduleDeliveryMapper salesScheduleDeliveryMapper;

    /**
     * 插入销售出库表
     *
     * @author weizecheng
     * @date 2018/8/29 11:44
     * @param prefix 表前缀
     * @param record  表对象
     * @return
     */
    public int insertSalesScheduleDelivery(String prefix, SalesScheduleDelivery record){
       return  salesScheduleDeliveryMapper.insert(prefix, record);
    }

}
