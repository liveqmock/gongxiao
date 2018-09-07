package com.yhglobal.gongxiao.inventory.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.bo.InventoryBatch;
import com.yhglobal.gongxiao.inventory.bo.ProductToBeDelivered;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(timeout = 5000)
public class InventoryBatchServiceImpl implements InventoryBatchService {

    private Logger logger = LoggerFactory.getLogger(InventoryBatchServiceImpl.class);

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Override
    public Map<String, Map<String, ProductInventory>> getDetailedInventory(String projectId, List<ProductToBeDelivered> productList) {
        Map<String, Map<String, ProductInventory>> resultMap = new HashMap<>();

        for (ProductToBeDelivered record : productList) {   //遍历需要智能分仓的商品
            List<ProductInventory> productInventoryList = productInventoryDao.selectRecordByAsk(projectId, record.getProductCode(), record.getQuantity());   //查询该商品的良品
            if (productInventoryList.size() > 0) {
                for (ProductInventory inventoryRecord : productInventoryList) {    //因为不同种商品的可能在同一仓库内,所以用"仓库id+商品编码",作为key
                    StringBuilder key = new StringBuilder().append(inventoryRecord.getWarehouseId()).append(":").append(inventoryRecord.getProductCode());
                    Map<String, ProductInventory> tempMap = new HashMap<>();
                    tempMap.put(record.getProductCode(), inventoryRecord);
                    resultMap.put(key.toString(), tempMap);
                }
            }
        }

        return resultMap;
    }



    @Override
    public PageInfo<InventoryBatch> getBatchDetail(RpcHeader rpcHeader, String projectId, String productCode, int pageNumber, int pageSize) {
        logger.info("#traceId={}# [IN][getBatchDetail]: projectId={}, productCode={}, pageNumber={}, pageSize={}", rpcHeader.getTraceId(), projectId, productCode, pageNumber, pageSize);
        PageHelper.startPage(pageNumber, pageSize);
        List<ProductInventory> productInventoryList = productInventoryDao.getBatchDetail(projectId, productCode);
        PageInfo<ProductInventory> productInventoryPageInfo = new PageInfo<>(productInventoryList);
        long total = productInventoryPageInfo.getTotal();
        int inventoryTotalAmount = 0;
        List<InventoryBatch> resultList = new ArrayList<>();
        //计算批次库存数量
        for (ProductInventory productInventory : productInventoryList) {
            inventoryTotalAmount += productInventory.getOnHandQuantity();
        }
        //遍历，得到该商品在同一个项目，同一仓库，不同批次号下的记录
        for (ProductInventory record : productInventoryList) {
            InventoryBatch inventoryBatch = new InventoryBatch();
            inventoryBatch.setProjectId(record.getProductId());
            inventoryBatch.setBatchNo(record.getBatchNo());
            inventoryBatch.setWarehouseId(record.getWarehouseId());
            inventoryBatch.setWarehouseName(record.getWarehouseName());
            inventoryBatch.setProductId(record.getProductId());
            inventoryBatch.setProductName(record.getProductName());
            inventoryBatch.setProductCode(record.getProductCode());
            inventoryBatch.setInventoryBatchAmount(record.getOnHandQuantity());
            inventoryBatch.setInventoryTotalAmount(inventoryTotalAmount);
            inventoryBatch.setPurchasePrice(record.getPurchasePrice());
            inventoryBatch.setCreateTime(record.getCreateTime());
            inventoryBatch.setCostPrice(record.getCostPrice());
            inventoryBatch.setPurchaseType(record.getPurchaseType());
            inventoryBatch.setInventoryStatus(record.getInventoryStatus());
            resultList.add(inventoryBatch);
        }
        PageInfo<InventoryBatch> inventoryBatchPageInfo = new PageInfo<InventoryBatch>(resultList);
        inventoryBatchPageInfo.setPageNum(pageNumber);
        inventoryBatchPageInfo.setPageSize(pageSize);
        inventoryBatchPageInfo.setTotal(total);
        logger.info("#traceId={}# [OUT] get getBatchInventory success: result.size():{}", rpcHeader.getTraceId(), resultList.size());
        return inventoryBatchPageInfo;
    }

    @Override
    public PageInfo<InventoryBatch> getBatchDetailByWarehouse(RpcHeader rpcHeader, String projectId, String productCode, String warehouseId, int pageNumber, int pageSize) {
        logger.info("#traceId={}# [IN][getBatchDetailByWarehouse]: projectId={}, productCode={}, warehouseId={}, pageNumber={}, pageSize={}", rpcHeader.getTraceId(), projectId, productCode, pageNumber, pageSize);
        PageHelper.startPage(pageNumber, pageSize);
        List<ProductInventory> productInventoryList = productInventoryDao.getBatchDetailByWarehouse(projectId, productCode, warehouseId);
        int inventoryTotalAmount = 0;
        List<InventoryBatch> resultList = new ArrayList<>();
        //计算批次库存数量
        for (ProductInventory productInventory : productInventoryList) {
            inventoryTotalAmount += productInventory.getOnHandQuantity();
        }
        //遍历，得到该商品在同一个项目，同一仓库，不同批次号下的记录
        for (ProductInventory record : productInventoryList) {
            InventoryBatch inventoryBatch = new InventoryBatch();
            inventoryBatch.setProjectId(record.getProductId());
            inventoryBatch.setBatchNo(record.getBatchNo());
            inventoryBatch.setWarehouseId(record.getWarehouseId());
            inventoryBatch.setWarehouseName(record.getWarehouseName());
            inventoryBatch.setProductId(record.getProductId());
            inventoryBatch.setProductName(record.getProductName());
            inventoryBatch.setProductCode(record.getProductCode());
            inventoryBatch.setInventoryBatchAmount(record.getOnHandQuantity());
            inventoryBatch.setInventoryTotalAmount(inventoryTotalAmount);
            inventoryBatch.setPurchasePrice(record.getPurchasePrice());
            inventoryBatch.setCreateTime(record.getCreateTime());
            inventoryBatch.setCostPrice(record.getCostPrice());
            inventoryBatch.setPurchaseType(record.getPurchaseType());
            inventoryBatch.setInventoryStatus(record.getInventoryStatus());
            resultList.add(inventoryBatch);
        }
        PageInfo<InventoryBatch> inventoryBatchPageInfo = new PageInfo<InventoryBatch>(resultList);
        logger.info("#traceId={}# [OUT] get getBatchDetailByWarehouse success: result.size():{}", rpcHeader.getTraceId(), resultList.size());
        return inventoryBatchPageInfo;
    }


}
