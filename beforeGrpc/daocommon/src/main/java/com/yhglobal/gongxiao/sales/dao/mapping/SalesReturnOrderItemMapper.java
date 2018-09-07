package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.cancel.model.SalesReturnOrderItem;
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
            "insert into sales_returned_order_item (itemStatus, ",
            "syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
            "salesReturnedOrderNo, warehouseId, ",
            "warehouseName, productId, ",
            "productCode, productName, ",
            "productUnit, totalReturnedQuantity, ",
            "totalImperfectQuantity, totalInStockQuantity, ",
            "inStockImperfectQuantity, deliveredQuantity, ",
            "wmsInboundRecord, dataVersion, ",
            "createTime, lastUpdateTime,returnCause)",
            "values (#{itemStatus,jdbcType=TINYINT}, ",
            "#{syncToGongxiaoWarehouseFlag,jdbcType=TINYINT}, #{gongxiaoWarehouseInboundOrderNo,jdbcType=VARCHAR}, ",
            "#{salesReturnedOrderNo,jdbcType=VARCHAR}, #{warehouseId,jdbcType=VARCHAR}, ",
            "#{warehouseName,jdbcType=VARCHAR}, #{productId,jdbcType=VARCHAR}, ",
            "#{productCode,jdbcType=VARCHAR}, #{productName,jdbcType=VARCHAR}, ",
            "#{productUnit,jdbcType=VARCHAR}, #{totalReturnedQuantity,jdbcType=INTEGER}, ",
            "#{totalImperfectQuantity,jdbcType=INTEGER}, #{totalInStockQuantity,jdbcType=INTEGER}, ",
            "#{inStockImperfectQuantity,jdbcType=INTEGER}, #{deliveredQuantity,jdbcType=INTEGER}, ",
            "#{wmsInboundRecord,jdbcType=VARCHAR}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP},#{returnCause,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "salesReturnOrderItem.rowId")
    int addSalesReturnOrderItem(SalesReturnOrderItem salesReturnOrderItem);
    @Select({
            "select",
            "rowId, itemStatus, syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
            "salesReturnedOrderNo, warehouseId, warehouseName, productId, productCode, productName, ",
            "productUnit, totalReturnedQuantity, totalImperfectQuantity, totalInStockQuantity, ",
            "inStockImperfectQuantity, deliveredQuantity, wmsInboundRecord, dataVersion, ",
            "createTime, lastUpdateTime,returnCause",
            "from sales_returned_order_item",
            "where salesReturnedOrderNo = #{salesReturnedOrderNo,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="rowId", property="rowId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="itemStatus", property="itemStatus", jdbcType=JdbcType.TINYINT),
            @Result(column="syncToGongxiaoWarehouseFlag", property="syncToGongxiaoWarehouseFlag", jdbcType=JdbcType.TINYINT),
            @Result(column="gongxiaoWarehouseInboundOrderNo", property="gongxiaoWarehouseInboundOrderNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="salesReturnedOrderNo", property="salesReturnedOrderNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="warehouseId", property="warehouseId", jdbcType=JdbcType.VARCHAR),
            @Result(column="warehouseName", property="warehouseName", jdbcType=JdbcType.VARCHAR),
            @Result(column="productId", property="productId", jdbcType=JdbcType.VARCHAR),
            @Result(column="productCode", property="productCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="productName", property="productName", jdbcType=JdbcType.VARCHAR),
            @Result(column="productUnit", property="productUnit", jdbcType=JdbcType.VARCHAR),
            @Result(column="totalReturnedQuantity", property="totalReturnedQuantity", jdbcType=JdbcType.INTEGER),
            @Result(column="totalImperfectQuantity", property="totalImperfectQuantity", jdbcType=JdbcType.INTEGER),
            @Result(column="totalInStockQuantity", property="totalInStockQuantity", jdbcType=JdbcType.INTEGER),
            @Result(column="inStockImperfectQuantity", property="inStockImperfectQuantity", jdbcType=JdbcType.INTEGER),
            @Result(column="deliveredQuantity", property="deliveredQuantity", jdbcType=JdbcType.INTEGER),
            @Result(column="wmsInboundRecord", property="wmsInboundRecord", jdbcType=JdbcType.VARCHAR),
            @Result(column="dataVersion", property="dataVersion", jdbcType=JdbcType.BIGINT),
            @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="returnCause", property="returnCause", jdbcType=JdbcType.VARCHAR),
    })
    List<SalesReturnOrderItem> selectBySalesReturnedOrderNo(@Param("salesReturnedOrderNo") String salesReturnedOrderNo);
    @Update({
            "update sales_returned_order_item",
            "set gongxiaoWarehouseInboundOrderNo = #{gongxiaoWarehouseInboundOrderNo,jdbcType=VARCHAR},",
            "dataVersion = dataVersion+1",
            "where rowId = #{rowId,jdbcType=BIGINT}",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateInboundOrderNo(@Param("gongxiaoWarehouseInboundOrderNo") String gongxiaoWarehouseInboundOrderNo,@Param("rowId") Long rowId,@Param("dataVersion") Long dataVersion);
    @Update({
            "update sales_returned_order_item",
            "set totalInStockQuantity = #{totalInStockQuantity},",
            "wmsInboundRecord = #{wmsInboundRecord},",
            "dataVersion = dataVersion+1",
            "where rowId = #{rowId,jdbcType=BIGINT}",
            "and dataVersion = #{dataVersion,jdbcType=LONGVARCHAR}"
    })
    int updateInboundRecord(@Param("totalInStockQuantity") int totalInStockQuantity,@Param("wmsInboundRecord") String wmsInboundRecord,@Param("rowId") Long rowId,@Param("dataVersion") Long dataVersion );
    @Select({
            "select",
            "rowId, itemStatus, syncToGongxiaoWarehouseFlag, gongxiaoWarehouseInboundOrderNo, ",
            "salesReturnedOrderNo, warehouseId, warehouseName, productId, productCode, productName, ",
            "productUnit, totalReturnedQuantity, totalImperfectQuantity, totalInStockQuantity, ",
            "inStockImperfectQuantity, deliveredQuantity, wmsInboundRecord, dataVersion, ",
            "createTime, lastUpdateTime,returnCause",
            "from sales_returned_order_item",
            "where gongxiaoWarehouseInboundOrderNo = #{inBoundOrderNo} and productCode = #{productCode}"
    })
    SalesReturnOrderItem selectSalesReturnOrderItemByInBoundOrderNo(@Param("inBoundOrderNo") String inBoundOrderNo,@Param("productCode") String productCode);
}
