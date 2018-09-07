package com.yhglobal.gongxiao.inventory.inventorybatch;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventoryBatchServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.model.bo.InventoryBatch;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class InventoryBatchServiceImpl extends InventoryBatchServiceGrpc.InventoryBatchServiceImplBase {
    private static Logger logger = LoggerFactory.getLogger(InventoryBatchServiceImpl.class);

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Override
    public void getBatchDetail(InventorybatchStructure.GetBatchDetailRequest request, StreamObserver<InventorybatchStructure.BatchDetailPageInfo> responseObserver) {
        InventorybatchStructure.BatchDetailPageInfo response;
        InventorybatchStructure.BatchDetailPageInfo.Builder respBuilder = InventorybatchStructure.BatchDetailPageInfo.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String projectId = request.getProjectId();
        String productCode=request.getProductCode();
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();
        try {
            logger.info("#traceId={}# [IN][getBatchDetail]: projectId={}, productCode={}, pageNumber={}, pageSize={}", rpcHeader.getTraceId(), projectId, productCode, pageNumber, pageSize);
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //启动分页
            PageHelper.startPage(pageNumber, pageSize);
            //查询列表
            List<ProductInventory> productInventoryList = productInventoryDao.getBatchDetail(projectId, productCode,projectPrefix);
            PageInfo<ProductInventory> productInventoryPageInfo = new PageInfo<>(productInventoryList);
            long total = productInventoryPageInfo.getTotal();
            int inventoryTotalAmount = 0;
            //计算批次库存数量
            for (ProductInventory productInventory : productInventoryList) {
                inventoryTotalAmount += productInventory.getOnHandQuantity();
            }
            //遍历，得到该商品在同一个项目，同一仓库，不同批次号下的记录
            for (ProductInventory record : productInventoryList) {

                InventorybatchStructure.InventoryBatch.Builder innerModel = InventorybatchStructure.InventoryBatch.newBuilder()
                        .setProjectId(record.getProductId())
                        .setBatchNo(record.getBatchNo())
                        .setWarehouseId(record.getWarehouseId())
                        .setWarehouseName(record.getWarehouseName())
                        .setProductId(record.getProductId())
                        .setProductName(record.getProductName())
                        .setProductCode(record.getProductCode())
                        .setInventoryBatchAmount(record.getOnHandQuantity())
                        .setInventoryTotalAmount(inventoryTotalAmount)
                        .setPurchasePrice(record.getPurchasePrice())
                        .setCreateTime(record.getCreateTime().getTime())
                        .setCostPrice(record.getCostPrice())
                        .setPurchaseType(record.getPurchaseType())
                        .setInventoryStatus(record.getInventoryStatus());
                respBuilder.addList(innerModel);
            }

            respBuilder.setPageNum(pageNumber);
            respBuilder.setPageSize(pageSize);
            respBuilder.setTotal(total);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getBatchInventory success", rpcHeader.getTraceId());

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

    @Override
    public void getBatchDetailByWarehouse(InventorybatchStructure.GetBatchDetailByWarehouseRequest request, StreamObserver<InventorybatchStructure.BatchDetailPageInfo> responseObserver) {
        InventorybatchStructure.BatchDetailPageInfo response;
        InventorybatchStructure.BatchDetailPageInfo.Builder respBuilder = InventorybatchStructure.BatchDetailPageInfo.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String projectId = request.getProjectId();
        String productCode = request.getProductCode();
        String warehouseId = request.getWarehouseId();
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();
        try {
            logger.info("#traceId={}# [IN][getBatchDetailByWarehouse]: projectId={}, productCode={}, warehouseId={}, pageNumber={}, pageSize={}", rpcHeader.getTraceId(), projectId, productCode, pageNumber, pageSize);
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //启动分页
            PageHelper.startPage(pageNumber, pageSize);
            //查询列表
            List<ProductInventory> productInventoryList = productInventoryDao.getBatchDetailByWarehouse(projectId, productCode, warehouseId, projectPrefix);
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
                InventorybatchStructure.InventoryBatch.Builder innerModel = InventorybatchStructure.InventoryBatch.newBuilder()
                        .setProjectId(record.getProductId())
                        .setBatchNo(record.getBatchNo())
                        .setWarehouseId(record.getWarehouseId())
                        .setWarehouseName(record.getWarehouseName())
                        .setProductId(record.getProductId())
                        .setProductName(record.getProductName())
                        .setProductCode(record.getProductCode())
                        .setInventoryBatchAmount(record.getOnHandQuantity())
                        .setInventoryTotalAmount(inventoryTotalAmount)
                        .setPurchasePrice(record.getPurchasePrice())
                        .setCreateTime(record.getCreateTime().getTime())
                        .setCostPrice(record.getCostPrice())
                        .setPurchaseType(record.getPurchaseType())
                        .setInventoryStatus(record.getInventoryStatus());
                respBuilder.addList(innerModel);
            }

            respBuilder.setPageNum(pageNumber);
            respBuilder.setPageSize(pageSize);
            respBuilder.setTotal(total);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getBatchDetailByWarehouse success: result.size():{}", rpcHeader.getTraceId(), resultList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

//    @Scheduled(cron = "0 */1 * * * ?")   //每一个小时执行一次
//    public void createAccountInfo() {
//        String inventoryJson = JSON.toJSONString(productInventoryDao.selectAllByProductNameAndProductCodeAndwarehouse("146798161","","","","shaver"));
//
//        System.out.println("=============================="+inventoryJson);
//    }
}
