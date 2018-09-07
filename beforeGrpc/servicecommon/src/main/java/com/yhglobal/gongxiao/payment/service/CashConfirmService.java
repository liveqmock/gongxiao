package com.yhglobal.gongxiao.payment.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.payment.model.CashConfirm;

import java.util.Date;
import java.util.List;

/**
 * 销售收款确认表Service
 *
 * @author: 葛灿
 */
public interface CashConfirmService {
    /**
     * 创建现金应收单
     * <p>
     * 用于销售订单审核通过的节点
     * 由销售模块发起调用
     *
     * @param rpcHeader       头
     * @param salesOrderNo    销售单号
     * @param distributorId   分销商id
     * @param distributorName 分销商名称
     * @param projectId       项目id
     * @param projectName     项目名称
     * @param currencyCode    货币编码
     * @param cashAmount      收款金额
     * @param createTime      创建时间
     * @return
     */
    RpcResult createSalesOrderCashConfirmOrder(RpcHeader rpcHeader,
                                          String salesOrderNo, Long distributorId,
                                          String distributorName, Long projectId,
                                          String projectName, String currencyCode,
                                          Long cashAmount, Date createTime);


    /**
     * 选择性查询现金确认列表(均为可选条件)
     *
     * @param rpcHeader       头
     * @param salesOrderNo    销售单号
     * @param projectId       项目id
     * @param distributorName ad名称
     * @param bankName        收款银行
     * @param confirmBegin    确认时间开始
     * @param confirmEnd      确认时间截止
     * @param orderBegin      订单时间起始
     * @param orderEnd        订单时间截止
     * @param orderStatus     订单状态
     * @param pageNum         pageNum
     * @param pageSize        pageSize
     * @return
     */
    PageInfo<CashConfirm> selectListSelective(RpcHeader rpcHeader,
                                              String salesOrderNo,
                                              Long projectId,
                                              String distributorName,
                                              String bankName,
                                              Date orderBegin,
                                              Date orderEnd,
                                              Date confirmBegin,
                                              Date confirmEnd,
                                              Integer[] orderStatus,
                                              int pageNum,
                                              int pageSize);


    /**
     * 收款确认详情页面
     *
     * @param rpcHeader        头
     * @param salesOrderNoList 销售单号
     * @return
     */
    List<CashConfirm> selectConfirmList(RpcHeader rpcHeader, String[] salesOrderNoList);

    /**
     * 确认收款通过
     *
     * @param rpcHeader     头
     * @param bankName      收款银行
     * @param recipientDate 收款日期
     * @param items         商品信息
     */
    RpcResult confirmCash(RpcHeader rpcHeader, String bankName, Date recipientDate, List<CashConfirm> items,String bankFlowNo) throws CloneNotSupportedException;

    /**
     * 取消订单确认
     *
     * @param rpcHeader       头
     * @param salesOrderNo    销售单号
     * @param prestoredAmount 预存金额
     * @param couponAmount    返利金额
     * @param prepaidAmount   代垫金额
     * @return
     */
    RpcResult cancelCashConfirm(RpcHeader rpcHeader, String salesOrderNo, long prestoredAmount, Long couponAmount, long prepaidAmount);

    /**
     * 自动确认“待确认金额为-值”的条目
     *
     * @param rpcHeader    头
     * @param salesOrderNo 销售单号
     * @return
     */
    RpcResult confirmNegativeAmountAutomatically(RpcHeader rpcHeader, String salesOrderNo);
}
