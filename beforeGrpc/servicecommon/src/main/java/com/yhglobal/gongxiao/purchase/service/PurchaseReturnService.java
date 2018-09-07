package com.yhglobal.gongxiao.purchase.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.purchase.bo.ProductReturnInfo;
import com.yhglobal.gongxiao.purchase.bo.PurchaseReturnInboundInfo;
import com.yhglobal.gongxiao.purchase.bo.PurchaseReturnOrderDetail;
import com.yhglobal.gongxiao.purchase.bo.PurchaseReturnOrderInfo;

import java.util.List;

/**
 * 采购退货服务类
 *
 * @author: 陈浩
 */
public interface PurchaseReturnService {

    /**
     * 根据采购单号,获取入库单号
     *
     * @param rpcHeader      rpc调用的header
     * @param projectId      项目ID
     * @param purchaseNumber 采购单号
     * @return
     */
    List<PurchaseReturnInboundInfo> getInboundOrderList(RpcHeader rpcHeader, String projectId, String purchaseNumber);

    /**
     * 根据入库单号,获取入库明细
     *
     * @param rpcHeader              rpc调用的header
     * @param projectId
     * @param gongxiaoInboundOrderNo
     * @return
     */
    List<ProductReturnInfo> getInboundItemList(RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo);

    /**
     * 新增采购退货
     *
     * @param rpcHeader                 rpc调用的header
     * @param purchaseReturnOrderDetail 采购退货详情
     * @return
     */
    int putPurchaseReturnDetail(RpcHeader rpcHeader, PurchaseReturnOrderDetail purchaseReturnOrderDetail) throws Exception;

    /**
     * 查询退货单列表
     *
     * @param rpcHeader   rpc调用的header
     * @param warehouseId 仓库ID
     * @param returnType  退货类型
     * @param orderNumber 单号(采购单号/退货单号)
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @return
     */
    List<PurchaseReturnOrderInfo> getPurchaseReturnList(RpcHeader rpcHeader, String projectId,
                                                        String warehouseId,
                                                        int returnType,
                                                        String orderNumber,
                                                        String startDate,
                                                        String endDate);

    /**
     * 获取采购详情
     *
     * @param rpcHeader               rpc调用的header
     * @param purchaseReturnedOrderNo 退货单号
     * @return
     */
    PurchaseReturnOrderDetail getPurchaseDetail(RpcHeader rpcHeader, String purchaseReturnedOrderNo);


}
