package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;

/**
 * 越海部分返利自动转入实收接口
 * @author 王帅
 */
public interface YhglobalCouponTransferIntoReceivedService {

    boolean yhglobalCouponTransferIntoReceived(RpcHeader rpcHeader, String currencyCode,
                                           long supplierId, long projectId, long couponAmount,
                                           String purchaseOrderNo);
}
