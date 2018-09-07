package com.yhglobal.gongxiao.wmscomfirm.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.wmscomfirm.dao.WarehouseManagerDynamicalSQLBuilder;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsIntboundRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WmsInboundMapper extends BaseMapper {
    @Insert({
            "insert into wms_inbound_record (",
            "projectId, supplier, purchaseType, inboundType, gongxiaoInboundOrderNo, wmsInboundOrderNo, ",
            "easInboundOrderNo, purchaseOrderNo, batchNo, warehouseId, warehouseName, productCode, ",
            "inStockQuantity, easFlag, createTime",
            ")",
            "values (",
            "#{record.projectId}, #{record.supplier}, #{record.purchaseType}, #{record.inboundType}, #{record.gongxiaoInboundOrderNo},",
            "#{record.wmsInboundOrderNo}, #{record.easInboundOrderNo}, ",
            "#{record.purchaseOrderNo}, #{record.batchNo}, ",
            "#{record.warehouseId}, #{record.warehouseName}, ",
            "#{record.productCode}, #{record.inStockQuantity}, ",
            "#{record.easFlag}, #{record.createTime}",
            ")"
    })
    int insertWmsInboundInfo(@Param("record") WmsIntboundRecord wmsIntboundRecord);

}
