package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.eas.model.OtherOutWare;
import com.yhglobal.gongxiao.eas.model.OtherOutWareItem;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.warehouse.dao.ManualOutboundDao;
import com.yhglobal.gongxiao.warehouse.dao.ManualOutboundItemDao;
import com.yhglobal.gongxiao.warehouse.model.ManualOutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.ManualOutboundOrderItem;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.outstockconfirm.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 当WMS回告对应的订单是其他出库单时 调用该任务 将wms入库确认信息同步给其他出库
 */
public class SyncWmsOutboundInfoToOtherTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToOtherTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;
    private Data outStockConfirmRequest;
    private ManualOutboundDao manualOutboundDao;
    private ManualOutboundItemDao manualOutboundItemDao;
    private String projectId;
    private String batchNo;
    private String warehouseId;
    private OutboundOrder olderRecord;

    public SyncWmsOutboundInfoToOtherTask(GongxiaoRpc.RpcHeader rpcHeader, ManualOutboundDao manualOutboundDao, ManualOutboundItemDao manualOutboundItemDao, Data outStockConfirmRequest, String projectId, String batchNo, String warehouseId, OutboundOrder olderRecord) {
        this.rpcHeader = rpcHeader;
        this.manualOutboundDao = manualOutboundDao;
        this.manualOutboundItemDao = manualOutboundItemDao;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.projectId = projectId;
        this.batchNo = batchNo;
        this.warehouseId = warehouseId;
        this.olderRecord = olderRecord;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsOutboundInfoToOtherTask.run() params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));

        boolean isFinish = true;
        ManualOutboundOrder manualOutboundOrder = new ManualOutboundOrder();
        int totalQuantity = 0;
        try {
            for (StockOutOrderConfirmOrderItemDto record : outStockConfirmRequest.getOrderItems()) {
                ManualOutboundOrderItem itemrcord = new ManualOutboundOrderItem();
                itemrcord.setStatus(record.isCompleted() ? 1 : 0);
                itemrcord.setProductCode(record.getItemCode());
                int quantity = 0;
                for (StockOutOrderConfirmItemDto newrecord : record.getItems()) {
                    quantity += newrecord.getQuantity();
                }
                isFinish = isFinish && record.isCompleted();
                totalQuantity += quantity;
                itemrcord.setRealQuantity(quantity);
                manualOutboundItemDao.updateManualOutboundInfo(itemrcord);

            }
            manualOutboundOrder.setRealQuantity(totalQuantity);
            manualOutboundOrder.setStatus(isFinish ? 1 : 0);

            if (isFinish){
                notificationEas(outStockConfirmRequest);
            }
            manualOutboundDao.updateManualOutboundInfo(manualOutboundOrder);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
        logger.info("#traceId={}# [OUT] get SyncWmsOutboundInfoToOtherTask.run() success", rpcHeader.getTraceId());

    }

    private void notificationEas(Data outStockConfirmRequest){
        logger.info("#traceId={}# [IN][run] start to SyncWmsOutboundInfoToOtherTask.notificationEas() params:  outStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));

        //调用基础模块的项目的grpc查询项目信息
        ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
        ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
        ProjectStructure.Project project = rpcResponse.getProject();

        //调用基础模块的仓库grpc服务查仓库编码
        WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseService = GlobalRpcStubStore.getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
        WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder().setRpcHeader(rpcHeader).setWarehouseId(warehouseId).build();
        WarehouseStructure.GetWarehouseByIdResp warehouseByIdResp = warehouseService.getWarehouseById(getWarehouseByIdReq);
        WarehouseStructure.Warehouse warehouse = warehouseByIdResp.getWarehouse();

        OtherOutWare otherOutWare = new OtherOutWare();
        otherOutWare.setCreatorId(rpcHeader.getUid());
        otherOutWare.setProjectId(project.getEasProjectCode());

        int quantity = 0;
        double purchasePrice = 0;

        List<StockOutOrderConfirmOrderItemDto> orderItemDtos = outStockConfirmRequest.getOrderItems();
        List<OtherOutWareItem> otherOutWareItemList = new ArrayList<>();
        try {
            for (StockOutOrderConfirmOrderItemDto itemInfo : orderItemDtos) {

                for (StockOutOrderConfirmItemDto record : itemInfo.getItems()){
                    quantity += record.getQuantity();

                }
                ManualOutboundOrderItem manualOutboundOrderItem = manualOutboundItemDao.selectRecordByGonxiaoNumAndProductCode(outStockConfirmRequest.getOrderNo(),itemInfo.getItemCode());
                purchasePrice = manualOutboundOrderItem.getWholesalePriceDouble();
                OtherOutWareItem record = new OtherOutWareItem();
                record.setNumber(quantity);
                record.setLocationId("01");                         //库位(良品)
                record.setLot(batchNo);

                //调用基础模块的商品的grpc查询项目信息
                ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                ProductStructure.GetProductDetailByModelReq getProductDetailByModelReq = ProductStructure.GetProductDetailByModelReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(projectId)).setProductModel(manualOutboundOrderItem.getProductId()).build();
                ProductStructure.GetProductDetailByModelResp response = productService.getProductDetailByModel(getProductDetailByModelReq);
                ProductStructure.ProductBasicDetail productBasicDetail = response.getProductBasicDetail();
                record.setMaterialId(productBasicDetail.getEasCode());            //物料号
                record.setTaxPrice(manualOutboundOrderItem.getGuidePrice());
                record.setUnit("GE");
                record.setSourceBillId("wEQAAADv8e8xcb+t");
                record.setWarehouseId(warehouse.getEasWarehouseCode());
                otherOutWareItemList.add(record);
            }

            otherOutWare.setNumber(quantity);
            otherOutWare.setTotalTaxAmount(quantity * purchasePrice);

            String result = EasUtil.sendOtherOutWare2Eas(otherOutWare, otherOutWareItemList);
            logger.info("通知EAS其他出库的结果: {}", result);
            EasResult easResult = JSON.parseObject(result, new TypeReference<EasResult>() {
            });

            boolean success = easResult.isSuccess();
            if (success){
                String orderNumber = easResult.getOrderNumber();
                manualOutboundDao.updateEasFlagInfo(orderNumber);
            }

            logger.info("#traceId={}# [OUT] get NotificationEasOutboundTask.run() success", rpcHeader.getTraceId());

        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
        }

    }
}
