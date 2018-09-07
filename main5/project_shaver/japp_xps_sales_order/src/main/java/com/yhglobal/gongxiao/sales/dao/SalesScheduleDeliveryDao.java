package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesScheduleDeliveryMapper;
import com.yhglobal.gongxiao.sales.model.SalesScheduleDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 销售订单商品表dao
 *
 * @Author: 葛灿
 * @Date:2018/2/28--11:37
 */
@Repository
public class SalesScheduleDeliveryDao {
    @Autowired
    private SalesScheduleDeliveryMapper salesScheduleDeliveryMapper;

    /**
     * 插入预约发货单
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:51
     */
    public int insert(String prefix, SalesScheduleDelivery record) {
        return salesScheduleDeliveryMapper.insert(prefix, record);
    }


}
