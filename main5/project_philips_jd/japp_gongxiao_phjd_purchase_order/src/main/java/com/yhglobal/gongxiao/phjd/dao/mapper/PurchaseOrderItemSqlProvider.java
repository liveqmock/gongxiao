package com.yhglobal.gongxiao.phjd.dao.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 采购订单详细辅助类
 *
 * @author weizecheng
 * @date 2018/9/5 11:20
 */
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
            if (StringUtils.isNotBlank(purchaseOrderNo)){
                WHERE("purchaseOrderNo = #{purchaseOrderNo}");
            }
            if (StringUtils.isNotBlank(productCode)){
                //%27%
                String str = String.format("%%%s%%", productCode);
                WHERE("productCode like '" + str + "'");
            }
        }}.toString();
    }
}
