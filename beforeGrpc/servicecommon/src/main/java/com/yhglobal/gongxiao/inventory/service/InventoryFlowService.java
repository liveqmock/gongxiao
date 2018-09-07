package com.yhglobal.gongxiao.inventory.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.warehousemanagement.bo.ProductInventoryFlowShow;

import java.util.Date;
import java.util.List;

/**
 * [库存中心]->[出入库流水]页
 *
 * @author : liukai
 */
public interface InventoryFlowService {


    /**
     * 查询条件：
     *   商品名称
     *   商品编码
     *   仓库名称
     *   起始时间
     *   结束时间
     * 返回库存流水信息
     *
     * @param rpcHeader rpc调用的header
     * @param pageNumber            页码(非空)
     * @param pageSize          条数(非空)
     * @param projectId         项目id(非空)
     * @param productModel      商品型号(可以空)
     * @param productName       商品名称(可以空)
     * @param warehouseName     仓库名称(可以空)
     * @param inventoryFlowId   库存流水id(可以空)
     * @param startDate         起始时间(可以空)
     * @param endDate           结束时间(可以空)
     * @return
     */
    PageInfo<ProductInventoryFlowShow> conditionalSelectInventoryFlow(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String productModel, String productName,
                                                                      String warehouseName, String inventoryFlowId,
                                                                      String startDate, String endDate);

}
