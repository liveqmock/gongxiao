package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class DistributorCouponFlowSqlProvider {

    public String selectAllBydistributorId(@Param("prefix") String prefix,
                                           @Param("currencyCode") String currencyCode,
                                           @Param("distributorId") long distributorId,
                                           @Param("projectId") long projectId,
                                           @Param("moneyFlow") int moneyFlow,
                                           @Param("beginDate") Date beginDate,
                                           @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "flowId, flowNo, distributorId, distributorName, flowType, projectId, projectName, " +
                            "currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, " +
                            "transactionTime, orderNo, extraInfo, statementCheckingFlag, statementCheckingTime, " +
                            "createById, createByName, unfreezeFlowNo, bufferAccountFlowNo, createTime");
            FROM(prefix+"_distributor_coupon_flow");
            WHERE("distributorId = #{distributorId}");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != -1) {
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

    public String selectIncomeCount(@Param("prefix") String prefix,
                                    @Param("currencyCode") String currencyCode,
                                    @Param("distributorId") long distributorId,
                                    @Param("projectId") long projectId,
                                    @Param("moneyFlow") Integer moneyFlow,
                                    @Param("beginDate") Date beginDate,
                                    @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "count(1)");
            FROM(prefix + "_distributor_coupon_flow");
            WHERE("distributorId = #{distributorId}");
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
                                     @Param("distributorId") long distributorId,
                                     @Param("projectId") long projectId,
                                     @Param("moneyFlow") Integer moneyFlow,
                                     @Param("beginDate") Date beginDate,
                                     @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "SUM(transactionAmount)");
            FROM(prefix + "_distributor_coupon_flow");
            WHERE("distributorId = #{distributorId}");
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
                                         @Param("distributorId") long distributorId,
                                         @Param("projectId") long projectId,
                                         @Param("moneyFlow") Integer moneyFlow,
                                         @Param("beginDate") Date beginDate,
                                         @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "count(1)");
            FROM(prefix + "_distributor_coupon_flow");
            WHERE("distributorId = #{distributorId}");
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
                                          @Param("distributorId") long distributorId,
                                          @Param("projectId") long projectId,
                                          @Param("moneyFlow") Integer moneyFlow,
                                          @Param("beginDate") Date beginDate,
                                          @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT(
                    "SUM(transactionAmount)");
            FROM(prefix + "_distributor_coupon_flow");
            WHERE("distributorId = #{distributorId}");
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
