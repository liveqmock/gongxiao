package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AllocateOrderItemMapper extends BaseMapper {
    @Insert({
            "<script>",
            "insert into allocate_order_item (",
            "allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, productCode, productName," ,
            "inventoryQuantity, alloteQuantity, status,",
            "createTime)",
            "values ",
            "<foreach collection='recordList'  item='record' separator=',' >",
            "( #{record.allocateNo}, ",
            "#{record.projectIdOut}, ",
            "#{record.projectIdEnter}, #{record.gongxiaoOutboundOrderNo},",
            "#{record.gongxiaoInboundOrderNo}, #{record.warehouseOut}, ",
            "#{record.warehouseEnter}, #{record.productCode}, ",
            "#{record.productName}, ",
            "#{record.inventoryQuantity}, #{record.alloteQuantity}, ",
            "#{record.status}, ",
            "#{record.createTime}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertAllocateOrderItems(@Param("recordList") List<AllocationOrderItem> allocationOrderItemList);

    @Select({
            "select",
            "allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, productCode, productName," ,
            "inventoryQuantity,",
            "alloteQuantity, status, createTime ",
            "from allocate_order_item",
            "where projectIdOut = #{projectId} and allocateNo = #{allocateNo}"
    })
    List<AllocationOrderItem> getAllocationOrderItemInfos(@Param("projectId") String projectId,@Param("allocateNo") String allocateNo);

    @Update({
            "update allocate_order_item",
            "set status = 1 ,",
            "where projectId = #{projectId} and allocateNo = #{allocateNo}"
    })
    int updateByAllocateNo(@Param("projectId") String projectId,@Param("allocateNo") String allocateNo);

}
