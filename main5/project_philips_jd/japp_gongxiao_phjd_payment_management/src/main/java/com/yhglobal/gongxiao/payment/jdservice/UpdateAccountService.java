package com.yhglobal.gongxiao.payment.jdservice;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;

import java.math.BigDecimal;

/**
 * 所有账户的更新额度功能
 * @author 王帅
 */
public interface UpdateAccountService {

    public RpcResult updateSupplierUnitPriceDiscountFrozenAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark );

    public RpcResult updateSupplierPurchaseReservedFrozenAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark );

    RpcResult updateSupplierPurchaseReservedAccount(Long supplierId,Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark );
    RpcResult updateSupplierUnitPriceDiscountReservedAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark );

    public RpcResult updateSalesDifferenceAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark );
//    public RpcResult updateYhglobalReceivedCouponAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark );
//
//    public RpcResult updateYhglobalReceivedPrepaidAccount(Long supplierId, Long projectId, String prefix, GongxiaoRpc.RpcHeader rpcHeader, BigDecimal transferAmount, String remark );


}
