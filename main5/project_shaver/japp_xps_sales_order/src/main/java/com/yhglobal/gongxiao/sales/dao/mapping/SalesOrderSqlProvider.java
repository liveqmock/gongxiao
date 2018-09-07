package com.yhglobal.gongxiao.sales.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 销售单SqlBuilder
 *
 * @Author: 葛灿
 */
public class SalesOrderSqlProvider {

    public String selectListSelectivelyByDistributor(@Param("prefix") String prefix,
                                                     @Param("projectId") long projectId,
                                                     @Param("distributorId") long distributorId,
                                                     @Param("salesOrderNo") String salesOrderNo,
                                                     @Param("productCode") String productCode,
                                                     @Param("outBoundTime") String outBoundTime,
                                                     @Param("createTimeBegin") String createTimeBegin,
                                                     @Param("createTimeEnd") String createTimeEnd,
                                                     @Param("salesOrderStatus") Integer salesOrderStatus) {
        return new SQL() {{
            SELECT(
                    "salesOrderId,settlementMode, salesOrderStatus, salesOrderNo, salesContactNo, marketingChannel, " +
                            "paymentChannel, projectId, projectName, brandId, brandName, supplierId, supplierName, " +
                            "distributorId, distributorName, shippingMode,  provinceId, provinceName, " +
                            "cityId, cityName, districtId, districtName, shippingAddress, recipientName, " +
                            "recipientMobile, warehouseId, warehouse, recipientCompany, shippingExpense, " +
                            "shippingExpensePaidBy, currencyId, currencyCode, totalOrderAmount, discountAmount, " +
                            "couponAmount, prepaidAmount, prestoredAmount, cashAmount, totalQuantity, yhDiscountAmount, " +
                            "supplierDiscountAmount, deliveredQuantity, inTransitQuantity, canceledQuantity, " +
                            "returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, finishedOutboundOrderInfo, " +
                            "dataVersion, createdById, createdByName, createTime, outBoundTime, orderCheckTime, " +
                            "rejectTime, paidTime, informWarehouseTime, signTime, lastUpdateTime, tracelog, paymentDays, sellHighAmount");
            FROM(prefix + "_sales_order");
            WHERE("projectId = #{projectId}");
            WHERE("distributorId = #{distributorId}");
            if (!StringUtils.isEmpty(salesOrderNo)) {
                WHERE("salesOrderNo like '%' #{salesOrderNo} '%'");
            }
            if (!StringUtils.isEmpty(productCode)) {
                WHERE("itemsCode like '%' #{productCode} '%' ");
            }
            if (!StringUtils.isEmpty(outBoundTime)) {
                WHERE("Date(outBoundTime) = Date(#{outBoundTime})");
            }
            if (!StringUtils.isEmpty(createTimeBegin)) {
                WHERE("Date(createTime) >= #{createTimeBegin}");
            }
            if (!StringUtils.isEmpty(createTimeEnd)) {
                WHERE("Date(createTime) <= #{createTimeEnd}");
            }
            if (salesOrderStatus != 0) {
                WHERE("salesOrderStatus = #{salesOrderStatus}");
            }
            ORDER_BY("salesOrderId DESC");
        }}.toString();
    }


    public String getListSelectively(@Param("prefix") String prefix,
                                     @Param("projectId") long projectId,
                                     @Param("salesOrderNo") String salesOrderNo,
                                     @Param("outBoundTime") String outBoundTime,
                                     @Param("createTime") String createTime,
                                     @Param("salesOrderStatus") Integer salesOrderStatus) {
        return new SQL() {{
            SELECT(
                    "salesOrderId,settlementMode, salesOrderStatus, salesOrderNo, salesContactNo, marketingChannel, " +
                            "paymentChannel, projectId, projectName, brandId, brandName, supplierId, supplierName, " +
                            "distributorId, distributorName, shippingMode,  provinceId, provinceName, " +
                            "cityId, cityName, districtId, districtName, shippingAddress, recipientName, " +
                            "recipientMobile, warehouseId, warehouse, recipientCompany, shippingExpense, " +
                            "shippingExpensePaidBy, currencyId, currencyCode, totalOrderAmount, discountAmount, " +
                            "couponAmount, prepaidAmount, prestoredAmount, cashAmount, totalQuantity, yhDiscountAmount, " +
                            "supplierDiscountAmount, deliveredQuantity, inTransitQuantity, canceledQuantity, " +
                            "returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, finishedOutboundOrderInfo, " +
                            "dataVersion, createdById, createdByName, createTime, outBoundTime, orderCheckTime, " +
                            "rejectTime, paidTime, informWarehouseTime, signTime, lastUpdateTime, tracelog, paymentDays, sellHighAmount");
            FROM(prefix + "_sales_order");
            WHERE("projectId = #{projectId}");
            if (!StringUtils.isEmpty(salesOrderNo)) {
                WHERE("salesOrderNo like '%' #{salesOrderNo} '%'");
            }
            if (!StringUtils.isEmpty(outBoundTime)) {
                WHERE("Date(outBoundTime) = Date(#{outBoundTime})");
            }
            if (!StringUtils.isEmpty(createTime)) {
                WHERE("Date(createTime) = #{createTime}");
            }
            if (salesOrderStatus != 0) {
                WHERE("salesOrderStatus = #{salesOrderStatus}");
            }
            ORDER_BY("salesOrderId DESC");
        }}.toString();
    }

    public String getCountMap(@Param("prefix") String prefix,
                              @Param("projectId") long projectId,
                              @Param("salesOrderNo") String salesOrderNo,
                              @Param("outBoundTime") String outBoundTime,
                              @Param("createTime") String createTime) {
        return new SQL() {{
            SELECT("salesOrderStatus as status,count(1) as count");
            FROM(prefix + "_sales_order");
            WHERE("projectId = #{projectId}");
            if (!StringUtils.isEmpty(salesOrderNo)) {
                WHERE("salesOrderNo like '%' #{salesOrderNo} '%'");
            }
            if (!StringUtils.isEmpty(outBoundTime)) {
                WHERE("DATE(outBoundTime) = DATE(#{outBoundTime})");
            }
            if (!StringUtils.isEmpty(createTime)) {
                WHERE("Date(createTime) = DATE(#{createTime})");
            }
            GROUP_BY("salesOrderStatus");
        }}.toString();
    }


}
