package com.yhglobal.gongxiao.sales.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.eas.model.SaleOrder;
import com.yhglobal.gongxiao.eas.model.SaleOrderItem;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.model.EasResultItem;
import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.sales.dao.SalesOutboundOrderDao;
import com.yhglobal.gongxiao.sales.model.OutboundOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesConfig;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrder;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.XpsTransportationManager;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.Receiver;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.Sender;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.StockInOrderItem;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.constant.FXConstant.MILLION;
import static com.yhglobal.gongxiao.constant.FXConstant.TAX_RATE;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.HANDLED;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.HANDLING;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.UNHANDLED;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.UNKNOWN;

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
    private SalesConfig salesConfig;

    //同步eas定时任务
    @Scheduled(fixedDelayString = "${syncEasRate}")
    void syncSalesOrderToEas() throws MalformedURLException, RemoteException {
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(LocalDateTime.now().toString(), "000", "system-sales");
        String salesOrderNo = null;
        long projectId = salesConfig.getTargetProjectId();
        // 0.查询表前缀
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        try {

            logger.info("#traceId={}# [IN][SyncSalesOrderToEasTask] sync start:", rpcHeader.getTraceId());
            int update = 0;
            int count = 0;
            //查找到待同步的单据
            List<SalesOrder> salesOrders = salesOrderDao.selectToSyncEasOrder(prefix, UNHANDLED.getStatus());
            for (SalesOrder salesOrder : salesOrders) {
                //修改单据状态为"同步中"
                salesOrderNo = salesOrder.getSalesOrderNo();
                int syncEas = HANDLING.getStatus();
                update = salesOrderDao.updateSyncEasStatus(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), syncEas);
                if (update != 1) {
                    // 如果修改失败,表示其他地方修改中,跳过该单
                    continue;
                }
                salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                SaleOrder easSaleOrder = new SaleOrder();
                //rpc project
                ProjectStructure.GetByProjectIdReq projectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId + "").build();
                ProjectServiceGrpc.ProjectServiceBlockingStub projectService = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
                ProjectStructure.GetByProjectIdResp project = projectService.getByProjectId(projectIdReq);
                // rpc distributor
                DistributorStructure.GetDistributorBusinessByIdReq getDistributorBasicByIdReq = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder().setRpcHeader(rpcHeader).setDistributorBusinessId(salesOrder.getDistributorId()).build();
                DistributorServiceGrpc.DistributorServiceBlockingStub distributorService = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
                DistributorStructure.GetDistributorBusinessByIdResp distributor = distributorService.getDistributorBusinessById(getDistributorBasicByIdReq);
                List<SalesOrderItem> salesOrderItems = salesOrderItemDao.selectListBySalesOrderNo(prefix, salesOrder.getSalesOrderNo());
                easSaleOrder.setCustomerId(distributor.getDistributorBusiness().getDistributorEasCode());
                easSaleOrder.setProjectId(project.getProject().getEasProjectCode());
                easSaleOrder.setCurrencyCode("BB01");
                double totalAmount = 1.0 * salesOrder.getTotalOrderAmount() / HUNDRED;
                easSaleOrder.setTotalTaxAmount(totalAmount);
                //税额
                double percent = TAX_RATE / 100;
                double totalTax = totalAmount * (percent / (1 + percent));
                easSaleOrder.setTotalTax(totalTax);
                easSaleOrder.setNumber(salesOrder.getTotalQuantity());
                easSaleOrder.setBusinessDate(salesOrder.getCreateTime());
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
                    //rpc productBasic
                    ProductServiceGrpc.ProductServiceBlockingStub productService = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                    ProductStructure.GetByProductModelReq getByProductModelReq = ProductStructure.GetByProductModelReq.newBuilder()
                            .setProjectId(projectId)
                            .setRpcHeader(rpcHeader).setProductModel(salesOrderItem.getProductCode()).build();
                    ProductStructure.GetByProductModelResp productBasic = productService.getByProductModel(getByProductModelReq);
                    easSaleItem.setUnit(productBasic.getProductBusiness().getEasUnitCode());
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
                boolean updateSuccess = false;
                while (maxRetryTimes-- > 0) {
                    salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrder.getSalesOrderNo());
                    String easOrderNo = easResult.getOrderNumber();
                    String easOrderId = easResult.getId();
                    syncEas = HANDLED.getStatus();
                    update = salesOrderDao.updateEasInfo(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), syncEas, easOrderNo, easOrderId);
                    if (update == 1) {
                        updateSuccess = true;
                        count++;
                        break;
                    }
                }
                if (!updateSuccess) {
                    logger.error("update salesOrder eas info FAILED. salesOrderNo={}", salesOrder.getSalesOrderNo());
                }
                List<EasResultItem> entryList = easResult.getEntryList();
                for (EasResultItem easResultItem : entryList) {
                    String easProductCode = easResultItem.getMaterialCode();
                    SalesOrderItem salesOrderItem =
                            salesOrderItemDao.getSalesOrderItemBySalesOrderNoAndEasProductCode(prefix, salesOrder.getSalesOrderNo(), easProductCode);
                    String entryId = easResultItem.getEntryId();
                    update = salesOrderItemDao.updateEasEntryId(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), entryId);
                }
            }
            logger.info("#traceId={}# [OUT] synchronize success. count={} ", rpcHeader.getTraceId(), salesOrders);
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            int maxRetryTimes = 6;
            if (message.contains("Read")) {
                while (maxRetryTimes-- > 0) {
                    // 状态修改为 UNKNOWN 未知
                    SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                    int syncEas = UNKNOWN.getStatus();
                    int update = salesOrderDao.updateSyncEasStatus(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), syncEas);
                    if (update == 1) {
                        break;
                    }
                }
            } else {
                while (maxRetryTimes-- > 0) {
                    // 回滚为未处理
                    SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOrderNo);
                    int syncEas = UNHANDLED.getStatus();
                    int update = salesOrderDao.updateSyncEasStatus(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), syncEas);
                    if (update == 1) {
                        break;
                    }
                }
            }
        }
    }


    //同步tms定时任务
    @Scheduled(fixedDelayString = "${syncTmsRate}")
    public void syncSalesOutboundOrderToTms() {
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(LocalDateTime.now().toString(), "000", "system-sales");
        String outboundNo = null;
        long projectId = salesConfig.getTargetProjectId();
        // 0.查询表前缀
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        try {
            logger.info("#traceId={}# [IN][syncSalesOutboundOrderToTms] ", rpcHeader.getTraceId());

            List<String> salesOutboundOrderNos = salesOutboundOrderDao.selectListByTmsStatus(prefix, UNHANDLED.getStatus());
            int update;
            for (String outboundOrderNo : salesOutboundOrderNos) {
                outboundNo = outboundOrderNo;
                SalesOutboundOrder salesOutboundOrder = null;
                int maxRetryTimes = 6;
                //修改订单至"同步中"状态
                boolean updateSuccess = false;
                while (maxRetryTimes-- > 0) {
                    salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(prefix, outboundOrderNo);
                    int syncTms = HANDLING.getStatus();
                    update = salesOutboundOrderDao.updateSyncTmsStatus(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms);
                    if (update == 1) {
                        updateSuccess = true;
                        break;
                    }
                }
                if (!updateSuccess) {
                    logger.error("#traceId={}. repeated request. outboundOrderNo={}", rpcHeader.getTraceId(), outboundOrderNo);
                    throw new RuntimeException("repeated request. outboundOrderNo={}" + outboundOrderNo);
                }
                String items = salesOutboundOrder.getItems();
                List<OutboundOrderItem> outboundOrderItems = JSON.parseArray(items, OutboundOrderItem.class);
                SalesOrder salesOrder = salesOrderDao.getByOrderNo(prefix, salesOutboundOrder.getSalesOrderNo());
                //rpc warehouse
                WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
                WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(salesOutboundOrder.getWarehouseId()).build();
                WarehouseStructure.Warehouse warehouse = warehouseService.getWarehouseById(getWarehouseByIdReq).getWarehouse();
                Receiver receiverInfo = new Receiver(salesOrder.getDistributorName(), salesOrder.getProvinceName(), salesOrder.getCityName(), salesOrder.getDistrictName(), salesOrder.getShippingAddress(), salesOrder.getRecipientName(), salesOrder.getRecipientMobile());
                Sender senderInfo = new Sender(warehouse.getWmsWarehouseName(), warehouse.getProvinceName(), warehouse.getCityName(), warehouse.getDistrictName(), warehouse.getStreetAddress(), warehouse.getGeneralContactName(), warehouse.getGeneralMobile());

                List<StockInOrderItem> orderItemList = new ArrayList<>();
                for (OutboundOrderItem record : outboundOrderItems) {
                    //rpc productBasic
                    ProductServiceGrpc.ProductServiceBlockingStub productService = RpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                    ProductStructure.GetProductDetailByModelReq getByProductModelReq = ProductStructure.GetProductDetailByModelReq.newBuilder()
                            .setProjectId(Long.parseLong(record.getProjectId()))
                            .setRpcHeader(rpcHeader).setProductModel(record.getProductCode()).build();
                    ProductStructure.ProductBasicDetail productBasicDetail = productService.getProductDetailByModel(getByProductModelReq).getProductBasicDetail();

                    float itemVol = (float)
                            (1.0 * productBasicDetail.getBoxHeight() * productBasicDetail.getBoxLength() * productBasicDetail.getBoxWidth() *
                                    record.getTotalQuantity() / HUNDRED / HUNDRED / HUNDRED / 1000 / 1000 / 1000);   //体积
                    float itemWgt = (float)
                            (1.0 * productBasicDetail.getProductGrossWeight() / HUNDRED / 1000 * record.getTotalQuantity());
                    StockInOrderItem tmsStockInOrderItem = new StockInOrderItem(productBasicDetail.getEasCode(), productBasicDetail.getCustomerProductCode(), record.getProductName(), record.getTotalQuantity(), itemVol, itemWgt, record.getBatchNo());
                    orderItemList.add(tmsStockInOrderItem);
                }
                DateFormat df2 = DateFormat.getDateTimeInstance();
                String createTime = df2.format(salesOutboundOrder.getCreateTime());
                String expectedArrivalTime = df2.format(salesOutboundOrder.getExpectedArrivalTime());
                //rpc project
                ProjectStructure.GetByProjectIdReq projectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(salesOrder.getProjectId() + "").build();
                ProjectServiceGrpc.ProjectServiceBlockingStub projectService = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
                ProjectStructure.Project project = projectService.getByProjectId(projectIdReq).getProject();

                // 根据channelId查询channelToken
                String channelId = salesConfig.getChannelId();
                //调用基础模块的SourceChannel服务
                ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
                ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setXpsChannelId(channelId + "").build();
                ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
                ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();
                String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

                GongxiaoResult gongxiaoResult =
                        XpsTransportationManager.createTmsOrder(salesConfig.getTransportationUrl(), channelId,
                                xpsChannelSecret, salesOrder.getProjectId(), outboundOrderNo, createTime, null, expectedArrivalTime, 4, 1,
                                null, null, null, null, 1258, 0, 0, 0, "06.001.27", project.getEasProjectName(), null, 5,
                                receiverInfo, senderInfo, orderItemList, null);


                maxRetryTimes = 6;
                while (maxRetryTimes-- > 0) {
                    salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(prefix, outboundOrderNo);
                    int syncTms;
                    if (gongxiaoResult != null) {
                        if (gongxiaoResult.getReturnCode() == 0) {
                            syncTms = HANDLED.getStatus();
                        } else {
                            syncTms = UNHANDLED.getStatus();
                        }
                    } else {
                        syncTms = UNHANDLED.getStatus();
                    }
                    update = salesOutboundOrderDao.updateSyncTmsStatus(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms);
                    if (update == 1) {
                        break;
                    }
                }
                if (maxRetryTimes <= 0) {
                    logger.error("update salesOutboundOrder FAILED!");
                }
            }
            logger.info("#traceId={}# [OUT]: sync finished. outboundOrderList={}", rpcHeader.getTraceId(), salesOutboundOrderNos);
        } catch (Exception e) {
            int maxRetryTimes = 6;
            boolean updateSuccess = false;
            //修改订单"同步中" => "未同步"
            while (maxRetryTimes-- > 0) {
                SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getOrderByOutboundNo(prefix, outboundNo);
                int syncTms = UNHANDLED.getStatus();
                int update = salesOutboundOrderDao.updateSyncTmsStatus(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms);
                if (update == 1) {
                    updateSuccess = true;
                    break;
                }
            }
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        List<SalesOutboundOrder> list = new ArrayList<>();
        SalesOutboundOrder salesOutboundOrder = new SalesOutboundOrder();
        salesOutboundOrder.setOutboundOrderNo("zz");
        list.add(salesOutboundOrder);
        list.add(salesOutboundOrder);
        System.out.println(list);
    }

}
