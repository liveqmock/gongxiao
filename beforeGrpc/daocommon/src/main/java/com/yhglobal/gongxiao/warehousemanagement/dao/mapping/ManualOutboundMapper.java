package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualOutboundOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ManualOutboundMapper extends BaseMapper {

    @Select({
            "select",
            "id,projectId, batchNo, " ,
            "supplierName, productCode,",
            "productName, productUnit, warehouseId," ,
            "warehouseName, recieveAddress," ,
            "inboundType, quantity, guidePrice," ,
            "purchasePrice, costPrice, createdById, createdByName," ,
            "note, status,createTime, lastUpdateTime",
            "from manual_outbound_order"
    })
    List<ManualOutboundOrder> selectAllRecord();

    @Insert({
            "insert into manual_outbound_order (",
            "orderNo, ",
            "projectId, ",
            "supplierName, ",
            "warehouseId, ",
            "warehouseName, deliverAddress, ",
            "outboundType, quantity, ",
            "createdById, createdByName, ",
            "note, createTime)",
            "values ",
            "( #{record.orderNo}, ",
            "#{record.projectId}, ",
            "#{record.supplierName},",
            "#{record.warehouseId}, ",
            "#{record.warehouseName}, #{record.deliverAddress}, ",
            "#{record.outboundType}, #{record.quantity}, ",
            "#{record.createdById}, ",
            "#{record.createdByName}, #{record.note}, ",
            "#{record.createTime}",
            ")"
    })
     int insertManualOutboundInfo(@Param("record")ManualOutboundOrder manualOutboundOrder);

    @Update({
            "update manual_inbound_order",
            "set ",
            "realQuantity = #{record.realQuantity}, ",
            "status = 1 ",
            "where projectId = #{record.projectId} and orderNo = #{record.orderNo}"
    })
     int updateManualOutboundInfo(@Param("record") ManualOutboundOrder manualOutboundOrder);

    @Update({
            "update manual_inbound_order",
            "set syncEasFlag = 1 ",
            "where orderNo = #{orderNo}"
    })
    int updateEasFlagInfo(@Param("orderNo") String orderNo);

}
