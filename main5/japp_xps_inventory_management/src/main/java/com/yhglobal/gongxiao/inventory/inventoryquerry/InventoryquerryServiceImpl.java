package com.yhglobal.gongxiao.inventory.inventoryquerry;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.inventory.account.microservice.ProductInventoryStructure;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.inventory.model.bo.ProductInventoryShowModel;
import com.yhglobal.gongxiao.inventory.model.bo.WarehouseInventoryShowModel;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.PurchaseTypeStatus;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class InventoryquerryServiceImpl extends InventoryquerryServiceGrpc.InventoryquerryServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(InventoryquerryServiceImpl.class);

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Override
    public void selectInventoryInfo(InventoryquerryStructure.GetInventoryInfoRequest request, StreamObserver<InventoryquerryStructure.GetInventoryInfoResponse> responseObserver) {
        InventoryquerryStructure.GetInventoryInfoResponse response;
        InventoryquerryStructure.GetInventoryInfoResponse.Builder respBuilder = InventoryquerryStructure.GetInventoryInfoResponse.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        int purchaseType = request.getPurchaseType();
        int inventoryStatus = request.getInventoryStatus();
        long projectId = request.getProjectId();
        String batchNo = request.getBatchNo();
        String productCode = request.getProductCode();
        String warehouseId = request.getWarehouseId();

        try {
            logger.info("#traceId={}# [IN][selectInventoryInfo]: purchaseType={},inventoryStatus={},projectId={},batchNo={},productCode={},warehouseId={}", rpcHeader.getTraceId(), purchaseType, inventoryStatus, projectId, batchNo, productCode, warehouseId);
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            ProductInventory record = productInventoryDao.selectRecordByProductNameAndProductCodeAndwarehouse(purchaseType,inventoryStatus,projectId,batchNo,productCode,warehouseId,projectPrefix);
            ProductInventoryStructure.ProductInventory respProductInventory = ProductInventoryStructure.ProductInventory.newBuilder()
                    .setId(String.valueOf(record.getId()))
                    .setPurchaseType(record.getPurchaseType())
                    .setInventoryStatus(record.getInventoryStatus())
                    .setProjectId(record.getProjectId())
                    .setBatchNo(record.getBatchNo())
                    .setProductId(record.getProductId())
                    .setProductCode(record.getProductCode())
                    .setProductModel(record.getProductModel())
                    .setProductName(record.getProductName())
                    .setPurchasePrice(record.getPurchasePrice())
                    .setMaterial(record.getMaterial())
                    .setCostPrice(record.getCostPrice())
                    .setWarehouseId(record.getWarehouseId())
                    .setWarehouseName(record.getWarehouseName())
                    .setOnHandQuantity(record.getOnHandQuantity())
                    .setOnSalesOrderQuantity(record.getOnSalesOrderQuantity())
                    .setOnSalesOrderInfo(GrpcCommonUtil.getProtoParam(record.getOnSalesOrderInfo()))
                    .setShouldRebate(record.getShouldRebate())
                    .setActualRebate(record.getActualRebate())
                    .setSalesTotalPrice(record.getSalesTotalPrice())
                    .setDataVersion(record.getDataVersion())
                    .setCreateTime(record.getCreateTime().getTime()).build();

            respBuilder.setProductInventory(respProductInventory);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] selectInventoryInfo success", rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectAllInventoryInfo(InventoryquerryStructure.GetAllInventoryInfoRequest request, StreamObserver<InventoryquerryStructure.GetAllInventoryInfoResponse> responseObserver) {
        InventoryquerryStructure.GetAllInventoryInfoResponse response;
        InventoryquerryStructure.GetAllInventoryInfoResponse.Builder respBuilder = InventoryquerryStructure.GetAllInventoryInfoResponse.newBuilder();
        long projectId = request.getProjectId();
        try {
            logger.info("[IN][selectInventoryInfo]: projectId={}", projectId);
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            List<ProductInventory> productInventoryList = productInventoryDao.selectAllRecord(projectPrefix);

            for (ProductInventory record : productInventoryList){
                ProductInventoryStructure.ProductInventory respProductInventory = ProductInventoryStructure.ProductInventory.newBuilder()
                        .setId(String.valueOf(record.getId()))
                        .setPurchaseType(record.getPurchaseType())
                        .setInventoryStatus(record.getInventoryStatus())
                        .setProjectId(record.getProjectId())
                        .setBatchNo(record.getBatchNo())
                        .setProductId(record.getProductId())
                        .setProductCode(record.getProductCode())
                        .setProductModel(record.getProductModel())
                        .setProductName(record.getProductName())
                        .setPurchasePrice(record.getPurchasePrice())
                        .setMaterial(record.getMaterial())
                        .setCostPrice(record.getCostPrice())
                        .setWarehouseId(record.getWarehouseId())
                        .setWarehouseName(record.getWarehouseName())
                        .setOnHandQuantity(record.getOnHandQuantity())
                        .setOnSalesOrderQuantity(record.getOnSalesOrderQuantity())
                        .setOnSalesOrderInfo(GrpcCommonUtil.getProtoParam(record.getOnSalesOrderInfo()))
                        .setShouldRebate(record.getShouldRebate())
                        .setActualRebate(record.getActualRebate())
                        .setSalesTotalPrice(record.getSalesTotalPrice())
                        .setDataVersion(record.getDataVersion())
                        .setCreateTime(record.getCreateTime().getTime()).build();
                respBuilder.addList(respProductInventory);
            }

            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("[OUT] selectInventoryInfo success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
        public void getProductInventoryByName (InventoryquerryStructure.GetProductInventoryByNameRequest
        request, StreamObserver < InventoryquerryStructure.GetProductInventoryByNameResponse > responseObserver){
            InventoryquerryStructure.GetProductInventoryByNameResponse response;
            InventoryquerryStructure.GetProductInventoryByNameResponse.Builder respBuilder = InventoryquerryStructure.GetProductInventoryByNameResponse.newBuilder();
            GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
            long projectId = request.getProjectId();
            String productCode = request.getProductCode();
            String productName = request.getProductName();
            int pageNumber = request.getPageNumber();
            int pageSize = request.getPageSize();

            try {
                logger.info("#traceId={}# [IN][getProductInventoryByName]: pageNumber={},pageSize={},projectId={},productCode={},productName={}", rpcHeader.getTraceId(), pageNumber, pageSize, projectId, productCode, productName);
//            List<ProductInventoryShowModel> resultList = new ArrayList<>();          //定义结果集list
                String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
                Map<String, ProductInventoryShowModel> productInfoMap = new HashMap<>();          //按照同一种商品型号汇总
                //启动分页
                PageHelper.startPage(pageNumber, pageSize);
                //查询列表
                List<ProductInventory> productInventoryList = productInventoryDao.selectAllByProjectIdAndProductModelAndProductName(projectId, productCode, productName,projectPrefix);
                PageInfo<ProductInventory> productInventoryPageInfo = new PageInfo<>(productInventoryList);
                long total = productInventoryPageInfo.getTotal();

                for (ProductInventory record : productInventoryList) {
                    StringBuilder key = new StringBuilder();
                    key.append(record.getProductModel());
                    key.append(record.getProductName());

                    if (productInfoMap.containsKey(key.toString())) {
                        ProductInventoryShowModel showModel = productInfoMap.get(key.toString());
                        showModel.setProjectId(projectId);
                        showModel.setOccupancyQuantity(showModel.getOccupancyQuantity() + record.getOnSalesOrderQuantity());
                        if (record.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {             //普通好机
                            showModel.setAvailableQuantity(showModel.getAvailableQuantity() + record.getOnHandQuantity());
                        } else if (record.getInventoryStatus() == WmsInventoryType.DEFECTIVE.getInventoryType()
                                || record.getInventoryStatus() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()
                                || record.getInventoryStatus() == WmsInventoryType.BOX_DAMAGE.getInventoryType()) {               //残次 = 残次+机损+箱损
                            showModel.setDefectiveQuantity(showModel.getDefectiveQuantity() + record.getOnHandQuantity());
                        } else if (record.getInventoryStatus() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()) {  //在途库存
                            showModel.setOnWayQuantity(showModel.getOnWayQuantity() + record.getOnHandQuantity());
                        } else {                                                                                                   //冻结库存
                            showModel.setFrozenQuantity(showModel.getFrozenQuantity() + record.getOnHandQuantity());
                        }
                        showModel.setPhysicalInventory(showModel.getPhysicalInventory() + record.getOnHandQuantity());

                    } else {
                        ProductInventoryShowModel newShowModel = new ProductInventoryShowModel();
                        newShowModel.setProductName(record.getProductName());
                        newShowModel.setProductCode(record.getProductCode());
                        newShowModel.setOccupancyQuantity(record.getOnSalesOrderQuantity()); //占用数量 = 预售单冻结数量(含已下单未出库数量)
                        if (record.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {
                            newShowModel.setAvailableQuantity(record.getOnHandQuantity()); //可用数量(实物良品数量)=在库总数量 - 残品数量
                        } else if (record.getInventoryStatus() == WmsInventoryType.DEFECTIVE.getInventoryType()
                                || record.getInventoryStatus() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()
                                || record.getInventoryStatus() == WmsInventoryType.BOX_DAMAGE.getInventoryType()) {
                            newShowModel.setDefectiveQuantity(record.getOnHandQuantity()); //残品数量
                        } else if (record.getInventoryStatus() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()) {
                            newShowModel.setOnWayQuantity(record.getOnHandQuantity());         //在途数量=采购在途数量+调拨入库在途+调拨出库在途
                        } else {
                            newShowModel.setFrozenQuantity(record.getOnHandQuantity());     //冻结数量
                        }
                        newShowModel.setPhysicalInventory(newShowModel.getAvailableQuantity() + newShowModel.getOccupancyQuantity() + newShowModel.getDefectiveQuantity() + newShowModel.getFrozenQuantity()); //实物在库

                        productInfoMap.put(key.toString(), newShowModel);
                    }
                }
                for (Map.Entry<String, ProductInventoryShowModel> entry : productInfoMap.entrySet()) {
                    InventoryquerryStructure.ProductInventoryShowModel.Builder innerModel = InventoryquerryStructure.ProductInventoryShowModel.newBuilder()
                            .setProjectId((int) entry.getValue().getProjectId())
                            .setProductName(entry.getValue().getProductName())
                            .setProductCode(entry.getValue().getProductCode())
                            .setAvailableQuantity(entry.getValue().getAvailableQuantity())
                            .setOccupancyQuantity(entry.getValue().getOccupancyQuantity())
                            .setDefectiveQuantity(entry.getValue().getDefectiveQuantity())
                            .setPhysicalInventory(entry.getValue().getPhysicalInventory())
                            .setOnWayQuantity(entry.getValue().getOnWayQuantity())
                            .setFrozenQuantity(entry.getValue().getFrozenQuantity());
                    respBuilder.addList(innerModel);
                }

                respBuilder.setPageNum(pageNumber);
                respBuilder.setPageSize(pageSize);
                respBuilder.setTotal(total);
                response = respBuilder.build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                logger.info("#traceId={}# [OUT] selectProductInventoryByName success: result.size():{}", rpcHeader.getTraceId(), productInfoMap.size());
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        }

        @Override
        public void getWarehouseInventory (InventoryquerryStructure.GetWarehouseInventoryRequest
        request, StreamObserver < InventoryquerryStructure.GetWarehouseInventoryResponse > responseObserver){
            InventoryquerryStructure.GetWarehouseInventoryResponse response;
            InventoryquerryStructure.GetWarehouseInventoryResponse.Builder respBuilder = InventoryquerryStructure.GetWarehouseInventoryResponse.newBuilder();
            GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
            String projectId = request.getProjectId();
            String productCode = request.getProductCode();
            String productName = request.getProductName();
            String warehouseId = request.getWarehouseId();
            String batchNo = request.getBatchNo();
            int pageNumber = request.getPageNumber();
            int pageSize = request.getPageSize();
            try {
                logger.info("#traceId={}# [IN][getWarehouseInventory]: pageNumber={},pageSize={},projectId={},productCode={},productName={},warehouseId={},batchNo={}", rpcHeader.getTraceId(), pageNumber, pageSize, projectId, productCode, productName, warehouseId,batchNo);
                String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
                Map<String, WarehouseInventoryShowModel> warehouseInventoryShowModelMap = new HashMap<>();
//            List<WarehouseInventoryShowModel> resultList = new ArrayList<>();
                List<ProductInventory> productInventoryList = productInventoryDao.selectAllByProductNameAndProductCodeAndwarehouse(projectId, productName, productCode, warehouseId, batchNo, projectPrefix);
                for (ProductInventory record : productInventoryList) {
                    StringBuilder key = new StringBuilder();
                    key.append(record.getProductModel());
                    key.append(record.getProductName());
                    key.append(record.getWarehouseName());
                    key.append(record.getBatchNo());
                    key.append(record.getPurchaseType());
                    key.append(record.getInventoryStatus());

                    if (warehouseInventoryShowModelMap.containsKey(key.toString())) {
                        WarehouseInventoryShowModel warehouseInventoryShowModel = warehouseInventoryShowModelMap.get(key.toString());
                        warehouseInventoryShowModel.setProjectId(record.getProjectId());
                        warehouseInventoryShowModel.setAvailableQuantity(warehouseInventoryShowModel.getAvailableQuantity() + record.getOnHandQuantity());
                        warehouseInventoryShowModel.setOccupancyQuantity(warehouseInventoryShowModel.getOccupancyQuantity() + record.getOnSalesOrderQuantity());
                    } else {
                        WarehouseInventoryShowModel newShowModel = new WarehouseInventoryShowModel();
                        newShowModel.setProjectId(record.getProjectId());
                        newShowModel.setBatchNo(record.getBatchNo());
                        newShowModel.setProductName(record.getProductName());
                        newShowModel.setProductCode(record.getProductModel());
                        newShowModel.setWarehouseName(record.getWarehouseName());
                        newShowModel.setAvailableQuantity(record.getOnHandQuantity());
                        newShowModel.setOccupancyQuantity(record.getOnSalesOrderQuantity());
                        newShowModel.setBatchNo(record.getBatchNo());
                        if (record.getPurchaseType() == PurchaseTypeStatus.GENERAL_PURCHASE.getStatus()){
                            newShowModel.setPurchaseType("普通采购");
                        }else if (record.getPurchaseType() == PurchaseTypeStatus.PRODUCT_ADDITIONAL.getStatus()){
                            newShowModel.setPurchaseType("货补");
                        }else if (record.getPurchaseType() == PurchaseTypeStatus.GIFT_PURCHASE.getStatus()){
                            newShowModel.setPurchaseType("赠品");
                        }else {
                            newShowModel.setPurchaseType("不详");
                        }
                        if (record.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()) {   //普通好机
                            newShowModel.setStatus("普通好机");
                        } else if (record.getInventoryStatus() == WmsInventoryType.DEFECTIVE.getInventoryType()) {       //残次
                            newShowModel.setStatus("残次");
                        } else if (record.getInventoryStatus() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()) {   //机损
                            newShowModel.setStatus("机损");
                        } else if (record.getInventoryStatus() == WmsInventoryType.BOX_DAMAGE.getInventoryType()) {      //箱损
                            newShowModel.setStatus("箱损");
                        } else if (record.getInventoryStatus() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()) {  //在途库存
                            newShowModel.setStatus("在途库存");
                        } else {     //冻结库存
                            newShowModel.setStatus("冻结库存");
                        }

                        warehouseInventoryShowModelMap.put(key.toString(), newShowModel);
                    }
                }

                for (Map.Entry<String, WarehouseInventoryShowModel> entry : warehouseInventoryShowModelMap.entrySet()) {
                    InventoryquerryStructure.WarehouseInventoryShowModel.Builder innerModel = InventoryquerryStructure.WarehouseInventoryShowModel.newBuilder()
                            .setProjectId((int) entry.getValue().getProjectId())
                            .setProductName(entry.getValue().getProductName())
                            .setProductCode(entry.getValue().getProductCode())
                            .setWarehouseName(entry.getValue().getWarehouseName())
                            .setBatchNo(entry.getValue().getBatchNo())
                            .setPurchaseType(entry.getValue().getPurchaseType())
                            .setAvailableQuantity(entry.getValue().getAvailableQuantity())
                            .setStatus(entry.getValue().getStatus())
                            .setOccupancyQuantity(entry.getValue().getOccupancyQuantity());

                    respBuilder.addList(innerModel);
                }
                response = respBuilder.build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                logger.info("#traceId={}# [OUT] get getWarehouseInventory success", rpcHeader.getTraceId());
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        }

        @Override
        public void selectQuantityByProjectAndProductCode (InventoryquerryStructure.GetQuantityRequest
        request, StreamObserver < InventoryquerryStructure.GetQuantityResponse > responseObserver){
            InventoryquerryStructure.GetQuantityResponse response;
            InventoryquerryStructure.GetQuantityResponse.Builder respBuilder = InventoryquerryStructure.GetQuantityResponse.newBuilder();
            GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
            String projectId = request.getProjectId();
            String productCode = request.getProductCode();
            try {
                logger.info("#traceId={}# [IN][selectQuantityByProjectIdAndProductCode] params: projectId={},productCode={}", rpcHeader.getTraceId(), projectId, productCode);
                String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
                int i = productInventoryDao.selectQuantityByProjectIdAndProductCode(projectId, productCode,projectPrefix);
                response = respBuilder.setQuantity(i).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                logger.info("#traceId={}# [OUT] get selectQuantityByProjectIdAndProductCode success", rpcHeader.getTraceId());
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        }

        @Override
        public void updateInventoryInfo (InventoryquerryStructure.UpdateRequest
        request, StreamObserver < InventoryquerryStructure.UpdateResponse > responseObserver){
            InventoryquerryStructure.UpdateResponse response;
            InventoryquerryStructure.UpdateResponse.Builder respBuilder = InventoryquerryStructure.UpdateResponse.newBuilder();
            GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
            ProductInventoryStructure.ProductInventory productInventory = request.getProductInventory();
            try {
                logger.info("#traceId={}# [IN][updateInventoryInfo] params: productInventory={}", rpcHeader.getTraceId());
                String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(productInventory.getProjectId());
                int i = productInventoryDao.updateInventoryOccupy(productInventory.getOnSalesOrderQuantity(), productInventory.getPurchaseType(), productInventory.getInventoryStatus(), productInventory.getProjectId(), productInventory.getBatchNo(), productInventory.getProductCode(), productInventory.getWarehouseId(),projectPrefix);
                logger.info("#traceId={}# [OUT] get updateInventoryInfo success, result.size={}", rpcHeader.getTraceId(), i);
                response = respBuilder.setCurrency(i).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        }


    }
