package com.yhglobal.gongxiao.inventory.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.bo.InventoryBatch;
import com.yhglobal.gongxiao.inventory.bo.InventoryBatchDetail;
import com.yhglobal.gongxiao.inventory.bo.ProductToBeDelivered;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;

import java.util.List;
import java.util.Map;

/**
 * [销售管理]->[订单管理]->发货通知页面 返回批次库存
 *
 * @author liukai
 */
public interface InventoryBatchService {

    /**
     * 智能分仓
     * @param projectId     项目id
     * @param productList   商品列表
     * @return  Map<warehouseId:productCode, Map<productCode, ProductInventory>>
     * 因为不同种商品的可能在同一仓库内,所以用"仓库id+商品编码",作为key
     */
    Map<String, Map<String, ProductInventory>> getDetailedInventory(String projectId,List<ProductToBeDelivered> productList);

    /**
     * 查询条件：
     *   项目
     *   商品编码
     * 返回同一个项目下，同一种商品的，每个批次的记录
     *
     * @param rpcHeader
     * @param projectId      项目id(非空)
     * @param productCode    商品编码(非空)
     * @return  projectId(项目);batchNo(批次);warehouseName(仓库名称);createTime(创建时间);purchasePrice(采购价格);inventoryBatchAmount(库存数量)
     */
    PageInfo<InventoryBatch> getBatchDetail(RpcHeader rpcHeader, String projectId, String productCode, int pageNumber, int pageSize);

    /**
     * 根据所选的仓库和商品查询
     * @param rpcHeader
     * @param projectId    项目id(非空)
     * @param productCode  商品编码(非空)
     * @param warehouseId  仓库id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<InventoryBatch> getBatchDetailByWarehouse(RpcHeader rpcHeader, String projectId, String productCode, String warehouseId, int pageNumber, int pageSize);

}
