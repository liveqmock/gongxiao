package com.yhglobal.gongxiao.phjd.sales.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesScheduleDelivery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 销售出库单mapper实现
 *
 * @author weizecheng
 * @date 2018/8/29 11:35
 */
public interface SalesScheduleDeliveryMapper  extends BaseMapper {

    /**
     * 插入销售出库表
     *
     * @author weizecheng
     * @date 2018/8/29 11:44
     * @param prefix 表前缀
     * @param record  表对象
     * @return
     */
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
            "#{record.dataVersion}, NOW(), ",
            "NOW(), #{record.tracelog}, #{record.uniqueNo})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SalesScheduleDelivery record);
}
