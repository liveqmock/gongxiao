package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.OutboundOrderStatusEnum;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.OutBoundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.Result;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.cancel.WmsCanselOrderResp;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.close.WmsCloseOrderRequest;
import com.yhglobal.gongxiao.warehouse.service.WmsCloseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 由前端关闭按钮触发该任务 通知Wms关闭出库单
 */
public class NotificationWmsCloseOutTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(NotificationWmsCancelEntryTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private String projectId;

    private WmsCloseOrderService wmsCloseOrderService;

    private WmsCloseOrderRequest wmsCloseOrderRequest;

    private OutBoundOrderDao outBoundOrderDao;

    private OutBoundOrderItemDao outBoundOrderItemDao;

    private WarehouseConfig warehouseConfig;

    public NotificationWmsCloseOutTask(GongxiaoRpc.RpcHeader rpcHeader, String projectId, WmsCloseOrderService wmsCloseOrderService, WmsCloseOrderRequest wmsCloseOrderRequest, WarehouseConfig warehouseConfig, OutBoundOrderDao outBoundOrderDao, OutBoundOrderItemDao outBoundOrderItemDao) {
        this.rpcHeader = rpcHeader;
        this.projectId = projectId;
        this.wmsCloseOrderService = wmsCloseOrderService;
        this.wmsCloseOrderRequest = wmsCloseOrderRequest;
        this.outBoundOrderDao = outBoundOrderDao;
        this.outBoundOrderItemDao = outBoundOrderItemDao;
        this.warehouseConfig = warehouseConfig;
    }

    @Override
    public void run() {
        logger.info("[IN][run] start NotificationWmsCloseOutTask.run() params: wmsCloseOrderRequest={}", JSON.toJSONString(wmsCloseOrderRequest));
        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
        OutboundOrder outboundOrder = outBoundOrderDao.getOutStorageInfoById(projectId,wmsCloseOrderRequest.getOrderNo(),projectPrefix);
        Date dateTime = new Date();
        try {
            String result = wmsCloseOrderService.closeOrderService(wmsCloseOrderRequest,warehouseConfig.getWmsUrl());   //通知WMS系统取消入库单
            WmsCanselOrderResp cancelResult = JSON.parseObject(result, new TypeReference<WmsCanselOrderResp>() {});

            TraceLog successTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"关闭订单成功");

            if (cancelResult == null){
                TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms系统网络超时");
                String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),failTraceLog);
                outBoundOrderDao.closeOrderFail(wmsCloseOrderRequest.getOrderNo(),newTraceLog,projectPrefix);
            }else {
                if (cancelResult.isSuccess()){    //先判断是否成功接收到wms反馈信息
                    Result handleResult = cancelResult.getResult();
                    if (handleResult.isSuccess()){      //再判断，是否取消订单成功
                        if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_POOUT_ORDER_NO.getPrefix())){  //取消采购退货出库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),successTraceLog);
                            outBoundOrderDao.closeOrder(handleResult.getOrderNo(), OutboundOrderStatusEnum.OUTBOUND_ORDER_CLOSE.getStatus(),newTraceLog,projectPrefix);
                            outBoundOrderItemDao.closeOrder(handleResult.getOrderNo(),projectPrefix);
                        }else if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_SOOUT_ORDER_NO.getPrefix())){  //取消销售出库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),successTraceLog);
                            outBoundOrderDao.closeOrder(handleResult.getOrderNo(),OutboundOrderStatusEnum.OUTBOUND_ORDER_CLOSE.getStatus(),newTraceLog,projectPrefix);
                            outBoundOrderItemDao.closeOrder(handleResult.getOrderNo(),projectPrefix);
                        }else if (handleResult.getOrderNo().startsWith(BizNumberType.STOCK_OTHER_OUT_NO.getPrefix())){  //取消其他出库单
                            String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),successTraceLog);
                            outBoundOrderDao.closeOrder(handleResult.getOrderNo(),OutboundOrderStatusEnum.OUTBOUND_ORDER_CLOSE.getStatus(),newTraceLog,projectPrefix);
                            outBoundOrderItemDao.closeOrder(handleResult.getOrderNo(),projectPrefix);
                        }else {     //后面有调拨出库
                            return;
                        }

                    }else {        //wms取消订单失败
                        TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms关闭订单失败:"+handleResult.getErrorMsg());
                        String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),failTraceLog);
                        outBoundOrderDao.closeOrderFail(handleResult.getOrderNo(),newTraceLog,projectPrefix);
                    }
                }else {    //wms无法取消订单
                    TraceLog failTraceLog = new TraceLog(dateTime.getTime(),rpcHeader.getTraceId(),rpcHeader.getUsername(),"wms无法关闭订单");
                    String newTraceLog = TraceLogUtil.appendTraceLog(outboundOrder.getTracelog(),failTraceLog);
                    outBoundOrderDao.closeOrderFail(wmsCloseOrderRequest.getOrderNo(),newTraceLog,projectPrefix);
                }
            }

            logger.info("[OUT] get NotificationWmsCloseOutTask.run() success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }


    }
}
