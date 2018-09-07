package com.yhglobal.gongxiao.warehouse.controller.inventory;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventoryCheckServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorycheck.microservice.InventorycheckStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.warehouse.model.bo.InventoryCheckModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/duizhan")
public class InventorycheckController {
    private static Logger logger = LoggerFactory.getLogger(InventorycheckController.class);

    /**
     * 核对wms库存
     *
     * @return
     */
    @RequestMapping("/checkinventory")
    @ResponseBody
    public PageInfo<InventoryCheckModel> checkinventory(String projectId, String warehouseId, String productCode, String productName, int pageNumber, int pageSize, HttpServletRequest request) {
        PageInfo<InventoryCheckModel> inventoryCheckModelPageInfo = new PageInfo<>();
        String traceId = null;
        try {
            traceId = "";
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            String signature = rpcHeader.getUsername();
            if (warehouseId.equals("null")){
                warehouseId = "";
            }
            logger.info("#traceId={}# [IN][checkinventory] params: projectId={},warehouseId={},projectId={},productCode={},productName={},pageNumber={},pageSize={}", traceId, projectId, projectId, warehouseId, productCode, productName, pageNumber, pageSize);
            List<InventoryCheckModel> resultList = new ArrayList<>();
            InventoryCheckServiceGrpc.InventoryCheckServiceBlockingStub inventoryCheckService = WarehouseRpcStubStore.getRpcStub(InventoryCheckServiceGrpc.InventoryCheckServiceBlockingStub.class);
            InventorycheckStructure.GetInventoryCheckRequest getInventoryCheckRequest = InventorycheckStructure.GetInventoryCheckRequest.newBuilder()
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .setProjectId(projectId)
                    .setWarehouseId(warehouseId)
                    .setProductCode(productCode)
                    .setProductName(productName).build();

            InventorycheckStructure.InventoryCheckPageInfo inventoryCheckPageInfo = inventoryCheckService.getInventoryCheck(getInventoryCheckRequest);
            for (InventorycheckStructure.InventoryCheckModel record : inventoryCheckPageInfo.getListList()) {
                InventoryCheckModel inventoryCheckModel = new InventoryCheckModel();
                inventoryCheckModel.setDateTime(new Date(record.getDateTime()));
                inventoryCheckModel.setProjectId(record.getProjectId());
                inventoryCheckModel.setProjectName(record.getProjectName());
                inventoryCheckModel.setWarehouseId(record.getWarehouseId());
                inventoryCheckModel.setWarehouseName(record.getWarehouseName());
                inventoryCheckModel.setProductId(record.getProductId());
                inventoryCheckModel.setProductCode(record.getProductCode());
                inventoryCheckModel.setProductName(record.getProductName());
                inventoryCheckModel.setProjectName(record.getProjectName());
                inventoryCheckModel.setFenxiaoPerfectQuantity(record.getFenxiaoPerfectQuantity());
                inventoryCheckModel.setWmsPerfectQuantity(record.getWmsPerfectQuantity());
                inventoryCheckModel.setPerfectDifferent(record.getPerfectDifferent());
                inventoryCheckModel.setFenxiaoImperfectQuantity(record.getFenxiaoImperfectQuantity());
                inventoryCheckModel.setWmsImperfectQuantity(record.getWmsImperfectQuantity());
                inventoryCheckModel.setImPerfectDifferent(record.getImPerfectDifferent());
                inventoryCheckModel.setFenxiaoEngineDamage(record.getFenxiaoEngineDamage());
                inventoryCheckModel.setWmsEngineDamage(record.getWmsEngineDamage());
                inventoryCheckModel.setEngineDamageDifferent(record.getEngineDamageDifferent());
                inventoryCheckModel.setFenxiaoBoxDamage(record.getFenxiaoBoxDamage());
                inventoryCheckModel.setWmsBoxDamage(record.getWmsBoxDamage());
                inventoryCheckModel.setBoxDamageDifferent(record.getBoxDamageDifferent());
                inventoryCheckModel.setFenxiaoFrozenStock(record.getFenxiaoFrozenStock());
                inventoryCheckModel.setWmsFrozenStock(record.getWmsFrozenStock());
                inventoryCheckModel.setFrozenStockDifferent(record.getFrozenStockDifferent());
                resultList.add(inventoryCheckModel);
            }
            PageHelper.startPage(pageNumber, pageSize);
            inventoryCheckModelPageInfo = new PageInfo<InventoryCheckModel>(resultList);
            inventoryCheckModelPageInfo.setTotal(inventoryCheckPageInfo.getTotal());
            logger.info("#traceId={}# [OUT] get checkinventory success", traceId);
            return inventoryCheckModelPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return inventoryCheckModelPageInfo;
        }
    }

}
