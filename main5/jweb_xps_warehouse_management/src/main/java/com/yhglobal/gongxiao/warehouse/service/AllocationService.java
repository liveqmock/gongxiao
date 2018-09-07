package com.yhglobal.gongxiao.warehouse.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
import com.yhglobal.gongxiao.warehouse.model.bo.AllocationOrderShowModel;

import java.util.List;

/**
 * 调拨单接口
 */
public interface AllocationService {

    /**
     * 创建调拨单
     * @param allocationOrder           调拨单
     * @param allocationOrderItemList   调拨单详情
     * @return
     */
    int createAllocationRecord(GongxiaoRpc.RpcHeader rpcHeader, AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList);

    /**
     * 多条件查询调拨单
     * @param rpcHeader
     * @param projectId                 项目id
     * @param allocateNo                调拨单号
     * @param gongxiaoOutboundOrderNo   出库单号
     * @param gongxiaoInboundOrderNo    入库单号
     * @param warehouseOut              调出仓库
     * @param warehouseEnter            调入仓库
     * @param createBeginTime           创建起始时间
     * @param createEndTime             创建结束时间
     * @return
     */
    PageInfo<AllocationOrderShowModel> getAllRecordByCondition(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo,
                                                               String warehouseOut, String warehouseEnter, String createBeginTime, String createEndTime);

    /**
     * 根据调拨单号查询调拨单基本信息
     * @param rpcHeader
     * @param projectId     项目id
     * @param allocateNo    调拨单号
     * @return
     */
    AllocationOrder selectInfoByAllocateNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String allocateNo);

    /**
     * 根据调拨单号查询调拨单详情记录
     * @param rpcHeader
     * @param projectId     项目id
     * @param allocateNo    调拨单号
     * @return
     */
    List<AllocationOrderItem> getAllocationOrderItemInfos(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String allocateNo);

    /**
     * 根据调拨单号更新调拨单实际调入数量
     * @param rpcHeader
     * @param alllocateNo
     * @param quantity
     * @return
     */
    int updateAllocateOrderEntry(GongxiaoRpc.RpcHeader rpcHeader, String alllocateNo, int quantity);

    /**
     * 根据调拨单号更新调拨单实际调入数量
     * @param rpcHeader
     * @param alllocateNo
     * @param quantity
     * @return
     */
    int updateAllocateOrderOut(GongxiaoRpc.RpcHeader rpcHeader, String alllocateNo, int quantity);

    /**
     * 根据调拨单号更新调拨入库单明细状态
     * @param rpcHeader
     * @param alllocateNo
     * @param productCode
     * @param quantity
     * @return
     */
    int updateAllocateEntryItem(GongxiaoRpc.RpcHeader rpcHeader, String alllocateNo, String productCode, int quantity);

    /**
     * 根据调拨单号更新调拨出库单状态
     * @param rpcHeader
     * @param alllocateNo
     * @param productCode
     * @param quantity
     * @return
     */
    int updateAllocateOutItem(GongxiaoRpc.RpcHeader rpcHeader, String alllocateNo, String productCode, int quantity);
}
