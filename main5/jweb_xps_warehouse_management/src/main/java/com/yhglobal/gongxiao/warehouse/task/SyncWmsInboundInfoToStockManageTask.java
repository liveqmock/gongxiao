package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehouse.service.ModifyInboundService;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.XpsWarehouseNotifyManager;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.InboundNotificationBackItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 收到WMS的入库回告后 通过该任务 更新<仓储模块>中入库汇总单和明细单，以及通知EAS，通知采购模块
 */
public class SyncWmsInboundInfoToStockManageTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToStockManageTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private String projectId;

    private InboundOrder oldInboundOrder;

    private WarehouseConfig warehouseConfig;

    private ModifyInboundService modifyInboundService;

    public SyncWmsInboundInfoToStockManageTask(GongxiaoRpc.RpcHeader rpcHeader, Data inStockConfirmRequest, String projectId, InboundOrder oldInboundOrder,WarehouseConfig warehouseConfig,ModifyInboundService modifyInboundService) {
        this.rpcHeader = rpcHeader;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.projectId = projectId;
        this.oldInboundOrder = oldInboundOrder;
        this.warehouseConfig = warehouseConfig;
        this.modifyInboundService = modifyInboundService;
    }

    @Override
    public void run() {

        logger.info("#traceId={}# [IN][run] start SyncWmsInboundInfoToStockManageTask,params: inStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));

        List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();
        try {
            int totalQuantity = 0; //当次入库通知的总可用数量(等于良品数量)
            int totalImperfectQuantity = 0; //当次入库通知的总入库数量
            int totalInStockQuantity = 0; //当次入库通知的总入库数量
            int totalInTransitQuantity = 0; //当次入库通知的总在途数量
            //注: WMS的反馈中 有IsCompleted字段标记入库单是否已完成 但这个字段不准确，不能逻辑依赖它
           //调用基础模块商品的grpc
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) { //遍历wms入库确认信息

                int imperfectQuantity = 0; //当次入库确认的残次品数总和
                int inStockQuantity = 0; //当次入库确认的良品数总和
                int inTransitQuantity = 0; //当次入库确认的在途数总和
                int itemQuantity = 0; //当次入库确认的商品数总和

                //根据wmscode查询
                ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(projectId)).setProductWmsCode(stockInDetailDto.getCargoCode()).build();
                ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
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
                            logger.error("#traceId={}# unknown WmsInventoryType: {}", rpcHeader.getTraceId(), wmsInventoryType);
                    }
                }
                totalQuantity += itemQuantity;
                totalImperfectQuantity += imperfectQuantity;
                totalInStockQuantity += inStockQuantity;
                totalInTransitQuantity += inTransitQuantity;

                //更新明细表
                modifyInboundService.modifyInboundItermByWms(rpcHeader, projectId, oldInboundOrder.getGongxiaoInboundOrderNo(), productBusiness.getProductModel(), imperfectQuantity, inStockQuantity, inTransitQuantity, itemQuantity);
            }

            //注: 汇总表是否收货完成在更新DB数量时实时判定，并在更新成功后返回
            boolean isFinished = modifyInboundService.modifyInboundOderByWms(rpcHeader, projectId, oldInboundOrder.getGongxiaoInboundOrderNo(), totalQuantity, totalImperfectQuantity, totalInStockQuantity, totalInTransitQuantity);

            //如果是采购入库通知采购模块
            if (inStockConfirmRequest.getOrderNo().contains(BizNumberType.STOCK_POIN_ORDER_NO.getPrefix())){
                notifyPurchase(isFinished);
            }

            logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToStockManageTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
    }

    private void notifyPurchase(boolean isFinished){
        try {
            String traceNo = oldInboundOrder.getPurchaseOrderNo();
            String gongxiaoInboundOrderNo = inStockConfirmRequest.getOrderNo();
            List<InboundNotificationBackItem> inboundNotificationBackItemList = new ArrayList<>();
            int realTotalQuantity = 0;
            List<StockInDetailDto> stockInDetailDtoList = inStockConfirmRequest.getStockInDetails();
            //调用基础模块的商品grpc
            ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
            for (StockInDetailDto stockInDetailDto : stockInDetailDtoList) {            //遍历入库确认信息
                InboundNotificationBackItem inboundNotificationBackItem = new InboundNotificationBackItem();
              //根据wmscode查询
                ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(Long.parseLong(projectId)).setProductWmsCode(stockInDetailDto.getCargoCode()).build();
                ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                inboundNotificationBackItem.setProductCode(productBusiness.getProductModel());
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

            String projectPrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));
            String uniqueNumber = DateTimeIdGenerator.nextId(projectPrefix,BizNumberType.STOCK_UNIQUE_NO);

            //通知采购模块,调用基础模块的sourceChannel服务获取渠道的url
            String channelId = null;
            if (oldInboundOrder.getGongxiaoInboundOrderNo().contains("shaver")){
                channelId = WmsSourceChannel.CHANNEL_SHAVER.getChannelId();
            }else if (oldInboundOrder.getGongxiaoInboundOrderNo().contains("PHTM")){
                channelId = WmsSourceChannel.CHANNEL_TMALL.getChannelId();
            }else if(oldInboundOrder.getGongxiaoInboundOrderNo().contains("JMGO")){
                channelId = WmsSourceChannel.CHANNEL_JMGO.getChannelId();
            }else {
                channelId = WmsSourceChannel.CHANNEL_TUANGOU.getChannelId();
            }
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId(channelId).build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

            //通知采购模块wms已入库
            XpsWarehouseNotifyManager.notifyPurchaseInbound(sourceChannel.getXpsWarehouseNotifyUrl(), String.valueOf(oldInboundOrder.getProjectId()), traceNo, oldInboundOrder.getGongxiaoInboundOrderNo(), inboundNotificationBackItemList, uniqueNumber);
            if (isFinished) { //若是收货完成
                logger.info("通知采购模块关闭订单成功,gongxiaoInboundOrderNo={},purchaseNo={}",gongxiaoInboundOrderNo,traceNo);
                XpsWarehouseNotifyManager.transferClosedNotification(sourceChannel.getXpsWarehouseNotifyUrl(), projectId, traceNo, gongxiaoInboundOrderNo, oldInboundOrder.getBatchNo(), uniqueNumber);
            }

            logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToPurchaseTask.run() success", rpcHeader.getTraceId());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
    }

}
