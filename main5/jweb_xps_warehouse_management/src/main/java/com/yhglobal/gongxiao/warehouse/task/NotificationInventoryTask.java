package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.account.microservice.ProductInventoryStructure;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.model.dto.PlanOutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 销售预约出库后 通过该任务 修改库存的销售占用数量
 */
public class NotificationInventoryTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(NotificationInventoryTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;
    private String projectId;
    private List<PlanOutboundOrderItem> itemList;

    public NotificationInventoryTask(GongxiaoRpc.RpcHeader rpcHeader, String projectId, List<PlanOutboundOrderItem> itemList) {
        this.rpcHeader = rpcHeader;
        this.projectId = projectId;
        this.itemList = itemList;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start updateInventory, params: projectId={},itemList={}", rpcHeader.getTraceId(), projectId, JSON.toJSONString(itemList));
        try {
            //调用库存模块的项目的grpc查询项目信息
            InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryService = WarehouseRpcStubStore.getRpcStub(InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub.class);

            for (PlanOutboundOrderItem record : itemList) {
                ProductInventoryStructure.ProductInventory.Builder productInventory = ProductInventoryStructure.ProductInventory.newBuilder()
                        .setPurchaseType(record.getPurchaseType())
                        .setInventoryStatus(record.getInventoryType())
                        .setProjectId(Long.parseLong(projectId))
                        .setBatchNo(record.getBatchNo())
                        .setProductCode(record.getProductCode())
                        .setWarehouseId(record.getWarehouseId())
                        .setOnSalesOrderQuantity(record.getTotalQuantity());
                InventoryquerryStructure.UpdateRequest updateRequest = InventoryquerryStructure.UpdateRequest.newBuilder().setRpcHeader(rpcHeader).setProductInventory(productInventory).build();
                inventoryquerryService.updateInventoryInfo(updateRequest);
                logger.info("#traceId={}# [OUT] get NotificationInventoryTask.run() success", rpcHeader.getTraceId());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
