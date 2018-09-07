package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.ManualInboundOrder;
import com.yhglobal.gongxiao.warehouse.model.bo.CreateNewInStockrder;

import java.util.List;

/**
 * 手工入库单接口
 * @author liukai
 */
public interface ManualInboundService {

    /**
     * 查询所有的手工入库单
     * @param rpcHeader
     * @return
     */
    List<ManualInboundOrder> getManualInboundList(GongxiaoRpc.RpcHeader rpcHeader);

    /**
     * 插入手工入库单
     * @param rpcHeader
     * @param manualInboundOrder     其它图库单
     * @param inStockrderList    入库单明细
     * @return
     */
    int createManualInbound(GongxiaoRpc.RpcHeader rpcHeader, ManualInboundOrder manualInboundOrder, List<CreateNewInStockrder> inStockrderList);

    /**
     * 修改手工入库单
     * @param rpcHeader
     * @param manualInboundOrder
     * @return
     */
    int updateManualInboundOrder(GongxiaoRpc.RpcHeader rpcHeader, ManualInboundOrder manualInboundOrder);

}
