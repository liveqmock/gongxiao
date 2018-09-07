package com.yhglobal.gongxiao.phjd.dao.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


/**
 * 采购单主表辅助SQL
 *
 * @author weizecheng
 * @date 2018/9/5 11:25
 */
public class PurchaseOrderSqlProvider {
    public String getList(@Param("tablePrefix") String tablePrefix,
                          @Param("purchaseOrderNo") String purchaseOrderNo,
                          @Param("supplierId") String supplierId,
                          @Param("warehouseId") String warehouseId,
                          @Param("productCode") String productCode,
                          @Param("requireArrivalDate") String requireArrivalDate,
                          @Param("arrivalDeadline") String arrivalDeadline,
                          @Param("businessDate") String businessDate) {
        return new SQL() {{
            SELECT("purchaseOrderId, orderStatus, purchaseOrderNo, projectId, projectName, brandId ",
                    "brandName, supplierId,supplierName,warehouseId, warehouseName,supplierReceipt,cashAmount, currencyId, currencyName, paymentMode ",
                    "paymentChannel, shippingMode, expectedInboundDate, orderDeadlineDate, brandOrderNo ",
                    "contractReferenceOrderNo, remark, purchaseCategory, totalQuantity, couponAmountUse ",
                    "prepaidAmountUse, purchaseGuideAmount, purchasePrivilegeAmount, returnCash ",
                    "purchaseShouldPayAmount, purchaseActualPayAmount, couponActivityInfo, paymentFlag ",
                    "productInfo, inTransitQuantity, inStockQuantity, canceledQuantity, returnedQuantity ",
                    "unhandledQuantity, ongoingInboundOrderInfo, finishedInboundOrderInfo, dataVersion ",
                    "createdById, createdByName, createTime, lastUpdateTime, tracelog,easOrderNumber,easPurchaseOrderId");
            FROM(tablePrefix+"_purchase_order");

            if ( StringUtils.isNotBlank(purchaseOrderNo)) {
                WHERE("purchaseOrderNo  = #{purchaseOrderNo}");
            }
            if (StringUtils.isNotBlank(supplierId)) {
                WHERE("supplierId = #{supplierId}");
            }
            if (StringUtils.isNotBlank(warehouseId)) {
                WHERE("warehouseId = #{warehouseId} ");
            }
            if (StringUtils.isNotBlank(productCode)){
                WHERE("productInfo like '%' #{productCode} '%' ");
            }
            if (StringUtils.isNotBlank(requireArrivalDate)) {
                WHERE("DATE(expectedInboundDate) = DATE(#{requireArrivalDate}) ");
            }
            if (StringUtils.isNotBlank(arrivalDeadline)) {
                WHERE("DATE(orderDeadlineDate) = DATE(#{arrivalDeadline}) ");
            }
            if (StringUtils.isNotBlank(businessDate)) {
                WHERE("DATE(createTime) = DATE(#{businessDate}) ");
            }
            WHERE("orderStatus!=99");
            ORDER_BY("createTime DESC");

        }}.toString();

    }

    public String getPurchaseReturnList(@Param("projectId") String projectId,
                                        @Param("warehouseId") String warehouseId,
                                        @Param("orderNumber") String orderNumber,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT("purchaseReturnedOrderId, returnedOrderStatus, returnedType, purchaseReturnedOrderNo ",
                    "outboundOrderNumber, projectId, projectName, brandId, brandName, currencyCode ",
                    "currencyName, originalPurchaseOrderNo, originalGongxiaoInboundOrderNo, warehouseId",
                    "warehouseName, recipientName, recipientMobile, recipientAddress, recipientDetailAddress ",
                    "recipientCompanyName, shippingMode, logisticsOrderNo, logisticsCompany, freight ",
                    "freightPaidBy, returnedCouponAmount, returnedPrepaidAmount, returnedCashAmount ",
                    "sourceOrderNumber, totalReturnedQuantity, totalImperfectQuantity, outStockQuantity",
                    "deliveredQuantity, outboundOrderInfo, returnItemInfo, dataVersion, createdById ",
                    "createdByName, createTime, lastUpdateTime, tracelog");
            FROM("purchase_returned_order");

            if (StringUtils.isNotBlank(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (StringUtils.isNotBlank(warehouseId) ) {
                WHERE("warehouseId = #{warehouseId}");
            }
            if (StringUtils.isNotBlank(orderNumber)) {
                WHERE("purchaseReturnedOrderNo = #{orderNumber} || originalPurchaseOrderNo = #{orderNumber} ");
            }
            if (StringUtils.isNotBlank(startDate)) {
                WHERE("createTime >= #{startDate} ");
            }
            if (StringUtils.isNotBlank(endDate)) {
                WHERE("createTime <= #{endDate}) ");
            }
        }}.toString();

    }

}
