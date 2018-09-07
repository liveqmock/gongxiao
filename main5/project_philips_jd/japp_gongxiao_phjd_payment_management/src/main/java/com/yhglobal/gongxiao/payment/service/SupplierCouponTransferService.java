package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

/**
 * 供应商过账的接口 内部调用
 * @author 王帅
 */
public interface SupplierCouponTransferService {

    boolean supplierCouponTransferToYh(long supplierId, String supplierName, long projectId,
                                       String projectName, String currrencyCode, long totalAmount, String purchaseOrderNo,
                                       String remark, GongxiaoRpc.RpcHeader rpcHeader);

    boolean supplierCouponTransferToAd(long supplierId, String supplierName, long projectId,
                                       String projectName, String currrencyCode, long totalAmount, String purchaseOrderNo,
                                       String remark, GongxiaoRpc.RpcHeader rpcHeader);
}
