package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.dao.Provider.SalesReturnOrderProvider;
import com.yhglobal.gongxiao.sales.model.SalesReturnOrder;
import com.yhglobal.gongxiao.sales.model.bo.SalesReturnClassificationCount;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: 销售退货Mapper
 * @Author: LUOYI
 * @Date: Created in 10:10 2018/3/21
 */
public interface SalesReturnOrderMapper extends BaseMapper {

    @SelectProvider(type = SalesReturnOrderProvider.class, method = "getList")
    List<SalesReturnOrder> getSalesReturnOrderByProject(@Param("prefix") String prefix,
                                                        @Param("projectId") Long projectId,
                                                        @Param("returnedType") Integer returnedType,
                                                        @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                                                        @Param("warehouseId") String warehouseId,
                                                        @Param("timeStart") String timeStart,
                                                        @Param("timeEnd") String timeEnd,
                                                        @Param("returnedOrderStatus") Integer returnedOrderStatus);

    @SelectProvider(type = SalesReturnOrderProvider.class, method = "getCount")
    int getCount(@Param("prefix") String prefix,
                 @Param("projectId") Long projectId,
                 @Param("returnedType") Integer returnedType,
                 @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                 @Param("warehouseId") String warehouseId,
                 @Param("timeStart") String timeStart,
                 @Param("timeEnd") String timeEnd);

    @SelectProvider(type = SalesReturnOrderProvider.class, method = "getClassificationCount")
    @MapKey("returnedOrderStatus")
    List<SalesReturnClassificationCount> getClassificationCount(@Param("prefix") String prefix,
                                                                @Param("projectId") Long projectId,
                                                                @Param("returnedType") Integer returnedType,
                                                                @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                                                                @Param("warehouseId") String warehouseId,
                                                                @Param("timeStart") String timeStart,
                                                                @Param("timeEnd") String timeEnd);

