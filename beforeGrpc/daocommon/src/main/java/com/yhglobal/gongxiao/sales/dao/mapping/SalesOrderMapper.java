package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.dao.Provider.SalesReturnOrderProvider;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
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
    @Delete({
            "delete from t_sales_order",
            "where salesOrderId = #{salesOrderId}"
    })
    int deleteByPrimaryKey(Long salesOrderId);

    @Insert({
            "insert into t_sales_order (salesOrderId, salesOrderStatus, settlementMode, ",
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
            "cashFlowNo, sellHighAmount, totalGeneratedPrepaid, syncEas)",
            "values (#{salesOrderId}, #{salesOrderStatus},#{settlementMode}, ",
            "#{salesOrderNo}, #{salesContactNo}, ",
            "#{marketingChannel}, #{paymentChannel}, ",
            "#{projectId}, #{projectName}, ",
            "#{brandId}, #{brandName}, ",
            "#{supplierId}, #{supplierName}, ",
            "#{distributorId}, #{distributorName}, ",
            "#{shippingMode},  ",
            "#{provinceId}, #{provinceName}, ",
            "#{cityId}, #{cityName}, ",
            "#{districtId}, #{districtName}, ",
            "#{shippingAddress}, #{recipientName}, ",
            "#{recipientMobile}, #{warehouseId}, ",
            "#{warehouse}, #{recipientCompany}, ",
            "#{shippingExpense}, #{shippingExpensePaidBy}, ",
            "#{currencyId}, #{currencyCode}, ",
            "#{totalOrderAmount}, #{discountAmount}, ",
            "#{couponAmount}, #{prepaidAmount}, ",
            "#{prestoredAmount}, #{cashAmount}, ",
            "#{totalQuantity}, #{yhDiscountAmount}, ",
            "#{supplierDiscountAmount}, #{deliveredQuantity}, ",
            "#{inTransitQuantity}, #{canceledQuantity}, ",
            "#{returnedQuantity}, #{unhandledQuantity}, ",
            "#{ongoingOutboundOrderInfo}, #{finishedOutboundOrderInfo}, ",
            "#{dataVersion}, #{createdById}, ",
            "#{createdByName}, #{createTime}, ",
            "#{outBoundTime}, #{orderCheckTime}, ",
            "#{rejectTime}, #{paidTime}, ",
            "#{informWarehouseTime}, #{signTime}, ",
            "#{lastUpdateTime}, #{tracelog},#{paymentDays},",
            "#{cashFlowNo}, #{sellHighAmount}, #{totalGeneratedPrepaid}, #{syncEas})"
    })
    int insert(SalesOrder record);


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
            "rejectTime, paidTime, informWarehouseTime, signTime, lastUpdateTime, tracelog,paymentDays,easOrderNo,easOrderId, sellHighAmount, totalGeneratedPrepaid, syncEas",
            "from t_sales_order",
            "where salesOrderNo = #{salesOrderNo}"
    })
    SalesOrder selectByOrderNo(@Param("salesOrderNo") String salesOrderNo);

    @Update({
            "update t_sales_order",
            "set salesOrderStatus = #{salesOrderStatus},",
            "settlementMode = #{settlementMode},",
            "salesOrderNo = #{salesOrderNo},",
            "salesContactNo = #{salesContactNo},",
            "marketingChannel = #{marketingChannel},",
            "paymentChannel = #{paymentChannel},",
            "projectId = #{projectId},",
            "projectName = #{projectName},",
            "brandId = #{brandId},",
            "brandName = #{brandName},",
            "supplierId = #{supplierId},",
            "supplierName = #{supplierName},",
            "distributorId = #{distributorId},",
            "distributorName = #{distributorName},",
            "shippingMode = #{shippingMode},",
            "provinceId = #{provinceId},",
            "provinceName = #{provinceName},",
            "cityId = #{cityId},",
            "cityName = #{cityName},",
            "districtId = #{districtId},",
            "districtName = #{districtName},",
            "shippingAddress = #{shippingAddress},",
            "recipientName = #{recipientName},",
            "recipientMobile = #{recipientMobile},",
            "warehouseId = #{warehouseId},",
            "warehouse = #{warehouse},",
            "recipientCompany = #{recipientCompany},",
            "shippingExpense = #{shippingExpense},",
            "shippingExpensePaidBy = #{shippingExpensePaidBy},",
            "currencyId = #{currencyId},",
            "currencyCode = #{currencyCode},",
            "totalOrderAmount = #{totalOrderAmount},",
            "discountAmount = #{discountAmount},",
            "couponAmount = #{couponAmount},",
            "prepaidAmount = #{prepaidAmount},",
            "prestoredAmount = #{prestoredAmount},",
            "cashAmount = #{cashAmount},",
            "totalQuantity = #{totalQuantity},",
            "yhDiscountAmount = #{yhDiscountAmount},",
            "supplierDiscountAmount = #{supplierDiscountAmount},",
            "deliveredQuantity = #{deliveredQuantity},",
            "inTransitQuantity = #{inTransitQuantity},",
            "canceledQuantity = #{canceledQuantity},",
            "returnedQuantity = #{returnedQuantity},",
            "unhandledQuantity = #{unhandledQuantity},",
            "ongoingOutboundOrderInfo = #{ongoingOutboundOrderInfo},",
            "finishedOutboundOrderInfo = #{finishedOutboundOrderInfo},",
            "dataVersion = dataVersion+1,",
            "createdById = #{createdById},",
            "createdByName = #{createdByName},",
            "createTime = #{createTime},",
            "outBoundTime = #{outBoundTime},",
            "orderCheckTime = #{orderCheckTime},",
            "rejectTime = #{rejectTime},",
            "paidTime = #{paidTime},",
            "informWarehouseTime = #{informWarehouseTime},",
            "signTime = #{signTime},",
            "tracelog = #{tracelog},",
            "creditRemark = #{creditRemark},",
            "paymentDays = #{paymentDays},",
            "couponFlowNo = #{couponFlowNo},",
            "prepaidFlowNo = #{prepaidFlowNo},",
            "returnCashFlowNo = #{returnCashFlowNo},",
            "returnCouponFlowNo = #{returnCouponFlowNo},",
            "returnPrepaidFlowNo = #{returnPrepaidFlowNo},",
            "syncEas = #{syncEas},",
            "easOrderNo = #{easOrderNo},",
            "easOrderId = #{easOrderId}",
            "where salesOrderId = #{salesOrderId} and dataVersion = #{dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(SalesOrder record);

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
    @SelectProvider(type = SalesOrderSqlProvider.class, method = "getListSelectively")
    List<SalesOrder> getListSelectively(@Param("projectId") long projectId,
                                        @Param("distributorId") Long distributorId,
                                        @Param("salesOrderNo") String salesOrderNo,
                                        @Param("productCode") String productCode,
                                        @Param("outBoundTime") Date outBoundTime,
                                        @Param("createTimeBegin") Date createTimeBegin,
                                        @Param("createTimeEnd") Date createTimeEnd,
                                        @Param("salesOrderStatus") Integer salesOrderStatus);


    /**
     * 查询每个状态对应的数量 map
     *
     * @param projectId       项目
     * @param distributorId   分销商id
     * @param salesOrderNo    销售单号
     * @param productCode     产品编码
     * @param outBoundTime    出库时间
     * @param createTimeBegin 创建时间-起始条件
     * @param createTimeEnd   创建时间-截止条件
     * @return
     */
    @SelectProvider(type = SalesOrderSqlProvider.class, method = "getCountMap")
    @MapKey("salesOrderStatus")
    List<Map<String, Integer>> getCountMap(@Param("projectId") long projectId,
                                           @Param("distributorId") Long distributorId,
                                           @Param("salesOrderNo") String salesOrderNo,
                                           @Param("productCode") String productCode,
                                           @Param("outBoundTime") Date outBoundTime,
                                           @Param("createTimeBegin") Date createTimeBegin,
                                           @Param("createTimeEnd") Date createTimeEnd);


    /**
     * 查询总数
     *
     * @param projectId       项目
     * @param distributorId   分销商id
     * @param salesOrderNo    销售单号
     * @param productCode     产品编码
     * @param outBoundTime    出库时间
     * @param createTimeBegin 创建时间-起始条件
     * @param createTimeEnd   创建时间-截止条件
     * @return
     */
    @SelectProvider(type = SalesOrderSqlProvider.class, method = "getCountSelective")
    int getCountSelective(@Param("projectId") long projectId,
                          @Param("distributorId") Long distributorId,
                          @Param("salesOrderNo") String salesOrderNo,
                          @Param("productCode") String productCode,
                          @Param("outBoundTime") Date outBoundTime,
                          @Param("createTimeBegin") Date createTimeBegin,
                          @Param("createTimeEnd") Date createTimeEnd);


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
            "from t_sales_order",
            "where syncEas = #{syncEas}"
    })
    List<SalesOrder> selectToSyncEasOrder(@Param("syncEas") int syncEas);

}