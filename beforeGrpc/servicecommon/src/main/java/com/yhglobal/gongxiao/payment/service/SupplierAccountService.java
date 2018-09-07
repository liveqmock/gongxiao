package com.yhglobal.gongxiao.payment.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.CashCouponPrepaidFlows;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.dto.PurchaseFlowCollections;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferToDistributorFlow;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferToDistributorFlow;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightTransferRecord;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
public interface SupplierAccountService {

    /**
     * 查询供应商过账ad账户余额（返利、代垫）
     * <p>
     * 由支付模块页面调用
     * 采购模块查询余额调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId
     * @return
     */
    AccountAmount getSupplierCouponBufferToDistributor(RpcHeader rpcHeader,
                                                       String currencyCode,
                                                       long projectId);

    /**
     * 查询供应商上账账户余额(返利、代垫)
     * <p>
     * 由支付模块页面调用
     * 采购模块查询余额调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    AccountAmount getSupplierAccountAmount(RpcHeader rpcHeader, String currencyCode, long projectId);

    /**
     * 返利上账
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param couponAmount 修改金额
     * @param remark       备注
     * @return
     */
    String postSupplierCouponAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long couponAmount,
                                     String remark);

    /**
     * 代垫上账
     * <p>
     * 支付模块页面嗲用
     *
     * @param rpcHeader     头
     * @param currencyCode  货币编码
     * @param projectId     项目id
     * @param prepaidAmount 修改金额
     * @param remark        备注
     * @return
     */
    String postSupplierPrepaidAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long prepaidAmount,
                                      String remark);


    /**
     * 上账账户暂扣返利、代垫
     * 上账账户转入ad过账账户
     * <p>
     * 采购模块调用
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param couponToYh      yh采购使用的返利金额，账户中暂扣
     * @param couponToAd      转到ad缓冲的返利
     * @param prepaidToYh     yh采购使用的代垫金额，账户中暂扣
     * @param prepaidToAd     转到ad缓冲的代垫
     * @param purchaseOrderNo 采购单号
     * @param transactionTime 交易时间
     * @return PurchaseFlowCollections
     */
    RpcResult<PurchaseFlowCollections> payForPurchase(RpcHeader rpcHeader, String currencyCode, long projectId,
                                                      long couponToYh, long couponToAd,
                                                      long prepaidToYh, long prepaidToAd,
                                                      String purchaseOrderNo, Date transactionTime);


    /**
     * 查询 ad返利过账账户 流水
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @param pageNum      页码
     * @param pageSize     条数
     * @return
     */
    PageInfo<FrontPageFlow> selectBufferCouponFlowList(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow,
                                                       Date beginDate,
                                                       Date endDate, int pageNum, int pageSize);

    /**
     * 查询 ad代垫过账账户 流水
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @param pageNum      页码
     * @param pageSize     条数
     * @return
     */
    PageInfo<FrontPageFlow> selectBufferPrepaidFlowList(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize);

    /**
     * 查询 ad返利过账账户 流水小计
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @return
     */
    FrontPageFlowSubtotal getCouponBufferToDistributorSubtotal(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate);

    /**
     * 查询 ad代垫过账账户 流水小计
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @return
     */
    FrontPageFlowSubtotal getPrepaidBufferToDistributorSubtotal(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate);

    /**
     * 查询供应商差价账户余额
     * <p>
     * 支付模块核销时调用
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @return
     */
    SupplierSellHeightAccount getSellHighAccount(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId);

    /**
     * 销售更新低买高卖账户
     *
     * @param rpcHeader       头
     * @param currencyCode    货币
     * @param projectId       项目id
     * @param amount          交易金额 销售产生差价，，amount为+  销售退货扣减差价，amount为-
     * @param salesOrderNo    销售（退货）单号
     * @param transactionTime 交易时间
     * @return
     */
    RpcResult salesUpdateSellHighAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long amount, String salesOrderNo, Date transactionTime);


    /**
     * 查询 供应商低买高卖账户 流水
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @param pageNum      页码
     * @param pageSize     条数
     * @return
     */
    PageInfo<FrontPageFlow> selectSupplierSellHighRecordList(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize);

    /**
     * 查询 供应商低买高卖账户 小计
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @param moneyFlow    资金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @return
     */
    FrontPageFlowSubtotal getSellHighSubtotal(RpcHeader rpcHeader, String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate);

    /**
     * 查询供应商代垫流水 包括收入和支出
     * <p>
     * 支付模块页面调用
     *
     * @param supplierId 供应商ID
     **/
    PageInfo<FrontPageFlow> selectPrepaidBySupplierId(String currencyCode,
                                                      Long supplierId,
                                                      long projectId,
                                                      Integer moneyFlow,
                                                      Date beginDate,
                                                      Date endDate, int pageNum, int pageSize);

    /**
     * 收入支出统计
     * <p>
     * 支付模块页面调用
     *
     * @param currencyCode
     * @param supplierId
     * @param projectId
     * @param moneyFlow
     * @param beginDate
     * @param endDate
     * @return
     */
    FrontPageFlowSubtotal selectIncomeAndExpenditure(String currencyCode, Long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate);
}
