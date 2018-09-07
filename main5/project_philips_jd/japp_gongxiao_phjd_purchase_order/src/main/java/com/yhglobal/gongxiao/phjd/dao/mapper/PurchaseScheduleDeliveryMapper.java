package com.yhglobal.gongxiao.phjd.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.model.PurchaseScheduleDelivery;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PurchaseScheduleDeliveryMapper extends BaseMapper {
    @Delete({
        "delete from ${tablePrefix}_purchase_schedule_delivery",
        "where scheduleId = #{scheduleId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(String tablePrefix, Long scheduleId);

    @Insert({
        "insert into ${tablePrefix}_purchase_schedule_delivery (scheduleId, scheduleStatus, ",
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
        "from ${tablePrefix}_purchase_schedule_delivery",
        "where gongxiaoWarehouseInboundOrderNo = #{gongxiaoWarehouseInboundOrderNo,}"
    })
    PurchaseScheduleDelivery selectByWarehouseInboudOrderNo(String tablePrefix, String gongxiaoWarehouseInboundOrderNo);

    @Select({
            "select",
            "scheduleId, scheduleStatus, syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
            "purchaseOrderNo, warehouseId, warehouseName, shippingMode, logisticsOrderNo, ",
            "logisticsCompany, productInfo, totalQuantity, dataVersion, createTime, lastUpdateTime, ",
            "tracelog",
            "from ${tablePrefix}_purchase_schedule_delivery",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    List<PurchaseScheduleDelivery> selectByPurchaseOrderNo(String tablePrefix, String purchaseOrderNo);

    @Update({
        "update ${tablePrefix}_purchase_schedule_delivery",
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
            "update ${tablePrefix}_purchase_schedule_delivery",
            "set",
            "productInfo = #{productInfo}",
            "where scheduleId = #{scheduleId}"
    })
    int updateSchedule(@Param("tablePrefix") String tablePrefix,
                       @Param("scheduleId") long scheduleId,
                       @Param("productInfo") String productInfo);
    @Update({
            "update ${tablePrefix}_purchase_schedule_delivery",
            "set scheduleStatus = #{scheduleStatus,jdbcType=TINYINT}",
            "where scheduleId = #{scheduleId,jdbcType=BIGINT}"
    })
    int updateScheduleStatus(@Param("tablePrefix") String tablePrefix,
                             @Param("scheduleId") long scheduleId,
                             @Param("scheduleStatus") byte scheduleStatus);

}