package com.yhglobal.gongxiao.phjd.sales.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum;
import com.yhglobal.gongxiao.eas.model.SaleOrder;
import com.yhglobal.gongxiao.eas.model.SaleOrderItem;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.model.EasResultItem;
import com.yhglobal.gongxiao.phjd.common.service.CommonService;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOrderAddressDao;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOrderItemDao;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOutboundOrderDao;
import com.yhglobal.gongxiao.phjd.sales.model.*;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.Receiver;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.Sender;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.StockInOrderItem;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.XpsTransportationManager;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.BigDecimalUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouseapi.model.OutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static com.yhglobal.gongxiao.constant.FXConstant.*;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.*;

/**
 * 定时任务
 *
 * @author weizecheng
 * @date 2018/9/3 17:07
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
    @Autowired
    private CommonService commonService;
    @Autowired
    private SalesOrderAddressDao salesOrderAddressDao;

    /**
     * 同步EAS任务
     *
     * @author weizecheng
     * @date 2018/9/3 18:59
     */
    @Scheduled(fixedDelayString = "${syncEasRate}")
    void syncSalesOrderToEas() throws MalformedURLException, RemoteException {
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(LocalDateTime.now().toString(), "000", "system-sales");
        String salesOrderNo = null;
        Long projectId = salesConfig.getProjectId();
        // 0.查询表前缀
        String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        try {

            logger.info("#traceId={}# [IN][SyncSalesOrderToEasTask] sync start:", rpcHeader.getTraceId());
            int update;
            int count = 0;
            //查找到待同步的单据
            List<SalesOrder> salesOrders = salesOrderDao.listBySyncStatus(prefix, SalesOrderSyncEnum.UNHANDLED.getStatus());
            for (SalesOrder salesOrder : salesOrders) {
                //修改单据状态为"同步中"
                salesOrderNo = salesOrder.getSalesOrderNo();
                int syncEas = HANDLING.getStatus();
                update = salesOrderDao.updateSyncEasStatus(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), syncEas);
                if (update != 1) {
                    // 如果修改失败,表示其他地方修改中,跳过该单
                    continue;
                }
                salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
                SaleOrder easSaleOrder = new SaleOrder();
                //rpc project
                ProjectStructure.Project project = commonService.getByProjectIdResp(rpcHeader,projectId.toString());
                // rpc distributor
                DistributorStructure.GetDistributorBusinessByIdReq getDistributorBasicByIdReq = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder().setRpcHeader(rpcHeader).setDistributorBusinessId(salesOrder.getDistributorId()).build();
                DistributorServiceGrpc.DistributorServiceBlockingStub distributorService = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
                DistributorStructure.GetDistributorBusinessByIdResp distributor = distributorService.getDistributorBusinessById(getDistributorBasicByIdReq);
                List<SalesOrderItem> salesOrderItems = salesOrderItemDao.listBySalesOrderNo(prefix, salesOrder.getSalesOrderNo());
                easSaleOrder.setCustomerId(distributor.getDistributorBusiness().getDistributorEasCode());
                easSaleOrder.setProjectId(project.getEasProjectCode());
                easSaleOrder.setCurrencyCode("BB01");
//                double totalAmount = 1.0 * salesOrder.getTotalOrderAmount() / HUNDRED;
                // 实际价格
                double totalAmount = salesOrder.getCashAmount().doubleValue();
                easSaleOrder.setTotalTaxAmount(totalAmount);
                //税额
                double percent = TAX_RATE / 100;
                double totalTax = totalAmount * (percent / (1 + percent));
                easSaleOrder.setTotalTax(totalTax);
                easSaleOrder.setNumber(salesOrder.getTotalCostQuantity());
                easSaleOrder.setBusinessDate(salesOrder.getCreateTime());
                ArrayList<SaleOrderItem> easSaleOrderItems = new ArrayList<>(salesOrderItems.size());
                for (SalesOrderItem salesOrderItem : salesOrderItems) {
                    SaleOrderItem easSaleItem = new SaleOrderItem();
                    easSaleItem.setCustomerId(salesOrder.getDistributorId().toString());
                    easSaleItem.setNumber(salesOrderItem.getTotalQuantity());
                    easSaleItem.setTaxPrice(salesOrderItem.getJdPurchaseGuidePrice().doubleValue());
                    // 京东项目 折扣设置为 0
                    easSaleItem.setDiscount(0);
                    easSaleItem.setTaxRate(TAX_RATE);
                    easSaleItem.setWarehouseId(null);
                    easSaleItem.setMaterialId(salesOrderItem.getEasCode());
                    //rpc productBasic
                    ProductStructure.GetByProductModelResp productBasic = commonService.getByProductModel(rpcHeader,projectId,salesOrderItem.getProductCode());
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
                    salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrder.getSalesOrderNo());
                    String easOrderNo = easResult.getOrderNumber();
                    String easOrderId = easResult.getId();
                    syncEas = SalesOrderSyncEnum.HANDLED.getStatus();
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
                            salesOrderItemDao.getBySalesOrderNoAndProductCode(prefix, salesOrder.getSalesOrderNo(), easProductCode);
                    String entryId = easResultItem.getEntryId();
                    update = salesOrderItemDao.updateEasEntryId(prefix, salesOrderItem.getSalesOrderItemId(), salesOrderItem.getDataVersion(), entryId);
                    if(update != 1){
                        logger.error("update salesOrderItem updateEasEntryId FAILED. salesOrderItemId={}",salesOrderItem.getSalesOrderItemId());
                    }
                }
            }
            logger.info("#traceId={}# [OUT] synchronize success. count={} ", rpcHeader.getTraceId(), count);
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            int maxRetryTimes = 6;
            if (message != null && message.contains("Read")) {
                while (maxRetryTimes-- > 0) {
                    // 状态修改为 UNKNOWN 未知
                    SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
                    int syncEas = UNKNOWN.getStatus();
                    int update = salesOrderDao.updateSyncEasStatus(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), syncEas);
                    if (update == 1) {
                        break;
                    }
                }
            } else {
                while (maxRetryTimes-- > 0) {
                    // 回滚为未处理
                    SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOrderNo);
                    int syncEas = UNHANDLED.getStatus();
                    int update = salesOrderDao.updateSyncEasStatus(prefix, salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), syncEas);
                    if (update == 1) {
                        break;
                    }
                }
            }
        }
    }



    /**
     * 同步TMS定时器
     *
     * @author weizecheng
     * @date 2018/9/4 10:11
     */
    @Scheduled(fixedDelayString = "${syncTmsRate}")
    public void syncSalesOutboundOrderToTms() {
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(LocalDateTime.now().toString(), "000", "system-sales");
        String outboundNo = null;
        Long projectId = salesConfig.getProjectId();
        String prefix = null;
        try {
            logger.info("#traceId={}# [IN][syncSalesOutboundOrderToTms] ", rpcHeader.getTraceId());
            // 0.查询表前缀
            prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            List<String> salesOutboundOrderNos = salesOutboundOrderDao.listByTmsStatus(prefix, SalesOrderSyncEnum.UNHANDLED.getStatus());
            int update;
            for (String outboundOrderNo : salesOutboundOrderNos) {
                outboundNo = outboundOrderNo;
                SalesOutboundOrder salesOutboundOrder = null;
                int maxRetryTimes = 6;
                //修改订单至"同步中"状态
                boolean updateSuccess = false;
                while (maxRetryTimes-- > 0) {
                    salesOutboundOrder = salesOutboundOrderDao.getByOutboundOrderNo(prefix, outboundOrderNo);
                    int syncTms = HANDLING.getStatus();
                    update = salesOutboundOrderDao.updateSyncTms(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms);
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
                SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo(prefix, salesOutboundOrder.getSalesOrderNo());
                SalesOrderAddressDO  address =  salesOrderAddressDao.getAddressBySalesOrderId(prefix,salesOrder.getSalesOrderId());
                //rpc warehouse
                WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = RpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
                WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(salesOutboundOrder.getWarehouseId()).build();
                WarehouseStructure.Warehouse warehouse = warehouseService.getWarehouseById(getWarehouseByIdReq).getWarehouse();
                // TODO 这里发货地址 JD 并没有 具体发货地
                Receiver receiverInfo = new Receiver(salesOrder.getDistributorName(), address.getArrived(), address.getArrived(), address.getArrived(), address.getReceivingAddress(), address.getReceiver(), address.getReceiverTel());
                Sender senderInfo = new Sender(warehouse.getWmsWarehouseName(), warehouse.getProvinceName(), warehouse.getCityName(), warehouse.getDistrictName(), warehouse.getStreetAddress(), warehouse.getGeneralContactName(), warehouse.getGeneralMobile());

                List<StockInOrderItem> orderItemList = new ArrayList<>(outboundOrderItems.size());

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
                ProjectStructure.Project project = commonService.getByProjectIdResp(rpcHeader,projectId.toString());
                // 根据channelId查询channelToken
                String channelId = salesConfig.getChannelId();
                //调用基础模块的SourceChannel服务
                ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel=commonService.getChannelByChannelIdResp(rpcHeader,channelId);
                String xpsChannelSecret = sourceChannel.getXpsChannelSecret();

                GongxiaoResult gongxiaoResult =
                        XpsTransportationManager.createTmsOrder(salesConfig.getTransportationUrl(), channelId,
                                xpsChannelSecret, salesOrder.getProjectId(), outboundOrderNo, createTime, null, expectedArrivalTime, 4, 1,
                                null, null, null, null, 1258, 0, 0, 0, project.getTmsProjectCode(), project.getEasProjectName(), null, 5,
                                receiverInfo, senderInfo, orderItemList, null);


                maxRetryTimes = 6;
                while (maxRetryTimes-- > 0) {
                    salesOutboundOrder = salesOutboundOrderDao.getByOutboundOrderNo(prefix, outboundOrderNo);
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
                    update = salesOutboundOrderDao.updateSyncTms(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms);
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
            //修改订单"同步中" => "未同步"
            while (maxRetryTimes-- > 0) {
                SalesOutboundOrder salesOutboundOrder = salesOutboundOrderDao.getByOutboundOrderNo(prefix, outboundNo);
                int syncTms = UNHANDLED.getStatus();
                int update = salesOutboundOrderDao.updateSyncTms(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms);
                if (update == 1) {
                    break;
                }
            }
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
//        List<SalesOutboundOrder> list = new ArrayList<>();
//        SalesOutboundOrder salesOutboundOrder = new SalesOutboundOrder();
//        salesOutboundOrder.setOutboundOrderNo("zz");
//        list.add(salesOutboundOrder);
//        list.add(salesOutboundOrder);
        System.out.println(BigDecimalUtil.multiplication(new BigDecimal(2314.41)).divide(new BigDecimal(MILLION),6,BigDecimal.ROUND_HALF_UP).doubleValue());
    }

}
