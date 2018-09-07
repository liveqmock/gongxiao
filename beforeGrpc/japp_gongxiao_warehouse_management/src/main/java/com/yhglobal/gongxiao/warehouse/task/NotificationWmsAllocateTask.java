package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.constant.PurchaseTypeStatus;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.project.dao.ProjectDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationInboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationOutboundService;
import com.yhglobal.gongxiao.warehouse.type.WmsAllocateWay;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehousemanagement.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.SenderInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.StockInOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.WmsInStockNotifyRequest;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.StockOutOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.WmsOutStockNotifyRequest;
import com.yhglobal.gongxiao.warehousemanagement.model.*;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.SyncWmsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 由前端调拨页面触发该任务 通知WMS调拨
 *
 * @author liukai
 */
public class NotificationWmsAllocateTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotificationWmsAllocateTask.class);

    private RpcHeader rpcHeader;
    private ApplicationContext applicationContext;
    private AllocationOrder allocationOrder;
    private List<AllocationOrderItem> allocationOrderItemList;
    private WarehouseConfig warehouseConfig;

    @Autowired
    WmsNotificationOutboundService wmsNotificationOutboundService;

    @Autowired
    WmsNotificationInboundService wmsNotificationInboundService;

    public NotificationWmsAllocateTask(RpcHeader rpcHeader, ApplicationContext applicationContext, AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList,WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.allocationOrder = allocationOrder;
        this.allocationOrderItemList = allocationOrderItemList;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start NotificationWmsAllocateTask.run() params: allocationOrder={},allocationOrderItemList={}", rpcHeader.traceId, JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));
        //1、项目之间仓内调拨不需要通知wms 2、项目之间跨仓调拨需要通知wms 3、项目内跨仓调拨需要通知wms
        if (allocationOrder.getAlloteWay() == WmsAllocateWay.ALLOCATE_DIFFERENT_STOCK.getAllocateWay()) {     //跨仓调拨

            //创建出库单
            notifyWarehouseStockOutbound(allocationOrder, allocationOrderItemList);

            //创建入库单
            notifyWarehouseStockInbound(allocationOrder, allocationOrderItemList);

            //通知WMS调拨出库
            notifyWmsOut(allocationOrder,allocationOrderItemList);

            //通知WMS调拨入库
            notifyWmsEnter(allocationOrder,allocationOrderItemList);

        } else {     //同仓调拨，从一个项目，调到另外一个项目

            WarehouseDao warehouseDao = applicationContext.getBean("warehouseDao", WarehouseDao.class);
            String warehouseCode = warehouseDao.selectWarehouseCodeById(Long.parseLong(allocationOrder.getWarehouseOutId()));
            ProductInventoryDao productInventoryDao = applicationContext.getBean("productInventoryDao",ProductInventoryDao.class);

            int purchaseType = PurchaseTypeStatus.GENERAL_PURCHASE.getStatus();
            int inventoryType = 1;

            for (AllocationOrderItem allocationOrderItem : allocationOrderItemList){

                ProductInventory olderInventoryOut = productInventoryDao.selectRecordByProductNameAndProductCodeAndwarehouse(purchaseType,allocationOrderItem.getInventoryStatus(),Long.parseLong(allocationOrderItem.getProjectIdOut()),allocationOrderItem.getBatchNo(), allocationOrderItem.getProductCode(), allocationOrderItem.getWarehouseOut());
                if (olderInventoryOut != null){
                    int amountBefor = olderInventoryOut.getOnHandQuantity();                                         //原有库存数
                    ProductInventory productInventory = new ProductInventory();
                    productInventory.setInventoryStatus(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());    //库存状态
                    productInventory.setProjectId(Long.parseLong(allocationOrder.getProjectIdOut()));                                   //项目id
                    productInventory.setProductCode(allocationOrderItem.getProductCode());                               //商品编码
                    productInventory.setProductModel(allocationOrderItem.getProductCode());           //商品型号
                    productInventory.setProductName(allocationOrderItem.getProductName());             //商品名称
                    productInventory.setWarehouseId(allocationOrderItem.getWarehouseOutId());                               //仓库id
                    productInventory.setWarehouseCode(warehouseCode);                                 //仓库编码
                    productInventory.setWarehouseName(allocationOrderItem.getWarehouseOut());         //仓库名称
                    productInventory.setOnHandQuantity(amountBefor - allocationOrderItem.getAlloteQuantity());                   //库存数量
                    productInventory.setOnSalesOrderQuantity(0);                                      //已下单未出库
                    productInventory.setDataVersion(00000001);                                  //数据版本干什么用的
                    productInventory.setCreateTime(new Date());                                 //创建时间

                    productInventoryDao.updateByProjectIdAndProductNameAndProductCodeAndWarehouse(productInventory);

                }

                int purchaseTypeEntry = PurchaseTypeStatus.GENERAL_PURCHASE.getStatus();
                ProductInventory olderInventoryEnter = productInventoryDao.selectRecordByProductNameAndProductCodeAndwarehouse(purchaseType,allocationOrderItem.getInventoryStatus(),Long.parseLong(allocationOrderItem.getProjectIdOut()),allocationOrderItem.getBatchNo(), allocationOrderItem.getProductCode(), allocationOrderItem.getWarehouseOut());
                if (olderInventoryEnter != null) {
                    int amountBefor = olderInventoryEnter.getOnHandQuantity();                                    //原有库存数
                    ProductInventory productInventoryEnter = new ProductInventory();
                    productInventoryEnter.setInventoryStatus(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());                                                 //库存状态
                    productInventoryEnter.setProjectId(Long.parseLong(allocationOrder.getProjectIdOut()));                                   //项目id
                    productInventoryEnter.setProductCode(allocationOrderItem.getProductCode());                               //商品编码
                    productInventoryEnter.setProductModel(allocationOrderItem.getProductCode());           //商品型号
                    productInventoryEnter.setProductName(allocationOrderItem.getProductName());             //商品名称
                    productInventoryEnter.setWarehouseId(allocationOrderItem.getWarehouseOutId());                               //仓库id
                    productInventoryEnter.setWarehouseCode(warehouseCode);                                 //仓库编码
                    productInventoryEnter.setWarehouseName(allocationOrderItem.getWarehouseOut());         //仓库名称
                    productInventoryEnter.setOnHandQuantity(amountBefor + allocationOrderItem.getAlloteQuantity());                   //库存数量
                    productInventoryEnter.setOnSalesOrderQuantity(0);                                //已下单未出库
                    productInventoryEnter.setDataVersion(00000001);                                  //数据版本干什么用的
                    productInventoryEnter.setCreateTime(new Date());                                 //创建时间

                    productInventoryDao.updateByProjectIdAndProductNameAndProductCodeAndWarehouse(productInventoryEnter);

                }

            }

        }
        logger.info("#traceId={}# [OUT] get NotificationWmsAllocateTask.run() success", rpcHeader.traceId);
    }

    private void notifyWarehouseStockInbound(AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList) {

        try {
            logger.info("#traceId={}# [IN][notifyWarehouseStockInbound] start NotificationWmsAllocateTask.notifyWarehouseStockInbound() params: allocationOrder={},allocationOrderItemList={}", rpcHeader.traceId, JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));
            InboundOrder newInbountOrder = new InboundOrder();                                      //入库单记录
//        newInbountOrder.setUniqueNo(uniqueNo);
            ProjectDao projectDao = applicationContext.getBean("projectDao", ProjectDao.class);
            String brandName = projectDao.selectSupplierNameById(allocationOrder.getWarehouseEnterId());
            newInbountOrder.setSupplierName(brandName);                                                  //品牌商
            newInbountOrder.setInboundType(WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode());     //入库单类型 wms必传
            newInbountOrder.setProjectId(Long.valueOf(allocationOrder.getProjectIdEnter()));            //项目id
            newInbountOrder.setGongxiaoInboundOrderNo(allocationOrder.getGongxiaoInboundOrderNo());                          //入库单号

            newInbountOrder.setSourceChannel(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));                        //发起入库通知单的渠道
            newInbountOrder.setPurchaseOrderNo(allocationOrder.getAllocateNo());                                             //采购单号

            StringBuilder batchNo = new StringBuilder();
            int totalQuantity = 0;
            List<String> connectedProduct = new ArrayList<>();
            List<String> connectedGood = new ArrayList<>();
            for (AllocationOrderItem record : allocationOrderItemList) {     //遍历预约入库明细
                batchNo.append(record.getBatchNo());
                totalQuantity += record.getAlloteQuantity();
                newInbountOrder.setBatchNo(batchNo.toString());
                connectedProduct.add(record.getProductCode());
                connectedGood.add(record.getProductCode());
            }

            newInbountOrder.setDeliverAddress(allocationOrder.getDeliverAddress());                                //发货地址
            newInbountOrder.setContactsPeople(allocationOrder.getCompanyNameOut());                                //发货联系人
