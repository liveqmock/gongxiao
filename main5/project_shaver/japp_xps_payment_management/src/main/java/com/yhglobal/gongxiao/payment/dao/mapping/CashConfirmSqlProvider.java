package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 收款确认Provider
 *
 * @author: 葛灿
 */
public class CashConfirmSqlProvider {


    public String selectListSelectively(@Param("prefix") String prefix,
                                        @Param("salesOrderNo") String salesOrderNo,
                                        @Param("projectId") Long projectId,
                                        @Param("distributorName") String distributorName,
                                        @Param("bankName") String bankName,
                                        @Param("orderBegin") String orderBegin,
                                        @Param("orderEnd") String orderEnd,
                                        @Param("confirmBegin") String confirmBegin,
                                        @Param("confirmEnd") String confirmEnd,
                                        @Param("orderStatus") String orderStatus) {
        return new SQL() {{
            SELECT(
                    "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, " +
                            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, " +
                            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, " +
                            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime, " +
                            "createTime, dataVersion, lastUpdateTime,  tracelog,bankName, bankFlowNo, clientName, remark");
            FROM(prefix + "_payment_cash_confirm");
            WHERE("canceled = 0");
            WHERE("(confirmStatus!=2 or (confirmStatus=2 and confirmAmount!=0))");
            if (!StringUtils.isEmpty(salesOrderNo)) {
                WHERE("salesOrderNo like '%' #{salesOrderNo} '%'");
            }
            if (projectId != null) {
                WHERE("projectId = #{projectId}");
            }
            if (!StringUtils.isEmpty(distributorName)) {
                WHERE("distributorName like '%' #{distributorName} '%'");
            }
            if (!StringUtils.isEmpty(bankName)) {
                WHERE("bankName like '%' #{bankName} '%'");
            }
            if (!StringUtils.isEmpty(orderBegin)) {
                WHERE("DATE(orderTime) >= DATE(#{orderBegin})");
            }
            if (!StringUtils.isEmpty(orderEnd)) {
                WHERE("DATE(orderTime) <= DATE(#{orderEnd}) ");
            }
            if (!StringUtils.isEmpty(confirmBegin)) {
                WHERE("DATE(orderTime) >= DATE(#{confirmBegin})");
            }
            if (!StringUtils.isEmpty(confirmEnd)) {
                WHERE("DATE(orderTime) <= DATE(#{confirmEnd}) ");
            }
            if (!StringUtils.isEmpty(orderStatus)) {
                WHERE("orderStatus in (#{orderStatus})");
            }
            ORDER_BY("salesOrderNo DESC");
        }}.toString();
    }

}