    @Insert({
            "insert into ${prefix}_sales_returned_order (returnedOrderStatus, ",
            "returnedType, salesReturnedOrderNo, ",
            "projectId, projectName, ",
            "supplierId, supplierName, ",
            "distributorId, distributorName, ",
            "originalSalesOrderNo, originalGongxiaoOutboundOrderNo, ",
            "originalOutboundWarehouseId, originalOutboundWarehouseName, ",
            "targetWarehouseId, targetWarehouseName, ",
            "warehouseAddress, senderName, ",
            "senderMobile, freight, logisticsType,",
            "provinceId,provinceName,cityId,cityName,districtId,districtName, senderAddressItem,",
            "freightPaidBy, logisticsOrderNo, ",
            "logisticsCompany, returnedCouponAmount, ",
            "returnedPrepaidAmount, returnedCashAmount, ",
            "totalReturnedQuantity, totalImperfectQuantity, ",
            "inTransitQuantity, inStockQuantity, ",
            "inboundOrderInfo, tracelog,",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime)",
            "values (#{record.returnedOrderStatus,jdbcType=TINYINT}, ",
            "#{record.returnedType,jdbcType=TINYINT}, #{record.salesReturnedOrderNo,jdbcType=VARCHAR}, ",
            "#{record.projectId,jdbcType=BIGINT}, #{record.projectName,jdbcType=VARCHAR}, ",
            "#{record.supplierId,jdbcType=BIGINT}, #{record.supplierName,jdbcType=VARCHAR}, ",
            "#{record.distributorId,jdbcType=BIGINT}, #{record.distributorName,jdbcType=VARCHAR}, ",
            "#{record.originalSalesOrderNo,jdbcType=VARCHAR}, #{record.originalGongxiaoOutboundOrderNo,jdbcType=VARCHAR}, ",
            "#{record.originalOutboundWarehouseId,jdbcType=VARCHAR}, #{record.originalOutboundWarehouseName,jdbcType=VARCHAR}, ",
            "#{record.targetWarehouseId,jdbcType=VARCHAR}, #{record.targetWarehouseName,jdbcType=VARCHAR}, ",
            "#{record.warehouseAddress,jdbcType=VARCHAR}, #{record.senderName,jdbcType=VARCHAR}, ",
            "#{record.senderMobile,jdbcType=VARCHAR}, #{record.freight,jdbcType=BIGINT},#{record.logisticsType,jdbcType=BIGINT}, ",
            "#{record.provinceId},#{record.provinceName},#{record.cityId},#{record.cityName},#{record.districtId},#{record.districtName}, #{record.senderAddressItem,jdbcType=VARCHAR}, ",
            "#{record.freightPaidBy,jdbcType=TINYINT}, #{record.logisticsOrderNo,jdbcType=VARCHAR}, ",
            "#{record.logisticsCompany,jdbcType=VARCHAR}, #{record.returnedCouponAmount,jdbcType=BIGINT}, ",
            "#{record.returnedPrepaidAmount,jdbcType=BIGINT}, #{record.returnedCashAmount,jdbcType=BIGINT}, ",
            "#{record.totalReturnedQuantity,jdbcType=INTEGER}, #{record.totalImperfectQuantity,jdbcType=INTEGER}, ",
            "#{record.inTransitQuantity,jdbcType=INTEGER}, #{record.inStockQuantity,jdbcType=INTEGER}, ",
            "#{record.inboundOrderInfo,jdbcType=VARCHAR},#{record.tracelog},  ",
            "#{record.createdById,jdbcType=BIGINT}, #{record.createdByName,jdbcType=VARCHAR}, ",
            "#{record.createTime,jdbcType=TIMESTAMP}, #{record.lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "salesReturnOrder.salesReturnedOrderId")
    int addSalesReturnOrder(@Param("prefix") String prefix,
                            @Param("record") SalesReturnOrder salesReturnOrder);

    @Select({
            "select",
            "salesReturnedOrderId, returnedOrderStatus, returnedType, salesReturnedOrderNo, ",
            "gongxiaoWarehouseInboundOrderNo, projectId, projectName,provinceId,provinceName,cityId,cityName,districtId,districtName,senderAddressItem,logisticsType, ",
            "supplierId, supplierName, distributorId, distributorName, originalSalesOrderNo, ",
            "originalGongxiaoOutboundOrderNo, originalOutboundWarehouseId, originalOutboundWarehouseName, ",
            "targetWarehouseId, targetWarehouseName, warehouseAddress, senderName, senderMobile, ",
            "freight, freightPaidBy, logisticsOrderNo, logisticsCompany, returnedCouponAmount, ",
            "returnedPrepaidAmount, returnedCashAmount, totalReturnedQuantity, totalImperfectQuantity, ",
            "inTransitQuantity, inStockQuantity, inboundOrderInfo, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from ${prefix}_sales_returned_order",
            "where salesReturnedOrderId = #{salesReturnedOrderId,jdbcType=BIGINT}"
    })
    SalesReturnOrder selectById(@Param("prefix") String prefix,
                                @Param("salesReturnedOrderId") Long salesReturnedOrderId);

    @Update({
            "update ${prefix}_sales_returned_order",
            "set returnedOrderStatus = #{returnedOrderStatus,jdbcType=TINYINT},",
            "dataVersion = dataVersion+1,",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where salesReturnedOrderId = #{salesReturnedOrderId,jdbcType=BIGINT}",
            "and returnedOrderStatus = 1",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateStatus(@Param("prefix") String prefix,
                     @Param("salesReturnedOrderId") Long salesReturnedOrderId, @Param("returnedOrderStatus") Integer returnedOrderStatus, @Param("dataVersion") Long dataVersion, @Param("tracelog") String tracelog);

    @Update({
            "update ${prefix}_sales_returned_order",
            "set gongxiaoWarehouseInboundOrderNo = #{gongxiaoWarehouseInboundOrderNo},",
            "dataVersion = dataVersion+1",
            "where salesReturnedOrderId = #{salesReturnedOrderId,jdbcType=BIGINT}",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateInboundOrderNo(@Param("prefix") String prefix,
                             @Param("gongxiaoWarehouseInboundOrderNo") String gongxiaoWarehouseInboundOrderNo,
                             @Param("salesReturnedOrderId") Long salesReturnedOrderId,
                             @Param("dataVersion") Long dataVersion);

    @Update({
            "update ${prefix}_sales_returned_order",
            "set returnedCouponAmount = #{returnedCouponAmount},",
            "returnedPrepaidAmount = #{returnedPrepaidAmount},",
            "returnedCashAmount = #{returnedCashAmount},",
            "inStockQuantity = #{inStockQuantity},",
            "returnedOrderStatus = #{returnedOrderStatus},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR},",
            "dataVersion = dataVersion+1",
            "where salesReturnedOrderId = #{salesReturnedOrderId,jdbcType=BIGINT}",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateInboundRecord(@Param("prefix") String prefix,
                            @Param("returnedCouponAmount") Long returnedCouponAmount,
                            @Param("returnedPrepaidAmount") Long returnedPrepaidAmount,
                            @Param("returnedCashAmount") Long returnedCashAmount,
                            @Param("inStockQuantity") int inStockQuantity,
                            @Param("salesReturnedOrderId") Long salesReturnedOrderId,
                            @Param("tracelog") String tracelog,
                            @Param("dataVersion") Long dataVersion,
                            @Param("returnedOrderStatus") int returnedOrderStatus);

    @Select({
            "select",
            "salesReturnedOrderId, returnedOrderStatus, returnedType, salesReturnedOrderNo, ",
            "gongxiaoWarehouseInboundOrderNo, projectId, projectName,  senderAddressItem, ",
            "provinceId,provinceName,cityId,cityName,districtId,districtName,  supplierId, supplierName, distributorId, distributorName, ",
            "originalSalesOrderNo, originalGongxiaoOutboundOrderNo, originalOutboundWarehouseId, ",
            "originalOutboundWarehouseName, targetWarehouseId, targetWarehouseName, warehouseAddress, ",
            "senderName, senderMobile, freight, freightPaidBy, logisticsOrderNo, logisticsCompany, ",
            "returnedCouponAmount, returnedPrepaidAmount, returnedCashAmount, totalReturnedQuantity, ",
            "totalImperfectQuantity, inTransitQuantity, inStockQuantity, inboundOrderInfo, ",
            "logisticsType, dataVersion, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from ${prefix}_sales_returned_order",
            "where gongxiaoWarehouseInboundOrderNo = #{inBoundOrderNo}"
    })
    SalesReturnOrder selectSalesReturnOrderByInBoundOrderNo(@Param("prefix") String prefix,
                                                            @Param("inBoundOrderNo") String inBoundOrderNo);
}
