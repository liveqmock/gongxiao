package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.dto.PlanInboundOrderItem;

import java.util.Date;
import java.util.List;

/**
 * 预约入库
 *
 * @author liukai
 */
public interface InboundService {

    /**
     * 采购入库时调用
     * 新建预约入库单，返回回执gongxiaoInboundOrderNo
     *
     * @param rpcHeader rpc调用的header
     * @param expectArrivalTime          预计到货时间(非空)
     * @param purchaseType               采购类型(非空)
     * @param sourceChannelId      发起入库通知单的渠道(非空)
     * @param inboundType          入库类型(非空)
     * @param projectId            项目id(非空)
     * @param traceNo              调用的发起方携带的标签(非空)
     * @param senderName           发件人名字(非空)
     * @param senderPhoneNo        发件人联系电话(非空)
     * @param warehouseId          入库目标仓库id(非空)
     * @param warehouseName        入库目标仓库名称(非空)
     * @param deliverAddress       发货地址(非空)
     * @param shippingMode         物流方式 0:不详 1:自提 2:第三方物流(非空)
     * @param logisticsCompanyName 物流公司的名字(非空)
     * @param logisticsNo          物流号(非空)
     * @param note                 备注(非空)
     * @param totalQuantity        各种类商品本次入库数量总和(非空)
     * @param itemList             货品明细(非空)
     * @param signature            签名(非空)
     * @param  uniqueNo             唯一号
     * @return gongxiaoInboundOrderNo 入库单号
     */
    GongxiaoResult createInboundOrder(GongxiaoRpc.RpcHeader rpcHeader, Date expectArrivalTime, int purchaseType, String orderSourceNo,
                                      String sourceChannelId, int inboundType, String projectId, String traceNo, String senderName,
                                      String senderPhoneNo, String warehouseId, String warehouseName,
                                      String deliverAddress, int shippingMode, String logisticsCompanyName, String logisticsNo,
                                      String note, int totalQuantity, List<PlanInboundOrderItem> itemList, String signature, String uniqueNo);


    /**
     * 销售退货入库时调用
     * 新建预约入库单，返回回执gongxiaoInboundOrderNo
     *
     * @param rpcHeader rpc调用的header
     * @param sourceChannelId      发起入库通知单的渠道(非空)
     * @param inboundType          入库类型(非空)
     * @param projectId            项目id(非空)
     * @param traceNo              调用的发起方携带的标签(非空)
     * @param senderName           发件人名字(非空)
     * @param senderPhoneNo        发件人联系电话(非空)
     * @param warehouseId          入库目标仓库id(非空)
     * @param warehouseName        入库目标仓库名称(非空)
     * @param deliverAddress       发货地址(非空)
     * @param shippingMode         物流方式 0:不详 1:自提 2:第三方物流(非空)
     * @param logisticsCompanyName 物流公司的名字(非空)
     * @param logisticsNo          物流号(非空)
     * @param note                 备注(非空)
     * @param totalQuantity        各种类商品本次入库数量总和(非空)
     * @param itemList             货品明细(非空)
     * @param signature            签名(非空)
     * @param  uniqueNo             唯一号
     * @return gongxiaoInboundOrderNo 入库单号
     */
    GongxiaoResult createInboundOrder2(GongxiaoRpc.RpcHeader rpcHeader, String orderSourceNo,
                                       String sourceChannelId, int inboundType, String projectId, String traceNo, String senderName,
                                       String senderPhoneNo, String warehouseId, String warehouseName,
                                       String deliverAddress, int shippingMode, String logisticsCompanyName, String logisticsNo,
                                       String note, int totalQuantity, List<PlanInboundOrderItem> itemList, String signature, String uniqueNo);


    /**
     * 取消整个入库单 返回错误码
     * @param rpcHeader rpc调用的header
     * @param projectId                 项目id(非空)
     * @param warehouseId               仓库id(非空)
     * @param gongxiaoInboundOrderNo    入库单号(非空)
     * @param signature                 签名(非空)
     * @return errorCode  返回错误码 0时表示取消成功
     */
    int cancelInboundOrder(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoInboundOrderNo, String signature);


    /**
     * 取消整个入库单 返回错误码
     * @param rpcHeader rpc调用的header
     * @param projectId                 项目id(非空)
     * @param warehouseId               仓库id(非空)
     * @param gongxiaoInboundOrderNo    入库单号(非空)
     * @param signature                 签名(非空)
     * @return errorCode  返回错误码 0时表示取消成功
     */
    int closeInboundOrder(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoInboundOrderNo, String signature);

    /**
     * 根据采购单号查询入库单明细
     * @param rpcHeader
     * @param purchaseOrderNo
     * @param purchaseOrderNo
     * @return
     */
    List<InboundOrderItem> getInboundItemRecord(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String purchaseOrderNo);

}
