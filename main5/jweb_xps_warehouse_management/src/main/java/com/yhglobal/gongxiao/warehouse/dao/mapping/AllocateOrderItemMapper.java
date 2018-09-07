package com.yhglobal.gongxiao.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface AllocateOrderItemMapper extends BaseMapper {
    @Insert({
            "<script>",
            "insert into warehouse_transfer_order_item (",
            "batchNo, allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, productId, productCode, productName," ,
            "inventoryQuantity, guidPrice, puchasePrice, costPrice, inventoryStatus, alloteQuantity, realInQuantity, realOutQuantity, status,",
            "purchaseType, entryId, materialCode, createTime)",
            "values ",
            "<foreach collection='recordList'  item='record' separator=',' >",
            "( #{record.batchNo}, #{record.allocateNo}, ",
            "#{record.projectIdOut}, ",
            "#{record.projectIdEnter}, #{record.gongxiaoOutboundOrderNo},",
            "#{record.gongxiaoInboundOrderNo}, #{record.warehouseOut}, ",
            "#{record.warehouseEnter}, #{record.productId}, #{record.productCode}, ",
            "#{record.productName}, ",
            "#{record.inventoryQuantity}, #{record.guidPrice}, ",
            "#{record.puchasePrice}, #{record.costPrice}, #{record.inventoryStatus}, ",
            " #{record.alloteQuantity}, #{record.realInQuantity}, #{record.realOutQuantity}, #{record.status}, #{record.purchaseType}, #{record.entryId}, #{record.materialCode}, ",
            "#{record.createTime}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertAllocateOrderItems(@Param("recordList") List<AllocationOrderItem> allocationOrderItemList);

    @Select({
            "select",
            "id, batchNo, allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, productId, productCode, productName, inventoryQuantity,",
            "alloteQuantity, realInQuantity, realOutQuantity, guidPrice, puchasePrice, costPrice, inventoryStatus, status, purchaseType, entryId, materialCode, createTime ",
            "from warehouse_transfer_order_item",
            "where projectIdOut = #{projectId} and allocateNo = #{allocateNo}"
    })
    List<AllocationOrderItem> getAllocationOrderItemInfos(@Param("projectId") String projectId,@Param("allocateNo") String allocateNo);

    @Update({
            "update warehouse_transfer_order_item",
            "set realInQuantity = realInQuantity+#{quantity} ",
            "where id = #{id}"
    })
    int updateAllocateEntryItem(@Param("id")long id, @Param("quantity")int quantity);

    @Update({
            "update warehouse_transfer_order_item",
            "set realOutQuantity = realOutQuantity+#{quantity} ",
            "where id = #{id}"
    })
    int updateAllocateOutItem(@Param("id")long id, @Param("quantity")int quantity);

    @Select({
            "select",
            "id, batchNo, allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, productId, productCode, productName, inventoryQuantity,",
            "alloteQuantity, realInQuantity, realOutQuantity, guidPrice, puchasePrice, costPrice, inventoryStatus, status, purchaseType, entryId, materialCode, createTime ",
            "from warehouse_transfer_order_item",
            "where allocateNo = #{allocateNo} and productCode = #{productCode}"
    })
    AllocationOrderItem getAllocationOrderItem(@Param("allocateNo")String allocateNo,@Param("productCode")String productCode);

    @Select({
            "select",
            "id, batchNo, allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, productId, productCode, productName, inventoryQuantity,",
            "alloteQuantity, realInQuantity, realOutQuantity, guidPrice, puchasePrice, costPrice, inventoryStatus, status, purchaseType, entryId, materialCode, createTime ",
            "from warehouse_transfer_order_item",
            "where allocateNo = #{allocateNo}"
    })
    List<AllocationOrderItem> selectOutboundOrderByNo(@Param("allocateNo") String allocateNo);

    @Update({
            "update warehouse_transfer_order_item",
            "set entryId = #{entryId}, ",
            "materialCode = #{materialCode} ",
            "where id = #{id}"
    })
    int updateItemEasInfo(@Param("id")int id, @Param("entryId")String entryId, @Param("materialCode")String materialCode);

    @Select({
            "select",
            "id, batchNo, allocateNo, projectIdOut, ",
            "projectIdEnter, gongxiaoOutboundOrderNo, gongxiaoInboundOrderNo, warehouseOut, ",
            "warehouseEnter, productId, productCode, productName, inventoryQuantity,",
            "alloteQuantity, realInQuantity, realOutQuantity, guidPrice, puchasePrice, costPrice, inventoryStatus, status, purchaseType, entryId, materialCode, createTime ",
            "from warehouse_transfer_order_item",
            "where allocateNo = #{allocateNo}"
    })
    List<AllocationOrderItem> selectRecorByAllocateNo(@Param("allocateNo")String allocateNo);
}
