package com.yhglobal.gongxiao.warehouse.service;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderBasicInfo;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderItemShowModel;
import com.yhglobal.gongxiao.warehouse.model.bo.InboundOrderShowModel;

import java.util.List;

/**
 * 与页面交互的入库单，入库单详情展示
 * @author liukai
 */
public interface InboundShowService {
    /**
     * 查询入库单信息
     *
     * @param rpcHeader rpc调用的header
     * @param gonxiaoInboundNo      入库单号(非空)
     * @param purchaseNo      入库单号(非空)
     * @param productCode       商品编码(可以空)
     * @param createTime        创建时间(可以空)
     * @param warehouseId     仓库名称(可以空)
     * @param supplier          供应商(可以空)
     * @return
     */
    PageInfo<InboundOrderShowModel> selectInStorageInfo(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String gonxiaoInboundNo, String purchaseNo, String productCode, String goodCode, String createTime, String warehouseId, String supplier);

    /**
     * 根据入库单号查询入库单
     *
     * @param rpcHeader rpc调用的header
     * @param projectId      项目id(非空)
     * @param inventoryNum   入库单号(非空)
     * @return
     */
    InboundOrderBasicInfo selectRecordById(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String inventoryNum);

//    /**
//     * 采购单跳转到入库单
//     *
//     * @param rpcHeader rpc调用的header
//     * @param projectId         项目id(非空)
//     * @param purchaseNo        采购单号(非空)
//     * @param inventoryNum      入库单号(可以空)
//     * @param productCode       商品编码(可以空)
//     * @param createTime        创建时间(可以空)
//     * @param warehouseId       仓库id(可以空)
//     * @param supplierName      供应商(可以空)
//     * @return
//     */
//    List<InboundOrder> selectRecordByPurchaseNo(RpcHeader rpcHeader, String projectId,String purchaseNo,String inventoryNum,String productCode,String createTime,String warehouseId,String supplierName);

    /**
     * 采购单 查询入库单
     *
     * @param rpcHeader rpc调用的header
     * @param projectId     项目id(非空)
     * @param PurchaseNo    采购单号(非空)
     * @return
     */
    List<InboundOrder> selectInboundRecordByPurchaseNo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String PurchaseNo);

    /**
     * 通过入库单号 查询 入库单明细
     * @param projectId     项目id(非空)
     * @param inventoryNum  入库单号(非空)
     * @return
     */
    List<InboundOrderItem> selectInStorageDetailInfoByinventoryNum(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String inventoryNum);

    /**
     * 查询入库单对应的入库单详情
     *
     * @param rpcHeader rpc调用的header
     * @param inventoryNum   入库单号(非空)
     * @return
     */
    List<InboundOrderItemShowModel> selectInStorageDetailInfoById(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String inventoryNum);


}
