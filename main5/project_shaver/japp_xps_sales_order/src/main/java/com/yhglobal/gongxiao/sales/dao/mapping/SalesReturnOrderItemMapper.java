package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.SalesReturnOrderItem;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Description: 销售退货详情Mapper
 * @Author: LUOYI
 * @Date: Created in 10:10 2018/3/21
 */
public interface SalesReturnOrderItemMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_sales_returned_order_item (itemStatus, ",
            "syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
            "salesReturnedOrderNo, warehouseId, ",
            "warehouseName, productId, ",
            "productCode, productName, ",
            "productUnit, totalReturnedQuantity, ",
            "totalImperfectQuantity, totalInStockQuantity, ",
            "inStockImperfectQuantity, deliveredQuantity, ",
            "wmsInboundRecord, dataVersion, ",
            "createTime, lastUpdateTime,returnCause, inventoryType)",
            "values (#{salesReturnOrderItem.itemStatus,jdbcType=TINYINT}, ",
            "#{salesReturnOrderItem.syncToGongxiaoWarehouseFlag,jdbcType=TINYINT}, #{salesReturnOrderItem.gongxiaoWarehouseInboundOrderNo,jdbcType=VARCHAR}, ",
            "#{salesReturnOrderItem.salesReturnedOrderNo,jdbcType=VARCHAR}, #{salesReturnOrderItem.warehouseId,jdbcType=VARCHAR}, ",
            "#{salesReturnOrderItem.warehouseName,jdbcType=VARCHAR}, #{salesReturnOrderItem.productId,jdbcType=VARCHAR}, ",
            "#{salesReturnOrderItem.productCode,jdbcType=VARCHAR}, #{salesReturnOrderItem.productName,jdbcType=VARCHAR}, ",
            "#{salesReturnOrderItem.productUnit,jdbcType=VARCHAR}, #{salesReturnOrderItem.totalReturnedQuantity,jdbcType=INTEGER}, ",
            "#{salesReturnOrderItem.totalImperfectQuantity,jdbcType=INTEGER}, #{salesReturnOrderItem.totalInStockQuantity,jdbcType=INTEGER}, ",
            "#{salesReturnOrderItem.inStockImperfectQuantity,jdbcType=INTEGER}, #{salesReturnOrderItem.deliveredQuantity,jdbcType=INTEGER}, ",
            "#{salesReturnOrderItem.wmsInboundRecord,jdbcType=VARCHAR}, #{salesReturnOrderItem.dataVersion,jdbcType=BIGINT}, ",
            "#{salesReturnOrderItem.createTime,jdbcType=TIMESTAMP}, #{salesReturnOrderItem.lastUpdateTime,jdbcType=TIMESTAMP},",
            "#{salesReturnOrderItem.returnCause,jdbcType=VARCHAR}, #{salesReturnOrderItem.inventoryType} )"
    })
    @Options(useGeneratedKeys = true, keyProperty = "salesReturnOrderItem.rowId")
    int addSalesReturnOrderItem(@Param("prefix") String prefix,
                                @Param("salesReturnOrderItem") SalesReturnOrderItem salesReturnOrderItem);

    @Select({
            "select",
            "rowId, itemStatus, syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
            "salesReturnedOrderNo, warehouseId, warehouseName, productId, productCode, productName, ",
            "productUnit, totalReturnedQuantity, totalImperfectQuantity, totalInStockQuantity, ",
            "inStockImperfectQuantity, deliveredQuantity, wmsInboundRecord, dataVersion, ",
            "createTime, lastUpdateTime,returnCause",
            "from ${prefix}_sales_returned_order_item",
            "where salesReturnedOrderNo = #{salesReturnedOrderNo,jdbcType=BIGINT}"
    })
    List<SalesReturnOrderItem> selectBySalesReturnedOrderNo(@Param("prefix") String prefix,
                                                            @Param("salesReturnedOrderNo") String salesReturnedOrderNo);

    @Update({
            "update ${prefix}_sales_returned_order_item",
            "set gongxiaoWarehouseInboundOrderNo = #{gongxiaoWarehouseInboundOrderNo,jdbcType=VARCHAR},",
            "dataVersion = dataVersion+1",
            "where rowId = #{rowId,jdbcType=BIGINT}",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateInboundOrderNo(@Param("prefix") String prefix,
                             @Param("gongxiaoWarehouseInboundOrderNo") String gongxiaoWarehouseInboundOrderNo, @Param("rowId") Long rowId, @Param("dataVersion") Long dataVersion);

    @Update({
            "update ${prefix}_sales_returned_order_item",
            "set totalInStockQuantity = #{totalInStockQuantity},",
            "wmsInboundRecord = #{wmsInboundRecord},",
            "dataVersion = dataVersion+1",
            "where rowId = #{rowId,jdbcType=BIGINT}",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateInboundRecord(@Param("prefix") String prefix,
                            @Param("totalInStockQuantity") int totalInStockQuantity, @Param("wmsInboundRecord") String wmsInboundRecord, @Param("rowId") Long rowId, @Param("dataVersion") Long dataVersion);

    @Select({
            "select",
            "rowId, itemStatus, syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
            "salesReturnedOrderNo, warehouseId, warehouseName, productId, productCode, productName, ",
            "productUnit, totalReturnedQuantity, totalImperfectQuantity, totalInStockQuantity, ",
            "inStockImperfectQuantity, deliveredQuantity, wmsInboundRecord, dataVersion, ",
            "createTime, lastUpdateTime,returnCause",
            "from ${prefix}_sales_returned_order_item",
            "where gongxiaoWarehouseInboundOrderNo = #{inBoundOrderNo} and productCode = #{productCode}"
    })
    SalesReturnOrderItem selectSalesReturnOrderItemByInBoundOrderNo(@Param("prefix") String prefix,
                                                                    @Param("inBoundOrderNo") String inBoundOrderNo, @Param("productCode") String productCode);

}
