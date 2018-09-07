package com.yhglobal.gongxiao.sales.dao.Provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * 销售计划
 *
 * @Description:
 * @author: LUOYI
 * @Date: Created in 9:45 2018/4/17
 */
public class SalesPlanItemProvider {
    public String searchProductByCustomer(@Param("tablePrefix") String tablePrefix,
                                          @Param("distributorId") String distributorId,
                                          @Param("queryStr") String queryStr,
                                          @Param("date") Date date) {
        return new SQL() {{
            SELECT("itemId, itemStatus, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName",
                    "supplierId, supplierName, productId, productCode, productName, distributorId ",
                    "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity",
                    "unallocatedQuantity, guidePrice, saleGuidePrice,currencyCode, currencyName, brandPrepaidUnit ",
                    "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount ",
                    "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById ",
                    "createdByName, createTime, lastUpdateTime, tracelog ");
            FROM(tablePrefix+"_sales_plan_item");
            WHERE("distributorId = #{distributorId}");
            WHERE("itemStatus != 91");
            if (queryStr != null && !"".equals(queryStr)) {
                WHERE("productName like '%' #{queryStr} '%' or productCode  like '%' #{queryStr} '%' ");
            }
            WHERE("#{date}>=startTime and #{date}<=endTime");
        }}.toString();
    }
    public String selectCustomerSalePlanList(@Param("tablePrefix") String tablePrefix,
                                             @Param("distributorId") String distributorId,
                                             @Param("salePlanNo") String salePlanNo,
                                             @Param("productCode") String productCode,
                                             @Param("productName") String productName,
                                             @Param("orderStatus") int orderStatus,
                                             @Param("startDate") Date startDate,
                                             @Param("endDate") Date endDate){
        return new SQL(){{
            SELECT("itemId, itemStatus, brandId,brandName,salesPlanNo, customerId, customerName, projectId, projectName ",
                    "supplierId, supplierName, productId, productCode, productName, distributorId ",
                    "distributorName, onSaleQuantity, saleOccupationQuantity, soldQuantity, freezedQuantity ",
                    "unallocatedQuantity, guidePrice, currencyCode, currencyName, brandPrepaidUnit ",
                    "brandPrepaidDiscount, brandPrepaidAmount, yhPrepaidUnit, yhPrepaidDiscount ",
                    "yhPrepaidAmount, wholesalePrice, startTime, endTime, dataVersion, createdById ",
                    "createdByName, createTime, lastUpdateTime, tracelog");
            FROM(tablePrefix+"_sales_plan_item");
            if (distributorId!=null && !"".equals(distributorId)){
                WHERE("distributorId =  #{distributorId} ");
            }
            if (salePlanNo!=null && !"".equals(salePlanNo)){
                WHERE("salePlanNo like '%' #{salePlanNo} '%'");
            }
            if (productCode!=null && !"".equals(productCode)){
                WHERE("productCode like '%' #{productCode} '%'");
            }
            if (productName!=null && !"".equals(productName)){
                WHERE("productName like '%' #{productName} '%'");
            }
            if (orderStatus==1){
                WHERE("NOW()>=startTime  && itemStatus != 91");
            }
            if (orderStatus==2){ //1 正常  2过期  3作废
                WHERE("itemStatus = 92");
            }
            if (orderStatus==3){
                WHERE("itemStatus = 91");
            }
            if (startDate!=null){
                WHERE("createTime > #{startDate} ");
            }
            if (endDate!=null){
                WHERE("createTime < #{endDate} ");
            }
        }}.toString();
    }
}
