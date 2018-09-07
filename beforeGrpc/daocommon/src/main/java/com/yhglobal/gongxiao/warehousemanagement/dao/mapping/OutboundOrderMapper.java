package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.bo.OutboundOrderShowModel;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OutboundOrderMapper extends BaseMapper {

    @Insert({
            "insert into warehouse_outbound_order (",
            "orderStatus,projectId,batchNo,inventoryType,outboundType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,createdById,",
            "createdByName,expectedArrivalTime,createTime",
            ")",
            "values (",
            "#{record.orderStatus}, #{record.projectId}, #{record.batchNo},#{record.inventoryType},#{record.outboundType}, ",
            "#{record.gongxiaoOutboundOrderNo}, #{record.outboundOrderItemNo}, #{record.sourceChannel},",
            "#{record.syncToWmsFlag}, #{record.wmsOutboundOrderNo}, ",
            "#{record.purchaseOrderNo}, #{record.returnedOrderNo}, ",
            "#{record.salesOrderNo}, ",
            "#{record.warehouseId}, #{record.warehouseName}, #{record.deliverAddress}, ",
            "#{record.shippingMode}, ",
            "#{record.supplierName}, ",
            "#{record.shippingAddress},#{record.provinceName},#{record.cityName}, ",
            "#{record.customer},#{record.contactsPeople}, #{record.phoneNum}, ",
            "#{record.shippingExpense}, #{record.paidBy}, ",
            "#{record.note}, #{record.connectedProduct}, #{record.totalQuantity}, ",
            "#{record.outStockQuantity},#{record.imperfectQuantity},",
            "#{record.canceledQuantity},#{record.realOutStockQuantity},#{record.dataVersion}, ",
            "#{record.tracelog},#{record.createdById}, ",
            "#{record.createdByName},#{record.expectedArrivalTime},#{record.createTime})"
    })
    int insertOutStorageInfo(@Param("record") OutboundOrder outStorageInfo);

    @SelectProvider(type = WarehouseManagerDynamicalSQLBuilder.class, method = "selectOutStorageInfoByRequire")
    List<OutboundOrder> selectOutStorageInfoByRequire(@Param("projectId") String projectId, @Param("inventoryNum") String inventoryNum, @Param("salseNum") String salseNum, @Param("createTimeBeging") String createTimeBeging,
                                                      @Param("createTimeLast") String createTimeLast, @Param("warehouseId") String warehouseId, @Param("productCode") String productCode, @Param("finishTimeBegin") String finishTimeBegin,
                                                      @Param("finishTimeLast") String finishTimeLast, @Param("supplier") String supplier, @Param("customer") String customer);

    @Update({
            "update warehouse_outbound_order",
            "set orderStatus = 6, ",
            "transporter = #{record.transporter}, ",
            "tmsOrdNo = #{record.tmsOrdNo}, ",
            "tmsOrderStatus = #{record.tmsOrderStatus}, ",
            "tmsSignedBy = #{record.tmsSignedBy}, ",
            "tmsSignedPhone = #{record.tmsSignedPhone}, ",
            "tmsSingedTime = #{record.tmsSingedTime}, ",
            "note = #{record.note} ",
            "where gongxiaoOutboundOrderNo= #{record.gongxiaoOutboundOrderNo} and connectedProduct = #{record.connectedProduct}"
    })
    int sureSighIn(@Param("record")OutboundOrder outboundOrder);

    @Select({
            "select",
            "projectId, batchNo, orderStatus,outboundType,inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime",
            " from warehouse_outbound_order",
            "where projectId = #{projectId} "
    })
    List<OutboundOrder> selectAllOutStorageInfoByProjectId(@Param("projectId") String projectId);

    @Select({
            "select",
            "projectId, batchNo, orderStatus,outboundType,inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum, shippingExpense, paidBy,",
            "transporter,tmsOrdNo,tmsOrderStatus,tmsSignedBy,tmsSignedPhone,tmsSingedTime,note, ",
            "connectedProduct, totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime",
            " from warehouse_outbound_order",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    OutboundOrder selectOutStorageInfoById(@Param("projectId") String projectId,@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType,inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            " from warehouse_outbound_order",
            "where projectId = #{projectId} and salesOrderNo = #{salesOrderNo} and connectedProduct =#{productCode}"
    })
    OutboundOrder selectRecordBySalseNoAndProduct(@Param("projectId")String projectId, @Param("salesOrderNo")String salesOrderNo, @Param("productCode")String productCode);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType,inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime",
            " from warehouse_outbound_order",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    List<OutboundOrder> getOutboundRecord(@Param("projectId")String projectId,@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("signature") String signature);

    @Update({
            "update warehouse_outbound_order",
            "set",
            "totalQuantity = #{adjustmentQuantity}",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    int updateByProjectAndOutboundNo(@Param("projectId")String projectId,@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("adjustmentQuantity")int adjustmentQuantity);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType,inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            " from warehouse_outbound_order",
            "where projectId = #{projectId} and salesOrderNo = #{salesOrderNo}"
    })
    List<OutboundOrder> selectRecordBySalesNo(@Param("projectId")String projectId, @Param("salesOrderNo")String salesOrderNo);

    @Delete({
            "delete from warehouse_outbound_order",
            "where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    int deleteOrderByOrderNo(@Param("projectId")String projectId,@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo);

    @Select({
            "select",
            "projectId ",
            "from warehouse_outbound_order",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    String getProjectId(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType, inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            "from warehouse_outbound_order",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and connectedProduct = #{connectedProduct}"
    })
    OutboundOrder getOutboundRecordByOrderNo(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("connectedProduct")String connectedProduct);

    @Update({
            "update warehouse_outbound_order",
            "set orderStatus=#{record.orderStatus}," ,
            "outStockQuantity=#{record.outStockQuantity},",
            "imperfectQuantity=#{record.imperfectQuantity},",
            "transferQuantity=#{record.transferQuantity},",
            "realOutStockQuantity=#{record.realOutStockQuantity},",
            "tracelog = #{record.tracelog}",
            "where gongxiaoOutboundOrderNo= #{record.gongxiaoOutboundOrderNo} and connectedProduct = #{record.connectedProduct}"
    })
    int updateOutboundOrderByNo(@Param("record")OutboundOrder outboundOrder);

    @Update({
            "update warehouse_outbound_order",
            "set syncToWmsFlag= 1" ,
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and connectedProduct = #{productCode}"
    })
    int updateSyncWmsFlag(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("productCode") String productCode);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType, inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            "from warehouse_outbound_order",
            "where syncToWmsFlag = #{syncToWmsFlag}"
    })
    List<OutboundOrder> selectOutboundRecordByWmsFlag(@Param("syncToWmsFlag")int syncToWmsFlag);

    @Update({
            "update warehouse_outbound_order",
            "set orderStatus = #{orderStatus}," ,
            "tracelog = #{tracelog}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo}"
    })
    int cancelOrder(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("orderStatus") int orderStatus,@Param("tracelog") String tracelog);

    @Update({
            "update warehouse_outbound_order",
            "set " ,
            "tracelog = #{tracelog}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo}"
    })
    int cancelOrderFail(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("tracelog") String tracelog);


    @Update({
            "update warehouse_outbound_order",
            "set orderStatus = #{orderStatus}," ,
            "tracelog = #{tracelog}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo}"
    })
    int closeOrder(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("orderStatus") int orderStatus,@Param("tracelog") String tracelog);

    @Update({
            "update warehouse_outbound_order",
            "set tracelog = #{tracelog}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo}"
    })
    int closeOrderFail(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("tracelog") String tracelog);

    @Update({
            "update warehouse_outbound_order",
            "set syncToWmsFlag = #{syncToWmsFlag}, tracelog = #{tracelog}, retryTime = retryTime+1",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and connectedProduct = #{productCode}"
    })
    int notifyFail(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("productCode") String productCode,@Param("syncToWmsFlag")int syncToWmsFlag,@Param("tracelog") String tracelog);

    @Update({
            "update warehouse_outbound_order",
            "set syncToWmsFlag =#{syncToWmsFlag}, tracelog = #{tracelog}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and connectedProduct = #{productCode}"
    })
    int notifySuccess(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("productCode") String productCode,@Param("syncToWmsFlag")int syncToWmsFlag,@Param("tracelog") String tracelog);

    @Update({
            "update warehouse_outbound_order",
            "set tracelog = #{tracelog}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and connectedProduct = #{productCode}"
    })
    int notifyTMSFail(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("productCode") String productCode,@Param("tracelog") String tracelog);

    @Update({
            "update warehouse_outbound_order",
            "set syncToTmsFlag =1, tracelog = #{tracelog}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and connectedProduct = #{productCode}"
    })
    int notifyTMSSuccess(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("productCode") String productCode,@Param("tracelog") String tracelog);

    @Update({
            "update warehouse_outbound_order",
            "set syncToWmsFlag =3, syncToTmsFlag = 1,tracelog = #{tracelog}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and connectedProduct = #{productCode}"
    })
    int notifyWMSSuccessTmsFail(@Param("outboundOrderItemNo") String outboundOrderItemNo,@Param("productCode") String productCode,@Param("tracelog") String tracelog);

    @Select({
            "select",
            "projectId, batchNo, orderStatus,outboundType, inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime",
            " from warehouse_outbound_order",
            "where syncToWmsFlag = #{syncToWmsFlag} and syncToTmsFlag = #{syncToTmsFlag}"
    })
    List<OutboundOrder> selectRecordByWmsFlagAndTmsFlag(@Param("syncToWmsFlag") int syncWmsFlag,@Param("syncToTmsFlag")int syncTmsFlag);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType, inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            "from warehouse_outbound_order",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    OutboundOrder getOutboundRecordByGoxiaoOutNo(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType, inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            "from warehouse_outbound_order",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    List<OutboundOrder> judeOrderIfFinish(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType, inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            "from warehouse_outbound_order",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    OutboundOrder getOutStorageInfoByNo(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo);

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType, inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, deliverAddress, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            "from warehouse_outbound_order",
            "where salesOrderNo = #{salesOrderNo}"
    })
    List<OutboundOrder> judeOrderIfFinishSight(@Param("salesOrderNo") String salesOrderNo);

    @Update({
            "update warehouse_outbound_order",
            "set syncToWmsFlag = #{syncToWmsFlag}, dataVersion = dataVersion+1",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and dataVersion = #{dataVersion}"
    })
    int updateWmsFlagByOrder(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("syncToWmsFlag") int syncToWmsFlag,@Param("dataVersion") long dataVersion);
}
