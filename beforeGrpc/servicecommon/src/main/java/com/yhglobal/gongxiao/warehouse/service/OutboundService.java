package com.yhglobal.gongxiao.warehouse.service;


import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanOutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出库单管理
 *
 * @author liukai
 */
public interface OutboundService {


    /**
     * 采购退货是调用
     * 新建预约出库单，返回回执gongxiaoOutboundOrderNo(对应从同一个仓库出货的情况)
     *
     * @param rpcHeader            rpc调用的header
     * @param sourceChannelId      发起出库通知单的渠道(对应枚举类:WmsSourceChannel)
     * @param outboundType         发起出库通知单的类型(对应枚举类:WmsOrderType)
     * @param projectId            项目id
     * @param customerId           客户id
     * @param traceNo              调用的发起方携带的标签
     * @param recipientName        收件人名字
     * @param recipientPhoneNumber 收件人电话
     * @param recipientAddress     收件地址
     * @param warehouseId          出库目标仓库id
     * @param warehouseName        出库目标仓库名称
     * @param shippingMode         物流方式 0:不详 1:自提 2:第三方物流
     * @param logisticsCompanyName 物流公司的名字
     * @param logisticsNo          物流号
     * @param note                 备注
     * @param totalQuantity        各种类商品本次出库数量总和
     * @param itemList             货品明细
     * @param signature            签名
     * @param uniqueNo             唯一号
     * @return gongxiaoInboundOrderNo
     */
    String createOutboundOrder(RpcHeader rpcHeader, String sourceChannelId, int outboundType, String projectId, String customerId, String traceNo, String recipientName,
                               String recipientPhoneNumber, String recipientAddress, String warehouseId, String warehouseName,
                               int shippingMode, String logisticsCompanyName, String logisticsNo, String note,
                               int totalQuantity, List<PlanOutboundOrderItem> itemList, String signature, String uniqueNo);


    /**
     * 销售出库是调用
     * 新建预约出库单，返回回执gongxiaoOutboundOrderNo(对应从多个仓库出货的情况)
     *
     * @param rpcHeader            rpc调用的header
     * @param sourceChannelId      发起出库通知单的渠道(对应枚举类:WmsSourceChannel)
     * @param outboundType         发起出库通知单的类型(对应枚举类:WmsOrderType)
     * @param projectId            项目id
     * @param customerId           客户id
     * @param traceNo              调用的发起方携带的标签
     * @param recipientName        收件人名字
     * @param recipientPhoneNumber 收件人电话
     * @param recipientAddress     收件地址
     * @param shippingMode         物流方式 0:不详 1:自提 2:第三方物流
     * @param logisticsCompanyName 物流公司的名字
     * @param logisticsNo          物流号
     * @param note                 备注
     * @param totalQuantity        各种类商品本次出库数量总和
     * @param itemList             货品明细
     * @param signature            签名
     * @param uniqueNo             唯一号
     * @return Map<String, List<OutboundOrderItem>>  key=出库单号 ,value=出库单对应的出库单明细
     */
    Map<String, List<OutboundOrderItem>> createOutboundOrder2(RpcHeader rpcHeader, String sourceChannelId, int outboundType, String projectId, String customerId,
                                                               String traceNo, String recipientName, String recipientPhoneNumber, String recipientAddress, String provinceName, String cityName,
                                                               int shippingMode, String logisticsCompanyName, String logisticsNo, String note,
                                                               int totalQuantity, List<PlanOutboundOrderItem> itemList, String signature, String uniqueNo, Date arrivalDate);


    /**
     * 取消整个出库单 返回错误码
     *
     * @param rpcHeader              rpc调用的header
     * @param projectId              项目id
     * @param warehouseId            仓库id
     * @param gongxiaoOutboundOrderNo 出库单号
     * @param signature              签名
     * @return errorCode  返回错误码 0时表示取消成功
     */
    int cancelOrder(RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoOutboundOrderNo, String productCode, String signature);


    /**
     * 关闭出库单 返回错误码
     *
     * @param rpcHeader
     * @param projectId               项目id
     * @param warehouseId             仓库id
     * @param gongxiaoOutboundOrderNo 出库单号
     * @param signature               签名
     * @return errorCode  返回错误码 0时表示取消成功
     */
    int closeOutboundOrder(RpcHeader rpcHeader, String projectId, String warehouseId, String gongxiaoOutboundOrderNo, String productCode, String signature);

    /**
     * 调整出库单中某商品的出库数量
     *
     * @param rpcHeader               rpc调用的header
     * @param projectId               项目id
     * @param gongxiaoOutboundOrderNo gongxiao出库单号
     * @param productCode             商品编码
     * @param originalQuantity        原始出库数量
     * @param adjustmentQuantity      调整数量 正数表示添加 负数表示减少
     * @param targetQuantity          调整之后的出库数量
     * @param signature               签名
     * @return errorCode  返回错误码 0时表示取消成功
     */
    int adjustOutboundQuantity(RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo, String productCode,
                               int originalQuantity, int adjustmentQuantity, int targetQuantity, String signature);


    /**
     * 查询出库单的出库记录
     *
     * @param rpcHeader             rpc调用的header
     * @param projectId             项目id
     * @param gongxiaoOutboundOrder 出库单号
     * @param signature             签名
     * @return
     */
    List<OutboundOrder> getOutboundRecord(RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrder, String signature);

//    /**
//     * 根据WMS系统返回的出库确认信息 更新出库明细单
//     *
//     * @param rpcHeader         rpc调用的header
//     * @param outboundOrderItem 出库明细
//     * @return
//     */
//    int modifyAccordingWms(RpcHeader rpcHeader, OutboundOrderItem outboundOrderItem);
//
//    /**
//     * 根据WMS系统返回的出库确认信息 更新出库单
//     *
//     * @param rpcHeader     rpc调用的header
//     * @param outboundOrder 出库明细
//     * @return
//     */
//    int modifyAOutboundInfoAccordingWms(RpcHeader rpcHeader, OutboundOrder outboundOrder);


    /**
     * 手动新增出库单
     * @param rpcHeader           rpc调用的header
     * @param supplierName        供应商
     * @param warehouseId         仓库id
     * @param dateTime            业务日期
     * @param outboundType        出库类型
     * @param note                备注
     * @param manualOutboundOrderList            手工出库明细
     * @return
     */
//    int insertNewOutboundOrder(RpcHeader rpcHeader, String projectId, String supplierName, String warehouseId, Date dateTime, int outboundType,
//                               String note, List<ManualOutboundOrder> manualOutboundOrderList);


    /**
     * 确认签收
     * @param rpcHeader
     * @param transporter   运输商
     * @param custOrdNo   出库单号
     * @param tmsOrdNo    TMS出库单号
     * @param remark      备注
     * @param signedBy     实际签收人
     * @param postedBy    签收时间维护人
     * @param signedPhone  签收电话
     * @param signedTime   签收时间
     */
    void sureSighIn(RpcHeader rpcHeader, String transporter, String custOrdNo, String tmsOrdNo, String remark, String signedBy, String postedBy, String signedPhone, Date signedTime);

}
