package com.yhglobal.gongxiao.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AllocateOrderMapper extends BaseMapper {

    @Insert({
            "insert into warehouse_transfer_order (",
            "allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOutId, warehouseOut, ",
            "warehouseEnterId, warehouseEnter, companyNameOut, companyNameEnter,deliverAddress, receiveAddress,",
            "alloteWay, status, batchNo, totalQuantity, realInQuantity, realOutQuantity, requireTime, syncEas, deadline, note,",
            "traceLog, dataVersion, createTime)",
            "values ",
            "( #{record.allocateNo}, ",
            "#{record.projectIdOut}, ",
            "#{record.projectIdEnter}, #{record.gongxiaoOutboundOrderNo},",
            "#{record.gongxiaoInboundOrderNo},#{record.warehouseOutId}, #{record.warehouseOut}, ",
            "#{record.warehouseEnterId},#{record.warehouseEnter}, #{record.companyNameOut}, ",
            "#{record.companyNameEnter},#{record.deliverAddress}, ",
            "#{record.receiveAddress}, #{record.alloteWay}, ",
            "#{record.status}, #{record.batchNo}, #{record.totalQuantity}, ",
            "#{record.realInQuantity}, #{record.realOutQuantity}, #{record.requireTime}, ",
            "#{record.syncEas}, #{record.deadline}, #{record.note},",
            " #{record.traceLog}, #{record.dataVersion}, #{record.createTime}",
            ")"
    })
    int insertAllocateOrder(@Param("record") AllocationOrder allocationOrder);

    @SelectProvider(type = AllocationDynamicalSQLBuilder.class, method = "selectrRecordByCondition")
    List<AllocationOrder> selectrRecordByCondition(@Param("projectId") String projectId, @Param("allocateNo") String allocateNo, @Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo, @Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,
                                                   @Param("warehouseOut") String warehouseOut, @Param("warehouseEnter") String warehouseEnter, @Param("createBeginTime") String createBeginTime, @Param("createEndTime") String createEndTime);

    @Select({
            "select",
            "id, allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOutId, warehouseOut, ",
            "warehouseEnterId, warehouseEnter, companyNameOut, companyNameEnter,deliverAddress, receiveAddress,",
            "alloteWay, status, batchNo, totalQuantity, realInQuantity, realOutQuantity, requireTime,deadline,syncEas, ",
            "easId,easOrderNumber,note,traceLog, dataVersion, createTime",
            "from warehouse_transfer_order",
            "where projectIdOut = #{projectId} and allocateNo = #{allocateNo}"
    })
    AllocationOrder selectInfoByAllocateNo(@Param("projectId") String projectId, @Param("allocateNo") String allocateNo);

    @Update({
            "update warehouse_transfer_order",
            "set realInQuantity = realInQuantity+#{quantity} ,",
            "where allocateNo = #{allocateNo}"
    })
    int updateAllocateOrderEntry(@Param("allocateNo") String allocateNo, @Param("quantity") int quantity);

    @Update({
            "update warehouse_transfer_order",
            "set realOutQuantity = realOutQuantity+#{quantity} ,",
            "where allocateNo = #{allocateNo}"
    })
    int updateAllocateOrderOut(@Param("allocateNo") String allocateNo, @Param("quantity") int quantity);

    @Select({
            "select",
            "id, allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOutId, warehouseOut, ",
            "warehouseEnterId, warehouseEnter, companyNameOut, companyNameEnter,deliverAddress, receiveAddress,",
            "alloteWay, batchNo, totalQuantity, realInQuantity, realOutQuantity, status, requireTime,deadline,syncEas, ",
            "easId,easOrderNumber,note,traceLog, dataVersion, createTime",
            "from warehouse_transfer_order",
            "where syncEas = #{syncEas}"
    })
    List<AllocationOrder> selectRecordByEasFlag(@Param("syncEas") int syncEas);

    @Update({
            "update warehouse_transfer_order",
            "set easId = #{easId}, ",
            "easOrderNumber= #{easOrderNumber}, ",
            "syncEas= #{syncEas} ",
            "where id = #{id}"
    })
    int syncAllocateEasSuccess(@Param("id")int id,@Param("easId")String easId, @Param("easOrderNumber")String easOrderNumber, @Param("syncEas")int syncEasStatus);

    @Update({
            "update warehouse_transfer_order",
            "set ",
            "syncEas= #{syncEas} ",
            "where id = #{id}"
    })
    int syncAllocateEasException(@Param("id")int id, @Param("syncEas")int syncEasStatus);

    @Update({
            "update warehouse_transfer_order",
            "set ",
            "syncEas= #{syncEas} ",
            "where id = #{id}"
    })
    int syncAllocateEasFail(@Param("id")int id, @Param("syncEas")int syncEasStatus);

    @Update({
            "update warehouse_transfer_order",
            "set ",
            "syncEas= #{syncEas}, ",
            "dataVersion= #{dataVersion}+1 ",
            "where allocateNo = #{allocateNo}"
    })
    int updateAllocateEasIng(@Param("allocateNo")String allocateNo,@Param("syncEas")int syncEasStatus, @Param("dataVersion")long dataVersion);

    @Select({
            "select",
            "id, allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOutId, warehouseOut, ",
            "warehouseEnterId, warehouseEnter, companyNameOut, companyNameEnter,deliverAddress, receiveAddress,",
            "alloteWay, batchNo, totalQuantity, realInQuantity, realOutQuantity, status, requireTime,deadline,syncEas, ",
            "easId,easOrderNumber,note,traceLog, dataVersion, createTime",
            "from warehouse_transfer_order",
            "where allocateNo = #{allocateNo}"
    })
    AllocationOrder selectRecordByAllocateNo(@Param("allocateNo")String allocateNo);
}
