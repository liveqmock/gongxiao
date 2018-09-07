package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author: 葛灿
 */
public class CashLedgerSqlProvider {

    public String selectCashLedgerList(@Param("projectId") Long projectId,
                                       @Param("bankName") String bankName,
                                       @Param("flowNo") String flowNo,
                                       @Param("confirmBegin") Date confirmBegin,
                                       @Param("confirmEnd") Date confirmEnd,
                                       @Param("receiveBegin") Date receiveBegin,
                                       @Param("receiveEnd") Date receiveEnd,
                                       @Param("approveStatus") Boolean approveStatus) {
        return new SQL() {{
            SELECT(
                    "ledgerId, flowNo, approvalStatus, distributorId, distributorName, projectId, " +
                            "projectName, confirmCurrency, confirmAmount, recipientCurrency, recipientAmount, " +
                            "confirmTime, approvalUserId, approvalUserName, recipient, receiveTime, createTime, " +
                            "dataVersion, lastUpdateTime, bankName, approveTime,tracelog, bankFlowNo");
            FROM("payment_cash_ledger");
            WHERE("canceled = 0");
            if (projectId != null) {
                WHERE("projectId = #{projectId}");
            }
            if (bankName != null && !"".equals(bankName)) {
                WHERE("bankName = #{bankName}");
            }
            if (flowNo != null && !"".equals(flowNo)) {
                WHERE("flowNo = #{flowNo}");
            }
            if (confirmBegin != null) {
                WHERE("DATE(confirmTime) >= DATE(#{confirmBegin})");
            }
            if (confirmEnd != null) {
                WHERE("DATE(confirmTime) <= DATE(#{confirmEnd})");
            }
            if (receiveBegin != null) {
                WHERE("DATE(receiveTime) >= DATE(#{receiveBegin})");
            }
            if (receiveEnd != null) {
                WHERE("DATE(receiveTime) <= DATE(#{receiveEnd})");
            }
            if (approveStatus != null) {
                WHERE("approvalStatus = #{approveStatus}");
            }
            ORDER_BY("ledgerId DESC");
        }}.toString();
    }

}
