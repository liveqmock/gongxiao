package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InboundOrderItemMapper extends BaseMapper {

    @Insert({
            "<script>",
            "insert into  warehouse_inbound_order_item (projectId, ",
            "purchaseOrderNo, inboundOrderItemNo, batchNo,",
            "itemStatus, gongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, productId, productCode, ",
            "productName, productUnit, totalQuantity, ",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity, wmsInboundRecord, dataVersion,",
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
            "#{record.rejectedQuantity}, #{record.wmsInboundRecord},#{record.dataVersion}, ",
            "#{record.createTime})",
            "</foreach>",
            "</script>"
    })
    int inserInboundOrderItemInfo(@Param("recordList") List<InboundOrderItem> recordList);

    @Select({
            "select",
            "projectId, inboundOrderItemNo, itemStatus, gongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord,dataVersion, ",
            "createTime, lastUpdateTime",
            "from warehouse_inbound_order_item",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    List<InboundOrderItem> selectInboundOrderItemInfosById(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Update({
            "update warehouse_inbound_order_item",
            "set ",
            "inTransitQuantity = #{afterInTransitQuantity}, ",
            "inStockQuantity = #{afterInStockQuantity}, ",
            "imperfectQuantity = #{afterImperfectQuantity}, ",
            "realInStockQuantity = #{afterRealInStockQuantity}",
            "where rowId = #{rowId}"
    })
    int updateInStorageDetailInfo(@Param("rowId")long rowId,@Param("afterImperfectQuantity")int afterImperfectQuantity,@Param("afterInStockQuantity")int afterInStockQuantity,@Param("afterInTransitQuantity")int afterInTransitQuantity,@Param("afterRealInStockQuantity")int afterRealInStockQuantity);

    @Select({
            "select",
            "projectId, purchaseOrderNo,inboundOrderItemNo, itemStatus, gongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord,dataVersion, ",
            "createTime",
            "from warehouse_inbound_order_item",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    List<InboundOrderItem> selectInStorageDetailInfoByPurchaseNo(@Param("projectId") String projectId, @Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Select({
            "select",
            "projectId, purchaseOrderNo,inboundOrderItemNo, itemStatus, gongxiaoInboundOrderNo, warehouseId, ",
            "warehouseName, productId,batchNo, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord,dataVersion, ",
            "createTime",
            "from warehouse_inbound_order_item",
            "where projectId = #{projectId} and purchaseOrderNo = #{purchaseOrderNo}"
    })
    List<InboundOrderItem> selectByPurchaseNo(@Param("projectId") String projectId, @Param("purchaseOrderNo") String purchaseOrderNo);


    @Update({
            "update warehouse_inbound_order_item",
            "set",
            "totalQuantity = #{targetQuantity}",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} and productCode = #{record.productCode}"
    })
    int updateByCondition(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("productCode") String productCode,@Param("originalQuantity") int originalQuantity,@Param("adjustmentQuantity") int adjustmentQuantity,@Param("targetQuantity") int targetQuantity);

    @Delete({
            "delete from warehouse_inbound_order_item",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int deleteOrderByOrderNo(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Update({
            "update warehouse_inbound_order_item",
            "set itemStatus = 2,",
            "realInStockQuantity = totalQuantity",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int updateIOrderDetailStatus(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Select({
            "select",
            "projectId, purchaseOrderNo,inboundOrderItemNo, batchNo, itemStatus, gongxiaoInboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord,dataVersion, ",
            "createTime",
            "from warehouse_inbound_order_item",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    List<InboundOrderItem> selectInboundOrderItemByNo(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Update({
            "update warehouse_inbound_order_item",
            "set itemStatus = 3",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int cancelOrder(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo);

    @Update({
            "update warehouse_inbound_order_item",
            "set itemStatus = 2",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int closeOrder(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo);

    @Select({
            "select",
            "rowId, projectId, purchaseOrderNo,inboundOrderItemNo, batchNo, itemStatus, gongxiaoInboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity,",
            "inTransitQuantity, inStockQuantity, imperfectQuantity, ",
            "rejectedQuantity,realInStockQuantity, wmsInboundRecord,dataVersion, ",
            "createTime",
            "from warehouse_inbound_order_item",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} and productCode = #{productCode}"
    })
    InboundOrderItem selectOrderItemByNo(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("productCode")String productCode);
}
