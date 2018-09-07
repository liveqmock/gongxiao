package com.yhglobal.gongxiao.sales.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * 销售单SqlBuilder
 *
 * @Author: 葛灿
 */
public class SalesOrderSqlProvider {

    public String getListSelectively(@Param("projectId") long projectId,
                                     @Param("distributorId") Long distributorId,
                                     @Param("salesOrderNo") String salesOrderNo,
                                     @Param("productCode") String productCode,
                                     @Param("outBoundTime") Date outBoundTime,
                                     @Param("createTimeBegin") Date createTimeBegin,
                                     @Param("createTimeEnd") Date createTimeEnd,
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
            FROM("t_sales_order");
            WHERE("projectId = #{projectId}");
            if (distributorId != null) {
                WHERE("distributorId = #{distributorId}");
            }
            if (salesOrderNo != null && !"".equals(salesOrderNo)) {
                WHERE("salesOrderNo like '%' #{salesOrderNo} '%'");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("itemsCode like '%' #{productCode} '%' ");
            }
            if (outBoundTime != null) {
                WHERE("Date(outBoundTime) = Date(#{outBoundTime})");
            }
            if (createTimeBegin != null) {
                WHERE("Date(createTime) between #{createTimeBegin} and #{createTimeEnd} ");
            }
            if (salesOrderStatus != null) {
                WHERE("salesOrderStatus = #{salesOrderStatus}");
            }
            ORDER_BY("salesOrderId DESC");
        }}.toString();
    }

    public String getCountMap(@Param("projectId") long projectId,
                              @Param("distributorId") Long distributorId,
                              @Param("salesOrderNo") String salesOrderNo,
                              @Param("productCode") String productCode,
                              @Param("outBoundTime") Date outBoundTime,
                              @Param("createTimeBegin") Date createTimeBegin,
                              @Param("createTimeEnd") Date createTimeEnd) {
        return new SQL() {{
            SELECT("salesOrderStatus ,count(1) as num");
            FROM("t_sales_order");
            WHERE("projectId = #{projectId}");
            if (distributorId != null && !"".equals(distributorId)) {
                WHERE("distributorId = #{distributorId}");
            }
            if (salesOrderNo != null && !"".equals(salesOrderNo)) {
                WHERE("salesOrderNo like '%' #{salesOrderNo} '%'");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("itemsCode like '%' #{productCode} '%' ");
            }
            if (outBoundTime != null && !"".equals(outBoundTime)) {
                WHERE("outBoundTime = #{outBoundTime}");
            }
            if (createTimeBegin != null && createTimeEnd != null) {
                WHERE("Date(createTime) between #{createTimeBegin} and #{createTimeEnd} ");
            }
            GROUP_BY("salesOrderStatus");
        }}.toString();
    }


    public String getCountSelective(@Param("projectId") long projectId,
                                    @Param("distributorId") Long distributorId,
                                    @Param("salesOrderNo") String salesOrderNo,
                                    @Param("productCode") String productCode,
                                    @Param("outBoundTime") Date outBoundTime,
                                    @Param("createTimeBegin") Date createTimeBegin,
                                    @Param("createTimeEnd") Date createTimeEnd) {
        return new SQL() {{
            SELECT("count(*)");
            FROM("t_sales_order");
            WHERE("projectId = #{projectId}");
            if (distributorId != null && !"".equals(distributorId)) {
                WHERE("distributorId = #{distributorId}");
            }
            if (salesOrderNo != null && !"".equals(salesOrderNo)) {
                WHERE("salesOrderNo like '%' #{salesOrderNo} '%'");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("itemsCode like '%' #{productCode} '%' ");
            }
            if (outBoundTime != null && !"".equals(outBoundTime)) {
                WHERE("outBoundTime = #{outBoundTime}");
            }
            if (createTimeBegin != null && createTimeEnd != null) {
                WHERE("Date(createTime) between #{createTimeBegin} and #{createTimeEnd} ");
            }
        }}.toString();
    }
}
