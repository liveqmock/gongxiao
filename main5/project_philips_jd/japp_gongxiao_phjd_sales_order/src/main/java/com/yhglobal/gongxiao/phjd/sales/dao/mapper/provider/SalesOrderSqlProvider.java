package com.yhglobal.gongxiao.phjd.sales.dao.mapper.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


/**
 * 销售订单Sql设计
 * 
 * @author weizecheng
 * @date 2018/8/22 10:01
 */
public class SalesOrderSqlProvider {

    /**
     * 根据下列条件获取销售订单列表
     *
     * @auther weizecheng
     * @date 2018/8/27 11:22
     * @param prefix 表前缀
     * @param distributorId 供应商Id
     * @param salesOrderNo 销售订单号
     * @param productCode 商品编码
     * @param outBoundTime 出库时间
     * @param createTimeBegin 创建开始时间
     * @param createTimeEnd 创建结束时间
     * @param salesOrderStatus 订单状态
     * @param purchaseNo 采购号
     * @return String SQL
     */
    public String listOrderPageBySeach(@Param("prefix") String prefix,
                                                     @Param("distributorId") long distributorId,
                                                     @Param("salesOrderNo") String salesOrderNo,
                                                     @Param("productCode") String productCode,
                                                     @Param("outBoundTime") String outBoundTime,
                                                     @Param("createTimeBegin") String createTimeBegin,
                                                     @Param("createTimeEnd") String createTimeEnd,
                                                     @Param("salesOrderStatus") Integer salesOrderStatus,
                                                     @Param("purchaseNo") String purchaseNo) {
        return new SQL() {{
            SELECT(
                    "salesOrderStatus,salesOrderNo,settlementMode,purchaseNo,totalOrderAmount,cashAmount,createTime,paidTime,totalQuantity,unhandledQuantity,paymentDays,salesOrderAttribute,distributorName");
            FROM(prefix + "_sales_order");
//            WHERE("distributorId = #{distributorId}");

//            if (StringUtils.isNotBlank(salesOrderNo)) {
//                WHERE("salesOrderNo like #{salesOrderNo} '%'");
//            }
//            if (StringUtils.isNotBlank(productCode)) {
//                WHERE("itemsCode like  #{productCode} '%' ");
//            }
            if (StringUtils.isNotBlank(outBoundTime)) {
                WHERE("Date(outBoundTime) = Date(#{outBoundTime})");
            }
            if (StringUtils.isNotBlank(createTimeBegin)) {
                WHERE("Date(createTime) >= #{createTimeBegin}");
            }
            if (StringUtils.isNotBlank(createTimeEnd)) {
                WHERE("Date(createTime) <= #{createTimeEnd}");
            }
            if (salesOrderStatus != 0) {
                WHERE("salesOrderStatus = #{salesOrderStatus}");
            }
            ORDER_BY("salesOrderId DESC");
        }}.toString();
    }

//    public String countSalesOrder(@Param("prefix") String prefix,
//                                  @Param("projectId") long projectId,
//                                  @Param("salesOrderNo") String salesOrderNo,
//                                  @Param("outBoundTime") String outBoundTime,
//                                  @Param("createTime") String createTime) {

    /**
     * 订单各状态 ---数量(未使用 根据后续需求使用)
     *
     * @author weizecheng
     * @date 2018/8/22 11:59
     * @param prefix
     * @return String SQL
     */
    public String countSalesOrder(@Param("prefix") String prefix) {
        return new SQL() {{
            SELECT("SELECT COUNT(*) as count,salesOrderStatus");
            FROM(prefix + "_sales_order");
//            if (!StringUtils.isEmpty(salesOrderNo)) {
//                WHERE("salesOrderNo like  #{salesOrderNo} '%'");
//            }
//            if (!StringUtils.isEmpty(outBoundTime)) {
//                WHERE("DATE(outBoundTime) = DATE(#{outBoundTime})");
//            }
//            if (!StringUtils.isEmpty(createTime)) {
//                WHERE("Date(createTime) = DATE(#{createTime})");
//            }
            GROUP_BY("salesOrderStatus");
        }}.toString();
    }
}
