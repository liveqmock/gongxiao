package com.yhglobal.gongxiao.payment.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToDistributor;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponRecord;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidRecord;

import java.util.Date;
import java.util.List;

/**
 * @author: 葛灿
 */
public interface YhglobalAccountService {

    /**
     * 查询越海实收账户余额
     * <p>
     * 支付模块页面查询
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    AccountAmount getYhglobalReceivedAccountAmount(RpcHeader rpcHeader,
                                                   String currencyCode,
                                                   long projectId);


    /**
     * 查询返利实收账户流水
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param moneyFlow    现金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @return
     */
    PageInfo<FrontPageFlow> selectCouponReceivedRecords(RpcHeader rpcHeader,
                                                        String currencyCode,
                                                        long projectId, Integer moneyFlow,
                                                        Date beginDate, Date endDate,
                                                        int pageNum, int pageSize);

    /**
     * 查询返利实收账户小计
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param moneyFlow    现金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @return
     */
    FrontPageFlowSubtotal getCouponSubtotal(RpcHeader rpcHeader,
                                            String currencyCode,
                                            long projectId, Integer moneyFlow,
                                            Date beginDate, Date endDate);


    /**
     * 查询代垫实收账户流水
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param moneyFlow    现金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @return
     */
    PageInfo<FrontPageFlow> selectPrepaidReceivedRecords(RpcHeader rpcHeader, String currencyCode, long projectId, Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize);

    /**
     * 查询代垫实收账户小计
     * <p>
     * 支付模块页面调用
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @param moneyFlow    现金流向
     * @param beginDate    查询起始日期
     * @param endDate      查询截止日期
     * @return
     */
    FrontPageFlowSubtotal getPrepaidSubtotal(RpcHeader rpcHeader, String currencyCode, long projectId, Integer moneyFlow, Date beginDate, Date endDate);

    /**
     * 越海实收返利转出
     *
     * @param rpcHeader    头
     * @param projectId    项目id
     * @param amountDouble 金额
     * @param remark       备注
     * @return
     */
    RpcResult transferYhglobalReceivedCoupon(RpcHeader rpcHeader, long projectId, double amountDouble, String remark);

    /**
     * 越海实收代垫转出
     *
     * @param rpcHeader    头
     * @param projectId    项目id
     * @param amountDouble 金额
      * @param remark       备注
     * @return
     */
    RpcResult transferYhglobalReceivedPrepaid(RpcHeader rpcHeader, long projectId, double amountDouble, String remark);

}
