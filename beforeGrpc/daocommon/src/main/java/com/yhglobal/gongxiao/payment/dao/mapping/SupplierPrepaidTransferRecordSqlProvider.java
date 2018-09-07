package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 罗祎
 */
public class SupplierPrepaidTransferRecordSqlProvider {

    public String selectAllBySupplierId(@Param("currencyCode") String currencyCode,
                                             @Param("supplierId") long supplierId,
                                             @Param("projectId") long projectId,
                                             @Param("moneyFlow") Integer moneyFlow,
                                             @Param("beginDate") Date beginDate,
                                             @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "recordId, transferAccountType, currencyCode, amountBeforeTransaction, transactionAmount, "+
                    "amountAfterTransaction, transferTime, supplierId, supplierName, projectId, projectName, "+
                    "createdById, createdByName, purchaseOrderNo,  recordType, createTime");
            FROM("supplier_prepaid_transfer_record");
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
    public String selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                        @Param("supplierId") long supplierId,
                                        @Param("projectId") long projectId,
                                        @Param("moneyFlow") Integer moneyFlow,
                                        @Param("beginDate") Date beginDate,
                                        @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "recordType,count(1) as count,sum(transactionAmount) as amountCount");
            FROM("supplier_prepaid_transfer_record");
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
            GROUP_BY("recordType");
        }}.toString();
    }

}
