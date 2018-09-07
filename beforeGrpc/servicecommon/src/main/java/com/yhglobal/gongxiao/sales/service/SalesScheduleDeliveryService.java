package com.yhglobal.gongxiao.sales.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.inventory.bo.InventoryBatch;
import com.yhglobal.gongxiao.sales.dto.EasOutboundItem;
import com.yhglobal.gongxiao.sales.dto.EasOutboundOrderRequest;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * 预约发货Service
 *
 * @Author: 葛灿
 */
public interface SalesScheduleDeliveryService {

    /**
     * 新建预约发货单
     *
     * @param rpcHeader    rpc调用的header
     * @param salesOrderNo 销售单号
     * @param productInfo  货品信息（Json）
     * @return int 返回新建订单的条数
     */
    RpcResult createScheduleOrder(RpcHeader rpcHeader,
                                  String projectId,
                                  String salesOrderNo,
                                  List<InventoryBatch> productInfo,
                                  Date arrivalDate) throws Exception;

    /**
     * 查询可预约货品信息
     *
     * @param rpcHeader    rpc调用的header
     * @param salesOrderNo 销售单号
     * @param productCodes 货品编码 有多条，使用","分割，可以为null
     * @return List<SalesScheduleProduct> 预约单商品列表
     */
    List<InventoryBatch> selectSaleScheduleProductList(RpcHeader rpcHeader, String salesOrderNo, String productCodes);

    /**
     * 通知tms可以出库
     *
     * @param rpcHeader       头
     * @param outboundOrderNo 出库单号
     * @return
     */
    RpcResult submitOutboundOrderToTms(RpcHeader rpcHeader, String outboundOrderNo);

    /**
     * 出库单完成
     *
     * @param rpcHeader       rpc调用的header
     * @param outboundOrderNo 出库单号
     * @return
     */
    void outboundOrderFinished(RpcHeader rpcHeader, String outboundOrderNo) throws MalformedURLException, RemoteException;


    EasOutboundOrderRequest syncSalesOutboundOrderToEas(RpcHeader rpcHeader, String salesOrderNo, List<EasOutboundItem> easOutboundItems) throws MalformedURLException, RemoteException;

    /**
     * 订单签收完成
     *
     * @param rpcHeader       rpc调用的header
     * @param outboundOrderNo 出库单号
     * @return
     */
    RpcResult outboundSigned(RpcHeader rpcHeader,
                             String outboundOrderNo,
                             String tmsOrderNo,
                             String tmsRemark,
                             String signedBy,
                             String postedBy,
                             String signedPhone,
                             Date signedTime,
                             String transporter
    );


}
