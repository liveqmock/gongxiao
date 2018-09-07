package com.yhglobal.gongxiao.warehouse.service;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecord;
import com.yhglobal.gongxiao.warehouse.model.WmsIntboundRecordItem;

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
     * @param gonxiaoInboundNo      入库单
     * @param purchaseNo            采购单
     * @param productCode           商品编码
     * @param createTimeBegin       创建起始时间
     * @param createTimeTo          创建结束时间
     * @param warehouseId           仓库id
     * @param supplier              供应商
     * @return
     */
    PageInfo<WmsIntboundRecord> selectInStorageInfo(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String  gonxiaoInboundNo, String  purchaseNo, String  productCode, String createTimeBegin, String createTimeTo, String  warehouseId, String  supplier);

    /**
     *
     * @param rpcHeader
     * @param projectId
     * @param gongxiaoInboundOrderNo
     * @return
     */
    WmsIntboundRecord selectRecordByOrderNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo,String wmsInboundOrderNo);

    /**
     * 查询wms入库通知单详情
     * @param rpcHeader
     * @param projectId
     * @param gongxiaoInboundOrderNo
     * @return
     */
    List<WmsIntboundRecordItem> selectWmsItemRecordByOrderNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo,String wmsInboundOrderNo);

}
