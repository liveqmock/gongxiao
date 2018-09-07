package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.purchase.bo.OutboundNotificationBackItem;

import java.util.List;

/**
 * 由预约出库单的发起方实现该服务 以便接收出库提醒
 *
 * @author liukai
 */
public interface OutboundNotificationService {


    /**
     * 按出库计划 货品出库通知
     *
     * @param rpcHeader rpc调用的header
     * @param projectId                 项目id
     * @param traceNo                   跟踪号
     * @param gongxiaoOutboundOrderNo   出库单号
     * @param planOutboundItemList      预约出库明细
     * @return 返回错误码 0表示通知成功
     */
    int transferDepartureNotification(RpcHeader rpcHeader, String projectId, String traceNo, String gongxiaoOutboundOrderNo, List<OutboundNotificationBackItem> planOutboundItemList);


    /**
     * 在出库计划之外 货品出库通知
     *
     * 计划内的商品超发？
     * 错发新的商品？
     *
     * 注：属于异常接口 后面再实现
     *
     * @param rpcHeader rpc调用的header
     * @param projectId         项目id
     * @param traceNo           跟踪号
     * @param gongxiaoOutboundOrderNo    出库单号
     * @param planOutboundItemList       预约出库明细
     * @return 返回错误码 0表示通知成功
     */
//    int transferMisDepartureNotification(RpcHeader rpcHeader, String projectId, String traceNo, String gongxiaoOutboundOrderNo, List<PlanOutboundItem> planOutboundItemList);


    /**
     * 按出库计划 货品签收通知
     *
     * @param rpcHeader rpc调用的header
     * @param projectId         项目id
     * @param traceNo           跟踪号
     * @param gongxiaoOutboundOrderNo    出库单号
     * @param planOutboundItemList       预约出库明细
     * @return 返回错误码 0表示通知成功
     */
//    int transferSignedNotification(RpcHeader rpcHeader, String projectId, String traceNo, String gongxiaoOutboundOrderNo, List<PlanOutboundItem> planOutboundItemList);



    /**
     * 出库单关闭通知
     *
     * @param rpcHeader rpc调用的header
     * @param projectId            项目id
     * @param traceNo              跟踪号
     * @param gongxiaoOutboundOrderNo  出库单号
     * @return
     */
    int transferClosedNotification(RpcHeader rpcHeader, String projectId, String traceNo, String gongxiaoOutboundOrderNo);


}
