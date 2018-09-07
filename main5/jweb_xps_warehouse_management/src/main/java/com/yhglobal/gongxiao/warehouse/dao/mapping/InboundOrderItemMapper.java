package com.yhglobal.gongxiao.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InboundOrderItemMapper extends BaseMapper {

    @Insert({
            "<script>",
            "insert into  ${prefix}_warehouse_inbound_order_item (projectId, ",
            "purchaseOrderNo, inboundOrderItemNo, batchNo,",
            "itemStatus, gongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, productId, productCode, ",
            "productName, productUnit, totalQuantity, ",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity, wmsInboundRecord, purchasePrice, costPrice, dataVersion,",
            "createTime)",
            "values ",
            "<foreach  collection='recordList' item='record' separator=','>",
            "(#{record.projectId}, ",
            "#{record.purchaseOrderNo}, #{record.inboundOrderItemNo}, #{record.batchNo}, ",
            "#{record.itemStatus}, #{record.gongxiaoInboundOrderNo}, ",
            "#{record.warehouseId}, #{record.warehouseName}, ",
            "#{record.productId}, #{record.productCode}, ",
            "#{record.productName}, #{record.productUnit}, ",
            "#{record.totalQuantity}, #{record.inTransitQuantity}, ",
            "#{record.inStockQuantity}, #{record.imperfectQuantity}, ",
            "#{record.rejectedQuantity}, #{record.wmsInboundRecord},",
            "#{record.purchasePrice}, #{record.costPrice}, #{record.dataVersion}, ",
            "#{record.createTime})",
            "</foreach>",
            "</script>"
    })
    int inserInboundOrderItemInfo(@Param("recordList") List<InboundOrderItem> recordList,@Param("prefix") String prefix);

    @Select({
            "select",
            "projectId, inboundOrderItemNo, itemStatus, gongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord, purchasePrice, ",
            "costPrice, dataVersion, createTime, lastUpdateTime",
            "from ${prefix}_warehouse_inbound_order_item ",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    List<InboundOrderItem> selectInboundOrderItemInfosById(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_inbound_order_item",
            "set ",
            "inTransitQuantity = #{afterInTransitQuantity}, ",
            "inStockQuantity = #{afterInStockQuantity}, ",
            "imperfectQuantity = #{afterImperfectQuantity}, ",
            "realInStockQuantity = #{afterRealInStockQuantity}",
            "where rowId = #{rowId}"
    })
    int updateInStorageDetailInfo(@Param("rowId")long rowId,@Param("afterImperfectQuantity")int afterImperfectQuantity,@Param("afterInStockQuantity")int afterInStockQuantity,@Param("afterInTransitQuantity")int afterInTransitQuantity,@Param("afterRealInStockQuantity")int afterRealInStockQuantity,@Param("prefix") String prefix);

    @Select({
            "select",
            "projectId, purchaseOrderNo,inboundOrderItemNo, itemStatus, gongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord, purchasePrice, ",
            "costPrice, dataVersion, createTime",
            "from ${prefix}_warehouse_inbound_order_item",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    List<InboundOrderItem> selectInStorageDetailInfoByPurchaseNo(@Param("projectId") String projectId, @Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("prefix") String prefix);

    @Select({
            "select",
            "projectId, purchaseOrderNo,inboundOrderItemNo, itemStatus, gongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, productId,batchNo, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord, purchasePrice, ",
            "costPrice, dataVersion, createTime",
            "from ${prefix}_warehouse_inbound_order_item",
            "where projectId = #{projectId} and purchaseOrderNo = #{purchaseOrderNo}"
    })
    List<InboundOrderItem> selectByPurchaseNo(@Param("projectId") String projectId, @Param("purchaseOrderNo") String purchaseOrderNo,@Param("prefix") String prefix);

    @Select({
            "select",
            "projectId, purchaseOrderNo,inboundOrderItemNo, batchNo, itemStatus, gongxiaoInboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord, purchasePrice, ",
            "costPrice, dataVersion, createTime",
            "from ${prefix}_warehouse_inbound_order_item",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    List<InboundOrderItem> selectInboundOrderItemByNo(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_inbound_order_item",
            "set itemStatus = 3",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int cancelOrder(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("prefix") String prefix);

    @Update({
            "update ${prefix}_warehouse_inbound_order_item",
            "set itemStatus = 2",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int closeOrder(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("prefix") String prefix);

    @Select({
            "select",
            "rowId, projectId, purchaseOrderNo,inboundOrderItemNo, batchNo, itemStatus, gongxiaoInboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord, purchasePrice, ",
            "costPrice, dataVersion, createTime",
            "from ${prefix}_warehouse_inbound_order_item",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} and productCode = #{productCode}"
    })
    InboundOrderItem selectOrderItemByNo(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("productCode")String productCode,@Param("prefix") String prefix);
}
