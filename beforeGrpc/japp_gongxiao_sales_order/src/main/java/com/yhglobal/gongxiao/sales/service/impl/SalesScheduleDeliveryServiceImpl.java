package com.yhglobal.gongxiao.sales.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.eas.SaleOutOrder;
import com.yhglobal.gongxiao.eas.SaleOutOrderItem;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorService;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.inventory.bo.InventoryBatch;
import com.yhglobal.gongxiao.inventory.service.InventoryBatchService;
import com.yhglobal.gongxiao.payment.prepaid.service.YhglobalPrepaidService;
import com.yhglobal.gongxiao.payment.service.CashConfirmService;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesOutboundOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesScheduleDeliveryDao;
import com.yhglobal.gongxiao.sales.dto.EasOutboundItem;
import com.yhglobal.gongxiao.sales.dto.EasOutboundOrderRequest;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.sales.model.SalesOrderSyncEnum;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrder;
import com.yhglobal.gongxiao.sales.model.SalesScheduleDelivery;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanOutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.yhglobal.gongxiao.constant.ErrorCode.EAS_ID_EMPTY;
import static com.yhglobal.gongxiao.constant.ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER;
import static com.yhglobal.gongxiao.constant.FXConstant.MILLION;
import static com.yhglobal.gongxiao.constant.FXConstant.TAX_RATE;
import static com.yhglobal.gongxiao.id.BizNumberType.SALES_SCHEDULE_ORDER_NO;
import static com.yhglobal.gongxiao.sales.model.SalesOrderSyncEnum.UNHANDLED;

/**
 * 预约发货Service实现类
 *
 * @Author: 葛灿
 */
@Service(timeout = 6000)
public class SalesScheduleDeliveryServiceImpl implements SalesScheduleDeliveryService {

    private static Logger logger = LoggerFactory.getLogger(SalesScheduleDeliveryServiceImpl.class);

    @Autowired
    private SalesScheduleDeliveryDao salesScheduleDeliveryDao;
    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOutboundOrderDao salesOutboundOrderDao;
    @Reference
    private OutboundService outboundService;
    @Reference
    InventoryBatchService inventoryBatchService;
    @Reference
    YhglobalPrepaidService yhglobalPrepaidService;
    @Reference
    SupplierAccountService supplierAccountService;
    @Reference
    CashConfirmService cashConfirmService;
    @Reference
    ProjectService projectService;
    @Reference
    DistributorService distributorService;
    @Reference
    WarehouseService warehouseService;
    @Reference
    ProductService productService;

