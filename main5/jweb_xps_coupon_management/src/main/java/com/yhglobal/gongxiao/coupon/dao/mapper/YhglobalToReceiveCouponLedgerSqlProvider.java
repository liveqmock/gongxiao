package com.yhglobal.gongxiao.coupon.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class YhglobalToReceiveCouponLedgerSqlProvider {

    //  Long projectId, String purchaseOrderNo,
    // String supplierOrderNo, Date dateS, Date dateE, Byte couponStatus, String flowNo
    public String searchByManyCondition(@Param("tablePrefix")String tablePrefix,
                                        @Param("projectId") Long projectId,
                                    @Param("purchaseOrderNo") String purchaseOrderNo,
                                    @Param("supplierOrderNo") String supplierOrderNo,
                                    @Param("flowNo") String flowNo,
                                    @Param("dateStart") Date dateS,
                                    @Param("dateEnd") Date dateE,
                                    @Param("couponStatus") String couponStatus, @Param("couponTypes") String couponTypes){
        return new SQL() {{
            SELECT("purchaseCouponLedgerId,supplierOrderNo,yw.toReceiveCouponId,couponStatus,yl.purchaseOrderNo,yl.createTime AS ylCreateTime,yl.purchaseTime AS ylPurchaseTime, yl.couponType,yl.currencyCode AS ylCurrencyCode," +
                    "yl.estimatedCouponAmount,yl.toBeConfirmAmount,yw.confirmCurrencyCode,yw.confirmAmount,yw.receivedCurrencyCode,yw.receiptAmount," +
                    "yw.accountType,yw.createTime AS ywCreateTime,yw.flowNo, yw.philipDocumentNo");
            FROM(""+tablePrefix+"_yhglobal_to_receive_coupon_ledger yl LEFT  JOIN  "+tablePrefix+"_yhglobal_coupon_ledger_writeoff_record yw ON yl.purchaseCouponLedgerId = yw.toReceiveCouponId and  yw.isRollback = 2");
            if (couponStatus != null && !"".equals(couponStatus)) {
                WHERE(String.format("couponStatus in (%s)",couponStatus));
            }
            if (couponTypes != null && !"".equals(couponTypes)) {
                WHERE(String.format("couponType in (%s)",couponTypes));
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

    public String searchByIds(@Param("tablePrefix")String tablePrefix, @Param("ids") String ids){
        return new SQL() {{
            SELECT("purchaseCouponLedgerId, couponStatus, couponType,  " +
                    " currencyCode, supplierOrderNo,purchaseOrderNo, purchaseTime, estimatedCouponAmount, " +
                    " confirmedCouponAmount, receivedCouponAmount,  createTime, lastUpdateTime, " +
                    "confirmRecord, toBeConfirmAmount");
            FROM(""+tablePrefix+"_yhglobal_to_receive_coupon_ledger ");
            if (ids != null && !"".equals(ids)) {
                WHERE(String.format("purchaseCouponLedgerId in (%s)",ids));
            }
        }}.toString();
    }
}
