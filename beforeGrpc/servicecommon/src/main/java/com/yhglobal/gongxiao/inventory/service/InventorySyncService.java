package com.yhglobal.gongxiao.inventory.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;

import java.util.List;

/**
 * WMS通知到仓储模块后，仓储模块会调用该服务的接口 来同步到库存模块
 *
 * @author : liukai
 */
public interface InventorySyncService {


    /**
     * 同步商品入库信息到库存模块
     *
     * @param rpcHeader rpc调用的header
     * @param productInventoryList  库存记录
     * @param purchaseOrderNo  采购单号
     * @return errorCode  返回错误码 0表示同步成功
     */
    int syncInboundInventory(RpcHeader rpcHeader,List<ProductInventory> productInventoryList,String purchaseOrderNo,String gonxiaoInboundOrderNo,int orderType);



    /**
     * 同步商品出库信息到库存模块
     *
     * @param rpcHeader rpc调用的header
     * @param productInventoryList  库存记录信息
     * @param salesOrderNo  销售单号
     * @return errorCode  返回错误码 0表示同步成功
     */
    int syncOutboundInventory(RpcHeader rpcHeader, List<ProductInventory> productInventoryList,String salesOrderNo,String gonxiaoOutboundOrderNo,int orderType);


}
