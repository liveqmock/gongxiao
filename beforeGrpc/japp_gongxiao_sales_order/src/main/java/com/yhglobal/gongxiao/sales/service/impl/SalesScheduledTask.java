package com.yhglobal.gongxiao.sales.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.eas.SaleOrder;
import com.yhglobal.gongxiao.eas.SaleOrderItem;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorService;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.model.EasResultItem;
import com.yhglobal.gongxiao.sales.bootstrap.TransportConfig;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesOutboundOrderDao;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesOrderSyncEnum;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrder;
import com.yhglobal.gongxiao.tmsapi.TmsManager;
import com.yhglobal.gongxiao.tmsapi.order.create.CreateDispatchOrderResponse;
import com.yhglobal.gongxiao.tmsapi.order.create.Receiver;
import com.yhglobal.gongxiao.tmsapi.order.create.Sender;
import com.yhglobal.gongxiao.tmsapi.order.create.StockInOrderItem;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.constant.FXConstant.MILLION;
import static com.yhglobal.gongxiao.constant.FXConstant.TAX_RATE;
import static com.yhglobal.gongxiao.sales.model.SalesOrderSyncEnum.HANDLED;
import static com.yhglobal.gongxiao.sales.model.SalesOrderSyncEnum.HANDLING;
import static com.yhglobal.gongxiao.sales.model.SalesOrderSyncEnum.UNHANDLED;

/**
 * 销售模块定时任务
 *
 * @author 葛灿
 */
@Service
public class SalesScheduledTask {

    private static Logger logger = LoggerFactory.getLogger(SalesScheduledTask.class);

    @Autowired
    private SalesOrderItemDao salesOrderItemDao;
    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOutboundOrderDao salesOutboundOrderDao;
    @Autowired
    private TransportConfig transportConfig;
    @Autowired
    private ChannelDao channelDao;
    @Reference
    ProjectService projectService;
    @Reference
    DistributorService distributorService;
    @Reference
    WarehouseService warehouseService;
    @Reference
    ProductService productService;