    @Override
    public RpcResult createScheduleOrder(RpcHeader rpcHeader, String projectId,
                                         String salesOrderNo, List<InventoryBatch> productInfo,
                                         Date arrivalDate) throws Exception {
        try {
            logger.info("#traceId={}# [IN][createScheduleOrder] params: salesOrderNo={},warehouseId={},warehouseName={}," +
                    " productInfo={}", rpcHeader.traceId, salesOrderNo, productInfo);

            HashMap<String, Integer> countMap = new HashMap<>();
            for (InventoryBatch inventoryBatch : productInfo) {
                String productCode = inventoryBatch.getProductCode();
                int scheduleQuantity = inventoryBatch.getScheduleQuantity();
                if (countMap.get(productCode) == null) {
                    countMap.put(productCode, scheduleQuantity);
                } else {
                    countMap.put(productCode, countMap.get(productCode) + scheduleQuantity);
                }
            }
            //数据校验
            for (String productCode : countMap.keySet()) {
                SalesOrderItem item = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(salesOrderNo, productCode);
                int unhandledQuantity = item.getUnhandledQuantity();
                Integer scheduleQuantity = countMap.get(productCode);
                if (scheduleQuantity > unhandledQuantity) {
                    //如果数据版本不一致
                    logger.info("#traceId={}# [OUT]: schedule FAILED !", rpcHeader.traceId);
                    return RpcResult.newFailureResult(OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode(), OVERTAKE_MAX_ALLOCATE_NUMBER.getMessage());
                }
            }

            //一、通知仓库
            int totalQuantity = 0;
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
            ArrayList<PlanOutboundOrderItem> planOutboundOrderItems = new ArrayList<>();
            //转换模型(数据校验)
            for (InventoryBatch record : productInfo) {
                totalQuantity += record.getScheduleQuantity();
                PlanOutboundOrderItem planOutboundOrderItem = new PlanOutboundOrderItem();
                planOutboundOrderItem.setProductId(record.getProductId());
                planOutboundOrderItem.setInventoryType(record.getInventoryStatus());
                planOutboundOrderItem.setBatchNo(record.getBatchNo());
                planOutboundOrderItem.setSalesOrderNo(salesOrderNo);
                planOutboundOrderItem.setWarehouseId(record.getWarehouseId());
                planOutboundOrderItem.setWarehouseName(record.getWarehouseName());
                planOutboundOrderItem.setProductId(record.getProductId());
                planOutboundOrderItem.setProductCode(record.getProductCode());
                planOutboundOrderItem.setProductName(record.getProductName());
                planOutboundOrderItem.setTotalQuantity(record.getScheduleQuantity());
                planOutboundOrderItem.setPurchaseType(record.getPurchaseType());
                planOutboundOrderItem.setInventoryType(record.getInventoryStatus());
                planOutboundOrderItems.add(planOutboundOrderItem);
                if (record.getScheduleQuantity() > record.getOrderUnhandledQuantity() || record.getScheduleQuantity() > record.getInventoryBatchAmount()) {
                    //如果 预约数量>未发货数量 || 预约数量>库存数量 ,预约失败
                    logger.info("#traceId={}# [OUT]: schedule FAILED !", rpcHeader.traceId);
                    return RpcResult.newFailureResult(OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode(), OVERTAKE_MAX_ALLOCATE_NUMBER.getMessage());
                }
            }
            String saleUniqueNo = DateTimeIdGenerator.nextId(BizNumberType.SALE_UNIQUE_NO);
            //返回<出库单号,出库单商品明细>
            Map<String, List<OutboundOrderItem>> outboundMap = outboundService.createOutboundOrder2(rpcHeader,
                    salesOrder.getMarketingChannel() + "",
                    WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode(),
                    projectId,
                    salesOrder.getDistributorId() + "",
                    salesOrderNo,
                    salesOrder.getRecipientName(),
                    salesOrder.getRecipientMobile(),
                    salesOrder.getShippingAddress(),
                    salesOrder.getProvinceName(),
                    salesOrder.getCityName(),
                    salesOrder.getShippingMode(), "logisticsCompanyName", "logisticsNo", "note", totalQuantity,
                    planOutboundOrderItems, "sales", saleUniqueNo, arrivalDate);


            //二、新建预约出库记录
            String schduleOrderNo = DateTimeIdGenerator.nextId(SALES_SCHEDULE_ORDER_NO);
            SalesScheduleDelivery salesScheduleDelivery = new SalesScheduleDelivery();
            //创建预约发货单号
            salesScheduleDelivery.setScheduleOrderNo(schduleOrderNo);
            //填充字段
            salesScheduleDelivery.setUniqueNo(saleUniqueNo);
            salesScheduleDelivery.setScheduleStatus(0);
            salesScheduleDelivery.setSyncToGongxiaoWarehouseFlag(false);
            salesScheduleDelivery.setSalesOrderId(salesOrderNo);
            salesScheduleDelivery.setShippingMode(0);
            salesScheduleDelivery.setDataVersion(0L);
            salesScheduleDelivery.setTotalQuantity(totalQuantity);
            salesScheduleDelivery.setOnGoingOutboundOrderInfo(JSON.toJSONString(outboundMap.keySet()));
            salesScheduleDelivery.setOutboundedOrderInfo("[]");
            salesScheduleDelivery.setSignedOrderInfo("[]");
            TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
            ArrayList<TraceLog> traceLogs = new ArrayList<>();
            traceLogs.add(traceLog);
            salesScheduleDelivery.setTracelog(JSON.toJSONString(traceLogs));
            salesScheduleDelivery.setCreateTime(new Date());
            //插入数据库
            salesScheduleDelivery.setProductInfo(JSON.toJSONString(productInfo));
            int insert = salesScheduleDeliveryDao.insert(salesScheduleDelivery);

            //插入出库单记录
            for (String outboundOrderNo : outboundMap.keySet()) {
                //出库单-数量总计
                int quantityInOutboundOrder = 0;
                String warehouseId = null;
                List<OutboundOrderItem> outboundItems = outboundMap.get(outboundOrderNo);
                for (OutboundOrderItem item : outboundItems) {
                    quantityInOutboundOrder += item.getTotalQuantity();
                    warehouseId = item.getWarehouseId();
                }
                SalesOutboundOrder salesOutboundOrder = new SalesOutboundOrder();
                salesOutboundOrder.setWarehouseId(warehouseId);
                salesOutboundOrder.setOutboundOrderNo(outboundOrderNo);
                salesOutboundOrder.setSalesOrderNo(salesOrderNo);
                salesOutboundOrder.setTotalQuantity(quantityInOutboundOrder);
                salesOutboundOrder.setOrderStatus(1);
                salesOutboundOrder.setSyncToTms(SalesOrderSyncEnum.INIT.getStatus());
                salesOutboundOrder.setExpectedArrivalTime(arrivalDate);
                salesOutboundOrder.setItems(JSON.toJSONString(outboundItems));
                salesOutboundOrder.setCreatedById(rpcHeader.getUid());
                salesOutboundOrder.setCreatedByName(rpcHeader.getUsername());
                salesOutboundOrderDao.insert(salesOutboundOrder);
            }


            //三、更新销售单商品明细
            for (InventoryBatch record : productInfo) {
                //每条明细的出库单信息，添加预约发货单号
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(salesOrderNo, record.getProductCode());
                record.setEasProductCode(salesOrderItem.getEasCode());
                int unhandledQuantity = salesOrderItem.getUnhandledQuantity() - record.getScheduleQuantity();
                salesOrderItem.setUnhandledQuantity(unhandledQuantity);
                String ongoingOutboundOrderInfo = salesOrderItem.getOngoingOutboundOrderInfo();
                List<String> itemOutboundOrderIno = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
                itemOutboundOrderIno.addAll(outboundMap.keySet());
                salesOrderItem.setOngoingOutboundOrderInfo(JSON.toJSONString(itemOutboundOrderIno));
                int update = salesOrderItemDao.update(salesOrderItem);
                if (update != 1) {
                    logger.error("FAILED to update salesOrder item quantity. salesOrderNo={}, productCode={}, unhandledQuantity={}", salesOrderNo, salesOrderItem.getProductCode(), unhandledQuantity);
                }
            }

            //四、更新销售单信息
            // 如果是销售单第一次发货，更改状态为待同步eas
            if (salesOrder.getUnhandledQuantity() == salesOrder.getTotalQuantity()) {
                salesOrder.setSyncEas(UNHANDLED.getStatus());
            }
            //如果销售单商品全部发货，修改状态为“已通知仓库”
            int totalUnhandledQuantity = salesOrder.getUnhandledQuantity() - totalQuantity;
            salesOrder.setUnhandledQuantity(totalUnhandledQuantity);
            if (totalUnhandledQuantity == 0) {
                salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.INFORMED.getStatus());
                salesOrder.setInformWarehouseTime(new Date());
            }
            String ongoingOutboundOrderInfo = salesOrder.getOngoingOutboundOrderInfo();
            //出库单json中，添加预约发货单号
            List<String> originalScheduleOrderNoList = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
            originalScheduleOrderNoList.addAll(outboundMap.keySet());
            salesOrder.setOngoingOutboundOrderInfo(JSON.toJSONString(originalScheduleOrderNoList));
            //操作
            TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建发货单");
            String traceLogList2 = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog2);
            salesOrder.setTracelog(traceLogList2);
            int update = salesOrderDao.update(salesOrder);

