package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import java.util.Date;

/**
 * @author: 葛灿
 */
public interface SupplierSellHeightTransferRecordService {

    String insertRecord(String prefix,GongxiaoRpc.RpcHeader rpcHeader,
                        String currencyCode, long amountBeforeTransfer,
                        long postingAmount, long amountAfterTransfer,
                        Date transferTime, long supplierId,
                        String supplierName,long projectId,
                        String projectName);
}
