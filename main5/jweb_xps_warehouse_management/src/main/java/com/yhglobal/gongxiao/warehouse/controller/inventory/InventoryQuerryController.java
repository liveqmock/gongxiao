package com.yhglobal.gongxiao.warehouse.controller.inventory;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryquerry.microservice.InventoryquerryStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.inventory.ProductInventoryShowModel;
import com.yhglobal.gongxiao.warehouse.model.inventory.WarehouseInventoryShowModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/inventoryQuerry")
public class InventoryQuerryController {

    private static Logger logger = LoggerFactory.getLogger(InventoryQuerryController.class);


    /**
     * 商品库存查询
     *
     * @param projectId   项目id
     * @param productCode 商品编码
     * @param productName 商品名称
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selectProductInventoryInfo(GongxiaoRpc.RpcHeader rpcHeader, int pageNumber, int pageSize, long projectId, String productCode, String productName, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selectProductInventoryInfo] params: projectId={}, productCode={}, productName={}", traceId, projectId, productCode, productName);
            List<ProductInventoryShowModel> resultList = new ArrayList<>();
            InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryService = WarehouseRpcStubStore.getRpcStub(InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub.class);
            InventoryquerryStructure.GetProductInventoryByNameRequest getProductInventoryByNameRequest = InventoryquerryStructure.GetProductInventoryByNameRequest.newBuilder()
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .setProjectId(projectId)
                    .setProductCode(productCode)
                    .setProductName(productName).build();
            InventoryquerryStructure.GetProductInventoryByNameResponse getProductInventoryByNameResponse = inventoryquerryService.getProductInventoryByName(getProductInventoryByNameRequest);

            for (InventoryquerryStructure.ProductInventoryShowModel record : getProductInventoryByNameResponse.getListList()) {
                ProductInventoryShowModel productInventoryShowModel = new ProductInventoryShowModel();
                productInventoryShowModel.setProjectId(record.getProjectId());
                productInventoryShowModel.setProductCode(record.getProductCode());
                productInventoryShowModel.setProductName(record.getProductName());
                productInventoryShowModel.setAvailableQuantity(record.getAvailableQuantity());
                productInventoryShowModel.setOccupancyQuantity(record.getOccupancyQuantity());
                productInventoryShowModel.setDefectiveQuantity(record.getDefectiveQuantity());
                productInventoryShowModel.setPhysicalInventory(record.getPhysicalInventory());
                productInventoryShowModel.setOnWayQuantity(record.getOnWayQuantity());
                productInventoryShowModel.setFrozenQuantity(record.getFrozenQuantity());
                resultList.add(productInventoryShowModel);
            }
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selectProductInventoryInfo success:  resultList.size()={}", traceId, resultList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

        return gongxiaoResult;
    }

    /**
     * 仓库库存查询
     *
     * @param projectId   项目id
     * @param productCode 商品编码
     * @param productName 商品名
     * @param warehouseId 仓库id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult selecWarehouseInventoryInfo(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String productCode, String productName, String warehouseId, String batchNo, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][selecWarehouseInventoryInfo] params: projectId={}, productCode={}, productName={}, warehouseId={}, batchNo = {}", traceId,projectId, productCode, productName, warehouseId, batchNo);
            List<WarehouseInventoryShowModel> resultList = new ArrayList<>();
            InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryService = WarehouseRpcStubStore.getRpcStub(InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub.class);
            InventoryquerryStructure.GetWarehouseInventoryRequest getWarehouseInventoryRequest = InventoryquerryStructure.GetWarehouseInventoryRequest.newBuilder()
                    .setPageNumber(1)
                    .setPageSize(60)
                    .setProjectId(projectId)
                    .setProductCode(productCode)
                    .setProductName(productName)
                    .setWarehouseId(warehouseId)
                    .setBatchNo(batchNo)
                    .build();

            InventoryquerryStructure.GetWarehouseInventoryResponse warehouseInventory = inventoryquerryService.getWarehouseInventory(getWarehouseInventoryRequest);
            for (InventoryquerryStructure.WarehouseInventoryShowModel record : warehouseInventory.getListList()) {
                WarehouseInventoryShowModel warehouseInventoryShowModel = new WarehouseInventoryShowModel();
                warehouseInventoryShowModel.setProjectId(record.getProjectId());
                warehouseInventoryShowModel.setProductCode(record.getProductCode());
                warehouseInventoryShowModel.setProductName(record.getProductName());
                warehouseInventoryShowModel.setWarehouseName(record.getWarehouseName());
                warehouseInventoryShowModel.setBatchNo(record.getBatchNo());
                warehouseInventoryShowModel.setPurchaseType(record.getPurchaseType());
                warehouseInventoryShowModel.setOccupancyQuantity(record.getOccupancyQuantity());
                warehouseInventoryShowModel.setAvailableQuantity(record.getAvailableQuantity());
                warehouseInventoryShowModel.setStatus(record.getStatus());

                resultList.add(warehouseInventoryShowModel);
            }
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(resultList);
            logger.info("#traceId={}# [OUT] get selecWarehouseInventoryInfo success:  resultList.size()={}", traceId, resultList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    @RequestMapping(value = "/productQuantity", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getproductQuqntity(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String productCode, HttpServletRequest request, HttpServletResponse response) {
        GongxiaoResult gongxiaoResult = new GongxiaoResult();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][getproductQuqntity] params: projectId={}, productCode={}", traceId, projectId, productCode);
            InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryService = WarehouseRpcStubStore.getRpcStub(InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub.class);
            InventoryquerryStructure.GetQuantityRequest getQuantityRequest = InventoryquerryStructure.GetQuantityRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setProductCode(productCode).build();

            InventoryquerryStructure.GetQuantityResponse getQuantityResponse = inventoryquerryService.selectQuantityByProjectAndProductCode(getQuantityRequest);
            int quantity = getQuantityResponse.getQuantity();
            gongxiaoResult.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            gongxiaoResult.setData(quantity);
            logger.info("#traceId={}# [OUT] get getproductQuqntity success", traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 导出商品库存Excel
     *
     * @param
     * @return
     */
//    @RequestMapping("/product/export")
//    public void exportProdctInventory(long projectId, String productCode, String productName, HttpServletRequest request, HttpServletResponse response) {
////        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
//        logger.info("#traceId={}# [IN][exportProdctInventory] params: projectId:{}, productCode:{}, productName:{}", "9527", projectId, productCode, productName);
//        String downFileName = new String("商品库存列表.xls");
//        try {
//            List<ProductInventoryShowModel> resultList = new ArrayList<>();
//            InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryService = WarehouseRpcStubStore.getRpcStub(InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub.class);
//            InventoryquerryStructure.GetProductInventoryByNameRequest getProductInventoryByNameRequest = InventoryquerryStructure.GetProductInventoryByNameRequest.newBuilder()
//                    .setPageNumber(0)
//                    .setPageSize(60)
//                    .setProjectId(projectId)
//                    .setProductCode(productCode)
//                    .setProductName(productName).build();
//            InventoryquerryStructure.GetProductInventoryByNameResponse productInventoryByNameResponse = inventoryquerryService.getProductInventoryByName(getProductInventoryByNameRequest);
//
//            for (InventoryquerryStructure.ProductInventoryShowModel record : productInventoryByNameResponse.getListList()){
//                ProductInventoryShowModel productInventoryShowModel = new ProductInventoryShowModel();
//                productInventoryShowModel.setProjectId(record.getProjectId());
//                productInventoryShowModel.setProductName(record.getProductName());
//                productInventoryShowModel.setAvailableQuantity(record.getAvailableQuantity());
//                productInventoryShowModel.setOccupancyQuantity(record.getOccupancyQuantity());
//                productInventoryShowModel.setDefectiveQuantity(record.getDefectiveQuantity());
//                productInventoryShowModel.setPhysicalInventory(record.getPhysicalInventory());
//                productInventoryShowModel.setOnWayQuantity(record.getOnWayQuantity());
//                productInventoryShowModel.setFrozenQuantity(record.getFrozenQuantity());
//                resultList.add(productInventoryShowModel);
//            }
//            ExcelUtil<ProductInventoryShowModel> util = new ExcelUtil<ProductInventoryShowModel>(ProductInventoryShowModel.class);
//            Workbook workbook = util.getListToExcel(resultList, "订单列表");
//            ExcelDownUtil.resetResponse(response, workbook, downFileName);
//            logger.info("#traceId={}# [exportProdctInventory][OUT] get exportProdctInventory success", "9527");
//        } catch (Exception e) {
//            logger.error("#traceId=" + "9527" + "# [OUT] errorMessage: " + e.getMessage(), e);
//        }
//    }

