package com.yhglobal.gongxiao.sales.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorCouponSettingService;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorPortalUserService;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.CashCouponPrepaidFlows;
import com.yhglobal.gongxiao.payment.service.CashConfirmService;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.sales.bo.SalesOrderDetail;
import com.yhglobal.gongxiao.sales.bo.SalesOrderInfo;
import com.yhglobal.gongxiao.sales.bo.SalesOrderItemInfo;
import com.yhglobal.gongxiao.sales.bo.SalesOrderList;
import com.yhglobal.gongxiao.sales.dao.SalePlanItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesScheduleDeliveryDao;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesOrderSettlementModeEnum;
import com.yhglobal.gongxiao.sales.model.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;
import com.yhglobal.gongxiao.sales.service.SalesOrderService;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;
import static com.yhglobal.gongxiao.constant.ErrorCode.DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.ErrorCode.DISTRIBUTOR_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.ErrorCode.DISTRIBUTOR_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.ErrorCode.ORDER_STATUS_CAN_NOT_MODIFY;
import static com.yhglobal.gongxiao.constant.ErrorCode.OVERTAKE_MAX_BUY_NUMBER;
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.constant.FXConstant.MILLION;

/**
 * 销售单商品详情Service实现类
 *
 * @author: 葛灿
 */
@Service(timeout = 15000)
public class SalesOrderServiceImpl implements SalesOrderService {

    private static Logger logger = LoggerFactory.getLogger(SalesOrderServiceImpl.class);

    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalePlanItemDao salePlanItemDao;
    @Reference
    private OutboundService outboundService;
    @Autowired
    private PlanSaleItemService planSaleItemService;
    @Reference
    private ProductService productService;
    @Reference
    private ProjectService projectService;
    @Reference
    private DistributorPortalUserService distributorPortalUserService;
    @Reference
    CashConfirmService cashConfirmService;
    @Reference(check = false)
    DistributorAccountService distributorAccountService;
    @Reference
    DistributorCouponSettingService distributorCouponSettingService;
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    SalesScheduleDeliveryDao salesScheduleDeliveryDao;

