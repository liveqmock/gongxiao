package com.yhglobal.gongxiao.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OutboundOrderItemMapper extends BaseMapper {

    @Insert({
            "<script>",
            "insert into ${prefix}_warehouse_outbound_order_item (",
            "inventoryType,purchaseType,",
            "projectId,",
            "outboundOrderItemNo,batchNo,",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo,",
            "warehouseId, warehouseName, productId,productCode," ,
            "productName, productUnit, totalQuantity,",
            "outStockQuantity, imperfectQuantity,",
            "canceledQuantity, returnQuantity,",
            "salesGuidePrice, wholesalePrice,",
            "purchasePrice, createTime)",
            "values ",
            "<foreach collection='recordList'  item='record' separator=',' >",
            "(#{record.inventoryType},#{record.purchaseType},#{record.projectId},",
            "#{record.outboundOrderItemNo}, #{record.batchNo}, ",
            "#{record.purchaseOrderNo}, #{record.salesOrderNo}, #{record.itemStatus}, ",
            "#{record.gongxiaoOutboundOrderNo}, #{record.warehouseId}, ",
            "#{record.warehouseName}, #{record.productId}, ",
            "#{record.productCode}, ",
            "#{record.productName}, #{record.productUnit},",
            "#{record.totalQuantity}, ",
            "#{record.outStockQuantity}, ",
            "#{record.imperfectQuantity}, ",
            "#{record.canceledQuantity}, ",
            "#{record.returnQuantity}, ",
            "#{record.salesGuidePrice}, #{record.wholesalePrice},",
            "#{record.purchasePrice}, #{record.createTime}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertOutboundOrderItem(@Param("recordList") List<OutboundOrderItem> recordList,@Param("prefix") String prefix);

    @Select({
            "select",
            "inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, returnQuantity, salesGuidePrice, wholesalePrice, purchasePrice, dataVersion, ",
            "createTime",
            "from ${prefix}_warehouse_outbound_order_item",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    List<OutboundOrderItem> selectOutboundOrderItems(@Param("projectId") String projectId, @Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_outbound_order_item",
            "set ",
            "outStockQuantity = #{outStockQuantity},",
            "imperfectQuantity = #{imperfectQuantity},",
            "realOutStockQuantity = #{realOutStockQuantity},",
            "dataVersion = #{dataVersion}+1",
            "where rowId = #{rowId} and dataVersion=#{dataVersion}"
    })
    int updateOutboundOrderItemInfo(@Param("rowId")long rowId, @Param("outboundOrderNo")String outboundOrderNo, @Param("outStockQuantity")int outStockQuantity, @Param("imperfectQuantity")int imperfectQuantity, @Param("realOutStockQuantity")int realOutStockQuantity, @Param("dataVersion")long dataVersion,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_outbound_order_item",
            "set",
            "totalQuantity = #{record.totalQuantity} ",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{record.gongxiaoOutboundOrderNo} and productCode = #{productCode}"
    })
    int updateByCondition(@Param("projectId")String projectId,@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("productCode")String productCode,@Param("adjustmentQuantity")int adjustmentQuantity,@Param("prefix") String prefix);

    @Select({
            "select",
            "inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, returnQuantity, salesGuidePrice, wholesalePrice, purchasePrice, dataVersion, ",
            "createTime",
            "from ${prefix}_warehouse_outbound_order_item",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    List<OutboundOrderItem> selectRecordItemByOutboundOrderNo(@Param("projectId")String projectId, @Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("prefix") String prefix);

    @Delete({
            "delete from ${prefix}_warehouse_outbound_order_item",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    int deleteOrderByOrderNo(@Param("projectId")String projectId,@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_outbound_order_item",
            "set itemStatus = 4",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    int sureSighIn(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("prefix") String prefix);

    @Select({
            "select",
            "rowId, inventoryType, purchaseType, inventoryType, projectId, outboundOrderItemNo, batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, returnQuantity, salesGuidePrice, wholesalePrice, purchasePrice, dataVersion, ",
            "createTime",
            "from ${prefix}_warehouse_outbound_order_item",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    List<OutboundOrderItem> selectOutboundOrderItemByNo(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_outbound_order_item",
            "set itemStatus = 3 ",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    int cancelOrder(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_outbound_order_item",
            "set itemStatus = 4 ",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    int closeOrder(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("prefix") String prefix);

    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, returnQuantity, salesGuidePrice, wholesalePrice, purchasePrice, dataVersion, ",
            "createTime",
            "from ${prefix}_warehouse_outbound_order_item",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and productCode = #{productCode} and inventoryType = #{inventoryType}"
    })
    List<OutboundOrderItem> selectItemByNoAndProductCode(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("productCode") String productCode,@Param("inventoryType") int inventoryType,@Param("prefix") String prefix);


    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, returnQuantity, salesGuidePrice, wholesalePrice, purchasePrice, dataVersion, ",
            "createTime",
            "from ${prefix}_warehouse_outbound_order_item",
            "where rowId=#{rowId}"
    })
    OutboundOrderItem getOutboundOrderItemById(@Param("rowId")long rowId,@Param("prefix") String prefix);

    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, returnQuantity, salesGuidePrice, wholesalePrice, purchasePrice, dataVersion, ",
            "createTime",
            "from ${prefix}_warehouse_outbound_order_item",
            "where outboundOrderItemNo=#{outboundOrderItemNo}"
    })
    OutboundOrderItem getOutboundOrderItemByItemNo(@Param("outboundOrderItemNo")String outboundOrderItemNo,@Param("prefix") String prefix);


    @Select({
            "select",
            "sum(outStockQuantity) as outStockQuantity, sum(canceledQuantity) as canceledQuantity ",
            "from ${prefix}_warehouse_outbound_order_item",
            "where projectId = #{projectId} and salesOrderNo = #{salesOrderNo} and productCode = #{productCode}",
            "group by projectId,salesOrderNo,productCode"
    })
    OutboundOrderItem selectRecordBySalseNoAndProduct(@Param("projectId")String projectId, @Param("salesOrderNo")String salesOrderNo, @Param("productCode")String productCode,@Param("prefix") String prefix);

    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, returnQuantity, salesGuidePrice, wholesalePrice, purchasePrice, dataVersion, ",
            "createTime",
            "from ${prefix}_warehouse_outbound_order_item",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and batchNo = #{batchNo} and productCode = #{productCode} and inventoryType = #{inventoryType}"
    })
    OutboundOrderItem selectItemByNoAndProductCodeAndBatchNo(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("batchNo") String batchNo,@Param("productCode") String productCode,@Param("inventoryType") int inventoryType,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_outbound_order_item",
            "set returnQuantity = returnQuantity - #{quantity}, ",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and productCode = #{productCode}"
    })
    int midifyReturnQuantity(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo, @Param("productCode") String productCode, @Param("quantity") int quantity, @Param("prefix") String prefix);
}
