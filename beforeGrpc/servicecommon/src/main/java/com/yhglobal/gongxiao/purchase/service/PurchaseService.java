package com.yhglobal.gongxiao.purchase.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.purchase.bo.*;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * 采购服务类
 *
 * @author: 陈浩
 */
public interface PurchaseService {

    /**
     * 通过采购单号获取采购详情
     *
     * @param rpcHeader rpc调用的header
     * @param purchaseOrderNo 采购单号
     * @return
     */
    PurchaseOrderDetailVo getPurchaseDetail(RpcHeader rpcHeader, String purchaseOrderNo);

    /**
     * 获取采购单货品列表信息
     *
     * @param rpcHeader rpc调用的header
     * @param purchaseOrderNo
     * @return
     */
    List<PurchaseOrderItem> getItemList(RpcHeader rpcHeader, String purchaseOrderNo);

    /**
     * 获取采购订单信息
     *
     * @param rpcHeader rpc调用的header
     * @param purchaseOrderNo 采购单号
     * @param brandId 品牌ID
     * @param warehouseId 仓库ID
     * @return
     */
    PageInfo<PurchaseOrderInfo> getPurchaseOrderList(RpcHeader rpcHeader, String projectId,
                                                     String purchaseOrderNo,
                                                     String brandId,
                                                     String warehouseId,
                                                     String productCode,
                                                     String requireArrivalDate,
                                                     String arrivalDeadline,
                                                     String businessDate,
                                                     int pageNumber,
                                                     int pageSize);

    /**
     * 获取入库货品列表
     *
     * @param rpcHeader rpc调用的header
     * @param purchaseNo 采购单号
     * @return
     */
    List<PlanInboundItem> getInboundItemList(RpcHeader rpcHeader,
                                             String purchaseNo,
                                             String productCode);

    /**
     * 生成采购单
     *
     * @param rpcHeader rpc调用的header
     * @param purchaseBasicInfo 采购单基础信息
     * @param purchaseItemInfoList 采购单的货品列表
     * @return 采购单号
     */
    int generatePurchaseOrder(RpcHeader rpcHeader,
                              CreatePurchaseBasicInfo purchaseBasicInfo,
                              List<CreatePurchaseItemInfo> purchaseItemInfoList) throws MalformedURLException, RemoteException;

    /**
     * 生成预约入库单
     *
     * @param rpcHeader rpc调用的header
     * @param planInboundBasicInfo 入库单订单信息
     * @param planInboundItemList 入库单货品信息
     */
    RpcResult planInbound(RpcHeader rpcHeader,
                          PlanInboundBasicInfo planInboundBasicInfo,
                          List<PlanInboundItem> planInboundItemList) throws Exception;

    /**
     *  获取采购详情
     * @param rpcHeader
     * @param purchaseNo
     * @return
     */
    PurchaseEditDetail getPurchaseEditDetail(RpcHeader rpcHeader,String purchaseNo);

    /**
     * 更新采购单信息
     *
     * @param rpcHeader rpc调用的header
     * @param purchaseBasicInfo 采购单基础信息
     * @param purchaseItemInfoList 采购单的货品列表
     * @return 采购单号
     */
    int updatePurchaseOrder(RpcHeader rpcHeader,
                            CreatePurchaseBasicInfo purchaseBasicInfo,
                            List<CreatePurchaseItemInfo> purchaseItemInfoList);

    /**
     * 通过采购单号获取采购单
     * @param rpcHeader
     * @param purchaseOrderNo 采购单号
     * @return
     */
    PurchaseOrder getPurchaseOrderByPurchaseNo(RpcHeader rpcHeader,String purchaseOrderNo);

    /**
     * 取消采购单
     * @param rpcHeader
     * @param purchaseOrderNo 采购单号
     * @return
     */
    int cancelPurchaseOrder(RpcHeader rpcHeader,String purchaseOrderNo);

}
