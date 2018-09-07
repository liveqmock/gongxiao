package com.yhglobal.gongxiao.warehouse.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.bo.AllocationOrderShowModel;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.AllocationOrderItem;

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
    int createAllocationRecord(RpcHeader rpcHeader,AllocationOrder allocationOrder,List<AllocationOrderItem> allocationOrderItemList);

    /**
     * 多条件查询调拨单
     * @param rpcHeader
     * @param projectId                 项目id
     * @param allocateNo                调拨单号
     * @param gongxiaoOutboundOrderNo   出库单号
     * @param gongxiaoInboundOrderNo    入库单号
     * @param warehouseOut              调出仓库
     * @param warehouseEnter            调入仓库
     * @param status                    状态
     * @param createBeginTime           创建起始时间
     * @param createEndTime             创建结束时间
     * @return
     */
    PageInfo<AllocationOrderShowModel> getAllRecordByCondition(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String allocateNo, String gongxiaoOutboundOrderNo, String gongxiaoInboundOrderNo,
                                                               String warehouseOut, String warehouseEnter, String status, String createBeginTime, String createEndTime);

    /**
     * 根据调拨单号查询调拨单基本信息
     * @param rpcHeader
     * @param projectId     项目id
     * @param allocateNo    调拨单号
     * @return
     */
    AllocationOrder selectInfoByAllocateNo(RpcHeader rpcHeader,String projectId,String allocateNo);

    /**
     * 根据调拨单号查询调拨单详情记录
     * @param rpcHeader
     * @param projectId     项目id
     * @param allocateNo    调拨单号
     * @return
     */
    List<AllocationOrderItem> getAllocationOrderItemInfos(RpcHeader rpcHeader,String projectId,String allocateNo);

    /**
     * 根据调拨单号更新调拨单状态
     * @param rpcHeader
     * @param projectId
     * @param allocateNo
     * @return
     */
    int updateByAllocateNo(RpcHeader rpcHeader,String projectId,String allocateNo);
}
