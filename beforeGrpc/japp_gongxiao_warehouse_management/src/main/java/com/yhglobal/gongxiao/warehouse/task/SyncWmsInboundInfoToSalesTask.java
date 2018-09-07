package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.sales.service.SalesReturnService;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


/**
 * 当WMS回告对应的订单是销售单时 调用该任务 将wms入库确认信息同步给销售模块
 */
public class SyncWmsInboundInfoToSalesTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SyncWmsInboundInfoToSalesTask.class);

    private RpcHeader rpcHeader;

    private Data inStockConfirmRequest;

    private ProductService productService;

    private SalesReturnService salesReturnService;

    public SyncWmsInboundInfoToSalesTask(RpcHeader rpcHeader, Data inStockConfirmRequest,ProductService productService,SalesReturnService salesReturnService) {
        this.rpcHeader = rpcHeader;
        this.inStockConfirmRequest = inStockConfirmRequest;
        this.productService = productService;
        this.salesReturnService = salesReturnService;
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
                for (StocksQtyDto newRecord : record.getStocksQty()) {

                    ProductBasic productBasic = productService.getByWmsProductCode(rpcHeader,record.getCargoCode());
                    inboundOrderNo = inStockConfirmRequest.getOrderNo();
                    productCode = productBasic.getProductCode();
                    productName = productBasic.getProductName();
                    productUnit = "个";
                    inStockQuantity += newRecord.getQuantity();
                }

                salesReturnService.salesReturnInbound(rpcHeader, inboundOrderNo, traceNo, productCode, productName, productUnit, inStockQuantity);
            }
            logger.info("#traceId={}# [OUT] get SyncWmsInboundInfoToSalesTask.run() success", rpcHeader.getTraceId());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }
}
