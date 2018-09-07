package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;

import java.util.Date;

/**
 * @author: 葛灿
 */
public interface SupplierCouponPostingRecordService {

    /**
     * 插入 供应商返利上账记录
     *
     * @param rpcHeader           头
     * @param supplierId          供应商id
     * @param supplierName        供应商名称
     * @param currencyCode        货币编码
     * @param amountBeforePosting 上账钱金额
     * @param postingAmount       上账金额
     * @return
     */
    void insertRecord(RpcHeader rpcHeader,
                      long supplierId, String supplierName,
                      String currencyCode, long amountBeforePosting,
                      long postingAmount,Date postingTime);
}