//        newInbountOrder.setPhoneNum(senderPhoneNo);                                       //联系电话
            newInbountOrder.setConnectedProduct(JSON.toJSONString(connectedProduct));                            //入库单关联的商品编码
            newInbountOrder.setConnectedGood(JSON.toJSONString(connectedGood));                                  //入库单关联的产品编码
            newInbountOrder.setWarehouseId(allocationOrder.getWarehouseEnterId());                                      //仓库id
            newInbountOrder.setWarehouseName(allocationOrder.getCompanyNameEnter());                                  //仓库名称
//        newInbountOrder.setShippingMode(shippingMode);                                  //物流方式
            newInbountOrder.setNote(allocationOrder.getNote());                               //备注
            newInbountOrder.setTotalQuantity(totalQuantity);                                  //预收入库总数
            newInbountOrder.setCreateTime(new Date());                                        //创建时间
            TraceLog traceLog = new TraceLog();                                               //操作日志
            Date date = new Date();
            traceLog.setOpTime(date.getTime());
            traceLog.setContent("创建入库单");
            traceLog.setOpName(rpcHeader.getUsername());
            traceLog.setOpUid(String.valueOf(rpcHeader.getTraceId()));
            newInbountOrder.setTracelog(TraceLogUtil.appendTraceLog(newInbountOrder.getTracelog(), traceLog));
            newInbountOrder.setCreateTime(new Date());

            List<InboundOrderItem> inboundOrderItemList = new ArrayList<>();
            if (allocationOrderItemList.size() > 0) {                                         //遍历入库单里面商品详情,组装入库商品的详情记录
                for (AllocationOrderItem allocationOrderItem : allocationOrderItemList) {
                    InboundOrderItem inboundOrderItem = new InboundOrderItem();                     //入库商品详情记录
                    inboundOrderItem.setInventoryType(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());     //入库类型
                    inboundOrderItem.setProjectId(allocationOrderItem.getProjectIdEnter());                         //项目id
//                inboundOrderItem.setPurchaseOrderNo(allocationOrderItem.getPurchaseOrderNo()); //采购单号
                    inboundOrderItem.setGongxiaoInboundOrderNo(allocationOrder.getGongxiaoInboundOrderNo());             //入库单号
                    inboundOrderItem.setBatchNo(allocationOrderItem.getBatchNo());                  //批次号
                    String inboundOrderItemNo = String.valueOf(CommonUtil.getOderNumber());
                    inboundOrderItem.setInboundOrderItemNo(inboundOrderItemNo);                     //商品入库单号
                    inboundOrderItem.setWarehouseId(allocationOrderItem.getWarehouseEnterId());         //仓库id
                    inboundOrderItem.setWarehouseName(allocationOrderItem.getWarehouseEnter());     //仓库名称
                    inboundOrderItem.setProductId(allocationOrderItem.getProductId());             //商品id
                    inboundOrderItem.setProductCode(allocationOrderItem.getProductCode());         //商品编码
                    inboundOrderItem.setProductName(allocationOrderItem.getProductName());         //商品名称
                    inboundOrderItem.setProductUnit(allocationOrderItem.getProductUnit());         //单位
                    inboundOrderItem.setTotalQuantity(allocationOrderItem.getAlloteQuantity());     //预约入库数量
                    inboundOrderItem.setCreateTime(new Date());                                     //入库时间
                    inboundOrderItemList.add(inboundOrderItem);
                }
                InBoundOrderDao inBoundOrderDao = applicationContext.getBean("inBoundOrderDao",InBoundOrderDao.class);
                InboundOrderItemDao inboundOrderItemDao = applicationContext.getBean("inboundOrderItemDao",InboundOrderItemDao.class);
                inBoundOrderDao.insertInStorageInfo(newInbountOrder);                               //将入库单信息记录到数据库
                inboundOrderItemDao.inserInboundOrderItemInfo(inboundOrderItemList);                //将入库单对应的明细记录到明细表
            }
            logger.info("#traceId={}# [OUT] get NotificationWmsAllocateTask.notifyWarehouseStockInbound() success", rpcHeader.traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    private void notifyWarehouseStockOutbound(AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList) {

        try {
            logger.info("#traceId={}# [IN][notifyWarehouseStockOutbound] start NotificationWmsAllocateTask.notifyWarehouseStockOutbound() params: allocationOrder={},allocationOrderItemList={}", rpcHeader.traceId, JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));
            OutboundOrder outboundOrder = new OutboundOrder();
            outboundOrder.setGongxiaoOutboundOrderNo(allocationOrder.getGongxiaoOutboundOrderNo());
            outboundOrder.setProjectId(allocationOrder.getProjectIdOut());
            outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_WAIT.getStatus());
            outboundOrder.setOutboundType(WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode());
            outboundOrder.setSourceChannel(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));
            outboundOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_FAIL.getStatus());
            outboundOrder.setPurchaseOrderNo(allocationOrder.getAllocateNo());
            outboundOrder.setWarehouseId(allocationOrder.getWarehouseOutId());
            outboundOrder.setWarehouseName(allocationOrder.getWarehouseOut());
            outboundOrder.setShippingMode(0);
            outboundOrder.setNote(allocationOrder.getNote());
            outboundOrder.setOutStockQuantity(0);
            outboundOrder.setImperfectQuantity(0);
            outboundOrder.setTransferQuantity(0);
            outboundOrder.setCanceledQuantity(0);
            TraceLog traceLog = new TraceLog();
            traceLog.setOpUid(rpcHeader.getUid());
            traceLog.setOpName(rpcHeader.getUsername());
            traceLog.setContent("创建出库单");
            List<TraceLog> traslogList = new ArrayList<>();
            traslogList.add(traceLog);
            outboundOrder.setTracelog(JSON.toJSONString(traslogList));
            outboundOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            outboundOrder.setCreatedByName(rpcHeader.getUsername());
            List<String> connectedProduct = new ArrayList<>();
            List<String> batchNoList = new ArrayList<>();
            int totalQuantity = 0;
            List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
            for (AllocationOrderItem record : allocationOrderItemList){
                OutboundOrderItem outboundOrderItem = new OutboundOrderItem();
                outboundOrderItem.setProjectId(allocationOrder.getProjectIdOut());
                outboundOrderItem.setGongxiaoOutboundOrderNo(allocationOrder.getGongxiaoOutboundOrderNo());
                outboundOrderItem.setBatchNo(record.getBatchNo());
                outboundOrderItem.setWarehouseId(record.getWarehouseOutId());
                outboundOrderItem.setWarehouseName(record.getWarehouseOut());
                outboundOrderItem.setPurchaseOrderNo(allocationOrder.getAllocateNo());
                outboundOrderItem.setItemStatus(false);
                outboundOrderItem.setProductCode(record.getProductCode());
                outboundOrderItem.setProductName(record.getProductName());
                outboundOrderItem.setProductUnit("个");
                outboundOrderItem.setTotalQuantity(record.getAlloteQuantity());
                outboundOrderItem.setOutStockQuantity(0);
                outboundOrderItem.setImperfectQuantity(0);
                outboundOrderItem.setCanceledQuantity(0);
                outboundOrderItem.setRealOutStockQuantity(0);
                outboundOrderItem.setDataVersion(Long.parseLong("0"));
                outboundOrderItem.setCreateTime(new Date());
                outboundOrderItemList.add(outboundOrderItem);
                connectedProduct.add(record.getProductCode());
                batchNoList.add(record.getBatchNo());
                totalQuantity += record.getAlloteQuantity();
            }
            outboundOrder.setBatchNo(JSON.toJSONString(batchNoList));
            outboundOrder.setConnectedProduct(JSON.toJSONString(connectedProduct));
            outboundOrder.setTotalQuantity(totalQuantity);
            OutBoundOrderDao outBoundOrderDao = applicationContext.getBean("outBoundOrderDao",OutBoundOrderDao.class);
            OutBoundOrderItemDao outBoundOrderItemDao = applicationContext.getBean("outBoundOrderItemDao",OutBoundOrderItemDao.class);
            outBoundOrderDao.insertOutStorageInfo(outboundOrder);
            outBoundOrderItemDao.insertOutboundOrderItem(outboundOrderItemList);
            logger.info("#traceId={}# [OUT] get NotificationWmsAllocateTask.notifyWarehouseStockOutbound() success", rpcHeader.traceId);

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    private void notifyWmsEnter(AllocationOrder allocationOrder,List<AllocationOrderItem> allocationOrderItemList) {
        logger.info("#traceId={}# [IN][notifyWarehouseStockOutbound] start NotificationWmsAllocateTask.notifyWarehouseStockOutbound() params: allocationOrder={},allocationOrderItemList={}", rpcHeader.traceId, JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));
        WmsInStockNotifyRequest wmsInStockNotifyRequest = new WmsInStockNotifyRequest();
        wmsInStockNotifyRequest.setCkid(allocationOrder.getWarehouseEnterId());                                           //仓库id  必选
        WarehouseDao warehouseDao = applicationContext.getBean("warehouseDao", WarehouseDao.class);
        String warehouseCode = warehouseDao.selectWarehouseCodeById(Long.parseLong(allocationOrder.getWarehouseEnterId()));    //查询仓库编码
        wmsInStockNotifyRequest.setWarehouseCode(warehouseCode);                                                         //仓库编码  必选
        wmsInStockNotifyRequest.setOrderNo(allocationOrder.getGongxiaoOutboundOrderNo());                                  //订单号    必选

        ChannelDao channelDao = applicationContext.getBean("channelDao", ChannelDao.class);
        Channel channel = channelDao.selectByChannelId(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));
        wmsInStockNotifyRequest.setCustCode(channel.getCustCode());                                                     //客户代码  必选
        wmsInStockNotifyRequest.setOrderType(WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode());                                            //操作类型/订单类型 必选
        wmsInStockNotifyRequest.setOrderSource(channel.getChannelName());                    //订单来源  必选
        wmsInStockNotifyRequest.setSourceOrderNo(allocationOrder.getAllocateNo());    //来源单号  可选
        wmsInStockNotifyRequest.setOrderCreateTime(allocationOrder.getCreateTime());          //订单创建时间 可选
