package com.yhglobal.gongxiao.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.model.PurchaseReturnedOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PurchaseReturnedOrderMapper extends BaseMapper {
    @Delete({
            "delete from purchase_returned_order",
            "where purchaseReturnedOrderId = #{purchaseReturnedOrderId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long purchaseReturnedOrderId);

    @Insert({
            "insert into purchase_returned_order (purchaseReturnedOrderId, returnedOrderStatus, ",
            "returnedType, purchaseReturnedOrderNo, ",
            "outboundOrderNumber, projectId, ",
            "projectName, brandId, ",
            "brandName, currencyCode, ",
            "currencyName, originalPurchaseOrderNo, ",
            "originalGongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, recipientName, ",
            "recipientMobile, recipientAddress, ",
            "recipientDetailAddress, recipientCompanyName, ",
            "shippingMode, logisticsOrderNo, ",
            "logisticsCompany, freight, ",
            "freightPaidBy, returnedCouponAmount, ",
            "returnedPrepaidAmount, returnedCashAmount, ",
            "sourceOrderNumber, totalReturnedQuantity, ",
            "totalImperfectQuantity, outStockQuantity, ",
            "deliveredQuantity, outboundOrderInfo, ",
            "returnItemInfo, dataVersion, ",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{purchaseReturnedOrderId,jdbcType=BIGINT}, #{returnedOrderStatus,jdbcType=TINYINT}, ",
            "#{returnedType,jdbcType=TINYINT}, #{purchaseReturnedOrderNo,jdbcType=VARCHAR}, ",
            "#{outboundOrderNumber,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{brandId,jdbcType=INTEGER}, ",
            "#{brandName,jdbcType=VARCHAR}, #{currencyCode,jdbcType=VARCHAR}, ",
            "#{currencyName,jdbcType=VARCHAR}, #{originalPurchaseOrderNo,jdbcType=VARCHAR}, ",
            "#{originalGongxiaoInboundOrderNo,jdbcType=VARCHAR}, #{warehouseId,jdbcType=VARCHAR}, ",
            "#{warehouseName,jdbcType=VARCHAR}, #{recipientName,jdbcType=VARCHAR}, ",
            "#{recipientMobile,jdbcType=VARCHAR}, #{recipientAddress,jdbcType=VARCHAR}, ",
            "#{recipientDetailAddress,jdbcType=VARCHAR}, #{recipientCompanyName,jdbcType=VARCHAR}, ",
            "#{shippingMode,jdbcType=TINYINT}, #{logisticsOrderNo,jdbcType=VARCHAR}, ",
            "#{logisticsCompany,jdbcType=VARCHAR}, #{freight,jdbcType=BIGINT}, ",
            "#{freightPaidBy,jdbcType=TINYINT}, #{returnedCouponAmount,jdbcType=BIGINT}, ",
            "#{returnedPrepaidAmount,jdbcType=BIGINT}, #{returnedCashAmount,jdbcType=BIGINT}, ",
            "#{sourceOrderNumber,jdbcType=INTEGER}, #{totalReturnedQuantity,jdbcType=INTEGER}, ",
            "#{totalImperfectQuantity,jdbcType=INTEGER}, #{outStockQuantity,jdbcType=INTEGER}, ",
            "#{deliveredQuantity,jdbcType=INTEGER}, #{outboundOrderInfo,jdbcType=VARCHAR}, ",
            "#{returnItemInfo,jdbcType=VARCHAR}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(PurchaseReturnedOrder record);

    @Select({
            "select",
            "purchaseReturnedOrderId, returnedOrderStatus, returnedType, purchaseReturnedOrderNo, ",
            "outboundOrderNumber, projectId, projectName, brandId, brandName, currencyCode, ",
            "currencyName, originalPurchaseOrderNo, originalGongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, recipientName, recipientMobile, recipientAddress, recipientDetailAddress, ",
            "recipientCompanyName, shippingMode, logisticsOrderNo, logisticsCompany, freight, ",
            "freightPaidBy, returnedCouponAmount, returnedPrepaidAmount, returnedCashAmount, ",
            "sourceOrderNumber, totalReturnedQuantity, totalImperfectQuantity, outStockQuantity, ",
            "deliveredQuantity, outboundOrderInfo, returnItemInfo, dataVersion, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from purchase_returned_order",
            "where purchaseReturnedOrderNo = #{purchaseReturnedOrderNo}"
    })
    PurchaseReturnedOrder getByReturnedOrderNo(@Param("purchaseReturnedOrderNo") String purchaseReturnedOrderNo);

    @Update({
            "update purchase_returned_order",
            "set returnedOrderStatus = #{returnedOrderStatus,jdbcType=TINYINT},",
            "outStockQuantity = #{outStockQuantity,jdbcType=INTEGER},",
            "deliveredQuantity = #{deliveredQuantity,jdbcType=INTEGER},",
            "where purchaseReturnedOrderNo = #{purchaseReturnedOrderNo}"
    })
    int updateByReturnOrderNo(String purchaseReturnedOrderNo,byte returnedOrderStatus,int outStockQuantity,int deliveredQuantity);

    @SelectProvider(type = PurchaseOrderSqlProvider.class, method = "getPurchaseReturnList")
    List<PurchaseReturnedOrder> getPurchaseReturnList(@Param("projectId") String projectId,
                                                      @Param("warehouseId") String warehouseId,
                                                      @Param("orderNumber") String orderNumber,
                                                      @Param("startDate") String startDate,
                                                      @Param("endDate") String endDate);
}