    //同步eas定时任务
    @Scheduled(fixedDelayString = "${syncEasRate}")
    void syncSalesOrderToEas() throws MalformedURLException, RemoteException {
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setTraceId(LocalDateTime.now().toString());
        rpcHeader.setUid("000");
        rpcHeader.setUsername("system-sales");
        String salesOrderNo = null;
        try {
            logger.info("#traceId={}# [IN][SyncSalesOrderToEasTask] sync start:", rpcHeader.traceId);
            int update = 0;
            int count = 0;
            //查找到待同步的单据
            List<SalesOrder> salesOrders = salesOrderDao.selectToSyncEasOrder(UNHANDLED.getStatus());
            for (SalesOrder salesOrder : salesOrders) {
                //修改单据状态为"同步中"
                salesOrderNo = salesOrder.getSalesOrderNo();
                salesOrder.setSyncEas(HANDLING.getStatus());
                update = salesOrderDao.update(salesOrder);
                if (update != 1) {
                    // 如果修改失败,表示其他地方修改中,跳过该单
                    continue;
                }
                salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                SaleOrder easSaleOrder = new SaleOrder();
                Project project = projectService.getByProjectId(rpcHeader, salesOrder.getProjectId() + "");
                Distributor distributor = distributorService.getDistributorByDistributorId(rpcHeader, salesOrder.getDistributorId() + "");
                List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(salesOrder.getSalesOrderNo());
                easSaleOrder.setCustomerId(distributor.getEasCustomerCode());
                easSaleOrder.setProjectId(project.getEasProjectCode());
                easSaleOrder.setCurrencyCode("BB01");
                double totalAmount = 1.0 * salesOrder.getTotalOrderAmount() / HUNDRED;
                easSaleOrder.setTotalTaxAmount(totalAmount);
                //税额
                double percent = TAX_RATE / 100;
                double totalTax = totalAmount * (percent / (1 + percent));
                easSaleOrder.setTotalTax(totalTax);
                easSaleOrder.setNumber(salesOrder.getTotalQuantity());
                ArrayList<SaleOrderItem> easSaleOrderItems = new ArrayList<>();
                for (SalesOrderItem salesOrderItem : salesOrderItems) {
                    SaleOrderItem easSaleItem = new SaleOrderItem();
                    easSaleItem.setCustomerId(salesOrder.getDistributorId() + "");
                    easSaleItem.setNumber(salesOrderItem.getTotalQuantity());
                    easSaleItem.setTaxPrice(1.0 * salesOrderItem.getSalesGuidePrice() / MILLION);
                    easSaleItem.setDiscount((salesOrderItem.getYhDiscountPercent() + salesOrderItem.getSupplierDiscountPercent()) / 10000.0);
                    easSaleItem.setTaxRate(TAX_RATE);
                    easSaleItem.setWarehouseId(null);
                    easSaleItem.setMaterialId(salesOrderItem.getEasCode());
                    ProductBasic productBasic = productService.getByProductCode(rpcHeader, salesOrderItem.getProductCode());
                    easSaleItem.setUnit(productBasic.getEasUnitCode());
                    //发货日期
                    easSaleItem.setSendDate(new Date());
                    //交货日期 = 订单下单时间 + 1天
                    Date createTime = salesOrder.getCreateTime();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(createTime);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    Date deliverDate = calendar.getTime();
                    easSaleItem.setDeliveryDate(deliverDate);
                    easSaleOrderItems.add(easSaleItem);
                }
                String easCallBack = EasUtil.sendSaleOrder2Eas(easSaleOrder, easSaleOrderItems);
                EasResult easResult;
                easResult = JSON.parseObject(easCallBack, new TypeReference<EasResult>() {
                });

                int maxRetryTimes = 6;
                while (maxRetryTimes-- > 0) {
                    salesOrder = salesOrderDao.getByOrderNo(salesOrder.getSalesOrderNo());
                    salesOrder.setEasOrderNo(easResult.getOrderNumber());
                    salesOrder.setEasOrderId(easResult.getId());
                    salesOrder.setSyncEas(HANDLED.getStatus());
                    update = salesOrderDao.update(salesOrder);
                    if (update == 1) {
                        count++;
                        break;
                    }
                }
                if (maxRetryTimes <= 0) {
                    logger.error("update salesOrder eas info FAILED. salesOrderNo={}", salesOrder.getSalesOrderNo());
                }
                List<EasResultItem> entryList = easResult.getEntryList();
                for (EasResultItem easResultItem : entryList) {
                    String easProductCode = easResultItem.getMaterialCode();
                    SalesOrderItem salesOrderItem =
                            salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndEasProductCode(salesOrder.getSalesOrderNo(), easProductCode);
                    salesOrderItem.setEntryId(easResultItem.getEntryId());
                    update = salesOrderItemDao.update(salesOrderItem);
                }
            }
            logger.info("#traceId={}# [OUT] synchronize success. count={} ", rpcHeader.traceId, salesOrders);
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            int maxRetryTimes = 6;
            boolean syncStatus = false;
            if (message.contains("Read")) {
                while (maxRetryTimes-- > 0) {
                    // 状态修改为 UNKNOWN 未知
                    SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                    salesOrder.setSyncEas(SalesOrderSyncEnum.UNKNOWN.getStatus());
                    int update = salesOrderDao.update(salesOrder);
                    if (update == 1) {
                        syncStatus = true;
                        break;
                    }
                }
                //如果6次均失败
                if (!syncStatus) {
                    logger.error("FAILED to modify {} syncEas status to UNKNOWN", salesOrderNo);
                }
            } else {
                while (maxRetryTimes-- > 0) {
                    // 回滚为未处理
                    SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOrderNo);
                    salesOrder.setSyncEas(UNHANDLED.getStatus());
                    int update = salesOrderDao.update(salesOrder);
                    if (update == 1) {
                        syncStatus = true;
                        break;
                    }
                }
                //如果6次均失败
                if (!syncStatus) {
                    logger.error("FAILED to modify {} syncEas status to UNHANDLED", salesOrderNo);
                }
            }
        }
    }


    //同步tms定时任务
    @Scheduled(fixedDelayString = "${syncEasRate}")
    public void syncSalesOutboundOrderToTms() {
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setUid("0");
        rpcHeader.setUsername("system-sales");
        rpcHeader.setTraceId(LocalDateTime.now().toString());
        String outboundNo = null;
        try {
            logger.info("#traceId={}# [IN][syncSalesOutboundOrderToTms] ", rpcHeader.traceId);
            List<String> salesOutboundOrderNos = salesOutboundOrderDao.selectListByTmsStatus(UNHANDLED.getStatus());
            int update;
            for (String outboundOrderNo : salesOutboundOrderNos) {
                outboundNo = outboundOrderNo;
                SalesOutboundOrder salesOutboundOrder = null;
                int maxRetryTimes = 6;
                //修改订单至"同步中"状态
                while (maxRetryTimes-- > 0) {
                    salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(outboundOrderNo);
                    salesOutboundOrder.setSyncToTms(HANDLING.getStatus());
                    update = salesOutboundOrderDao.update(salesOutboundOrder);
                    if (update == 1) {
                        break;
                    }
                }
                if (maxRetryTimes <= 0) {
                    logger.error("#traceId={}. repeated request. outboundOrderNo={}", rpcHeader.getTraceId(), outboundOrderNo);
                    throw new RuntimeException("repeated request. outboundOrderNo={}" + outboundOrderNo);
                }
                String items = salesOutboundOrder.getItems();
                List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(items, OutboundOrderItem.class);
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(salesOutboundOrder.getSalesOrderNo());
                Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, salesOutboundOrder.getWarehouseId() + "");
                Receiver receiverInfo = new Receiver(salesOrder.getDistributorName(), salesOrder.getProvinceName(), salesOrder.getCityName(), salesOrder.getDistrictName(), salesOrder.getShippingAddress(), salesOrder.getRecipientName(), salesOrder.getRecipientMobile());
                Sender senderInfo = new Sender(warehouse.getWmsWarehouseName(), warehouse.getProvinceName(), warehouse.getCityName(), warehouse.getCountryName(), warehouse.getStreetAddress(), warehouse.getGeneralContactName(), warehouse.getGeneralMobile());

                List<StockInOrderItem> orderItemList = new ArrayList<>();
                for (OutboundOrderItem record : outboundOrderItems) {
                    ProductBasic productBasic = productService.getByProductCode(rpcHeader, record.getProductCode());
                    float itemVol = (float)
                            (1.0 * productBasic.getBoxHeight() * productBasic.getBoxLength() * productBasic.getBoxWidth()
                                    * record.getTotalQuantity()
                                    / HUNDRED / HUNDRED / HUNDRED / 1000 / 1000 / 1000);   //体积
                    float itemWgt = (float)
                            (1.0 * productBasic.getProductGrossWeight() / HUNDRED / 1000 * record.getTotalQuantity());
                    StockInOrderItem tmsStockInOrderItem = new StockInOrderItem(productBasic.getEasCode(), productBasic.getEasCode(), productBasic.getEasName(), record.getTotalQuantity(), itemVol, itemWgt, record.getBatchNo());
                    orderItemList.add(tmsStockInOrderItem);
                }

                DateFormat df2 = DateFormat.getDateTimeInstance();
                String createTime = df2.format(salesOutboundOrder.getCreateTime());
                String expectedArrivalTime = df2.format(salesOutboundOrder.getExpectedArrivalTime());
                Channel channel = channelDao.selectByChannelId(Integer.parseInt(WmsSourceChannel.CHANNEL_TMS.getChannelId()));
                Project project = projectService.getByProjectId(rpcHeader, salesOrder.getProjectId() + "");
                logger.info("==============TMS系统接口URL:{}", transportConfig.getTmsUrl() + "/api/TmsApi/CreateDispatchOrder");
                CreateDispatchOrderResponse resp = TmsManager.createTmsOrder(transportConfig.getTmsUrl() + "/api/TmsApi/CreateDispatchOrder", outboundOrderNo, createTime, null, expectedArrivalTime, 4, 1,
                        channel.getChannelName(), channel.getCustCode(), null, null, 1258, 0, 0, 0, "06.001.27", project.getEasProjectName(), null, 5,
                        receiverInfo, senderInfo, orderItemList, null);

                maxRetryTimes = 6;
                while (maxRetryTimes-- > 0) {
                    salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(outboundOrderNo);
                    if (resp != null) {
                        if (resp.isSuccess()) {
                            salesOutboundOrder.setSyncToTms(HANDLED.getStatus());
                        } else {
                            salesOutboundOrder.setSyncToTms(UNHANDLED.getStatus());
                        }
                    } else {
                        salesOutboundOrder.setSyncToTms(UNHANDLED.getStatus());
                    }
                    update = salesOutboundOrderDao.update(salesOutboundOrder);
                    if (update == 1) {
                        break;
                    }
                }
                if (maxRetryTimes <= 0) {
                    logger.error("update salesOutboundOrder FAILED!");
                }
            }
            logger.info("#traceId={}# [OUT]: sync finished. outboundOrderList={}", rpcHeader.traceId, salesOutboundOrderNos);
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            int maxRetryTimes = 6;
            boolean syncStatus = false;
            if (message.contains("Read")) {
                while (maxRetryTimes-- > 0) {
                    // 状态修改为 UNKNOWN 未知
                    SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(outboundNo);
                    salesOutboundOrder.setSyncToTms(SalesOrderSyncEnum.UNKNOWN.getStatus());
                    int update = salesOutboundOrderDao.update(salesOutboundOrder);
                    if (update == 1) {
                        syncStatus = true;
                        break;
                    }
                }
                //如果6次均失败
                if (!syncStatus) {
                    logger.error("FAILED to modify outboundOrder {} syncTms status to UNKNOWN", outboundNo);
                }
            } else {
                while (maxRetryTimes-- > 0) {
                    // 回滚为未处理
                    SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(outboundNo);
                    salesOutboundOrder.setSyncToTms(UNHANDLED.getStatus());
                    int update = salesOutboundOrderDao.update(salesOutboundOrder);
                    if (update == 1) {
                        syncStatus = true;
                        break;
                    }
                }
                //如果6次均失败
                if (!syncStatus) {
                    logger.error("FAILED to modify outboundOrder {} syncTms status to UNHANDLED", outboundNo);
                }
            }

        }
    }


    public static void main(String[] args) {
        float itemVol = (float)
                (1.0 * 12300 * 16800 *15300 / HUNDRED / HUNDRED / HUNDRED / 1000 / 1000 / 1000);   //体积
        System.out.println(itemVol);
        float itemWgt = (float)
                (1.0 *17900 / HUNDRED / 1000 * 2);
        System.out.println(itemWgt);
    }

}
