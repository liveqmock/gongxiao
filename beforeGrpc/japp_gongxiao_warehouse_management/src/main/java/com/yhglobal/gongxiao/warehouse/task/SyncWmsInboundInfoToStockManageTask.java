package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.purchase.bo.InboundNotificationBackItem;
import com.yhglobal.gongxiao.warehouse.service.InboundNotificationService;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 收到WMS的入库回告后 通过该任务 更新<仓储模块>中入库汇总单和明细单，以及通知EAS，通知采购模块
 */
public class SyncWmsInboundInfoToStockManageTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToStockManageTask.class);

    private RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private String projectId;

    private String purchaseOrderNo;

    private ApplicationContext applicationContext;

    private ProductService productService;

    private InboundOrder oldInboundOrder;

    private InboundNotificationService inboundNotificationService;


    public SyncWmsInboundInfoToStockManageTask(RpcHeader rpcHeader, ApplicationContext applicationContext, Data inStockConfirmRequest, String projectId, String purchaseOrderNo, ProductService productService, InboundOrder oldInboundOrder,InboundNotificationService inboundNotificationService) {
        this.rpcHeader = rpcHeader;
        this.applicationContext = applicationContext;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.projectId = projectId;
        this.purchaseOrderNo = purchaseOrderNo;
        this.productService = productService;
        this.oldInboundOrder = oldInboundOrder;
        this.inboundNotificationService = inboundNotificationService;
    }

    @Override
    public void run() {

        logger.info("#traceId={}# [IN][run] start SyncWmsInboundInfoToStockManageTask,params: inStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));

        List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();
        InboundService inboundService = applicationContext.getBean("inboundServiceImpl", InboundService.class);
        try {
            int totalQuantity = 0; //当次入库通知的总可用数量(等于良品数量)
            int totalImperfectQuantity = 0; //当次入库通知的总入库数量
            int totalInStockQuantity = 0; //当次入库通知的总入库数量
            int totalInTransitQuantity = 0; //当次入库通知的总在途数量
            //注: WMS的反馈中 有IsCompleted字段标记入库单是否已完成 但这个字段不准确，不能逻辑依赖它
            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) { //遍历wms入库确认信息

                int imperfectQuantity = 0; //当次入库确认的残次品数总和
                int inStockQuantity = 0; //当次入库确认的良品数总和
                int inTransitQuantity = 0; //当次入库确认的在途数总和
                int itemQuantity = 0; //当次入库确认的商品数总和

                ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader, stockInDetailDto.getCargoCode());   //wmscode转成分销商品编码
                for (StocksQtyDto stocksQtyDto : stockInDetailDto.getStocksQty()) {
                    itemQuantity += stocksQtyDto.getQuantity();
                    WmsInventoryType wmsInventoryType = WmsInventoryType.getInventoryTypeByNumValue(stocksQtyDto.getInventoryType()); //获取wms的出库类型
                    switch (wmsInventoryType) {
                        case COMMON_GOOD_MACHINE: //可销售库存
                            inStockQuantity = stocksQtyDto.getQuantity();
                            break;
                        case DEFECTIVE:
                            imperfectQuantity += stocksQtyDto.getQuantity();
                            break;
                        case ENGINE_DAMAGE:
                            imperfectQuantity += stocksQtyDto.getQuantity();
                            break;
                        case BOX_DAMAGE:
                            imperfectQuantity += stocksQtyDto.getQuantity();
                            break;
                        case FROZEN_STOCK:
                            imperfectQuantity = stocksQtyDto.getQuantity();
                            break;
                        case TRANSPORTATION_INVENTORY:
                            inTransitQuantity = stocksQtyDto.getQuantity();
                        default:
                            logger.error("#traceId={}# unknown WmsInventoryType: {}", rpcHeader.traceId, wmsInventoryType);
                    }
                }
                totalQuantity += itemQuantity;
                totalImperfectQuantity += imperfectQuantity;
                totalInStockQuantity += inStockQuantity;
                totalInTransitQuantity += inTransitQuantity;

                //更新明细表
                inboundService.modifyInboundItermByWms(rpcHeader, oldInboundOrder.getGongxiaoInboundOrderNo(), productBasic.getProductCode(), imperfectQuantity, inStockQuantity, inTransitQuantity, itemQuantity);
            }

            //注: 汇总表是否收货完成在更新DB数量时实时判定，并在更新成功后返回
            boolean isFinished = inboundService.modifyInboundOderByWms(rpcHeader, oldInboundOrder.getGongxiaoInboundOrderNo(), totalQuantity, totalImperfectQuantity, totalInStockQuantity, totalInTransitQuantity);

            //如果是采购入库通知采购模块
            if (inStockConfirmRequest.getOrderNo().startsWith(BizNumberType.STOCK_POIN_ORDER_NO.getPrefix())){
                notifyPurchase(isFinished);
            }

            logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToStockManageTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
    }

    private void notifyPurchase(boolean isFinished){
        try {
            String traceNo = oldInboundOrder.getPurchaseOrderNo();
            String gongxiaoInboundOrderNo = inStockConfirmRequest.getOrderNo();
            List<InboundNotificationBackItem> inboundNotificationBackItemList = new ArrayList<>();
            int realTotalQuantity = 0;
            List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();
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
                    if (stocksQtyDto.getInventoryType() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()){   //根据入库的库存类型分为“良品”和“不良品”
                        perfectQuantity += stocksQtyDto.getQuantity();   //良品
                    }else {
                        imperfectQuantity += stocksQtyDto.getQuantity();   //残品
                    }
                }

                inboundNotificationBackItem.setInStockQuantity(perfectQuantity);
                inboundNotificationBackItem.setImperfectQuantity(imperfectQuantity);
                inboundNotificationBackItemList.add(inboundNotificationBackItem);

            }

            String uniqueNumber = DateTimeIdGenerator.nextId(BizNumberType.STOCK_UNIQUE_NO);
            inboundNotificationService.notifyPurchaseInbound(rpcHeader, String.valueOf(oldInboundOrder.getProjectId()), traceNo, oldInboundOrder.getGongxiaoInboundOrderNo(), inboundNotificationBackItemList, uniqueNumber);

            if (isFinished) { //若是收货完成
                logger.info("通知采购模块关闭订单成功,gongxiaoInboundOrderNo={},purchaseNo={}",gongxiaoInboundOrderNo,traceNo);
                inboundNotificationService.transferClosedNotification(rpcHeader, projectId, traceNo, gongxiaoInboundOrderNo, oldInboundOrder.getBatchNo(), uniqueNumber);
            }

            logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToPurchaseTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
    }

}