//        wmsInStockNotifyRequest.setTmsServiceCode(inboundOrder.get);   //快递公司编码/车次号  可选
//        wmsInStockNotifyRequest.setTmsOrderCode();     //运单号/配送车牌  可选
//        wmsInStockNotifyRequest.setExpectStartTime();  //预期送达开始时间  可选
//        wmsInStockNotifyRequest.setBatchSendCtrlParam();//分批下发控制参数  可选
        ReceiverInfo receiverInfo = new ReceiverInfo();
        receiverInfo.setReceiverAddress(allocationOrder.getReceiveAddress());
        receiverInfo.setReceiverName(allocationOrder.getCompanyNameEnter());
        wmsInStockNotifyRequest.setReceiverInfo(receiverInfo);                      //收货方信息  必选
        SenderInfo senderInfo = new SenderInfo();
        senderInfo.setSenderAddress(allocationOrder.getDeliverAddress());
        senderInfo.setSenderName(allocationOrder.getCompanyNameOut());
        wmsInStockNotifyRequest.setSenderInfo(senderInfo);                          //发货方信息   必选
        List<StockInOrderItem> orderItemList = new ArrayList<>();
        for (AllocationOrderItem record : allocationOrderItemList) {
            StockInOrderItem stockInOrderItem = new StockInOrderItem();
            stockInOrderItem.setItemNo(record.getGongxiaoInboundOrderNo());         //行号
            stockInOrderItem.setItemCode(record.getProductCode());              //商品编码  必选
            stockInOrderItem.setItemName(record.getProductName());              //商品名称
            stockInOrderItem.setInventoryType(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());       //库存类型  必选
            stockInOrderItem.setItemQuantity(record.getAlloteQuantity());        //商品数量  必选
            stockInOrderItem.setBatchCode(record.getBatchNo());                 //批次号
            orderItemList.add(stockInOrderItem);
        }
        wmsInStockNotifyRequest.setOrderItemList(orderItemList);                      //订单商品信息  必选
        wmsInStockNotifyRequest.setRemark(allocationOrder.getNote());                 //备注   可选

        wmsNotificationInboundService.notificationWmsInboundInfo(wmsInStockNotifyRequest,warehouseConfig.getWmsUrl());
        logger.info("#traceId={}# [OUT] get NotificationWmsAllocateTask.notifyWmsEnter() success", rpcHeader.traceId);
    }


    private void notifyWmsOut(AllocationOrder allocationOrder,List<AllocationOrderItem> allocationOrderItemList) {
        WmsOutStockNotifyRequest wmsOutStockNotifyRequest = new WmsOutStockNotifyRequest();
        ChannelDao channelDao = applicationContext.getBean("channelDao", ChannelDao.class);
        Channel channel = channelDao.selectByChannelId(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));
        wmsOutStockNotifyRequest.setCustCode(channel.getCustCode());                       //客户代码  必选
        WarehouseDao warehouseDao = applicationContext.getBean("warehouseDao", WarehouseDao.class);
        String warehouseCode = warehouseDao.selectWarehouseCodeById(Long.parseLong(allocationOrder.getWarehouseOutId()));
        wmsOutStockNotifyRequest.setWarehouseCode(warehouseCode);                          //仓库编码  必选
        wmsOutStockNotifyRequest.setOrderNo(allocationOrder.getGongxiaoOutboundOrderNo());  //订单号    必选
        wmsOutStockNotifyRequest.setOrderSource(channel.getChannelName());                 //订单来源  必选
        wmsOutStockNotifyRequest.setOrderType(WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode());  //操作类型/订单类型 必选
        ReceiverInfo receiverInfo = new ReceiverInfo();
        receiverInfo.setReceiverAddress(allocationOrder.getReceiveAddress());
        receiverInfo.setReceiverName(allocationOrder.getCompanyNameEnter());
        SenderInfo senderInfo = new SenderInfo();
        senderInfo.setSenderAddress(allocationOrder.getDeliverAddress());
        senderInfo.setSenderName(allocationOrder.getCompanyNameOut());
        wmsOutStockNotifyRequest.setReceiverInfo(receiverInfo);                                    //收货方信息   必选
        wmsOutStockNotifyRequest.setSenderInfo(senderInfo);                                        //发货方信息   必选

        List<StockOutOrderItem> orderItemList = new ArrayList<>();
        for (AllocationOrderItem record : allocationOrderItemList) {
            StockOutOrderItem stockOutOrderItem = new StockOutOrderItem();
            stockOutOrderItem.setItemCode(record.getProductCode());             //商品编码  必选
            stockOutOrderItem.setItemName(record.getProductName());
            stockOutOrderItem.setInventoryType(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());   //库存类型  必选
            stockOutOrderItem.setItemQuantity(record.getAlloteQuantity());       //商品数量  必选
            stockOutOrderItem.setBatchCode(record.getBatchNo());       //批次号
            orderItemList.add(stockOutOrderItem);
        }
        wmsOutStockNotifyRequest.setOrderItemList(orderItemList);     //订单商品信息  必选
        wmsOutStockNotifyRequest.setRemark(allocationOrder.getNote());  //备注   可选
        wmsNotificationOutboundService.notificationWmsOutboundInfo(wmsOutStockNotifyRequest,warehouseConfig.getWmsUrl());
        logger.info("#traceId={}# [OUT] get NotificationWmsAllocateTask.notifyWmsEnter() success", rpcHeader.traceId);
    }

}
