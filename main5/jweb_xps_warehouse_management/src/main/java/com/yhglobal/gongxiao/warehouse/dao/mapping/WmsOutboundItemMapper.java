package com.yhglobal.gongxiao.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecordItem;
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
            "from wms_outbound_record_item where projectId = #{projectId} and gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and  wmsOutboundOrderNo = #{wmsOutboundOrderNo}"
    })
    List<WmsOutboundRecordItem> selectRecordByOrderNo(@Param("projectId") String projectId, @Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo, @Param("wmsOutboundOrderNo") String wmsOutboundOrderNo);

    @Select({
            "select ",
            "projectId, purchaseType, inventoryType, gongxiaoOutboundOrderNo, outboundOrderItemNo, ",
            "salesOrderNo, batchNo, warehouseId, warehouseName, productId, productCode, ",
            "productName, productUnit, outStockQuantity, createTime",
            "from wms_outbound_record_item where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and wmsOutboundOrderNo = #{wmsOutboundOrderNo}"
    })
    List<WmsOutboundRecordItem> selectOutboundOrderItemByNo(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo, @Param("wmsOutboundOrderNo") String wmsOutboundOrderNo);


    /**
     * 根据年月获取所有数据
     *
     * @param projectId 表前缀
     * @param date   日期yyyy-MM-dd 查询时会根据年月去查询所有数据
     * @return List<货品明细>
     */
    @Select({
            "select ",
            "projectId, purchaseType, inventoryType, gongxiaoOutboundOrderNo, wmsOutboundOrderNo, outboundOrderItemNo, ",
            "salesOrderNo, batchNo, warehouseId, warehouseName, productId, productCode, ",
            "productName, productUnit, outStockQuantity, createTime",
            "FROM wms_outbound_record_item",
            "WHERE YEAR(createTime) = YEAR(#{date})",
            "AND MONTH(createTime) = MONTH(#{date})",
            "AND projectId = #{projectId}"
    })
    List<WmsOutboundRecordItem> getListByYearAndMonth(@Param("projectId") long projectId,
                                                      @Param("date") String date);
}
