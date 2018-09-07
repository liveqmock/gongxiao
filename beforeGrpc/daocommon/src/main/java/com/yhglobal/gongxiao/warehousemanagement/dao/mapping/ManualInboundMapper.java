package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualInboundOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ManualInboundMapper extends BaseMapper {

    @Select({
            "select",
            "id, orderNo, projectId, " ,
            "batchNo,supplierName, ",
            "warehouseId," ,
            "warehouseName, recieveAddress," ,
            "inboundType, quantity, " ,
            "createdById, createdByName," ,
            "note, status,createTime, lastUpdateTime",
            "from manual_inbound_order"
    })
    List<ManualInboundOrder> selectAllRecord();

    @Insert({
            "insert into manual_inbound_order (",
            "orderNo, gongxiaoInboundOrderNo, projectId, batchNo, ",
            "supplierName, warehouseId, ",
            "warehouseName, recieveAddress, ",
            "inboundType, quantity, ",
            "createdById, createdByName, ",
            "note, status, createTime)",
            "values ",
            "( " ,
            "#{record.orderNo}, ",
            "#{record.gongxiaoInboundOrderNo}, ",
            "#{record.projectId}, ",
            "#{record.batchNo}, ",
            "#{record.supplierName},",
            "#{record.warehouseId}, ",
            "#{record.warehouseName}, #{record.recieveAddress}, ",
            "#{record.inboundType}, #{record.quantity}, ",
            "#{record.createdById}, ",
            "#{record.createdByName}, #{record.note}, ",
            "#{record.status}, #{record.createTime})"
    })
     int insertManualInboundInfo(@Param("record") ManualInboundOrder manualInboundOrder);

    @Update({
            "update manual_inbound_order",
            "set ",
            "realQuantity = #{record.realQuantity}, ",
            "status = #{record.status}",
            "where orderNo = #{record.orderNo}"
    })
     int updateManualInboundInfo(@Param("record") ManualInboundOrder manualInboundOrder);

    @Update({
            "update manual_inbound_order",
            "set ",
            "syncEasFlag = 1 ",
            "where orderNo = #{orderNo}"
    })
    int updateEasFlagInfo(@Param("orderNo") String orderNo);
}
