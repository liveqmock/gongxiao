package com.yhglobal.gongxiao.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class PurchaseOrderItemSqlProvider {
    public String selectByNoAndProduct(@Param("tablePrefix") String tablePrefix,
                                       @Param("purchaseOrderNo") String purchaseOrderNo,
                                       @Param("productCode") String productCode){
        return new SQL(){{
            SELECT("purchaseItemId, orderStatus,factoryPrice, purchaseOrderNo, productId, productCode, productName ",
                    "productUnit, warehouseId, warehouseName, shippingMode,  couponAmount ",
                    "prepaidAmount, cashAmount,guidePrice, purchasePrice, costPrice, couponBasePrice, purchaseNumber ",
                    "inTransitQuantity, inStockQuantity, imperfectQuantity, canceledQuantity, returnedQuantity ",
                    "ongoingInboundOrderInfo, finishedInboundOrderInfo, dataVersion, createTime ",
                    "lastUpdateTime, tracelog,easEntryId,easMateriaCode");
            FROM(tablePrefix+"_purchase_order_item");
            if (purchaseOrderNo!=null && !"".equals(purchaseOrderNo)){
                WHERE("purchaseOrderNo = #{purchaseOrderNo}");
            }
            if (productCode!=null && !"".equals(productCode)){
                String str = String.format("%%%s%%", productCode); //%27%
                WHERE("productCode like '" + str + "'");
            }
        }}.toString();
    }
}
