package com.yhglobal.gongxiao.inventory.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.bo.ProductInventoryBriefSummary;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.warehouse.bo.ProductInventoryShowModel;
import com.yhglobal.gongxiao.warehouse.bo.WarehouseInventoryShowModel;

import java.util.List;
import java.util.Map;

/**
 * 按货品或仓库的维度 查询库存
 *
 * @author : liukai
 */
public interface ProductInventoryService {


    /**
     * [库存中心]->[货品库存]页 根据productName 分页查询 货品库存
     *
     * @param rpcHeader rpc调用的header
     * @param pageNo
     * @param pageSize
     * @param projectId
     * @param productName
     * @return
     */
    List<ProductInventoryShowModel> selectProductInventoryByName(RpcHeader rpcHeader, int pageNo, int pageSize, long projectId, String productCode, String productName);


    /**
     * [库存中心]->[仓库库存] 按商品编码+商品名称+仓库名称条件 分页查询仓库库存
     *
     * @param rpcHeader rpc调用的header
     * @param pageNo  第几页
     * @param pageSize  每页条数
     * @param projectId  项目id
     * @param productCode  商品编码
     * @param productName  商品名称
     * @param warehouseId  仓库ID
     * @return
     */
    List<WarehouseInventoryShowModel> conditionalSelectWarehouseInventory(RpcHeader rpcHeader, int pageNo, int pageSize, String projectId, String productCode, String productName, String warehouseId);


    /**
     * 实时查询 单个商品的库存
     *
     * @param rpcHeader rpc调用的header
     * @param projectId
     * @param productCode
     * @return
     */
    int selectQuantityByProjectIdAndProductCode(RpcHeader rpcHeader, String projectId, String productCode);

    /**
     * 跟新库存信息
     * @param rpcHeader
     * @param productInventory
     * @return
     */
    int updateInventoryInfo(RpcHeader rpcHeader,ProductInventory productInventory);

}
