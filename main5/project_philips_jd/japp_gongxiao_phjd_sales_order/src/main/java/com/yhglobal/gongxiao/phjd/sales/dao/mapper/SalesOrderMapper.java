package com.yhglobal.gongxiao.phjd.sales.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.sales.dao.mapper.provider.SalesOrderSqlProvider;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOrder;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderCountBO;
import com.yhglobal.gongxiao.phjd.sales.model.bo.SalesOrderListBO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description 销售订单表DAO
 * @author weizecheng
 * @date 2018/8/21 9:18
 * @param : prefix  表前缀
 */
public interface SalesOrderMapper  extends BaseMapper  {

    /**
     * By SalesOrderNo
     *
     * @author weizecheng
     * @date 2018/8/20 16:32
     * @param salesOrderNo
     * @param prefix
     * @return
     */
    @Select({
            "select",
            "salesOrderId, salesOrderStatus, dataVersion,salesContactNo,projectId, projectName, warehouseId, warehouse, brandId, brandName, supplierId, supplierName, jdSkuNumber, totalQuantity, totalCostQuantity, totalSalesAmount, totalOrderAmount, cashAmount, shippingExpense, shippingExpensePaidBy, currencyCode, prepaidAmount, yhDiscountAmount, supplierDiscountAmount, deliveredQuantity, inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, shortageReasonQuantity, createdById, createdByName, createTime, purchaseTime, outBoundTime, orderCheckTime, rejectTime, paidTime, informWarehouseTime, signTime, lastUpdateTime, settlementMode, salesOrderNo, comments, marketingChannel, paymentChannel, paymentDays, creditRemark, cashFlowNo, prepaidFlowNo, returnCashFlowNo, returnPrepaidFlowNo, easOrderNo, easOrderId, totalGeneratedPrepaid, syncEas, shouldReceiveGrossProfit, receivedGrossProfit, purchaseNo, salesOrderAttribute, distributorId, distributorName, ongoingOutboundOrderInfo, finishedOutboundOrderInfo, tracelog",
            "from ${prefix}_sales_order",
            "where salesOrderNo = #{salesOrderNo}"
    })
    SalesOrder getByOrderNo(@Param("prefix") String prefix,
                               @Param("salesOrderNo") String salesOrderNo);

    /**
     * 根据Id获取销售订单
     *
     * @author weizecheng
     * @date 2018/8/28 12:30
     * @param prefix 表前缀
     * @param salesOrderId 销售订单Id
     * @return SalesOrder
     */
    @Select({
            "select",
            "salesOrderId, salesOrderStatus, dataVersion,salesContactNo, projectId, projectName, warehouseId, warehouse, brandId, brandName, supplierId, supplierName, jdSkuNumber, totalQuantity, totalCostQuantity, totalSalesAmount, totalOrderAmount, cashAmount, shippingExpense, shippingExpensePaidBy, currencyCode, prepaidAmount, yhDiscountAmount, supplierDiscountAmount, deliveredQuantity, inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, shortageReasonQuantity, createdById, createdByName, createTime, purchaseTime, outBoundTime, orderCheckTime, rejectTime, paidTime, informWarehouseTime, signTime, lastUpdateTime, settlementMode, salesOrderNo, comments, marketingChannel, paymentChannel, paymentDays, creditRemark, cashFlowNo, prepaidFlowNo, returnCashFlowNo, returnPrepaidFlowNo, easOrderNo, easOrderId, totalGeneratedPrepaid, syncEas, shouldReceiveGrossProfit, receivedGrossProfit, purchaseNo, salesOrderAttribute, distributorId, distributorName, ongoingOutboundOrderInfo, finishedOutboundOrderInfo, tracelog",
            "from ${prefix}_sales_order",
            "where salesOrderId = #{salesOrderId}"
    })
    SalesOrder getById(@Param("prefix") String prefix,
                            @Param("salesOrderId") Long salesOrderId);


    /**
     * 根据一定条件选择性查询销售订单
     *
     * @author weizecheng
     * @date 2018/8/20 16:32
     * @param prefix           表前缀
     * @param distributorId    分销商id
     * @param salesOrderNo     销售单号
     * @param productCode      产品编码
     * @param outBoundTime     出库日期
     * @param createTimeBegin  创建时间-起始条件
     * @param createTimeEnd    创建时间-截止条件
     * @param salesOrderStatus 订单状态
     * @param purchaseNo       客户采购单号
     * @return java.util.List<SalesOrder>
     */
    @SelectProvider(type = SalesOrderSqlProvider.class, method = "listOrderPageBySeach")
    List<SalesOrderListBO> listSalesOrderByDistributor(@Param("prefix") String prefix,
                                                       @Param("distributorId") long distributorId,
                                                       @Param("salesOrderNo") String salesOrderNo,
                                                       @Param("productCode") String productCode,
                                                       @Param("outBoundTime") String outBoundTime,
                                                       @Param("createTimeBegin") String createTimeBegin,
                                                       @Param("createTimeEnd") String createTimeEnd,
                                                       @Param("salesOrderStatus") Integer salesOrderStatus,
                                                       @Param("purchaseNo") String purchaseNo);



