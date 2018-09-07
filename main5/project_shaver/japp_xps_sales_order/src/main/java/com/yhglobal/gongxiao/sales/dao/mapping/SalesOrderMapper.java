package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.dto.SalesOrderCount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SalesOrderMapper extends BaseMapper {


    @Insert({
            "insert into ${prefix}_sales_order (salesOrderId, salesOrderStatus, settlementMode, ",
            "salesOrderNo, salesContactNo, ",
            "marketingChannel, paymentChannel, ",
            "projectId, projectName, ",
            "brandId, brandName, ",
            "supplierId, supplierName, ",
            "distributorId, distributorName, ",
            "shippingMode, ",
            "provinceId, provinceName, ",
            "cityId, cityName, ",
            "districtId, districtName, ",
            "shippingAddress, recipientName, ",
            "recipientMobile, warehouseId, ",
            "warehouse, recipientCompany, ",
            "shippingExpense, shippingExpensePaidBy, ",
            "currencyId, currencyCode, ",
            "totalOrderAmount, discountAmount, ",
            "couponAmount, prepaidAmount, ",
            "prestoredAmount, cashAmount, ",
            "totalQuantity, yhDiscountAmount, ",
            "supplierDiscountAmount, deliveredQuantity, ",
            "inTransitQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ",
            "ongoingOutboundOrderInfo, finishedOutboundOrderInfo, ",
            "dataVersion, createdById, ",
            "createdByName, createTime, ",
            "outBoundTime, orderCheckTime, ",
            "rejectTime, paidTime, ",
            "informWarehouseTime, signTime, ",
            "lastUpdateTime, tracelog, paymentDays,",
            "cashFlowNo, sellHighAmount, totalGeneratedPrepaid, syncEas,",
            "shouldReceiveGrossProfit, receivedGrossProfit)",
            "values (#{record.salesOrderId}, #{record.salesOrderStatus},#{record.settlementMode}, ",
            "#{record.salesOrderNo}, #{record.salesContactNo}, ",
            "#{record.marketingChannel}, #{record.paymentChannel}, ",
            "#{record.projectId}, #{record.projectName}, ",
            "#{record.brandId}, #{record.brandName}, ",
            "#{record.supplierId}, #{record.supplierName}, ",
            "#{record.distributorId}, #{record.distributorName}, ",
            "#{record.shippingMode},  ",
            "#{record.provinceId}, #{record.provinceName}, ",
            "#{record.cityId}, #{record.cityName}, ",
            "#{record.districtId}, #{record.districtName}, ",
            "#{record.shippingAddress}, #{record.recipientName}, ",
            "#{record.recipientMobile}, #{record.warehouseId}, ",
            "#{record.warehouse}, #{record.recipientCompany}, ",
            "#{record.shippingExpense}, #{record.shippingExpensePaidBy}, ",
            "#{record.currencyId}, #{record.currencyCode}, ",
            "#{record.totalOrderAmount}, #{record.discountAmount}, ",
            "#{record.couponAmount}, #{record.prepaidAmount}, ",
            "#{record.prestoredAmount}, #{record.cashAmount}, ",
            "#{record.totalQuantity}, #{record.yhDiscountAmount}, ",
            "#{record.supplierDiscountAmount}, #{record.deliveredQuantity}, ",
            "#{record.inTransitQuantity}, #{record.canceledQuantity}, ",
            "#{record.returnedQuantity}, #{record.unhandledQuantity}, ",
            "#{record.ongoingOutboundOrderInfo}, #{record.finishedOutboundOrderInfo}, ",
            "#{record.dataVersion}, #{record.createdById}, ",
            "#{record.createdByName}, #{record.createTime}, ",
            "#{record.outBoundTime}, #{record.orderCheckTime}, ",
            "#{record.rejectTime}, #{record.paidTime}, ",
            "#{record.informWarehouseTime}, #{record.signTime}, ",
            "#{record.lastUpdateTime}, #{record.tracelog},#{record.paymentDays},",
            "#{record.cashFlowNo}, #{record.sellHighAmount}, #{record.totalGeneratedPrepaid}, #{record.syncEas},",
            "#{record.shouldReceiveGrossProfit}, #{record.receivedGrossProfit})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SalesOrder record);


    @Select({
            "select",
            "salesOrderId, salesOrderStatus, salesOrderNo, salesContactNo, marketingChannel,settlementMode, creditRemark,",
            "paymentChannel, projectId, projectName, brandId, brandName, supplierId, supplierName, ",
            "distributorId, distributorName, shippingMode,  provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, shippingAddress, recipientName, ",
            "recipientMobile, warehouseId, warehouse, recipientCompany, shippingExpense, ",
            "shippingExpensePaidBy, currencyId, currencyCode, totalOrderAmount, discountAmount, ",
            "couponAmount, prepaidAmount, prestoredAmount, cashAmount, totalQuantity, yhDiscountAmount, ",
            "supplierDiscountAmount, deliveredQuantity, inTransitQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, finishedOutboundOrderInfo, ",
            "dataVersion, createdById, createdByName, createTime, outBoundTime, orderCheckTime, ",
            "rejectTime, paidTime, informWarehouseTime, signTime, lastUpdateTime, ",
            "tracelog,paymentDays,easOrderNo,easOrderId, sellHighAmount, totalGeneratedPrepaid, syncEas,",
            "shouldReceiveGrossProfit, receivedGrossProfit",
            "from ${prefix}_sales_order",
            "where salesOrderNo = #{salesOrderNo}"
    })
    SalesOrder selectByOrderNo(@Param("prefix") String prefix, @Param("salesOrderNo") String salesOrderNo);


    /**
     * 根据条件选择性查询
     *
     * @param projectId        项目id
     * @param distributorId    分销商id
     * @param salesOrderNo     销售单号
     * @param productCode      产品编码
     * @param outBoundTime     出库日期
     * @param createTimeBegin  创建时间-起始条件
     * @param createTimeEnd    创建时间-截止条件
     * @param salesOrderStatus 订单状态
     * @return java.util.List<SalesOrder>
     */
    @SelectProvider(type = SalesOrderSqlProvider.class, method = "selectListSelectivelyByDistributor")
    List<SalesOrder> selectListSelectivelyByDistributor(@Param("prefix") String prefix,
                                                        @Param("projectId") long projectId,
                                                        @Param("distributorId") long distributorId,
                                                        @Param("salesOrderNo") String salesOrderNo,
                                                        @Param("productCode") String productCode,
                                                        @Param("outBoundTime") String outBoundTime,
                                                        @Param("createTimeBegin") String createTimeBegin,
                                                        @Param("createTimeEnd") String createTimeEnd,
                                                        @Param("salesOrderStatus") Integer salesOrderStatus);

    @SelectProvider(type = SalesOrderSqlProvider.class, method = "getListSelectively")
    List<SalesOrder> getListSelectively(@Param("prefix") String prefix,
                                        @Param("projectId") long projectId,
                                        @Param("salesOrderNo") String salesOrderNo,
                                        @Param("outBoundTime") String outBoundTime,
                                        @Param("createTime") String createTime,
                                        @Param("salesOrderStatus") Integer salesOrderStatus);


    /**
     * 查询每个状态对应的数量 map
     *
     * @param projectId    项目
     * @param salesOrderNo 销售单号
     * @param outBoundTime 出库时间
     * @param createTime   下单时间
     * @return
     */
    @SelectProvider(type = SalesOrderSqlProvider.class, method = "getCountMap")
    @MapKey("salesOrderStatus")
    List<SalesOrderCount> getCountMap(@Param("prefix") String prefix,
                                      @Param("projectId") long projectId,
                                      @Param("salesOrderNo") String salesOrderNo,
                                      @Param("outBoundTime") String outBoundTime,
                                      @Param("createTime") String createTime);


    @Select({
            "select",
            "salesOrderId, salesOrderStatus, salesOrderNo, salesContactNo, marketingChannel,settlementMode, ",
            "paymentChannel, projectId, projectName, brandId, brandName, supplierId, supplierName, ",
            "distributorId, distributorName, shippingMode,  provinceId, provinceName, ",
            "cityId, cityName, districtId, districtName, shippingAddress, recipientName, ",
            "recipientMobile, warehouseId, warehouse, recipientCompany, shippingExpense, ",
            "shippingExpensePaidBy, currencyId, currencyCode, totalOrderAmount, discountAmount, ",
            "couponAmount, prepaidAmount, prestoredAmount, cashAmount, totalQuantity, yhDiscountAmount, ",
            "supplierDiscountAmount, deliveredQuantity, inTransitQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ongoingOutboundOrderInfo, finishedOutboundOrderInfo, ",
            "dataVersion, createdById, createdByName, createTime, outBoundTime, orderCheckTime, ",
            "rejectTime, paidTime, informWarehouseTime, signTime, lastUpdateTime, ",
            "tracelog,paymentDays,easOrderNo,easOrderId, sellHighAmount, totalGeneratedPrepaid, syncEas",
            "from ${prefix}_sales_order",
            "where syncEas = #{syncEas}"
    })
    List<SalesOrder> selectToSyncEasOrder(@Param("prefix") String prefix,
                                          @Param("syncEas") int syncEas);


    /**
     * 修改账期结算模式
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param salesOrderStatus 销售单状态
     * @param settlementMode   结算模式
     * @param paymentDays      账期天数
     * @param creditRemark     备注
     * @param tracelog         操作日志
     * @return 更新成功条数
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "settlementMode = #{settlementMode},",
            "paymentDays = #{paymentDays},",
            "creditRemark = #{creditRemark},",
            "tracelog = #{tracelog},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateSettlementMode(@Param("prefix") String prefix,
                             @Param("salesOrderId") long salesOrderId,
                             @Param("dataVersion") long dataVersion,
                             @Param("salesOrderStatus") int salesOrderStatus,
                             @Param("settlementMode") int settlementMode,
                             @Param("paymentDays") int paymentDays,
                             @Param("creditRemark") String creditRemark,
                             @Param("tracelog") String tracelog);

    /**
     * 修改收件信息
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param recipientName    收件人
     * @param recipientMobile  收件人手机
     * @param provinceId       省id
     * @param provinceName     省
     * @param cityId           市id
     * @param cityName         市
     * @param districtId       区id
     * @param districtName     区
     * @param shippingAddress  详细地址
     * @param recipientCompany 收件人公司
     * @param tracelog         操作日志
     * @return 更新成功条数
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "recipientName = #{recipientName},",
            "recipientMobile = #{recipientMobile},",
            "provinceId = #{provinceId},",
            "provinceName = #{provinceName},",
            "cityId = #{cityId},",
            "cityName = #{cityName},",
            "districtId = #{districtId},",
            "districtName = #{districtName},",
            "shippingAddress = #{shippingAddress},",
            "recipientCompany = #{recipientCompany},",
            "tracelog = #{tracelog},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateRecipientInfo(@Param("prefix") String prefix,
                            @Param("salesOrderId") Long salesOrderId,
                            @Param("dataVersion") Long dataVersion,
                            @Param("recipientName") String recipientName,
                            @Param("recipientMobile") String recipientMobile,
                            @Param("provinceId") String provinceId,
                            @Param("provinceName") String provinceName,
                            @Param("cityId") String cityId,
                            @Param("cityName") String cityName,
                            @Param("districtId") String districtId,
                            @Param("districtName") String districtName,
                            @Param("shippingAddress") String shippingAddress,
                            @Param("recipientCompany") String recipientCompany,
                            @Param("tracelog") String tracelog);


    /**
     * 更新审批信息
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param salesOrderStatus 销售单状态
     * @param couponAmount     返利金额
     * @param prepaidAmount    代垫金额
     * @param cashAmount       现金金额
     * @param orderCheckTime   审批时间
     * @param tracelog         操作日志
     * @param salesContactNo  销售合同号
     * @return 更新条数
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "couponAmount = #{couponAmount},",
            "prepaidAmount = #{prepaidAmount},",
            "cashAmount = #{cashAmount},",
            "orderCheckTime = #{orderCheckTime},",
            "tracelog = #{tracelog},",
            "salesContactNo = #{salesContactNo},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateApprovalInfo(@Param("prefix") String prefix,
                           @Param("salesOrderId") Long salesOrderId,
                           @Param("dataVersion") Long dataVersion,
                           @Param("salesOrderStatus") int salesOrderStatus,
                           @Param("couponAmount") long couponAmount,
                           @Param("prepaidAmount") long prepaidAmount,
                           @Param("cashAmount") long cashAmount,
                           @Param("orderCheckTime") Date orderCheckTime,
                           @Param("tracelog") String tracelog,
                           @Param("salesContactNo") String salesContactNo);

    /**
     * 更新返利、代垫流水号
     *
     * @param salesOrderId  主键id
     * @param dataVersion   数据版本
     * @param couponFlowNo  返利流水号
     * @param prepaidFlowNo 代垫流水号
     * @return 更新条数
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "couponFlowNo = #{couponFlowNo},",
            "prepaidFlowNo = #{prepaidFlowNo},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateCouponPrepaidFlowNo(@Param("prefix") String prefix,
                                  @Param("salesOrderId") Long salesOrderId,
                                  @Param("dataVersion") Long dataVersion,
                                  @Param("couponFlowNo") String couponFlowNo,
                                  @Param("prepaidFlowNo") String prepaidFlowNo);


    /**
     * 更细销售单状态
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param salesOrderStatus 订单状态
     * @param tracelog         操作日志
     * @return 更新成功条数
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "tracelog = #{tracelog},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateSalesOrderStatus(@Param("prefix") String prefix,
                               @Param("salesOrderId") Long salesOrderId,
                               @Param("dataVersion") Long dataVersion,
                               @Param("salesOrderStatus") int salesOrderStatus,
                               @Param("tracelog") String tracelog);

    /**
     * 更细销售单流水号
     *
     * @param salesOrderId     主键id
     * @param dataVersion      数据版本
     * @param returnCashFlowNo 取消订单对应的现金流水号
     * @return 更新成功条数
     */
    @Update({
            "update ${prefix}_sales_order",
            "set",
            "returnCashFlowNo = #{returnCashFlowNo},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateReturnCashFlowNo(@Param("prefix") String prefix,
                               @Param("salesOrderId") Long salesOrderId,
                               @Param("dataVersion") Long dataVersion,
                               @Param("returnCashFlowNo") String returnCashFlowNo);

    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "tracelog = #{tracelog},",
            "rejectTime = #{rejectTime},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateSalesOrderStatusAndRejectTime(@Param("prefix") String prefix,
                                            @Param("salesOrderId") Long salesOrderId,
                                            @Param("dataVersion") Long dataVersion,
                                            @Param("salesOrderStatus") int salesOrderStatus,
                                            @Param("tracelog") String tracelog,
                                            @Param("rejectTime") Date rejectTime);

    @Update({
            "update ${prefix}_sales_order",
            "set",
            "returnCashFlowNo = #{returnCashFlowNo},",
            "returnCouponFlowNo = #{returnCouponFlowNo},",
            "returnPrepaidFlowNo = #{returnPrepaidFlowNo},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateReturnFlowNos(@Param("prefix") String prefix,
                            @Param("salesOrderId") Long salesOrderId,
                            @Param("dataVersion") Long dataVersion,
                            @Param("returnCashFlowNo") String returnCashFlowNo,
                            @Param("returnCouponFlowNo") String returnCouponFlowNo,
                            @Param("returnPrepaidFlowNo") String returnPrepaidFlowNo);


    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "tracelog = #{tracelog},",
            "paidTime = #{paidTime},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateSalesOrderStatusAndPaidTime(@Param("prefix") String prefix,
                                          @Param("salesOrderId") Long salesOrderId,
                                          @Param("dataVersion") Long dataVersion,
                                          @Param("salesOrderStatus") int salesOrderStatus,
                                          @Param("tracelog") String tracelog,
                                          @Param("paidTime") Date paidTime);

    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "informWarehouseTime = #{informWarehouseTime},",
            "syncEas = #{syncEas},",
            "unhandledQuantity = #{unhandledQuantity},",
            "ongoingOutboundOrderInfo = #{ongoingOutboundOrderInfo},",
            "tracelog = #{tracelog},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateWhenScheduleDelivery(@Param("prefix") String prefix,
                                   @Param("salesOrderId") Long salesOrderId,
                                   @Param("dataVersion") Long dataVersion,
                                   @Param("salesOrderStatus") int salesOrderStatus,
                                   @Param("informWarehouseTime") Date informWarehouseTime,
                                   @Param("syncEas") int syncEasStatus,
                                   @Param("unhandledQuantity") int unhandledQuantity,
                                   @Param("ongoingOutboundOrderInfo") String ongoingOutboundOrderInfo,
                                   @Param("tracelog") String tracelog);


    @Update({
            "update ${prefix}_sales_order",
            "set",
            "salesOrderStatus = #{salesOrderStatus},",
            "outboundTime = #{outboundTime},",
            "onGoingOutboundOrderInfo = #{onGoingOutboundOrderInfo},",
            "finishedOutboundOrderInfo = #{finishedOutboundOrderInfo},",
            "inTransitQuantity = #{inTransitQuantity},",
            "tracelog = #{tracelog},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateWhenOutbound(@Param("prefix") String prefix,
                           @Param("salesOrderId") Long salesOrderId,
                           @Param("dataVersion") Long dataVersion,
                           @Param("salesOrderStatus") int salesOrderStatus,
                           @Param("outboundTime") Date outboundTime,
                           @Param("onGoingOutboundOrderInfo") String onGoingOutboundOrderInfo,
                           @Param("finishedOutboundOrderInfo") String finishedOutboundOrderInfo,
                           @Param("inTransitQuantity") int inTransitQuantity,
                           @Param("tracelog") String tracelog);

    @Update({
            "update ${prefix}_sales_order",
            "set",
            "deliveredQuantity = #{deliveredQuantity},",
            "inTransitQuantity = #{inTransitQuantity},",
            "salesOrderStatus = #{salesOrderStatus},",
            "signTime = #{signTime},",
            "tracelog = #{tracelog},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateWhenOutboundSigned(@Param("prefix") String prefix,
                                 @Param("salesOrderId") Long salesOrderId,
                                 @Param("dataVersion") Long dataVersion,
                                 @Param("deliveredQuantity") int deliveredQuantity,
                                 @Param("inTransitQuantity") int inTransitQuantity,
                                 @Param("salesOrderStatus") int salesOrderStatus,
                                 @Param("signTime") Date signTime,
                                 @Param("tracelog") String tracelog);


    @Update({
            "update ${prefix}_sales_order",
            "set",
            "syncEas = #{syncEas},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateSyncEasStatus(@Param("prefix") String prefix,
                            @Param("salesOrderId") Long salesOrderId,
                            @Param("dataVersion") Long dataVersion,
                            @Param("syncEas") int syncEas);


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

    @Update({
            "update ${prefix}_sales_order",
            "set",
            "returnedQuantity = #{returnedQuantity},",
            "dataVersion = #{dataVersion} + 1",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateReturnedQuantity(@Param("prefix") String prefix,
                               @Param("salesOrderId") Long salesOrderId,
                               @Param("dataVersion") Long dataVersion,
                               @Param("returnedQuantity") int returnedQuantity);


}