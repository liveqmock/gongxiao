package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.service.ProductInventoryService;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanOutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 销售预约出库后 通过该任务 修改库存的销售占用数量
 */
public class NotificationInventoryTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(NotificationInventoryTask.class);

    private RpcHeader rpcHeader;
    private ProductInventoryService productInventoryService;
    private String projectId;
    private List<PlanOutboundOrderItem> itemList;

    public NotificationInventoryTask(RpcHeader rpcHeader, ProductInventoryService productInventoryService, String projectId, List<PlanOutboundOrderItem> itemList) {
        this.rpcHeader = rpcHeader;
        this.productInventoryService = productInventoryService;
        this.projectId = projectId;
        this.itemList = itemList;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start updateInventory, params: projectId={},itemList={}", rpcHeader.traceId,projectId,JSON.toJSONString(itemList));
        try {
            for (PlanOutboundOrderItem record : itemList){
                ProductInventory productInventory = new ProductInventory();
                productInventory.setPurchaseType(record.getPurchaseType());
                productInventory.setInventoryStatus(record.getInventoryType());
                productInventory.setProjectId(Long.parseLong(projectId));
                productInventory.setBatchNo(record.getBatchNo());
                productInventory.setProductCode(record.getProductCode());
                productInventory.setWarehouseId(record.getWarehouseId());
                productInventory.setOnSalesOrderQuantity(record.getTotalQuantity());
                productInventoryService.updateInventoryInfo(rpcHeader,productInventory);
                logger.info("#traceId={}# [OUT] get NotificationInventoryTask.run() success", rpcHeader.traceId);
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
