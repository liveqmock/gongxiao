package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.bo.SalesReturnClassificationCount;
import com.yhglobal.gongxiao.sales.dao.Provider.SalesReturnOrderProvider;
import com.yhglobal.gongxiao.sales.model.cancel.model.SalesReturnOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * @Description: 销售退货Mapper
 * @Author: LUOYI
 * @Date: Created in 10:10 2018/3/21
 */
public interface SalesReturnOrderMapper extends BaseMapper {

    @SelectProvider(type = SalesReturnOrderProvider.class, method = "getList")
    List<SalesReturnOrder> getSalesReturnOrderByProject(@Param("projectId") Integer projectId,
                                                        @Param("returnedType") Integer returnedType,
                                                        @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                                                        @Param("warehouseId") String warehouseId,
                                                        @Param("timeStart") String timeStart,
                                                        @Param("timeEnd") String timeEnd,
                                                        @Param("returnedOrderStatus") Integer returnedOrderStatus);

    @SelectProvider(type = SalesReturnOrderProvider.class, method = "getCount")
    int getCount(@Param("projectId") Integer projectId,
                           @Param("returnedType") Integer returnedType,
                           @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                           @Param("warehouseId") String warehouseId,
                           @Param("timeStart") String timeStart,
                           @Param("timeEnd") String timeEnd);

    @SelectProvider(type = SalesReturnOrderProvider.class, method = "getClassificationCount")
    @MapKey("returnedOrderStatus")
    List<SalesReturnClassificationCount> getClassificationCount(@Param("projectId") Integer projectId,
                                                                @Param("returnedType") Integer returnedType,
                                                                @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                                                                @Param("warehouseId") String warehouseId,
                                                                @Param("timeStart") String timeStart,
                                                                @Param("timeEnd") String timeEnd);

