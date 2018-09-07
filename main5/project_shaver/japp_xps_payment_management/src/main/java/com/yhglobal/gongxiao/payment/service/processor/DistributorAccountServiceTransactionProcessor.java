package com.yhglobal.gongxiao.payment.service.processor;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.payment.bo.CashCouponPrepaidFlows;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidAccount;

import java.util.Date;
import java.util.List;

/**
 * ad账户事务控制类
 *
 * @author: 葛灿
 */
public interface DistributorAccountServiceTransactionProcessor {

    /**
     * 销售单支付
     * <p>
     * 由销售模块调用
     * 用于销售订单下单、审核的场景
     * 各个账户的总金额做修改
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param distributorId   分销商id
     * @param projectId       项目id
     * @param couponAmount    返利金额 金额为+，没有为0
     * @param prepaidAmount   代垫金额 金额为+，没有为0
     * @param cashAmount      预存金额 金额为+，没有为0
     * @param salesOrderNo    销售单号
     * @param transactionTime 交易时间
     * @return
     */
    RpcResult<CashCouponPrepaidFlows> payForSalesOrder(String prefix,
                                                       GongxiaoRpc.RpcHeader rpcHeader,
                                                       String currencyCode, long distributorId,
                                                       long projectId, long couponAmount,
                                                       long prepaidAmount, long cashAmount,
                                                       String salesOrderNo, Date transactionTime);

    /**
     * 销售单退款
     * <p>
     * 由销售模块调用
     * 用于销售单取消、销售单退货场景
     * 插入 ad账户流水（现金、返利、代垫）
     *
     * @param rpcHeader          头
     * @param currencyCode       货币编码
     * @param distributorId      分销商id
     * @param projectId          项目id
     * @param couponAmount       返利退货金额 金额为+，没有为0
     * @param prepaidAmount      代垫退货金额 金额为+，没有为0
     * @param cashAmount         预存退货金额 金额为+，没有为0
     * @param salesReturnOrderNo 销售单号
     * @param transactionTime    交易时间
     * @return
     */
    RpcResult<CashCouponPrepaidFlows> returnForSalesReturnOrder(String prefix,GongxiaoRpc.RpcHeader rpcHeader,
                                                                String currencyCode, long distributorId,
                                                                long projectId, long couponAmount,
                                                                long prepaidAmount, long cashAmount,
                                                                String salesReturnOrderNo, Date transactionTime);


    /**
     * 从返利缓冲账户过账到ad实收账户
     * <p>
     * 由支付模块页面调用
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param distributorId   分销商id
     * @param distributorName 分销商
     * @param projectId       项目id
     * @param couponAmount    返利金额 金额为+
     * @param transactionTime 交易时间
     * @param remark          备注
     * @return
     */
    RpcResult<String> depositCouponReceived(String prefix,
                                            GongxiaoRpc.RpcHeader rpcHeader,
                                            String currencyCode,
                                            long distributorId,
                                            String distributorName,
                                            long projectId, long couponAmount,
                                            Date transactionTime,
                                            String remark);

    /**
     * 从代垫缓冲账户过账到ad实收账户
     * <p>
     * 由支付模块页面调用
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param distributorId   分销商id
     * @param distributorName 分销商
     * @param projectId       项目id
     * @param prepaidAmount   代垫金额 金额为+，没有为0
     * @param transactionTime 交易时间
     * @param remark          备注
     * @return
     */
    RpcResult<String> depositPrepaidReceived(String prefix,
                                             GongxiaoRpc.RpcHeader rpcHeader,
                                             String currencyCode, long distributorId,
                                             String distributorName,
                                             long projectId, long prepaidAmount,
                                             Date transactionTime, String remark);


    /**
     * 从过账缓冲账户给ad多个账户分配返利
     * <p>
     * 由支付模块页面调用
     *
     * @param rpcHeader                 头
     * @param distributorCouponAccounts 账户信息
     * @return
     */
    RpcResult depositCouponReceivedAccounts(String prefix, GongxiaoRpc.RpcHeader rpcHeader, List<DistributorCouponAccount> distributorCouponAccounts);


    /**
     * 从过账缓冲账户给ad多个账户分配返利
     * <p>
     * 由支付模块页面调用
     *
     * @param rpcHeader                  头
     * @param distributorPrepaidAccounts 账户信息
     * @return
     */
    RpcResult depositPrepaidReceivedAccounts(String prefix,
                                             GongxiaoRpc.RpcHeader rpcHeader, List<DistributorPrepaidAccount> distributorPrepaidAccounts);

}
