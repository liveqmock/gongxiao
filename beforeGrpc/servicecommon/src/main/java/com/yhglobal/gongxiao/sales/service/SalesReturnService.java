package com.yhglobal.gongxiao.sales.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.sales.bo.SalesReturnClassificationCount;
import com.yhglobal.gongxiao.sales.model.cancel.dto.OutBound;
import com.yhglobal.gongxiao.sales.model.cancel.dto.OutBoundItem;
import com.yhglobal.gongxiao.sales.model.cancel.dto.SalesReturn;
import com.yhglobal.gongxiao.sales.model.cancel.model.SalesReturnOrder;

import java.util.List;
import java.util.Map;

/**
 * 销售退货service
 */
public interface SalesReturnService {

    /**
     * 退货实际入库回调接口
     * @param rpcHeader
     * @param inboundOrderNo 入库单号
     * @param traceNo  调用的发起方携带的标签 【退货单号】
     * @param productCode 商品编号
     * @param productName 商品名称
     * @param productUnit 商品单位
     * @param inStockQuantity 退货数量
     */
    public boolean salesReturnInbound(RpcHeader rpcHeader, String inboundOrderNo, String traceNo, String productCode, String productName, String productUnit, int inStockQuantity);
    /**
     * 根据项目查询退货单列表
     * @param projectId
     * @param returnedType
     * @param salesReturnedOrderNo
     * @param warehouseId
     * @param timeStart
     * @param timeEnd
     * @param returnedOrderStatus
     * @return List<SalesReturnOrder>
     */
    public PageInfo<SalesReturnOrder> getsSalesReturnOrderByProject(RpcHeader rpcHeader, Integer projectId,
                                                                    Integer returnedType,
                                                                    String salesReturnedOrderNo,
                                                                    String warehouseId,
                                                                    String timeStart,
                                                                    String timeEnd,
                                                                    Integer returnedOrderStatus,
                                                                    Integer page,
                                                                    Integer pageSize);

    /**
     * 查询结果集中各分类数据条数
     * @param projectId
     * @param returnedType
     * @param salesReturnedOrderNo
     * @param warehouseId
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public List<SalesReturnClassificationCount> selectClassificationCount(RpcHeader rpcHeader, Integer projectId,
                                                                          Integer returnedType,
                                                                          String salesReturnedOrderNo,
                                                                          String warehouseId,
                                                                          String timeStart,
                                                                          String timeEnd);

    /**
     * 新增销售退货
     * @param salesReturnOrder 销售退货实体
     * @return
     */
    public RpcResult<Integer> addSalesReturnOrder(RpcHeader rpcHeader, SalesReturnOrder salesReturnOrder);

    /**
     * 退货单批量审核
     * @param salesReturnOrder
     * @return
     */
    public int checkSalesReturnOrder(RpcHeader rpcHeader,SalesReturnOrder[] salesReturnOrder) throws Exception;

    /**
     * 按ID查询销售退货单
     * @param rpcHeader
     * @param salesReturnedOrderId
     * @return
     */
    public SalesReturn getSalesReturn(RpcHeader rpcHeader, Long salesReturnedOrderId);

    /**
     *
     * @param rpcHeader
     * @param salesOrderNo
     * @param gongxiaoOutboundOrderNo
     * @return
     */
    public List<OutBoundItem> getOutBoundItem(RpcHeader rpcHeader,String projectId,  String salesOrderNo,String gongxiaoOutboundOrderNo);

    /**
     * 按销售单查出库单
     * @param rpcHeader
     * @param projectId
     * @param salesNo
     * @return
     */
    public List<OutBound> getOutBound(RpcHeader rpcHeader, String projectId, String salesNo);
}
