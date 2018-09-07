package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author: 葛灿
 */
public class CashLedgerSqlProvider {

    public String selectCashLedgerList(@Param("prefix") String prefix,
                                       @Param("projectId") Long projectId,
                                       @Param("bankName") String bankName,
                                       @Param("flowNo") String flowNo,
                                       @Param("confirmBegin") String confirmBegin,
                                       @Param("confirmEnd") String confirmEnd,
                                       @Param("receiveBegin") String receiveBegin,
                                       @Param("receiveEnd") String receiveEnd,
                                       @Param("approveStatus") String approveStatus,
                                       @Param("clientName") String clientName) {
        return new SQL() {{
            SELECT(
                    "ledgerId, flowNo, approvalStatus, distributorId, distributorName, projectId, " +
                            "projectName, confirmCurrency, confirmAmount, recipientCurrency, recipientAmount, " +
                            "confirmTime, approvalUserId, approvalUserName, recipient, receiveTime, createTime, " +
                            "dataVersion, lastUpdateTime, bankName, approveTime, tracelog, bankFlowNo, clientName, remark, createdByName");
            FROM(prefix + "_payment_cash_ledger");
            WHERE("canceled = 0");
            if (projectId != 0) {
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
            if (!StringUtils.isEmpty(clientName)) {
                WHERE("clientName = #{clientName}");
            }
            ORDER_BY("ledgerId DESC");
        }}.toString();
    }

}
