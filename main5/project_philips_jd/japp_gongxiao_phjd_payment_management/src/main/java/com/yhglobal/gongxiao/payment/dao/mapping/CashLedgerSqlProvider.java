package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author: 葛灿
 */
public class CashLedgerSqlProvider {

    public String selectCashLedgerList(@Param("projectId") String projectId,
                                       @Param("bankName") String bankName,
                                       @Param("flowNo") String flowNo,
                                       @Param("confirmBegin") String confirmBegin,
                                       @Param("confirmEnd") String confirmEnd,
                                       @Param("receiveBegin") String receiveBegin,
                                       @Param("receiveEnd") String receiveEnd,
                                       @Param("approveStatus") String approveStatus) {
        return new SQL() {{
            SELECT(
                    "ledgerId, flowNo, approvalStatus, distributorId, distributorName, projectId, " +
                            "projectName, confirmCurrency, confirmAmount, recipientCurrency, recipientAmount, " +
                            "confirmTime, approvalUserId, approvalUserName, recipient, receiveTime, createTime, " +
                            "dataVersion, lastUpdateTime, bankName, approveTime,tracelog");
            FROM("payment_cash_ledger");
            WHERE("canceled = 0");
            if (!StringUtils.isEmpty(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (!StringUtils.isEmpty(bankName)) {
                WHERE("bankName = #{bankName}");
            }
            if (!StringUtils.isEmpty(flowNo)) {
                WHERE("flowNo = #{flowNo}");
            }
            if (!StringUtils.isEmpty(confirmBegin)) {
                WHERE("DATE(confirmTime) >= DATE(#{confirmBegin})");
            }
            if (!StringUtils.isEmpty(confirmEnd)) {
                WHERE("DATE(confirmTime) <= DATE(#{confirmEnd})");
            }
            if (!StringUtils.isEmpty(receiveBegin)) {
                WHERE("DATE(receiveTime) >= DATE(#{receiveBegin})");
            }
            if (!StringUtils.isEmpty(receiveEnd)) {
                WHERE("DATE(receiveTime) <= DATE(#{receiveEnd})");
            }
            if (!StringUtils.isEmpty(approveStatus)) {
                WHERE("approvalStatus = #{approveStatus}");
            }
            ORDER_BY("ledgerId DESC");
        }}.toString();
    }

}
