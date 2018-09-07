package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SalesOrderItemMapper extends BaseMapper {


    @Insert({
            "insert into ${prefix}_sales_order_item (salesOrderItemId, itemStatus, ",
            "salesOrderNo, productId, ",
            "productCode, productName, ",
            "warehouseId, warehouseName, ",
            "currencyId, currencyCode, ",
            "currencyName, supplierDiscountPercent, ",
            "yhDiscountPercent, retailPrice, ",
            "purchaseGuidePrice, salesGuidePrice, sellHighAmount, wholesalePrice, ",
            "supplierDiscountAmount, yhDiscountAmount, ",
            "totalOrderAmount, cashAmount, ",
            "totalQuantity, deliveredQuantity, ",
            "inTransitQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ",
            "ongoingOutboundOrderInfo, finishedOutboundOrderInfo, ",
            "dataVersion, createTime, ",
            "lastUpdateTime, tracelog, easCode, entryId, generatedPrepaid,",
            "shouldReceiveGrossProfit, receivedGrossProfit)",
            "values (#{record.salesOrderItemId}, #{record.itemStatus}, ",
            "#{record.salesOrderNo}, #{record.productId}, ",
            "#{record.productCode}, #{record.productName}, ",
            "#{record.warehouseId}, #{record.warehouseName}, ",
            "#{record.currencyId}, #{record.currencyCode}, ",
            "#{record.currencyName}, #{record.supplierDiscountPercent}, ",
            "#{record.yhDiscountPercent}, #{record.retailPrice}, ",
            "#{record.purchaseGuidePrice}, #{record.salesGuidePrice}, #{record.sellHighAmount}, #{record.wholesalePrice}, ",
            "#{record.supplierDiscountAmount}, #{record.yhDiscountAmount}, ",
            "#{record.totalOrderAmount}, #{record.cashAmount}, ",
            "#{record.totalQuantity}, #{record.deliveredQuantity}, ",
            "#{record.inTransitQuantity}, #{record.canceledQuantity}, ",
            "#{record.returnedQuantity}, #{record.unhandledQuantity}, ",
            "#{record.ongoingOutboundOrderInfo}, #{record.finishedOutboundOrderInfo}, ",
            "#{record.dataVersion}, #{record.createTime}, ",
            "#{record.lastUpdateTime}, #{record.tracelog}, ",
            "#{record.easCode}, #{record.entryId}, #{record.generatedPrepaid},",
            "#{record.shouldReceiveGrossProfit}, #{record.receivedGrossProfit})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SalesOrderItem record);


    @Select({
            "select",
            "salesOrderItemId, itemStatus, salesOrderNo, productId, productCode, productName, ",
            "warehouseId, warehouseName, currencyId, currencyCode, currencyName, supplierDiscountPercent, ",
            "yhDiscountPercent, retailPrice, wholesalePrice, supplierDiscountAmount, ",
            "yhDiscountAmount, totalOrderAmount, cashAmount, totalQuantity, deliveredQuantity, ",
            "inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, ",
            "finishedOutboundOrderInfo, dataVersion, createTime, lastUpdateTime, tracelog, ",
            "easCode,entryId, purchaseGuidePrice, salesGuidePrice, ",
            "sellHighAmount, generatedPrepaid, shouldReceiveGrossProfit, receivedGrossProfit",
            "from ${prefix}_sales_order_item",
            "where salesOrderNo = #{salesOrderNo}"
    })
    List<SalesOrderItem> selectListBySalesOrderNo(@Param("prefix") String prefix,
                                                  @Param("salesOrderNo") String salesOrderNo);


    /**
     * 根据销售单号、货品编码查询销售单商品信息
     *
     * @param salesOrderNo 销售单号
     * @param productCode  货品编码
     * @return
     */
    @Select({
            "select",
            "salesOrderItemId, itemStatus, salesOrderNo, productId, productCode, productName, ",
            "warehouseId, warehouseName, currencyId, currencyCode, currencyName, supplierDiscountPercent, ",
            "yhDiscountPercent, retailPrice, wholesalePrice, supplierDiscountAmount, ",
            "yhDiscountAmount, totalOrderAmount, cashAmount, totalQuantity, deliveredQuantity, ",
            "inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, ",
            "finishedOutboundOrderInfo, dataVersion, createTime, lastUpdateTime, tracelog, ",
            "easCode,entryId, purchaseGuidePrice, salesGuidePrice, ",
            "sellHighAmount, generatedPrepaid, shouldReceiveGrossProfit, receivedGrossProfit",
            "from ${prefix}_sales_order_item",
            "where salesOrderNo = #{salesOrderNo}",
            "AND",
            "productCode = #{productCode}"
    })
    SalesOrderItem getSalesOrderItemBySalesOrderNoAndProductCode(@Param("prefix") String prefix,
                                                                 @Param("salesOrderNo") String salesOrderNo,
                                                                 @Param("productCode") String productCode);

    /**
     * 根据销售单号、货品编码查询销售单商品信息
     *
     * @param salesOrderNo 销售单号
     * @param easCode      货品编码
     * @return
     */
    @Select({
            "select",
            "salesOrderItemId, itemStatus, salesOrderNo, productId, productCode, productName, ",
            "warehouseId, warehouseName, currencyId, currencyCode, currencyName, supplierDiscountPercent, ",
            "yhDiscountPercent, retailPrice, wholesalePrice, supplierDiscountAmount, ",
            "yhDiscountAmount, totalOrderAmount, cashAmount, totalQuantity, deliveredQuantity, ",
            "inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, ",
            "finishedOutboundOrderInfo, dataVersion, createTime, lastUpdateTime, tracelog, ",
            "easCode,entryId, purchaseGuidePrice, salesGuidePrice, ",
            "sellHighAmount, generatedPrepaid, shouldReceiveGrossProfit, receivedGrossProfit",
            "from ${prefix}_sales_order_item",
            "where salesOrderNo = #{salesOrderNo}",
            "AND",
            "easCode = #{easCode}"
    })
    SalesOrderItem getSalesOrderItemBySalesOrderNoAndEasProductCode(@Param("prefix") String prefix,
                                                                    @Param("salesOrderNo") String salesOrderNo,
                                                                    @Param("easCode") String easCode);


    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "usedCouponAmount = #{usedCouponAmount},",
            "usedPrepaidAmount = #{usedPrepaidAmount},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int updateCouponPrepaidAmount(@Param("prefix") String prefix,
                                  @Param("salesOrderItemId") Long salesOrderItemId,
                                  @Param("dataVersion") Long dataVersion,
                                  @Param("usedCouponAmount") long usedCouponAmount,
                                  @Param("usedPrepaidAmount") long userPrepaidAmount);

    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "unhandledQuantity = #{unhandledQuantity},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int updateUnhandledQuantity(@Param("prefix") String prefix,
                                @Param("salesOrderItemId") Long salesOrderItemId,
                                @Param("dataVersion") Long dataVersion,
                                @Param("unhandledQuantity") int unhandledQuantity);

    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "deliveredQuantity = #{deliveredQuantity},",
            "inTransitQuantity = #{inTransitQuantity},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int updateDeliveryAndInTransitQuantity(@Param("prefix") String prefix,
                                           @Param("salesOrderItemId") Long salesOrderItemId,
                                           @Param("dataVersion") Long dataVersion,
                                           @Param("deliveredQuantity") int deliveredQuantity,
                                           @Param("inTransitQuantity") int inTransitQuantity);

    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "entryId = #{entryId},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int updateEasEntryId(@Param("prefix") String prefix,
                         @Param("salesOrderItemId") Long salesOrderItemId,
                         @Param("dataVersion") Long dataVersion,
                         @Param("entryId") String entryId);


    @Update({
            "update ${prefix}_sales_order_item",
            "set ",
            "returnedQuantity = #{returnedQuantity},",
            "dataVersion = dataVersion+1",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int updateReturnedQuantity(@Param("prefix") String prefix,
                               @Param("salesOrderItemId") Long salesOrderItemId,
                               @Param("dataVersion") Long dataVersion,
                               @Param("returnedQuantity") int returnedQuantity);
}