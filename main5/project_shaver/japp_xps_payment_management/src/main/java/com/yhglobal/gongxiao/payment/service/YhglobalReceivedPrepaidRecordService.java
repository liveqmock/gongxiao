package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import java.util.Date;

/**
 * @author: 葛灿
 */
public interface YhglobalReceivedPrepaidRecordService {

    String insertRecord(String prefix, GongxiaoRpc.RpcHeader rpcHeader,
                        long supplierId, String supplierName,
                        long projectId, String projectName,
                        String currencyCode, long amountBeforeTrans, long receivedAmount,
                        Date receivedTime, String purchaseOrderNo, String sourceFlowNo);
}
