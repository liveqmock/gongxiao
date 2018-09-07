package com.yhglobal.gongxiao.payment.project.dao.supplier.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class SupplierCouponTransferRecordSqlProvider {

    public String selectAllBySupplierId(@Param("prefix") String prefix,
                                        @Param("currencyCode") String currencyCode,
                                        @Param("supplierId") long supplierId,
                                        @Param("projectId") long projectId,
                                        @Param("moneyFlow") Integer moneyFlow,
                                        @Param("beginDate") Date beginDate,
                                        @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "recordId, transferAccountType, currencyCode, amountBeforeTransaction, transactionAmount, " +
                            "amountAfterTransaction, transactionTime, supplierId, supplierName, projectId, projectName, " +
                            "createdById, createdByName, businessOrderNo, flowNo, flowType, createTime, remark");
            FROM(prefix+"_supplier_coupon_flow");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != null && !"".equals(moneyFlow)&& moneyFlow != -1) {
                WHERE("flowType = #{moneyFlow}");
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

    public String selectIncomeCount(@Param("prefix") String prefix,
                                    @Param("currencyCode") String currencyCode,
                                    @Param("supplierId") long supplierId,
                                    @Param("projectId") long projectId,
                                    @Param("moneyFlow") Integer moneyFlow,
                                    @Param("beginDate") Date beginDate,
                                    @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "count(1)");
            FROM(prefix+"_supplier_coupon_flow");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            WHERE("flowType = 306");
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }

    public String selectIncomeAmount(@Param("prefix") String prefix,
                                     @Param("currencyCode") String currencyCode,
                                     @Param("supplierId") long supplierId,
                                     @Param("projectId") long projectId,
                                     @Param("moneyFlow") Integer moneyFlow,
                                     @Param("beginDate") Date beginDate,
                                     @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "SUM(transactionAmount)");
            FROM(prefix+"_supplier_coupon_flow");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            WHERE("flowType = 306");
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }

    public String selectExpenditureCount(@Param("prefix") String prefix,
                                         @Param("currencyCode") String currencyCode,
                                         @Param("supplierId") long supplierId,
                                         @Param("projectId") long projectId,
                                         @Param("moneyFlow") Integer moneyFlow,
                                         @Param("beginDate") Date beginDate,
                                         @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "count(1)");
            FROM(prefix+"_supplier_coupon_flow");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            WHERE("flowType = 305");
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }

    public String selectExpenditureAmount(@Param("prefix") String prefix,
                                          @Param("currencyCode") String currencyCode,
                                          @Param("supplierId") long supplierId,
                                          @Param("projectId") long projectId,
                                          @Param("moneyFlow") Integer moneyFlow,
                                          @Param("beginDate") Date beginDate,
                                          @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "SUM(transactionAmount)");
            FROM(prefix+"_supplier_coupon_flow");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            WHERE("flowType = 305");
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }

}
