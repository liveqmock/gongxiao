package com.yhglobal.gongxiao.warehouse.controller.inventory;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.grpc.client.WarehouseRpcStubStore;
import com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryage.microservice.InventoryAgeStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.warehouse.model.inventory.InventoryAge;
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
import java.util.List;

@RequestMapping("/inventoryAge")
@Controller
public class InventoryAgeController {
    private static Logger logger = LoggerFactory.getLogger(InventoryAgeController.class);

    @RequestMapping(value = "/getAgeInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<InventoryAge> selectAccountInfo(String projectId, String batchNo, String inboundOrderNo, String outboundOrderNo, String startDate, String endDate, int pageNumber, int pageSize, HttpServletRequest request, HttpServletResponse response) {

        PageInfo<InventoryAge> inventoryAgePageInfo = new PageInfo<>();

        String traceId = null;
        try {
            List<InventoryAge> inventoryAgeList = new ArrayList<>();
            traceId = "";
            logger.info("#traceId={}# [IN][selectAccountInfo] params: pageNumber={}, pageSize={}, projectId={}, batchNo={}, inboundOrderNo={}, outboundOrderNo={}, startDate={}, endDate={}", traceId, pageNumber, pageSize, projectId, batchNo, inboundOrderNo, outboundOrderNo, startDate, endDate);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "", "");
            InventoryAgeServiceGrpc.InventoryAgeServiceBlockingStub inventoryAgeService = WarehouseRpcStubStore.getRpcStub(InventoryAgeServiceGrpc.InventoryAgeServiceBlockingStub.class);
            InventoryAgeStructure.GetInventoryAgeRequest getInventoryLedgerRequest = InventoryAgeStructure.GetInventoryAgeRequest.newBuilder()
                    .setProjectId(projectId)
                    .setBatchNo(batchNo)
                    .setInboundOrderNo(inboundOrderNo)
                    .setOutboundOrderNo(outboundOrderNo)
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize).build();
            InventoryAgeStructure.InventoryAgePageInfo inventoryLedgerPageInfo = inventoryAgeService.getInventoryAge(getInventoryLedgerRequest);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (InventoryAgeStructure.InventoryAge record : inventoryLedgerPageInfo.getListList()) {
                InventoryAge inventoryAge = new InventoryAge();
                if (record.getStatus() == 1){
                    inventoryAge.setStatus("正常");
                }else {
                    inventoryAge.setStatus("关闭");
                }

                inventoryAge.setBatchNo(record.getBatchNo());
                inventoryAge.setProductCode(record.getProductCode());
                inventoryAge.setProductName(record.getProductName());
                inventoryAge.setWarehouseName(record.getWarehouseName());
                inventoryAge.setInboundQuantity(record.getInboundQuantity());
                inventoryAge.setPurchaseOrderNo(record.getPurchaseOrderNo());
                inventoryAge.setCreateTime(record.getCreateTime());
                inventoryAge.setReceiveFinishTime(record.getReceiveFinishTime());
                inventoryAge.setPurchaseGuidPrice(record.getPurchaseGuidPrice());
                inventoryAge.setPurchasePrice(record.getPurchasePrice());
                inventoryAge.setCostPrice(record.getCostPrice());
                inventoryAge.setBatchInboundAmount(record.getBatchInboundAmount());
                inventoryAge.setInventoryAge(record.getInventoryAge());
                inventoryAge.setRestAmount(record.getRestAmount());
                inventoryAge.setSalesOrderNo(record.getSalesOrderNo());
                inventoryAge.setOutboundOrderNo(record.getOutboundOrderNo());
                inventoryAge.setSalesGuidPrice(record.getSalesGuidPrice());
                inventoryAge.setOutboundPrice(record.getOutboundPrice());
                inventoryAge.setOutboundQuantity(record.getOutboundQuantity());
                inventoryAge.setBatchOutboundAmount(record.getBatchOutboundAmount());
                inventoryAge.setCustomerUseCoupon(record.getCustomerUseCoupon());
                inventoryAge.setCustomerUsePrepaid(record.getCustomerUsePrepaid());
                inventoryAge.setOutboundDate(record.getOutboundDate());
                inventoryAge.setSightTime(record.getSightTime());
                inventoryAgeList.add(inventoryAge);
            }
            PageHelper.startPage(pageNumber, pageSize);
            inventoryAgePageInfo = new PageInfo<InventoryAge>(inventoryAgeList);
            inventoryAgePageInfo.setTotal(inventoryAgePageInfo.getTotal());
            logger.info("#traceId={}# [OUT] get selectAccountInfo success: resultList.size()={}", traceId);
            return inventoryAgePageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return inventoryAgePageInfo;
        }

    }

}
