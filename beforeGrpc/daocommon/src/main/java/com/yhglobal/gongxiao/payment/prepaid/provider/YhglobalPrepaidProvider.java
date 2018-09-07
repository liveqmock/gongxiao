package com.yhglobal.gongxiao.payment.prepaid.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 17:56 2018/5/9
 */
public class YhglobalPrepaidProvider {
    public String searchPrepaidInfo(@Param("projectId") Long projectId,
                                           @Param("prepaidNo") String prepaidNo,
                                           @Param("receivables") String receivables,
                                          @Param("dateStart") Date dateStart,
                                          @Param("dateEnd") Date dateEnd){
        return new SQL() {{
            SELECT("prepaidId, prepaidNo, standardCurrencyAmount,receivables,supplierName,createTime,createdByName");
            FROM("yhglobal_prepaid_info");
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
    public String searchPrepaidConfirm(@Param("projectId") Long projectId,
                                    @Param("flowCode") String flowCode,
                                    @Param("accountType") Integer accountType,
                                       @Param("useDateStart") Date useDateStart,
                                       @Param("useDateEnd") Date useDateEnd,
                                    @Param("dateStart") Date dateStart,
                                    @Param("dateEnd") Date dateEnd){
        return new SQL() {{
            SELECT("flowCode,MIN(philipNo) AS philipNo,MIN(currencyCode) AS currencyCode,SUM(confirmAmount) AS confirmAmount,SUM(receiptAmount) AS receiptAmount,MIN(useDate) AS useDate,MIN(useCurrencyCode) AS useCurrencyCode,MIN(accountType) AS accountType,MIN(createTime) AS createTime,MIN(createdByName) AS createdByName");
            FROM("yhglobal_prepaid_ledger_writeoff_record");
            WHERE(" isRollback = 0 and projectId = #{projectId}");
            if (flowCode != null && !"".equals(flowCode)) {
                WHERE("flowCode like '%' #{flowCode} '%'");
            }
            if (accountType != null && !"".equals(accountType)) {
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

    public String selectReceiveAndRecord(@Param("projectId") Long projectId,
                                    @Param("orderId") String orderId,
                                    @Param("flowCode") String flowCode,
                                    @Param("accountType") Integer accountType,
                                    @Param("dateStart") Date dateStart,
                                    @Param("dateEnd") Date dateEnd,
                                    @Param("dateStartConfirm") Date dateStartConfirm,
                                    @Param("dateEndConfirm") Date dateEndConfirm,
                                    @Param("receiveStatus") String receiveStatus ){
        return new SQL() {{
            SELECT("tr.receiveId,orderId,tr.projectId,projectName,supplierId,supplierName",
                    "salesContractNo,receiveAmount,tr.currencyCode,toBeConfirmAmount",
                    "dateInto,differenceAmountAdjustPattern,receiveStatus,wr.currencyCode AS confirmCurrencyCode",
                    "tr.createTime as createTime,wr.createTime as confirmTime,  wr.confirmAmount,wr.receiptAmount,wr.flowCode,wr.useDate,wr.accountType"
            );
            FROM("yhglobal_to_receive_prepaid_ledger tr  left join yhglobal_prepaid_ledger_writeoff_record wr on tr.receiveId = wr.receiveId and wr.isRollback = 0");
            WHERE("tr.projectId = #{projectId}");
            if (orderId != null && !"".equals(orderId)) {
                WHERE("orderId like '%' #{orderId} '%'");
            }
            if (flowCode != null && !"".equals(flowCode)) {
                WHERE("flowCode like '%' #{flowCode} '%'");
            }
            if (accountType != null && !"".equals(accountType)) {
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
    public String selectReceiveAndRecordCount(@Param("projectId") Long projectId,
                                         @Param("receiveIds") String receiveIds ){
        return new SQL() {{
            SELECT("receiveAmount,tr.toBeConfirmAmount,sum(wr.receiptAmount) AS receiptAmount,sum(wr.confirmAmount) AS confirmAmount");
            FROM("yhglobal_to_receive_prepaid_ledger tr  left join yhglobal_prepaid_ledger_writeoff_record wr on tr.receiveId = wr.receiveId and wr.isRollback = 0");
            WHERE("tr.projectId = #{projectId}");
            WHERE(String.format("tr.receiveId in (%s)", receiveIds));

            GROUP_BY("tr.receiveId,receiveAmount,toBeConfirmAmount");
        }}.toString();
    }
}
