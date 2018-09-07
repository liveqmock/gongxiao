package com.yhglobal.gongxiao.sales.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 销售单详情SqlBuilder
 *
 * @Author: 葛灿
 */
public class SalesOrderItemSqlProvider {

    public String selectItemListByNo(@Param("tablePrefix") String tablePrefix,
                                     @Param("salesPlanNo") String salesPlanNo,
                                     @Param("distributorId") String distributorId,
                                     @Param("distributorName") String distributorName) {
        String sqlString = new SQL() {{
            SELECT("itemId, itemStatus,grossProfitValue, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName ",
                    "supplierId, supplierName, productId, productCode, productName, distributorId ",
                    "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity ",
                    "unallocatedQuantity, guidePrice,saleGuidePrice, currencyCode, currencyName, brandPrepaidUnit ",
                    "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount ",
                    "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById",
                    "createdByName, createTime, lastUpdateTime, tracelog");
            FROM(tablePrefix+"_sales_plan_item");
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
