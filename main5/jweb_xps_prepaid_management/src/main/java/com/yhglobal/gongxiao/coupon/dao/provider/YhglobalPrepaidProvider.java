package com.yhglobal.gongxiao.coupon.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 17:56 2018/5/9
 */
public class YhglobalPrepaidProvider {
    public String searchPrepaidInfo(@Param("tablePrefix")String tablePrefix,
                                    @Param("projectId") Long projectId,
                                           @Param("prepaidNo") String prepaidNo,
                                           @Param("receivables") String receivables,
                                          @Param("dateStart") Date dateStart,
                                          @Param("dateEnd") Date dateEnd){
        return new SQL() {{
            SELECT("prepaidId, prepaidNo, standardCurrencyAmount,receivables,supplierName,createTime,createdByName");
            FROM(tablePrefix+"_yhglobal_prepaid_info");
            WHERE("projectId = #{projectId}");
            if (prepaidNo != null && !"".equals(prepaidNo)) {
                WHERE("prepaidNo like '%' #{prepaidNo} '%'");
            }
            if (receivables != null && !"".equals(receivables)) {
                WHERE("receivables like '%' #{receivables} '%'");
            }
            if (dateStart != null) {
                WHERE("createTime >= #{dateStart}");
            }
            if (dateEnd != null) {
                WHERE("createTime <= #{dateEnd}");
            }
        }}.toString();
    }
    public String searchPrepaidConfirm(@Param("tablePrefix")String tablePrefix,
                                       @Param("projectId") Long projectId,
                                    @Param("flowCode") String flowCode,
                                    @Param("accountType") Integer accountType,
                                       @Param("useDateStart") Date useDateStart,
                                       @Param("useDateEnd") Date useDateEnd,
                                    @Param("dateStart") Date dateStart,
                                    @Param("dateEnd") Date dateEnd){
        return new SQL() {{
            SELECT("flowCode,MIN(philipNo) AS philipNo,MIN(currencyCode) AS currencyCode,SUM(confirmAmount) AS confirmAmount,SUM(receiptAmount) AS receiptAmount,MIN(useDate) AS useDate,MIN(useCurrencyCode) AS useCurrencyCode,MIN(accountType) AS accountType,MIN(createTime) AS createTime,MIN(createdByName) AS createdByName");
            FROM(tablePrefix+"_yhglobal_prepaid_ledger_writeoff_record");
            WHERE(" isRollback = 2 and projectId = #{projectId}");
            if (flowCode != null && !"".equals(flowCode)) {
                WHERE("flowCode like '%' #{flowCode} '%'");
            }
            if (accountType != null && !"".equals(accountType)  && 0 != accountType) {
                WHERE("accountType = #{accountType}");
            }
            if (dateStart != null) {
                WHERE("createTime >= #{dateStart}");
            }
            if (dateEnd != null) {
                WHERE("createTime <= #{dateEnd}");
            }
            if (useDateStart != null) {
                WHERE("useDate >= #{useDateStart}");
            }
            if (useDateEnd != null) {
                WHERE("useDate <= #{useDateEnd}");
            }
            GROUP_BY("flowCode");
            ORDER_BY("createTime DESC");
        }}.toString();
    }

