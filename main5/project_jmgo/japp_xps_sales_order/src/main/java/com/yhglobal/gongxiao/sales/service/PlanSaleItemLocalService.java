package com.yhglobal.gongxiao.sales.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;

import java.util.Date;

/**
 * 销售计划明细
 *
 * @author 葛灿
 */
public interface PlanSaleItemLocalService {

    /**
     * 更新销售占用信息
     * 正向流程 未处理数量->销售占用  下单
     * 逆向流程 销售占用->未处理数量  审核前取消
     *
     * @param rpcHeader
     * @param projectId              必填   项目ID
     * @param distributorId          必填   经销商ID
     * @param productId              必填   货品Id
     * @param businessDate           必填   业务发生时间
     * @param saleOccupationQuantity 必填  销售占用变更数量
     * @return
     */
    RpcResult updateSaleOccupation(String prefix,
                                   GongxiaoRpc.RpcHeader rpcHeader,
                                   String projectId,
                                   String distributorId,
                                   String productId,
                                   Date businessDate,
                                   int saleOccupationQuantity);


    /**
     * 更新已售数量信息
     *  正向流程 审核操作 销售占用->已售  soldQuantity为正数
     *  逆向流程 审核后取消 已售->销售占用  soldQuantity为负数
     * @param rpcHeader
     * @param projectId     必填  项目ID
     * @param distributorId 必填  经销商ID
     * @param productCode   必填  商品ID
     * @param businessDate  必填  业务发生时间
     * @param soldQuantity  必填  已售数量
     * @return
     */
    RpcResult updateSoldQuantity(String prefix,
                                 GongxiaoRpc.RpcHeader rpcHeader,
                                 String projectId,
                                 String distributorId,
                                 String productCode,
                                 Date businessDate,
                                 int soldQuantity);

    /**
     * 取消审核销售订单
     * @param prefix
     * @param rpcHeader
     * @param projectId
     * @param distributorId
     * @param productCode
     * @param businessDate
     * @param changeNumber
     * @return
     */
    RpcResult cancelAuditSaleOrder(String prefix,
                                   GongxiaoRpc.RpcHeader rpcHeader,
                                   String projectId,
                                   String distributorId,
                                   String productCode,
                                   Date businessDate,
                                   int changeNumber);


}
