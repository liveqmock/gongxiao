package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.CreateOutboundItemInfo;
import com.yhglobal.gongxiao.warehouse.model.ManualOutboundOrder;

import java.util.List;

/**
 * 手工出库单接口
 * @author liukai
 */
public interface ManualOutboundService {

    /**
     * 查询所有的出库单
     * @return
     */
    List<ManualOutboundOrder> getManualOutboundList(GongxiaoRpc.RpcHeader rpcHeader);

    /**
     * 插入手工出库单
     * @param rpcHeader
     * @param manualOutboundOrder
     * @param createOutboundItemInfos
     * @return
     */
    int createManualOutbound(GongxiaoRpc.RpcHeader rpcHeader, ManualOutboundOrder manualOutboundOrder, List<CreateOutboundItemInfo> createOutboundItemInfos);

    /**
     * 更新手工出库单
     * @param rpcHeader
     * @param manualOutboundOrder
     * @return
     */
    int updateManualOutboundOrder(GongxiaoRpc.RpcHeader rpcHeader, ManualOutboundOrder manualOutboundOrder);
}
