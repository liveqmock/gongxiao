package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

/**
 * 销售单详情SqlBuilder
 *
 * @Author: 葛灿
 */
public class SalesOrderItemSqlProvider {

    public String getListSelectively(@Param("salesOrderNo") String salesOrderNo,
                                      @Param("productCodes") String productCodes) {
        return new SQL() {{
            SELECT(
                    "salesOrderItemId, itemStatus, salesOrderNo, productId, productCode, productName, " +
                    "warehouseId, warehouseName, currencyId, currencyCode, currencyName, supplierDiscountPercent, " +
                    "yhDiscountPercent, retailPrice, sellHighAmount,saleGuidePrice, wholesalePrice, supplierDiscountAmount, " +
                    "yhDiscountAmount, totalOrderAmount, cashAmount, totalQuantity, deliveredQuantity, " +
                    "inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, " +
                    "finishedOutboundOrderInfo, dataVersion, createTime, lastUpdateTime, tracelog, purchaseGuidePrice, salesGuidePrice");
            FROM("t_sales_order_item");
            WHERE("salesOrderNo = #{salesOrderNo}");
            WHERE("unhandledQuantity <> 0");
            if (productCodes != null && !"".equals(productCodes)) {
                WHERE("productCode in (#{productCodes})");
            }

        }}.toString();
    }

    public String selectItemListByNo(@Param("salesPlanNo") String salesPlanNo,
                                     @Param("distributorId") String distributorId,
                                     @Param("distributorName") String distributorName) {
        String sqlString = new SQL() {{
            SELECT("itemId, itemStatus, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName ",
                    "supplierId, supplierName, productId, productCode, productName, distributorId ",
                    "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity ",
                    "unallocatedQuantity, guidePrice,saleGuidePrice, currencyCode, currencyName, brandPrepaidUnit ",
                    "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount ",
                    "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById",
                    "createdByName, createTime, lastUpdateTime, tracelog");
            FROM("sales_plan_item");
            WHERE("itemStatus!=91");
            if (salesPlanNo != null && !"".equals(salesPlanNo)) {
                WHERE("salesPlanNo = #{salesPlanNo}");
            }
            if (distributorId != null && !"".equals(distributorId)) {
                WHERE("distributorId like '%' #{distributorId}'%'");
            }
            if (distributorName != null && !"".equals(distributorName)) {
                WHERE("distributorName like '%' #{distributorName} '%'");
            }
        }}.toString();
        System.out.println("sqlStringj=" + sqlString);
        return sqlString;
    }

}
