package com.yhglobal.gongxiao.inventory.account;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.inventory.account.microservice.AccountStructure;
import com.yhglobal.gongxiao.inventory.account.microservice.InventoryLedgerServiceGrpc;
import com.yhglobal.gongxiao.inventory.dao.ImportAndExportAccountDao;
import com.yhglobal.gongxiao.inventory.model.InventoryLedger;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLedgerServiceImpl extends InventoryLedgerServiceGrpc.InventoryLedgerServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(InventoryLedgerServiceImpl.class);

    @Autowired
    ImportAndExportAccountDao importAndExportAccountDao;   //进销存台账

    @Override
    public void getInventoryLedger(AccountStructure.GetInventoryLedgerRequest request, StreamObserver<AccountStructure.AccountPageInfo> responseObserver) {

        AccountStructure.AccountPageInfo response;
        AccountStructure.AccountPageInfo.Builder respBuilder = AccountStructure.AccountPageInfo.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String projectId = request.getProjectId();
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();
        String productCode = request.getProductCode();
        String productName = request.getProductNam();
        String warehouseId = request.getWarehouseId();
        String startDate = request.getStartDate();
        String endDate = request.getEndDate();

        try {
            logger.info("#traceId={}# [IN][conditionalSelectInventoryLedger] params: projectId={}, productCode={}, productName={}, warehouseId={}, startDate={}, endDate={}", rpcHeader.getTraceId(),projectId, productCode, productName, warehouseId, startDate, endDate);
            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //启动分页
            PageHelper.startPage(pageNumber, pageSize);
            //查询列表
            List<InventoryLedger> resultList =  importAndExportAccountDao.selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate(projectId,productCode,productName,warehouseId, startDate,endDate,projectPrefix);
            PageInfo<InventoryLedger> inventoryLedgerPageInfo = new PageInfo<>(resultList);
            long total = inventoryLedgerPageInfo.getTotal();
            logger.info("#traceId={}# [OUT]: select list success.", rpcHeader.getTraceId());

            for (InventoryLedger javaObject : resultList) {

                AccountStructure.InventoryLedger.Builder innerModel = AccountStructure.InventoryLedger.newBuilder()
                        .setProjectId(javaObject.getProjectId())
                        .setDateTime(javaObject.getDateTime().getTime())
                        .setProductId(javaObject.getProductId())
                        .setProductCode(javaObject.getProductCode())
                        .setProductModel(javaObject.getProductModel())
                        .setProductName(javaObject.getProductName())
                        .setWarehouseName(javaObject.getWarehouseName())
                        .setBeginTotalAmount(javaObject.getBeginTotalAmount())
                        .setBeginNonDefective(javaObject.getBeginNonDefective())
                        .setBeginDefective(javaObject.getBeginDefective())
                        .setInStockTotalAmount(javaObject.getInStockTotalAmount())
                        .setInStockNonDefectiveAmount(javaObject.getInStockNonDefectiveAmount())
                        .setInStockDefectiveAmount(javaObject.getInStockDefectiveAmount())
                        .setOutStockTotalAmount(javaObject.getOutStockTotalAmount())
                        .setNonDefectiveSaleAmount(javaObject.getNonDefectiveSaleAmount())
                        .setNonDefectiveOtherAmount(javaObject.getNonDefectiveOtherAmount())
                        .setEndTotalAmount(javaObject.getEndTotalAmount())
                        .setEndNonDefectiveAmount(javaObject.getEndNonDefectiveAmount())
                        .setEndDefectiveAmount(javaObject.getEndDefectiveAmount())
                        .setOnPurchaseAmount(javaObject.getOnPurchaseAmount())
                        .setOnTransferInAmount(javaObject.getOnTransferInAmount())
                        .setOnTransferOutAmount(javaObject.getOnTransferOutAmount())
                        .setNonDefectiveProfitkAmount(javaObject.getNonDefectiveProfitkAmount())
                        .setDefectiveProfitAmount(javaObject.getDefectiveProfitAmount())
                        .setDefectiveOutAmount(javaObject.getDefectiveOutAmount())
                        .setNonDefectiveLossAmount(javaObject.getNonDefectiveLossAmount())
                        .setDefectiveLossAmount(javaObject.getDefectiveLossAmount())
                        .setAdjustmentAccountTotalOut(javaObject.getAdjustmentAccountTotalOut())
                        .setAdjustmentAccountNonDefectiveOut(javaObject.getAdjustmentAccountNonDefectiveOut())
                        .setAdjustmentAccountDefectiveOut(javaObject.getAdjustmentAccountDefectiveOut())
                        .setModifyTotalAmountOut(javaObject.getModifyTotalAmountOut())
                        .setModifyNonDefectiveAmountOut(javaObject.getModifyNonDefectiveAmountOut())
                        .setModifyDefectiveAmountOut(javaObject.getModifyDefectiveAmountOut())
                        .setAdjustmentAccountTotalIn(javaObject.getAdjustmentAccountTotalIn())
                        .setAdjustmentAccountNonDefectiveIn(javaObject.getAdjustmentAccountNonDefectiveIn())
                        .setAdjustmentAccountDefectiveIn(javaObject.getAdjustmentAccountDefectiveIn())
                        .setModifyTotalAmountIn(javaObject.getModifyTotalAmountIn())
                        .setModifyNonDefectiveAmountIn(javaObject.getModifyNonDefectiveAmountIn())
                        .setModifyDefectiveAmountIn(javaObject.getModifyDefectiveAmountIn());

                respBuilder.addList(innerModel);
            }
            respBuilder.setPageNum(pageNumber);
            respBuilder.setPageSize(pageSize);
            respBuilder.setTotal(total);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get conditionalSelectInventoryLedger success: result.size():{}", rpcHeader.getTraceId(),resultList.size());


        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }
}
