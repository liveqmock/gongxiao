package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WmsOutboundMapper extends BaseMapper {

    @Insert({
            "insert into wms_outbound_record (",
            "projectId, outboundType, customer, gongxiaoOutboundOrderNo, outboundOrderItemNo, wmsOutboundOrderNo, ",
            "easOutboundOrderNo, tmsOutboundOrderNo, purchaseOrderNo, salesOrderNo, warehouseId, warehouseName, productCode, ",
            "outStockQuantity, easFlag, createTime",
            ")",
            "values (",
            "#{record.projectId}, #{record.outboundType}, #{record.customer}, #{record.gongxiaoOutboundOrderNo},",
            "#{record.outboundOrderItemNo}, #{record.wmsOutboundOrderNo}, #{record.easOutboundOrderNo}, ",
            "#{record.tmsOutboundOrderNo}, #{record.purchaseOrderNo}, #{record.salesOrderNo}, ",
            "#{record.warehouseId}, #{record.warehouseName}, ",
            "#{record.productCode},#{record.outStockQuantity}, ",
            "#{record.easFlag}, #{record.createTime}",
            ")"
    })
    int insertWmsOutboundInfo(@Param("record") WmsOutboundRecord record);

    @SelectProvider(type = WarehouseManagerDynamicalSQLBuilder.class, method = "selectWmsOutStorageInfo")
    List<WmsOutboundRecord> selectWmsOutStorageInfo(@Param("projectId") String projectId, @Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("salesOrderNo") String salesOrderNo, @Param("outboundType")String outboundType,@Param("customer") String customer, @Param("createTimeBegin")String createTimeBegin,@Param("createTimeTo") String createTimeTo);

    @Select({
            "select ",
            "projectId, outboundType, customer, gongxiaoOutboundOrderNo, outboundOrderItemNo, wmsOutboundOrderNo, ",
            "easOutboundOrderNo, tmsOutboundOrderNo, purchaseOrderNo, salesOrderNo, warehouseId, warehouseName, productCode, ",
            "outStockQuantity, createTime",
            "from wms_outbound_record where gongxiaoOutboundOrderNo = #{gongxiaoOutboundOrderNo} and wmsOutboundOrderNo = #{wmsOutboundOrderNo} "

    })
    WmsOutboundRecord selectRecordByOrderNo(@Param("gongxiaoOutboundOrderNo")String gongxiaoOutboundOrderNo,@Param("wmsOutboundOrderNo")String wmsOutboundOrderNo);

    @Select({
            "select ",
            "projectId, outboundType, customer, gongxiaoOutboundOrderNo, outboundOrderItemNo, wmsOutboundOrderNo, ",
            "easOutboundOrderNo, tmsOutboundOrderNo, purchaseOrderNo, salesOrderNo, warehouseId, warehouseName, productCode, ",
            "outStockQuantity, easFlag, dataVersion, createTime",
            "from wms_outbound_record where easFlag = #{easFlag}"
    })
    List<WmsOutboundRecord> selectRecordByEasFlag(@Param("easFlag") int easFlag);

    @Update({
            "update wms_outbound_record",
            "set easFlag = #{easFlag},",
            "easOutboundOrderNo = #{easOutboundOrderNo}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and wmsOutboundOrderNo =#{wmsOutboundOrderNo}"
    })
    int notifyEasSuccess(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("wmsOutboundOrderNo")String wmsOutboundOrderNo,@Param("easFlag") int easFlag,@Param("easOutboundOrderNo") String easOutboundOrderNo);

    @Update({
            "update wms_outbound_record",
            "set easFlag = #{easFlag},",
            "dataVersion = dataVersion+1",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and wmsOutboundOrderNo =#{wmsOutboundOrderNo} and dataVersion = #{dataVersion}"
    })
    int updateEasFlagToIng(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("wmsOutboundOrderNo")String wmsOutboundOrderNo,@Param("easFlag") int easFlag,@Param("dataVersion") int dataVersion);

    @Update({
            "update wms_outbound_record",
            "set easFlag = #{easFlag}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and wmsOutboundOrderNo =#{wmsOutboundOrderNo}"
    })
    int notifyEasFail(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("wmsOutboundOrderNo")String wmsOutboundOrderNo,@Param("easFlag") int easFlag);

    @Update({
            "update wms_outbound_record",
            "set easFlag = #{easFlag}",
            "where gongxiaoOutboundOrderNo= #{gongxiaoOutboundOrderNo} and wmsOutboundOrderNo =#{wmsOutboundOrderNo}"
    })
    int notifyEasNeedHandle(@Param("gongxiaoOutboundOrderNo") String gongxiaoOutboundOrderNo,@Param("wmsOutboundOrderNo")String wmsOutboundOrderNo,@Param("easFlag") int easFlag);
}

