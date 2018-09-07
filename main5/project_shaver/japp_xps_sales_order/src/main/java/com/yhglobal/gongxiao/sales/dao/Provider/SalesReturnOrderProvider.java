package com.yhglobal.gongxiao.sales.dao.Provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


/**
 * @Description: 销售退货provider
 * @Author: LUOYI
 * @Date: Created in 10:11 2018/3/21
 */
public class SalesReturnOrderProvider {

    public String getList(@Param("prefix") String prefix,
                          @Param("projectId") Long projectId,
                          @Param("returnedType") Integer returnedType,
                          @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                          @Param("warehouseId") String warehouseId,
                          @Param("timeStart") String timeStart,
                          @Param("timeEnd") String timeEnd,
                          @Param("returnedOrderStatus") Integer returnedOrderStatus) {
        return new SQL() {{
            SELECT("salesReturnedOrderId, returnedOrderStatus, returnedType, originalSalesOrderNo,salesReturnedOrderNo, originalGongxiaoOutboundOrderNo  ",
                    "senderName, senderMobile, originalOutboundWarehouseName, targetWarehouseName,dataVersion",
                    "lastUpdateTime, createTime ");
            FROM(prefix + "_sales_returned_order");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (returnedType != null && !"".equals(returnedType) && 0 != returnedType) {
                WHERE("returnedType = #{returnedType}");
            }
            if (salesReturnedOrderNo != null && !"".equals(salesReturnedOrderNo)) {
                WHERE("salesReturnedOrderNo like '%' #{salesReturnedOrderNo} '%'");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("targetWarehouseId = #{warehouseId} ");
            }
            if (timeStart != null && !"".equals(timeStart)) {
                WHERE("date(createTime) >= date(#{timeStart})");
            }
            if (timeEnd != null && !"".equals(timeEnd)) {
                WHERE("date(createTime) <= date(#{timeEnd})");
            }
            if (0 != returnedOrderStatus) {
                WHERE("returnedOrderStatus = #{returnedOrderStatus}");
            }
        }}.toString();
    }

    public String getCount(@Param("prefix") String prefix,
                           @Param("projectId") Long projectId,
                           @Param("returnedType") Integer returnedType,
                           @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                           @Param("warehouseId") String warehouseId,
                           @Param("timeStart") String timeStart,
                           @Param("timeEnd") String timeEnd) {
        return new SQL() {{
            SELECT("count(1) ");
            FROM(prefix + "_sales_returned_order");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (returnedType != null && !"".equals(returnedType) && 0 != returnedType) {
                WHERE("returnedType = #{returnedType}");
            }
            if (salesReturnedOrderNo != null && !"".equals(salesReturnedOrderNo)) {
                WHERE("salesReturnedOrderNo like '%' #{salesReturnedOrderNo} '%'");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("targetWarehouseId = #{warehouseId} ");
            }
            if (timeStart != null && !"".equals(timeStart)) {
                WHERE("date(createTime) >= date(#{timeStart})");
            }
            if (timeEnd != null && !"".equals(timeEnd)) {
                WHERE("date(createTime) <= date(#{timeEnd})");
            }
        }}.toString();
    }

    public String getClassificationCount(@Param("prefix") String prefix,
                                         @Param("projectId") Long projectId,
                                         @Param("returnedType") Integer returnedType,
                                         @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                                         @Param("warehouseId") String warehouseId,
                                         @Param("timeStart") String timeStart,
                                         @Param("timeEnd") String timeEnd) {
        return new SQL() {{
            SELECT("returnedOrderStatus as status,count(1) as count");
            FROM(prefix + "_sales_returned_order");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (returnedType != null && !"".equals(returnedType) && 0 != returnedType) {
                WHERE("returnedType = #{returnedType}");
            }
            if (salesReturnedOrderNo != null && !"".equals(salesReturnedOrderNo)) {
                WHERE("salesReturnedOrderNo like '%' #{salesReturnedOrderNo} '%'");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("targetWarehouseId = #{warehouseId} ");
            }
            if (timeStart != null && !"".equals(timeStart)) {
                WHERE("date(createTime) >= date(#{timeStart})");
            }
            if (timeEnd != null && !"".equals(timeEnd)) {
                WHERE("date(createTime) <= date(#{timeEnd})");
            }
            GROUP_BY("returnedOrderStatus");
        }}.toString();
    }

}
