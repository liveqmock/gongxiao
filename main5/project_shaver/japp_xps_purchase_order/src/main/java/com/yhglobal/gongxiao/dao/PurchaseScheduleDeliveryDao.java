package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.dao.mapper.PurchaseScheduleDeliveryMapper;
import com.yhglobal.gongxiao.model.PurchaseScheduleDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 预约收货
 *
 * @author: 陈浩
 * @create: 2018-03-02 11:45
 **/
@Repository
public class PurchaseScheduleDeliveryDao {

    @Autowired
    PurchaseScheduleDeliveryMapper purchaseScheduleDeliveryMapper;

    /**
     * 插入预约收货记录
     * @param record 预约收货记录
     * @return
     */
    public int insert(PurchaseScheduleDelivery record){
        return purchaseScheduleDeliveryMapper.insert(record);
    }

    /**
     * 仓库模块的订单号获取预约收货信息
     * @param warehouseInboundOrderNo 仓储模块那边给的回执
     * @return
     */
    public PurchaseScheduleDelivery getByWarehouseInboudOrderNo(String tablePrefix,String  warehouseInboundOrderNo){
        return purchaseScheduleDeliveryMapper.selectByWarehouseInboudOrderNo(tablePrefix,warehouseInboundOrderNo);
    }

    /**
     * 仓库模块的订单号获取预约收货信息
     * @param purchaseOrderNo 采购单号
     * @return
     */
    public List<PurchaseScheduleDelivery> selectByPurchaseOrderNo(String tablePrefix,String  purchaseOrderNo){
        return purchaseScheduleDeliveryMapper.selectByPurchaseOrderNo(tablePrefix,purchaseOrderNo);
    }

    /**
     * 更新预约收货信息
     * @param scheduleId 预约ID
     * @param productInfo 商品信息
     * @return
     */
    public int updateSchedule(String tablePrefix,long scheduleId,String productInfo){
        return purchaseScheduleDeliveryMapper.updateSchedule(tablePrefix,scheduleId,productInfo);
    }

    /**
     * 更新预约收货状态
     * @param scheduleId 预约ID
     * @param scheduleStatus 预约状态
     * @return
     */
    public int updateScheduleStatus(String tablePrefix,long scheduleId,byte scheduleStatus){
        return purchaseScheduleDeliveryMapper.updateScheduleStatus(tablePrefix,scheduleId,scheduleStatus);
    }


}
