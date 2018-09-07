package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class AllocationDynamicalSQLBuilder {
    //注:查询调拨单信息
    public String selectrRecordByCondition(@Param("projectId")String projectId, @Param("allocateNo")String allocateNo, @Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo, @Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,
                                           @Param("warehouseOut")String warehouseOut, @Param("warehouseEnter")String warehouseEnter, @Param("status")String status, @Param("createBeginTime")String createBeginTime, @Param("createEndTime")String createEndTime) {
        return new SQL() {{
            SELECT("allocateNo, projectIdOut ",
                    "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut ",
                    "warehouseEnter, companyNameOut, companyNameEnter,deliverAddress, receiveAddress " ,
                    "alloteWay, status, requireTime,deadline,note ",
                    "traceLog, createTime");
            FROM("allocate_order");
            if (projectId != null && !"".equals(projectId))
                WHERE("projectIdOut = #{projectId}");
            if (allocateNo != null && !"".equals(allocateNo)) {
                WHERE("allocateNo = #{allocateNo} ");
            }
            if (gongxiaoOutboundOrderNo != null && !"".equals(gongxiaoOutboundOrderNo)) {
                WHERE("gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} ");
            }
            if (gongxiaoInboundOrderNo != null && !"".equals(gongxiaoInboundOrderNo)) {
                WHERE("gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} ");
            }
            if (warehouseOut != null && !"".equals(warehouseOut)) {
                WHERE("warehouseOut like '%' #{warehouseOut} '%'");
            }
            if (warehouseEnter != null && !"".equals(warehouseEnter)) {
                WHERE("warehouseEnter like '%' #{warehouseEnter} '%'");
            }
            if (status != null && !"".equals(status)) {
                WHERE("status = #{status} ");
            }
            if (createBeginTime != null && !"".equals(createBeginTime)) {
                WHERE("createBeginTime >= #{createBeginTime} ");
            }
            if (createEndTime != null && !"".equals(createEndTime)) {
                WHERE("createEndTime <= #{createEndTime} ");
            }
        }}.toString();
    }
}
