package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.foundation.distributor.dao.DistributorDao;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.customer.service.OutstockService;
import com.yhglobal.gongxiao.warehouse.service.ManualOutboundService;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsOutboundTask;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehousemanagement.dao.ManualOutboundDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.ManualOutboundItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.model.*;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.OutboundOrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ManualOutboundServiceImpl implements ManualOutboundService {

    private static Logger logger = LoggerFactory.getLogger(ManualOutboundServiceImpl.class);

    @Autowired
    ManualOutboundDao manualOutboundDao;

    @Autowired
    ManualOutboundItemDao manualOutboundItemDao;

    @Autowired
    OutBoundOrderDao outBoundOrderDao;

    @Autowired
    OutBoundOrderItemDao outBoundOrderItemDao;

    @Autowired
    DistributorDao distributorDao;

    @Autowired
    WarehouseDao warehouseDao;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Reference(check = false)
    ProductService productService;

    @Reference(check = false)
    WarehouseService warehouseService;

    @Reference(check = false)
    ProjectService projectService;

    @Reference(check = false)
    SalesScheduleDeliveryService salesScheduleDeliveryService;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Autowired
    OutstockService outstockService;

    @Override
    public List<ManualOutboundOrder> getManualOutboundList(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][getManualOutboundList] params: projectId={},inventoryNum={}", rpcHeader.getTraceId());
        List<ManualOutboundOrder> resultList = manualOutboundDao.selectAllRecord();
        logger.info("#traceId={}# [OUT] get getManualOutboundList success: resultList.size:{}", rpcHeader.getTraceId(), resultList.size());
        return resultList;
    }

    @Override
    public int createManualOutbound(RpcHeader rpcHeader, ManualOutboundOrder manualOutboundOrder, List<CreateOutboundItemInfo> createOutboundItemInfos) {
        try {
            logger.info("#traceId={}# [IN][createManualOutbound] params: manualOutboundOrder={},createOutboundItemInfos={}", rpcHeader.getTraceId(), JSON.toJSONString(manualOutboundOrder), JSON.toJSONString(createOutboundItemInfos));
            String orderNo = DateTimeIdGenerator.nextId(BizNumberType.OTHER_OUT_NO);
            String gongxiaoOutboundOrderNo =DateTimeIdGenerator.nextId(BizNumberType.STOCK_OTHER_OUT_NO);

            int totalQuantity = 0;
            List<String> conectedProduct = new ArrayList<>();
            List<String> conectedGood = new ArrayList<>();
            manualOutboundOrder.setOrderNo(orderNo);
            manualOutboundOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
            int i = manualOutboundDao.insertManualOutboundInfo(manualOutboundOrder);
            if (0 < i) {
                List<ManualOutboundOrderItem> manualOutboundOrderItemList = new ArrayList<>();
                for (CreateOutboundItemInfo record : createOutboundItemInfos) {
                    ManualOutboundOrderItem manualOutboundOrderItem = new ManualOutboundOrderItem();
                    manualOutboundOrderItem.setProjectId(Integer.parseInt(manualOutboundOrder.getProjectId()));
                    manualOutboundOrderItem.setOrderNo(orderNo);
                    manualOutboundOrderItem.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
                    manualOutboundOrderItem.setBatchNo(record.getBatchNo());
                    manualOutboundOrderItem.setOutboundType(manualOutboundOrder.getOutboundType());
                    manualOutboundOrderItem.setStatus(0);
                    manualOutboundOrderItem.setProductId(record.getProductId());
                    manualOutboundOrderItem.setProductCode(record.getProductCode());
                    manualOutboundOrderItem.setProductName(record.getProductName());
                    manualOutboundOrderItem.setProductUnit("个");
                    manualOutboundOrderItem.setGuidePrice(record.getGuidePrice());
                    manualOutboundOrderItem.setWholesalePriceDouble(record.getWholesalePriceDouble());
                    manualOutboundOrderItem.setNote(manualOutboundOrder.getNote());
                    manualOutboundOrderItem.setWarehouseId(Integer.parseInt(manualOutboundOrder.getWarehouseId()));
                    manualOutboundOrderItem.setWarehouseName(manualOutboundOrder.getWarehouseName());
                    manualOutboundOrderItem.setQuantity(record.getQuantity());
                    manualOutboundOrderItem.setCreateTime(manualOutboundOrder.getCreateTime());
                    manualOutboundOrderItemList.add(manualOutboundOrderItem);

                    totalQuantity += record.getQuantity();
                    conectedProduct.add(record.getProductCode());
                    conectedGood.add(record.getProductCode());
                }
                manualOutboundItemDao.insertOrderList(manualOutboundOrderItemList);
            }



//            StringBuilder outboundBatchNo = new StringBuilder();

            for (CreateOutboundItemInfo record : createOutboundItemInfos) {
                String outboundOrderItemNo = DateTimeIdGenerator.nextId(BizNumberType.STOCK_SOOUT_ORDER_ITEM_NO);   //出库明细单号
                //1、仓储模块记录出库单
                OutboundOrder outboundOrder = new OutboundOrder();
                String salesOrderNo = DateTimeIdGenerator.nextId(BizNumberType.SALES_ORDER_NO);
                outboundOrder.setSourceChannel(Integer.parseInt(WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId()));          //发起出库通知单的渠道
                outboundOrder.setProjectId(manualOutboundOrder.getProjectId());
                outboundOrder.setOutboundType(manualOutboundOrder.getOutboundType());                                //订单类型
//            outboundOrder.setSupplierName(supplierName);                                                       //供应商
                outboundOrder.setWarehouseId(manualOutboundOrder.getWarehouseId());                                  //仓库id
                outboundOrder.setWarehouseName(manualOutboundOrder.getWarehouseName());                              //仓库名称
                outboundOrder.setConnectedProduct(record.getProductCode());                                          //出库商品
                outboundOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));                                    //创建人id
                outboundOrder.setCreatedByName(rpcHeader.getUsername());                                             //创建人
                String deliverAddress = warehouseDao.selectDeliverAddressById(manualOutboundOrder.getWarehouseId());
                outboundOrder.setShippingAddress(deliverAddress);                           //发货地址
                outboundOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);          //出库单号
                outboundOrder.setOutboundOrderItemNo(outboundOrderItemNo);
                outboundOrder.setSalesOrderNo(salesOrderNo);                              //销售单号
                outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_WAIT.getStatus());    //出库单状态
                outboundOrder.setTotalQuantity(totalQuantity);                              //数量
                outboundOrder.setNote(manualOutboundOrder.getNote());                       //备注
                outboundOrder.setBatchNo(record.getBatchNo());                              //批次
                outboundOrder.setConnectedProduct(record.getProductCode());
                List<TraceLog> traceLogList = new ArrayList<>();
                TraceLog traceLog = new TraceLog();
                Date date = new Date();
                traceLog.setOpTime(date.getTime());
                traceLog.setContent("创建出库单");
                traceLog.setOpName(rpcHeader.getUsername());
                traceLog.setOpUid(String.valueOf(rpcHeader.getTraceId()));
                traceLogList.add(traceLog);
                outboundOrder.setTracelog(JSON.toJSONString(traceLogList));
                outboundOrder.setCreateTime(date);

                //1、仓储模块记录出库单明细
                OutboundOrderItem outboundOrderItem = new OutboundOrderItem();
                List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
                outboundOrderItem.setBatchNo(record.getBatchNo());                              //批次号
                outboundOrderItem.setProjectId(manualOutboundOrder.getProjectId());             //项目id
                outboundOrderItem.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);          //出库单号
                outboundOrderItem.setOutboundOrderItemNo(outboundOrderItemNo);                  //出库明细单号
                outboundOrderItem.setInventoryType(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());  //出库类型默认为良品
                outboundOrderItem.setOutboundOrderItemNo(String.valueOf(CommonUtil.getOderNumber()));         //出库单明细单号
                outboundOrderItem.setSalesOrderNo(salesOrderNo);                                     //销售单号
                outboundOrderItem.setProductCode(record.getProductCode());                      //商品编码
                outboundOrderItem.setProductName(record.getProductName());                      //商品名称
                outboundOrderItem.setWarehouseId(manualOutboundOrder.getWarehouseId());         //仓库id
                outboundOrderItem.setWarehouseName(manualOutboundOrder.getWarehouseName());     //仓库名称
                outboundOrderItem.setTotalQuantity(record.getQuantity());                       //数量
                outboundOrderItem.setCreateTime(manualOutboundOrder.getCreateTime());           //创建时间
                outboundOrderItemList.add(outboundOrderItem);

                outBoundOrderDao.insertOutStorageInfo(outboundOrder);
                outBoundOrderItemDao.insertOutboundOrderItem(outboundOrderItemList);

                //2、通知WMS入库
                NotificationWmsOutboundTask task = new NotificationWmsOutboundTask(rpcHeader,ApplicationContextProvider.getApplicationContext(), outboundOrder, outboundOrderItemList,outBoundOrderDao,warehouseService,projectService,productService,warehouseConfig,salesScheduleDeliveryService);
                threadPoolTaskExecutor.submit(task);
                outstockService.createInstockRecor(outboundOrder, outboundOrderItemList);
            }
            logger.info("#traceId={}# [OUT] get createManualOutbound success: resultList.size:{}", rpcHeader.getTraceId(), i);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public int updateManualOutboundOrder(RpcHeader rpcHeader, ManualOutboundOrder manualOutboundOrder) {
        try {
            logger.info("#traceId={}# [IN][updateManualOutboundOrder] params: ", rpcHeader.traceId);
            int i = manualOutboundDao.updateManualOutboundInfo(manualOutboundOrder);
            logger.info("#traceId={}# [OUT] updateManualOutboundOrder success: ", rpcHeader.traceId);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
