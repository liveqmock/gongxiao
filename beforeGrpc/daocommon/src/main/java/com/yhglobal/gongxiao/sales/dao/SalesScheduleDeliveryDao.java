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
     * 根据销售单号查询
     *
     * @param orderNo 销售单号
     * @return SalesScheduleDelivery
     * @author 葛灿
     * @date 2018/2/28 11:43
     */
    public List<SalesScheduleDelivery> selectListBySalesOrderNo(String orderNo) {
        return salesScheduleDeliveryMapper.selectListBySalesOrderNo(orderNo);
    }

    /**
     * 更新预约发货单信息
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:49
     */
    public int update(SalesScheduleDelivery record) {
        return salesScheduleDeliveryMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    /**
     * 插入预约发货单
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:51
     */
    public int insert(SalesScheduleDelivery record) {
        return salesScheduleDeliveryMapper.insert(record);
    }


    /**
     * 根据出库单号查询
     *
     * @param outboundOrderNo 出库单号
     * @return
     */
    public SalesScheduleDelivery getSalesScheduleDeliveryByOutboundOrderNo(String outboundOrderNo) {
        return salesScheduleDeliveryMapper.getSalesScheduleDeliveryByOutboundOrderNo(outboundOrderNo);
    }

    /**
     * 通过唯一号查询
     * @param uniqueNumber 唯一号
     * @return
     */
    public SalesScheduleDelivery getSalesScheduleDeliveryByUniqueNo(String uniqueNumber) {
        return salesScheduleDeliveryMapper.getSalesScheduleDeliveryByUniqueNo(uniqueNumber);
    }
}
