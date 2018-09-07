package com.yhglobal.gongxiao.inventory.inventoryflow;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.inventory.dao.ProductInventoryFlowDao;
import com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryFlowServiceGrpc;
import com.yhglobal.gongxiao.inventory.inventoryflow.microservice.InventoryflowStructure;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.inventory.util.TablePrefixUtil;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsOrderType;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class InventoryFlowServiceImpl extends InventoryFlowServiceGrpc.InventoryFlowServiceImplBase {
    private Logger logger = LoggerFactory.getLogger(InventoryFlowServiceImpl.class);

    @Autowired
    ProductInventoryFlowDao productInventoryFlowDao;

    @Override
    public void getInventoryFlow(InventoryflowStructure.GetInventoryFlowRequest request, StreamObserver<InventoryflowStructure.GetInventoryFlowResponse> responseObserver) {
        InventoryflowStructure.GetInventoryFlowResponse response;
        InventoryflowStructure.GetInventoryFlowResponse.Builder respBuilder = InventoryflowStructure.GetInventoryFlowResponse.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String projectId = request.getProjectId();
        String productCode = request.getProductModel();
        String productName = request.getProductName();
        String warehouseName = request.getWarehouseName();
        String inventoryFlowId = request.getInventoryFlowId();
        String startDate = request.getStartDate();
        String endDate = request.getEndDate();
        int pageNumber = request.getPageNumber();
        int pageSize = request.getPageSize();
        try {
            logger.info("#traceId={}# [IN][conditionalSelectInventoryFlow]: projectId={}, productModel={}, productName={}, warehouseId={}, " +
                    "inventoryFlowId={}, beginDate={}, endDate={}", rpcHeader.getTraceId(), projectId, productCode, warehouseName, inventoryFlowId, startDate, endDate);

            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            //启动分页
            PageHelper.startPage(pageNumber, pageSize);

            List<ProductInventoryFlow> productInventoryFlowList = productInventoryFlowDao.selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate(projectId, productName, productCode, inventoryFlowId, warehouseName, startDate, endDate, projectPrefix);
            PageInfo<ProductInventoryFlow> inventoryLedgerPageInfo = new PageInfo<>(productInventoryFlowList);
            long total = inventoryLedgerPageInfo.getTotal();

            for (ProductInventoryFlow record : productInventoryFlowList) {
                int imperfectQuantity = 0;
                int transactionAmount = record.getTransactionAmount();
                InventoryflowStructure.ProductInventoryFlowShow.Builder innerModel = InventoryflowStructure.ProductInventoryFlowShow.newBuilder()
                        .setOrderNo(record.getOrderNo())
                        .setBatchNo(record.getBatchNo())
                        .setRelatedOrderNo(record.getRelatedOrderId())
                        .setAmountAfterTransaction(record.getAmountAfterTransaction())
                        .setAmountBeforeTransaction(record.getAmountBeforeTransaction())
                        .setInventoryFlowId(record.getInventoryFlowId());

                if (WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType() == record.getInventoryFlowType()) {
                    innerModel.setInventoryFlowType("普通好机");
                    innerModel.setPerfectQuantity(record.getTransactionAmount());
                } else if (WmsInventoryType.DEFECTIVE.getInventoryType() == record.getInventoryFlowType()) {
                    innerModel.setInventoryFlowType("残次");
                    imperfectQuantity += record.getTransactionAmount();
                } else if (WmsInventoryType.ENGINE_DAMAGE.getInventoryType() == record.getInventoryFlowType()) {
                    innerModel.setInventoryFlowType("机损");
                    imperfectQuantity += record.getTransactionAmount();
                } else if (WmsInventoryType.BOX_DAMAGE.getInventoryType() == record.getInventoryFlowType()) {
                    innerModel.setInventoryFlowType("箱损");
                    imperfectQuantity += record.getTransactionAmount();
                } else if (WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType() == record.getInventoryFlowType()) {  //在途库存
                    innerModel.setInventoryFlowType("在途");
                    imperfectQuantity += record.getTransactionAmount();
                } else if (WmsInventoryType.FROZEN_STOCK.getInventoryType() == record.getInventoryFlowType()) {     //冻结库存
                    innerModel.setInventoryFlowType("冻结");
                    imperfectQuantity += record.getTransactionAmount();
                } else {
                    innerModel.setInventoryFlowType("不详");
                }
                innerModel.setImperfectQuantity(imperfectQuantity);
                if (WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("采购入库");
                } else if (WmsOrderType.INBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("调拨入库");
                } else if (WmsOrderType.INBOUND_FOR_RETURNING_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("退货入库");
                } else if (WmsOrderType.INBOUND_FOR_OTHER_REASON.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("其他入库");
                } else if (WmsOrderType.OUTBOUND_FOR_TRANSFERRING_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("调拨出库");
                    transactionAmount = -1 * transactionAmount;
                } else if (WmsOrderType.OUTBOUND_FOR_RETURN_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("采购退货出库");
                    transactionAmount = -1 * transactionAmount;
                } else if (WmsOrderType.OUTBOUND_FOR_B2B_SELLING_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("普通出库");
                    transactionAmount = -1 * transactionAmount;
                } else if (WmsOrderType.OUTBOUND_FOR_EXCHANGING_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("换货出库");
                    transactionAmount = -1 * transactionAmount;
                } else if (WmsOrderType.OUTBOUND_FOR_B2C_SELLING_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("交易出库");
                    transactionAmount = -1 * transactionAmount;
                } else if (WmsOrderType.OUTBOUND_FOR_DISCARDING_PRODUCT.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("报废出库");
                    transactionAmount = -1 * transactionAmount;
                } else if (WmsOrderType.OUTBOUND_FOR_OTHER_REASON.getInboundOrderCode() == record.getOrderType()) {
                    innerModel.setOrderType("其他出库");
                    transactionAmount = -1 * transactionAmount;
                } else {
                    innerModel.setOrderType("不详");
                }
                innerModel.setTransactionAmount(transactionAmount);
                innerModel.setProductCode(record.getProductCode());
                innerModel.setProductId((record.getProductId()==""|| record.getProductId()==null)?"":record.getProductId());
                innerModel.setProductModel(record.getProductModel());
                innerModel.setProductName(record.getProductName());
                innerModel.setProjectId(record.getProjectId());
                innerModel.setWarehouseId(record.getWarehouseId());
                innerModel.setWarehouseName(record.getWarehouseName());
                innerModel.setCreateTime(record.getTransactionTime().getTime());

                respBuilder.addList(innerModel);
            }
            respBuilder.setPageNum(pageNumber);
            respBuilder.setPageSize(pageSize);
            respBuilder.setTotal(total);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("#traceId={}# [OUT] get conditionalSelectInventoryFlow success: result.size():{}", rpcHeader.getTraceId(), productInventoryFlowList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }
}
