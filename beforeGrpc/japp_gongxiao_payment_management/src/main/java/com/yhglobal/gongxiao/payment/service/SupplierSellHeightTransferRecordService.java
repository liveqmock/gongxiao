package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;

import java.util.Date;

/**
 * @author: 葛灿
 */
public interface SupplierSellHeightTransferRecordService {

    String insertRecord(RpcHeader rpcHeader,
                      String currencyCode, long amountBeforeTransfer,
                      long postingAmount, long amountAfterTransfer,
                      Date transferTime, long supplierId,
                      String supplierName,long projectId,String projectName);
}
