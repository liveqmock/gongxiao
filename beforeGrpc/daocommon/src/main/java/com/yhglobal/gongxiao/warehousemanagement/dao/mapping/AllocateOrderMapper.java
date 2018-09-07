package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AllocateOrderMapper extends BaseMapper {

    @Insert({
            "insert into allocate_order (",
            "allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, companyNameOut, companyNameEnter,deliverAddress, receiveAddress," ,
            "alloteWay, status, requireTime,deadline,note,",
            "traceLog, createTime)",
            "values ",
            "( #{record.allocateNo}, ",
            "#{record.projectIdOut}, ",
            "#{record.projectIdEnter}, #{record.gongxiaoOutboundOrderNo},",
            "#{record.gongxiaoInboundOrderNo}, #{record.warehouseOut}, ",
            "#{record.warehouseEnter}, #{record.companyNameOut}, ",
            "#{record.companyNameEnter},#{record.deliverAddress}, ",
            "#{record.receiveAddress}, #{record.alloteWay}, ",
            "#{record.status}, #{record.requireTime}, ",
            "#{record.deadline}, #{record.note},",
            " #{record.traceLog}, #{record.createTime}",
            ")"
    })
    int insertAllocateOrder(@Param("record") AllocationOrder allocationOrder);

    @SelectProvider(type=AllocationDynamicalSQLBuilder.class, method="selectrRecordByCondition")
    List<AllocationOrder> selectrRecordByCondition(@Param("projectId")String projectId, @Param("allocateNo")String allocateNo, @Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo, @Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,
                                                   @Param("warehouseOut")String warehouseOut, @Param("warehouseEnter")String warehouseEnter, @Param("status")String status, @Param("createBeginTime")String createBeginTime, @Param("createEndTime")String createEndTime);

    @Select({
            "select",
            "allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, companyNameOut, companyNameEnter,deliverAddress, receiveAddress," ,
            "alloteWay, status, requireTime,deadline,note,",
            "traceLog, createTime",
            "from allocate_order",
            "where projectIdOut = #{projectId} and allocateNo = #{allocateNo}"
    })
    AllocationOrder selectInfoByAllocateNo(@Param("projectId")String projectId, @Param("allocateNo")String allocateNo);

    @Update({
            "update allocate_order",
            "set status = 1 ,",
            "where projectId = #{projectId} and allocateNo = #{allocateNo}"
    })
    int updateByAllocateNo(@Param("projectId")String projectId,@Param("allocateNo")String allocateNo);

}
