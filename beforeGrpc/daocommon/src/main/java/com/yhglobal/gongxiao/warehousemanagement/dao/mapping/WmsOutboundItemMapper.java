package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecordItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WmsOutboundItemMapper extends BaseMapper {

    @Insert({
            "<script>",
            "insert into wms_outbound_record_item (",
            "projectId, purchaseType, inventoryType, gongxiaoOutboundOrderNo, wmsOutboundOrderNo, outboundOrderItemNo, ",
            "salesOrderNo, batchNo, warehouseId, warehouseName, productId, productCode, ",
            "productName, productUnit, outStockQuantity,",
            "createTime)",
            "values ",
            "<foreach collection='recordList' item='record' separator=',' >",
            "(#{record.projectId}, #{record.purchaseType}, #{record.inventoryType}, #{record.gongxiaoOutboundOrderNo},",
            "#{record.wmsOutboundOrderNo},#{record.outboundOrderItemNo}, #{record.salesOrderNo}, ",
            "#{record.batchNo}, #{record.warehouseId}, ",
            "#{record.warehouseName}, #{record.productId}, ",
            "#{record.productCode}, #{record.productName},",
            "#{record.productUnit}, #{record.outStockQuantity}, ",
            "#{record.createTime}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertWmsOutboundInfo(@Param("recordList") List<WmsOutboundRecordItem> wmsOutboundRecordItemList);

    @Select({
            "select ",
            "projectId, purchaseType, inventoryType, gongxiaoOutboundOrderNo, wmsOutboundOrderNo, outboundOrderItemNo, ",
            "salesOrderNo, batchNo, warehouseId, warehouseName, productId, productCode, ",
            "productName, productUnit, outStockQuantity, createTime",
            "from wms_outbound_record_item where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and wmsOutboundOrderNo= #{wmsOutboundOrderNo}"
    })
    List<WmsOutboundRecordItem> selectRecordByOrderNo(@Param("projectId") String projectId, @Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("wmsOutboundOrderNo")String wmsOutboundOrderNo);

    @Select({
            "select ",
            "projectId, purchaseType, inventoryType, gongxiaoOutboundOrderNo, outboundOrderItemNo, ",
            "salesOrderNo, batchNo, warehouseId, warehouseName, productId, productCode, ",
            "productName, productUnit, outStockQuantity, createTime",
            "from wms_outbound_record_item where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and wmsOutboundOrderNo = #{wmsOutboundOrderNo}"
    })
    List<WmsOutboundRecordItem> selectOutboundOrderItemByNo(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("wmsOutboundOrderNo") String wmsOutboundOrderNo);
}
