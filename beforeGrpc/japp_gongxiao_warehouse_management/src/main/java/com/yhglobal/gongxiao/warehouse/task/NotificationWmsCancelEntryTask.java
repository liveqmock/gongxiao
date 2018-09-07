package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import com.yhglobal.gongxiao.warehousemanagement.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.Result;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.InboundOrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 由前端取消按钮触发该任务 通知Wms取消入库单
 */
public class NotificationWmsCancelEntryTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotificationWmsCancelEntryTask.class);

    private RpcHeader rpcHeader;

    private String projectId;

    private WmsCancelOrderService wmsCancelOrderService;

    private WmsCancelOrderRequest wmsCancelOrderRequest;

    private InBoundOrderDao inBoundOrderDao;

    private InboundOrderItemDao inboundOrderItemDao;

    private WarehouseConfig warehouseConfig;

    public NotificationWmsCancelEntryTask(RpcHeader rpcHeader, String projectId, WmsCancelOrderService wmsCancelOrderService, WmsCancelOrderRequest wmsCancelOrderRequest, InBoundOrderDao inBoundOrderDao,
                                          InboundOrderItemDao inboundOrderItemDao,WarehouseConfig warehouseConfig) {
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
        logger.info("[IN][run] start NotificationWmsCancelEntryTask.run() params: wmsCancelOrderRequest={}",JSON.toJSONString(wmsCancelOrderRequest));
        InboundOrder inboundOrder = inBoundOrderDao.selectRecordByOrderNo(projectId,wmsCancelOrderRequest.getOrderNo());
        Date dateTime = new Date();
        try {
            String httpResult=null;
            int retryTime = 10;
            while (retryTime>0){
                httpResult = wmsCancelOrderService.cancelOrderService(wmsCancelOrderRequest,warehouseConfig.getWmsUrl());   //通知WMS系统取消入库单
                retryTime--;
                if (httpResult != null){
                    break;
                }
            }

            WmsCanselOrderResp cancelResult = JSON.parseObject(httpResult, new TypeReference<WmsCanselOrderResp>() {});

            TraceLog successTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"取消订单成功");

            if (cancelResult == null){
                TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms系统网络超时");
                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),failTraceLog);
                inBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(),newTraceLog);
            }else {
                if (cancelResult.isSuccess()){    //先判断是否成功接收到wms反馈信息
                    Result handleResult = cancelResult.getResult();
                    if (handleResult.isSuccess()){      //再判断wms是否取消订单成功
                        if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_POIN_ORDER_NO.getPrefix())){    //取消采购入库单

                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),successTraceLog);
                            inBoundOrderDao.cancelOrder(handleResult.getOrderNo(),InboundOrderStatusEnum.INBOUND_ORDER_CANCEL.getStatus(),newTraceLog);      //增加状态为“取消中”---跳转为“已取消”(添加tracelog日志)
                            inboundOrderItemDao.cancelOrder(handleResult.getOrderNo());

                        }else if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_SOIN_ORDER_NO.getPrefix())){  //取消销售退货入库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),successTraceLog);
                            inBoundOrderDao.cancelOrder(handleResult.getOrderNo(),InboundOrderStatusEnum.INBOUND_ORDER_CANCEL.getStatus(),newTraceLog);
                            inboundOrderItemDao.cancelOrder(handleResult.getOrderNo());
                        }else if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_OTHER_IN_NO.getPrefix())){  //取消其他入库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),successTraceLog);
                            inBoundOrderDao.cancelOrder(handleResult.getOrderNo(),InboundOrderStatusEnum.INBOUND_ORDER_CANCEL.getStatus(),newTraceLog);
                            inboundOrderItemDao.cancelOrder(handleResult.getOrderNo());
                        }else {     //后面有调拨入库单
                            return;
                        }

                    }else {    //wms取消订单失败
                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms取消订单失败:"+handleResult.getErrorMsg());
                        String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),failTraceLog);
                        inBoundOrderDao.cancelOrderFail(handleResult.getOrderNo(),newTraceLog);
                    }
                }else {     //wms无法取消订单
                    TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms无法取消订单"+cancelResult.getError());
                    String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),failTraceLog);
                    inBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(),newTraceLog);
                }
            }

            logger.info("[OUT] get NotificationWmsCancelEntryTask.run() success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }



    }
}
