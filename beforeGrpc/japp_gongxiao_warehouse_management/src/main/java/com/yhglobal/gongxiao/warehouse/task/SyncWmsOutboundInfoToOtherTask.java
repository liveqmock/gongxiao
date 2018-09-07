package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.eas.OtherOutWare;
import com.yhglobal.gongxiao.eas.OtherOutWareItem;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.purchase.bo.OutboundNotificationBackItem;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehousemanagement.dao.ManualInboudDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.ManualInboundItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.ManualOutboundDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.ManualOutboundItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.CreateOutboundItemInfo;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualOutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualOutboundOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 当WMS回告对应的订单是其他出库单时 调用该任务 将wms入库确认信息同步给其他出库
 */
public class SyncWmsOutboundInfoToOtherTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SyncWmsOutboundInfoToOtherTask.class);

    private RpcHeader rpcHeader;
    private Data outStockConfirmRequest;
    private ManualOutboundDao manualOutboundDao;
    private ManualOutboundItemDao manualOutboundItemDao;
    private String projectId;
    private String batchNo;
    private String warehouseId;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private ProductService productService;
    private OutboundOrder olderRecord;

    public SyncWmsOutboundInfoToOtherTask(RpcHeader rpcHeader, ManualOutboundDao manualOutboundDao, ManualOutboundItemDao manualOutboundItemDao, Data outStockConfirmRequest,String projectId,String batchNo,String warehouseId,ProjectService projectService,ProductService productService,WarehouseService warehouseService,OutboundOrder olderRecord) {
        this.rpcHeader = rpcHeader;
        this.manualOutboundDao = manualOutboundDao;
        this.manualOutboundItemDao = manualOutboundItemDao;
        this.outStockConfirmRequest = outStockConfirmRequest;
        this.projectId = projectId;
        this.batchNo = batchNo;
        this.warehouseId = warehouseId;
        this.projectService = projectService;
        this.productService = productService;
        this.warehouseService = warehouseService;
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
//                itemrcord.setOrderNo();
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
//            manualOutboundOrder.setOrderNo();
            manualOutboundOrder.setRealQuantity(totalQuantity);
            manualOutboundOrder.setStatus(isFinish ? 1 : 0);

            if (isFinish){
                notificationEas(outStockConfirmRequest);
            }
            manualOutboundDao.updateManualOutboundInfo(manualOutboundOrder);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
        logger.info("#traceId={}# [OUT] get SyncWmsOutboundInfoToOtherTask.run() success", rpcHeader.getTraceId());

    }

    private void notificationEas(Data outStockConfirmRequest){
        logger.info("#traceId={}# [IN][run] start to SyncWmsOutboundInfoToOtherTask.notificationEas() params:  outStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(outStockConfirmRequest));
        Project project = projectService.getByProjectId(rpcHeader, projectId);
        Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, warehouseId);
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
                ProductBasic productBasic = productService.getByProductId(rpcHeader, manualOutboundOrderItem.getProductId());
                record.setMaterialId(productBasic.getEasCode());            //物料号
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

            logger.info("#traceId={}# [OUT] get NotificationEasOutboundTask.run() success", rpcHeader.traceId);

        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
        }

    }
}
