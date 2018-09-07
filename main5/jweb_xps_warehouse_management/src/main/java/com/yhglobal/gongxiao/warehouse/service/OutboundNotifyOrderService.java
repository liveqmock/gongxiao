package com.yhglobal.gongxiao.warehouse.service;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehouse.model.WmsOutboundRecordItem;
import com.yhglobal.gongxiao.warehouse.model.bo.WmsOutboundBasic;

import java.util.List;

/**
 * 出库单管理接口
 * @author liukai
 */
public interface OutboundNotifyOrderService {
    /**
     * 多条件查询出库单
     * @param rpcHeader
     * @param pageNumber          页码
     * @param pageSize              条数
     * @param projectId             项目
     * @param gonxiaoOutboundNo      入库单
     * @param purchaseNo            采购单
     * @param productCode           商品编码
     * @param createTimeBegin       创建起始时间
     * @param createTimeTo          创建结束时间
     * @param warehouseId           仓库id
     * @param customer              客户
     * @return
     */
    PageInfo<WmsOutboundRecord> selectOutStorageInfo(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String  gonxiaoOutboundNo, String  purchaseNo, String  productCode, String createTimeBegin, String createTimeTo, String  warehouseId, String  customer);


    /**
     * 根据单号查询出库单
     * @param rpcHeader
     * @param wmsOutboundOrderNo
     * @param gongxiaoOutboundOrderNo
     * @return
     */
    WmsOutboundBasic selectRecordByOrderNo(GongxiaoRpc.RpcHeader rpcHeader, String gongxiaoOutboundOrderNo, String wmsOutboundOrderNo);

    /**
     * 查询wms出库通知单详情
     * @param rpcHeader
     * @param projectId
     * @param gongxiaoOutboundOrderNo
     * @param wmsOutboundOrderNo
     * @return
     */
    List<WmsOutboundRecordItem> selectWmsItemRecordByOrderNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo, String wmsOutboundOrderNo);


}
