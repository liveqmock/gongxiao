package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author 葛灿
 */
public class DistributorCashFlowSqlProvider {

    public String selectAllBydistributorId(@Param("currencyCode") String currencyCode,
                                           @Param("distributorId") long distributorId,
                                           @Param("projectId") long projectId,
                                           @Param("moneyFlow") int moneyFlow,
                                           @Param("beginDate") String beginDate,
                                           @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT(
                    "flowId, flowNo, distributorId, distributorName, flowType, projectId, projectName, " +
                            "currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, " +
                            "transactionTime, orderNo, extraInfo,  " +
                            "createById, createByName, createTime");
            FROM("distributor_cash_flow");
            WHERE("projectId = #{projectId}");
            WHERE("distributorId = #{distributorId}");
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
                                             @Param("distributorId") long distributorId,
                                             @Param("projectId") long projectId,
                                             @Param("moneyFlow") Integer moneyFlow,
                                             @Param("beginDate") String beginDate,
                                             @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT(
                    "flowType as recordType,count(1) as count,sum(transactionAmount) as amountCount");
            FROM("distributor_cash_flow");
            WHERE("distributorId = #{distributorId}");
            WHERE("projectId = #{projectId}");
            WHERE("flowType = 306");
            if (moneyFlow != 0) {
                WHERE("flowType = #{moneyFlow}");
            }
            if (!StringUtils.isEmpty(beginDate)) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (!StringUtils.isEmpty(endDate)) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
        }}.toString();
    }
}
