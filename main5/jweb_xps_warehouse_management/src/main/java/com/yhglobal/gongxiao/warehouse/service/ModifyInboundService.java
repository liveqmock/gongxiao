package com.yhglobal.gongxiao.warehouse.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

public interface ModifyInboundService {
    /**
     * 根据wms系统返回的入库确认信息 更新入库单
     * @param rpcHeader
     * @param projectId
     * @param gongxiaoInBoundOrderNo   入库单号
     * @param productCode       商品编码
     * @param imperfectQuantity 残次
     * @param inStockQuantity   入库可用数量
     * @param inTransitQuantity    在途
     * @param realInStockQuantity   实际入库数量
     * @return
     */
    int modifyInboundItermByWms(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoInBoundOrderNo, String productCode, int imperfectQuantity, int inStockQuantity, int inTransitQuantity, int realInStockQuantity);

    /**
     * 根据wms系统返回的入库确认信息 更新入库单
     * @param rpcHeader
     * @param projectId                  项目id
     * @param gongxiaoInboundOrderNo     入库单号
     * @param realInStockQuantity        实际入库数量
     * @param imperfectQuantity           残次
     * @param inStockQuantity             良品
     * @param inTransitQuantity            在途
     * @return boolean 返回更新后的入库汇总单是否已完成
     */
    boolean modifyInboundOderByWms(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo, int realInStockQuantity, int imperfectQuantity, int inStockQuantity, int inTransitQuantity);


}
