package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.InboundOrderStatusEnum;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.Result;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 由前端取消按钮触发该任务 通知Wms取消入库单
 */
public class NotificationWmsCancelEntryTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotificationWmsCancelEntryTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private String projectId;

    private WmsCancelOrderService wmsCancelOrderService;

    private WmsCancelOrderRequest wmsCancelOrderRequest;

    private InBoundOrderDao inBoundOrderDao;

    private InboundOrderItemDao inboundOrderItemDao;

    private WarehouseConfig warehouseConfig;

    public NotificationWmsCancelEntryTask(GongxiaoRpc.RpcHeader rpcHeader, String projectId, WmsCancelOrderService wmsCancelOrderService, WmsCancelOrderRequest wmsCancelOrderRequest, InBoundOrderDao inBoundOrderDao,
                                          InboundOrderItemDao inboundOrderItemDao, WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.projectId = projectId;
        this.wmsCancelOrderService = wmsCancelOrderService;
        this.wmsCancelOrderRequest = wmsCancelOrderRequest;
        this.inBoundOrderDao = inBoundOrderDao;
        this.inboundOrderItemDao = inboundOrderItemDao;
        this.warehouseConfig = warehouseConfig;

    }

    @Override
    public void run() {
        logger.info("[IN][run] start NotificationWmsCancelEntryTask.run() params: wmsCancelOrderRequest={}", JSON.toJSONString(wmsCancelOrderRequest));
        //调用基础模块的项目的grpc查询项目信息
        ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
        ProjectStructure.GetByProjectIdResp projectIdResp = projectService.getByProjectId(getByProjectIdReq);
        ProjectStructure.Project project = projectIdResp.getProject();
        String prefix = project.getProjectTablePrefix();
        InboundOrder inboundOrder = inBoundOrderDao.selectRecordByOrderNo(projectId, wmsCancelOrderRequest.getOrderNo(), prefix);
        Date dateTime = new Date();
        try {
            /**
             * 调用wms取消订单的接口，尝试10次
             * 操作1：通知采购模块取消采购订单 todo:采购还未提供接口
             */
            WmsCanselOrderResp cancelResult = null;
            TraceLog successTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "取消入库订单成功");
            int maxRetriesTime = 10;
            while (maxRetriesTime-- > 0) {
                String httpResult = wmsCancelOrderService.cancelOrderService(wmsCancelOrderRequest, warehouseConfig.getWmsUrl());   //通知WMS系统取消入库单
                cancelResult = JSON.parseObject(httpResult, new TypeReference<WmsCanselOrderResp>() {
                });
                if (cancelResult != null) {
                    if (cancelResult.isSuccess()) {    //先判断是否成功接收到wms反馈信息
                        Result handleResult = cancelResult.getResult();
                        if (handleResult.isSuccess()) {      //再判断wms是否取消订单成功
                            if (handleResult.getOrderNo().contains(BizNumberType.STOCK_POIN_ORDER_NO.getPrefix())) {    //取消采购入库单

                                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), successTraceLog);
                                inBoundOrderDao.cancelOrder(handleResult.getOrderNo(), InboundOrderStatusEnum.INBOUND_ORDER_CANCEL.getStatus(), newTraceLog, prefix);      //增加状态为“取消中”---跳转为“已取消”(添加tracelog日志)
                                inboundOrderItemDao.cancelOrder(handleResult.getOrderNo(), prefix);

                            } else if (handleResult.getOrderNo().contains(BizNumberType.STOCK_SOIN_ORDER_NO.getPrefix())) {  //取消销售退货入库单
                                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), successTraceLog);
                                inBoundOrderDao.cancelOrder(handleResult.getOrderNo(), InboundOrderStatusEnum.INBOUND_ORDER_CANCEL.getStatus(), newTraceLog, prefix);
                                inboundOrderItemDao.cancelOrder(handleResult.getOrderNo(), prefix);
                            } else if (handleResult.getOrderNo().contains(BizNumberType.STOCK_OTHER_IN_NO.getPrefix())) {  //取消其他入库单
                                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), successTraceLog);
                                inBoundOrderDao.cancelOrder(handleResult.getOrderNo(), InboundOrderStatusEnum.INBOUND_ORDER_CANCEL.getStatus(), newTraceLog, prefix);
                                inboundOrderItemDao.cancelOrder(handleResult.getOrderNo(), prefix);
                            } else {     //后面有调拨入库单
                                return;
                            }

                        } else {    //wms取消订单失败
                            TraceLog failTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "wms取消入库订单失败:" + handleResult.getErrorMsg());
                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                            inBoundOrderDao.cancelOrderFail(handleResult.getOrderNo(), newTraceLog, prefix);
                        }
                    } else {     //wms无法取消订单
                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "wms无法取消入库订单" + cancelResult.getError());
                        String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                        inBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(), newTraceLog, prefix);
                    }
                }
                break;
            }

            if (maxRetriesTime == 0) {    //如果重试了10次还是不行，打印错误日志
                logger.error("# errorMessage:通知WMS取消订单的时候超时了");
                TraceLog failTraceLog = new TraceLog(dateTime.getTime(), rpcHeader.getTraceId(), rpcHeader.getUsername(), "通知wms取消入库订单超时");
                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(), failTraceLog);
                inBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(), newTraceLog, prefix);
            }

            logger.info("[OUT] get NotificationWmsCancelEntryTask.run() success");
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }


    }
}
