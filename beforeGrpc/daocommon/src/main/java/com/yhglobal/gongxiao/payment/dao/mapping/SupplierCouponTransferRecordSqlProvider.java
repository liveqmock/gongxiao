package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class SupplierCouponTransferRecordSqlProvider {

    public String selectAllBySupplierId(@Param("currencyCode") String currencyCode,
                                        @Param("supplierId") long supplierId,
                                        @Param("projectId") long projectId,
                                        @Param("moneyFlow") Integer moneyFlow,
                                        @Param("beginDate") Date beginDate,
                                        @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "recordId, transferAccountType, currencyCode, amountBeforeTransaction, transactionAmount, " +
                            "amountAfterTransaction, transferTime, supplierId, supplierName, projectId, projectName, " +
                            "createdById, createdByName, purchaseOrderNo, flowNo, recordType, createTime");
            FROM("supplier_coupon_transfer_record");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != null && !"".equals(moneyFlow)) {
                WHERE("recordType = #{moneyFlow}");
            }
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
            ORDER_BY("recordId DESC");
        }}.toString();
    }

    public String selectIncomeCount(@Param("currencyCode") String currencyCode,
                                    @Param("supplierId") long supplierId,
                                    @Param("projectId") long projectId,
                                    @Param("moneyFlow") Integer moneyFlow,
                                    @Param("beginDate") Date beginDate,
                                    @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "count(1)");
            FROM("supplier_coupon_transfer_record");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            WHERE("recordType = 306");
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }

    public String selectIncomeAmount(@Param("currencyCode") String currencyCode,
                                     @Param("supplierId") long supplierId,
                                     @Param("projectId") long projectId,
                                     @Param("moneyFlow") Integer moneyFlow,
                                     @Param("beginDate") Date beginDate,
                                     @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "SUM(transactionAmount)");
            FROM("supplier_coupon_transfer_record");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            WHERE("recordType = 306");
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }

    public String selectExpenditureCount(@Param("currencyCode") String currencyCode,
                                         @Param("supplierId") long supplierId,
                                         @Param("projectId") long projectId,
                                         @Param("moneyFlow") Integer moneyFlow,
                                         @Param("beginDate") Date beginDate,
                                         @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "count(1)");
            FROM("supplier_coupon_transfer_record");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            WHERE("recordType = 305");
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }

    public String selectExpenditureAmount(@Param("currencyCode") String currencyCode,
                                          @Param("supplierId") long supplierId,
                                          @Param("projectId") long projectId,
                                          @Param("moneyFlow") Integer moneyFlow,
                                          @Param("beginDate") Date beginDate,
                                          @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "SUM(transactionAmount)");
            FROM("supplier_coupon_transfer_record");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            WHERE("recordType = 305");
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }

}
