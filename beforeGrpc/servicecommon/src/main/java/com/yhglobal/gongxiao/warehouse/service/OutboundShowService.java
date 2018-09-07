package com.yhglobal.gongxiao.warehouse.service;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.bo.OutboundOrderBasicInfo;
import com.yhglobal.gongxiao.warehousemanagement.bo.OutboundOrderItemShowModel;
import com.yhglobal.gongxiao.warehousemanagement.bo.OutboundOrderShowModel;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanOutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;

import java.util.Date;
import java.util.List;

/**
 * 与页面交互出库单，出库单详情展示接口
 *
 * @author liukai
 */
public interface OutboundShowService {

    /**
     * 查询出库单信息
     *
     * @param rpcHeader rpc调用的header
     * @param projectId             项目id
     * @param inventoryNum          出库单号
     * @param salseNum              销售单号
     * @param createTimeBeging      创建开始时间
     * @param createTimeLast        创建结束时间
     * @param warehouseId           仓库id
     * @param productCode           商品编码
     * @param finishTimeBegin       结束起始时间
     * @param finishTimeLaste       结束终结时间
     * @param supplier              供应商
     * @param customer              客户
     * @return
     */
    PageInfo<OutboundOrderShowModel> selectOutStorageInfo(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String inventoryNum, String salseNum, String createTimeBeging, String createTimeLast, String warehouseId, String productCode, String finishTimeBegin, String finishTimeLaste, String supplier, String customer);

    /**
     * 根据出库单号查询出库单信息
     *
     * @param rpcHeader rpc调用的header
     * @param projectId      项目id
     * @param outboundOrderItemNo   出库单号
     * @return
     */
    OutboundOrderBasicInfo selectOutStorageInfoById(RpcHeader rpcHeader, String projectId, String outboundOrderItemNo,String productCode);

    /**
     * 查询出库单明细信息
     *
     * @param rpcHeader rpc调用的header
     * @param projectId     项目id
     * @param gongxiaoOutboundOrderNo  出库单号
     * @return
     */
    List<OutboundOrderItemShowModel> selectOutboundOrderItem(RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo);

    /**
     * 根据销售单号和商品编码 查询出库单
     *
     * @param rpcHeader rpc调用的header
     * @param projectId          项目id
     * @param salesOrderNo       销售单号
     * @param productCode       商品编
     * @return
     */
    OutboundOrderItem selectRecordBySalseNoAndProduct(RpcHeader rpcHeader, String projectId, String salesOrderNo, String productCode);

    /**
     * 根据销售单号查询 出库单
     *
     * @param rpcHeader rpc调用的header
     * @param projectId     项目id
     * @param salesNo       销售单号
     * @return
     */
    List<OutboundOrder> selectRecordBySalesNo(RpcHeader rpcHeader, String projectId,String salesNo);

    /**
     * 根据出库单好查询 出库单明细
     *
     * @param rpcHeader rpc调用的header
     * @param projectId                 项目id
     * @param gongxiaoOutboundOrderNo   出库单号
     * @return
     */
    List<OutboundOrderItem> selectRecordItemByOutboundOrderNo(RpcHeader rpcHeader, String projectId,String gongxiaoOutboundOrderNo);


}
