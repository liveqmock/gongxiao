package com.yhglobal.gongxiao.warehouse.controller.report;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure;
import com.yhglobal.gongxiao.inventory.account.microservice.InventoryLedgerServiceGrpc;
import com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.productinventory180age.microservice.ProductInventory180AgeStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.model.ProductInventory180Age;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 报表库龄180天以上库存情况
 */

@Controller
@RequestMapping("/inventory180Age")
public class ProductInventory180AgeController {

    private static Logger logger = LoggerFactory.getLogger(ProductInventory180AgeController.class);

    @RequestMapping(value = "/get180Age", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getInventory180Age(String projectId, HttpServletRequest request, HttpServletResponse response) {
        logger.info("#[getInventory180Age] params: projectId = {}",  projectId);
        String traceId = "1325456";
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
        try {
            ProductInventory180AgeServiceGrpc.ProductInventory180AgeServiceBlockingStub inventory180AgeService = WarehouseRpcStubStore.getRpcStub(ProductInventory180AgeServiceGrpc.ProductInventory180AgeServiceBlockingStub.class);
            ProductInventory180AgeStructure.GetProductInventory180AgeRequest getInventoryLedgerRequest = ProductInventory180AgeStructure.GetProductInventory180AgeRequest.newBuilder()
                    .setProjectId(Long.parseLong(projectId))
                    .setRpcHeader(rpcHeader)
                    .build();
            ProductInventory180AgeStructure.GetProductInventory180AgeRespon getProductInventory180AgeRespon = inventory180AgeService.getProductInventory180Age(getInventoryLedgerRequest);
            int size = getProductInventory180AgeRespon.getListList().size();
            ProductInventory180Age productInventory180Age = new ProductInventory180Age();

            String[] productCode = new String[size];
            double[] sumOfmoney = new double[size];
            double[] proportion = new double[size];
            int j = 0;
            for (ProductInventory180AgeStructure.ProductInventory180Age record : getProductInventory180AgeRespon.getListList()) {
                productCode[j] = record.getProductCode();
                sumOfmoney[j] = record.getSumOfmoney() / 1000000.0;
                proportion[j] = record.getProportion();

                j++;
            }
            productInventory180Age.setProductCode(productCode);
            productInventory180Age.setSumOfmoney(sumOfmoney);
            productInventory180Age.setProportion(proportion);


            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(),productInventory180Age);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }


    }
}
