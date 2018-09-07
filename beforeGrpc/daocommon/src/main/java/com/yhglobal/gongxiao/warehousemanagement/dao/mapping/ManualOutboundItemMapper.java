package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualOutboundOrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ManualOutboundItemMapper extends BaseMapper{

    @Insert({
            "<script>",
            "insert into manual_outbound_order_item (",
            "orderNo, gongxiaoOutboundOrderNo, projectId, batchNo, productId,",
            "productCode, productName, productUnit, ",
            "warehouseId, warehouseName, ",
            "outboundType, quantity, ",
            "guidePrice, wholesalePriceDouble, ",
            "note, status, createTime)",
            "values ",
            "<foreach collection='recordList'  item='record' separator=',' >",
            "( " ,
            "#{record.orderNo}, ",
            "#{record.gongxiaoOutboundOrderNo}, ",
            "#{record.projectId}, ",
            "#{record.batchNo}, ",
            "#{record.productId}, ",
            "#{record.productCode},",
            "#{record.productName},",
            "#{record.productUnit},",
            "#{record.warehouseId}, ",
            "#{record.warehouseName}, ",
            "#{record.outboundType}, #{record.quantity}, ",
            "#{record.guidePrice}, ",
            "#{record.wholesalePriceDouble}, ",
            "#{record.note},#{record.status},",
            "#{record.createTime}",
            ")",
            "</foreach>",
            "</script>"

    })
    int insertOrderList(@Param("recordList") List<ManualOutboundOrderItem> manualOutboundOrderItemList);

    @Update({
            "update manual_outbound_order_item",
            "set ",
            "realQuantity = #{record.realQuantity}, ",
            "status = #{record.status}",
            "where orderNo = #{record.orderNo}"
    })
    int updateManualOutboundInfo(@Param("record") ManualOutboundOrderItem manualOutboundOrderItem);

    @Select({
            "select",
            "orderNo, gongxiaoOutboundOrderNo, projectId, batchNo, productId,",
            "productCode, productName, productUnit, ",
            "warehouseId, warehouseName, ",
            "outboundType, quantity, ",
            "guidePrice, wholesalePriceDouble, ",
            "note, status, createTime",
            "from manual_outbound_order_item where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and productCode = #{productCode}"
    })
    ManualOutboundOrderItem selectRecordByGonxiaoNumAndProductCode(@Param("gonxiaoOutboundOrderNo") String gonxiaoOutboundOrderNo, @Param("productCode") String productCode);
}
