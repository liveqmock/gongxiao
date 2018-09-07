package com.yhglobal.gongxiao.warehouse.controller.inventory;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryFlowServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.bo.ProductInventoryFlowShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/InventoryFlow")
public class InventoryFlowController {

    private static Logger logger = LoggerFactory.getLogger(InventoryFlowController.class);

    /**
     * 查询出入库流水
     *
     * @param pageNumber  页数
     * @param pageSize    页码
     * @param projectId   项目id
     * @param productCode 商品编码
     * @param productName 商品名称
     * @param warehouseId 仓库id
     * @param orderNo     单号
     * @param startDate   起始时间
     * @param endDate     结束时间
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/selectFlow")
    @ResponseBody
    public PageInfo<ProductInventoryFlowShow> handlePurchaseFlow(int pageNumber, int pageSize, String projectId, String productCode, String productName, String warehouseId, String orderNo, String startDate, String endDate, HttpServletRequest request, HttpServletResponse response) {
        PageInfo<ProductInventoryFlowShow> productInventoryFlowShowPageInfo = new PageInfo<>();
        String traceId = null;
        try {
            traceId = "";
            logger.info("#traceId={}# [IN][handlePurchaseFlow] params: projectId={}, productCode={}, productName={}, warehouseid={}, " +
                    "orderno={}, startdate={}, enddate={}", traceId, projectId, productCode, productName, warehouseId, orderNo, startDate, endDate);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            List<ProductInventoryFlowShow> resultList = new ArrayList<>();
            InventoryFlowServiceGrpc.InventoryFlowServiceBlockingStub inventoryFlowService = WarehouseRpcStubStore.getRpcStub(InventoryFlowServiceGrpc.InventoryFlowServiceBlockingStub.class);
            InventoryflowStructure.GetInventoryFlowRequest getInventoryFlowRequest = InventoryflowStructure.GetInventoryFlowRequest.newBuilder()
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .setProjectId(projectId)
                    .setProductModel(productCode)
                    .setProductName(productName)
                    .setWarehouseName(warehouseId).build();

            InventoryflowStructure.GetInventoryFlowResponse getInventoryFlowResponse = inventoryFlowService.getInventoryFlow(getInventoryFlowRequest);
            for (InventoryflowStructure.ProductInventoryFlowShow record : getInventoryFlowResponse.getListList()) {
                ProductInventoryFlowShow productInventoryFlowShow = new ProductInventoryFlowShow();
                productInventoryFlowShow.setInventoryFlowId(record.getInventoryFlowId());
                productInventoryFlowShow.setOrderNo(record.getOrderNo());
                productInventoryFlowShow.setOrderType(record.getOrderType());
                productInventoryFlowShow.setBatchNo(record.getBatchNo());
                productInventoryFlowShow.setInventoryFlowType(record.getInventoryFlowType());
                productInventoryFlowShow.setProjectId(record.getProjectId());
                productInventoryFlowShow.setRelatedOrderNo(record.getRelatedOrderNo());
                productInventoryFlowShow.setProductId(record.getProductId());
                productInventoryFlowShow.setProductCode(record.getProductCode());
                productInventoryFlowShow.setProductModel(record.getProductModel());
                productInventoryFlowShow.setProductName(record.getProductName());
                productInventoryFlowShow.setWarehouseId(record.getWarehouseId());
                productInventoryFlowShow.setWarehouseName(record.getWarehouseName());
                productInventoryFlowShow.setAmountBeforeTransaction(record.getAmountBeforeTransaction());
                productInventoryFlowShow.setTransactionAmount(record.getTransactionAmount());
                productInventoryFlowShow.setAmountAfterTransaction(record.getAmountAfterTransaction());
                productInventoryFlowShow.setPerfectQuantity(record.getPerfectQuantity());
                productInventoryFlowShow.setImperfectQuantity(record.getImperfectQuantity());
                productInventoryFlowShow.setCreateTime(new Date(record.getCreateTime()));
                resultList.add(productInventoryFlowShow);
            }
            PageHelper.startPage(pageNumber, pageSize);
            productInventoryFlowShowPageInfo = new PageInfo<ProductInventoryFlowShow>(resultList);
            productInventoryFlowShowPageInfo.setTotal(getInventoryFlowResponse.getTotal());
            logger.info("#traceId={}# [OUT] get handlePurchaseFlow success: resultList.size()={}", rpcHeader.getTraceId(), resultList.size());
            return productInventoryFlowShowPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return productInventoryFlowShowPageInfo;
        }
    }
}
