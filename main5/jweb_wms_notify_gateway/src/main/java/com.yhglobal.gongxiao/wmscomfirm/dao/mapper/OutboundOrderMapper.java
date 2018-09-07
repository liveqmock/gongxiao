package com.yhglobal.gongxiao.wmscomfirm.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrder;
import com.yhglobal.gongxiao.wmscomfirm.dao.WarehouseManagerDynamicalSQLBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OutboundOrderMapper extends BaseMapper {

    @Select({
            "select",
            "projectId, batchNo, orderStatus, outboundType, inventoryType, ",
            "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, syncToTmsFlag, wmsOutboundOrderNo, ",
            "purchaseOrderNo, returnedOrderNo, salesOrderNo,",
            "warehouseId, warehouseName, shippingMode,",
            "supplierName, shippingAddress, provinceName, cityName, customerId, customer,",
            "contactsPeople, phoneNum,",
            "shippingExpense, paidBy, note, connectedProduct, ",
            "totalQuantity, outStockQuantity,imperfectQuantity,transferQuantity,",
            "canceledQuantity, realOutStockQuantity, dataVersion,tracelog,",
            "createdById, createdByName,expectedArrivalTime,createTime,lastUpdateTime",
            "from ${prefix}_warehouse_outbound_order",
            "where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo}"
    })
    OutboundOrder getOutboundRecordByGoxiaoOutNo(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("prefix")String prefix);
}