    @Override
    public SalesOrderDetail getOrderDetailBySalesOrderNo(RpcHeader rpcHeader, String salesOrderNo) {
        try {
            logger.info("#traceId={}# [IN][getOrderDetailBySalesOrderNo] params: salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            String floatString;
            // 一、查询订单信息(转换为前台页面接受的BO)
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
            SalesOrderDetail salesOrderDetail = new SalesOrderDetail();
            salesOrderDetail.setSalesOrderNo(salesOrder.getSalesOrderNo());
            salesOrderDetail.setSalesContactNo(salesOrder.getSalesContactNo());
            String status = SalesOrderStatusEnum.getMessage(salesOrder.getSalesOrderStatus());
            salesOrderDetail.setSalesOrderStatusString(status);
            salesOrderDetail.setSalesOrderStatus(salesOrder.getSalesOrderStatus());
            floatString = DoubleScale.keepTwoBits(salesOrder.getPrestoredAmount());
            salesOrderDetail.setPrestoredAmountDouble(floatString);
            salesOrderDetail.setRecipientCompany(salesOrder.getRecipientCompany());
            salesOrderDetail.setShippingAddress(salesOrder.getShippingAddress());
            salesOrderDetail.setProvinceId(salesOrder.getProvinceId());
            salesOrderDetail.setProvinceName(salesOrder.getProvinceName());
            salesOrderDetail.setCityId(salesOrder.getCityId());
            salesOrderDetail.setCityName(salesOrder.getCityName());
            salesOrderDetail.setDistrictId(salesOrder.getDistrictId());
            salesOrderDetail.setDistrictName(salesOrder.getDistrictName());
            salesOrderDetail.setRecipientName(salesOrder.getRecipientName());
            salesOrderDetail.setRecipientMobile(salesOrder.getRecipientMobile());
            salesOrderDetail.setOutBoundTime(salesOrder.getOutBoundTime());
            salesOrderDetail.setCreateTime(salesOrder.getCreateTime());
            salesOrderDetail.setOrderCheckTime(salesOrder.getOrderCheckTime());
            salesOrderDetail.setRejectTime(salesOrder.getRejectTime());
            salesOrderDetail.setPaidTime(salesOrder.getPaidTime());
            salesOrderDetail.setInformWarehouseTime(salesOrder.getInformWarehouseTime());
            salesOrderDetail.setSighTime(salesOrder.getSignTime());
            floatString = DoubleScale.keepTwoBits(salesOrder.getSupplierDiscountAmount());
            salesOrderDetail.setSupplierDiscountAmountDouble(floatString);
            floatString = DoubleScale.keepTwoBits(salesOrder.getYhDiscountAmount());
            salesOrderDetail.setYhDiscountAmountDouble(floatString);
            floatString = DoubleScale.keepTwoBits(salesOrder.getCouponAmount());
            salesOrderDetail.setCouponAmountDouble(floatString);
            floatString = DoubleScale.keepTwoBits(salesOrder.getPrepaidAmount());
            salesOrderDetail.setPrepaidAmountDouble(floatString);
            floatString = DoubleScale.keepTwoBits(salesOrder.getPrestoredAmount());
            salesOrderDetail.setPrestoredAmountDouble(floatString);
            floatString = DoubleScale.keepTwoBits(salesOrder.getCashAmount());
            salesOrderDetail.setCashAmountDouble(floatString);
            floatString = DoubleScale.keepTwoBits(salesOrder.getTotalOrderAmount());
            salesOrderDetail.setTotalOrderAmountDouble(floatString);
            salesOrderDetail.setTraceLog(JSON.parseArray(salesOrder.getTracelog(), TraceLog.class));
            // 二、查询销售单货品信息
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(salesOrderNo);
            ArrayList<SalesOrderItemInfo> salesOrderItemInfos = new ArrayList<>();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                SalesOrderItemInfo salesOrderItemInfo = new SalesOrderItemInfo();
                salesOrderItemInfo.setProductName(salesOrderItem.getProductName());
                salesOrderItemInfo.setProductCode(salesOrderItem.getProductCode());
                salesOrderItemInfo.setTotalQuantity(salesOrderItem.getTotalQuantity());
                salesOrderItemInfo.setCurrencyName(salesOrderItem.getCurrencyName());
                floatString = DoubleScale.keepSixBits(salesOrderItem.getSalesGuidePrice());
                salesOrderItemInfo.setGuidePriceDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getSupplierDiscountPercent());
                salesOrderItemInfo.setSupplierDiscountPercentDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getSupplierDiscountAmount());
                salesOrderItemInfo.setSupplierDiscountAmountDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getYhDiscountPercent());
                salesOrderItemInfo.setYhDiscountPercentDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getYhDiscountAmount());
                salesOrderItemInfo.setYhDiscountAmountDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getWholesalePrice());
                salesOrderItemInfo.setWholesalePriceDouble(floatString);
                floatString = DoubleScale.keepTwoBits(salesOrderItem.getTotalOrderAmount());
                salesOrderItemInfo.setTotalOrderAmountDouble(floatString);
                salesOrderItemInfo.setDeliveredQuantity(salesOrderItem.getDeliveredQuantity());
                salesOrderItemInfo.setSendQuantity(salesOrderItem.getTotalQuantity() - salesOrderItem.getUnhandledQuantity());
                salesOrderItemInfos.add(salesOrderItemInfo);
            }
            salesOrderDetail.setSalesOrderItemInfos(salesOrderItemInfos);

            logger.info("#traceId={}# [OUT]: get sales order success", rpcHeader.traceId);
            return salesOrderDetail;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public SalesOrderDetail getOrderByOrderNo(RpcHeader rpcHeader, String salesOrderNo) {
        try {
            logger.info("#traceId={}# [IN][getOrderByOrderNo] params: salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            String floatString;

            // 一、查询订单信息(转换为前台页面接受的BO)
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
            SalesOrderDetail salesOrderDetail = new SalesOrderDetail();
            salesOrderDetail.setSalesOrderNo(salesOrder.getSalesOrderNo());
            salesOrderDetail.setSalesContactNo(salesOrder.getSalesContactNo());
            String status = SalesOrderStatusEnum.getMessage(salesOrder.getSalesOrderStatus());
            salesOrderDetail.setSalesOrderStatusString(status);
            salesOrderDetail.setSalesOrderStatus(salesOrder.getSalesOrderStatus());
            floatString = DoubleScale.keepTwoBits(salesOrder.getPrestoredAmount());
            salesOrderDetail.setPrestoredAmountDouble(floatString);
            salesOrderDetail.setRecipientCompany(salesOrder.getRecipientCompany());
            salesOrderDetail.setShippingAddress(salesOrder.getShippingAddress());
            salesOrderDetail.setProvinceId(salesOrder.getProvinceId());
            salesOrderDetail.setProvinceName(salesOrder.getProvinceName());
            salesOrderDetail.setCityId(salesOrder.getCityId());
            salesOrderDetail.setCityName(salesOrder.getCityName());
            salesOrderDetail.setDistrictId(salesOrder.getDistrictId());
            salesOrderDetail.setDistrictName(salesOrder.getDistrictName());
            salesOrderDetail.setRecipientName(salesOrder.getRecipientName());
            salesOrderDetail.setRecipientMobile(salesOrder.getRecipientMobile());
            salesOrderDetail.setOutBoundTime(salesOrder.getOutBoundTime());
            salesOrderDetail.setCreateTime(salesOrder.getCreateTime());
            salesOrderDetail.setOrderCheckTime(salesOrder.getOrderCheckTime());
            salesOrderDetail.setRejectTime(salesOrder.getRejectTime());
            salesOrderDetail.setPaidTime(salesOrder.getPaidTime());
            salesOrderDetail.setInformWarehouseTime(salesOrder.getInformWarehouseTime());
            salesOrderDetail.setSighTime(salesOrder.getSignTime());
            floatString = DoubleScale.keepTwoBits(salesOrder.getTotalOrderAmount());
            salesOrderDetail.setTotalOrderAmountDouble(floatString);
            floatString = DoubleScale.keepTwoBits(salesOrder.getSupplierDiscountAmount());
            salesOrderDetail.setSupplierDiscountAmountDouble(floatString);
            floatString = DoubleScale.keepTwoBits(salesOrder.getYhDiscountAmount());
            salesOrderDetail.setYhDiscountAmountDouble(floatString);
            salesOrderDetail.setTraceLog(JSON.parseArray(salesOrder.getTracelog(), TraceLog.class));
            // 查询账户返利、账户余额
            AccountAmount distributorAccountAmount = distributorAccountService.getDistributorAccountAmount(rpcHeader, "CNY", salesOrder.getProjectId(), salesOrder.getDistributorId());
            long accountCouponAmount = distributorAccountAmount.getCouponAmount();
            floatString = DoubleScale.keepTwoBits(accountCouponAmount);
            salesOrderDetail.setDistributorCouponAmountDouble(floatString);
            long accountPrepaidAmount = distributorAccountAmount.getPrepaidAmount();
            floatString = DoubleScale.keepTwoBits(accountPrepaidAmount);
            salesOrderDetail.setDistributorPrepaidAmountDouble(floatString);
            // 订单需付金额 = 订单总金额 - 现金 - 返利 - 代垫
            long totalToPay = salesOrder.getTotalOrderAmount() - salesOrder.getPrestoredAmount();
            floatString = DoubleScale.keepTwoBits(totalToPay);
            salesOrderDetail.setCashAmountDouble(floatString);
            // 二、查询销售单货品信息
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(salesOrderNo);
            ArrayList<SalesOrderItemInfo> salesOrderItemInfos = new ArrayList<>();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                SalesOrderItemInfo salesOrderItemInfo = new SalesOrderItemInfo();
                salesOrderItemInfo.setProductName(salesOrderItem.getProductName());
                salesOrderItemInfo.setProductCode(salesOrderItem.getProductCode());
                salesOrderItemInfo.setTotalQuantity(salesOrderItem.getTotalQuantity());
                salesOrderItemInfo.setCurrencyName(salesOrderItem.getCurrencyName());
                floatString = DoubleScale.keepSixBits(salesOrderItem.getSalesGuidePrice());
                salesOrderItemInfo.setGuidePriceDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getSupplierDiscountPercent());
                salesOrderItemInfo.setSupplierDiscountPercentDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getSupplierDiscountAmount());
                salesOrderItemInfo.setSupplierDiscountAmountDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getYhDiscountPercent());
                salesOrderItemInfo.setYhDiscountPercentDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getYhDiscountAmount());
                salesOrderItemInfo.setYhDiscountAmountDouble(floatString);
                floatString = DoubleScale.keepSixBits(salesOrderItem.getWholesalePrice());
                salesOrderItemInfo.setWholesalePriceDouble(floatString);
                floatString = DoubleScale.keepTwoBits(salesOrderItem.getTotalOrderAmount());
                salesOrderItemInfo.setTotalOrderAmountDouble(floatString);
                salesOrderItemInfos.add(salesOrderItemInfo);
            }
            salesOrderDetail.setSalesOrderItemInfos(salesOrderItemInfos);

            logger.info("#traceId={}# [OUT]: get sales order success", rpcHeader.traceId);
            return salesOrderDetail;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public SalesOrderList getListSelectively(RpcHeader rpcHeader,
                                             long projectId, String orderNo,
                                             Date outDate, Date createTime,
                                             Integer orderStatus, int pageSize, int pageNum) {
        try {
            logger.info("#traceId={}# [IN][getListSelectively] params:  projectId={}, orderNo={}, outDate={}, createTime={}, orderStatus={}, pageSize={}, pageNum={}",
                    rpcHeader.traceId, projectId, orderNo, outDate, createTime, orderStatus, pageSize, pageNum);
            // 启动分页
            PageHelper.startPage(pageNum, pageSize);
            // 新建BO
            SalesOrderList salesOrderList = new SalesOrderList();
            // 订单信息
            List<SalesOrder> salesOrders = salesOrderDao.selectListSelectively(projectId, orderNo, outDate, createTime, orderStatus);
            for (SalesOrder salesOrder : salesOrders) {
                Long totalOrderAmount = salesOrder.getTotalOrderAmount();
                String floatString = DoubleScale.keepTwoBits(totalOrderAmount);
                salesOrder.setTotalOrderAmountDouble(floatString);
                Long cashAmount = salesOrder.getCashAmount();
                salesOrder.setCashAmountDoubleStr(DoubleScale.keepTwoBits(cashAmount));
            }
            // 包装分页对象
            PageInfo<SalesOrder> pageInfo = new PageInfo<SalesOrder>(salesOrders);
            salesOrderList.setSalesOrders(pageInfo);
            // 数量信息
            List<Map<String, Integer>> countMap = salesOrderDao.getCountMap(projectId, orderNo, outDate, createTime);
            salesOrderList.setCountMap(countMap);
            int count = salesOrderDao.getCountSelective(projectId, orderNo, outDate, createTime);
            salesOrderList.setTotalNumbers(count);
            logger.info("#traceId={}# [OUT]: get sales order list success", rpcHeader.traceId);
            return salesOrderList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public RpcResult changeSettlementMode(RpcHeader rpcHeader, String[] salesOrderNoList, int days, String remark) {
        try {
            logger.info("#traceId={}# [IN][changeSettlementMode] params: salesOrderNo={}", rpcHeader.traceId, String.valueOf(salesOrderNoList));
            ArrayList<SalesOrder> salesOrders = new ArrayList<>();
            for (String salesOrderNo : salesOrderNoList) {
                // 根据单号找到订单
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
                Integer settlementMode = salesOrder.getSettlementMode();
                // 如果订单的状态不为“初始化/待审核”、“已审核”，或结算方式不为“单结”,无法更改结算模式
                if ((salesOrderStatus != SalesOrderStatusEnum.INIT.getStatus() || salesOrderStatus != SalesOrderStatusEnum.CHECKED.getStatus()) && settlementMode != SalesOrderSettlementModeEnum.NORMAL.getStatus()) {
                    logger.info("#traceId={}# [OUT]: order {} can NOT change mode.", rpcHeader.traceId, salesOrderNo);
                    return RpcResult.newFailureResult(ORDER_STATUS_CAN_NOT_MODIFY.getErrorCode(), ORDER_STATUS_CAN_NOT_MODIFY.getMessage());
                }
                salesOrders.add(salesOrder);
            }
            for (SalesOrder salesOrder : salesOrders) {
                // 确认所有订单均可以修改“结算方式后”，修改
                salesOrder.setSettlementMode(SalesOrderSettlementModeEnum.CREDIT.getStatus());
                salesOrder.setPaymentDays(days);
                salesOrder.setCreditRemark(remark);
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "修改结算方式");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                salesOrder.setTracelog(traceLogs);
                // 如果订单的状态为"已审核/待收款"，则修改状态为“待发货”
                if (SalesOrderStatusEnum.CHECKED.getStatus() == salesOrder.getSalesOrderStatus()) {
                    salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.PAID.getStatus());
                }
                int update = salesOrderDao.update(salesOrder);
                if (update != 1) {
                    logger.error("salesOrder {} change settlement mode FAILED!", salesOrder.getSalesOrderNo());
                }
            }
            logger.info("#traceId={}# [OUT]: change settlement mode success", rpcHeader.traceId);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult createSalesOrder(RpcHeader rpcHeader,
                                      String salesOrderNo, String salesContactNo,
                                      Integer marketingChannel,
                                      Integer paymentChannel, Long projectId,
                                      String projectName, Long distributorId,
                                      String distributorName, String shippingAddress,
                                      String provinceId, String provinceName,
                                      String cityId, String cityName,
                                      String districtId, String districtName,
                                      String recipientName, String recipientMobile,
                                      String currencyId, String currencyCode,
                                      double cashAmount, double couponAmount,
                                      double prepaidAmount, double totalOrderAmount, double totalPaidAmount,
                                      Date createTime, List<SalesOrderItemInfo> list) {
        try {
            logger.info("#traceId={}# [IN][createSalesOrder] params: distributorId={}, distributorName={}, projectId={}, totalOrderAmount={}",
                    rpcHeader.traceId, distributorId, distributorName, projectId, totalOrderAmount);

            // 一、数据校验
            // 查询账户余额 如果使用现金>现金余额，信息返回页面
            AccountAmount account = distributorAccountService.getDistributorAccountAmount(rpcHeader, currencyCode, projectId, distributorId);
            if (account.getCashAmount() < cashAmount) {
                logger.info("#traceId={}# [OUT]: can NOT create salesOrderNo. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                return RpcResult.newFailureResult(DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), DISTRIBUTOR_CASH_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
            }
            // 查询预售库存
            if (true == false) {
                logger.info("#traceId={}# [OUT]: can NOT create salesOrderNo. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                return RpcResult.newFailureResult(OVERTAKE_MAX_BUY_NUMBER.getErrorCode(), OVERTAKE_MAX_BUY_NUMBER.getMessage());
            }
            if (totalPaidAmount < 0 || cashAmount < 0) {
                logger.info("#traceId={}# [OUT]: inputed ILLEGAL amount. totalPaidAmount={}, cashAmount={}", rpcHeader.traceId, totalPaidAmount, cashAmount);
                return RpcResult.newFailureResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
            }

            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
            String traceLogs = TraceLogUtil.appendTraceLog(null, traceLog);

            // 一、创建销售单商品记录
            // 总数量、越海支持金，供应商支持金 高卖金额 应收代垫
            int totalQuantity = 0;
            long yhSupportAmount = 0;
            long supplierSupportAmount = 0;
            long totalSellHighAmount = 0;
            long totalGeneratedPrepaid = 0;
            // 存放商品型号的set
            HashSet<String> itemsCodeSet = new HashSet<>();
            for (SalesOrderItemInfo info : list) {
                String productCode = info.getProductCode();
                Integer quantity = info.getTotalQuantity();
                SalesOrderItem salesOrderItem = new SalesOrderItem();
                double customerDiscountAmountDouble = info.getCustomerDiscountAmountDouble();
                long customerDiscountAmount = Math.round(customerDiscountAmountDouble * HUNDRED);
                salesOrderItem.setCustomerDiscountAmount(customerDiscountAmount);
                salesOrderItem.setUsedCouponAmount(0);
                salesOrderItem.setUsedPrepaidAmount(0);
                salesOrderItem.setItemStatus(0);
                salesOrderItem.setSalesOrderNo(salesOrderNo);
                salesOrderItem.setProductCode(productCode);
                salesOrderItem.setTotalQuantity(quantity);
                salesOrderItem.setTotalOrderAmount(Math.round(Double.parseDouble(info.getTotalOrderAmountDouble()) * HUNDRED));
                // 从预售获取商品信息
                SalesPlanItem salesPlanItem = salePlanItemDao.getItemByProductCode(projectId + "", distributorId + "", productCode, createTime);
                salesOrderItem.setProductId(salesPlanItem.getProductId());
                salesOrderItem.setProductCode(productCode);
                salesOrderItem.setProductName(salesPlanItem.getProductName());
                salesOrderItem.setCurrencyCode(salesPlanItem.getCurrencyCode());
                salesOrderItem.setCurrencyName(salesPlanItem.getCurrencyName());
                salesOrderItem.setSupplierDiscountPercent(salesPlanItem.getBrandPrepaidDiscount());
                salesOrderItem.setYhDiscountPercent(salesPlanItem.getYhPrepaidDiscount());
                salesOrderItem.setWholesalePrice(salesPlanItem.getWholesalePrice());

                long yhDiscountAmount = Math.round(1.0 * salesPlanItem.getYhPrepaidUnit() * quantity / 10000.0);
                salesOrderItem.setYhDiscountAmount(salesPlanItem.getYhPrepaidUnit());
                salesOrderItem.setTotalQuantity(quantity);
                salesOrderItem.setDeliveredQuantity(0);
                salesOrderItem.setInTransitQuantity(0);
                salesOrderItem.setCanceledQuantity(0);
                salesOrderItem.setReturnedQuantity(0);
                salesOrderItem.setDataVersion(0L);
                salesOrderItem.setRetailPrice(0);
                salesOrderItem.setUnhandledQuantity(quantity);
                salesOrderItem.setCreateTime(createTime);
                salesOrderItem.setTracelog(traceLogs);
                salesOrderItem.setOngoingOutboundOrderInfo("[]");
                salesOrderItem.setFinishedOutboundOrderInfo("[]");
                ProductBasic productBasic = productService.getByProductCode(rpcHeader, productCode);
                String easCode = productBasic.getEasCode();
                salesOrderItem.setEasCode(easCode);
                long purchaseGuidePrice = salesPlanItem.getGuidePrice();
                long salesGuidePrice = salesPlanItem.getSaleGuidePrice();
                //销售成本价 = 采购指导价*(1-YH毛利支持折扣)
                double percent = 1.0 * salesPlanItem.getYhPrepaidDiscount() / MILLION;
                long salesCostPrice = Math.round(purchaseGuidePrice * (1 - percent));
                //出货价
                long wholesalePrice = salesPlanItem.getWholesalePrice();
                //当【出货价-销售成本价>0】时，就是高卖，生成销售差价，乘以数量后的金额到销售差价预留账户中。
                //当【出货价-销售成本价<0】时，就是低卖，生成应收代垫，乘以数量后的金额到代垫确认页面生成应收代垫。
                long difference = wholesalePrice - salesCostPrice;
                if (difference > 0) {
                    //产生差价
                    totalSellHighAmount += difference * quantity / 1000.0;
                    salesOrderItem.setSellHighAmount(difference);
                } else if (difference < 0) {
                    //产生代垫
                    salesOrderItem.setGeneratedPrepaid(Math.abs(difference));
                    totalGeneratedPrepaid += (long) (Math.abs(difference) * quantity / 10000.0);
                }
                salesOrderItem.setSupplierDiscountAmount(salesPlanItem.getBrandPrepaidUnit());
                supplierSupportAmount += salesPlanItem.getBrandPrepaidUnit() / 10000.0 * quantity;
                salesOrderItem.setPurchaseGuidePrice(purchaseGuidePrice);
                salesOrderItem.setSalesGuidePrice(salesGuidePrice);
                salesOrderItemDao.insert(salesOrderItem);
                // 调用预售接口，修改预售剩余商品数量
                RpcResult rpcResult = planSaleItemService.updateSaleOccupation(rpcHeader, projectId + "", distributorId + "", salesOrderItem.getProductId() + "", createTime, quantity);
                if (!rpcResult.getSuccess()) {
                    logger.error("update plan item FAILED! productCode={}, quantity={}", productCode, quantity);
                }
                // 销售单总金额累加
                totalQuantity += quantity;
                yhSupportAmount += yhDiscountAmount;
                itemsCodeSet.add(productCode);
            }

            // 二、创建销售单
            SalesOrder salesOrder = new SalesOrder();
            salesOrder.setSettlementMode(SalesOrderSettlementModeEnum.NORMAL.getStatus());
            salesOrder.setSalesContactNo(salesContactNo);
            salesOrder.setShippingMode(1);
            salesOrder.setSalesOrderNo(salesOrderNo);
            salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.INIT.getStatus());
            salesOrder.setMarketingChannel(marketingChannel);
            salesOrder.setPaymentChannel(paymentChannel);
            salesOrder.setProjectId(projectId);
            salesOrder.setProjectName(projectName);
            salesOrder.setTotalGeneratedPrepaid(totalGeneratedPrepaid);
            // 去基础资料查数据
            Project project = projectService.getByProjectId(rpcHeader, projectId + "");
            salesOrder.setBrandId((long) project.getBrandId());
            salesOrder.setBrandName(project.getBrandName());
            salesOrder.setSupplierId((long) project.getSupplierId());
            salesOrder.setSupplierName(project.getSupplierName());
            salesOrder.setDistributorId(distributorId);
            salesOrder.setDistributorName(distributorName);
            salesOrder.setRecipientCompany(distributorName);
            salesOrder.setShippingAddress(shippingAddress);
            salesOrder.setProvinceId(provinceId);
            salesOrder.setProvinceName(provinceName);
            salesOrder.setCityId(cityId);
            salesOrder.setCityName(cityName);
            salesOrder.setDistrictId(districtId);
            salesOrder.setDistrictName(districtName);
            salesOrder.setRecipientName(recipientName);
            salesOrder.setRecipientMobile(recipientMobile);
            salesOrder.setCurrencyId(currencyId);
            salesOrder.setCurrencyCode(currencyCode);
            salesOrder.setTotalQuantity(totalQuantity);
            salesOrder.setUnhandledQuantity(totalQuantity);
            salesOrder.setTotalOrderAmount(Math.round(totalOrderAmount * HUNDRED));
            salesOrder.setCouponAmount(0L);
            salesOrder.setPrepaidAmount(0L);
            salesOrder.setCashAmount(Math.round(totalPaidAmount * HUNDRED));
            salesOrder.setPrestoredAmount(Math.round(cashAmount * HUNDRED));
            salesOrder.setYhDiscountAmount(yhSupportAmount);
            salesOrder.setSupplierDiscountAmount(supplierSupportAmount);
            salesOrder.setSellHighAmount(totalSellHighAmount);
            salesOrder.setCreateTime(createTime);
            salesOrder.setTracelog(traceLogs);
            salesOrder.setDeliveredQuantity(0);
            salesOrder.setInTransitQuantity(0);
            salesOrder.setCanceledQuantity(0);
            salesOrder.setReturnedQuantity(0);
            salesOrder.setDataVersion(0L);
            salesOrder.setOngoingOutboundOrderInfo("[]");
            salesOrder.setFinishedOutboundOrderInfo("[]");
            String itemsCodeJson = JSON.toJSONString(itemsCodeSet);
            salesOrder.setItemsCode(itemsCodeJson);
            salesOrder.setDiscountAmount(yhSupportAmount + supplierSupportAmount);
            salesOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            salesOrder.setCreatedByName(rpcHeader.getUsername());
            // 销售单插入数据库
            // 三、账户中暂扣分销商的返利、代垫、现金
            if (cashAmount > 0) {
                RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountService.payForSalesOrder(rpcHeader, currencyCode,
                        distributorId, projectId,
                        0, 0, Math.round(cashAmount * HUNDRED),
                        salesOrderNo, new Date());
                if (rpcResult.getSuccess()) {
                    salesOrder.setCashFlowNo(rpcResult.getResult().getCashFlowNo());
                } else {
                    logger.error("salesOrder update cashAccount FAILED ! salesOrderNo={}", salesOrder);
                }
            }
            int insert = salesOrderDao.insert(salesOrder);

            logger.info("#traceId={}# [OUT]: create sales order success", rpcHeader.traceId);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult updateRecipientInfo(RpcHeader rpcHeader,
                                         String orderNo, String recipientName,
                                         String recipientMobile, String provinceId, String provinceName,
                                         String cityId, String cityName,
                                         String districtId, String districtName,
                                         String shippingAddress, String recipientCompany) {
        try {
            logger.info("#traceId={}# [IN][updateRecipientInfo] params: orderNo={}, recipientName={}, recipientMobile={}, provinceId={}, provinceName={}, cityId={}, cityName={}, districtId={}, districtName={}, shippingAddress={}, recipientCompany={}",
                    rpcHeader.traceId, orderNo, recipientName, recipientMobile, provinceId, provinceName, cityId, cityName, districtId, districtName, shippingAddress, recipientCompany);
            int maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                // 找到销售单，修改字段信息，更新数据库
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(orderNo);
                salesOrder.setRecipientName(recipientName);
                salesOrder.setRecipientMobile(recipientMobile);
                salesOrder.setProvinceId(provinceId);
                salesOrder.setProjectName(provinceName);
                salesOrder.setCityId(cityId);
                salesOrder.setCityName(cityName);
                salesOrder.setDistrictId(districtId);
                salesOrder.setDistrictName(districtName);
                salesOrder.setShippingAddress(shippingAddress);
                salesOrder.setRecipientCompany(recipientCompany);
                // 操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "修改收件人信息");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                salesOrder.setTracelog(traceLogs);
                int update = salesOrderDao.update(salesOrder);
                if (update == 1) {
                    //修改成功
                    break;
                }
            }
            // 如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update salesOrder recipient info. salesOrder={}", orderNo);
                throw new RuntimeException("FAILED to update salesOrder recipient info");
            }
            logger.info("#traceId={}# [OUT]: update recipient info success. orderNo={}", rpcHeader.traceId, orderNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public RpcResult approveSalesOrder(RpcHeader rpcHeader,
                                       String salesOrderNo, Double couponAmountDouble,
                                       Double prepaidAmountDouble) throws RemoteException, MalformedURLException {
        try {
            logger.info("#traceId={}# [IN][approveSalesOrder] params: salesOrderNo={}, couponAmountDouble={}, prepaidAmountDouble={}, cashAmountDouble={}",
                    rpcHeader.traceId, salesOrderNo, couponAmountDouble, prepaidAmountDouble);


            SalesOrder salesOrder = null;
            int update = 0;
            int maxRetryTimes = 6;
            long couponAmount = Math.round(couponAmountDouble * HUNDRED);
            long prepaidAmount = Math.round(prepaidAmountDouble * HUNDRED);
//            一、查找到订单
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                long totalAmount = salesOrder.getTotalOrderAmount() - couponAmount - prepaidAmount - salesOrder.getPrestoredAmount();

                if (couponAmount < 0 || prepaidAmount < 0 || totalAmount < 0) {
                    logger.info("#traceId={}# [OUT]: can NOT approve sales order. salesOrderNo={}, couponAmount={}, prepaidAmount={}, totalPaid={}", rpcHeader.traceId, salesOrderNo, couponAmount, prepaidAmount, totalAmount);
                    return RpcResult.newFailureResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                }
                // 查询账户余额 如果使用现金>现金余额，信息返回页面
                AccountAmount account = distributorAccountService.getDistributorAccountAmount(rpcHeader, salesOrder.getCurrencyCode(), salesOrder.getProjectId(), salesOrder.getDistributorId());
                if (account.getCouponAmount() < couponAmount) {
                    //返利余额不足
                    logger.info("#traceId={}# [OUT]: can NOT approve sales order. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                    return RpcResult.newFailureResult(DISTRIBUTOR_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), DISTRIBUTOR_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }
                if (account.getPrepaidAmount() < prepaidAmount) {
                    //代垫余额不足
                    logger.info("#traceId={}# [OUT]: can NOT approve sales order. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                    return RpcResult.newFailureResult(DISTRIBUTOR_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), DISTRIBUTOR_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
                }

                // 更改订单状态，各种金额
                Integer settlementMode = salesOrder.getSettlementMode();
                if (settlementMode == SalesOrderSettlementModeEnum.CREDIT.getStatus() || totalAmount == 0) {
                    // 如果订单结算方式为“信用（账期）”,或者应收款金额为0，修改订单状态为“已付款/待发货”
                    salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.PAID.getStatus());
                } else if (settlementMode == SalesOrderSettlementModeEnum.NORMAL.getStatus()) {
                    // 如果订单结算方式为“普通（先款后货）”，修改订单状态为“已审核/待收款”
                    salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.CHECKED.getStatus());
                }
                salesOrder.setCouponAmount(couponAmount);
                salesOrder.setPrepaidAmount(prepaidAmount);
                // 实付金额 = 订单总金额 - 返利 - 代垫 - 预存
                salesOrder.setCashAmount(totalAmount);
                salesOrder.setOrderCheckTime(new Date());
                // 操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "审核");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                salesOrder.setTracelog(traceLogs);
                update = salesOrderDao.update(salesOrder);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to approve salesOrder. salesOrderNo={}, couponAmount={}, prepaidAmount={}", salesOrderNo, couponAmount, prepaidAmount);
                throw new RuntimeException();
            }

            if (couponAmount > 0 || prepaidAmount > 0) {
                //如果 返利 或者 代垫 大于0，调用支付接口
                salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountService.payForSalesOrder(rpcHeader,
                        salesOrder.getCurrencyCode(), salesOrder.getDistributorId(), salesOrder.getProjectId(), couponAmount, prepaidAmount, 0, salesOrderNo, new Date());
                if (rpcResult.getSuccess()) {
                    salesOrder.setCouponFlowNo(rpcResult.getResult().getCouponFlowNo());
                    salesOrder.setPrepaidFlowNo(rpcResult.getResult().getPrepaidFlowNo());
                    update = salesOrderDao.update(salesOrder);
                } else {
                    logger.error("sales order update coupon/prepaid FAILED! salesOrderNo={}", salesOrderNo);
                }
            }

            // 二、所有商品明细，添加返利、代垫，使用金额
            Long totalOrderAmount = salesOrder.getTotalOrderAmount();
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(salesOrderNo);
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                Integer quantity = salesOrderItem.getTotalQuantity();
                // 使用返利金额 = 返利使用总金额*商品小计金额/订单总金额
                long wholesalePrice = salesOrderItem.getWholesalePrice();
                long itemCouponAmount = couponAmount * salesOrderItem.getTotalOrderAmount() / totalOrderAmount;
                // 使用代垫金额 = 代垫使用总金额*商品小计金额/订单总金额
                salesOrderItem.setUsedCouponAmount(itemCouponAmount);
                long itemPrepaidAmount = prepaidAmount * salesOrderItem.getTotalOrderAmount() / totalOrderAmount;
                salesOrderItem.setUsedPrepaidAmount(itemPrepaidAmount);
                // 操作日志
                TraceLog traceLog1 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "审核");
                String appendTraceLog = TraceLogUtil.appendTraceLog(salesOrderItem.getTracelog(), traceLog1);
                salesOrderItem.setTracelog(appendTraceLog);
                int update1 = salesOrderItemDao.update(salesOrderItem);
                if (update1 != 1) {
                    logger.error("FAILED to approve salesOrder item. salesOrderNo={}, productCode={},couponAmount={}, prepaidAmount={}", salesOrderNo, salesOrderItem.getProductCode(), itemCouponAmount, itemPrepaidAmount);
                }
            }

            // 三、插入收款确认数据
            cashConfirmService.createSalesOrderCashConfirmOrder(rpcHeader, salesOrderNo,
                    salesOrder.getDistributorId(),
                    salesOrder.getDistributorName(),
                    salesOrder.getProjectId(),
                    salesOrder.getProjectName(),
                    salesOrder.getCurrencyCode(),
                    salesOrder.getCashAmount(),
                    salesOrder.getCreateTime()
            );


            // 四、调用预售接口
            // 修改预售占用数量->已售数量
            Long projectId = salesOrder.getProjectId();
            Long distributorId = salesOrder.getDistributorId();
            Date createTime = salesOrder.getCreateTime();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                String productCode = salesOrderItem.getProductCode();
                Integer quantity = salesOrderItem.getTotalQuantity();
                RpcResult rpcResult = planSaleItemService.updateSoldQuantity(rpcHeader, projectId + "", distributorId + "", productCode, createTime, quantity);
                if (!rpcResult.getSuccess()) {
                    logger.error("FAILED to update planItem quantity. productCode={}", productCode);
                }
            }

            logger.info("#traceId={}# [OUT]: approve sales order success. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }


    @Override
    public RpcResult cancelSalesOrderByDistributor(RpcHeader rpcHeader, String salesOrderNo) {
        try {
            logger.info("#traceId={}# [IN][cancelSalesOrderByDistributor] params: salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            SalesOrder salesOrder = null;
            int maxRetryTimes = 6;
            int update;
            while (maxRetryTimes-- > 0) {
                // 查找到订单，如果订单状态不为“待审核”，则无法修改
                salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
                if (SalesOrderStatusEnum.INIT.getStatus() != salesOrderStatus) {
                    logger.info("#traceId={}# [OUT]: fail, can NOT cancel!", rpcHeader.traceId);
                    return RpcResult.newFailureResult(ORDER_STATUS_CAN_NOT_MODIFY.getErrorCode(), ORDER_STATUS_CAN_NOT_MODIFY.getMessage());
                }
                salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.CANCELED.getStatus());
                // 操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                salesOrder.setTracelog(traceLogs);
                update = salesOrderDao.update(salesOrder);
                if (update == 1) {
                    // 如果可以修改，修改订单状态，退换客户已使用的预存金额
                    break;
                }
            }
            // 如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to cancel salesOrder. salesOrderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to update salesOrder");
            }

            if (salesOrder.getPrestoredAmount() > 0) {
                RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountService.returnForSalesReturnOrder(rpcHeader,
                        salesOrder.getCurrencyCode(), salesOrder.getDistributorId(),
                        salesOrder.getProjectId(), 0, 0,
                        salesOrder.getPrestoredAmount(), salesOrderNo, new Date());
                if (rpcResult.getSuccess()) {
                    salesOrder.setReturnCashFlowNo(rpcResult.getResult().getCashFlowNo());
                    salesOrderDao.update(salesOrder);
                } else {
                    logger.error("salesOrder update cash account FAILED ! salesOrderNo={}, cashAmount={}", salesOrderNo, salesOrder.getPrestoredAmount());
                }
            }

            //退还预售数量
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(salesOrderNo);
            Long projectId = salesOrder.getProjectId();
            Long distributorId = salesOrder.getDistributorId();
            Date createTime = salesOrder.getCreateTime();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                String productCode = salesOrderItem.getProductCode();
                Integer quantity = salesOrderItem.getTotalQuantity();
                update = salePlanItemDao.cancelCustomerOrder(projectId + "", distributorId + "", productCode, salesOrder.getCreateTime(), quantity, 0);
                if (update != 1) {
                    logger.error("FAILED to update planItem quantity. productCode={}", productCode);
                }
            }
            logger.info("#traceId={}# [OUT]: cancel success. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public RpcResult rejectSalesOrder(RpcHeader rpcHeader, String salesOrderNo) {
        try {
            logger.info("#traceId={}# [IN][rejectSalesOrder] params: salesOrderNo={}", rpcHeader.traceId, salesOrderNo);

            SalesOrder salesOrder;

            int update;
            int maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                // 如果销售单状态不为“待审核”，无法驳回
                if (SalesOrderStatusEnum.INIT.getStatus() != salesOrder.getSalesOrderStatus()) {
                    logger.info("#traceId={}# [OUT]: order can NOT reject! salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                    return RpcResult.newFailureResult(ORDER_STATUS_CAN_NOT_MODIFY.getErrorCode(), ORDER_STATUS_CAN_NOT_MODIFY.getMessage());
                }
                // 一、查找到订单，更改订单状态，各种金额
                salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.REJECTED.getStatus());
                salesOrder.setRejectTime(new Date());
                // 操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "驳回");
                String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                salesOrder.setTracelog(traceLogs);

                // 二、退回返利代垫账户中冻结的金额
                //如果更新账户成功，更新销售单
                update = salesOrderDao.update(salesOrder);
                if (update == 1) {
                    break;
                }
            }
            //如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to reject SalesOrder. salesOrderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to reject SalesOrder");
            }
            salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
            RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountService.returnForSalesReturnOrder(rpcHeader, salesOrder.getCurrencyCode(), salesOrder.getDistributorId(), salesOrder.getProjectId(), 0, 0, salesOrder.getPrestoredAmount(), salesOrderNo, new Date());
            if (rpcResult.getSuccess()) {
                salesOrder.setReturnCashFlowNo(rpcResult.getResult().getCashFlowNo());
                update = salesOrderDao.update(salesOrder);
            }else {
                logger.error("FAILED to return cash. salesOrderNo={}", salesOrderNo);
            }

            //退还预售数量
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(salesOrderNo);
            Long projectId = salesOrder.getProjectId();
            Long distributorId = salesOrder.getDistributorId();
            Date createTime = salesOrder.getCreateTime();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                String productCode = salesOrderItem.getProductCode();
                Integer quantity = salesOrderItem.getTotalQuantity();
                update = salePlanItemDao.cancelCustomerOrder(projectId + "", distributorId + "", productCode, salesOrder.getCreateTime(), quantity, 0);
                if (update != 1) {
                    logger.error("FAILED to update planItem quantity. productCode={}", productCode);
                }
            }
            logger.info("#traceId={}# [OUT]: reject sales order success. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult cancelSalesOrderApprove(RpcHeader rpcHeader, String salesOrderNo) {
        try {
            logger.info("#traceId={}# [IN][cancelSalesOrderApprove] params: salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            SalesOrder salesOrder = null;
            int maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                // 找到销售单
                salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                // 如果销售单状态不为“待收款” 或 结算方式为“账期付款”，无法取消审核
                if (SalesOrderStatusEnum.CHECKED.getStatus() != salesOrder.getSalesOrderStatus() || SalesOrderSettlementModeEnum.CREDIT.getStatus() == salesOrder.getSettlementMode()) {
                    logger.info("#traceId={}# [OUT]: order can NOT cancel! salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                    return RpcResult.newFailureResult(ORDER_STATUS_CAN_NOT_MODIFY.getErrorCode(), ORDER_STATUS_CAN_NOT_MODIFY.getMessage());
                }

                // 如果销售模块修改成功，修改销售单状态
                salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.CANCELED.getStatus());
                TraceLog newTracelog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消审核");
                String traceLogJson = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), newTracelog);
                salesOrder.setTracelog(traceLogJson);
                int update = salesOrderDao.update(salesOrder);
                if (update == 1) {
                    break;
                }
            }
            //如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to cancel salesOrder. orderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to cancel salesOrder");
            }

            salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
            // 使用的返利、代垫、预存金额 通知给销售模块
            RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountService.returnForSalesReturnOrder(rpcHeader, salesOrder.getCurrencyCode(), salesOrder.getDistributorId(), salesOrder.getProjectId(), salesOrder.getCouponAmount(), salesOrder.getPrepaidAmount(), salesOrder.getPrestoredAmount(), salesOrderNo, new Date());
            //记录流水号
            if (rpcResult.getSuccess()) {
                salesOrder.setReturnCashFlowNo(rpcResult.getResult().getCashFlowNo());
                salesOrder.setReturnCouponFlowNo(rpcResult.getResult().getCouponFlowNo());
                salesOrder.setReturnPrepaidFlowNo(rpcResult.getResult().getPrepaidFlowNo());
                salesOrderDao.update(salesOrder);
            }
            cashConfirmService.cancelCashConfirm(rpcHeader, salesOrderNo, salesOrder.getPrestoredAmount(), salesOrder.getCouponAmount(), salesOrder.getPrepaidAmount());

            // 退回已售数量 -> 占用数量
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(salesOrderNo);
            Long projectId = salesOrder.getProjectId();
            Long distributorId = salesOrder.getDistributorId();
            Date createTime = salesOrder.getCreateTime();
            for (SalesOrderItem salesOrderItem : salesOrderItems) {
                String productCode = salesOrderItem.getProductCode();
                Integer quantity = salesOrderItem.getTotalQuantity();
                int update = salePlanItemDao.cancelCustomerOrder(projectId + "", distributorId + "", productCode, salesOrder.getCreateTime(), 0, quantity);
                if (update != 0) {
                    logger.error("FAILED to update planItem quantity. productCode={}", productCode);
                }
            }
            logger.info("#traceId={}# [OUT]: cancel order success. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public RpcResult confirmSalesOrderAmount(RpcHeader rpcHeader, String salesOrderNo) {
        try {
            logger.info("#traceId={}# [IN][confirmSalesOrderAmount] params: salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            int maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                // 一、查找到订单，更改订单状态，各种金额
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                if (salesOrder.getPaidTime() != null) {
                    logger.info("#traceId={}# [OUT]: order already confirmed. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
                    return RpcResult.newSuccessResult();
                }
                Integer settlementMode = salesOrder.getSettlementMode();
                if (settlementMode == SalesOrderSettlementModeEnum.NORMAL.getStatus()) {
                    // 如果订单结算方式为“普通（先款后货）”，修改订单状态为“已付款/待发货”
                    salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.PAID.getStatus());
                }
                salesOrder.setPaidTime(new Date());
                TraceLog newTracelog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "确认收款");
                String traceLogJson = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), newTracelog);
                salesOrder.setTracelog(traceLogJson);
                int update = salesOrderDao.update(salesOrder);
                if (update == 1) {
                    break;
                }
            }
            //如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to confirm salesOrder amount. orderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to confirm salesOrder amount");
            }
            logger.info("#traceId={}# [OUT]: confirm sales order amount success. salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public PageInfo<SalesOrderInfo> searchOrderListWithPage(RpcHeader rpcHeader, String salesOrderNo, String productCode, Integer status, Date dateStart, Date dateEnd, Integer page, Integer pageSize) {
        logger.info("#traceId={}# [IN][searchOrderList] params: salesOrderNo={}, productCode={}, status={}, dateStart={}, dateEnd={}, page={}, pageSize={} ",
                rpcHeader.traceId, salesOrderNo, productCode, status, dateStart, page, pageSize);
        PageInfo<SalesOrderInfo> pageInfo;
        try {
            // 查询用户信息
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader, rpcHeader.getUid());
            PageHelper.startPage(page, pageSize);
            List<SalesOrder> salesOrderList = salesOrderDao.selectListSelectively(distributorPortalUser.getProjectId(), distributorPortalUser.getDistributorId(), salesOrderNo, productCode, dateStart, dateEnd, status);
            ArrayList<SalesOrderInfo> salesOrderInfos = new ArrayList<>();
            for (SalesOrder salesOrder : salesOrderList) {
                SalesOrderInfo salesOrderInfo = new SalesOrderInfo();
                salesOrderInfo.setSalesOrderNo(salesOrder.getSalesOrderNo());
                Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
                salesOrderInfo.setSalesOrderStatus(salesOrderStatus);
                salesOrderInfo.setDistributorName(salesOrder.getDistributorName());
                salesOrderInfo.setPaidTime(salesOrder.getPaidTime());
                salesOrderInfo.setCreateTime(salesOrder.getCreateTime());
                salesOrderInfo.setPaidTime(salesOrder.getPaidTime());
                salesOrderInfo.setTotalOrderAmountDouble(salesOrder.getTotalOrderAmount() / FXConstant.HUNDRED_DOUBLE);
                salesOrderInfo.setDiscountAmountTotal(salesOrder.getDiscountAmount() / FXConstant.HUNDRED_DOUBLE);
                salesOrderInfo.setCouponAmountDouble(salesOrder.getCouponAmount() / FXConstant.HUNDRED_DOUBLE);
                salesOrderInfo.setPrepaidAmountDouble(salesOrder.getPrepaidAmount() / FXConstant.HUNDRED_DOUBLE);
                salesOrderInfo.setTotalQuantity(salesOrder.getTotalQuantity());
                salesOrderInfo.setUnhandledQuantity(salesOrder.getUnhandledQuantity());
                salesOrderInfos.add(salesOrderInfo);
            }
            pageInfo = new PageInfo<SalesOrderInfo>(salesOrderInfos);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
        return pageInfo;
    }

    @Override
    public List<SalesOrderInfo> searchOrderList(RpcHeader rpcHeader, String purchaseNo, String productCode, Integer status, Date dateStart, Date dateEnd) {
        logger.info("#traceId={}# [IN][searchOrderList] params:  purchaseNo={}, productCode={}, status={}, dateStart={}, dateEnd={} ",
                rpcHeader.traceId, purchaseNo, productCode, status, dateStart);
        ArrayList<SalesOrderInfo> salesOrderInfos = new ArrayList<>();
        try {
            // 查询用户信息
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader, rpcHeader.getUid());
            List<SalesOrder> salesOrderList = salesOrderDao.selectListSelectively(distributorPortalUser.getProjectId(), distributorPortalUser.getDistributorId(), purchaseNo, productCode, dateStart, dateEnd, status);
            for (SalesOrder salesOrder : salesOrderList) {
                SalesOrderInfo salesOrderInfo = new SalesOrderInfo();
                salesOrderInfo.setSalesOrderNo(salesOrder.getSalesOrderNo());
                Integer salesOrderStatus = salesOrder.getSalesOrderStatus();
                salesOrderInfo.setSalesOrderStatus(salesOrderStatus);
                salesOrderInfo.setDistributorName(salesOrder.getDistributorName());
                salesOrderInfo.setPaidTime(salesOrder.getPaidTime());
                salesOrderInfo.setCreateTime(salesOrder.getCreateTime());
                salesOrderInfo.setPaidTime(salesOrder.getPaidTime());
                salesOrderInfo.setTotalOrderAmountDouble(1.0 * salesOrder.getTotalOrderAmount() / HUNDRED);
                salesOrderInfo.setDiscountAmountTotal(1.0 * salesOrder.getDiscountAmount() / HUNDRED);
                salesOrderInfo.setTotalQuantity(salesOrder.getTotalQuantity());
                salesOrderInfo.setUnhandledQuantity(salesOrder.getUnhandledQuantity());
                salesOrderInfos.add(salesOrderInfo);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
        return salesOrderInfos;
    }

    @Override
    public boolean whetherOutbound(RpcHeader rpcHeader, String salesOrderNo) {
        logger.info("#traceId={}# [IN][whetherOutbound] params: salesOrderNo={}",
                rpcHeader.traceId, salesOrderNo);
        try {
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
            int unhandledQuantity = salesOrder.getUnhandledQuantity();
            int totalQuantity = salesOrder.getTotalQuantity();
            // 如果总数量==未处理数量，则未发货，否则已发货
            if (totalQuantity == unhandledQuantity) {
                logger.info("#traceId={}# [OUT]:get status success. whetherOutbound=false", rpcHeader.traceId);
                return false;
            } else {
                logger.info("#traceId={}# [OUT]:get status success. whetherOutbound=true", rpcHeader.traceId);
                return true;
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
    }

    @Override
    public RpcResult cancelReceivedCash(RpcHeader rpcHeader, String salesOrderNo) {
        logger.info("#traceId={}# [IN][cancelReceivedCash] params: salesOrderNo={}",
                rpcHeader.traceId, salesOrderNo);
        try {
            int maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                int unhandledQuantity = salesOrder.getUnhandledQuantity();
                int totalQuantity = salesOrder.getTotalQuantity();
                // 如果总数量==未处理数量，则未发货，否则已发货
                if (totalQuantity != unhandledQuantity) {
                    logger.info("#traceId={}# [OUT]:can NOT cancel", rpcHeader.traceId);
                    return RpcResult.newFailureResult();
                } else {
                    if (salesOrder.getSettlementMode() != SalesOrderSettlementModeEnum.CREDIT.getStatus()) {
                        // 如果不是“账期结算”，修改订单状态为待收款
                        salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.CHECKED.getStatus());
                    }
                    salesOrder.setPaidTime(null);
                    TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消收款");
                    String appendTraceLog = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                    salesOrder.setTracelog(appendTraceLog);
                    int update = salesOrderDao.update(salesOrder);
                    if (update == 1) {
                        logger.info("#traceId={}# [OUT]:change status success", rpcHeader.traceId);
                        return RpcResult.newSuccessResult();
                    }
                }
            }
            //如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to cancel salesOrder. orderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to cancel salesOrder");
            }
            return RpcResult.newFailureResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search orderList error!", e);
        }
    }

}
