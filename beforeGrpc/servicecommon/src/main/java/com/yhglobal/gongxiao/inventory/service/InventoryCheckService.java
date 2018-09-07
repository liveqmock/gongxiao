package com.yhglobal.gongxiao.inventory.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.bo.InventoryCheckModel;


/**
 * 库存核对的结果的接口类
 * @author liukai
 */
public interface InventoryCheckService {
    /**
     * 查询库存核对的结果
     * @param projectId     项目id
     * @param warehouseId   仓库id
     * @param productCode   商品编码
     * @param productName   商品名称
     * @return
     */
    PageInfo<InventoryCheckModel> getInventoryCheck(RpcHeader rpcHeader, String projectId, String warehouseId, String productCode, String productName, int pageNumber, int pageSize);
}
