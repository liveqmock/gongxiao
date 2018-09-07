package com.yhglobal.gongxiao.phjd.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.model.PurchaseOrder;
import com.yhglobal.gongxiao.phjd.model.PurchaseOrderBackWrite;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface PurchaseOrderMapper extends BaseMapper {
    @Delete({
            "delete from ${tablePrefix}_purchase_order",
            "where purchaseOrderId = #{purchaseOrderId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(@Param("tablePrefix") String tablePrefix,
                           @Param("purchaseOrderId") Long purchaseOrderId);

    @Insert({
            "insert into ${tablePrefix}_purchase_order (purchaseOrderId, orderStatus,easStatus, ",
            "purchaseOrderNo, projectId, ",
            "projectName, brandId, ",
            "brandName, supplierId, ",
            "supplierName, warehouseId, ",
            "warehouseName,supplierReceipt,cashAmount, purchaseType,provinceId, ",
            "provinceName, cityId, ",
            "cityName, districtId, ",
            "districtName, currencyId, ",
            "currencyName, paymentMode, ",
            "paymentChannel, shippingMode, businessDate,",
            "expectedInboundDate, orderDeadlineDate, ",
            "brandOrderNo, contractReferenceOrderNo, ",
            "remark, purchaseCategory, ",
            "totalQuantity, couponAmountUse, ",
            "prepaidAmountUse,adCouponAmountUse,adPrepaidAmountUse, estimatedCouponTotalAmount, ",
            "purchaseGuideAmount, purchasePrivilegeAmount, ",
            "returnCash, purchaseShouldPayAmount, ",
            "purchaseActualPayAmount, couponActivityInfo, ",
            "paymentFlag, productInfo, ",
            "inTransitQuantity, inStockQuantity, ",
            "canceledQuantity, returnedQuantity, ",
            "unhandledQuantity, ongoingInboundOrderInfo, ",
            "finishedInboundOrderInfo, dataVersion, ",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime, ",
            "cashAmountUse, couponConfirmReceipt,",
            "prepaidConfirmReceipt, cashConfirmReceipt,",
            "easPurchaseOrderId, easOrderNumber,",
            " tracelog)",
            "values (#{purchaseOrderId,jdbcType=BIGINT}, #{orderStatus,jdbcType=TINYINT},#{easStatus}, ",
            "#{purchaseOrderNo,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{brandId,jdbcType=INTEGER}, ",
            "#{brandName,jdbcType=VARCHAR}, #{supplierId,jdbcType=VARCHAR}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{warehouseId,jdbcType=INTEGER}, ",
            "#{warehouseName,jdbcType=VARCHAR},#{supplierReceipt},#{cashAmount},#{purchaseType}, #{provinceId,jdbcType=INTEGER}, ",
            "#{provinceName,jdbcType=VARCHAR}, #{cityId,jdbcType=INTEGER}, ",
            "#{cityName,jdbcType=VARCHAR}, #{districtId,jdbcType=INTEGER}, ",
            "#{districtName,jdbcType=VARCHAR}, #{currencyId,jdbcType=VARCHAR}, ",
            "#{currencyName,jdbcType=VARCHAR}, #{paymentMode,jdbcType=TINYINT}, ",
            "#{paymentChannel,jdbcType=TINYINT}, #{shippingMode,jdbcType=TINYINT},#{businessDate} ,",
            "#{expectedInboundDate,jdbcType=TIMESTAMP}, #{orderDeadlineDate,jdbcType=TIMESTAMP}, ",
            "#{brandOrderNo,jdbcType=VARCHAR}, #{contractReferenceOrderNo,jdbcType=VARCHAR}, ",
            "#{remark,jdbcType=VARCHAR}, #{purchaseCategory,jdbcType=INTEGER}, ",
            "#{totalQuantity,jdbcType=INTEGER}, #{couponAmountUse,jdbcType=BIGINT}, ",
            "#{prepaidAmountUse,jdbcType=BIGINT}, ",
            "#{adCouponAmountUse,jdbcType=BIGINT}, #{adPrepaidAmountUse,jdbcType=BIGINT},  #{estimatedCouponTotalAmount,jdbcType=BIGINT},",
            "#{purchaseGuideAmount,jdbcType=BIGINT}, #{purchasePrivilegeAmount,jdbcType=BIGINT}, ",
            "#{returnCash,jdbcType=BIGINT}, #{purchaseShouldPayAmount,jdbcType=BIGINT}, ",
            "#{purchaseActualPayAmount,jdbcType=BIGINT}, #{couponActivityInfo,jdbcType=VARCHAR}, ",
            "#{paymentFlag,jdbcType=TINYINT}, #{productInfo,jdbcType=VARCHAR}, ",
            "#{inTransitQuantity,jdbcType=INTEGER}, #{inStockQuantity,jdbcType=INTEGER}, ",
            "#{canceledQuantity,jdbcType=INTEGER}, #{returnedQuantity,jdbcType=INTEGER}, ",
            "#{unhandledQuantity,jdbcType=INTEGER}, #{ongoingInboundOrderInfo,jdbcType=VARCHAR}, ",
            "#{finishedInboundOrderInfo,jdbcType=VARCHAR}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{cashAmountUse,jdbcType=BIGINT}, #{couponConfirmReceipt,jdbcType=VARCHAR},",
            "#{prepaidConfirmReceipt,jdbcType=VARCHAR}, #{cashConfirmReceipt},",
            "#{easPurchaseOrderId}, #{easOrderNumber},",
            " #{tracelog})"
    })
    int insert(PurchaseOrder record);

    @Select({
            "select",
            "purchaseOrderId, orderStatus, easStatus,businessDate,purchaseType,purchaseOrderNo, projectId, projectName, brandId, ",
            "brandName, supplierId, supplierName, warehouseId, warehouseName, supplierReceipt,cashAmount,provinceId, ",
            "provinceName, cityId, cityName, districtId, districtName, currencyId, currencyName, ",
            "paymentMode, paymentChannel, shippingMode, expectedInboundDate, orderDeadlineDate, ",
            "brandOrderNo, contractReferenceOrderNo, remark, purchaseCategory, totalQuantity, ",
            "couponAmountUse, prepaidAmountUse, adCouponAmountUse,adPrepaidAmountUse,estimatedCouponTotalAmount, purchaseGuideAmount, ",
            "purchasePrivilegeAmount, returnCash, purchaseShouldPayAmount, purchaseActualPayAmount, ",
            "couponActivityInfo, paymentFlag, productInfo, inTransitQuantity, inStockQuantity, ",
            "canceledQuantity, returnedQuantity, unhandledQuantity, ongoingInboundOrderInfo, ",
            "finishedInboundOrderInfo, dataVersion, createdById, createdByName, createTime, ",
            "lastUpdateTime, cashAmountUse, couponConfirmReceipt, prepaidConfirmReceipt, ",
            "cashConfirmReceipt,easPurchaseOrderId, easOrderNumber, tracelog",
            "from ${tablePrefix}_purchase_order",
            "where purchaseOrderId = #{purchaseOrderId,jdbcType=BIGINT} and orderStatus!=99"
    })
    PurchaseOrder selectByPrimaryKey(@Param("tablePrefix") String tablePrefix,
                                     @Param("purchaseOrderId") Long purchaseOrderId);

    @Select({
            "select",
            "purchaseOrderId, orderStatus,easStatus,businessDate, purchaseType,purchaseOrderNo, projectId, projectName, brandId, ",
            "brandName, supplierId, supplierName, warehouseId, warehouseName,supplierReceipt,cashAmount, provinceId, ",
            "provinceName, cityId, cityName, districtId, districtName, currencyId, currencyName, ",
            "paymentMode, paymentChannel, shippingMode, expectedInboundDate, orderDeadlineDate, ",
            "brandOrderNo, contractReferenceOrderNo, remark, purchaseCategory, totalQuantity, ",
            "couponAmountUse, prepaidAmountUse, adCouponAmountUse,adPrepaidAmountUse,estimatedCouponTotalAmount, purchaseGuideAmount, ",
            "purchasePrivilegeAmount, returnCash, purchaseShouldPayAmount, purchaseActualPayAmount, ",
            "couponActivityInfo, paymentFlag, productInfo, inTransitQuantity, inStockQuantity, ",
            "canceledQuantity, returnedQuantity, unhandledQuantity, ongoingInboundOrderInfo, ",
            "finishedInboundOrderInfo, dataVersion, createdById, createdByName, createTime, ",
            "lastUpdateTime, cashAmountUse, couponConfirmReceipt, prepaidConfirmReceipt, ",
            "cashConfirmReceipt, easPurchaseOrderId, easOrderNumber, tracelog",
            "from ${tablePrefix}_purchase_order",
            "where and orderStatus!=99",
            "order by createTime desc "
    })
    List<PurchaseOrder> selectAll(@Param("tablePrefix") String tablePrefix);

    @Select({
            "select",
            "purchaseOrderId, uniqueNumber,businessDate,orderStatus,easStatus, purchaseType,purchaseOrderNo, projectId, projectName, brandId, ",
            "brandName,supplierId,supplierName, warehouseId, warehouseName,supplierReceipt,cashAmount, currencyId, currencyName, paymentMode, ",
            "paymentChannel, shippingMode, expectedInboundDate, orderDeadlineDate, brandOrderNo, ",
            "contractReferenceOrderNo, remark, purchaseCategory, totalQuantity, couponAmountUse, ",
            "prepaidAmountUse,adCouponAmountUse,adPrepaidAmountUse, estimatedCouponTotalAmount, purchaseGuideAmount, purchasePrivilegeAmount, ",
            "returnCash, purchaseShouldPayAmount, purchaseActualPayAmount, couponActivityInfo, ",
            "paymentFlag, productInfo, inTransitQuantity, inStockQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ongoingInboundOrderInfo, finishedInboundOrderInfo, ",
            "dataVersion, createdById, createdByName, createTime, lastUpdateTime",
            "cashAmountUse, couponConfirmReceipt, prepaidConfirmReceipt, cashConfirmReceipt, ",
            "easPurchaseOrderId, easOrderNumber, tracelog",
            "from ${tablePrefix}_purchase_order",
            "where purchaseOrderNo = #{purchaseOrderNo} and orderStatus!=99 "
    })
    PurchaseOrder selectByPurchaseNo(@Param("tablePrefix") String tablePrefix,
                                     @Param("purchaseOrderNo") String purchaseOrderNo);

    @Select({
            "select",
            "purchaseOrderId, uniqueNumber,businessDate,orderStatus,easStatus, purchaseType,purchaseOrderNo, projectId, projectName, brandId, ",
            "brandName,supplierId,supplierName, warehouseId, warehouseName,supplierReceipt,cashAmount, currencyId, currencyName, paymentMode, ",
            "paymentChannel, shippingMode, expectedInboundDate, orderDeadlineDate, brandOrderNo, ",
            "contractReferenceOrderNo, remark, purchaseCategory, totalQuantity, couponAmountUse, ",
            "prepaidAmountUse,adCouponAmountUse,adPrepaidAmountUse, estimatedCouponTotalAmount, purchaseGuideAmount, purchasePrivilegeAmount, ",
            "returnCash, purchaseShouldPayAmount, purchaseActualPayAmount, couponActivityInfo, ",
            "paymentFlag, productInfo, inTransitQuantity, inStockQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ongoingInboundOrderInfo, finishedInboundOrderInfo, ",
            "dataVersion, createdById, createdByName, createTime, lastUpdateTime",
            "cashAmountUse, couponConfirmReceipt, prepaidConfirmReceipt, cashConfirmReceipt, ",
            "easPurchaseOrderId, easOrderNumber, tracelog",
            "from ${tablePrefix}_purchase_order",
            "where brandOrderNo = #{brandOrderNo} and orderStatus!=99"
    })
    List<PurchaseOrder> selectByBrandOrderNo(@Param("tablePrefix") String tablePrefix,
                                             @Param("brandOrderNo") String brandOrderNo);

    @Select({
            "select",
            "purchaseOrderId, uniqueNumber,businessDate,orderStatus, purchaseType,easStatus,purchaseOrderNo, projectId, projectName, brandId, ",
            "brandName,supplierId,supplierName, warehouseId, warehouseName,supplierReceipt,cashAmount, currencyId, currencyName, paymentMode, ",
            "paymentChannel, shippingMode, expectedInboundDate, orderDeadlineDate, brandOrderNo, ",
            "contractReferenceOrderNo, remark, purchaseCategory, totalQuantity, couponAmountUse, ",
            "prepaidAmountUse, adCouponAmountUse,adPrepaidAmountUse,estimatedCouponTotalAmount, purchaseGuideAmount, purchasePrivilegeAmount, ",
            "returnCash, purchaseShouldPayAmount, purchaseActualPayAmount, couponActivityInfo, ",
            "paymentFlag, productInfo, inTransitQuantity, inStockQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ongoingInboundOrderInfo, finishedInboundOrderInfo, ",
            "dataVersion, createdById, createdByName, createTime, lastUpdateTime",
            "cashAmountUse, couponConfirmReceipt, prepaidConfirmReceipt, cashConfirmReceipt, ",
            "easPurchaseOrderId, easOrderNumber, tracelog",
            "from ${tablePrefix}_purchase_order",
            "where uniqueNumber = #{uniqueNumber}  and orderStatus!=99"
    })
    PurchaseOrder getByUniqueNumber(@Param("tablePrefix") String tablePrefix,
                                    @Param("uniqueNumber") String uniqueNumber);

    @Select({
            "select",
            "purchaseOrderId, uniqueNumber,businessDate,orderStatus,purchaseType,easStatus, purchaseOrderNo, projectId, projectName, brandId, ",
            "brandName,supplierId,supplierName, warehouseId, warehouseName, supplierReceipt,cashAmount,currencyId, currencyName, paymentMode, ",
            "paymentChannel, shippingMode, expectedInboundDate, orderDeadlineDate, brandOrderNo, ",
            "contractReferenceOrderNo, remark, purchaseCategory, totalQuantity, couponAmountUse, ",
            "prepaidAmountUse, adCouponAmountUse,adPrepaidAmountUse,estimatedCouponTotalAmount, purchaseGuideAmount, purchasePrivilegeAmount, ",
            "returnCash, purchaseShouldPayAmount, purchaseActualPayAmount, couponActivityInfo, ",
            "paymentFlag, productInfo, inTransitQuantity, inStockQuantity, canceledQuantity, ",
            "returnedQuantity, unhandledQuantity, ongoingInboundOrderInfo, finishedInboundOrderInfo, ",
            "dataVersion, createdById, createdByName, createTime, lastUpdateTime",
            "cashAmountUse, couponConfirmReceipt, prepaidConfirmReceipt, cashConfirmReceipt, ",
            "easPurchaseOrderId, easOrderNumber, tracelog",
            "from ${tablePrefix}_purchase_order",
            "where easStatus = #{easStatus} and orderStatus>3 and orderStatus!=99"
    })
    List<PurchaseOrder> selectPurchaseOrderListByEasStatus(@Param("tablePrefix") String tablePrefix,
                                                           @Param("easStatus") int easStatus);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set orderStatus = #{orderStatus,jdbcType=TINYINT},",
            "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "brandId = #{brandId,jdbcType=INTEGER},",
            "brandName = #{brandName,jdbcType=VARCHAR},",
            "warehouseId = #{warehouseId,jdbcType=INTEGER},",
            "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
            "currencyId = #{currencyId,jdbcType=VARCHAR},",
            "currencyName = #{currencyName,jdbcType=VARCHAR},",
            "paymentMode = #{paymentMode,jdbcType=TINYINT},",
            "paymentChannel = #{paymentChannel,jdbcType=TINYINT},",
            "shippingMode = #{shippingMode,jdbcType=TINYINT},",
            "expectedInboundDate = #{expectedInboundDate,jdbcType=TIMESTAMP},",
            "orderDeadlineDate = #{orderDeadlineDate,jdbcType=TIMESTAMP},",
            "brandOrderNo = #{brandOrderNo,jdbcType=VARCHAR},",
            "contractReferenceOrderNo = #{contractReferenceOrderNo,jdbcType=VARCHAR},",
            "remark = #{remark,jdbcType=VARCHAR},",
            "purchaseCategory = #{purchaseCategory,jdbcType=INTEGER},",
            "totalQuantity = #{totalQuantity,jdbcType=INTEGER},",
            "couponAmountUse = #{couponAmountUse,jdbcType=BIGINT},",
            "prepaidAmountUse = #{prepaidAmountUse,jdbcType=BIGINT},",
            "estimatedCouponTotalAmount = #{estimatedCouponTotalAmount,jdbcType=BIGINT},",
            "purchaseGuideAmount = #{purchaseGuideAmount,jdbcType=BIGINT},",
            "purchasePrivilegeAmount = #{purchasePrivilegeAmount,jdbcType=BIGINT},",
            "returnCash = #{returnCash,jdbcType=BIGINT},",
            "purchaseShouldPayAmount = #{purchaseShouldPayAmount,jdbcType=BIGINT},",
            "purchaseActualPayAmount = #{purchaseActualPayAmount,jdbcType=BIGINT},",
            "couponActivityInfo = #{couponActivityInfo,jdbcType=VARCHAR},",
            "paymentFlag = #{paymentFlag,jdbcType=TINYINT},",
            "productInfo = #{productInfo,jdbcType=VARCHAR},",
            "inTransitQuantity = #{inTransitQuantity,jdbcType=INTEGER},",
            "inStockQuantity = #{inStockQuantity,jdbcType=INTEGER},",
            "canceledQuantity = #{canceledQuantity,jdbcType=INTEGER},",
            "returnedQuantity = #{returnedQuantity,jdbcType=INTEGER},",
            "unhandledQuantity = #{unhandledQuantity,jdbcType=INTEGER},",
            "ongoingInboundOrderInfo = #{ongoingInboundOrderInfo,jdbcType=VARCHAR},",
            "finishedInboundOrderInfo = #{finishedInboundOrderInfo,jdbcType=VARCHAR},",
            "dataVersion = #{dataVersion,jdbcType=BIGINT},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "cashAmountUse = #{cashAmountUse,jdbcType=BIGINT},",
            "couponConfirmReceipt = #{couponConfirmReceipt,jdbcType=VARCHAR},",
            "prepaidConfirmReceipt = #{prepaidConfirmReceipt,jdbcType=VARCHAR},",
            "cashConfirmReceipt = #{cashConfirmReceipt,jdbcType=VARCHAR},",
            "easPurchaseOrderId = #{easPurchaseOrderId,jdbcType=VARCHAR},",
            "easOrderNumber = #{easOrderNumber,jdbcType=VARCHAR},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where purchaseOrderId = #{purchaseOrderId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PurchaseOrder record);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set inTransitQuantity = #{inTransitQuantity,jdbcType=INTEGER},",
            "unhandledQuantity = #{unhandledQuantity,jdbcType=INTEGER},",
            "inStockQuantity = #{inStockQuantity,jdbcType=INTEGER},",
            "lastUpdateTime = NOW() ,",
            "ongoingInboundOrderInfo = #{ongoingInboundOrderInfo},",
            "orderStatus = #{orderStatus}",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int updateBackWrite(PurchaseOrderBackWrite purchaseOrderBackWrite);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set inTransitQuantity = #{inTransitQuantity,jdbcType=INTEGER},",
            "unhandledQuantity = #{unhandledQuantity,jdbcType=INTEGER},",
            "lastUpdateTime = NOW() ,",
            "ongoingInboundOrderInfo = #{ongoingInboundOrderInfo},",
            "orderStatus = #{orderStatus}",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int updateBack(PurchaseOrderBackWrite purchaseOrderBackWrite);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set ongoingInboundOrderInfo = #{ongoingInboundOrderInfo},",
            "finishedInboundOrderInfo = #{finishedInboundOrderInfo} ",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int updateInboundOrderInfo(@Param("tablePrefix") String tablePrefix,
                               @Param("purchaseOrderNo") String purchaseOrderNo,
                               @Param("ongoingInboundOrderInfo") String ongoingInboundOrderInfo,
                               @Param("finishedInboundOrderInfo") String finishedInboundOrderInfo);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set easPurchaseOrderId = #{easPurchaseOrderId},",
            "easStatus = #{easStatus}, ",
            "easOrderNumber = #{easOrderNumber}, ",
            "lastUpdateTime = NOW() ",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int updateEasInfo(@Param("tablePrefix") String tablePrefix,
                      @Param("purchaseOrderNo") String purchaseOrderNo,
                      @Param("easPurchaseOrderId") String easPurchaseOrderId,
                      @Param("easOrderNumber") String easOrderNumber,
                      @Param("easStatus") byte easStatus);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set orderStatus = 3,",
            "lastUpdateTime = NOW() ",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int updateAlreadyPay(@Param("tablePrefix") String tablePrefix,
                         @Param("purchaseOrderNo") String purchaseOrderNo);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set orderStatus = #{orderStatus},",
            "lastUpdateTime = NOW() ",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int updatePurchaseOrderStatus(@Param("tablePrefix") String tablePrefix,
                                  @Param("purchaseOrderNo") String purchaseOrderNo,
                                  @Param("orderStatus") int orderStatus);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set easStatus = #{targetEasStatus}",
            "where purchaseOrderId = #{purchaseOrderId} and easStatus = #{currentEasStatus}"
    })
    int updatePurchaseEasStatus(@Param("tablePrefix") String tablePrefix,
                                @Param("purchaseOrderId") long purchaseOrderId,
                                @Param("currentEasStatus") int currentEasStatus,
                                @Param("targetEasStatus") int targetEasStatus);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set traceLog = #{traceLog},",
            "lastUpdateTime = NOW() ",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int updateOperateTrace(@Param("tablePrefix") String tablePrefix,
                           @Param("purchaseOrderNo") String purchaseOrderNo,
                           @Param("traceLog") String traceLog);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set orderStatus = #{orderStatus,jdbcType=TINYINT},",
            "easStatus = #{easStatus},",
            "warehouseId = #{warehouseId,jdbcType=INTEGER},",
            "warehouseName = #{warehouseName,jdbcType=VARCHAR},",
            "purchaseType = #{purchaseType},",
            "supplierId = #{supplierId},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "paymentMode = #{paymentMode,jdbcType=TINYINT},",
            "paymentChannel = #{paymentChannel,jdbcType=TINYINT},",
            "shippingMode = #{shippingMode,jdbcType=TINYINT},",
            "businessDate = #{businessDate,jdbcType=TIMESTAMP},",
            "expectedInboundDate = #{expectedInboundDate,jdbcType=TIMESTAMP},",
            "orderDeadlineDate = #{orderDeadlineDate,jdbcType=TIMESTAMP},",
            "remark = #{remark,jdbcType=VARCHAR},",
            "couponAmountUse = #{couponAmountUse,jdbcType=BIGINT},",
            "prepaidAmountUse = #{prepaidAmountUse,jdbcType=BIGINT},",
            "purchaseGuideAmount = #{purchaseGuideAmount,jdbcType=BIGINT},",
            "purchasePrivilegeAmount = #{purchasePrivilegeAmount,jdbcType=BIGINT},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int updateByPurchase(PurchaseOrder record);

    @Update({
            "update ${tablePrefix}_purchase_order",
            "set orderStatus = 99,",
            "lastUpdateTime = NOW() ",
            "where purchaseOrderNo = #{purchaseOrderNo}"
    })
    int cancelPurchaseOrder(@Param("tablePrefix") String tablePrefix,
                            @Param("purchaseOrderNo") String purchaseOrderNo);


    @SelectProvider(type = PurchaseOrderSqlProvider.class, method = "getList")
    List<PurchaseOrder> getPurchaseOrderList(@Param("tablePrefix") String tablePrefix,
                                             @Param("purchaseOrderNo") String purchaseOrderNo,
                                             @Param("supplierId") String supplierId,
                                             @Param("warehouseId") String warehouseId,
                                             @Param("productCode") String productCode,
                                             @Param("requireArrivalDate") String requireArrivalDate,
                                             @Param("arrivalDeadline") String arrivalDeadline,
                                             @Param("businessDate") String businessDate);

}