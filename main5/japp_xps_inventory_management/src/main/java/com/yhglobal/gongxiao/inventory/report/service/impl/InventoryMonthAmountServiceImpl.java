package com.yhglobal.gongxiao.inventory.report.service.impl;

import com.yhglobal.gongxiao.inventory.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmoundServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventorymonthamount.microservice.InventoryMonthAmountStructure;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InventoryMonthAmountServiceImpl extends InventoryMonthAmoundServiceGrpc.InventoryMonthAmoundServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(ProductInventory180AgeServiceImpl.class);

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Override
    public void getInventoryMonthAmount(InventoryMonthAmountStructure.GetInventoryMonthAmountRequest request, StreamObserver<InventoryMonthAmountStructure.GetInventoryMonthAmountRespon> responseObserver) {
        InventoryMonthAmountStructure.GetInventoryMonthAmountRespon response;
        InventoryMonthAmountStructure.GetInventoryMonthAmountRespon.Builder respBuilder = InventoryMonthAmountStructure.GetInventoryMonthAmountRespon.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        String yearMonthRequest = request.getYearMonth();
        String year = yearMonthRequest.substring(0, 4);
        String month = yearMonthRequest.substring(4, 6);
        String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

        try {
            logger.info("#traceId={}# [IN][getInventoryMonthAmount] params: projectId = {}, yearMonth= {}", rpcHeader.getTraceId(), projectId, yearMonthRequest);
            //根据日期查出月份的库存金额
            long totalProductInventoryAmount = 0;
            List<ProductInventory> productInventoryList = productInventoryDao.getMonthTotalInventoryAmount(year, month, projectPrefix);
            for (ProductInventory record : productInventoryList) {
                totalProductInventoryAmount += (record.getOnHandQuantity() + record.getOnSalesOrderQuantity()) * record.getPurchasePrice();
            }

            logger.info("======{}月份的库存金额 = {}", yearMonthRequest, totalProductInventoryAmount);
            InventoryMonthAmountStructure.InventoryMonthAmount inventoryMonthAmount = InventoryMonthAmountStructure.InventoryMonthAmount.newBuilder()
                    .setSumOfmoney(totalProductInventoryAmount)
                    .build();

            respBuilder.setInventoryMonthAmount(inventoryMonthAmount);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get getInventoryMonthAmount success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}
