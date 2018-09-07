package com.yhglobal.gongxiao.warehouse.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.bo.WmsInboundBasic;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsIntboundRecordItem;

import java.util.List;

/**
 * 入库通知单接口
 */
public interface InboundNotifyOrderService {
    /**
     * 多条件查询
     * @param rpcHeader
     * @param pageNumber          页码
     * @param pageSize              条数
     * @param projectId             项目
     * @param gonxiaoInboundNo     入库单
     * @param batchNo            批次号
     * @param orderType           订单类型
     * @param warehouseId           仓库id
     * @param supplier              供应商
     * @param wmsInboundOrderNo     入库通知单号
     * @param createTimeBegin       创建起始时间
     * @param createTimeTo          创建结束时间
     * @return
     */
    PageInfo<WmsIntboundRecord> selectInStorageInfo(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String  gonxiaoInboundNo, String  batchNo, String  orderType, String  warehouseId, String  supplier, String wmsInboundOrderNo,String createTimeBegin, String createTimeTo);

    /**
     * 查询入库通知单基本信息
     * @param rpcHeader
     * @param projectId
     * @param gongxiaoInboundOrderNo
     * @return
     */
    WmsInboundBasic selectRecordByOrderNo(RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo, String wmsInboundOrderNo);

    /**
     * 查询wms入库通知单详情
     * @param rpcHeader
     * @param projectId
     * @param gongxiaoInboundOrderNo
     * @return
     */
    List<WmsIntboundRecordItem> selectWmsItemRecordByOrderNo(RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo, String wmsInboundOrderNo);

}
