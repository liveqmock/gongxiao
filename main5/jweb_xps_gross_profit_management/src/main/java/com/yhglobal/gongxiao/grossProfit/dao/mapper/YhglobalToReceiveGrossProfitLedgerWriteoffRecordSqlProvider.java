package com.yhglobal.gongxiao.grossProfit.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class YhglobalToReceiveGrossProfitLedgerWriteoffRecordSqlProvider {

    public String selectGrossProfitBook(@Param("tablePrefix")String tablePrefix,
                                      @Param("projectId") Long projectId,
                                      @Param("flowNo") String flowNo,
                                      @Param("transferIntoPattern") Integer transferIntoPattern,
                                      @Param("useDateStart") Date useDateStart,
                                      @Param("useDateEnd") Date useDateEnd,
                                      @Param("dateStart") Date dateStart,
                                      @Param("dateEnd") Date dateEnd){
        return new SQL() {{
            SELECT("flowNo,MIN(confirmCurrencyCode) AS confirmCurrencyCode,SUM(confirmAmount) AS confirmAmount," +
                    "SUM(receiptAmount) AS receiptAmount,MIN(useDate) AS useDate,MIN(receivedCurrencyCode) AS receivedCurrencyCode," +
                    "MIN(transferIntoPattern) AS transferIntoPattern,MIN(createTime) AS createTime,MIN(createdByName) AS createdByName, SUM(differenceAmount) AS differenceAmount");
            FROM(tablePrefix+"_yhglobal_to_receive_gross_profit_ledger_writeoff_record");
            WHERE("projectId = #{projectId}");
            WHERE("isRollback = 2");
            if (flowNo != null && !"".equals(flowNo)) {
                WHERE("flowNo like concat('%',#{flowNo},'%')");
            }
            if (transferIntoPattern != null && !"".equals(transferIntoPattern)) {
                WHERE("transferIntoPattern = #{transferIntoPattern}");
            }
            if (dateStart != null) {
                WHERE("transactionTime >= #{dateStart}");
            }
            if (dateEnd != null) {
                WHERE("transactionTime <= #{dateEnd}");
            }
            if (useDateStart != null) {
                WHERE("statementCheckingTime >= #{useDateStart}");
            }
            if (useDateEnd != null) {
                WHERE("statementCheckingTime <= #{useDateEnd}");
            }
            GROUP_BY("flowNo");
            ORDER_BY("createTime DESC");
        }}.toString();
    }
}
