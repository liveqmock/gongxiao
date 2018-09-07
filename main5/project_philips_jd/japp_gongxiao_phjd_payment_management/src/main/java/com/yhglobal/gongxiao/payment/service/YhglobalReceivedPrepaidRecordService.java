package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 葛灿
 */
public interface YhglobalReceivedPrepaidRecordService {

    String insertRecord(String prefix, GongxiaoRpc.RpcHeader rpcHeader,
                        long supplierId, String supplierName,
                        long projectId, String projectName,
                        String currencyCode, BigDecimal amountBeforeTrans, BigDecimal receivedAmount,
                        Date receivedTime, String purchaseOrderNo, String sourceFlowNo);
}
