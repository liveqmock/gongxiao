package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualInboundOrderIterm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ManualInboundItemMapper extends BaseMapper {
    @Insert({
            "<script>",
            "insert into manual_inbound_order_item (",
            "orderNo, gongxiaoInboundOrderNo, projectId, batchNo, productId,",
            "productCode, productName, productUnit, ",
            "warehouseId, warehouseName, ",
            "inboundType, quantity, ",
            "guidePrice, purchasePrice, costPrice, ",
            "note, status, createTime)",
            "values ",
            "<foreach collection='recordList'  item='record' separator=',' >",
            "( " ,
            "#{record.orderNo}, ",
            "#{record.gongxiaoInboundOrderNo}, ",
            "#{record.projectId}, ",
            "#{record.batchNo}, ",
            "#{record.productId}, ",
            "#{record.productCode},",
            "#{record.productName},",
            "#{record.productUnit},",
            "#{record.warehouseId}, ",
            "#{record.warehouseName}, ",
            "#{record.inboundType}, #{record.quantity}, ",
            "#{record.guidePrice}, ",
            "#{record.purchasePrice}, #{record.costPrice}, ",
            "#{record.note},#{record.status},",
            "#{record.createTime}",
            ")",
            "</foreach>",
            "</script>"

    })
    int insertOrderList(@Param("recordList") List<ManualInboundOrderIterm> manualInboundOrderItermList);

    @Update({
            "update manual_inbound_order_item",
            "set realQuantity = #{record.realQuantity}, ",
            "status = #{record.status}" ,
            "where orderNo= #{record.orderNo} and productCode = #{record.productCode}"
    })
    int updateManualItemInfo(@Param("record")ManualInboundOrderIterm manualInboundOrderIterm);

    @Select({
            "select ",
            "orderNo, gongxiaoInboundOrderNo, projectId, batchNo, productId, productCode, ",
            "productCode, productName, productUnit, ",
            "warehouseId, warehouseName, ",
            "inboundType, quantity, ",
            "guidePrice, purchasePrice, costPrice, ",
            "note, status, createTime",
            "from manual_inbound_order_item where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} and productCode = #{productCode}"
    })
    ManualInboundOrderIterm selectRecordByGonxiaoNumAndProductCode(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("productCode")String productCode);
}
