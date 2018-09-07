package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author: 葛灿
 */
public class SupplierPrepaidTransferToDistributorFlowSqlProvider {

    public String selectBufferPrepaidFlowList(@Param("currencyCode") String currencyCode,
                                              @Param("supplierId") long supplierId,
                                              @Param("projectId") long projectId,
                                              @Param("moneyFlow") Integer moneyFlow,
                                              @Param("beginDate") Date beginDate,
                                              @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "flowId, flowNo, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, "+
                    "transferTime, supplierId, supplierName, projectId, projectName, createdById, "+
                    "createdByName, purchaseOrderNo, flowType, createTime");
            FROM("supplier_prepaid_transfer_to_distributor_flow");
//            WHERE("supplierId = #{supplierId}");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != null && !"".equals(moneyFlow)) {
                WHERE("flowType = #{moneyFlow}");
            }
            if (beginDate != null) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (endDate != null) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
            ORDER_BY("flowId DESC");
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
                    "flowType AS recordType,count(1) as count,sum(transactionAmount) as amountCount");
            FROM("supplier_prepaid_transfer_to_distributor_flow");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != null) {
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