            logger.info("#traceId={}# [OUT]: create schedule order success", rpcHeader.traceId);

            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<InventoryBatch> selectSaleScheduleProductList(RpcHeader rpcHeader, String salesOrderNo, String productCodes) {
        try {
            logger.info("#traceId={}# [IN][selectSaleScheduleProductList] params: salesOrderNo={}", rpcHeader.traceId, salesOrderNo);
            List<InventoryBatch> inventoryBatches = new LinkedList<>();
            //查询销售商品信息
            List<SalesOrderItem> list = salesOrderItemDao.selectListBySalesOrderNo(salesOrderNo);
            for (SalesOrderItem item : list) {
                if (item.getUnhandledQuantity() == 0) {
                    continue;
                }
                InventoryBatch inventoryBatch = new InventoryBatch();
                inventoryBatch.setProductName(item.getProductName());
                inventoryBatch.setProductCode(item.getProductCode());
                inventoryBatch.setOrderTotalQuantity(item.getTotalQuantity());
                inventoryBatch.setOrderUnhandledQuantity(item.getUnhandledQuantity());
                inventoryBatch.setEasProductCode(item.getEasCode());
                inventoryBatches.add(inventoryBatch);
            }
            logger.info("#traceId={}# [OUT]: get schedule order list success", rpcHeader.traceId);
            return inventoryBatches;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult submitOutboundOrderToTms(RpcHeader rpcHeader, String outboundOrderNo) {
        try {
            logger.info("#traceId={}# [IN][submitOutboundOrderToTms]: outboundOrderNo={}", rpcHeader.traceId, outboundOrderNo);
            //修改出库单的状态为 待同步tms
            int maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(outboundOrderNo);
                salesOutboundOrder.setSyncToTms(UNHANDLED.getStatus());
                int update = salesOutboundOrderDao.update(salesOutboundOrder);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update outboundOrder. outboundOrderNo={}", outboundOrderNo);
                throw new RuntimeException();
            }
            logger.info("#traceId={}# [OUT]: update order info success", rpcHeader.traceId);
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void outboundOrderFinished(RpcHeader rpcHeader, String outboundOrderNo) throws MalformedURLException, RemoteException {
        try {
            logger.info("#traceId={}# [IN][outboundOrderFinished]: outboundOrderNo={}", rpcHeader.traceId, outboundOrderNo);

            int update;
            int maxRetryTimes = 6;
            SalesOrder salesOrder = null;
            TraceLog traceLog;
            String traceLogList;

            //一、查到出库单，找到对应的销售单明细、销售单

            //更新出库单
            SalesOutboundOrder outboundOrder = null;
            while (maxRetryTimes-- > 0) {
                outboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(outboundOrderNo);
                outboundOrder.setOrderStatus(2);
                outboundOrder.setSyncToEas(UNHANDLED.getStatus());
                outboundOrder.setOutboundFinishedTime(new Date());
                update = salesOutboundOrderDao.update(outboundOrder);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update outboundOrder. outboundOrderNo={}", outboundOrderNo);
                throw new RuntimeException("FAILED to update outboundOrder");
            }
            maxRetryTimes = 6;
            //更新销售单
            String salesOrderNo = outboundOrder.getSalesOrderNo();
            int totalQuantity = outboundOrder.getTotalQuantity();
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                List<String> onGoingOutboundOrderList = JSON.parseArray(salesOrder.getOngoingOutboundOrderInfo(), String.class);
                List<String> finishedOutboundOrderList = JSON.parseArray(salesOrder.getFinishedOutboundOrderInfo(), String.class);
                onGoingOutboundOrderList.remove(outboundOrderNo);
                finishedOutboundOrderList.add(outboundOrderNo);
                salesOrder.setOngoingOutboundOrderInfo(JSON.toJSONString(onGoingOutboundOrderList));
                salesOrder.setFinishedOutboundOrderInfo(JSON.toJSONString(finishedOutboundOrderList));
                int transitQuantity = salesOrder.getInTransitQuantity() + totalQuantity;
                salesOrder.setInTransitQuantity(transitQuantity);
                TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "出库");
                String traceLogList2 = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog2);
                //if 在途数量 = 总数量 ，订单状态 为 “已出库”，设置出库时间
                if (salesOrder.getUnhandledQuantity() == 0 && onGoingOutboundOrderList.size() == 0) {
                    salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.OUTBOUND.getStatus());
                    salesOrder.setOutBoundTime(new Date());
                    //增加操作日志
                    traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "出库完成");
                    traceLogList = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                    salesOrder.setTracelog(traceLogList);
                }
                update = salesOrderDao.update(salesOrder);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to update salesOrder. salesOrderNo={}", salesOrderNo);
            }

