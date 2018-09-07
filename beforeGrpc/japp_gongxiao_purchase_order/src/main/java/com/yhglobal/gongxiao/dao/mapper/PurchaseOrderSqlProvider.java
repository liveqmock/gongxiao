package com.yhglobal.gongxiao.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 * @create: 2018-03-08 12:22
 **/
public class PurchaseOrderSqlProvider {
    public String getList(@Param("projectId") String projectId,
                          @Param("purchaseOrderNo") String purchaseOrderNo,
                          @Param("supplierId") String supplierId,
                          @Param("warehouseId") String warehouseId,
                          @Param("productCode") String productCode,
                          @Param("requireArrivalDate") String requireArrivalDate,
                          @Param("arrivalDeadline") String arrivalDeadline,
                          @Param("businessDate") String businessDate) {
        return new SQL() {{
            SELECT("purchaseOrderId, orderStatus, purchaseOrderNo, projectId, projectName, brandId ",
                    "brandName, supplierId,supplierName,warehouseId, warehouseName, currencyId, currencyName, paymentMode ",
                    "paymentChannel, shippingMode, expectedInboundDate, orderDeadlineDate, brandOrderNo ",
                    "contractReferenceOrderNo, remark, purchaseCategory, totalQuantity, couponAmountUse ",
                    "prepaidAmountUse, purchaseGuideAmount, purchasePrivilegeAmount, returnCash ",
                    "purchaseShouldPayAmount, purchaseActualPayAmount, couponActivityInfo, paymentFlag ",
                    "productInfo, inTransitQuantity, inStockQuantity, canceledQuantity, returnedQuantity ",
                    "unhandledQuantity, ongoingInboundOrderInfo, finishedInboundOrderInfo, dataVersion ",
                    "createdById, createdByName, createTime, lastUpdateTime, tracelog,easOrderNumber,easPurchaseOrderId");
            FROM("purchase_order");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (purchaseOrderNo != null && !"".equals(purchaseOrderNo)) {
                WHERE("purchaseOrderNo  = #{purchaseOrderNo}");
            }
            if (supplierId != null && !"".equals(supplierId)) {
                WHERE("supplierId = #{supplierId}");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("warehouseId = #{warehouseId} ");
            }
            if (productCode!=null && !"".equals(productCode)){
                WHERE("productInfo like '%' #{productCode} '%' ");
            }
            if (requireArrivalDate != null && !"".equals(requireArrivalDate)) {
                WHERE("DATE(expectedInboundDate) = DATE(#{requireArrivalDate}) ");
            }
            if (arrivalDeadline != null && !"".equals(arrivalDeadline)) {
                WHERE("DATE(orderDeadlineDate) = DATE(#{arrivalDeadline}) ");
            }
            if (businessDate != null && !"".equals(businessDate)) {
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

            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("warehouseId = #{warehouseId}");
            }
            if (orderNumber != null && !"".equals(orderNumber)) {
                WHERE("purchaseReturnedOrderNo = #{orderNumber} || originalPurchaseOrderNo = #{orderNumber} ");
            }
            if (startDate != null && !"".equals(startDate)) {
                WHERE("createTime >= #{startDate} ");
            }
            if (endDate != null && !"".equals(endDate)) {
                WHERE("createTime <= #{endDate}) ");
            }
        }}.toString();

    }
}
