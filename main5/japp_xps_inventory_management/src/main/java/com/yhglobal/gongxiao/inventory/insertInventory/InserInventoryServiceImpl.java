package com.yhglobal.gongxiao.inventory.insertInventory;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.account.microservice.ProductInventoryStructure;
import com.yhglobal.gongxiao.inventory.dao.CheckEasInventoryDao;
import com.yhglobal.gongxiao.inventory.dao.CheckEasInventoryItemDao;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryFlowDao;
import com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryServiceGrpc;
import com.yhglobal.gongxiao.inventory.insertinventory.microservice.InsertInventoryStructure;
import com.yhglobal.gongxiao.inventory.inventorybatch.microservice.InventorybatchStructure;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure;
import com.yhglobal.gongxiao.inventory.model.InventoryExcelModel;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.inventory.model.bo.EasAndXpsInventoryModel;
import com.yhglobal.gongxiao.inventory.model.bo.EasInventoryCheckResult;
import com.yhglobal.gongxiao.inventory.util.InventoryPaser;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsOrderType;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InserInventoryServiceImpl extends InsertInventoryServiceGrpc.InsertInventoryServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(InserInventoryServiceImpl.class);

    private final static String xlsxFile = "D:\\BL\\即时库存查询.xlsx";

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Autowired
    ProductInventoryFlowDao productInventoryFlowDao;

    @Autowired
    CheckEasInventoryItemDao checkEasInventoryItemDao;

    @Autowired
    CheckEasInventoryDao checkEasInventoryDao;

    /**
     * 导入eas即时库存
     * @param request
     * @param responseObserver
     */
    @Override
    public void insertInventory(InsertInventoryStructure.GetInsertInventoryRequest request, StreamObserver<InsertInventoryStructure.GetInsertInventoryResponse> responseObserver) {
        InsertInventoryStructure.GetInsertInventoryResponse response;
        InsertInventoryStructure.GetInsertInventoryResponse.Builder respBuilder = InsertInventoryStructure.GetInsertInventoryResponse.newBuilder();
        long projectId = request.getProjectId();
        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("").setTraceId("123456").setUsername("").build();
        Date dateTime = new Date();
        /**
         * 1、解析即时库存EXCEL
         * 2、记录批次库存记录
         * 3、记录出入库流水
         */
        try {
            logger.info("#traceId={}# [IN][insertInventory]: projectId={}", rpcHeader.getTraceId(), projectId);
            logger.info("========开始解析即时库存文件,目录为：D:\\BL\\即时库存查询.xlsx===============");
            List<InventoryExcelModel> inventoryExcelModelList = InventoryPaser.parse(xlsxFile);
            logger.info("========解析即时库存文件完毕,得到{}条库存数据===============", inventoryExcelModelList.size());
            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.SelectAllWarehouseReq selectAllWarehouseReq = WarehouseStructure.SelectAllWarehouseReq.newBuilder().setRpcHeader(rpcHeader).build();
            WarehouseStructure.SelectAllWarehouseResp rpcResponse = warehouseService.selectAllWarehouse(selectAllWarehouseReq);
            List<WarehouseStructure.Warehouse> warehouseListList = rpcResponse.getWarehouseListList();

            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(String.valueOf(projectId)).build();
            ProjectStructure.GetByProjectIdResp projectIdResp = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = projectIdResp.getProject();

//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //调用基础模块的商品的grpc查询项目信息
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            long projectID = project.getProjectId();
            int i = 1;
            for (InventoryExcelModel record : inventoryExcelModelList) {        //遍历解析出来的即时库存，1:生成库存记录 2:生成入库流水记录
                if (record == null) {
                    continue;
                }
                ProductInventory productInventory = new ProductInventory();
                ProductInventoryFlow flowRecord = new ProductInventoryFlow();

                productInventory.setPurchaseType(record.getPosition());
                productInventory.setInventoryStatus(record.getInventoryQuality());
                productInventory.setProjectId(projectID);
                productInventory.setBatchNo(record.getBatchNo());

                ProductStructure.GetProductDetailByModelReq getProductDetailByModelReq = ProductStructure.GetProductDetailByModelReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectID).setProductModel(record.getProductCode()).build();
                ProductStructure.GetProductDetailByModelResp productResponse = productService.getProductDetailByModel(getProductDetailByModelReq);
                ProductStructure.ProductBasicDetail productBasicDetail = productResponse.getProductBasicDetail();

                productInventory.setProductId(String.valueOf(productBasicDetail.getCategoryId()));
                productInventory.setProductCode(productBasicDetail.getProductModel());
                productInventory.setProductModel(productBasicDetail.getProductModel());
                productInventory.setProductName(productBasicDetail.getProductName());
                productInventory.setPurchasePrice((long) (record.getPurchasePrice() * 1000000));
                productInventory.setCostPrice((long) (record.getCostPrice() * 1000000));
                productInventory.setPurchaseGuidPrice((long) (record.getGuidePurchasePrice() * 1000000));
                productInventory.setMaterial(record.getMaterial());
                productInventory.setCostPrice((long) (record.getCostPrice()));


                for (WarehouseStructure.Warehouse warehouse : warehouseListList) {
                    if (warehouse.getEasWarehouseName().equals(record.getWarehouseName())) {
                        productInventory.setWarehouseId(String.valueOf(warehouse.getWarehouseId()));
                        productInventory.setWarehouseCode(warehouse.getWarehouseCode());
                        productInventory.setWarehouseName(warehouse.getWarehouseName());
                        flowRecord.setWarehouseId(String.valueOf(warehouse.getWarehouseId()));
                        flowRecord.setWarehouseCode(warehouse.getWarehouseCode());
                        flowRecord.setWarehouseName(warehouse.getWarehouseName());
                    }
                }
                productInventory.setOnHandQuantity(record.getQuantity());
                productInventory.setInboundQuantity(record.getQuantity());
                productInventory.setOnSalesOrderQuantity(0);
                productInventory.setOnSalesOrderInfo("");
                productInventory.setShouldRebate(record.getShouldRebate());
                productInventory.setActualRebate(record.getActualRebate());
                productInventory.setSalesTotalPrice(0);
                productInventory.setDataVersion(0);
                productInventory.setInboundOrderNo(String.valueOf(dateTime.getTime()));
                productInventory.setPurchaseOrderNo("PUR_" + String.valueOf(dateTime.getTime()));

                try {
                    productInventory.setCreateTime(format.parse(record.getEntryTime()));
                } catch (ParseException e) {
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
                }
                productInventoryDao.insert(productInventory, projectPrefix);   //插入库存表

                flowRecord.setOrderNo(String.valueOf(dateTime.getTime()));
                flowRecord.setBatchNo(record.getBatchNo());
                flowRecord.setInventoryFlowType(record.getInventoryQuality());
                flowRecord.setOrderType(WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode());
                flowRecord.setProjectId(projectID);
                flowRecord.setProductId(String.valueOf(productBasicDetail.getCategoryId()));
                flowRecord.setProductCode(productBasicDetail.getProductModel());
                flowRecord.setProductModel(productBasicDetail.getProductModel());
                flowRecord.setProductName(productBasicDetail.getProductName());

                flowRecord.setAmountBeforeTransaction(0);
                flowRecord.setTransactionAmount(record.getQuantity());
                flowRecord.setAmountAfterTransaction(record.getQuantity());
                flowRecord.setTransactionTime(dateTime);
                flowRecord.setRelatedOrderId(String.valueOf(dateTime.getTime()));
                flowRecord.setExtraInfo("导入即时库存");
                flowRecord.setStatementCheckingFlag(String.valueOf(1));
                flowRecord.setStatementCheckingTime(dateTime);
                flowRecord.setCreateTime(dateTime);
                productInventoryFlowDao.insert(flowRecord, projectPrefix);  //插出入库流水表
            }

            respBuilder.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getBatchInventory success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 核对EAS即时库存
     * @param request
     * @param responseObserver
     */
    @Override
    public void checkEasInventory(InsertInventoryStructure.GetCheckEasInventoryRequest request, StreamObserver<InsertInventoryStructure.GetCheckEasInventoryResponse> responseObserver) {
        InsertInventoryStructure.GetCheckEasInventoryResponse response;
        InsertInventoryStructure.GetCheckEasInventoryResponse.Builder respBuilder = InsertInventoryStructure.GetCheckEasInventoryResponse.newBuilder();
        long projectId = request.getProjectId();
        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("").setTraceId("123456").setUsername("").build();
        Date dateTime = new Date();
        /**
         * 1、解析EAS即时库存的EXCEL文件
         * 2、遍历EAS库存记录，与分销系统的每个批次的库存进行对比
         * 3、生成核对库存记录，生成核对库存明细
         */
        try {
            logger.info("#traceId={}# [IN][checkEasInventory]: projectId={}", rpcHeader.getTraceId(), projectId);
            logger.info("========开始解析即时库存文件,目录为：D:\\BL\\即时库存查询.xlsx===============");
            List<InventoryExcelModel> inventoryExcelModelList = InventoryPaser.parse(xlsxFile);
            logger.info("========解析即时库存文件完毕,得到{}条库存数据===============", inventoryExcelModelList.size());
            //调用基础模块的仓库grpc服务查仓库编码
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            WarehouseStructure.SelectAllWarehouseReq selectAllWarehouseReq = WarehouseStructure.SelectAllWarehouseReq.newBuilder().setRpcHeader(rpcHeader).build();
            WarehouseStructure.SelectAllWarehouseResp rpcResponse = warehouseService.selectAllWarehouse(selectAllWarehouseReq);
            List<WarehouseStructure.Warehouse> warehouseListList = rpcResponse.getWarehouseListList();

            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(String.valueOf(projectId)).build();
            ProjectStructure.GetByProjectIdResp projectIdResp = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = projectIdResp.getProject();

            //调用基础模块的商品的grpc查询项目信息
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);

            boolean exception = false;    //EAS和分销系统的库存默认为没有差异
            long projectID = project.getProjectId();
            int xpsInventoryQuantity = 0;
            int easInventoryQuantity = 0;
            EasInventoryCheckResult easInventoryCheckResult = new EasInventoryCheckResult();
            List<EasAndXpsInventoryModel> easAndXpsInventoryModelList = new ArrayList<>();
            for (InventoryExcelModel record : inventoryExcelModelList) {        //遍历解析出来的即时库存，1:生成库存记录 2:生成入库流水记录
                if (record == null) {
                    continue;
                }
                easInventoryQuantity += record.getQuantity();
                EasAndXpsInventoryModel easAndXpsInventoryModel = new EasAndXpsInventoryModel();
                easAndXpsInventoryModel.setPurchaseType(record.getPosition());
                easAndXpsInventoryModel.setInventoryType(record.getInventoryQuality());
                easAndXpsInventoryModel.setProjectId(String.valueOf(projectID));
                easAndXpsInventoryModel.setBatchNo(record.getBatchNo());

                //查询商品
                ProductStructure.GetProductDetailByModelReq getProductDetailByModelReq = ProductStructure.GetProductDetailByModelReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectID).setProductModel(record.getProductCode()).build();
                ProductStructure.GetProductDetailByModelResp productResponse = productService.getProductDetailByModel(getProductDetailByModelReq);
                ProductStructure.ProductBasicDetail productBasicDetail = productResponse.getProductBasicDetail();

                easAndXpsInventoryModel.setProductId(productBasicDetail.getProjectId());
                easAndXpsInventoryModel.setProductCode(productBasicDetail.getProductModel());
                easAndXpsInventoryModel.setProductName(productBasicDetail.getProductName());

                for (WarehouseStructure.Warehouse warehouse : warehouseListList) {   //遍历仓库列表，根据仓库名称匹配
                    if (warehouse.getEasWarehouseName().equals(record.getWarehouseName())) {
                        easAndXpsInventoryModel.setWarehouseId(String.valueOf(warehouse.getWarehouseId()));
                        easAndXpsInventoryModel.setWarehouseName(warehouse.getWarehouseName());

                    }
                }
                easAndXpsInventoryModel.setEasQuantity(record.getQuantity());
                easAndXpsInventoryModel.setDateTime(dateTime);

                /**
                 * 根据 采购类型,库存类型,项目id,批次号,商品code,仓库id 查询分销系统的批次库存
                 */
                ProductInventory productInventory = productInventoryDao.selectRecordByProductNameAndProductCodeAndwarehouse(record.getPosition(), record.getInventoryQuality(), projectID, record.getBatchNo(), record.getProductCode(), easAndXpsInventoryModel.getWarehouseId(), projectPrefix);
                if (productInventory != null) {
                    easAndXpsInventoryModel.setFenxiaoQuantity(productInventory.getOnHandQuantity() + productInventory.getOnSalesOrderQuantity());

                } else {
                    logger.info("即时库存Excel表中,purchaseType={},inventoryType={},projectId={},batchNo={},productCode={},warehouse={}在分销系统中没有匹配上", record.getPosition(), record.getInventoryQuality(), projectID, record.getBatchNo(), record.getProductCode(), easAndXpsInventoryModel.getWarehouseName());
                    easAndXpsInventoryModel.setFenxiaoQuantity(0);
                }
                easAndXpsInventoryModel.setDifferentQuantity(easAndXpsInventoryModel.getEasQuantity() - easAndXpsInventoryModel.getFenxiaoQuantity());
                easAndXpsInventoryModel.setDateTime(dateTime);
                easAndXpsInventoryModel.setCreateTime(dateTime);

                if (easAndXpsInventoryModel.getDifferentQuantity() != 0) {   //如果EAS的库存和分销的库存数量不对，记录对不上的批次库存记录
                    exception = true;
                    easAndXpsInventoryModelList.add(easAndXpsInventoryModel);
                }

            }
            checkEasInventoryItemDao.insertRecords(easAndXpsInventoryModelList, projectPrefix);   //插入EAS核对库存明细表

            easInventoryCheckResult.setProjectId(projectID);
            easInventoryCheckResult.setProjectName(project.getProjectName());
            easInventoryCheckResult.setException(exception);
            easInventoryCheckResult.setEasInventoryQuantity(easInventoryQuantity);

            //调用库存模块的项目的grpc查询项目信息
            InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryService = WarehouseRpcStubStore.getRpcStub(InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub.class);
            InventoryquerryStructure.GetAllInventoryInfoRequest getAllInventoryInfoRequest = InventoryquerryStructure.GetAllInventoryInfoRequest.newBuilder().setProjectId(projectId).build();
            InventoryquerryStructure.GetAllInventoryInfoResponse getAllInventoryInfoResponse = inventoryquerryService.selectAllInventoryInfo(getAllInventoryInfoRequest);
            for (ProductInventoryStructure.ProductInventory record : getAllInventoryInfoResponse.getListList()){     //获取分销的库存总量
                xpsInventoryQuantity += record.getOnHandQuantity()+record.getOnSalesOrderQuantity();
            }
            easInventoryCheckResult.setXpsInventoryQuantity(xpsInventoryQuantity);
            easInventoryCheckResult.setDifferentQuantity(easInventoryQuantity - xpsInventoryQuantity);
            easInventoryCheckResult.setDateTime(dateTime);
            easInventoryCheckResult.setCreateTime(dateTime);
            checkEasInventoryDao.insertRecords(easInventoryCheckResult,projectPrefix); //插入EAS核对库存主表

            respBuilder.setErrorCode(ErrorCode.SUCCESS.getErrorCode());
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get checkEasInventory success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectEasInventoryCheck(InsertInventoryStructure.SelectCheckEasInventoryRequest request, StreamObserver<InsertInventoryStructure.SelectCheckEasInventoryResponse> responseObserver) {
        InsertInventoryStructure.SelectCheckEasInventoryResponse response;
        InsertInventoryStructure.SelectCheckEasInventoryResponse.Builder respBuilder = InsertInventoryStructure.SelectCheckEasInventoryResponse.newBuilder();
        long projectId = request.getProjectId();
        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
        GongxiaoRpc.RpcHeader rpcHeader = GongxiaoRpc.RpcHeader.newBuilder().setUid("").setTraceId("123456").setUsername("").build();
        Date dateTime = new Date();
        try {
            List<EasInventoryCheckResult> easInventoryCheckResults = checkEasInventoryDao.selectRecords(projectId, projectPrefix);
            for (EasInventoryCheckResult record : easInventoryCheckResults) {
                InsertInventoryStructure.EasAndXpsInventoryModel model = InsertInventoryStructure.EasAndXpsInventoryModel.newBuilder().build();
            }

            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get checkEasInventory success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectEasInventoryCheckItem(InsertInventoryStructure.SelectEasInventoryCheckItemRequest request, StreamObserver<InsertInventoryStructure.SelectEasInventoryCheckItemResponse> responseObserver) {
        super.selectEasInventoryCheckItem(request, responseObserver);
    }

    public static void main(String[] agrs){
        Date date = new Date(1536162920101L);
        System.out.println(date);
    }
}

