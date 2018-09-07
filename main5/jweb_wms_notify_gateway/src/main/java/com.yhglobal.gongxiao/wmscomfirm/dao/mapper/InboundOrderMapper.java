package com.yhglobal.gongxiao.wmscomfirm.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouseapi.model.InboundOrder;
import org.apache.ibatis.annotations.*;


public interface InboundOrderMapper extends BaseMapper{

    @Select({
            "select ",
            "projectId, batchNo,orderStatus, inboundType, purchaseType, gongxiaoInboundOrderNo, sourceChannel, syncToWmsFlag, wmsInboundOrderNo, purchaseOrderNo,",
            "salesOrderNo, returnedSalesOrderNo, warehouseId, warehouseName, contactsPeople,",
            "phoneNum,deliverAddress,shippingMode, note," ,
            "connectedProduct, connectedGood, totalQuantity, inTransitQuantity, inStockQuantity,",
            "imperfectQuantity, rejectedQuantity, realInStockQuantity, tracelog, dataVersion,",
            "createdById, createdByName, expectedArrivalTime, createTime, lastUpdateTime ",
            "from ${prefix}_warehouse_inbound_order",
            "where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo}"
    })
    InboundOrder getInboundRecordByOrderNo(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("prefix") String prefix);

}