    public String selectReceiveAndRecord(@Param("tablePrefix")String tablePrefix,
                                         @Param("projectId") Long projectId,
                                    @Param("orderId") String orderId,
                                    @Param("flowCode") String flowCode,
                                    @Param("accountType") Integer accountType,
                                    @Param("dateStart") Date dateStart,
                                    @Param("dateEnd") Date dateEnd,
                                    @Param("dateStartConfirm") Date dateStartConfirm,
                                    @Param("dateEndConfirm") Date dateEndConfirm,
                                    @Param("receiveStatus") String receiveStatus ){
        return new SQL() {{
            SELECT("tr.receiveId,tr.orderId AS orderId,tr.projectId,projectName,supplierId,supplierName,"+
                    "salesContractNo,receiveAmount,tr.currencyCode,toBeConfirmAmount,"+
                    "dateInto,differenceAmountAdjustPattern,receiveStatus,wr.currencyCode AS confirmCurrencyCode, "+
                    "tr.createTime as createTime,wr.createTime as confirmTime,  wr.confirmAmount,wr.receiptAmount,wr.flowCode,wr.useDate,wr.accountType"
            );
            FROM(tablePrefix+"_yhglobal_to_receive_prepaid_ledger tr  left join "+tablePrefix+"_yhglobal_prepaid_ledger_writeoff_record wr on tr.receiveId = wr.receiveId and wr.isRollback = 2");
            WHERE("tr.projectId = #{projectId}");
            if (orderId != null && !"".equals(orderId)) {
                WHERE("orderId like '%' #{orderId} '%'");
            }
            if (flowCode != null && !"".equals(flowCode)) {
                WHERE("flowCode like '%' #{flowCode} '%'");
            }
            if (accountType != null && !"".equals(accountType)  && 0 != accountType) {
                WHERE("accountType = #{accountType}");
            }
            if (receiveStatus != null &&!"".equals(receiveStatus)) {
                WHERE(String.format("receiveStatus in (%s)", receiveStatus) );
            }
            if (dateStart != null) {
                WHERE("tr.createTime >= #{dateStart}");
            }
            if (dateEnd != null) {
                WHERE("tr.createTime <= #{dateEnd}");
            }
            if (dateStartConfirm != null) {
                WHERE("wr.createTime >= #{dateStartConfirm}");
            }
            if (dateEndConfirm != null) {
                WHERE("wr.createTime <= #{dateEndConfirm}");
            }
            ORDER_BY("tr.receiveId,wr.createTime DESC");
        }}.toString();
    }

    public String selectReceiveAndRecordCount(@Param("tablePrefix")String tablePrefix,
                                              @Param("projectId") Long projectId,
                                              @Param("receiveIds") String receiveIds ){
        return new SQL() {{
            SELECT("receiveAmount,tr.toBeConfirmAmount,sum(wr.receiptAmount) AS receiptAmount,sum(wr.confirmAmount) AS confirmAmount");
            FROM(tablePrefix+"_yhglobal_to_receive_prepaid_ledger tr  left join  "+tablePrefix+"_yhglobal_prepaid_ledger_writeoff_record wr on tr.receiveId = wr.receiveId and wr.isRollback = 2");
            WHERE("tr.projectId = #{projectId}");
            if (receiveIds != null && !"".equals(receiveIds)) {
                WHERE(String.format("tr.receiveId in (%s)", receiveIds));
            }
            GROUP_BY("tr.receiveId,receiveAmount,toBeConfirmAmount");
        }}.toString();
    }

    public String selectByIdsStr(@Param("tablePrefix")String tablePrefix,
                                 @Param("ids") String ids ){
        return new SQL() {{
            SELECT("receiveId, orderId,projectId,projectName,supplierId,supplierId, salesContractNo, receiveAmount, currencyCode," +
                    " toBeConfirmAmount, confirmAmount, receiptAmount, dateInto, differenceAmountAdjustPattern, confirmRecord, " +
                    "receiveStatus,dataVersion, createTime, lastUpdateTime, createdById, createdByName");
            FROM(tablePrefix+"_yhglobal_to_receive_prepaid_ledger");
            WHERE(String.format("receiveId in (%s)", ids));
        }}.toString();
    }

    public String getCountByTime(@Param("tablePrefix")String tablePrefix,
                                       @Param("projectId") Long projectId,
                                       @Param("dateStart") Date dateStart,
                                       @Param("dateEnd") Date dateEnd){
        return new SQL() {{
            SELECT("SUM(receiveAmount) AS receiveAmount,SUM(receiptAmount) AS receiptAmount,SUM(toBeConfirmAmount) AS toBeConfirmAmount");
            FROM(tablePrefix+"_yhglobal_to_receive_prepaid_ledger");
            WHERE("projectId = #{projectId}");
            if (dateStart != null) {
                WHERE("createTime >= #{dateStart}");
            }
            if (dateEnd != null) {
                WHERE("createTime <= #{dateEnd}");
            }
        }}.toString();
    }
}
