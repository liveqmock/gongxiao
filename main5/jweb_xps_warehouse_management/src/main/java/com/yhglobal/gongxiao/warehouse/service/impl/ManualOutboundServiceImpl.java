package com.yhglobal.gongxiao.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.*;
import com.yhglobal.gongxiao.warehouse.model.*;
import com.yhglobal.gongxiao.warehouse.service.ManualOutboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationOutboundService;
import com.yhglobal.gongxiao.warehouse.task.NotificationWmsOutboundTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

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
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    WarehouseConfig warehouseConfig;

    @Autowired
    WmsNotificationOutboundService wmsNotificationOutboundService;

    @Override
    public List<ManualOutboundOrder> getManualOutboundList(GongxiaoRpc.RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][getManualOutboundList] params: projectId={},inventoryNum={}", rpcHeader.getTraceId());
        List<ManualOutboundOrder> resultList = manualOutboundDao.selectAllRecord();
        logger.info("#traceId={}# [OUT] get getManualOutboundList success: resultList.size:{}", rpcHeader.getTraceId(), resultList.size());
        return resultList;
    }

    @Override
    public int createManualOutbound(GongxiaoRpc.RpcHeader rpcHeader, ManualOutboundOrder manualOutboundOrder, List<CreateOutboundItemInfo> createOutboundItemInfos) {
        try {
            logger.info("#traceId={}# [IN][createManualOutbound] params: manualOutboundOrder={},createOutboundItemInfos={}", rpcHeader.getTraceId(), JSON.toJSONString(manualOutboundOrder), JSON.toJSONString(createOutboundItemInfos));
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(manualOutboundOrder.getProjectId()));
            String orderNo = DateTimeIdGenerator.nextId(projectPrefix,BizNumberType.OTHER_OUT_NO);
            String gongxiaoOutboundOrderNo =DateTimeIdGenerator.nextId(projectPrefix,BizNumberType.STOCK_OTHER_OUT_NO);

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
                String outboundOrderItemNo = DateTimeIdGenerator.nextId(projectPrefix,BizNumberType.STOCK_SOOUT_ORDER_ITEM_NO);   //出库明细单号
                //1、仓储模块记录出库单
                OutboundOrder outboundOrder = new OutboundOrder();
                String salesOrderNo = DateTimeIdGenerator.nextId(projectPrefix,BizNumberType.SALES_ORDER_NO);
                //todo:怎么根据获取对应的channelId
                outboundOrder.setSourceChannel(Integer.parseInt(WmsSourceChannel.CHANNEL_SHAVER.getChannelId()));          //发起出库通知单的渠道
                outboundOrder.setProjectId(manualOutboundOrder.getProjectId());
                outboundOrder.setOutboundType(manualOutboundOrder.getOutboundType());                                //订单类型
//            outboundOrder.setSupplierName(supplierName);                                                       //供应商
                outboundOrder.setWarehouseId(manualOutboundOrder.getWarehouseId());                                  //仓库id
                outboundOrder.setWarehouseName(manualOutboundOrder.getWarehouseName());                              //仓库名称
                outboundOrder.setConnectedProduct(record.getProductCode());                                          //出库商品
                outboundOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));                                    //创建人id
                outboundOrder.setCreatedByName(rpcHeader.getUsername());                                             //创建人

                //调用基础模块的仓库grpc服务查仓库编码
                WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
                WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(manualOutboundOrder.getWarehouseId()).build();
                WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
                WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();
                String deliverAddress = warehouse.getStreetAddress();
                outboundOrder.setShippingAddress(deliverAddress);
                outboundOrder.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
                outboundOrder.setOutboundOrderItemNo(outboundOrderItemNo);
                outboundOrder.setSalesOrderNo(salesOrderNo);
                outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_WAIT.getStatus());    //出库单状态
                outboundOrder.setTotalQuantity(totalQuantity);
                outboundOrder.setNote(manualOutboundOrder.getNote());
                outboundOrder.setBatchNo(record.getBatchNo());   //批次
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
                outboundOrderItem.setProjectId(manualOutboundOrder.getProjectId());
                outboundOrderItem.setGongxiaoOutboundOrderNo(gongxiaoOutboundOrderNo);
                outboundOrderItem.setOutboundOrderItemNo(outboundOrderItemNo);                  //出库明细单号
                outboundOrderItem.setInventoryType(1);                                          //出库类型默认为良品
                outboundOrderItem.setOutboundOrderItemNo(String.valueOf(CommonUtil.getOderNumber()));
                outboundOrderItem.setSalesOrderNo(salesOrderNo);
                outboundOrderItem.setProductCode(record.getProductCode());
                outboundOrderItem.setProductName(record.getProductName());
                outboundOrderItem.setWarehouseId(manualOutboundOrder.getWarehouseId());
                outboundOrderItem.setWarehouseName(manualOutboundOrder.getWarehouseName());
                outboundOrderItem.setTotalQuantity(record.getQuantity());
                outboundOrderItem.setCreateTime(manualOutboundOrder.getCreateTime());
                outboundOrderItemList.add(outboundOrderItem);

                outBoundOrderDao.insertOutStorageInfo(outboundOrder,projectPrefix);
                outBoundOrderItemDao.insertOutboundOrderItem(outboundOrderItemList,projectPrefix);

                //2、通知WMS入库
                NotificationWmsOutboundTask outboundTask = new NotificationWmsOutboundTask(rpcHeader, outboundOrder, outboundOrderItemList, outBoundOrderDao, wmsNotificationOutboundService, warehouseConfig);
                threadPoolTaskExecutor.submit(outboundTask);

            }
            logger.info("#traceId={}# [OUT] get createManualOutbound success: resultList.size:{}", rpcHeader.getTraceId(), i);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public int updateManualOutboundOrder(GongxiaoRpc.RpcHeader rpcHeader, ManualOutboundOrder manualOutboundOrder) {
        try {
            logger.info("#traceId={}# [IN][updateManualOutboundOrder] params: ", rpcHeader.getTraceId());
            int i = manualOutboundDao.updateManualOutboundInfo(manualOutboundOrder);
            logger.info("#traceId={}# [OUT] updateManualOutboundOrder success: ", rpcHeader.getTraceId());
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