    /**
     * 导出仓库库存Excel
     *
     * @param
     * @return
     */
//    @RequestMapping("/stock/export")
//    public void exportStockInventory(String projectId, String productCode, String productName, String warehouseId, HttpServletRequest request, HttpServletResponse response) {
//
//        String traceId = null;
//        traceId = "";
//        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
//        logger.info("#traceId={}# [IN][exportStockInventory] params: projectId={}, productCode={}, productName={}, warehouseId={}, ", traceId, projectId, productCode, productName, warehouseId);
//        String downFileName = new String("仓库库存列表.xls");
//        try {
//            List<WarehouseInventoryShowModel> resultList = new ArrayList<>();
//            InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub inventoryquerryService = WarehouseRpcStubStore.getRpcStub(InventoryquerryServiceGrpc.InventoryquerryServiceBlockingStub.class);
//            InventoryquerryStructure.GetWarehouseInventoryRequest getWarehouseInventoryRequest = InventoryquerryStructure.GetWarehouseInventoryRequest.newBuilder()
//                    .setPageNumber(0)
//                    .setPageSize(60)
//                    .setProjectId(projectId)
//                    .setProductCode(productCode)
//                    .setProductName(productName)
//                    .setWarehouseId(warehouseId).build();
//
//            InventoryquerryStructure.GetWarehouseInventoryResponse warehouseInventory = inventoryquerryService.getWarehouseInventory(getWarehouseInventoryRequest);
//            for (InventoryquerryStructure.WarehouseInventoryShowModel record : warehouseInventory.getListList()) {
//                WarehouseInventoryShowModel warehouseInventoryShowModel = new WarehouseInventoryShowModel();
//                warehouseInventoryShowModel.setProjectId(record.getProjectId());
//                warehouseInventoryShowModel.setProductCode(record.getProductCode());
//                warehouseInventoryShowModel.setProductName(record.getProductName());
//                warehouseInventoryShowModel.setWarehouseName(record.getWarehouseName());
//                warehouseInventoryShowModel.setBatchNo(record.getBatchNo());
//                warehouseInventoryShowModel.setPurchaseType(record.getPurchaseType());
//                warehouseInventoryShowModel.setOccupancyQuantity(record.getOccupancyQuantity());
//                warehouseInventoryShowModel.setAvailableQuantity(record.getAvailableQuantity());
//                warehouseInventoryShowModel.setStatus(record.getStatus());
//
//                resultList.add(warehouseInventoryShowModel);
//            }
//            ExcelUtil<WarehouseInventoryShowModel> util = new ExcelUtil<WarehouseInventoryShowModel>(WarehouseInventoryShowModel.class);
//            Workbook workbook = util.getListToExcel(resultList,"订单列表");
//            ExcelDownUtil.resetResponse(response,workbook,downFileName);
//            logger.info("#traceId={}# [exportStockInventory][OUT] get exportStockInventory success",traceId);
//        } catch (Exception e) {
//            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
//        }
//    }
}
