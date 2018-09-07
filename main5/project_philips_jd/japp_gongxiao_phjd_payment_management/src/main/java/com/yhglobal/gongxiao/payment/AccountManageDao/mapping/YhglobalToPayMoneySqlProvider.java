package com.yhglobal.gongxiao.payment.AccountManageDao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

/**
 * @author 王帅
 */
public class YhglobalToPayMoneySqlProvider {


    public String queryData(@Param("paymentApplyNo")String paymentApplyNo,
                            @Param("purchasePlanNo")String purchasePlanNo,
                            @Param("supplierId")Long supplierId,
                            @Param("status")Byte status,
                            @Param("settlementType")Byte settlementType,
                            @Param("paymentType")Byte paymentType,
                            @Param("dateS")Date dateS,
                            @Param("dateE")Date dateE){
        return new SQL() {{
            SELECT(" paymentId, confirmStatus,  status, paymentApplyNo, createTime, estimatedPaymentTime,supplierId,supplierName," +
                    "settlementType,creditPaymentDays,paymentType,bankAcceptancePeriod,purchasePlanNo,orderTime,currencyCode, toPayAmount," +
                    "toBePayAmount,confirmAmount, receiptAmount,receiverName,projectId,projectName, dataVersion,lastUpdateTime, confirmRecord," +
                    " planOrderAmount, tracelog");
            FROM("yhglobal_to_pay_money ");
            if (paymentApplyNo != null && !"".equals(paymentApplyNo)) {
                WHERE("paymentApplyNo like concat('%',#{paymentApplyNo},'%')");
            }
            if (purchasePlanNo != null && !"".equals(purchasePlanNo)) {
                WHERE("purchasePlanNo like concat('%',#{purchasePlanNo},'%')");
            }
            if (supplierId != null && !"".equals(supplierId)) {
                WHERE("supplierId like concat('%',#{supplierId},'%')");
            }
            if (status != null && !"".equals(status)) {
                WHERE("status = #{status}");
            }
            if (settlementType != null && !"".equals(settlementType)) {
                WHERE("settlementType = #{settlementType}");
            }
            if (paymentType != null && !"".equals(paymentType)) {
                WHERE("paymentType = #{paymentType}");
            }
            if (dateS != null) {
                WHERE("createTime >= #{dateS}");
            }
            if (dateE != null) {
                WHERE("createTime <= #{dateE}");
            }ORDER_BY("createTime  DESC");
        }}.toString();
    }

    public String selectByManyConditions(@Param("paymentApplyNo")String paymentApplyNo,
                            @Param("purchasePlanNo")String purchasePlanNo,
                            @Param("supplierName")String supplierName,
                            @Param("confirmStatus")String confirmStatus,
                            @Param("settlementType")Byte settlementType,
                            @Param("paymentType")Byte paymentType,
                            @Param("dateS")Date payPlanDateStart,
                            @Param("dateE")Date payPlanDateEnd){
        return new SQL() {{
            SELECT(" paymentId, confirmStatus,  status, paymentApplyNo, createTime, estimatedPaymentTime,supplierId,supplierName," +
                    "settlementType,creditPaymentDays,paymentType,bankAcceptancePeriod,purchasePlanNo,orderTime,currencyCode, toPayAmount," +
                    "toBePayAmount,confirmAmount, receiptAmount,receiverName,projectId,projectName, dataVersion,lastUpdateTime, confirmRecord," +
                    " planOrderAmount, tracelog");
            FROM("yhglobal_to_pay_money ");
            if (paymentApplyNo != null && !"".equals(paymentApplyNo)) {
                WHERE("paymentApplyNo like concat('%',#{paymentApplyNo},'%')");
            }
            if (purchasePlanNo != null && !"".equals(purchasePlanNo)) {
                WHERE("purchasePlanNo like concat('%',#{purchasePlanNo},'%')");
            }
            if (supplierName != null && !"".equals(supplierName)) {
                WHERE("supplierName like concat('%',#{supplierName},'%')");
            }
            if (confirmStatus != null && !"".equals(confirmStatus)) {
                WHERE(String.format("confirmStatus in (%s)"),confirmStatus);
            }
            if (settlementType != null && !"".equals(settlementType)) {
                WHERE("settlementType = #{settlementType}");
            }
            if (paymentType != null && !"".equals(paymentType)) {
                WHERE("paymentType = #{paymentType}");
            }
            if (payPlanDateStart != null) {
                WHERE("createTime >= #{payPlanDateStart}");
            }
            if (payPlanDateEnd != null) {
                WHERE("createTime <= #{payPlanDateEnd}");
            }ORDER_BY("createTime  DESC");
        }}.toString();
    }
//    public String searchMany(@Param("ids")String ids){
//        return new SQL() {{
//            SELECT("purchaseCouponLedgerId, couponStatus, couponType, couponModel, couponRatio, "+
//                    "projectId, projectName, supplierId, supplierName, currencyCode, supplierOrderNo," +
//                    "purchaseOrderNo, purchaseTime, estimatedCouponAmount, estimatedPostingDate," +
//                    "confirmedCouponAmount, receivedCouponAmount, dataVersion, createTime, lastUpdateTime," +
//                    "confirmRecord, toBeConfirmAmount, tracelog");
//            FROM("yhglobal_to_receive_coupon_ledger");
//            WHERE(String.format("purchaseCouponLedgerId in (%s)",ids));
//        }}.toString();
//    }
}
