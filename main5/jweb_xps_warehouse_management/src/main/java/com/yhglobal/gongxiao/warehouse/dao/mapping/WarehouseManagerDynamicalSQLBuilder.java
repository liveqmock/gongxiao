package com.yhglobal.gongxiao.warehouse.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


public class WarehouseManagerDynamicalSQLBuilder {

    //注:查询仓储管理的入库单信息
    public String selectInStorageInfo(@Param("projectId") String projectId,@Param("gonxiaoInboundNo") String gonxiaoInboundNo,@Param("purchaseNo")String purchaseNo,@Param("productCode") String productCode,@Param("goodCode") String goodCode,
                                      @Param("createTime") String createTime,@Param("warehouseId") String warehouseId,@Param("supplierName") String supplierName,@Param("prefix") String prefix) {
        return new SQL() {{
            SELECT("projectId, batchNo, orderStatus ",
                    "inboundType, gongxiaoInboundOrderNo ",
                    "syncToWmsFlag ",
                    "purchaseOrderNo ",
                    "warehouseId ",
                    "warehouseName ",
                    "totalQuantity, inTransitQuantity, inStockQuantity ",
                    "imperfectQuantity, rejectedQuantity,realInStockQuantity ",
                    "expectedArrivalTime, createTime, lastUpdateTime ");
            FROM(prefix+"_warehouse_inbound_order");
            if (projectId != null && !"".equals(projectId))
            WHERE("projectId = #{projectId}");
            if (gonxiaoInboundNo != null && !"".equals(gonxiaoInboundNo)) {
                WHERE("gongxiaoInboundOrderNo like '%' #{gonxiaoInboundNo} '%'");
            }
            if (purchaseNo != null && !"".equals(purchaseNo)) {
                WHERE("purchaseOrderNo like '%' #{purchaseNo} '%'");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("connectedProduct like '%' #{productCode} '%'");
            }
            if (goodCode != null && !"".equals(goodCode)) {
                WHERE("connectedGood like '%' #{goodCode} '%'");
            }
            if (createTime != null && !"".equals(createTime)) {
                WHERE("DATE(createTime) = #{createTime}");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("warehouseId = #{warehouseId}");
            }
            if (supplierName != null && !"".equals(supplierName)) {
                WHERE("supplierName like '%' #{supplierName} '%'");
            }
            ORDER_BY("rowId desc");
        }}.toString();
    }

    //注:查询仓储管理的出库单信息
    public String selectOutStorageInfoByRequire(@Param("projectId") String projectId, @Param("inventoryNum") String inventoryNum, @Param("salseNum")String salseNum, @Param("createTimeBeging")String createTimeBeging,
                                                @Param("createTimeLast")String createTimeLast, @Param("warehouseId")String warehouseId, @Param("productCode")String productCode,
                                                @Param("finishTimeBegin")String finishTimeBegin,@Param("finishTimeLast")String finishTimeLast, @Param("supplier")String supplier,
                                                @Param("customer")String customer,@Param("prefix") String prefix) {
        return new SQL() {{
            SELECT("projectId, batchNo, orderStatus,outboundType,inventoryType ",
                    "gongxiaoOutboundOrderNo, outboundOrderItemNo, sourceChannel, syncToWmsFlag, wmsOutboundOrderNo ",
                    "purchaseOrderNo, returnedOrderNo, salesOrderNo" ,
                    "warehouseId, warehouseName, deliverAddress, shippingMode",
                    "supplierName, shippingAddress, customerId, customer",
                    "contactsPeople, phoneNum",
                    "shippingExpense, paidBy, note, connectedProduct ",
                    "totalQuantity, outStockQuantity,imperfectQuantity",
                    "transferQuantity, canceledQuantity, realOutStockQuantity, dataVersion,tracelog",
                    "createdById, createdByName,createTime");
            FROM(prefix+"_warehouse_outbound_order");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (inventoryNum != null && !"".equals(inventoryNum)) {
                WHERE("gongxiaoOutboundOrderNo like '%' #{inventoryNum} '%'");
            }
            if (salseNum != null && !"".equals(salseNum)) {
                WHERE("salesOrderNo like '%' #{salseNum} '%'");
            }
            if (createTimeBeging != null && !"".equals(createTimeBeging)) {
                WHERE("createTime >= #{createTimeBeging}");
            }
            if (createTimeLast != null && !"".equals(createTimeLast)) {
                WHERE("createTime <=  #{createTimeLast}");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("warehouseId = #{warehouseId}");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("connectedProduct like '%' #{productCode} '%'");
            }
            if (finishTimeBegin != null && !"".equals(finishTimeBegin)) {
                WHERE("lastUpdateTime >= #{finishTimeBegin}");
            }
            if (finishTimeLast != null && !"".equals(finishTimeLast)) {
                WHERE("lastUpdateTime <= #{finishTimeLast}");
            }
            if (supplier != null && !"".equals(supplier)) {
                WHERE("supplierName like '%' #{supplier} '%'");
            }
            if (customer != null && !"".equals(customer)) {
                WHERE("customer like '%' #{customer} '%'");
            }
            ORDER_BY("rowId desc");
        }}.toString();
    }

   public String selectRecordByPurchaseNo(@Param("projectId")String projectId,@Param("purchaseNo") String purchaseNo,@Param("inventoryNum") String inventoryNum,
                                          @Param("productCode") String productCode,@Param("createTime") String createTime,@Param("warehouseId") String warehouseId,
                                          @Param("supplierName") String supplierName,@Param("prefix") String prefix) {
       return new SQL() {{
           SELECT("projectId, orderStatus ",
                   "inboundType, gongxiaoInboundOrderNo ",
                   "purchaseOrderNo ",
                   "warehouseName , supplierName",
                   "totalQuantity, inStockQuantity ",
                   "createTime");
           FROM(prefix+"_warehouse_inbound_order");
           if (projectId != null && !"".equals(projectId))
               WHERE("projectId = #{projectId}");
           if (purchaseNo != null && !"".equals(purchaseNo))
               WHERE("purchaseOrderNo like '%' #{purchaseNo} '%'");
           if (inventoryNum != null && !"".equals(inventoryNum)) {
               WHERE("gongxiaoInboundOrderNo like '%' #{inventoryNum} '%'");
           }
           if (productCode != null && !"".equals(productCode)) {
               WHERE("connectedProduct like '%' #{productCode} '%'");
           }
           if (createTime != null && !"".equals(createTime)) {
               WHERE("createTime = #{createTime}");
           }
           if (warehouseId != null && !"".equals(warehouseId)) {
               WHERE("warehouseId = #{warehouseId}");
           }
           if (supplierName != null && !"".equals(supplierName)) {
               WHERE("supplierName like '%' #{supplierName} '%'");
           }
       }}.toString();
    }

    public String selectWmsInStorageInfo(@Param("projectId") String projectId,@Param("gonxiaoInboundNo")String gonxiaoInboundNo,
                                         @Param("purchaseNo")String purchaseNo, @Param("productCode")String productCode,
                                         @Param("createTimeBegin")String createTimeBegin, @Param("createTimeTo")String createTimeTo,
                                         @Param("warehouseId")String warehouseId, @Param("supplierName")String supplierName){
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
