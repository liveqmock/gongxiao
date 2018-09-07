package com.yhglobal.gongxiao.wmscomfirm.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface OutboundOrderItemMapper extends BaseMapper {

    @Select({
            "select",
            "rowId,inventoryType,purchaseType,projectId,outboundOrderItemNo,batchNo, ",
            "purchaseOrderNo, salesOrderNo, itemStatus, gongxiaoOutboundOrderNo, ",
            "warehouseId, warehouseName, productId, productCode," ,
            "productName, productUnit, totalQuantity, ",
            "outStockQuantity, imperfectQuantity, ",
            "canceledQuantity, realOutStockQuantity, returnQuantity, dataVersion, ",
            "createTime",
            "from ${prefix}_warehouse_outbound_order_item",
            "where outboundOrderItemNo=#{outboundOrderItemNo}"
    })
    OutboundOrderItem getOutboundOrderItemByItemNo(@Param("outboundOrderItemNo") String outboundOrderItemNo,@Param("prefix") String prefix);

}
