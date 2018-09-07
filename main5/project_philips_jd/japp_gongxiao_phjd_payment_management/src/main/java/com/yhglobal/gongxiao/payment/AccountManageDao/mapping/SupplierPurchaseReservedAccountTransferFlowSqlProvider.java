package com.yhglobal.gongxiao.payment.AccountManageDao.mapping;

import com.yhglobal.gongxiao.payment.model.SupplierPurchaseReservedAccountTransferFlow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class SupplierPurchaseReservedAccountTransferFlowSqlProvider {

    public String selectByConditions(@Param("moneyFlow") Integer moneyFlow,
                                     @Param("dateS") Date dateS,
                                     @Param("dateE") Date dateE, @Param("tablePrefix") String tablePrefix) {
        return new SQL() {{
            SELECT(
                    "purchaseOrderNo,transactionAmount,currencyCode,amountAfterTransaction,flowType,transferTime,createdByName, remark");
            FROM(tablePrefix+"_supplier_purchase_reserved_account_transfer_flow");
            if (moneyFlow != null && !"".equals(moneyFlow)) {
                WHERE("flowType = #{moneyFlow}");
            }
            if (dateS != null) {
                WHERE("DATE(transferTime) >= DATE(#{dateS}) ");
            }
            if (dateE != null) {
                WHERE("DATE(transferTime) <= DATE(#{dateE}) ");
            }
            ORDER_BY("flowId DESC");
        }}.toString();
    }

    public String selectTotal(@Param("moneyFlow") Integer moneyFlow,
                              @Param("dateS") Date dateS,
                              @Param("dateE") Date dateE, @Param("tablePrefix") String tablePrefix) {
        return new SQL() {{
            SELECT(
                    "flowType as flowType,count(1) as count,sum(transactionAmount) as amountCount");
            FROM(tablePrefix+"_supplier_purchase_reserved_account_transfer_flow");
            if (moneyFlow != null && !"".equals(moneyFlow)) {
                WHERE("flowType = #{moneyFlow}");
            }
            if (dateS != null) {
                WHERE("DATE(transferTime) >= DATE(#{dateS}) ");
            }
            if (dateE != null) {
                WHERE("DATE(transferTime) <= DATE(#{dateE}) ");
            }
            GROUP_BY("flowType");
        }}.toString();
    }
}