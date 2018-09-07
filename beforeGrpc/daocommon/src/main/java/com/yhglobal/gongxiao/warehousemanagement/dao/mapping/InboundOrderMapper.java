package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InboundOrderMapper extends BaseMapper{

    @Insert({
            "insert into warehouse_inbound_order (",
            "projectId, batchNo,orderStatus, inboundType, purchaseType, gongxiaoInboundOrderNo, sourceChannel, syncToWmsFlag, wmsInboundOrderNo, purchaseOrderNo,",
            "salesOrderNo, returnedSalesOrderNo, warehouseId, warehouseName, contactsPeople,",
            "phoneNum,deliverAddress,shippingMode, note," ,
            "connectedProduct, connectedGood, totalQuantity, inTransitQuantity, inStockQuantity,",
            "imperfectQuantity, rejectedQuantity, realInStockQuantity, tracelog, dataVersion,",
            "createdById, createdByName, uniqueNo, expectedArrivalTime, createTime, lastUpdateTime)",
            "values (",
            "#{record.projectId},#{record.batchNo}, #{record.orderStatus}, #{record.inboundType},",
            "#{record.purchaseType},#{record.gongxiaoInboundOrderNo}, #{record.sourceChannel}, ",
            "#{record.syncToWmsFlag}, #{record.wmsInboundOrderNo}, ",
            "#{record.purchaseOrderNo}, ",
            "#{record.salesOrderNo}, #{record.returnedSalesOrderNo}, ",
            "#{record.warehouseId}, #{record.warehouseName},",
            "#{record.contactsPeople}, #{record.phoneNum}, #{record.deliverAddress},",
            "#{record.shippingMode}, #{record.note}, #{record.connectedProduct}, #{record.connectedGood}, ",
            "#{record.totalQuantity}, #{record.inTransitQuantity}, ",
            "#{record.inStockQuantity}, #{record.imperfectQuantity}, ",
            "#{record.rejectedQuantity},#{record.realInStockQuantity}, #{record.tracelog}, #{record.dataVersion}, ",
            "#{record.createdById}, ",
            "#{record.createdByName}, #{record.uniqueNo}, #{record.expectedArrivalTime}, #{record.createTime}, ",
            "#{record.lastUpdateTime})"
    })
    int insertOutStorageInfos(@Param("record") InboundOrder record);

    @SelectProvider(type=WarehouseManagerDynamicalSQLBuilder.class, method="selectInStorageInfo")
    List<InboundOrder> selectInStorageInfo(@Param("projectId") String projectId, @Param("gonxiaoInboundNo") String gonxiaoInboundNo,@Param("purchaseNo")String purchaseNo, @Param("productCode") String productCode, @Param("goodCode") String goodCode, @Param("createTime") String createTime, @Param("warehouseId") String warehouseId, @Param("supplierName") String supplierName);

    @Update({
            "update warehouse_inbound_order",
            "set orderStatus = #{orderStatus}, ",
            "inTransitQuantity = #{afterInTransitQuantity}, ",
            "inStockQuantity = #{afterInStockQuantity}, ",
            "imperfectQuantity = #{afterImperfectQuantity}, ",
            "realInStockQuantity = #{afterRealInStockQuantity}, ",
            "tracelog = #{tracelog}, ",
            "dataVersion = #{dataVersion} + 1",
            "where rowId = #{rowId} and dataVersion = #{dataVersion}"
    })
    int updateInStorageInfo(@Param("rowId")long rowId,@Param("afterRealInStockQuantity")int afterRealInStockQuantity,@Param("afterImperfectQuantity")int afterImperfectQuantity,@Param("afterInStockQuantity")int afterInStockQuantity,@Param("afterInTransitQuantity")int afterInTransitQuantity,@Param("tracelog")String tracelog,@Param("orderStatus") int orderStatus,@Param("dataVersion")long dataVersion);

    @Select({
            "select ",
            "projectId,batchNo,orderStatus,inboundType,purchaseType,gongxiaoInboundOrderNo,",
            "purchaseOrderNo, warehouseId, warehouseName,contactsPeople,phoneNum,",
            "deliverAddress,shippingMode,note,tracelog,createTime,lastUpdateTime",
            "from warehouse_inbound_order",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{inventoryNum}"
    })
    InboundOrder selectRecordById(@Param("projectId")String projectId,@Param("inventoryNum")String inventoryNum);

    @SelectProvider(type=WarehouseManagerDynamicalSQLBuilder.class, method="selectRecordByPurchaseNo")
    List<InboundOrder> selectRecordByPurchaseNo(@Param("projectId")String projectId,@Param("purchaseNo") String purchaseNo,@Param("inventoryNum") String inventoryNum,@Param("productCode") String productCode,@Param("createTime") String createTime,@Param("warehouseId") String warehouseId,@Param("supplierName") String supplierName);

    @Select({
            "select ",
            "projectId,batchNo,orderStatus,inboundType,purchaseType,gongxiaoInboundOrderNo,",
            "purchaseOrderNo, warehouseId, warehouseName,contactsPeople,phoneNum,",
            "deliverAddress,shippingMode,note,tracelog,createTime,lastUpdateTime",
            "from warehouse_inbound_order",
            "where projectId = #{projectId} and purchaseOrderNo = #{purchaseNo}"
    })
    List<InboundOrder> selectInboundRecordByPurchaseNo(@Param("projectId")String projectId, @Param("purchaseNo")String purchaseNo);

    @Update({
            "update warehouse_inbound_order",
            "set totalQuantity = #{targetQuantity}, ",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int updateByProjectIdAndGongxiaoInboundOrderNo(@Param("projectId")String projectId,@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("targetQuantity")int targetQuantity);

    @Select({
            "select ",
            "projectId,batchNo,orderStatus,inboundType,purchaseType,gongxiaoInboundOrderNo,",
            "purchaseOrderNo, warehouseId, warehouseName,contactsPeople,phoneNum,",
            "deliverAddress,shippingMode,note,tracelog,createTime,lastUpdateTime",
            "from warehouse_inbound_order",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    List<InboundOrder> getInboundRecord(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("signature") String signature);

    @Delete({
            "delete from warehouse_inbound_order",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int deleteOrderByOrderNo(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Select({
            "select ",
            "projectId, batchNo,orderStatus, inboundType, purchaseType, gongxiaoInboundOrderNo, sourceChannel, syncToWmsFlag, wmsInboundOrderNo, purchaseOrderNo,",
            "salesOrderNo, returnedSalesOrderNo, warehouseId, warehouseName, contactsPeople,",
            "phoneNum,deliverAddress,shippingMode, note," ,
            "connectedProduct, connectedGood, totalQuantity, inTransitQuantity, inStockQuantity,",
            "imperfectQuantity, rejectedQuantity, realInStockQuantity, tracelog, dataVersion,",
            "createdById, createdByName, expectedArrivalTime, createTime, lastUpdateTime ",
            "from warehouse_inbound_order",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    InboundOrder getRecordByInBoundNo(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Select({
            "select ",
            "count(uniqueNo)",
            "from warehouse_inbound_order",
            "where uniqueNo = #{uniqueNo} limit 1"
    })
    int judgeExit(@Param("uniqueNo") String uniqueNo);

    @Update({
            "update warehouse_inbound_order",
            "set orderStatus = 2 ,",
            "realInStockQuantity = totalQuantity ",
            "where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int updateOrderStatus(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Select({
            "select ",
            "rowId,projectId, batchNo,orderStatus, inboundType, purchaseType, gongxiaoInboundOrderNo, sourceChannel, syncToWmsFlag, wmsInboundOrderNo, purchaseOrderNo,",
            "salesOrderNo, returnedSalesOrderNo, warehouseId, warehouseName, contactsPeople,",
            "phoneNum,deliverAddress,shippingMode, note," ,
            "connectedProduct, connectedGood, totalQuantity, inTransitQuantity, inStockQuantity,",
            "imperfectQuantity, rejectedQuantity, realInStockQuantity, tracelog, dataVersion,",
            "createdById, createdByName, expectedArrivalTime, createTime, lastUpdateTime ",
            "from warehouse_inbound_order",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} limit 1"
    })
    InboundOrder getInboundRecordByOrderNo(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Select({
            "select ",
            "orderStatus, ",
            "tracelog, ",
            "totalQuantity , ",
            "inTransitQuantity , ",
            "inStockQuantity , ",
            "imperfectQuantity , ",
            "realInStockQuantity",
            "from warehouse_inbound_order",
            "where projectId=#{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} limit 1"
    })
    InboundOrder selectRecordByOrderNo(@Param("projectId") String projectId,@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo);

    @Update({
            "update warehouse_inbound_order",
            "set syncToWmsFlag = 1",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int updateSyncWmsFlag(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo);

    @Select({
            "select ",
            "projectId, batchNo,orderStatus, inboundType,purchaseType,gongxiaoInboundOrderNo, sourceChannel, syncToWmsFlag, wmsInboundOrderNo, purchaseOrderNo,",
            "salesOrderNo, returnedSalesOrderNo, warehouseId, warehouseName, contactsPeople,",
            "phoneNum,deliverAddress,shippingMode, note," ,
            "connectedProduct, connectedGood, totalQuantity, inTransitQuantity, inStockQuantity,",
            "imperfectQuantity, rejectedQuantity, realInStockQuantity, tracelog, dataVersion,",
            "createdById, createdByName, expectedArrivalTime, createTime, lastUpdateTime ",
            "from warehouse_inbound_order",
            "where syncToWmsFlag = #{syncToWmsFlag} and retryTime"
    })
    List<InboundOrder> selectInboundRecordByWmsFlag(@Param("syncToWmsFlag")int syncToWmsFlag);

    @Update({
            "update warehouse_inbound_order",
            "set orderStatus = #{orderStatus},",
            "tracelog = #{traceLog}",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int cancelOrder(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("orderStatus")int orderStatus,@Param("traceLog")String traceLog);

    @Update({
            "update warehouse_inbound_order",
            "set tracelog = #{traceLog}",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int cancelOrderFail(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("traceLog")String traceLog);

    @Update({
            "update warehouse_inbound_order",
            "set orderStatus = #{orderStatus},",
            "tracelog = #{traceLog}",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int closeOrder(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("orderStatus")int orderStatus,@Param("traceLog")String traceLog);

    @Update({
            "update warehouse_inbound_order",
            "set tracelog = #{traceLog}",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int closeOrderFail(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("traceLog")String traceLog);

    @Update({
            "update warehouse_inbound_order",
            "set tracelog = #{traceLog}, retryTime = retryTime+1",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int notifyFail(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("traceLog")String traceLog);

    @Update({
            "update warehouse_inbound_order",
            "set syncToWmsFlag = #{syncToWmsFlag}, tracelog = #{traceLog}",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    int notifySuccess(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("syncToWmsFlag")int syncToWmsFlag,@Param("traceLog")String traceLog);

}
