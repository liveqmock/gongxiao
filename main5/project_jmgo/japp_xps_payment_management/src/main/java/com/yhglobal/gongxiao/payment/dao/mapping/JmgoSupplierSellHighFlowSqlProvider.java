package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: 葛灿
 */
public class JmgoSupplierSellHighFlowSqlProvider {

    public String selectSupplierSellHighFlowList(@Param("prefix") String prefix,
                                                 @Param("currencyCode") String currencyCode,
                                                 @Param("projectId") long projectId,
                                                 @Param("moneyFlow") int moneyFlow,
                                                 @Param("beginDate") String beginDate,
                                                 @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT(
                    "recordId, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction,"+
                            "transactionTime, projectId, projectName, distributorId, distributorName, createdById,"+
                            "createdByName, createTime, flowType, flowNo, remark, salesOrderNo"
                    );
            FROM(prefix + "_supplier_sell_high_flow");
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
            ORDER_BY("recordId DESC");
        }}.toString();
    }

    public String selectIncomeAndExpenditure(@Param("prefix") String prefix,
                                             @Param("currencyCode") String currencyCode,
                                             @Param("projectId") long projectId,
                                             @Param("moneyFlow") Integer moneyFlow,
                                             @Param("beginDate") String beginDate,
                                             @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT(
                    "flowType AS recordType,count(1) as count,sum(transactionAmount) as amountCount");
            FROM(prefix + "_supplier_coupon_buffer_to_distributor_flow");
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
