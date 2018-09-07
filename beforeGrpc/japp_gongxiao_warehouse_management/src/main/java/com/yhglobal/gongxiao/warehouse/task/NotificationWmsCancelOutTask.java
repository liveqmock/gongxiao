package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.TraceLog;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.bootstrap.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.service.WmsCancelOrderService;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.Result;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCancelOrderRequest;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum.OutboundOrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 由前端取消按钮触发该任务 通知Wms取消出库单
 */
public class NotificationWmsCancelOutTask implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(NotificationWmsCancelEntryTask.class);

    private RpcHeader rpcHeader;

    private String projectId;

    private String productCode;

    private WmsCancelOrderService wmsCancelOrderService;

    private WmsCancelOrderRequest wmsCancelOrderRequest;

    private OutBoundOrderDao outBoundOrderDao;

    private OutBoundOrderItemDao outBoundOrderItemDao;

    private WarehouseConfig warehouseConfig;


    public NotificationWmsCancelOutTask(RpcHeader rpcHeader,String projectId,String productCode, WmsCancelOrderService wmsCancelOrderService, WmsCancelOrderRequest wmsCancelOrderRequest,
                                      OutBoundOrderDao outBoundOrderDao, OutBoundOrderItemDao outBoundOrderItemDao,WarehouseConfig warehouseConfig) {
        this.rpcHeader = rpcHeader;
        this.projectId = projectId;
        this.productCode = productCode;
        this.wmsCancelOrderService = wmsCancelOrderService;
        this.wmsCancelOrderRequest = wmsCancelOrderRequest;
        this.outBoundOrderDao = outBoundOrderDao;
        this.outBoundOrderItemDao = outBoundOrderItemDao;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("[IN][run] start NotificationWmsCancelOutTask.run() params: wmsCancelOrderRequest={}", JSON.toJSONString(wmsCancelOrderRequest));
        OutboundOrder outboundOrder = outBoundOrderDao.getOutStorageInfoById(projectId,wmsCancelOrderRequest.getOrderNo());
        Date dateTime = new Date();
        try {
            String result = wmsCancelOrderService.cancelOrderService(wmsCancelOrderRequest,warehouseConfig.getWmsUrl());   //通知WMS系统取消入库单
            WmsCanselOrderResp cancelResult = JSON.parseObject(result, new TypeReference<WmsCanselOrderResp>() {});

            TraceLog successTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"取消订单成功");

            if (cancelResult == null){
                TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms系统网络超时");
                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),failTraceLog);
                outBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(),newTraceLog);
            }else {
                if (cancelResult.isSuccess()){    //先判断是否成功接收到wms反馈信息
                    Result handleResult = cancelResult.getResult();
                    if (handleResult.isSuccess()){      //再判断，是否取消订单成功
                        if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_POOUT_ORDER_NO.getPrefix())){  //取消采购退货出库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),successTraceLog);
                            outBoundOrderDao.cancelOrder(handleResult.getOrderNo(),OutboundOrderStatusEnum.OUTBOUND_ORDER_CANCEL.getStatus(),newTraceLog);
                            outBoundOrderItemDao.cancelOrder(handleResult.getOrderNo());
                        }else if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_SOOUT_ORDER_NO.getPrefix())){  //取消销售出库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),successTraceLog);
                            outBoundOrderDao.cancelOrder(handleResult.getOrderNo(),OutboundOrderStatusEnum.OUTBOUND_ORDER_CANCEL.getStatus(),newTraceLog);
                            outBoundOrderItemDao.cancelOrder(handleResult.getOrderNo());
                        }else if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_OTHER_OUT_NO.getPrefix())){  //取消其他出库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),successTraceLog);
                            outBoundOrderDao.cancelOrder(handleResult.getOrderNo(),OutboundOrderStatusEnum.OUTBOUND_ORDER_CANCEL.getStatus(),newTraceLog);
                            outBoundOrderItemDao.cancelOrder(handleResult.getOrderNo());
                        }else {     //后面有调拨出库
                            return;
                        }

                    }else {        //wms取消订单失败
                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms取消订单失败:"+handleResult.getErrorMsg());
                        String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),failTraceLog);
                        outBoundOrderDao.cancelOrderFail(handleResult.getOrderNo(),newTraceLog);
                    }
                }else {    //wms无法取消订单
                    TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms无法取消订单"+cancelResult.getError());
                    String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),failTraceLog);
                    outBoundOrderDao.cancelOrderFail(wmsCancelOrderRequest.getOrderNo(),newTraceLog);
                }
            }

            logger.info("[OUT] get NotificationWmsCancelOutTask.run() success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }



    }
}
