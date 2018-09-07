package com.yhglobal.gongxiao.payment.project.dao.yhglobal.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class YhglobalToReceiveCouponLedgerSqlProvider {

    //  Long projectId, String purchaseOrderNo,
    // String supplierOrderNo, Date dateS, Date dateE, Byte couponStatus, String flowNo
    public String searchByManyCondition(@Param("projectId") Long projectId,
                                    @Param("purchaseOrderNo") String purchaseOrderNo,
                                    @Param("supplierOrderNo") String supplierOrderNo,
                                    @Param("flowNo") String flowNo,
                                    @Param("dateStart") Date dateS,
                                    @Param("dateEnd") Date dateE,
                                    @Param("couponStatus") String couponStatus){
        return new SQL() {{
            SELECT("purchaseCouponLedgerId,yw.philipDocumentNo, yl.supplierOrderNo, yw.toReceiveCouponId,couponStatus,yl.purchaseOrderNo,yl.createTime AS ylCreateTime,yl.couponType,yl.currencyCode AS ylCurrencyCode," +
                    "yl.estimatedCouponAmount,yl.toBeConfirmAmount,yw.confirmCurrencyCode,yw.confirmAmount,yw.receivedCurrencyCode,yw.receiptAmount," +
                    "yw.accountType,yw.createTime AS ywCreateTime,yw.flowNo");
            FROM("yhglobal_to_receive_coupon_ledger yl LEFT  JOIN yhglobal_coupon_ledger_writeoff_record yw ON yl.purchaseCouponLedgerId = yw.toReceiveCouponId and  yw.isRollback = 2");
            if (couponStatus != null && !"".equals(couponStatus)) {
                WHERE(String.format("couponStatus in (%s)",couponStatus));
            }
            if (purchaseOrderNo != null && !"".equals(purchaseOrderNo)) {
                WHERE("yl.purchaseOrderNo like concat('%',#{purchaseOrderNo},'%')");
            }
            if (supplierOrderNo != null && !"".equals(supplierOrderNo)) {
                WHERE("yl.supplierOrderNo like concat('%',#{supplierOrderNo},'%')");
            }
            if (flowNo != null && !"".equals(flowNo)) {
                WHERE("flowNo like concat('%',#{flowNo},'%')");
            }
            if (dateS != null) {
                WHERE("yl.createTime >= #{dateStart}");
            }
            if (dateE != null) {
                WHERE("yl.createTime <= #{dateEnd}");
            }ORDER_BY("purchaseCouponLedgerId , yw.createTime DESC");
        }}.toString();
    }
}
