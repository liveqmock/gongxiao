package com.yhglobal.gongxiao.warehouse.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.bo.WmsOutboundBasic;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecord;
import com.yhglobal.gongxiao.warehousemanagement.model.WmsOutboundRecordItem;

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
     * @param salesNo            采购单
     * @param orderType           订单类型
     * @param createTimeBegin       创建起始时间
     * @param createTimeTo          创建结束时间
     * @param customer              客户
     * @return
     */
    PageInfo<WmsOutboundRecord> selectOutStorageInfo(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String  gonxiaoOutboundNo, String  salesNo, String  orderType,String  customer, String createTimeBegin, String createTimeTo );


    /**
     * 根据单号查询出库单
     * @param rpcHeader
     * @param wmsOutboundOrderNo
     * @param gongxiaoOutboundOrderNo
     * @return
     */
    WmsOutboundBasic selectRecordByOrderNo(RpcHeader rpcHeader, String gongxiaoOutboundOrderNo, String wmsOutboundOrderNo);

    /**
     * 查询wms出库通知单详情
     * @param rpcHeader
     * @param projectId
     * @param gongxiaoOutboundOrderNo
     * @return
     */
    List<WmsOutboundRecordItem> selectWmsItemRecordByOrderNo(RpcHeader rpcHeader, String projectId, String gongxiaoOutboundOrderNo,String wmsOutboundOrderNo);


}
