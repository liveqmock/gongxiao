package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 罗祎
 */
public class SupplierPrepaidTransferRecordSqlProvider {

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
                            "createdById, createdByName, businessOrderNo,  flowType, createTime,remark");
            FROM(prefix + "_supplier_prepaid_flow");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != 0) {
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

    public String selectIncomeAndExpenditure(@Param("prefix") String prefix,
                                             @Param("currencyCode") String currencyCode,
                                             @Param("supplierId") long supplierId,
                                             @Param("projectId") long projectId,
                                             @Param("moneyFlow") Integer moneyFlow,
                                             @Param("beginDate") Date beginDate,
                                             @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "flowType as flowType ,count(1) as count,sum(transactionAmount) as amountCount");
            FROM(prefix + "_supplier_prepaid_flow");
            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != 0) {
                WHERE("flowType = #{moneyFlow}");
            }
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
            GROUP_BY("flowType");
        }}.toString();
    }

}