    /**
     * 根据销售订单状态获取订单数量(未带条件 可以考虑条件 看需求)
     * //TODO 具体数量看是否条件 看后续需求
     *  @SelectProvider(type = SalesOrderSqlProvider.class, method = "countSalesOrder") (条件的加入)
     *
     * @author weizecheng
     * @date 2018/8/22 12:24
     * @param prefix 表前缀
     * @return List<SalesOrderCountBO>
     */
    @Select({
            "SELECT",
            "COUNT(*) as count,salesOrderStatus",
            "from ${prefix}_sales_order",
            "GROUP BY salesOrderStatus"
    })
    List<SalesOrderCountBO> countSalesOrderByOrderStatus(@Param("prefix") String prefix);

    /**
     * 审核更新 同时确定实际数量
     *
     * @auther weizecheng
     * @date 2018/8/24 12:10
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 销售订单Id
     * @param cashAmount 实际审核数量
     * @param totalCostQuantity 实际发货数量
     * @param salesOrderStatus 销售订单状态
     * @param shortageReasonQuantity 未发货数量
     * @param orderCheckTime 审核时间
     * @param tracelog 销售日志
     * @return int
     */
    @Update({
            "update ${prefix}_sales_order",
            "set ",
            "salesOrderStatus = #{salesOrderStatus},",
            "cashAmount= #{cashAmount},",
            "totalCostQuantity = #{totalCostQuantity},",
            "unhandledQuantity= #{totalCostQuantity},",
            "shortageReasonQuantity = #{shortageReasonQuantity},",
            "tracelog = #{tracelog},",
            "orderCheckTime = #{orderCheckTime},",
            "dataVersion = dataVersion+1",
            "where salesOrderId = #{salesOrderId} AND dataVersion = #{dataVersion}",
    })
    int updateReviewSalesOrder(@Param("prefix") String prefix,
                               @Param("dataVersion")Long dataVersion,
                               @Param("salesOrderId")Long id,
                               @Param("cashAmount")BigDecimal cashAmount,
                               @Param("totalCostQuantity")Integer totalCostQuantity,
                               @Param("salesOrderStatus")Integer salesOrderStatus,
                               @Param("shortageReasonQuantity")Integer shortageReasonQuantity,
                               @Param("orderCheckTime")Date orderCheckTime,
                               @Param("tracelog")String tracelog);


    /**
     * 驳回销售订单
     *
     * @auther weizecheng
     * @date 2018/8/28 9:59
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 销售订单Id
     * @param salesOrderStatus 销售订单状态
     * @param rejectedDate 驳回时间
     * @param tracelog 日志
     * @return int 1成功
     */
    @Update({
            "update ${prefix}_sales_order",
            "set ",
            "salesOrderStatus = #{salesOrderStatus},",
            "tracelog = #{tracelog},",
            "rejectTime = #{rejectedDate},",
            "dataVersion = dataVersion+1",
            "where salesOrderId = #{salesOrderId} AND dataVersion = #{dataVersion}",
    })
    int updateRejectedSalesOrder(@Param("prefix") String prefix,
                                 @Param("dataVersion")Long dataVersion,
                                 @Param("salesOrderId")Long id,
                                 @Param("salesOrderStatus")Integer salesOrderStatus,
                                 @Param("rejectedDate")Date rejectedDate,
                                 @Param("tracelog")String tracelog);

    /**
     * 更新销售订单日志
     *
     * @author weizecheng
     * @date 2018/8/28 14:36
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 销售 订单Id
     * @param tracelog 日志
     * @return int
     */
    @Update({
            "update ${prefix}_sales_order",
            "set ",
            "tracelog = #{tracelog},",
            "dataVersion = dataVersion+1",
            "where salesOrderId = #{salesOrderId} AND dataVersion = #{dataVersion}",
    })
    int updateTracelogSalesOrder(@Param("prefix") String prefix,
                                 @Param("dataVersion")Long dataVersion,
                                 @Param("salesOrderId")Long id,
                                 @Param("tracelog")String tracelog);