            //更新销售单明细
            String items = outboundOrder.getItems();
            List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(items, OutboundOrderItem.class);
            for (OutboundOrderItem outboundOrderItem : outboundOrderItems) {
                String productCode = outboundOrderItem.getProductCode();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(salesOrderNo, productCode);
                //进行中的出库单JSON中删除该单，已完成的出库单JSON中添加该单
                String ongoingOutboundOrderInfo = salesOrderItem.getOngoingOutboundOrderInfo();
                String finishedOutboundOrderInfo = salesOrderItem.getFinishedOutboundOrderInfo();
                List<String> ongoingScheduleOrderNoList = JSON.parseArray(ongoingOutboundOrderInfo, String.class);
                ongoingScheduleOrderNoList.remove(outboundOrderNo);
                salesOrderItem.setOngoingOutboundOrderInfo(JSON.toJSONString(ongoingScheduleOrderNoList));
                List<String> finishedScheduleOrderNoList = JSON.parseArray(finishedOutboundOrderInfo, String.class);
                finishedScheduleOrderNoList.add(outboundOrderNo);
                salesOrderItem.setFinishedOutboundOrderInfo(JSON.toJSONString(finishedScheduleOrderNoList));
                update = salesOrderItemDao.update(salesOrderItem);
                if (update != 1) {
                    logger.error("FAILED to update salesOrder item. outboundOrderNo={}, salesOrderNo={}, productCode={}", outboundOrderNo, salesOrderNo, productCode);
                }
            }

