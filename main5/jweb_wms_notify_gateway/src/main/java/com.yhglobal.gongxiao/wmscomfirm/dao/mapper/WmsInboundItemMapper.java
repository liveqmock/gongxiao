package com.yhglobal.gongxiao.wmscomfirm.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.wmscomfirm.model.wms.WmsIntboundRecordItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WmsInboundItemMapper extends BaseMapper {

    @Insert({
            "<script>",
            "insert into wms_inbound_record_item (",
            "projectId, purchaseType, inventoryType, gongxiaoInboundOrderNo, wmsInboundOrderNo, purchaseOrderNo,",
            "warehouseId, warehouseName, productId, productCode, ",
            "productName, productUnit, inStockQuantity,",
            "createTime)",
            "values ",
            "<foreach collection='recordList' item='record' separator=',' >",
            "(#{record.projectId}, #{record.purchaseType}, #{record.inventoryType}, #{record.gongxiaoInboundOrderNo},",
            "#{record.wmsInboundOrderNo}, #{record.purchaseOrderNo},#{record.warehouseId}, #{record.warehouseName},",
            "#{record.productId}, #{record.productCode}, #{record.productName},",
            "#{record.productUnit}, #{record.inStockQuantity},",
            "#{record.createTime}",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertWmsInboundInfo(@Param("recordList") List<WmsIntboundRecordItem> wmsIntboundRecordItemList);

    @Select({
            "select ",
            "projectId, purchaseType, inventoryType, gongxiaoInboundOrderNo, wmsInboundOrderNo, ",
            "purchaseOrderNo, warehouseId, warehouseName, productId, productCode, ",
            "productName, productUnit, inStockQuantity,createTime",
            "from wms_inbound_record_item where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} and wmsInboundOrderNo = #{wmsInboundOrderNo}"
    })
    List<WmsIntboundRecordItem> selectRecordByOrderNo(@Param("projectId") String projectId, @Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo, @Param("wmsInboundOrderNo") String wmsInboundOrderNo);

    @Select({
            "select ",
            "projectId, purchaseType, inventoryType, gongxiaoInboundOrderNo, wmsInboundOrderNo, ",
            "purchaseOrderNo, warehouseId, warehouseName, productId, productCode, ",
            "productName, productUnit, inStockQuantity,createTime",
            "from wms_inbound_record_item  where gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} and wmsInboundOrderNo = #{wmsInboundOrderNo}"
    })
    List<WmsIntboundRecordItem> selectInboundOrderItemByNo(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo, @Param("wmsInboundOrderNo") String wmsInboundOrderNo);
}
