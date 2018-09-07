package com.yhglobal.gongxiao.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;
import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderItemBackWrite;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface PurchaseOrderItemMapper extends BaseMapper {
    @Delete({
            "delete from purchase_order_item",
            "where purchaseItemId = #{purchaseItemId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long purchaseItemId);

    @Select({
            "select",
            "purchaseItemId, orderStatus, purchaseOrderNo, productId, factoryPrice,productCode, productName, ",
            "productUnit, warehouseId, warehouseName, shippingMode, discountPercent, couponAmount, ",
            "prepaidAmount, guidePrice, purchasePrice, costPrice, couponBasePrice, purchaseNumber, ",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, canceledQuantity, returnedQuantity, ",
            "ongoingInboundOrderInfo, finishedInboundOrderInfo, dataVersion, createTime, ",
            "lastUpdateTime, tracelog,easEntryId,easMateriaCode",
            "from purchase_order_item",
            "where purchaseItemId = #{purchaseItemId,jdbcType=BIGINT}"
    })
    PurchaseOrderItem selectByItemId(Long purchaseItemId);

    @Insert({
            "insert into purchase_order_item (purchaseItemId,orderStatus, ",
            "purchaseOrderNo, productId, ",
            "productCode, productName, ",
            "productUnit, warehouseId, ",
            "warehouseName, shippingMode, ",
            "discountPercent, couponAmount, ",
            "prepaidAmount,cashAmount, guidePrice, ",
            "purchasePrice, costPrice, ",
            "couponBasePrice, purchaseNumber,factoryPrice, ",
            "inTransitQuantity, inStockQuantity, ",
            "imperfectQuantity, canceledQuantity, ",
            "returnedQuantity, ongoingInboundOrderInfo, ",
            "finishedInboundOrderInfo, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "tracelog,easEntryId,easMateriaCode)",
            "values ( #{purchaseItemId},#{orderStatus,jdbcType=TINYINT}, ",
            "#{purchaseOrderNo,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
            "#{productCode,jdbcType=VARCHAR}, #{productName,jdbcType=VARCHAR}, ",
            "#{productUnit,jdbcType=VARCHAR}, #{warehouseId,jdbcType=INTEGER}, ",
            "#{warehouseName,jdbcType=VARCHAR}, #{shippingMode,jdbcType=TINYINT}, ",
            "#{discountPercent,jdbcType=INTEGER}, #{couponAmount,jdbcType=BIGINT}, ",
            "#{prepaidAmount,jdbcType=BIGINT},#{cashAmount}, #{guidePrice,jdbcType=BIGINT}, ",
            "#{purchasePrice,jdbcType=BIGINT}, #{costPrice,jdbcType=BIGINT}, ",
            "#{couponBasePrice,jdbcType=BIGINT}, #{purchaseNumber,jdbcType=INTEGER},#{factoryPrice}, ",
            "#{inTransitQuantity,jdbcType=INTEGER}, #{inStockQuantity,jdbcType=INTEGER}, ",
            "#{imperfectQuantity,jdbcType=INTEGER}, #{canceledQuantity,jdbcType=INTEGER}, ",
            "#{returnedQuantity,jdbcType=INTEGER}, #{ongoingInboundOrderInfo,jdbcType=VARCHAR}, ",
            "#{finishedInboundOrderInfo,jdbcType=VARCHAR}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR},#{easEntryId},#{easMateriaCode})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "purchaseItemId")
    int insert(PurchaseOrderItem record);

    @Select({
            "select",
            "purchaseItemId, orderStatus, purchaseOrderNo, factoryPrice,productId, productCode, productName, ",
            "productUnit, warehouseId, warehouseName, shippingMode, discountPercent, couponAmount, ",
            "prepaidAmount,cashAmount, guidePrice, purchasePrice, costPrice, couponBasePrice, purchaseNumber, ",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, canceledQuantity, returnedQuantity, ",
            "ongoingInboundOrderInfo, finishedInboundOrderInfo, dataVersion, createTime, ",
            "lastUpdateTime, tracelog,easEntryId,easMateriaCode",
            "from purchase_order_item",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    List<PurchaseOrderItem> selectByOrderNo(String purchaseOrderNo);

    @Select({
            "select",
            "purchaseItemId, orderStatus, purchaseOrderNo,factoryPrice, productId, productCode, productName, ",
            "productUnit, warehouseId, warehouseName, shippingMode, discountPercent, couponAmount, ",
            "prepaidAmount, cashAmount,guidePrice, purchasePrice, costPrice, couponBasePrice, purchaseNumber, ",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, canceledQuantity, returnedQuantity, ",
            "ongoingInboundOrderInfo, finishedInboundOrderInfo, dataVersion, createTime, ",
            "lastUpdateTime, tracelog,easEntryId,easMateriaCode",
            "from purchase_order_item",
            "where purchaseOrderNo = #{purchaseOrderNo} and productCode = #{productCode}"
    })
    PurchaseOrderItem selectByOrderNoAndProduct(@Param("purchaseOrderNo")String purchaseOrderNo,
                                                @Param("productCode") String productCode);

    @SelectProvider(type = PurchaseOrderItemSqlProvider.class, method = "selectByNoAndProduct")
    List<PurchaseOrderItem> selectByNoAndProduct(@Param("purchaseOrderNo") String purchaseOrderNo,
                                                 @Param("productCode") String productCode);

    @Update({
            "update purchase_order_item",
            "set orderStatus = #{orderStatus,jdbcType=TINYINT},",
            "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR},",
            "productId = #{productId,jdbcType=VARCHAR},",
            "productCode = #{productCode,jdbcType=VARCHAR},",
            "productName = #{productName,jdbcType=VARCHAR},",
            "productUnit = #{productUnit,jdbcType=VARCHAR},",
            "warehouseId = #{warehouseId,jdbcType=INTEGER},",
            "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
            "shippingMode = #{shippingMode,jdbcType=TINYINT},",
            "discountPercent = #{discountPercent,jdbcType=INTEGER},",
            "couponAmount = #{couponAmount,jdbcType=BIGINT},",
            "prepaidAmount = #{prepaidAmount,jdbcType=BIGINT},",
            "guidePrice = #{guidePrice,jdbcType=BIGINT},",
            "purchasePrice = #{purchasePrice,jdbcType=BIGINT},",
            "costPrice = #{costPrice,jdbcType=BIGINT},",
            "couponBasePrice = #{couponBasePrice,jdbcType=BIGINT},",
            "purchaseNumber = #{purchaseNumber,jdbcType=INTEGER},",
            "inTransitQuantity = #{inTransitQuantity,jdbcType=INTEGER},",
            "inStockQuantity = #{inStockQuantity,jdbcType=INTEGER},",
            "imperfectQuantity = #{imperfectQuantity,jdbcType=INTEGER},",
            "canceledQuantity = #{canceledQuantity,jdbcType=INTEGER},",
            "returnedQuantity = #{returnedQuantity,jdbcType=INTEGER},",
            "ongoingInboundOrderInfo = #{ongoingInboundOrderInfo,jdbcType=VARCHAR},",
            "finishedInboundOrderInfo = #{finishedInboundOrderInfo,jdbcType=VARCHAR},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "easEntryId = #{easEntryId, jdbcType=VARCHAR}, ",
            "easMateriaCode = #{easMateriaCode, jdbcType=VARCHAR}, ",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where purchaseItemId = #{purchaseItemId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PurchaseOrderItem record);

    @Update({
            "update purchase_order_item",
            "set inTransitQuantity = #{inTransitQuantity,jdbcType=INTEGER},",
            "inStockQuantity = #{inStockQuantity},",
            "ongoingInboundOrderInfo = #{ongoingInboundOrderInfo},",
            "orderStatus = #{orderStatus}",
            "where purchaseItemId = #{purchaseItemId}"
    })
    int updateBackWrite(PurchaseOrderItemBackWrite purchaseOrderItemBackWrite);

    @Update({
            "update purchase_order_item",
            "set inTransitQuantity = #{inTransitQuantity,jdbcType=INTEGER},",
            "ongoingInboundOrderInfo = #{ongoingInboundOrderInfo},",
            "orderStatus = #{orderStatus}",
            "where purchaseItemId = #{purchaseItemId}"
    })
    int updateBackWritePurchaseItem(PurchaseOrderItemBackWrite purchaseOrderItemBackWrite);

    @Update({
            "update purchase_order_item",
            "set ongoingInboundOrderInfo = #{ongoingInboundOrderInfo},",
            "uniqueNumber = #{uniqueNumber}",
            "finishedInboundOrderInfo = #{finishedInboundOrderInfo}",
            "where purchaseItemId = #{purchaseItemId}"
    })
    int updateInboundOrder(long purchaseItemId,
                           String ongoingInboundOrderInfo,
                           String finishedInboundOrderInfo);

    @Update({
            "update purchase_order_item",
            "set easEntryId = #{easEntryId},",
            "easMateriaCode = #{easMateriaCode},",
            "orderStatus = 2 ,",
            "lastUpdateTime = NOW() ",
            "where purchaseItemId = #{purchaseItemId}"
    })
    int updateEasInfo(@Param("purchaseItemId") long purchaseItemId,
                      @Param("easEntryId") String easEntryId,
                      @Param("easMateriaCode") String easMateriaCode);

    @Update({
            "update purchase_order_item",
            "set orderStatus = #{orderStatus,jdbcType=TINYINT},",
            "warehouseId = #{warehouseId,jdbcType=INTEGER},",
            "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
            "shippingMode = #{shippingMode,jdbcType=TINYINT},",
            "discountPercent = #{discountPercent,jdbcType=INTEGER},",
            "couponAmount = #{couponAmount,jdbcType=BIGINT},",
            "prepaidAmount = #{prepaidAmount,jdbcType=BIGINT},",
            "guidePrice = #{guidePrice,jdbcType=BIGINT},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where purchaseItemId = #{purchaseItemId}"
    })
    int updateBPurchase(PurchaseOrderItem record);

}