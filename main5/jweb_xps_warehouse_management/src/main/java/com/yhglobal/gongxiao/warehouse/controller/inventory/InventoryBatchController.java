package com.yhglobal.gongxiao.warehouse.controller.inventory;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventoryBatchServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.warehouse.controller.inventoryModel.InventoryBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryBatchController {

    private static Logger logger = LoggerFactory.getLogger(InventoryBatchController.class);

    @RequestMapping(value = "/getBatchDetail",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<InventoryBatch> getBatchDetail(String projectId, String productCode, int pageNumber, int pageSize){
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setTraceId("01").setUid("132").setUsername("liukai").build();
        logger.info("#traceId={}# [IN][getBatchDetail] params: pageNumber={}, pageSize={}, projectId={}, productCode={}" , rpcHeader.getTraceId(),pageNumber,pageSize,projectId,productCode);
        PageInfo<InventoryBatch> pageInfo = new PageInfo<>();
        try {
            List<InventoryBatch> resultList = new ArrayList<>();
            InventoryBatchServiceGrpc.InventoryBatchServiceBlockingStub inventoryBatchService = WarehouseRpcStubStore.getRpcStub(InventoryBatchServiceGrpc.InventoryBatchServiceBlockingStub.class);
            InventorybatchStructure.GetBatchDetailRequest getBatchDetailRequest = InventorybatchStructure.GetBatchDetailRequest.newBuilder()
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setProductCode(productCode).build();
            InventorybatchStructure.BatchDetailPageInfo batchDetail = inventoryBatchService.getBatchDetail(getBatchDetailRequest);
            for (InventorybatchStructure.InventoryBatch record : batchDetail.getListList()){
                InventoryBatch inventoryBatch = new InventoryBatch();
                inventoryBatch.setProjectId(record.getProjectId());
                inventoryBatch.setBatchNo(record.getBatchNo());
                inventoryBatch.setPurchaseType(record.getPurchaseType());
                inventoryBatch.setInventoryStatus(record.getInventoryStatus());
                inventoryBatch.setProductId(record.getProductId());
                inventoryBatch.setProductCode(record.getProductCode());
                inventoryBatch.setProductName(record.getProductName());
                inventoryBatch.setWarehouseId(record.getWarehouseId());
                inventoryBatch.setWarehouseName(record.getWarehouseName());
                inventoryBatch.setInventoryTotalAmount(record.getInventoryTotalAmount());
                inventoryBatch.setInventoryBatchAmount(record.getInventoryBatchAmount());
                inventoryBatch.setCreateTime(new Date(record.getCreateTime()));
                inventoryBatch.setPurchasePrice(record.getPurchasePrice());
                inventoryBatch.setCostPrice(record.getCostPrice());
                resultList.add(inventoryBatch);
            }
            PageHelper.startPage(pageNumber, pageSize);
            pageInfo = new PageInfo<InventoryBatch>(resultList);
            pageInfo.setTotal(batchDetail.getTotal());
            logger.info("#traceId={}# [OUT][getBatchDetail] get getBatchDetail success " , rpcHeader.getTraceId());
            return pageInfo;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return pageInfo;
        }

    }

    @RequestMapping("/getBatchDetailByWarehouse")
    @ResponseBody
    public PageInfo<InventoryBatch> getBatchDetailByWarehouse(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String warehouseId, String productCode, int pageNumber, int pageSize){
        logger.info("#traceId={}# [IN][getBatchDetailByWarehouse] params: pageNumber={}, pageSize={}, projectId={}, warehouseId={},productCode={}" , rpcHeader.getTraceId(),pageNumber,pageSize,projectId,warehouseId,productCode);
        PageInfo<InventoryBatch> pageInfo = new PageInfo<>();
        List<InventoryBatch> resultList = new ArrayList<>();
        try {
            InventoryBatchServiceGrpc.InventoryBatchServiceBlockingStub inventoryBatchService = WarehouseRpcStubStore.getRpcStub(InventoryBatchServiceGrpc.InventoryBatchServiceBlockingStub.class);
            InventorybatchStructure.GetBatchDetailByWarehouseRequest getBatchDetailByWarehouseRequest = InventorybatchStructure.GetBatchDetailByWarehouseRequest.newBuilder()
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setWarehouseId(warehouseId)
                    .setProductCode(productCode).build();
            InventorybatchStructure.BatchDetailPageInfo batchDetailByWarehouse = inventoryBatchService.getBatchDetailByWarehouse(getBatchDetailByWarehouseRequest);
            for (InventorybatchStructure.InventoryBatch record : batchDetailByWarehouse.getListList()){
                InventoryBatch inventoryBatch = new InventoryBatch();
                inventoryBatch.setProjectId(record.getProjectId());
                inventoryBatch.setBatchNo(record.getBatchNo());
                inventoryBatch.setPurchaseType(record.getPurchaseType());
                inventoryBatch.setInventoryStatus(record.getInventoryStatus());
                inventoryBatch.setProductId(record.getProductId());
                inventoryBatch.setProductName(record.getProductName());
                inventoryBatch.setWarehouseId(record.getWarehouseId());
                inventoryBatch.setWarehouseName(record.getWarehouseName());
                inventoryBatch.setInventoryTotalAmount(record.getInventoryTotalAmount());
                inventoryBatch.setInventoryBatchAmount(record.getInventoryBatchAmount());
                inventoryBatch.setCreateTime(new Date(record.getCreateTime()));
                inventoryBatch.setPurchasePrice(record.getPurchasePrice());
                inventoryBatch.setCostPrice(record.getCostPrice());
                resultList.add(inventoryBatch);
            }

            PageHelper.startPage(pageNumber, pageSize);
            pageInfo = new PageInfo<InventoryBatch>(resultList);
            pageInfo.setTotal(resultList.size());
            logger.info("#traceId={}# [OUT][getBatchDetail] get getBatchDetail success " , rpcHeader.getTraceId());
            return pageInfo;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return pageInfo;
        }

    }
}
