package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehouse.config.WarehouseConfig;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.XpsWarehouseNotifyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 当WMS回告对应的订单是销售单时 调用该任务 将wms入库确认信息同步给销售模块
 */
public class SyncWmsInboundInfoToSalesTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToSalesTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private WarehouseConfig warehouseConfig;

    private InboundOrder inboundOrder;

    public SyncWmsInboundInfoToSalesTask(GongxiaoRpc.RpcHeader rpcHeader, Data inStockConfirmRequest,WarehouseConfig warehouseConfig,InboundOrder inboundOrder) {
        this.rpcHeader = rpcHeader;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.warehouseConfig = warehouseConfig;
        this.inboundOrder = inboundOrder;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start to SyncWmsInboundInfoToSalesTask params: wmsOutStockConfirmRequest={}", rpcHeader.getTraceId(), JSON.toJSONString(inStockConfirmRequest));
        try {
            int inStockQuantity = 0;
            for (StockInDetailDto record : inStockConfirmRequest.getStockInDetails()) {     //遍历wms入库确认信息
                String inboundOrderNo = null;
                String traceNo = "wms";
                String productCode = null;
                String productName = null;
                String productUnit = null;
                //调用基础模块的商品grpc
                ProductServiceGrpc.ProductServiceBlockingStub productService = GlobalRpcStubStore.getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);
                for (StocksQtyDto newRecord : record.getStocksQty()) {
                    //根据wmscode查询
                    ProductStructure.GetByWmsProductCodeReq getByWmsProductCodeReq = ProductStructure.GetByWmsProductCodeReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(inboundOrder.getProjectId()).setProductWmsCode(record.getCargoCode()).build();
                    ProductStructure.GetByWmsProductCodeResp response = productService.getByWmsProductCode(getByWmsProductCodeReq);
                    ProductStructure.ProductBusiness productBusiness = response.getProductBusiness();
                    inboundOrderNo = inStockConfirmRequest.getOrderNo();
                    productCode = productBusiness.getProductModel();
                    productName = productBusiness.getProductName();
                    productUnit = "个";
                    inStockQuantity += newRecord.getQuantity();
                }

                //通知采购模块,调用基础模块的sourceChannel服务获取渠道的url
                String channelId = null;
                if (inboundOrder.getGongxiaoInboundOrderNo().contains("shaver")){
                    channelId = WmsSourceChannel.CHANNEL_SHAVER.getChannelId();
                }else if (inboundOrder.getGongxiaoInboundOrderNo().contains("PHTM")){
                    channelId = WmsSourceChannel.CHANNEL_TMALL.getChannelId();
                }else if(inboundOrder.getGongxiaoInboundOrderNo().contains("JMGO")){
                    channelId = WmsSourceChannel.CHANNEL_JMGO.getChannelId();
                }else {
                    channelId = WmsSourceChannel.CHANNEL_TUANGOU.getChannelId();
                }
                ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
                ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder().setRpcHeader(rpcHeader).setXpsChannelId(channelId).build();
                ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
                ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

                logger.info("开始通知销售模块，销售退货单已经入库");
                XpsWarehouseNotifyManager.notifySalesReturnInbound(sourceChannel.getXpsWarehouseNotifyUrl(), inboundOrderNo, traceNo, productCode, productName, productUnit, String.valueOf(inStockQuantity), inboundOrder.getProjectId());
                logger.info("通知销售模块，销售退货单入库成功");
            }
            logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToSalesTask.run() success", rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }
}
