package com.yhglobal.gongxiao.sales.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * 收款确认Provider
 *
 * @author: 葛灿
 */
public class SalesCashConfirmSqlProvider {


    public String selectListSelectively(@Param("salesOrderNo") String salesOrderNo,
                                        @Param("projectId") Long projectId,
                                        @Param("distributorName") String distributorName,
                                        @Param("bankName") String bankName,
                                        @Param("orderBegin") Date orderBegin,
                                        @Param("orderEnd") Date orderEnd,
                                        @Param("confirmBegin") Date confirmBegin,
                                        @Param("confirmEnd") Date confirmEnd,
                                        @Param("orderStatus") Integer[] orderStatus) {
        return new SQL() {{
            SELECT(
                    "confirmId, salesOrderNo, flowNo, recipientStatus, confirmStatus, distributorId, " +
                            "distributorName, projectId, projectName, shouldReceiveCurrency, shouldReceiveAmount, " +
                            "unreceiveCurrency, unreceiveAmount, confirmCurrency, confirmAmount, recipientCurrency, " +
                            "recipientAmount, confirmTime, receiveTime, recipient, payer, orderTime, " +
                            "createTime, dataVersion, lastUpdateTime,  tracelog,bankName, bankFlowNo");
            FROM("sales_cash_confirm");
            WHERE("canceled = 0");
            WHERE("(confirmStatus!=2 or (confirmStatus=2 and confirmAmount!=0))");
            if (salesOrderNo != null && !"".equals(salesOrderNo)) {
                WHERE("salesOrderNo like '%' #{salesOrderNo} '%'");
            }
            if (projectId != null) {
                WHERE("projectId = #{projectId}");
            }
            if (distributorName != null && !"".equals(distributorName)) {
                WHERE("distributorName like '%' #{distributorName} '%'");
            }
            if (bankName != null && !"".equals(bankName)) {
                WHERE("bankName = #{bankName} ");
            }
            if (orderBegin != null) {
                WHERE("DATE(orderTime) >= DATE(#{orderBegin})");
            }
            if (orderEnd != null) {
                WHERE("DATE(orderTime) <= DATE(#{orderEnd}) ");
            }
            if (confirmBegin != null) {
                WHERE("DATE(confirmTime) >= DATE(#{confirmBegin})");
            }
            if (confirmEnd != null) {
                WHERE("DATE(confirmTime) <= DATE(#{confirmEnd}) ");
            }
            if (orderStatus != null && !"".equals(orderStatus)) {
                WHERE("orderStatus in (#{orderStatus})");
            }
            ORDER_BY("salesOrderNo DESC");
        }}.toString();
    }

}
