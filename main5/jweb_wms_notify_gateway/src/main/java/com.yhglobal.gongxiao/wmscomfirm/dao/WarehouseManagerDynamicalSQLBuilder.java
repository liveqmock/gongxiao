package com.yhglobal.gongxiao.wmscomfirm.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


public class WarehouseManagerDynamicalSQLBuilder {

    public String selectWmsInStorageInfo(@Param("projectId") String projectId,@Param("gonxiaoInboundNo")String gonxiaoInboundNo, @Param("purchaseNo")String purchaseNo, @Param("productCode")String productCode, @Param("createTimeBegin")String createTimeBegin, @Param("createTimeTo")String createTimeTo, @Param("warehouseId")String warehouseId, @Param("supplierName")String supplierName){
        return new SQL() {{
            SELECT( "projectId, supplier, inboundType, gongxiaoInboundOrderNo, wmsInboundOrderNo ",
                    "easInboundOrderNo, purchaseOrderNo, batchNo, warehouseId, warehouseName, productCode ",
                    "inStockQuantity, createTime");
            FROM("wms_inbound_record");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (gonxiaoInboundNo != null && !"".equals(gonxiaoInboundNo)) {
                WHERE("gongxiaoOutboundOrderNo like '%' #{gongxiaoOutboundOrderNo} '%'");
            }
            if (purchaseNo != null && !"".equals(purchaseNo)) {
                WHERE("purchaseOrderNo like '%' #{purchaseNo} '%'");
            }
            if (createTimeBegin != null && !"".equals(createTimeBegin)) {
                WHERE("createTime >= #{createTimeBegin}");
            }
            if (createTimeTo != null && !"".equals(createTimeTo)) {
                WHERE("createTime <=  #{createTimeTo}");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("warehouseId = #{warehouseId}");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("productCode like '%' #{productCode} '%'");
            }
            if (supplierName != null && !"".equals(supplierName)) {
                WHERE("supplier like '%' #{supplierName} '%'");
            }
        }}.toString();
    }
}
