package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 罗祎
 */
public class DistributorPrepaidFlowSqlProvider {

    public String selectAllBydistributorId(@Param("prefix") String prefix,
                                           @Param("currencyCode") String currencyCode,
                                           @Param("distributorId") long distributorId,
                                           @Param("projectId") long projectId,
                                           @Param("moneyFlow") Integer moneyFlow,
                                           @Param("beginDate") Date beginDate,
                                           @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "flowId, flowNo, distributorId, distributorName, flowType, projectId, projectName, " +
                            "currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, " +
                            "transactionTime, orderNo, extraInfo, statementCheckingFlag, statementCheckingTime, " +
                            "createById, createByName, unfreezeFlowNo, bufferAccountFlowNo, createTime");
            FROM(prefix + "_distributor_prepaid_flow");
            WHERE("distributorId = #{distributorId}");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != 0 && !"".equals(moneyFlow)) {
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

    public String selectIncomeAndExpenditure(@Param("prefix") String prefix,
                                             @Param("currencyCode") String currencyCode,
                                             @Param("distributorId") long distributorId,
                                             @Param("projectId") long projectId,
                                             @Param("moneyFlow") Integer moneyFlow,
                                             @Param("beginDate") Date beginDate,
                                             @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "flowType as recordType,count(1) as count,sum(transactionAmount) as amountCount");
            FROM(prefix + "_distributor_prepaid_flow");
            WHERE("distributorId = #{distributorId}");
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
            GROUP_BY("flowType");
        }}.toString();
    }
}
