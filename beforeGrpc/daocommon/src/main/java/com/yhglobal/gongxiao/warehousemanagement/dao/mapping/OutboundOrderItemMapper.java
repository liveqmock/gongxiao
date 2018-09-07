package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OutboundOrderItemMapper extends BaseMapper {

    @Insert({
            "<script>",
            "insert into warehouse_outbound_order_item (",
            "inventoryType,purchaseType,",
            "projectId,",
            "outboundOrderItemNo,batchNo,",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo,",
            "warehouseId, warehouseName, productId,productCode," ,
            "productName, productUnit, totalQuantity,",
            "outStockQuantity, imperfectQuantity,",
            "canceledQuantity,",
            "createTime)",
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
            "#{record.createTime}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertOutboundOrderItem(@Param("recordList") List<OutboundOrderItem> recordList);

    @Select({
            "select",
            "inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, dataVersion, ",
            "createTime",
            "from warehouse_outbound_order_item",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    List<OutboundOrderItem> selectOutboundOrderItems(@Param("projectId") String projectId, @Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo);

    @Update({
            "update warehouse_outbound_order_item",
            "set ",
            "outStockQuantity = #{outStockQuantity},",
            "imperfectQuantity = #{imperfectQuantity},",
            "realOutStockQuantity = #{realOutStockQuantity},",
            "dataVersion = #{dataVersion}+1",
            "where rowId = #{rowId} and dataVersion=#{dataVersion}"
    })
    int updateOutboundOrderItemInfo(@Param("rowId")long rowId, @Param("outboundOrderNo")String outboundOrderNo, @Param("outStockQuantity")int outStockQuantity, @Param("imperfectQuantity")int imperfectQuantity, @Param("realOutStockQuantity")int realOutStockQuantity, @Param("dataVersion")long dataVersion);

    @Update({
            "update warehouse_outbound_order_item",
            "set",
            "totalQuantity = #{record.totalQuantity} ",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{record.gongxiaoOutboundOrderNo} and productCode = #{productCode}"
    })
    int updateByCondition(@Param("projectId")String projectId,@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("productCode")String productCode,@Param("adjustmentQuantity")int adjustmentQuantity);

    @Select({
            "select",
            "inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, dataVersion, ",
            "createTime",
            "from warehouse_outbound_order_item",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    List<OutboundOrderItem> selectRecordItemByOutboundOrderNo(@Param("projectId")String projectId, @Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo);

    @Delete({
            "delete from warehouse_outbound_order_item",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    int deleteOrderByOrderNo(@Param("projectId")String projectId,@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo);

    @Update({
            "update warehouse_outbound_order_item",
            "set itemStatus = 4",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    int sureSighIn(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo);

    @Select({
            "select",
            "rowId, inventoryType, purchaseType, inventoryType, projectId, outboundOrderItemNo, batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, dataVersion, ",
            "createTime",
            "from warehouse_outbound_order_item",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    List<OutboundOrderItem> selectOutboundOrderItemByNo(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo);

    @Update({
            "update warehouse_outbound_order_item",
            "set itemStatus = 3 ",
            "where outboundOrderItemNo = #{outboundOrderItemNo}"
    })
    int cancelOrder(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo);

    @Update({
            "update warehouse_outbound_order_item",
            "set itemStatus = 4 ",
            "where outboundOrderItemNo = #{outboundOrderItemNo}"
    })
    int closeOrder(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo);

    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, dataVersion, ",
            "createTime",
            "from warehouse_outbound_order_item",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and productCode = #{productCode} and inventoryType = #{inventoryType}"
    })
    List<OutboundOrderItem> selectItemByNoAndProductCode(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("productCode") String productCode,@Param("inventoryType") int inventoryType);


    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, dataVersion, ",
            "createTime",
            "from warehouse_outbound_order_item",
            "where rowId=#{rowId}"
    })
    OutboundOrderItem getOutboundOrderItemById(@Param("rowId")long rowId);

    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, dataVersion, ",
            "createTime",
            "from warehouse_outbound_order_item",
            "where outboundOrderItemNo=#{outboundOrderItemNo}"
    })
    OutboundOrderItem getOutboundOrderItemByItemNo(@Param("outboundOrderItemNo")String outboundOrderItemNo);


    @Select({
            "select",
            "sum(outStockQuantity) as outStockQuantity, sum(canceledQuantity) as canceledQuantity ",
            "from warehouse_outbound_order_item",
            "where projectId = #{projectId} and salesOrderNo = #{salesOrderNo} and productCode = #{productCode}",
            "group by projectId,salesOrderNo,productCode"
    })
    OutboundOrderItem selectRecordBySalseNoAndProduct(@Param("projectId")String projectId, @Param("salesOrderNo")String salesOrderNo, @Param("productCode")String productCode);

    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, dataVersion, ",
            "createTime",
            "from warehouse_outbound_order_item",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and batchNo = #{batchNo} and productCode = #{productCode} and inventoryType = #{inventoryType}"
    })
    OutboundOrderItem selectItemByNoAndProductCodeAndBatchNo(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("batchNo") String batchNo,@Param("productCode") String productCode,@Param("inventoryType") int inventoryType);

}
