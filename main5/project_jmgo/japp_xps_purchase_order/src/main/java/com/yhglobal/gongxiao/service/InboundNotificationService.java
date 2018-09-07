package com.yhglobal.gongxiao.service;


import com.yhglobal.gongxiao.model.PlanInboundItem;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.InboundNotificationBackItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.PurchaseEasInboundModel;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 由预约入库单的发起方实现该服务 以便接收入库提醒
 * @author liukai
 */
public interface InboundNotificationService {


    /**
     * 按入库计划 货品入库通知
     *
     * @param projectId                         项目id(非空)
     * @param traceNo                           跟踪号(非空)
     * @param gongxiaoInboundOrderNo            入库单号(非空)
     * @param inboundNotificationBackItemList   入库单详情(非空)
     * @param uniqueNumber              唯一号
     * @return 返回EASjson 若为空 表示重复请求
     */
    PurchaseEasInboundModel transferReceivedNotification(String projectId,
                                                         String traceNo,
                                                         String gongxiaoInboundOrderNo,
                                                         List<InboundNotificationBackItem> inboundNotificationBackItemList,
                                                         String uniqueNumber) throws CloneNotSupportedException, MalformedURLException, RemoteException;



    RpcResult notifyPurchaseInbound(String projectId,
                                    String traceNo,
                                    String gongxiaoInboundOrderNo,
                                    List<InboundNotificationBackItem> inboundNotificationBackItemList,
                                    String uniqueNumber);

    /**
     * 在入库计划之外 货品入库通知
     *
     * 计划内的商品超收？
     * 收到新的商品？
     *
     * 注：属于异常接口 后面再实现
     *
     * @param projectId                     项目id(非空)
     * @param traceNo                       跟踪号(非空)
     * @param gongxiaoInboundOrderNo        入库单号(非空)
     * @param planInboundItemList           入库单详情(非空)
     * @param uniqueNumber              唯一号
     * @return 返回错误码 0表示通知成功
     */
    int transferMisReceivedNotification(String projectId,
                                        String traceNo,
                                        String gongxiaoInboundOrderNo,
                                        List<PlanInboundItem> planInboundItemList,
                                        String uniqueNumber);


    /**
     * 入库单关闭通知
     *
     * @param projectId                 项目id(非空)
     * @param traceNo                   跟踪号(非空)
     * @param gongxiaoInboundOrderNo    入库单号(非空)
     * @param batchNo                   批次号
     * @param uniqueNumber              唯一号
     * @return
     */
    RpcResult transferClosedNotification(String projectId,
                                         String traceNo,
                                         String gongxiaoInboundOrderNo,
                                         String batchNo,
                                         String uniqueNumber) throws Exception;


}
