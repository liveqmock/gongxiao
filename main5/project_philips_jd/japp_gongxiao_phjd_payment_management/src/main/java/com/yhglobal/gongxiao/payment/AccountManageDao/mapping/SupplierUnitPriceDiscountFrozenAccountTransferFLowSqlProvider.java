package com.yhglobal.gongxiao.payment.AccountManageDao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class SupplierUnitPriceDiscountFrozenAccountTransferFLowSqlProvider {

    public String selectByConditions(@Param("moneyFlow") Integer moneyFlow,
                                     @Param("dateS") Date dateS,
                                     @Param("dateE") Date dateE, @Param("tablePrefix") String tablePrefix) {
        return new SQL() {{
            SELECT(
                    "purchaseOrderNo,transactionAmount,currencyCode,amountAfterTransaction,flowType,transferTime,createdByName, remark");
            FROM(tablePrefix+"_supplier_unit_price_discount_frozen_account_transfer_flow");
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
            FROM(tablePrefix+"_supplier_unit_price_discount_frozen_account_transfer_flow");
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
