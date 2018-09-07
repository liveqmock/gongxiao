package com.yhglobal.gongxiao.grossProfit.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class YhglobalToReceiveGrossProfitLedgerSqlProvider {

    //  Long projectId, String purchaseOrderNo,
    // String supplierOrderNo, Date dateS, Date dateE, Byte couponStatus, String flowNo
    public String selectByManyCondition(@Param("tablePrefix")String tablePrefix,
                                        @Param("projectId") Long projectId,
                                    @Param("purchaseOrderNo") String purchaseOrderNo,
                                    @Param("salesOrderNo") String salesOrderNo,
                                    @Param("flowNo") String flowNo,
                                    @Param("dateStart") Date dateS,
                                    @Param("dateEnd") Date dateE,
                                    @Param("confirmStatus") String confirmStatus){
        return new SQL() {{
            SELECT("yl.grossProfitId AS grossProfitId,yl.confirmStatus, yl.salesOrderNo, yl.purchaseOrderNo, yl.createTime, yl.estimatedGrossProfitAmount, yl.toBeConfirmAmount, " +
                    "yw.confirmAmount AS confirmedAmount, yw.receiptAmount AS receivedAmount, yw.createTime AS ywCreateTime, yw.useDate, yw.flowNo");
            FROM(""+tablePrefix+"_yhglobal_to_receive_gross_profit_ledger yl LEFT  JOIN  "+tablePrefix+"_yhglobal_to_receive_gross_profit_ledger_writeoff_record yw ON yl.grossProfitId = yw.grossProfitId and  yw.isRollback = 2");
            if (confirmStatus != null && !"".equals(confirmStatus)) {
                WHERE(String.format("couponStatus in (%s)",confirmStatus));
            }
            if (purchaseOrderNo != null && !"".equals(purchaseOrderNo)) {
                WHERE("yl.purchaseOrderNo like concat('%',#{purchaseOrderNo},'%')");
            }
            if (salesOrderNo != null && !"".equals(salesOrderNo)) {
                WHERE("yl.salesOrderNo like concat('%',#{salesOrderNo},'%')");
            }
            if (flowNo != null && !"".equals(flowNo)) {
                WHERE("flowNo like concat('%',#{flowNo},'%')");
            }
            if (dateS != null) {
                WHERE("yl.salesTime >= #{dateStart}");
            }
            if (dateE != null) {
                WHERE("yl.salesTime <= #{dateEnd}");
            }ORDER_BY("yl.grossProfitId , yw.createTime DESC");
        }}.toString();
    }

    public String selectByIds(@Param("tablePrefix")String tablePrefix, @Param("ids") String ids){
        return new SQL() {{
            SELECT("grossProfitId, confirmStatus, salesOrderNo, purchaseOrderNo, estimatedGrossProfitAmount, toBeConfirmAmount, currencyCode");
            FROM(tablePrefix+"_yhglobal_to_receive_gross_profit_ledger ");
            if (ids != null && !"".equals(ids)) {
                WHERE(String.format("grossProfitId in (%s)",ids));
            }
        }}.toString();
    }
}