    /**
     * 更新预约发货信息
     *
     * @author weizecheng
     * @date 2018/8/29 11:04
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 销售订单Id
     * @param tracelog 操作日志
     * @param salesOrderStatus 销售订单状态
     * @param informWarehouseTime 通知仓库时间
     * @param syncEasStatus 同步eas状态
     * @param totalUnhandledQuantity 待预约发货数量
     * @param newOngoingOutboundOrderInfo 正在进行的出库通知单(JSON)
     * @return int
     */
    @Update({
            "update ${prefix}_sales_order",
            "set ",
            "salesOrderStatus = #{salesOrderStatus},",
            "tracelog = #{tracelog},",
            "rejectTime = #{rejectedDate},",
            "unhandledQuantity= #{totalUnhandledQuantity},",
            "ongoingOutboundOrderInfo = #{newOngoingOutboundOrderInfo},",
            "informWarehouseTime = #{informWarehouseTime},",
            "syncEas = #{syncEasStatus},",
            "dataVersion = dataVersion+1",
            "where salesOrderId = #{salesOrderId} AND dataVersion = #{dataVersion}",
    })
    int updateScheduleDelivery(@Param("prefix") String prefix,
                               @Param("dataVersion")Long dataVersion,
                               @Param("salesOrderId")Long id,
                               @Param("tracelog")String tracelog,
                               @Param("salesOrderStatus")Integer salesOrderStatus,
                               @Param("informWarehouseTime")Date informWarehouseTime,
                               @Param("syncEasStatus")Integer syncEasStatus,
                               @Param("totalUnhandledQuantity")Integer totalUnhandledQuantity,
                               @Param("newOngoingOutboundOrderInfo")String newOngoingOutboundOrderInfo);


