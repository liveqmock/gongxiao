package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WmsInboundMapper extends BaseMapper {

    @Insert({
            "insert into wms_inbound_record (",
            "projectId, supplier, purchaseType, inboundType, gongxiaoInboundOrderNo, wmsInboundOrderNo, ",
            "easInboundOrderNo, purchaseOrderNo, batchNo, warehouseId, warehouseName, productCode, ",
            "inStockQuantity, easFlag, createTime",
            ")",
            "values (",
            "#{record.projectId}, #{record.supplier}, #{record.purchaseType}, #{record.inboundType}, #{record.gongxiaoInboundOrderNo},",
            "#{record.wmsInboundOrderNo}, #{record.easInboundOrderNo}, ",
            "#{record.purchaseOrderNo}, #{record.batchNo}, ",
            "#{record.warehouseId}, #{record.warehouseName}, ",
            "#{record.productCode}, #{record.inStockQuantity}, ",
            "#{record.easFlag}, #{record.createTime}",
            ")"
    })
    int insertWmsInboundInfo(@Param("record") WmsIntboundRecord wmsIntboundRecord);

    @SelectProvider(type=WarehouseManagerDynamicalSQLBuilder.class, method="selectWmsInStorageInfo")
    List<WmsIntboundRecord> selectWmsInStorageInfo(@Param("projectId") String projectId,@Param("gonxiaoInboundNo")String gonxiaoInboundNo, @Param("batchNo")String batchNo, @Param("inboundType")String inboundType, @Param("warehouseId")String warehouseId, @Param("supplierName")String supplierName, @Param("wmsInboundOrderNo")String wmsInboundOrderNo, @Param("createTimeBegin")String createTimeBegin, @Param("createTimeTo")String createTimeTo);

    @Select({
            "select ",
            "projectId, supplier, purchaseType, inboundType, gongxiaoInboundOrderNo, wmsInboundOrderNo, ",
            "easInboundOrderNo, purchaseOrderNo, batchNo, warehouseId, warehouseName, productCode, ",
            "inStockQuantity, createTime",
            "from wms_inbound_record where projectId = #{projectId} and gongxiaoInboundOrderNo = #{gongxiaoInboundOrderNo} and wmsInboundOrderNo = #{wmsInboundOrderNo}"
    })
    WmsIntboundRecord selectRecordByOrderNo(@Param("projectId") String projectId, @Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("wmsInboundOrderNo") String wmsInboundOrderNo);

    @Select({
            "select ",
            "projectId, supplier, purchaseType, inboundType, gongxiaoInboundOrderNo, wmsInboundOrderNo, ",
            "easInboundOrderNo, purchaseOrderNo, batchNo, warehouseId, warehouseName, productCode, ",
            "inStockQuantity, easFlag, dataVersion, createTime",
            "from wms_inbound_record where easFlag = #{easFlag}"
    })
    List<WmsIntboundRecord> selectInboundRecordByEasFlag(@Param("easFlag") int easFlag);

    @Update({
            "update wms_inbound_record",
            "set easFlag = #{easFlag},",
            "easInboundOrderNo = #{easInboundOrderNo}",
            "where gongxiaoInboundOrderNo= #{gongxiaoInboundOrderNo} and wmsInboundOrderNo =#{wmsInboundOrderNo}"
    })
    int notifyEasSuccess(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("wmsInboundOrderNo") String wmsInboundOrderNo,@Param("easFlag") int easFlag,@Param("easInboundOrderNo") String easInboundOrderNo);

    @Update({
            "update wms_inbound_record",
            "set easFlag = #{easFlag},",
            "dataVersion = dataVersion+1",
            "where gongxiaoInboundOrderNo= #{gongxiaoInboundOrderNo} and wmsInboundOrderNo =#{wmsInboundOrderNo} and dataVersion = #{dataVersion}"
    })
    int updateEasFlagToIng(@Param("gongxiaoInboundOrderNo") String gongxiaoInboundOrderNo,@Param("wmsInboundOrderNo") String wmsInboundOrderNo,@Param("easFlag") int easFlag,@Param("dataVersion") int dataVersion);

    @Update({
            "update wms_inbound_record",
            "set easFlag = #{easFlag}",
            "where gongxiaoInboundOrderNo= #{gongxiaoInboundOrderNo} and wmsInboundOrderNo =#{wmsInboundOrderNo}"
    })
    int notifyEasFail(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("wmsInboundOrderNo") String wmsInboundOrderNo,@Param("easFlag") int easFlag);

    @Update({
            "update wms_inbound_record",
            "set easFlag = #{easFlag}",
            "where gongxiaoInboundOrderNo= #{gongxiaoInboundOrderNo} and wmsInboundOrderNo =#{wmsInboundOrderNo}"
    })
    int notifyEasNeedHandle(@Param("gongxiaoInboundOrderNo")String gongxiaoInboundOrderNo,@Param("wmsInboundOrderNo") String wmsInboundOrderNo,@Param("easFlag") int easFlag);
}
