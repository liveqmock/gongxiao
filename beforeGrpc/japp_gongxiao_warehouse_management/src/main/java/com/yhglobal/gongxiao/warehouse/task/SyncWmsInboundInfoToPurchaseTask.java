package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.purchase.bo.InboundNotificationBackItem;
import com.yhglobal.gongxiao.warehouse.service.InboundNotificationService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehousemanagement.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehousemanagement.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * 当WMS回告对应的订单是采购单时 调用该任务 将wms入库确认信息同步给采购模块
 */
public class SyncWmsInboundInfoToPurchaseTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToPurchaseTask.class);

    private RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private ProductService productService;

    private InboundOrder stockRecord;

    private InBoundOrderDao inBoundOrderDao;

    private String projectId;

    private String batchNo;

    private InboundNotificationService inboundNotificationService;

    public SyncWmsInboundInfoToPurchaseTask(RpcHeader rpcHeader, InboundNotificationService inboundNotificationService, ProductService productService, Data inStockConfirmRequest, InboundOrder stockRecord, InBoundOrderDao inBoundOrderDao, String projectId, String batchNo) {
        this.rpcHeader = rpcHeader;
        this.inboundNotificationService = inboundNotificationService;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.productService = productService;
        this.stockRecord = stockRecord;
        this.inBoundOrderDao = inBoundOrderDao;
        this.projectId = projectId;
        this.batchNo = batchNo;

    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start SyncWmsInboundInfoToPurchaseTask params: wmsInStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));
        try {
            String traceNo = stockRecord.getPurchaseOrderNo();
            String gongxiaoInboundOrderNo = inStockConfirmRequest.getOrderNo();
            List<InboundNotificationBackItem> inboundNotificationBackItemList = new ArrayList<>();
            List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();

            int realTotalQuantity = 0;
            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) {            //遍历入库确认信息
                InboundNotificationBackItem inboundNotificationBackItem = new InboundNotificationBackItem();
                ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader, stockInDetailDto.getCargoCode());
                inboundNotificationBackItem.setProductCode(productBasic.getProductCode());
                inboundNotificationBackItem.setInboundNotificationItemId(inStockConfirmRequest.getOrderNo());
                List<StocksQtyDto> stocksQtyDtoList = stockInDetailDto.getStocksQty();

                int perfectQuantity = 0;
                int imperfectQuantity = 0;
                for (StocksQtyDto stocksQtyDto : stocksQtyDtoList) {
                    realTotalQuantity += stocksQtyDto.getQuantity();
                    if (stocksQtyDto.getInventoryType() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()){   //gen
                        perfectQuantity += stocksQtyDto.getQuantity();   //良品+残品
                    }else {
                        imperfectQuantity += stocksQtyDto.getQuantity();   //残品
                    }
                }

                inboundNotificationBackItem.setInStockQuantity(perfectQuantity);
                inboundNotificationBackItem.setImperfectQuantity(imperfectQuantity);
                inboundNotificationBackItemList.add(inboundNotificationBackItem);

            }

            String uniqueNumber = DateTimeIdGenerator.nextId(BizNumberType.STOCK_UNIQUE_NO);
            InboundOrder inboundOrder = inBoundOrderDao.selectRecordByOrderNo(String.valueOf(stockRecord.getProjectId()), stockRecord.getGongxiaoInboundOrderNo());

            //判断是否完成收货
            if (inboundOrder.getTotalQuantity() > realTotalQuantity+stockRecord.getRealInStockQuantity()) {   //预约入库数量 > 刚入库数量+原来实际入库数量    部分收货
                logger.info("wms部分收货,通知采购模快更改数量,gongxiaoInboundOrderNo={},purchaseNo={}",gongxiaoInboundOrderNo,traceNo);
                inboundNotificationService.notifyPurchaseInbound(rpcHeader, String.valueOf(stockRecord.getProjectId()), traceNo, stockRecord.getGongxiaoInboundOrderNo(), inboundNotificationBackItemList, uniqueNumber);
                logger.info("通知采购模快更改数量成功,gongxiaoInboundOrderNo={},purchaseNo={}",gongxiaoInboundOrderNo,traceNo);
            } else {               //收货完成
                logger.info("wms收货完成,通知采购模块关闭订单,gongxiaoInboundOrderNo={},purchaseNo={}",gongxiaoInboundOrderNo,traceNo);
                inboundNotificationService.notifyPurchaseInbound(rpcHeader, String.valueOf(stockRecord.getProjectId()), traceNo, stockRecord.getGongxiaoInboundOrderNo(), inboundNotificationBackItemList, uniqueNumber);
                inboundNotificationService.transferClosedNotification(rpcHeader, projectId, traceNo, gongxiaoInboundOrderNo, batchNo, uniqueNumber);
                logger.info("通知采购模块关闭订单成功,gongxiaoInboundOrderNo={},purchaseNo={}",gongxiaoInboundOrderNo,traceNo);
            }

            logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToPurchaseTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }

    }
}
