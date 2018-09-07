package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 葛灿
 */
public interface SupplierCouponTransferToYhglobalFlowService {

    /**
     * 插入 供应商返利过账到越海 流水
     *
     * @param rpcHeader       头
     * @param supplierId      供应商id
     * @param supplierName    供应商名称
     * @param projectId       项目id
     * @param projectName     项目名称
     * @param currencyCode    货币编码
     * @param transferAmount  过账金额
     * @param transferTime    过账时间
     * @param purchaseOrderNo 采购单号
     * @param sourceFlowNo    来源流水号
     * @return
     */
    String insertFlow(String prefix, GongxiaoRpc.RpcHeader rpcHeader,
                      long supplierId, String supplierName,
                      long projectId, String projectName,
                      String currencyCode, BigDecimal originalAmount,BigDecimal transferAmount,
                      Date transferTime, String purchaseOrderNo, String sourceFlowNo
    );
}
