package com.yhglobal.gongxiao.payment.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author: 葛灿
 */
public class SupplierSellHeightTransferRecordSqlProvider {

    public String selectSupplierSellHighRecordList(@Param("prefix") String prefix,
                                                   @Param("currencyCode") String currencyCode,
                                                   @Param("projectId") long projectId,
                                                   @Param("moneyFlow") Integer moneyFlow,
                                                   @Param("beginDate") String beginDate,
                                                   @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT(
                    "recordId, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, " +
                            "transferTime,projectId,projectName, supplierId, supplierName, createdById, createdByName, recordType, " +
                            "createTime");
            FROM(prefix + "_supplier_sell_height_transfer_record");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != 0) {
                WHERE("recordType = #{moneyFlow}");
            }
            if (!StringUtils.isEmpty(beginDate)) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (!StringUtils.isEmpty(endDate)) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
            ORDER_BY("recordId DESC");
        }}.toString();
    }

    public String selectIncomeAndExpenditure(@Param("prefix") String prefix,
                                             @Param("currencyCode") String currencyCode,
                                             @Param("projectId") long projectId,
                                             @Param("moneyFlow") Integer moneyFlow,
                                             @Param("beginDate") String beginDate,
                                             @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT(
                    "recordType,count(1) as count,sum(transactionAmount) as amountCount");
            FROM(prefix + "_supplier_sell_height_transfer_record");
            WHERE("projectId = #{projectId}");
            if (moneyFlow != 0) {
                WHERE("recordType = #{moneyFlow}");
            }
            if (!StringUtils.isEmpty(beginDate)) {
                WHERE("DATE(createTime) >= DATE(#{beginDate}) ");
            }
            if (!StringUtils.isEmpty(endDate)) {
                WHERE("DATE(createTime) <= DATE(#{endDate}) ");
            }
            GROUP_BY("recordType");
        }}.toString();
    }

}
