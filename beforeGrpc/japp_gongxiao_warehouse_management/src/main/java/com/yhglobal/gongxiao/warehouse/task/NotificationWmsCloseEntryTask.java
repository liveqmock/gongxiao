package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.WmsCloseOrderService;
import com.yhglobal.gongxiao.warehousemanagement.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.Result;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.close.WmsCloseOrderRequest;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.InboundOrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 由前端关闭按钮触发该任务 通知Wms关闭入库单
 */
public class NotificationWmsCloseEntryTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotificationWmsCancelEntryTask.class);

    private RpcHeader rpcHeader;

    private String projectId;

    private WmsCloseOrderService wmsCloseOrderService;

    private WmsCloseOrderRequest wmsCloseOrderRequest;

    private InBoundOrderDao inBoundOrderDao;

    private InboundOrderItemDao inboundOrderItemDao;

    private WarehouseConfig warehouseConfig;

    public NotificationWmsCloseEntryTask(RpcHeader rpcHeader, String projectId, WmsCloseOrderService wmsCloseOrderService, WmsCloseOrderRequest wmsCloseOrderRequest, InBoundOrderDao inBoundOrderDao, InboundOrderItemDao inboundOrderItemDao,WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.projectId = projectId;
        this.wmsCloseOrderService = wmsCloseOrderService;
        this.wmsCloseOrderRequest = wmsCloseOrderRequest;
        this.inBoundOrderDao = inBoundOrderDao;
        this.inboundOrderItemDao = inboundOrderItemDao;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("[IN][run] start NotificationWmsCloseEntryTask.run() params: wmsCloseOrderService={}", JSON.toJSONString(wmsCloseOrderRequest));
        InboundOrder inboundOrder = inBoundOrderDao.selectRecordByOrderNo(projectId,wmsCloseOrderRequest.getOrderNo());
        Date dateTime = new Date();
        try {
            String result =  wmsCloseOrderService.closeOrderService(wmsCloseOrderRequest,warehouseConfig.getWmsUrl());  //通知WMS系统关闭入库单
            WmsCanselOrderResp closeResult = JSON.parseObject(result, new TypeReference<WmsCanselOrderResp>() {});

            TraceLog successTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"关闭订单成功");

            if (closeResult == null){
                TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms系统网络超时");
                String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),failTraceLog);
                inBoundOrderDao.closeOrderFail(wmsCloseOrderRequest.getOrderNo(),newTraceLog);
            }else {
                if (closeResult.isSuccess()){    //先判断是否成功接收到wms反馈信息
                    Result handleResult = closeResult.getResult();
                    if (handleResult.isSuccess()){      //再判断wms是否取消订单成功
                        if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_POIN_ORDER_NO.getPrefix())){    //取消采购入库单

                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),successTraceLog);
                            inBoundOrderDao.closeOrder(handleResult.getOrderNo(),InboundOrderStatusEnum.INBOUND_ORDER_CLOSE.getStatus(),newTraceLog);      //增加状态为“取消中”---跳转为“已关闭”(添加tracelog日志)
                            inboundOrderItemDao.closeOrder(handleResult.getOrderNo());

                        }else if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_SOIN_ORDER_NO.getPrefix())){  //关闭销售退货入库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),successTraceLog);
                            inBoundOrderDao.closeOrder(handleResult.getOrderNo(),InboundOrderStatusEnum.INBOUND_ORDER_CLOSE.getStatus(),newTraceLog);
                            inboundOrderItemDao.closeOrder(handleResult.getOrderNo());
                        }else if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_OTHER_IN_NO.getPrefix())){  //关闭其他入库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),successTraceLog);
                            inBoundOrderDao.closeOrder(handleResult.getOrderNo(),InboundOrderStatusEnum.INBOUND_ORDER_CLOSE.getStatus(),newTraceLog);
                            inboundOrderItemDao.closeOrder(handleResult.getOrderNo());
                        }else {     //后面有调拨入库单
                            return;
                        }



                    }else {    //wms关闭订单失败
                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms关闭订单失败:"+handleResult.getErrorMsg());
                        String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),failTraceLog);
                        inBoundOrderDao.closeOrderFail(handleResult.getOrderNo(),newTraceLog);
                    }
                }else {     //wms无法关闭订单
                    TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms无法关闭订单");
                    String newTraceLog = TraceLogUtil.appendTraceLog(inboundOrder.getTracelog(),failTraceLog);
                    inBoundOrderDao.closeOrderFail(wmsCloseOrderRequest.getOrderNo(),newTraceLog);
                }
            }

            logger.info("[OUT] get NotificationWmsCancelEntryTask.run() success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }


    }
}
