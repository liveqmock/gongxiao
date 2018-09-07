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
import com.yhglobal.gongxiao.warehouse.dao.ManualInboudDao;
import com.yhglobal.gongxiao.warehouse.dao.ManualInboundItemDao;
import com.yhglobal.gongxiao.warehouse.model.ManualInboundOrder;
import com.yhglobal.gongxiao.warehouse.model.ManualInboundOrderIterm;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.instockconfirm.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 当WMS回告对应的订单是其他入库单时 调用该任务 将wms入库确认信息同步给其他入库
 */
public class SyncWmsInboundInfoToOtherTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToOtherTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;
    private Data inStockConfirmRequest;
    private ManualInboudDao manualInboudDao;
    private ManualInboundItemDao manualInboundItemDao;
    private String projectId;
    private String batchNo;
    private String warehouseId;

    public SyncWmsInboundInfoToOtherTask(GongxiaoRpc.RpcHeader rpcHeader, ManualInboudDao manualInboudDao, ManualInboundItemDao manualInboundItemDao, Data inStockConfirmRequest, String projectId, String batchNo, String warehouseId) {
        this.rpcHeader = rpcHeader;
        this.manualInboudDao = manualInboudDao;
        this.manualInboundItemDao = manualInboundItemDao;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.projectId = projectId;
        this.batchNo = batchNo;
        this.warehouseId = warehouseId;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsInboundInfoToOtherTask params: wmsInStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));
        String gongxiaoInboundOrderNo = inStockConfirmRequest.getOrderNo();
        ManualInboundOrder manualInboundOrder = new ManualInboundOrder();
        List<ManualInboundOrderIterm> manualInboundOrderItermList = new ArrayList<>();
        List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();
        boolean isFinish = true;   //判断是否完成
        int totalQuantity = 0;
        try {
            //调用基础模块的商品的grpc查询项目信息
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) {          //遍历wms入库单确认信息
                ManualInboundOrderIterm manualInboundOrderIterm = new ManualInboundOrderIterm();
               //根据wmscode查询
                ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProductWmsCode(stockInDetailDto.getCargoCode()).build();
                ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                manualInboundOrderIterm.setProductCode(productBusiness.getProductModel());
                manualInboundOrderIterm.setOrderNo(gongxiaoInboundOrderNo);
                List<StocksQtyDto> stocksQtyDtoList = stockInDetailDto.getStocksQty();
                int inStockQuantity = 0;
                for (StocksQtyDto stocksQtyDto : stocksQtyDtoList) {            //根据库存品质分为良品和不良品
                    inStockQuantity += stocksQtyDto.getQuantity();   //良品+残品
                }
                manualInboundOrderIterm.setRealQuantity(inStockQuantity);
                manualInboundOrderIterm.setStatus(stockInDetailDto.getIsCompleted() ? 1 : 0);
                manualInboundOrderItermList.add(manualInboundOrderIterm);
                isFinish = isFinish && stockInDetailDto.getIsCompleted();
                totalQuantity += inStockQuantity;
                manualInboundItemDao.updateManualItemInfo(manualInboundOrderIterm);
            }
            manualInboundOrder.setOrderNo(gongxiaoInboundOrderNo);
            manualInboundOrder.setRealQuantity(totalQuantity);
            manualInboundOrder.setStatus(isFinish ? 1 : 0);

            manualInboudDao.updateManualInboundInfo(manualInboundOrder);

            if (isFinish) {
                //通知EAS
                notificationEas(inStockConfirmRequest);
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }

        logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToOtherTask.run() success", rpcHeader.getTraceId());
    }


    private void notificationEas(Data inStockConfirmRequest) {
        logger.info("#traceId={}# [IN][run] start to NotificationEasOutboundTask.notificationEas() params: createOutboundItemInfoList={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));
        try {
            //调用基础模拟块的项目的grpc差项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(String.valueOf(projectId)).build();
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
            List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();
            List<OtherOutWareItem> otherOutWareItemList = new ArrayList<>();

            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) {     //遍历入库了多少种类型的商品

                //调用基础模块的商品的grpc查询项目信息
                ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                for (StocksQtyDto record : stockInDetailDto.getStocksQty()) {
                    quantity += record.getQuantity();
                }
                ManualInboundOrderIterm manualInboundOrderIterm = manualInboundItemDao.selectRecordByGonxiaoNumAndProductCode(inStockConfirmRequest.getOrderNo(), stockInDetailDto.getCargoCode());  //查找原来的其他出库单
                purchasePrice = manualInboundOrderIterm.getPurchasePrice();
                OtherOutWareItem record = new OtherOutWareItem();
                record.setNumber(quantity);
                record.setLocationId("01");                         //库位(良品)
                record.setLot(batchNo);

                //根据wmscode查询
                ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProductWmsCode(manualInboundOrderIterm.getProductCode()).build();
                ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                record.setMaterialId(productBusiness.getEasCode());            //物料号
                record.setTaxPrice(manualInboundOrderIterm.getGuidePrice());
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
            if (success) {
                String orderNumber = easResult.getOrderNumber();
                manualInboudDao.updateEasFlagInfo(orderNumber);
            }

            logger.info("#traceId={}# [OUT] get NotificationEasOutboundTask.notificationEas() success", rpcHeader.getTraceId());

        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);

        }

    }

}

