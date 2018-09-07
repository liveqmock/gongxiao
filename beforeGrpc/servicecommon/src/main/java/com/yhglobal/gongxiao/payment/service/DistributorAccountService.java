package com.yhglobal.gongxiao.payment.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.CashCouponPrepaidFlows;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidAccount;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
public interface DistributorAccountService {

    /**
     * 查询ad账户余额
     * <p>
     * 用于账户管理页面查询
     * 用于销售模块接口查询
     *
     * @param rpcHeader     头
     * @param currencyCode  货币编码
     * @param distributorId 分销商id
     * @param projectId     项目id
     * @return
     */
    AccountAmount getDistributorAccountAmount(RpcHeader rpcHeader,
                                              String currencyCode,
                                              long projectId, long distributorId);


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
    RpcResult<CashCouponPrepaidFlows> payForSalesOrder(RpcHeader rpcHeader,
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
    RpcResult<CashCouponPrepaidFlows> returnForSalesReturnOrder(RpcHeader rpcHeader,
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
    RpcResult<String> depositCouponReceived(RpcHeader rpcHeader,
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
    RpcResult<String> depositPrepaidReceived(RpcHeader rpcHeader,
                                  String currencyCode, long distributorId,
                                  String distributorName,
                                  long projectId, long prepaidAmount,
                                  Date transactionTime, String remark);

    /**
     * 查询所有的ad返利账户
     * <p>
     * 由支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    PageInfo<DistributorCouponAccount> selectDistributorCouponAccountList(RpcHeader rpcHeader, String currencyCode, long projectId, int pageNum, int pageSize);

    /**
     * 查询所有的ad代垫账户
     * <p>
     * 由支付模块页面电泳
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    PageInfo<DistributorPrepaidAccount> selectDistributorPrepaidAccountList(RpcHeader rpcHeader, String currencyCode, long projectId, int pageNum, int pageSize);

    /**
     * 从过账缓冲账户给ad多个账户分配返利
     * <p>
     * 由支付模块页面调用
     *
     * @param rpcHeader                 头
     * @param distributorCouponAccounts 账户信息
     * @return
     */
    RpcResult depositCouponReceivedAccounts(RpcHeader rpcHeader, List<DistributorCouponAccount> distributorCouponAccounts);

    /**
     * 从过账缓冲账户给ad多个账户分配返利
     * <p>
     * 由支付模块页面调用
     *
     * @param rpcHeader                  头
     * @param distributorPrepaidAccounts 账户信息
     * @return
     */
    RpcResult depositPrepaidReceivedAccounts(RpcHeader rpcHeader, List<DistributorPrepaidAccount> distributorPrepaidAccounts);


    /**
     * 查询ad现金账户流水
     * <p>
     * 由支付模块页面调用、ad模块账户页面调用
     *
     * @param rpcHeader     头
     * @param currencyCode  货币编码
     * @param distributorId 分销商id
     * @param projectId     项目id
     * @param moneyFlow     现金流向
     * @param beginDate     查询起始日期
     * @param endDate       查询截止日期
     * @return
     */
    PageInfo<FrontPageFlow> selectCashFlow(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize);

    /**
     * 查询代垫实收账户小计
     * <p>
     * 由支付模块页面调用、ad模块账户页面调用
     *
     * @param rpcHeader     头
     * @param currencyCode  货币编码
     * @param distributorId 分销商id
     * @param projectId     项目id
     * @param moneyFlow     现金流向
     * @param beginDate     查询起始日期
     * @param endDate       查询截止日期
     * @return
     */
    FrontPageFlowSubtotal getCashSubtotal(RpcHeader rpcHeader, String currencyCode, long distributorId, long projectId, Integer moneyFlow, Date beginDate, Date endDate);

    /**
     * 查询ad代垫流水
     *
     * @param currencyCode
     * @param distributorId
     * @param projectId
     * @param accountType
     * @param moneyFlow
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<FrontPageFlow> selectPrepaidFlow(RpcHeader rpcHeader, String currencyCode, long distributorId,
                                              long projectId,
                                              int accountType,
                                              Integer moneyFlow,
                                              Date beginDate, Date endDate,
                                              int pageNum,
                                              int pageSize);

    /**
     * 查询ad代垫流水统计
     *
     * @param rpcHeader
     * @param currencyCode
     * @param distributorId
     * @param projectId
     * @param moneyFlow
     * @param beginDate
     * @param endDate
     * @return
     */
    FrontPageFlowSubtotal getPrepaidSubtotal(RpcHeader rpcHeader, String currencyCode,
                                             long distributorId,
                                             long projectId,
                                             Integer moneyFlow,
                                             Date beginDate, Date endDate);
}
