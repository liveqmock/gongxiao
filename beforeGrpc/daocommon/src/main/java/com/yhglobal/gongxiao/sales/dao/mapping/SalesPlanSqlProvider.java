package com.yhglobal.gongxiao.sales.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;


/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 * @create: 2018-03-14 10:41
 **/
public class SalesPlanSqlProvider {

    public String getList(@Param("projectId") String projectId,
                          @Param("productCode") String productCode,
                          @Param("productName") String productName,
                          @Param("createTime") String createTime) {
        return new SQL() {{
            SELECT("salesPlanId, salesPlanStatus, salesPlanNo, projectId, projectName, brandId ",
                    "brandName, supplierId, supplierName, productCategory, productId, productCode ",
                    "productName, guidePrice,saleGuidePrice, onSaleQuantity, allocatedQuantity, unallocatedQuantity ",
                    "inStockQuantity, startTime, endTime, dataVersion, createdById, createdByName ",
                    "createTime, lastUpdateTime, tracelog");
            FROM("sales_plan");
            WHERE("salesPlanStatus!=91");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("productCode like '%' #{productCode} '%'");
            }
            if (createTime != null && !"".equals(createTime)) {
                WHERE("DATE(createTime) = DATE(#{createTime}) ");
            }
            if (productName != null && !"".equals(productName)) {
                WHERE("productName like '%' #{productName} '%'");
            }
            ORDER_BY("createTime desc");
        }}.toString();
    }



    public String getItemListByCondition(@Param("projectId") String projectId,
                                         @Param("productCode") String productCode,
                                         @Param("distributorId") String distributorId,
                                         @Param("businessDate") Date businessDate) {
        return new SQL() {{
            SELECT("itemId, itemStatus, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName ",
                    "supplierId, supplierName, productId, productCode, productName, distributorId ",
                    "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity ",
                    "unallocatedQuantity, guidePrice, saleGuidePrice,currencyCode, currencyName, brandPrepaidUnit ",
                    "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount ",
                    "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById ",
                    "createdByName, createTime, lastUpdateTime, tracelog");
            FROM("sales_plan_item");
            WHERE("projectId = #{projectId}");
            WHERE("#{businessDate} > startTime && #{businessDate}<endTime");
            WHERE("distributorId = #{distributorId} ");
            WHERE("itemStatus!=91");
            if (productCode != null && !"".equals(productCode)) {
                WHERE("productCode = #{productCode} or productName = #{productCode}");
            }
        }}.toString();
    }

    public String getCustomerItemListByCondition(@Param("projectId") String projectId,
                                                 @Param("distributorId") String distributorId,
                                                 @Param("distributorName") String distributorName) {
        return new SQL() {{
            SELECT("distributorId");
            FROM("sales_plan_item");
            WHERE("projectId = #{projectId}");
            if (projectId!= null && !"".equals(projectId)){
                WHERE("projectId = #{projectId}");
            }
            if (distributorId!= null && !"".equals(distributorId)){
                WHERE("distributorId like '%' #{projectId} '%' ");
            }
            if (distributorName!= null && !"".equals(distributorName)){
                WHERE("distributorName like '%' #{distributorName} '%' ");
            }
            GROUP_BY("distributorId");
        }}.toString();
    }

}
