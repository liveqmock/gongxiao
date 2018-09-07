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
    @Delete({
            "delete from t_sales_order_item",
            "where salesOrderItemId = #{salesOrderItemId}"
    })
    int deleteByPrimaryKey(Long salesOrderItemId);

    @Insert({
            "insert into t_sales_order_item (salesOrderItemId, itemStatus, ",
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
            "lastUpdateTime, tracelog, easCode, entryId, generatedPrepaid)",
            "values (#{salesOrderItemId}, #{itemStatus}, ",
            "#{salesOrderNo}, #{productId}, ",
            "#{productCode}, #{productName}, ",
            "#{warehouseId}, #{warehouseName}, ",
            "#{currencyId}, #{currencyCode}, ",
            "#{currencyName}, #{supplierDiscountPercent}, ",
            "#{yhDiscountPercent}, #{retailPrice}, ",
            "#{purchaseGuidePrice}, #{salesGuidePrice}, #{sellHighAmount}, #{wholesalePrice}, ",
            "#{supplierDiscountAmount}, #{yhDiscountAmount}, ",
            "#{totalOrderAmount}, #{cashAmount}, ",
            "#{totalQuantity}, #{deliveredQuantity}, ",
            "#{inTransitQuantity}, #{canceledQuantity}, ",
            "#{returnedQuantity}, #{unhandledQuantity}, ",
            "#{ongoingOutboundOrderInfo}, #{finishedOutboundOrderInfo}, ",
            "#{dataVersion}, #{createTime}, ",
            "#{lastUpdateTime}, #{tracelog}, #{easCode}, #{entryId}, #{generatedPrepaid})"
    })
    int insert(SalesOrderItem record);


    @Select({
            "select",
            "salesOrderItemId, itemStatus, salesOrderNo, productId, productCode, productName, ",
            "warehouseId, warehouseName, currencyId, currencyCode, currencyName, supplierDiscountPercent, ",
            "yhDiscountPercent, retailPrice, wholesalePrice, supplierDiscountAmount, ",
            "yhDiscountAmount, totalOrderAmount, cashAmount, totalQuantity, deliveredQuantity, ",
            "inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, ",
            "finishedOutboundOrderInfo, dataVersion, createTime, lastUpdateTime, tracelog, ",
            "easCode,entryId, purchaseGuidePrice, salesGuidePrice, sellHighAmount, generatedPrepaid",
            "from t_sales_order_item",
            "where salesOrderNo = #{salesOrderNo}"
    })
    List<SalesOrderItem> selectListBySalesOrderNo(@Param("salesOrderNo") String salesOrderNo);

    @Update({
            "update t_sales_order_item",
            "set itemStatus = #{itemStatus},",
            "salesOrderNo = #{salesOrderNo},",
            "productId = #{productId},",
            "productCode = #{productCode},",
            "productName = #{productName},",
            "warehouseId = #{warehouseId},",
            "warehouseName = #{warehouseName},",
            "currencyId = #{currencyId},",
            "currencyCode = #{currencyCode},",
            "currencyName = #{currencyName},",
            "supplierDiscountPercent = #{supplierDiscountPercent},",
            "yhDiscountPercent = #{yhDiscountPercent},",
            "retailPrice = #{retailPrice},",
            "wholesalePrice = #{wholesalePrice},",
            "supplierDiscountAmount = #{supplierDiscountAmount},",
            "yhDiscountAmount = #{yhDiscountAmount},",
            "totalOrderAmount = #{totalOrderAmount},",
            "cashAmount = #{cashAmount},",
            "totalQuantity = #{totalQuantity},",
            "deliveredQuantity = #{deliveredQuantity},",
            "inTransitQuantity = #{inTransitQuantity},",
            "canceledQuantity = #{canceledQuantity},",
            "returnedQuantity = #{returnedQuantity},",
            "unhandledQuantity = #{unhandledQuantity},",
            "ongoingOutboundOrderInfo = #{ongoingOutboundOrderInfo},",
            "finishedOutboundOrderInfo = #{finishedOutboundOrderInfo},",
            "dataVersion = dataVersion+1,",
            "createTime = #{createTime},",
            "tracelog = #{tracelog},",
            "entryId = #{entryId},",
            "easCode = #{easCode}",
            "where salesOrderItemId = #{salesOrderItemId} AND dataVersion = #{dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(SalesOrderItem record);

    @Select({
            "select",
            "salesOrderItemId, itemStatus, salesOrderNo, productId, productCode, productName, ",
            "warehouseId, warehouseName, currencyId, currencyCode, currencyName, supplierDiscountPercent, ",
            "yhDiscountPercent, retailPrice, wholesalePrice, supplierDiscountAmount, ",
            "yhDiscountAmount, totalOrderAmount, cashAmount, totalQuantity, deliveredQuantity, ",
            "inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, ",
            "finishedOutboundOrderInfo, dataVersion, createTime, lastUpdateTime, tracelog,easCode,entryId, purchaseGuidePrice, salesGuidePrice, sellHighAmount",
            "from t_sales_order_item",
            "where salesOrderItemId = #{salesOrderItemId}"
    })
    SalesOrderItem selectById(long salesOrderItemId);

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
            "easCode,entryId, purchaseGuidePrice, salesGuidePrice, sellHighAmount, generatedPrepaid",
            "from t_sales_order_item",
            "where salesOrderNo = #{salesOrderNo}",
            "AND",
            "productCode = #{productCode}"
    })
    SalesOrderItem getSalesOrderItemBySalesOrderNoAndProductCode(@Param("salesOrderNo") String salesOrderNo,
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
            "finishedOutboundOrderInfo, dataVersion, createTime,  tracelog, ",
            "easCode,entryId, purchaseGuidePrice, salesGuidePrice, sellHighAmount, generatedPrepaid",
            "from t_sales_order_item",
            "where salesOrderNo = #{salesOrderNo}",
            "AND",
            "easCode = #{easCode}"
    })
    SalesOrderItem getSalesOrderItemBySalesOrderNoAndEasProductCode(@Param("salesOrderNo") String salesOrderNo,
                                                                    @Param("easCode") String easCode);

    /**
     * 选择性查询详情
     *
     * @param salesOrderNo 销售单号
     * @param productCodes 货品编码 有多条，使用","分割
     * @return
     */
    @SelectProvider(type = SalesOrderItemSqlProvider.class, method = "getListSelectively")
    List<SalesOrderItem> selectListSelective(@Param("salesOrderNo") String salesOrderNo,
                                             @Param("productCodes") String productCodes);
}