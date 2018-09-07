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
    @Delete({
            "delete from sales_schedule_delivery",
            "where scheduleId = #{scheduleId}"
    })
    int deleteByPrimaryKey(Long scheduleId);

    @Insert({
            "insert into sales_schedule_delivery (scheduleId, scheduleStatus, scheduleOrderNo, ",
            "syncToGongxiaoWarehouseFlag, onGoingOutboundOrderInfo, outboundedOrderInfo, signedOrderInfo, ",
            "salesOrderId, warehouseId, ",
            "warehouseName, shippingMode, ",
            "logisticsOrderNo, logisticsCompany, ",
            "productInfo, totalQuantity, ",
            "dataVersion, createTime, ",
            "lastUpdateTime, tracelog ,uniqueNo)",
            "values (#{scheduleId}, #{scheduleStatus}, #{scheduleOrderNo},",
            "#{syncToGongxiaoWarehouseFlag}, #{onGoingOutboundOrderInfo}, #{outboundedOrderInfo}, #{signedOrderInfo}, ",
            "#{salesOrderId}, #{warehouseId}, ",
            "#{warehouseName}, #{shippingMode}, ",
            "#{logisticsOrderNo}, #{logisticsCompany}, ",
            "#{productInfo}, #{totalQuantity}, ",
            "#{dataVersion}, #{createTime}, ",
            "#{lastUpdateTime}, #{tracelog}, #{uniqueNo})"
    })
    int insert(SalesScheduleDelivery record);

    @Select({
            "select",
            "uniqueNo,scheduleId, scheduleStatus, syncToGongxiaoWarehouseFlag, onGoingOutboundOrderInfo, outboundedOrderInfo, signedOrderInfo,",
            "salesOrderId, warehouseId, warehouseName, shippingMode, logisticsOrderNo, logisticsCompany, ",
            "productInfo, totalQuantity, dataVersion, createTime, lastUpdateTime, tracelog,easOrderNo,easOrderId",
            "from sales_schedule_delivery",
            "where scheduleId = #{scheduleId}"
    })
    SalesScheduleDelivery selectByPrimaryKey(Long scheduleId);

    @Update({
            "update sales_schedule_delivery",
            "set scheduleStatus = #{scheduleStatus},",
            "syncToGongxiaoWarehouseFlag = #{syncToGongxiaoWarehouseFlag},",
            "onGoingOutboundOrderInfo = #{onGoingOutboundOrderInfo},",
            "outboundedOrderInfo = #{outboundedOrderInfo},",
            "signedOrderInfo = #{signedOrderInfo},",
            "salesOrderId = #{salesOrderId},",
            "warehouseId = #{warehouseId},",
            "warehouseName = #{warehouseName},",
            "shippingMode = #{shippingMode},",
            "logisticsOrderNo = #{logisticsOrderNo},",
            "logisticsCompany = #{logisticsCompany},",
            "productInfo = #{productInfo},",
            "totalQuantity = #{totalQuantity},",
            "dataVersion = dataVersion+1,",
            "createTime = #{createTime},",
            "uniqueNo = #{uniqueNo},",
            "easOrderNo = #{easOrderNo},",
            "easOrderId = #{easOrderId},",
            "tracelog = #{tracelog}",
            "where scheduleId = #{scheduleId} AND dataVersion = #{dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(SalesScheduleDelivery record);

    /**
     * 根据销售单号查询
     *
     * @param salesOrderId 销售单号
     * @return SalesScheduleDelivery
     * @author 葛灿
     * @date 2018/2/28 11:43
     */
    @Select({
            "select",
            "uniqueNo, scheduleId, scheduleStatus, syncToGongxiaoWarehouseFlag, onGoingOutboundOrderInfo, outboundedOrderInfo, signedOrderInfo,",
            "salesOrderId, warehouseId, warehouseName, shippingMode, logisticsOrderNo, logisticsCompany, ",
            "productInfo, totalQuantity, dataVersion, createTime, lastUpdateTime, tracelog, easOrderNo, easOrderId",
            "from sales_schedule_delivery",
            "where salesOrderId = #{salesOrderId}"
    })
    List<SalesScheduleDelivery> selectListBySalesOrderNo(String salesOrderId);

    /**
     * 根据出库单号查询
     *
     * @param outboundOrderNo 出库单号
     * @return
     */
    @Select({
            "select",
            "uniqueNo, scheduleId, scheduleStatus, syncToGongxiaoWarehouseFlag, onGoingOutboundOrderInfo, outboundedOrderInfo, signedOrderInfo, ",
            "salesOrderId, warehouseId, warehouseName, shippingMode, logisticsOrderNo, logisticsCompany, ",
            "productInfo, totalQuantity, dataVersion, createTime, lastUpdateTime, tracelog ,uniqueNo, easOrderNo, easOrderId",
            "from sales_schedule_delivery",
            "where scheduleOrderNo like '%' #{outboundOrderNo} '%'"
    })
    SalesScheduleDelivery getSalesScheduleDeliveryByOutboundOrderNo(@Param("outboundOrderNo")String outboundOrderNo);

    /**
     * 根据唯一号查询
     *
     * @param uniqueNumber 出库单号
     * @return
     */
    @Select({
            "select",
            "uniqueNo, scheduleId, scheduleStatus, syncToGongxiaoWarehouseFlag, onGoingOutboundOrderInfo, outboundedOrderInfo, signedOrderInfo,",
            "salesOrderId, warehouseId, warehouseName, shippingMode, logisticsOrderNo, logisticsCompany, ",
            "productInfo, totalQuantity, dataVersion, createTime, lastUpdateTime, tracelog ,uniqueNo, easOrderNo, easOrderId",
            "from sales_schedule_delivery",
            "where uniqueNumber = #{uniqueNumber}"
    })
    SalesScheduleDelivery getSalesScheduleDeliveryByUniqueNo(@Param("uniqueNumber") String uniqueNumber);
}