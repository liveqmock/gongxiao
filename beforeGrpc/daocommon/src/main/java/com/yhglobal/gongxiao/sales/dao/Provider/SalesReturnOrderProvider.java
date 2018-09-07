package com.yhglobal.gongxiao.sales.dao.Provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


/**
 * @Description: 销售退货provider
 * @Author: LUOYI
 * @Date: Created in 10:11 2018/3/21
 */
public class SalesReturnOrderProvider {

    public String getList(@Param("projectId") Integer projectId,
                          @Param("returnedType") Integer returnedType,
                          @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                          @Param("warehouseId") String warehouseId,
                          @Param("timeStart") String timeStart,
                          @Param("timeEnd") String timeEnd,
                          @Param("returnedOrderStatus") Integer returnedOrderStatus) {
        return new SQL() {{
            SELECT("salesReturnedOrderId, returnedOrderStatus, returnedType, salesReturnedOrderNo, originalGongxiaoOutboundOrderNo  ",
                    "senderName,distributorName, senderMobile, originalOutboundWarehouseName, targetWarehouseName,dataVersion",
                    "lastUpdateTime, createTime ");
            FROM("sales_returned_order");
            if (projectId != null && !"".equals(projectId)) {
                    WHERE("projectId = #{projectId}");
            }
            if (returnedType != null && !"".equals(returnedType)) {
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
            if (returnedOrderStatus != null && !"".equals(returnedOrderStatus)) {
                WHERE("returnedOrderStatus = #{returnedOrderStatus}");
            }
        }}.toString();
    }
    public String getCount(@Param("projectId") Integer projectId,
                          @Param("returnedType") Integer returnedType,
                          @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                          @Param("warehouseId") String warehouseId,
                          @Param("timeStart") String timeStart,
                          @Param("timeEnd") String timeEnd) {
        return new SQL() {{
            SELECT("count(1) ");
            FROM("sales_returned_order");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (returnedType != null && !"".equals(returnedType)) {
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
    public String getClassificationCount(@Param("projectId") Integer projectId,
                           @Param("returnedType") Integer returnedType,
                           @Param("salesReturnedOrderNo") String salesReturnedOrderNo,
                           @Param("warehouseId") String warehouseId,
                           @Param("timeStart") String timeStart,
                           @Param("timeEnd") String timeEnd) {
        return new SQL() {{
            SELECT("returnedOrderStatus as status,count(1) as count");
            FROM("sales_returned_order");
            if (projectId != null && !"".equals(projectId)) {
                WHERE("projectId = #{projectId}");
            }
            if (returnedType != null && !"".equals(returnedType)) {
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
