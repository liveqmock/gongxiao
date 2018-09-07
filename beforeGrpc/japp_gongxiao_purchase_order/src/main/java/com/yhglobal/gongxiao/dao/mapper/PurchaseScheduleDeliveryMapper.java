package com.yhglobal.gongxiao.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.model.PurchaseScheduleDelivery;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PurchaseScheduleDeliveryMapper extends BaseMapper {
    @Delete({
        "delete from purchase_schedule_delivery",
        "where scheduleId = #{scheduleId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long scheduleId);

    @Insert({
        "insert into purchase_schedule_delivery (scheduleId, scheduleStatus, ",
        "syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
        "purchaseOrderNo, warehouseId, ",
        "warehouseName, shippingMode, ",
        "logisticsOrderNo, logisticsCompany, ",
        "productInfo, totalQuantity, ",
        "dataVersion, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{scheduleId,jdbcType=BIGINT}, #{scheduleStatus,jdbcType=TINYINT}, ",
        "#{syncToGongxiaoWarehouseFlag,jdbcType=TINYINT}, #{gongxiaoWarehouseInboundOrderNo,jdbcType=VARCHAR}, ",
        "#{purchaseOrderNo}, #{warehouseId,jdbcType=VARCHAR}, ",
        "#{warehouseName,jdbcType=VARCHAR}, #{shippingMode,jdbcType=TINYINT}, ",
        "#{logisticsOrderNo,jdbcType=VARCHAR}, #{logisticsCompany,jdbcType=VARCHAR}, ",
        "#{productInfo,jdbcType=VARCHAR}, #{totalQuantity,jdbcType=INTEGER}, ",
        "#{dataVersion,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(PurchaseScheduleDelivery record);

    @Select({
        "select",
        "scheduleId, scheduleStatus, syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
        "purchaseOrderNo, warehouseId, warehouseName, shippingMode, logisticsOrderNo, ",
        "logisticsCompany, productInfo, totalQuantity, dataVersion, createTime, lastUpdateTime, ",
        "tracelog",
        "from purchase_schedule_delivery",
        "where gongxiaoWarehouseInboundOrderNo = #{gongxiaoWarehouseInboundOrderNo,}"
    })
    PurchaseScheduleDelivery selectByWarehouseInboudOrderNo(String gongxiaoWarehouseInboundOrderNo);

    @Select({
            "select",
            "scheduleId, scheduleStatus, syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
            "purchaseOrderNo, warehouseId, warehouseName, shippingMode, logisticsOrderNo, ",
            "logisticsCompany, productInfo, totalQuantity, dataVersion, createTime, lastUpdateTime, ",
            "tracelog",
            "from purchase_schedule_delivery",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    List<PurchaseScheduleDelivery> selectByPurchaseOrderNo(String purchaseOrderNo);

    @Update({
        "update purchase_schedule_delivery",
        "set scheduleStatus = #{scheduleStatus,jdbcType=TINYINT},",
          "syncToGongxiaoWarehouseFlag = #{syncToGongxiaoWarehouseFlag,jdbcType=TINYINT},",
          "gongxiaoWarehouseInboundOrderNo = #{gongxiaoWarehouseInboundOrderNo,jdbcType=VARCHAR},",
          "purchaseOrderNo = #{purchaseOrderNo},",
          "warehouseId = #{warehouseId,jdbcType=VARCHAR},",
          "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
          "shippingMode = #{shippingMode,jdbcType=TINYINT},",
          "logisticsOrderNo = #{logisticsOrderNo,jdbcType=VARCHAR},",
          "logisticsCompany = #{logisticsCompany,jdbcType=VARCHAR},",
          "productInfo = #{productInfo,jdbcType=VARCHAR},",
          "totalQuantity = #{totalQuantity,jdbcType=INTEGER},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where scheduleId = #{scheduleId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PurchaseScheduleDelivery record);

    @Update({
            "update purchase_schedule_delivery",
            "set",
            "productInfo = #{productInfo}",
            "where scheduleId = #{scheduleId}"
    })
    int updateSchedule(@Param("scheduleId") long scheduleId,
                       @Param("productInfo")String productInfo);
    @Update({
            "update purchase_schedule_delivery",
            "set scheduleStatus = #{scheduleStatus,jdbcType=TINYINT}",
            "where scheduleId = #{scheduleId,jdbcType=BIGINT}"
    })
    int updateScheduleStatus(@Param("scheduleId") long scheduleId, @Param("scheduleStatus")byte scheduleStatus);

}