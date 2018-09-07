package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;

import java.util.Date;

/**
 * @author: 葛灿
 */
public interface YhglobalReceivedCouponRecordService {

    String insertRecord(RpcHeader rpcHeader,
                      long supplierId, String supplierName,
                      long projectId, String projectName,
                      String currencyCode, long amountBeforeTrans, long receivedAmount,
                      Date receivedTime, String purchaseOrderNo,String sourceFlowNo,String remark);
}
