package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.*;
import com.yhglobal.gongxiao.util.CommonUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.*;
import com.yhglobal.gongxiao.warehouse.model.*;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationInboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsNotificationOutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    private GongxiaoRpc.RpcHeader rpcHeader;
    private AllocationOrder allocationOrder;
    InBoundOrderDao inBoundOrderDao;
    InboundOrderItemDao inboundOrderItemDao;
    OutBoundOrderDao outBoundOrderDao;
    OutBoundOrderItemDao outBoundOrderItemDao;
    private List<AllocationOrderItem> allocationOrderItemList;
    private WarehouseConfig warehouseConfig;

    @Autowired
    WmsNotificationOutboundService wmsNotificationOutboundService;

    @Autowired
    WmsNotificationInboundService wmsNotificationInboundService;

    public NotificationWmsAllocateTask(GongxiaoRpc.RpcHeader rpcHeader, AllocationOrder allocationOrder, InBoundOrderDao inBoundOrderDao, InboundOrderItemDao inboundOrderItemDao,
                                       OutBoundOrderDao outBoundOrderDao, OutBoundOrderItemDao outBoundOrderItemDao, List<AllocationOrderItem> allocationOrderItemList, WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.allocationOrder = allocationOrder;
        this.inBoundOrderDao = inBoundOrderDao;
        this.inboundOrderItemDao = inboundOrderItemDao;
        this.outBoundOrderDao = outBoundOrderDao;
        this.outBoundOrderItemDao = outBoundOrderItemDao;
        this.allocationOrderItemList = allocationOrderItemList;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start NotificationWmsAllocateTask.run() params: allocationOrder={},allocationOrderItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));

        String outProjectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(allocationOrder.getProjectIdOut()));
        String inProjectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(allocationOrder.getProjectIdEnter()));

        /**
         * 1、调拨需要通知wms + tms + eas
         * 2、通知WMS调拨，需要创建对应的出库单和入库单，然后通过定时任务同步到WMS
         * 3、通知TMS, 调拨出库需要通知TMS, 这个步骤在 同步出库单到WMS成功的时候做
         * 4、通知EAS, 创建调拨单, 然后通过定时任务，将调拨单同步到EAS; 调拨单通过EAS审核通过之后,才将调拨入库单 和 调拨出库单 同步到wms
         */
        //创建出库单
        notifyWarehouseStockOutbound(allocationOrder, allocationOrderItemList, outProjectPrefix);

        //创建入库单
        notifyWarehouseStockInbound(allocationOrder, allocationOrderItemList, inProjectPrefix);
        logger.info("#traceId={}# [OUT] get NotificationWmsAllocateTask.run() success", rpcHeader.getTraceId());
    }


    private void notifyWarehouseStockInbound(AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList, String projectPrefix) {

        try {
            logger.info("#traceId={}# [IN][notifyWarehouseStockInbound] start NotificationWmsAllocateTask.notifyWarehouseStockInbound() params: allocationOrder={},allocationOrderItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));
            InboundOrder newInbountOrder = new InboundOrder();                                      //入库单记录
//        newInbountOrder.setUniqueNo(uniqueNo);

            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(allocationOrder.getProjectIdEnter()).build();
            ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = rpcResponse.getProject();
            String brandName = project.getSupplierName();
            newInbountOrder.setSupplierName(brandName);   //品牌商
            newInbountOrder.setInboundType(WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode());  //入库单类型 wms必传
            newInbountOrder.setProjectId(Long.valueOf(allocationOrder.getProjectIdEnter()));  //项目id
            newInbountOrder.setGongxiaoInboundOrderNo(allocationOrder.getGongxiaoInboundOrderNo());   //入库单号

            newInbountOrder.setSourceChannel(Integer.parseInt(WmsSourceChannel.CHANNEL_SHAVER.getChannelId()));   //发起入库通知单的渠道
            newInbountOrder.setOrderStatus(InboundOrderStatusEnum.INBOUND_ORDER_WAIT.getStatus());
            newInbountOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_WAIT.getStatus());   //因为调拨入库单需要 调拨单通过EAS审核通过后才能通知WMS,所以状态设置为“等待审核”
            newInbountOrder.setPurchaseOrderNo(allocationOrder.getAllocateNo());      //采购单号

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
            newInbountOrder.setWarehouseName(allocationOrder.getWarehouseEnter());                                  //仓库名称
//        newInbountOrder.setShippingMode(shippingMode);                                  //物流方式
            newInbountOrder.setNote(allocationOrder.getNote());                               //备注
            newInbountOrder.setTotalQuantity(totalQuantity);                                  //预收入库总数
            newInbountOrder.setCreateTime(new Date());                                        //创建时间
            TraceLog traceLog = new TraceLog();                                               //操作日志
            Date date = new Date();
            traceLog.setOpTime(date.getTime());
            traceLog.setContent("创建调拨入库单");
            traceLog.setOpName(rpcHeader.getUsername());
            traceLog.setOpUid(String.valueOf(rpcHeader.getTraceId()));
            newInbountOrder.setTracelog(TraceLogUtil.appendTraceLog(newInbountOrder.getTracelog(), traceLog));
            newInbountOrder.setCreateTime(new Date());

            List<InboundOrderItem> inboundOrderItemList = new ArrayList<>();
            if (allocationOrderItemList.size() > 0) {                                         //遍历入库单里面商品详情,组装入库商品的详情记录
                for (AllocationOrderItem allocationOrderItem : allocationOrderItemList) {
                    InboundOrderItem inboundOrderItem = new InboundOrderItem();                     //入库商品详情记录
                    inboundOrderItem.setInventoryType(allocationOrderItem.getInventoryStatus());     //入库类型
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
                    inboundOrderItem.setPurchasePrice(allocationOrderItem.getPuchasePrice());
                    inboundOrderItem.setCostPrice(allocationOrderItem.getCostPrice());
                    inboundOrderItem.setTotalQuantity(allocationOrderItem.getAlloteQuantity());     //预约入库数量
                    inboundOrderItem.setCreateTime(new Date());                                     //入库时间
                    inboundOrderItemList.add(inboundOrderItem);
                }
                inBoundOrderDao.insertInStorageInfo(newInbountOrder, projectPrefix);                               //将入库单信息记录到数据库
                inboundOrderItemDao.inserInboundOrderItemInfo(inboundOrderItemList, projectPrefix);                //将入库单对应的明细记录到明细表
            }
            logger.info("#traceId={}# [OUT] get NotificationWmsAllocateTask.notifyWarehouseStockInbound() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

    private void notifyWarehouseStockOutbound(AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList, String projectPrefix) {

        try {
            logger.info("#traceId={}# [IN][notifyWarehouseStockOutbound] start NotificationWmsAllocateTask.notifyWarehouseStockOutbound() params: allocationOrder={},allocationOrderItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));
            OutboundOrder outboundOrder = new OutboundOrder();
            outboundOrder.setGongxiaoOutboundOrderNo(allocationOrder.getGongxiaoOutboundOrderNo());
            outboundOrder.setProjectId(allocationOrder.getProjectIdOut());
            outboundOrder.setCustomerId("10001");    //客户id 因为调拨面对的是自己,暂时写死,后面将出库单通知wms的时候需要根据这个字段来查客户信息，需要把客户编码，和客户名称传给WMS
            outboundOrder.setCustomer("深圳越海全球供应链有限公司");   //客户名称
            outboundOrder.setOrderStatus(OutboundOrderStatusEnum.OUTBOUND_ORDER_WAIT.getStatus());
            outboundOrder.setOutboundType(WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode());
            outboundOrder.setSourceChannel(Integer.parseInt(WmsSourceChannel.CHANNEL_SHAVER.getChannelId()));
            outboundOrder.setSyncToWmsFlag(SyncWmsEnum.SYNC_WMS_WAIT.getStatus()); //因为调拨出库单需要等待 调拨单通过EAS审核通过后才能 通知WMS,所以设为“等待审核”
            outboundOrder.setSalesOrderNo(allocationOrder.getAllocateNo());
            outboundOrder.setWarehouseId(allocationOrder.getWarehouseOutId());
            outboundOrder.setWarehouseName(allocationOrder.getWarehouseOut());
            //调用基础模块的仓库service
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(allocationOrder.getWarehouseEnterId()).build();
            WarehouseStructure.GetWarehouseByIdResp rpcResponse = warehouseService.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = rpcResponse.getWarehouse();

            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq2 = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(allocationOrder.getWarehouseOutId()).build();
            WarehouseStructure.GetWarehouseByIdResp rpcResponse2 = warehouseService.getWarehouseById(getWarehouseByIdReq2);
            WarehouseStructure.Warehouse warehouse2 = rpcResponse2.getWarehouse();

            outboundOrder.setDeliverAddress(warehouse.getShortAddress());
            outboundOrder.setShippingAddress(warehouse2.getShortAddress());
            outboundOrder.setProvinceName(warehouse2.getProvinceName());
            outboundOrder.setCityName(warehouse2.getCityName());
            outboundOrder.setShippingMode(0);
            outboundOrder.setNote(allocationOrder.getNote());
            outboundOrder.setOutStockQuantity(0);
            outboundOrder.setImperfectQuantity(0);
            outboundOrder.setTransferQuantity(0);
            outboundOrder.setCanceledQuantity(0);
            TraceLog traceLog = new TraceLog();
            traceLog.setOpUid(rpcHeader.getUid());
            traceLog.setOpName(rpcHeader.getUsername());
            traceLog.setContent("创建调拨出库单");
            List<TraceLog> traslogList = new ArrayList<>();
            traslogList.add(traceLog);
            outboundOrder.setTracelog(JSON.toJSONString(traslogList));
            outboundOrder.setCreatedById(Long.parseLong(rpcHeader.getUid()));
            outboundOrder.setCreatedByName(rpcHeader.getUsername());
            List<String> connectedProduct = new ArrayList<>();
            List<String> batchNoList = new ArrayList<>();
            int totalQuantity = 0;
            List<OutboundOrderItem> outboundOrderItemList = new ArrayList<>();
            for (AllocationOrderItem record : allocationOrderItemList) {
                OutboundOrderItem outboundOrderItem = new OutboundOrderItem();
                outboundOrderItem.setInventoryType(record.getInventoryStatus());
                outboundOrderItem.setProjectId(allocationOrder.getProjectIdOut());
                outboundOrderItem.setGongxiaoOutboundOrderNo(allocationOrder.getGongxiaoOutboundOrderNo());
                outboundOrderItem.setBatchNo(record.getBatchNo());
                outboundOrderItem.setWarehouseId(record.getWarehouseOutId());
                outboundOrderItem.setWarehouseName(record.getWarehouseOut());
                outboundOrderItem.setPurchaseOrderNo(allocationOrder.getAllocateNo());
                outboundOrderItem.setItemStatus(false);
                outboundOrderItem.setProductId(record.getProductId());
                outboundOrderItem.setProductCode(record.getProductCode());
                outboundOrderItem.setProductName(record.getProductName());
                outboundOrderItem.setProductUnit("GE");
                outboundOrderItem.setSalesGuidePrice(record.getGuidPrice());
                outboundOrderItem.setWholesalePrice(record.getCostPrice());
                outboundOrderItem.setPurchaseType(record.getPurchaseType());
                outboundOrderItem.setTotalQuantity(record.getAlloteQuantity());
                outboundOrderItem.setReturnQuantity(record.getAlloteQuantity());
                outboundOrderItem.setOutStockQuantity(0);
                outboundOrderItem.setImperfectQuantity(0);
                outboundOrderItem.setCanceledQuantity(0);
                outboundOrderItem.setRealOutStockQuantity(0);
                outboundOrderItem.setDataVersion((long) 0);
                outboundOrderItem.setCreateTime(new Date());
                outboundOrderItemList.add(outboundOrderItem);
                connectedProduct.add(record.getProductCode());
                batchNoList.add(record.getBatchNo());
                totalQuantity += record.getAlloteQuantity();
            }
            outboundOrder.setBatchNo(JSON.toJSONString(batchNoList));
            outboundOrder.setConnectedProduct(JSON.toJSONString(connectedProduct));
            outboundOrder.setTotalQuantity(totalQuantity);
            outBoundOrderDao.insertOutStorageInfo(outboundOrder, projectPrefix);
            outBoundOrderItemDao.insertOutboundOrderItem(outboundOrderItemList, projectPrefix);
            logger.info("#traceId={}# [OUT] get NotificationWmsAllocateTask.notifyWarehouseStockOutbound() success", rpcHeader.getTraceId());

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }
}