            logger.info("#traceId={}# [OUT]: update order info success", rpcHeader.traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public EasOutboundOrderRequest syncSalesOutboundOrderToEas(RpcHeader rpcHeader, String salesOrderNo, List<EasOutboundItem> easOutboundItems) throws MalformedURLException, RemoteException {
        logger.info("#traceId={}# [IN][syncSalesOutboundOrderToEas] salesOrderNo={}, easOutboundItems.size={} ", rpcHeader.traceId, salesOrderNo, easOutboundItems.size());
        EasOutboundOrderRequest easOutboundOrderRequest = new EasOutboundOrderRequest();
        try {
            SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
            if (salesOrder.getEasOrderId() == null) {
                logger.info("#traceId={}# [OUT]: no EAS salesOrder id", rpcHeader.traceId);
                easOutboundOrderRequest.setErrorCode(EAS_ID_EMPTY.getErrorCode());
                return easOutboundOrderRequest;
            }
            Project project = projectService.getByProjectId(rpcHeader, salesOrder.getProjectId() + "");
            Distributor distributor = distributorService.getDistributorByDistributorId(rpcHeader, salesOrder.getDistributorId() + "");
            ArrayList<SaleOutOrderItem> easSaleOutItemList = new ArrayList<>();
            double totalAmount = 0;
            int totalQuantity = 0;
            for (EasOutboundItem item : easOutboundItems) {
                SaleOutOrderItem easItem = new SaleOutOrderItem();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(salesOrder.getSalesOrderNo(), item.getProductCode());
                easItem.setTaxRate(TAX_RATE);
                easItem.setDiscount((salesOrderItem.getYhDiscountPercent() + salesOrderItem.getSupplierDiscountPercent()) / 10000.0);
                int quantity = item.getQuantity();
                totalQuantity += quantity;
                easItem.setNumber(quantity);
                double wholesalePrice = 1.0 * salesOrderItem.getSalesGuidePrice() / MILLION;
                easItem.setTaxPrice(wholesalePrice);
                totalAmount += wholesalePrice * quantity;
                String warehouseId = item.getWarehouseId();
                Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, warehouseId);
                easItem.setWarehouseId(warehouse.getEasWarehouseCode());
                if (item.isGoodProduct()) {
                    easItem.setLocationId("01"); // 良品
                } else {
                    easItem.setLocationId("02"); // 不良品
                }
                if (item.isGift()) {
                    easItem.setLocationId("05"); // 赠品
                }
                easItem.setLot(item.getBatchNo());
                easItem.setSourceBillId(salesOrder.getEasOrderId());
                easItem.setSourceOrderNumber(salesOrder.getEasOrderNo());
                easItem.setSourceItemBillId(salesOrderItem.getEntryId());
                ProductBasic productBasic = productService.getByProductCode(rpcHeader, item.getProductCode());
                easItem.setUnit(productBasic.getEasUnitCode());
                easItem.setMaterialId(productBasic.getEasCode());
                easItem.setCustomerId(distributor.getEasCustomerCode());
                easSaleOutItemList.add(easItem);
            }
            SaleOutOrder easSaleOutOrder = new SaleOutOrder();
            easSaleOutOrder.setProjectId(project.getEasProjectCode());
            easSaleOutOrder.setCustomerId(distributor.getEasCustomerCode());
            easSaleOutOrder.setCurrencyId("BB01");
            easSaleOutOrder.setTotalTaxAmount(totalAmount);
            //税额
            double percent = TAX_RATE / 100;
            double totalTax = totalAmount * (percent / (1 + percent));
            easSaleOutOrder.setTaxAmount(totalTax);
            easSaleOutOrder.setTotalQuantity(totalQuantity);
            easSaleOutOrder.setNumber(totalQuantity);
            easSaleOutOrder.setId(salesOrder.getEasOrderId());
            //回调

            easOutboundOrderRequest.setEasSalesOutboundOrder(easSaleOutOrder);
            easOutboundOrderRequest.setEasSalesOutboundItems(easSaleOutItemList);
            logger.info("#traceId={}# [OUT]: sync finished.", rpcHeader.traceId);
            return easOutboundOrderRequest;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public RpcResult outboundSigned(RpcHeader rpcHeader,
                                    String outboundOrderNo,
                                    String tmsOrderNo,
                                    String tmsRemark,
                                    String signedBy,
                                    String postedBy,
                                    String signedPhone,
                                    Date signedTime,
                                    String transporter) {
        try {
            logger.info("#traceId={}# [IN][outboundSigned]: outboundOrderNo={}, tmsOrderNo={}, tmsRemark={}, signedBy={}, postedBy={}, signedPhone={}, signedTime={}, transporter={}",
                    rpcHeader.traceId, outboundOrderNo, tmsOrderNo, tmsRemark, signedBy, postedBy, signedPhone, signedTime, transporter);
            int update;
            SalesOutboundOrder outboundOrder = null;
            int maxRetryTimes = 6;
            boolean firstTime = false;
            //一、查找出库单,修改状态
            while (maxRetryTimes-- > 0) {
                outboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(outboundOrderNo);
                firstTime = outboundOrder.getTmsSingedTime() == null;
                outboundOrder.setTmsOrdNo(tmsOrderNo);
                outboundOrder.setSignedRemark(tmsRemark);
                outboundOrder.setTmsSignedBy(signedBy);
                outboundOrder.setTmsPostedBy(postedBy);
                outboundOrder.setTmsSignedPhone(signedPhone);
                outboundOrder.setTmsSingedTime(signedTime);
                outboundOrder.setTransporter(transporter);
                update = salesOutboundOrderDao.update(outboundOrder);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("update outboundOrder FAILED. outboundOrderNo={}", outboundOrderNo);
                throw new RuntimeException("update outboundOrder FAILED!");
            }
            outboundService.sureSighIn(rpcHeader, transporter, outboundOrderNo, tmsOrderNo, tmsRemark, signedBy, postedBy, signedPhone, signedTime);
            // 如果已经签收过了,只用修改tms推回的收件人信息即可
            if (!firstTime) {
                logger.info("#traceId={}# [OUT]: already signed. update TMS info success.", rpcHeader.traceId);
                return RpcResult.newSuccessResult();
            }
            String salesOrderNo = outboundOrder.getSalesOrderNo();

            //二、修改销售单明细内的“送达数量”
            List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(salesOrderNo);
            //更新销售单明细
            String items = outboundOrder.getItems();
            List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(items, OutboundOrderItem.class);
            for (OutboundOrderItem outboundOrderItem : outboundOrderItems) {
                String productCode = outboundOrderItem.getProductCode();
                SalesOrderItem salesOrderItem = salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndProductCode(salesOrderNo, productCode);
                //如果货品编码一致
                int outboundQuantity = outboundOrderItem.getTotalQuantity();
                int deliveredQuantity = salesOrderItem.getDeliveredQuantity();
                salesOrderItem.setDeliveredQuantity(outboundQuantity + deliveredQuantity);
                salesOrderItem.setInTransitQuantity(salesOrderItem.getInTransitQuantity() - outboundQuantity);
                salesOrderItemDao.update(salesOrderItem);
            }

            //三、修改销售单内的“出库单状态”、“送达数量”
            SalesOrder salesOrder = null;
            maxRetryTimes = 6;
            while (maxRetryTimes-- > 0) {
                salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                int deliveredQuantity = salesOrder.getDeliveredQuantity();
                Integer totalQuantity = outboundOrder.getTotalQuantity();
                int inTransitQuantity = salesOrder.getInTransitQuantity();
                salesOrder.setDeliveredQuantity(deliveredQuantity + totalQuantity);
                salesOrder.setInTransitQuantity(inTransitQuantity - totalQuantity);
                if (salesOrder.getTotalQuantity() == salesOrder.getDeliveredQuantity()) {
                    //如果订单总数量==送达总数量，修改订单状态为“已签收”,产生应收代垫，产生低买高卖账户金额
                    salesOrder.setSalesOrderStatus(SalesOrderStatusEnum.SIGNED.getStatus());
                    salesOrder.setSignTime(new Date());
                    //增加操作日志
                    TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "签收完成");
                    String traceLogList2 = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog2);
                    salesOrder.setTracelog(traceLogList2);

                }
                update = salesOrderDao.update(salesOrder);
                if (update == 1) {
                    break;
                }
            }
            if (maxRetryTimes <= 0) {
                logger.error("update salesOrder FAILED! salesOrderNo={}", salesOrderNo);
            }

            //前往支付模块修改金额相关
            if (salesOrder.getTotalQuantity() == salesOrder.getDeliveredQuantity()) {
                //如果供应商代垫金额》0，产生应收代垫
                Long generatedPrepaid = salesOrder.getTotalGeneratedPrepaid();
                if (generatedPrepaid > 0) {
                    int i = yhglobalPrepaidService.addReceive(
                            rpcHeader, salesOrderNo, salesOrder.getProjectId(),
                            salesOrder.getProjectName(), null, null,
                            salesOrder.getSalesContactNo(), generatedPrepaid, salesOrder.getCurrencyCode());
                }
                //产生低买高卖金额
                Long sellHighAmount = salesOrder.getSellHighAmount();
                //如果产生了差价金额，去账户转入金额
                if (sellHighAmount > 0) {
                    supplierAccountService.salesUpdateSellHighAccount(rpcHeader, salesOrder.getCurrencyCode(), salesOrder.getProjectId(), sellHighAmount, salesOrderNo, new Date());
                }

                //前往支付模块，如果存在 待确认金额为-值  ，将金额自动转入实收账户
                cashConfirmService.confirmNegativeAmountAutomatically(rpcHeader, salesOrderNo);
            }
            return RpcResult.newSuccessResult();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}