    /**
     * 插入销售订单
     *  @Options(useGeneratedKeys=true) 返回插入主键！！！！！！
     * @author weizecheng
     * @date 2018/8/29 15:41
     * @param prefix 表前缀
     * @param order 销售订单
     * @return int
     */
    @Insert({
            "insert into ${prefix}_sales_order (salesOrderStatus, ",
            "dataVersion, projectId, ",
            "projectName, warehouseId, ",
            "warehouse, brandId, ",
            "brandName, supplierId, ",
            "supplierName, jdSkuNumber, ",
            "totalQuantity, totalCostQuantity, ",
            "totalSalesAmount, totalOrderAmount, ",
            "cashAmount, shippingExpense, ",
            "shippingExpensePaidBy, currencyCode, ",
            "prepaidAmount, yhDiscountAmount, ",
            "supplierDiscountAmount, deliveredQuantity, ",
            "inTransitQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ",
            "shortageReasonQuantity, createdById, ",
            "createdByName, createTime, ",
            "purchaseTime, outBoundTime, ",
            "orderCheckTime, rejectTime, ",
            "paidTime, informWarehouseTime, ",
            "signTime, lastUpdateTime, ",
            "settlementMode, salesOrderNo, ",
            "comments, marketingChannel, ",
            "paymentChannel, paymentDays, ",
            "creditRemark, cashFlowNo, ",
            "prepaidFlowNo, returnCashFlowNo, ",
            "returnPrepaidFlowNo, easOrderNo, ",
            "easOrderId, totalGeneratedPrepaid, ",
            "syncEas, shouldReceiveGrossProfit, ",
            "receivedGrossProfit, purchaseNo, ",
            "salesOrderAttribute, distributorId, ",
            "distributorName, requiredArrivalTime, ",
            "salesContactNo, ongoingOutboundOrderInfo, ",
            "finishedOutboundOrderInfo, tracelog)",
            "values (#{salesOrderStatus,jdbcType=TINYINT}, ",
            "#{dataVersion,jdbcType=BIGINT}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{warehouseId,jdbcType=BIGINT}, ",
            "#{warehouse,jdbcType=VARCHAR}, #{brandId,jdbcType=BIGINT}, ",
            "#{brandName,jdbcType=VARCHAR}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{jdSkuNumber,jdbcType=INTEGER}, ",
            "#{totalQuantity,jdbcType=INTEGER}, #{totalCostQuantity,jdbcType=INTEGER}, ",
            "#{totalSalesAmount,jdbcType=DECIMAL}, #{totalOrderAmount,jdbcType=DECIMAL}, ",
            "#{cashAmount,jdbcType=DECIMAL}, #{shippingExpense,jdbcType=DECIMAL}, ",
            "#{shippingExpensePaidBy,jdbcType=TINYINT}, #{currencyCode,jdbcType=VARCHAR}, ",
            "#{prepaidAmount,jdbcType=DECIMAL}, #{yhDiscountAmount,jdbcType=DECIMAL}, ",
            "#{supplierDiscountAmount,jdbcType=DECIMAL}, #{deliveredQuantity,jdbcType=INTEGER}, ",
            "#{inTransitQuantity,jdbcType=INTEGER}, #{canceledQuantity,jdbcType=INTEGER}, ",
            "#{returnedQuantity,jdbcType=INTEGER}, #{unhandledQuantity,jdbcType=INTEGER}, ",
            "#{shortageReasonQuantity,jdbcType=INTEGER}, #{createdById,jdbcType=BIGINT}, ",
            "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{purchaseTime,jdbcType=TIMESTAMP}, #{outBoundTime,jdbcType=TIMESTAMP}, ",
            "#{orderCheckTime,jdbcType=TIMESTAMP}, #{rejectTime,jdbcType=TIMESTAMP}, ",
            "#{paidTime,jdbcType=TIMESTAMP}, #{informWarehouseTime,jdbcType=TIMESTAMP}, ",
            "#{signTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{settlementMode,jdbcType=INTEGER}, #{salesOrderNo,jdbcType=VARCHAR}, ",
            "#{comments,jdbcType=VARCHAR}, #{marketingChannel,jdbcType=TINYINT}, ",
            "#{paymentChannel,jdbcType=TINYINT}, #{paymentDays,jdbcType=INTEGER}, ",
            "#{creditRemark,jdbcType=VARCHAR}, #{cashFlowNo,jdbcType=VARCHAR}, ",
            "#{prepaidFlowNo,jdbcType=VARCHAR}, #{returnCashFlowNo,jdbcType=VARCHAR}, ",
            "#{returnPrepaidFlowNo,jdbcType=VARCHAR}, #{easOrderNo,jdbcType=VARCHAR}, ",
            "#{easOrderId,jdbcType=VARCHAR}, #{totalGeneratedPrepaid,jdbcType=DECIMAL}, ",
            "#{syncEas,jdbcType=INTEGER}, #{shouldReceiveGrossProfit,jdbcType=DECIMAL}, ",
            "#{receivedGrossProfit,jdbcType=DECIMAL}, #{purchaseNo,jdbcType=VARCHAR}, ",
            "#{salesOrderAttribute,jdbcType=VARCHAR}, #{distributorId,jdbcType=BIGINT}, ",
            "#{distributorName,jdbcType=VARCHAR}, #{requiredArrivalTime,jdbcType=TIMESTAMP}, ",
            "#{salesContactNo,jdbcType=VARCHAR}, #{ongoingOutboundOrderInfo,jdbcType=LONGVARCHAR}, ",
            "#{finishedOutboundOrderInfo,jdbcType=LONGVARCHAR}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    @Options(useGeneratedKeys=true)
    int insertSalesOrder(@Param("prefix")String prefix,@Param("order")SalesOrder order);

    /**
     * 更新订单出库时间
     *
     * @author weizecheng
     * @date 2018/8/31 19:24
     * @param prefix 表主键
     * @param salesOrderId 销售订单Id
     * @param dataVersion 版本号
     * @param salesOrderStatus 订单状态
     * @param outboundTime 出库时间
     * @param onGoingOutboundOrderInfo JSON
     * @param finishedOutboundOrderInfo JSON
     * @param inTransitQuantity  在途数量
     * @param tracelog 操作日志
     * @param totalGeneratedPrepaid 产生代垫
     * @param shouldReceiveGrossProfit 产生应收毛利
     * @param receivedGrossProfit 产生实际毛利
     * @return int
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "outboundTime = #{outboundTime},",
            "onGoingOutboundOrderInfo = #{onGoingOutboundOrderInfo},",
            "finishedOutboundOrderInfo = #{finishedOutboundOrderInfo},",
            "totalGeneratedPrepaid = #{totalGeneratedPrepaid},",
            "shouldReceiveGrossProfit = #{shouldReceiveGrossProfit},",
            "receivedGrossProfit = #{receivedGrossProfit},",
            "inTransitQuantity = #{inTransitQuantity},",
            "tracelog = #{tracelog},",
            "dataVersion = dataVersion + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateWhenOutbound(@Param("prefix") String prefix,
                           @Param("salesOrderId") Long salesOrderId,
                           @Param("dataVersion") Long dataVersion,
                           @Param("salesOrderStatus") Integer salesOrderStatus,
                           @Param("outboundTime") Date outboundTime,
                           @Param("onGoingOutboundOrderInfo") String onGoingOutboundOrderInfo,
                           @Param("finishedOutboundOrderInfo") String finishedOutboundOrderInfo,
                           @Param("inTransitQuantity") Integer inTransitQuantity,
                           @Param("tracelog") String tracelog,
                           @Param("totalGeneratedPrepaid") BigDecimal totalGeneratedPrepaid,
                           @Param("shouldReceiveGrossProfit") BigDecimal shouldReceiveGrossProfit,
                           @Param("receivedGrossProfit") BigDecimal receivedGrossProfit);


    /**
     * 更新订单出库时间
     *
     * @author weizecheng
     * @date 2018/8/31 19:24
     * @param prefix 表主键
     * @param salesOrderId 销售订单Id
     * @param dataVersion 版本号
     * @param salesOrderStatus 订单状态
     * @param outboundTime 出库时间
     * @param inTransitQuantity  在途数量
     * @param deliveredQuantity 到达数量
     * @param tracelog 操作日志
     * @return int
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "outboundTime = #{outboundTime},",
            "deliveredQuantity = #{deliveredQuantity},",
            "inTransitQuantity = #{inTransitQuantity},",
            "tracelog = #{tracelog},",
            "dataVersion = dataVersion + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateWhenOutboundSign(@Param("prefix") String prefix,
                           @Param("salesOrderId") Long salesOrderId,
                           @Param("dataVersion") Long dataVersion,
                           @Param("salesOrderStatus") Integer salesOrderStatus,
                           @Param("outboundTime") Date outboundTime,
                           @Param("deliveredQuantity") Integer deliveredQuantity,
                           @Param("inTransitQuantity") Integer inTransitQuantity,
                           @Param("tracelog") String tracelog);


    /**
     * 获取同步EAS 某个状态下的所有订单
     *
     * @author weizecheng
     * @date 2018/9/3 17:18
     * @param prefix 表前缀
     * @param syncEas  同步Eas状态
     * @return
     */
    @Select({
            "select",
            "salesOrderId, salesOrderStatus, dataVersion,salesContactNo, projectId, projectName, warehouseId, warehouse, brandId, brandName, supplierId, supplierName, jdSkuNumber, totalQuantity, totalCostQuantity, totalSalesAmount, totalOrderAmount, cashAmount, shippingExpense, shippingExpensePaidBy, currencyCode, prepaidAmount, yhDiscountAmount, supplierDiscountAmount, deliveredQuantity, inTransitQuantity, canceledQuantity, returnedQuantity, unhandledQuantity, shortageReasonQuantity, createdById, createdByName, createTime, purchaseTime, outBoundTime, orderCheckTime, rejectTime, paidTime, informWarehouseTime, signTime, lastUpdateTime, settlementMode, salesOrderNo, comments, marketingChannel, paymentChannel, paymentDays, creditRemark, cashFlowNo, prepaidFlowNo, returnCashFlowNo, returnPrepaidFlowNo, easOrderNo, easOrderId, totalGeneratedPrepaid, syncEas, shouldReceiveGrossProfit, receivedGrossProfit, purchaseNo, salesOrderAttribute, distributorId, distributorName, ongoingOutboundOrderInfo, finishedOutboundOrderInfo, tracelog",
            "from ${prefix}_sales_order",
            "where syncEas = #{syncEas}"
    })
    List<SalesOrder> listBySyncStatus(@Param("prefix") String prefix,
                                      @Param("syncEas") Integer syncEas);

    /**
     * 更新EAS状态
     * 
     * @author weizecheng
     * @date 2018/9/3 17:35
     * @param prefix 表前缀
     * @param salesOrderId 销售订单Id
     * @param dataVersion 版本号
     * @param syncEas eas状态
     * @return
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "syncEas = #{syncEas},",
            "dataVersion = dataVersion + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateSyncEasStatus(@Param("prefix") String prefix,
                            @Param("salesOrderId") Long salesOrderId,
                            @Param("dataVersion") Long dataVersion,
                            @Param("syncEas")Integer syncEas);


    /**
     *  更新销售订单的EAS信息
     *
     * @author weizecheng
     * @date 2018/9/3 17:53
     * @param prefix 表前缀
     * @param salesOrderId 销售订单Id
     * @param dataVersion 版本号
     * @param syncEas EAS状态
     * @param easOrderNo EAS订单号
     * @param easOrderId EAS订单Id
     * @return INT
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "syncEas = #{syncEas},",
            "easOrderNo = #{easOrderNo},",
            "easOrderId = #{easOrderId},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateEasInfo(@Param("prefix") String prefix,
                      @Param("salesOrderId") Long salesOrderId,
                      @Param("dataVersion") Long dataVersion,
                      @Param("syncEas") int syncEas,
                      @Param("easOrderNo") String easOrderNo,
                      @Param("easOrderId") String easOrderId);

}
