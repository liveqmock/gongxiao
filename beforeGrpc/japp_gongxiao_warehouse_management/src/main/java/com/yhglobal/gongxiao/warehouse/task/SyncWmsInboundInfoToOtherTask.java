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
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.warehousemanagement.dao.ManualInboudDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.ManualInboundItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.CreateOutboundItemInfo;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualInboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.ManualInboundOrderIterm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 当WMS回告对应的订单是其他入库单时 调用该任务 将wms入库确认信息同步给其他入库
 */
public class SyncWmsInboundInfoToOtherTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToOtherTask.class);

    private RpcHeader rpcHeader;
    private Data inStockConfirmRequest;
    private ManualInboudDao manualInboudDao;
    private ManualInboundItemDao manualInboundItemDao;
    private String projectId;
    private String batchNo;
    private String warehouseId;
    private ProjectService projectService;
    private WarehouseService warehouseService;
    private ProductService productService;

    public SyncWmsInboundInfoToOtherTask(RpcHeader rpcHeader, ManualInboudDao manualInboudDao, ManualInboundItemDao manualInboundItemDao, Data inStockConfirmRequest, String projectId, String batchNo, String warehouseId, ProjectService projectService, ProductService productService, WarehouseService warehouseService) {
        this.rpcHeader = rpcHeader;
        this.manualInboudDao = manualInboudDao;
        this.manualInboundItemDao = manualInboundItemDao;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.projectId = projectId;
        this.batchNo = batchNo;
        this.warehouseId = warehouseId;
        this.projectService = projectService;
        this.productService = productService;
        this.warehouseService = warehouseService;

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
            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) {          //遍历wms入库单确认信息
                ManualInboundOrderIterm manualInboundOrderIterm = new ManualInboundOrderIterm();
                ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader, stockInDetailDto.getCargoCode());
                manualInboundOrderIterm.setProductCode(productBasic.getProductCode());
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
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }

        logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToOtherTask.run() success", rpcHeader.getTraceId());
    }


    private void notificationEas(Data inStockConfirmRequest) {
        logger.info("#traceId={}# [IN][run] start to NotificationEasOutboundTask.notificationEas() params: createOutboundItemInfoList={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));
        try {
            Project project = projectService.getByProjectId(rpcHeader, projectId);
            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, warehouseId);
            OtherOutWare otherOutWare = new OtherOutWare();
            otherOutWare.setCreatorId(rpcHeader.getUid());
            otherOutWare.setProjectId(project.getEasProjectCode());

            int quantity = 0;
            double purchasePrice = 0;
            List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();
            List<OtherOutWareItem> otherOutWareItemList = new ArrayList<>();

            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) {     //遍历入库了多少种类型的商品

                for (StocksQtyDto record : stockInDetailDto.getStocksQty()) {
                    quantity += record.getQuantity();
                }
                ManualInboundOrderIterm manualInboundOrderIterm = manualInboundItemDao.selectRecordByGonxiaoNumAndProductCode(inStockConfirmRequest.getOrderNo(), stockInDetailDto.getCargoCode());  //查找原来的其他出库单
                purchasePrice = manualInboundOrderIterm.getPurchasePrice();
                OtherOutWareItem record = new OtherOutWareItem();
                record.setNumber(quantity);
                record.setLocationId("01");                         //库位(良品)
                record.setLot(batchNo);
                ProductBasic productBasic = productService.getByProductId(rpcHeader, manualInboundOrderIterm.getProductId());
                record.setMaterialId(productBasic.getEasCode());            //物料号
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

            logger.info("#traceId={}# [OUT] get NotificationEasOutboundTask.notificationEas() success", rpcHeader.traceId);

        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);

        }

    }

}

