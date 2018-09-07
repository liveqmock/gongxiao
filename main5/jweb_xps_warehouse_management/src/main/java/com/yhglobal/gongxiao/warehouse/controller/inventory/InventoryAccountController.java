package com.yhglobal.gongxiao.warehouse.controller.inventory;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure;
import com.yhglobal.gongxiao.inventory.account.microservice.InventoryLedgerServiceGrpc;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouse.controller.inventoryModel.InventoryBatch;
import com.yhglobal.gongxiao.warehouse.model.inventory.InventoryLedger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/inventoryAccount")
public class InventoryAccountController {

    private static Logger logger = LoggerFactory.getLogger(InventoryAccountController.class);

    /**
     * 查询台账信息
     *
     * @param pageNumber
     * @param pageSize
     * @param projectId   项目id
     * @param productCode 商品编码
     * @param productName 商品名称
     * @param warehouseId 仓库id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/standFlow", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<InventoryLedger> selectAccountInfo(String projectId, String productCode, String productName, String warehouseId, String startDate, String endDate, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {
        PageInfo<InventoryLedger> inventoryLedgerPageInfo = new PageInfo<>();

        String traceId = null;
        try {
            List<InventoryLedger> inventoryLedgerList = new ArrayList<>();
            traceId = "";
            logger.info("#traceId={}# [IN][selectAccountInfo] params: pageNumber={}, pageSize={}, projectId={}, productCode={}, productName={}, warehouseId={}, startDate={}, endDate={}", traceId, pageNumber, pageSize, projectId, productCode, productName, warehouseId, startDate, endDate);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            InventoryLedgerServiceGrpc.InventoryLedgerServiceBlockingStub inventoryLedgerService = WarehouseRpcStubStore.getRpcStub(InventoryLedgerServiceGrpc.InventoryLedgerServiceBlockingStub.class);
            AccountStructure.GetInventoryLedgerRequest getInventoryLedgerRequest = AccountStructure.GetInventoryLedgerRequest.newBuilder()
                    .setProjectId(projectId)
                    .setProductCode(productCode)
                    .setProductNam(productName)
                    .setWarehouseId(warehouseId)
                    .setStartDate(startDate)
                    .setEndDate(endDate).build();
            AccountStructure.AccountPageInfo accountpageInfo = inventoryLedgerService.getInventoryLedger(getInventoryLedgerRequest);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (AccountStructure.InventoryLedger record : accountpageInfo.getListList()) {
                InventoryLedger inventoryLedger = new InventoryLedger();
                inventoryLedger.setDateTime(new Date(record.getDateTime()));
                inventoryLedger.setProjectId(record.getProjectId());
                inventoryLedger.setProductId(record.getProductId());
                inventoryLedger.setProductCode(record.getProductCode());
                inventoryLedger.setProductModel(record.getProductModel());
                inventoryLedger.setProductName(record.getProductName());
                inventoryLedger.setWarehouseName(record.getWarehouseName());
                inventoryLedger.setBeginTotalAmount(record.getBeginTotalAmount());
                inventoryLedger.setBeginNonDefective(record.getBeginNonDefective());
                inventoryLedger.setBeginDefective(record.getBeginDefective());
                inventoryLedger.setInStockTotalAmount(record.getInStockTotalAmount());
                inventoryLedger.setInStockNonDefectiveAmount(record.getInStockNonDefectiveAmount());
                inventoryLedger.setInStockDefectiveAmount(record.getInStockDefectiveAmount());
                inventoryLedger.setOutStockTotalAmount(record.getOutStockTotalAmount());
                inventoryLedger.setNonDefectiveSaleAmount(record.getNonDefectiveSaleAmount());
                inventoryLedger.setNonDefectiveOtherAmount(record.getNonDefectiveOtherAmount());
                inventoryLedger.setEndTotalAmount(record.getEndTotalAmount());
                inventoryLedger.setEndNonDefectiveAmount(record.getEndNonDefectiveAmount());
                inventoryLedger.setEndDefectiveAmount(record.getEndDefectiveAmount());
                inventoryLedger.setOnPurchaseAmount(record.getOnPurchaseAmount());
                inventoryLedger.setOnTransferInAmount(record.getOnTransferInAmount());
                inventoryLedger.setOnTransferOutAmount(record.getOnTransferOutAmount());
                inventoryLedger.setNonDefectiveProfitkAmount(record.getNonDefectiveProfitkAmount());
                inventoryLedger.setDefectiveProfitAmount(record.getDefectiveProfitAmount());
                inventoryLedger.setDefectiveOutAmount(record.getDefectiveOutAmount());
                inventoryLedger.setNonDefectiveLossAmount(record.getNonDefectiveLossAmount());
                inventoryLedger.setDefectiveLossAmount(record.getDefectiveLossAmount());
                inventoryLedger.setAdjustmentAccountTotalOut(record.getAdjustmentAccountTotalOut());
                inventoryLedger.setAdjustmentAccountNonDefectiveOut(record.getAdjustmentAccountNonDefectiveOut());
                inventoryLedger.setAdjustmentAccountDefectiveOut(record.getAdjustmentAccountDefectiveOut());
                inventoryLedger.setModifyTotalAmountOut(record.getModifyTotalAmountOut());
                inventoryLedger.setModifyNonDefectiveAmountOut(record.getModifyNonDefectiveAmountOut());
                inventoryLedger.setModifyDefectiveAmountOut(record.getModifyDefectiveAmountOut());
                inventoryLedger.setAdjustmentAccountTotalIn(record.getAdjustmentAccountTotalIn());
                inventoryLedger.setAdjustmentAccountNonDefectiveIn(record.getAdjustmentAccountNonDefectiveIn());
                inventoryLedger.setAdjustmentAccountDefectiveIn(record.getAdjustmentAccountDefectiveIn());
                inventoryLedger.setModifyTotalAmountIn(record.getModifyTotalAmountIn());
                inventoryLedger.setModifyNonDefectiveAmountIn(record.getModifyNonDefectiveAmountIn());
                inventoryLedger.setModifyDefectiveAmountIn(record.getModifyDefectiveAmountIn());

                inventoryLedgerList.add(inventoryLedger);
            }
            PageHelper.startPage(pageNumber, pageSize);
            inventoryLedgerPageInfo = new PageInfo<InventoryLedger>(inventoryLedgerList);
            inventoryLedgerPageInfo.setTotal(accountpageInfo.getTotal());
            logger.info("#traceId={}# [OUT] get selectAccountInfo success: resultList.size()={}", traceId);
            return inventoryLedgerPageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return inventoryLedgerPageInfo;
        }

    }
}
