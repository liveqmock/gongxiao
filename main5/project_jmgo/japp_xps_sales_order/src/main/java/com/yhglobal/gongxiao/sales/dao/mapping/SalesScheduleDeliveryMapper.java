package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.SalesScheduleDelivery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SalesScheduleDeliveryMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_sales_schedule_delivery (scheduleId, scheduleStatus, scheduleOrderNo, ",
            "syncToGongxiaoWarehouseFlag, onGoingOutboundOrderInfo, outboundedOrderInfo, signedOrderInfo, ",
            "salesOrderId, warehouseId, ",
            "warehouseName, shippingMode, ",
            "logisticsOrderNo, logisticsCompany, ",
            "productInfo, totalQuantity, ",
            "dataVersion, createTime, ",
            "lastUpdateTime, tracelog ,uniqueNo)",
            "values (#{record.scheduleId}, #{record.scheduleStatus}, #{record.scheduleOrderNo},",
            "#{record.syncToGongxiaoWarehouseFlag}, #{record.onGoingOutboundOrderInfo}, #{record.outboundedOrderInfo}, #{record.signedOrderInfo}, ",
            "#{record.salesOrderId}, #{record.warehouseId}, ",
            "#{record.warehouseName}, #{record.shippingMode}, ",
            "#{record.logisticsOrderNo}, #{record.logisticsCompany}, ",
            "#{record.productInfo}, #{record.totalQuantity}, ",
            "#{record.dataVersion}, #{record.createTime}, ",
            "#{record.lastUpdateTime}, #{record.tracelog}, #{record.uniqueNo})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SalesScheduleDelivery record);

}