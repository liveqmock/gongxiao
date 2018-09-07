package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorysync.microservice.InventorysyncStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.Result;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.XpsWarehouseNotifyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 由前端取消按钮触发该任务 通知Wms取消出库单
 */
public class NotificationWmsCancelOutTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotificationWmsCancelEntryTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private String projectId;

    private WmsCancelOrderService wmsCancelOrderService;

    private WmsCancelOrderRequest wmsCancelOrderRequest;

    private OutBoundOrderDao outBoundOrderDao;

    private OutBoundOrderItemDao outBoundOrderItemDao;

    private WarehouseConfig warehouseConfig;


    public NotificationWmsCancelOutTask(GongxiaoRpc.RpcHeader rpcHeader, String projectId, WmsCancelOrderService wmsCancelOrderService, WmsCancelOrderRequest wmsCancelOrderRequest,
                                        OutBoundOrderDao outBoundOrderDao, OutBoundOrderItemDao outBoundOrderItemDao, WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.projectId = projectId;
        this.wmsCancelOrderService = wmsCancelOrderService;
        this.wmsCancelOrderRequest = wmsCancelOrderRequest;
        this.outBoundOrderDao = outBoundOrderDao;
        this.outBoundOrderItemDao = outBoundOrderItemDao;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("[IN][run] start NotificationWmsCancelOutTask.run() params: wmsCancelOrderRequest={}", JSON.toJSONString(wmsCancelOrderRequest));
        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
        OutboundOrder outboundOrder = outBoundOrderDao.getOutStorageInfoById(projectId, wmsCancelOrderRequest.getOrderNo(), projectPrefix);
        List<OutboundOrderItem> itemList = outBoundOrderItemDao.selectOutboundOrderItemByNo(wmsCancelOrderRequest.getOrderNo(), projectPrefix);
        Date dateTime = new Date();
        try {
            /**
             * 调用wms取消订单的接口，尝试10次
             * 操作1：通知wms取消订单但成功之后,修改库存销售占用
             * 操作2：通知销售模块取消销售订单
             */
            int maxRetriesTime = 10;
            WmsCanselOrderResp cancelResult = null;
            TraceLog successTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "取消订单成功");

            while (maxRetriesTime-- > 0) {
                String result = wmsCancelOrderService.cancelOrderService(wmsCancelOrderRequest, warehouseConfig.getWmsUrl());   //通知WMS系统取消出库单
                cancelResult = JSON.parseObject(result, new TypeReference<WmsCanselOrderResp>() {
                });
                if (cancelResult != null) {
                    if (cancelResult.isSuccess()) {    //先判断是否成功接收到wms反馈信息
                        Result handleResult = cancelResult.getResult();
                        if (handleResult.isSuccess()) {      //再判断，是否取消订单成功

                            if (handleResult.getOrderNo().contains(BizNumberType.STOCK_POOUT_ORDER_NO.getPrefix())) {  //取消采购退货出库单
                                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), successTraceLog);
                                outBoundOrderDao.cancelOrder(handleResult.getOrderNo(), OutboundOrderStatusEnum.OUTBOUND_ORDER_CANCEL.getStatus(), newTraceLog, projectPrefix);
                                outBoundOrderItemDao.cancelOrder(handleResult.getOrderNo(), projectPrefix);
                            } else if (handleResult.getOrderNo().contains(BizNumberType.STOCK_SOOUT_ORDER_NO.getPrefix())) {  //取消销售出库单
                                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), successTraceLog);
                                outBoundOrderDao.cancelOrder(handleResult.getOrderNo(), OutboundOrderStatusEnum.OUTBOUND_ORDER_CANCEL.getStatus(), newTraceLog, projectPrefix);
                                outBoundOrderItemDao.cancelOrder(handleResult.getOrderNo(), projectPrefix);

                                //通知销售模块取消销售订单
                                XpsWarehouseNotifyManager.cancelOutbound(warehouseConfig.getWarehouseNtf(),outboundOrder.getGongxiaoOutboundOrderNo(),Long.parseLong(outboundOrder.getProjectId()));
                                try {
                                    //调用库存模块的更新库存
                                    InventorysyncServiceGrpc.InventorysyncServiceBlockingStub inventorysyncService = WarehouseRpcStubStore.getRpcStub(InventorysyncServiceGrpc.InventorysyncServiceBlockingStub.class);
                                    for (OutboundOrderItem record : itemList) {  //根据出库明细更新库存信息,释放销售占用数量,恢复库存数量
                                        InventorysyncStructure.ResumeOnsalesQuantiryRequest resumeOnsalesQuantiryRequest = InventorysyncStructure.ResumeOnsalesQuantiryRequest.newBuilder()
                                                .setRpcHeader(rpcHeader)
                                                .setTotalQuantity(record.getTotalQuantity())
                                                .setPurchaseType(record.getPurchaseType())
                                                .setInventoryType(record.getInventoryType())
                                                .setProjectId(Long.parseLong(projectId))
                                                .setBatchNo(record.getBatchNo())
                                                .setProductCode(record.getProductCode())
                                                .setWarehouseId(Integer.parseInt(record.getWarehouseId())).build();
                                        inventorysyncService.resumeOnsalesQuantiry(resumeOnsalesQuantiryRequest);
                                    }
                                } catch (Exception e) {
                                    logger.info("============释放批次销售占用数量的时候失败==========");
                                    logger.info("# errorMessage:" + e.getMessage(), e);
                                }
                            } else if (handleResult.getOrderNo().contains(BizNumberType.STOCK_OTHER_OUT_NO.getPrefix())) {  //取消其他出库单
                                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), successTraceLog);
                                outBoundOrderDao.cancelOrder(handleResult.getOrderNo(), OutboundOrderStatusEnum.OUTBOUND_ORDER_CANCEL.getStatus(), newTraceLog, projectPrefix);
                                outBoundOrderItemDao.cancelOrder(handleResult.getOrderNo(), projectPrefix);
                            } else {     //后面有调拨出库
                                return;
                            }

                        } else {        //wms取消订单失败
                            TraceLog failTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "wms取消订单失败:" + handleResult.getErrorMsg());
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                            outBoundOrderDao.cancelOrderFail(handleResult.getOrderNo(), newTraceLog, projectPrefix);
                        }
                    } else {    //wms无法取消订单
                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "wms无法取消订单" + cancelResult.getError());
                        String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                        outBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(), newTraceLog, projectPrefix);
                    }
                    break;
                }
            }
            if (maxRetriesTime == 0) {    //如果重试了10次还是不行，打印错误日志
                logger.error("# errorMessage:通知WMS取消订单的时候超时了");
                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "通知wms取消出库订单超时");
                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
                outBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(), newTraceLog, projectPrefix);
            }

            logger.info("[OUT] get NotificationWmsCancelOutTask.run() success");
        } catch (Exception e) {
            TraceLog failTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "系统内部错误");
            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(), failTraceLog);
            outBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(), newTraceLog, projectPrefix);
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
