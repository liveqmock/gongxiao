package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: 葛灿
 */
public class SupplierCouponTransferToDistributorFlowSqlProvider {

    public String selectBufferCouponFlowList(@Param("currencyCode") String currencyCode,
                                             @Param("projectId") long projectId,
                                             @Param("moneyFlow") Integer moneyFlow,
                                             @Param("beginDate") String beginDate,
                                             @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT(
                    "flowId, flowNo, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, " +
                            "transferTime, supplierId, supplierName, projectId, projectName, createdById, " +
                            "createdByName, purchaseOrderNo, flowType, createTime");
            FROM("supplier_coupon_transfer_to_distributor_flow");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != 0) {
                WHERE("flowType = #{moneyFlow}");
            }
            if (!StringUtils.isEmpty(beginDate)) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (!StringUtils.isEmpty(endDate)) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
            ORDER_BY("flowId DESC");
        }}.toString();
    }

    public String selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                             @Param("projectId") long projectId,
                                             @Param("moneyFlow") Integer moneyFlow,
                                             @Param("beginDate") String beginDate,
                                             @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT(
                    "flowType AS recordType,count(1) as count,sum(transactionAmount) as amountCount");
            FROM("supplier_coupon_transfer_to_distributor_flow");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != 0) {
                WHERE("flowType = #{moneyFlow}");
            }
            if (!StringUtils.isEmpty(beginDate)) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (!StringUtils.isEmpty(endDate)) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
            GROUP_BY("flowType");
        }}.toString();
    }
}
