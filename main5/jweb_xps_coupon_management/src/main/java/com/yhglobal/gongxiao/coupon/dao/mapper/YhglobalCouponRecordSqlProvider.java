package com.yhglobal.gongxiao.coupon.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class YhglobalCouponRecordSqlProvider {

    public String searchByToReceiveCouponIdAndFlowNo(@Param("tablePrefix")String tablePrefix,@Param("toReceiveCouponId") Long toReceiveCouponId,
                                                     @Param("flowNo") String flowNo){
        return new SQL() {{
            SELECT("confirmId, toReceiveCouponId, confirmAmount, receiptAmount, flowNo, dataVersion, "+
                    "createTime, lastUpdateTime, receivedCurrencyCode, confirmCurrencyCode, createdById, "+
                    "createdByName,philipDocumentNo");
            FROM(tablePrefix+"_yhglobal_coupon_ledger_writeoff_record");
            WHERE("toReceiveCouponId = #{toReceiveCouponId}");
            WHERE("isRollback = 2");
            if (flowNo != null && !"".equals(flowNo)) {
                WHERE("flowNo like concat('%',#{flowNo},'%')");
            }ORDER_BY("createTime DESC");
        }}.toString();
    }

    public String searchCouponConfirm(@Param("tablePrefix")String tablePrefix,
                                      @Param("projectId") Long projectId,
                                      @Param("flowNo") String flowNo,
                                      @Param("accountType") Integer accountType,
                                      @Param("useDateStart") Date useDateStart,
                                      @Param("useDateEnd") Date useDateEnd,
                                      @Param("dateStart") Date dateStart,
                                      @Param("dateEnd") Date dateEnd){
        return new SQL() {{
            SELECT("flowNo,MIN(philipDocumentNo) AS philipDocumentNo,MIN(confirmCurrencyCode) AS confirmCurrencyCode,SUM(confirmAmount) AS confirmAmount," +
                    "SUM(receiptAmount) AS receiptAmount,MIN(useDate) AS useDate,MIN(receivedCurrencyCode) AS receivedCurrencyCode," +
                    "MIN(accountType) AS accountType,MIN(createTime) AS createTime,MIN(createdByName) AS createdByName");
            FROM(tablePrefix+"_yhglobal_coupon_ledger_writeoff_record");
            WHERE("projectId = #{projectId}");
            WHERE("isRollback = 2");
            if (flowNo != null && !"".equals(flowNo)) {
                WHERE("flowNo like concat('%',#{flowNo},'%')");
            }
            if (accountType != null && !"".equals(accountType)) {
                WHERE("accountType = #{accountType}");
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