    @Insert({
            "insert into sales_returned_order (returnedOrderStatus, ",
            "returnedType, salesReturnedOrderNo, ",
            "projectId, projectName, ",
            "brandId, brandName, ",
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
            "values (#{returnedOrderStatus,jdbcType=TINYINT}, ",
            "#{returnedType,jdbcType=TINYINT}, #{salesReturnedOrderNo,jdbcType=VARCHAR}, ",
            "#{projectId,jdbcType=BIGINT}, #{projectName,jdbcType=VARCHAR}, ",
            "#{brandId,jdbcType=INTEGER}, #{brandName,jdbcType=VARCHAR}, ",
            "#{supplierId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
            "#{distributorId,jdbcType=BIGINT}, #{distributorName,jdbcType=VARCHAR}, ",
            "#{originalSalesOrderNo,jdbcType=VARCHAR}, #{originalGongxiaoOutboundOrderNo,jdbcType=VARCHAR}, ",
            "#{originalOutboundWarehouseId,jdbcType=VARCHAR}, #{originalOutboundWarehouseName,jdbcType=VARCHAR}, ",
            "#{targetWarehouseId,jdbcType=VARCHAR}, #{targetWarehouseName,jdbcType=VARCHAR}, ",
            "#{warehouseAddress,jdbcType=VARCHAR}, #{senderName,jdbcType=VARCHAR}, ",
            "#{senderMobile,jdbcType=VARCHAR}, #{freight,jdbcType=BIGINT},#{logisticsType,jdbcType=BIGINT}, ",
            "#{provinceId},#{provinceName},#{cityId},#{cityName},#{districtId},#{districtName}, #{senderAddressItem,jdbcType=VARCHAR}, ",
            "#{freightPaidBy,jdbcType=TINYINT}, #{logisticsOrderNo,jdbcType=VARCHAR}, ",
            "#{logisticsCompany,jdbcType=VARCHAR}, #{returnedCouponAmount,jdbcType=BIGINT}, ",
            "#{returnedPrepaidAmount,jdbcType=BIGINT}, #{returnedCashAmount,jdbcType=BIGINT}, ",
            "#{totalReturnedQuantity,jdbcType=INTEGER}, #{totalImperfectQuantity,jdbcType=INTEGER}, ",
            "#{inTransitQuantity,jdbcType=INTEGER}, #{inStockQuantity,jdbcType=INTEGER}, ",
            "#{inboundOrderInfo,jdbcType=VARCHAR},#{tracelog},  ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "salesReturnOrder.salesReturnedOrderId")
    int addSalesReturnOrder(SalesReturnOrder salesReturnOrder);
    @Select({
            "select",
            "salesReturnedOrderId, returnedOrderStatus, returnedType, salesReturnedOrderNo, ",
            "gongxiaoWarehouseInboundOrderNo, projectId, projectName, brandId, brandName,provinceId,provinceName,cityId,cityName,districtId,districtName,senderAddressItem,logisticsType, ",
            "supplierId, supplierName, distributorId, distributorName, originalSalesOrderNo, ",
            "originalGongxiaoOutboundOrderNo, originalOutboundWarehouseId, originalOutboundWarehouseName, ",
            "targetWarehouseId, targetWarehouseName, warehouseAddress, senderName, senderMobile, ",
            "freight, freightPaidBy, logisticsOrderNo, logisticsCompany, returnedCouponAmount, ",
            "returnedPrepaidAmount, returnedCashAmount, totalReturnedQuantity, totalImperfectQuantity, ",
            "inTransitQuantity, inStockQuantity, inboundOrderInfo, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from sales_returned_order",
            "where salesReturnedOrderId = #{salesReturnedOrderId,jdbcType=BIGINT}"
    })
    SalesReturnOrder selectById(Long salesReturnedOrderId);
    @Update({
            "update sales_returned_order",
            "set returnedOrderStatus = #{returnedOrderStatus,jdbcType=TINYINT},",
            "dataVersion = dataVersion+1,",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where salesReturnedOrderId = #{salesReturnedOrderId,jdbcType=BIGINT}",
            "and returnedOrderStatus = 1",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateStatus(@Param("salesReturnedOrderId") Long salesReturnedOrderId,@Param("returnedOrderStatus") Integer returnedOrderStatus,@Param("dataVersion") Long dataVersion,@Param("tracelog") String tracelog);
    @Update({
            "update sales_returned_order",
            "set gongxiaoWarehouseInboundOrderNo = #{gongxiaoWarehouseInboundOrderNo,jdbcType=VARCHAR},",
            "dataVersion = dataVersion+1",
            "where salesReturnedOrderId = #{salesReturnedOrderId,jdbcType=BIGINT}",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateInboundOrderNo(@Param("gongxiaoWarehouseInboundOrderNo") String gongxiaoWarehouseInboundOrderNo, @Param("salesReturnedOrderId") Long salesReturnedOrderId, @Param("dataVersion") Long dataVersion);
    @Update({
            "update sales_returned_order",
            "set returnedCouponAmount = #{returnedCouponAmount},",
            "returnedPrepaidAmount = #{returnedPrepaidAmount},",
            "returnedCashAmount = #{returnedCashAmount},",
            "inStockQuantity = #{inStockQuantity},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR},",
            "dataVersion = dataVersion+1",
            "where salesReturnedOrderId = #{salesReturnedOrderId,jdbcType=BIGINT}",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateInboundRecord(@Param("returnedCouponAmount") Long returnedCouponAmount,
                            @Param("returnedPrepaidAmount") Long returnedPrepaidAmount,
                            @Param("returnedCashAmount") Long returnedCashAmount,
                            @Param("inStockQuantity") int inStockQuantity,
                            @Param("salesReturnedOrderId") Long salesReturnedOrderId,
                            @Param("tracelog") String tracelog,
                            @Param("dataVersion") Long dataVersion);
    @Select({
            "select",
            "salesReturnedOrderId, returnedOrderStatus, returnedType, salesReturnedOrderNo, ",
            "gongxiaoWarehouseInboundOrderNo, projectId, projectName, brandId, senderAddressItem, ",
            "provinceId,provinceName,cityId,cityName,districtId,districtName, brandName, supplierId, supplierName, distributorId, distributorName, ",
            "originalSalesOrderNo, originalGongxiaoOutboundOrderNo, originalOutboundWarehouseId, ",
            "originalOutboundWarehouseName, targetWarehouseId, targetWarehouseName, warehouseAddress, ",
            "senderName, senderMobile, freight, freightPaidBy, logisticsOrderNo, logisticsCompany, ",
            "returnedCouponAmount, returnedPrepaidAmount, returnedCashAmount, totalReturnedQuantity, ",
            "totalImperfectQuantity, inTransitQuantity, inStockQuantity, inboundOrderInfo, ",
            "logisticsType, dataVersion, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from sales_returned_order",
            "where gongxiaoWarehouseInboundOrderNo = #{inBoundOrderNo}"
    })
    SalesReturnOrder selectSalesReturnOrderByInBoundOrderNo(String inBoundOrderNo);
}
