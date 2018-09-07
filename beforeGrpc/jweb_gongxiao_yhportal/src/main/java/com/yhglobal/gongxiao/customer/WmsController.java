package com.yhglobal.gongxiao.customer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.dao.InstockDao;
import com.yhglobal.gongxiao.dao.OutstockDao;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.warehouse.service.WmsConfirmInboundService;
import com.yhglobal.gongxiao.warehouse.service.WmsConfirmOutboundService;
import com.yhglobal.gongxiao.warehousemanagement.customermodel.InstockOrder;
import com.yhglobal.gongxiao.warehousemanagement.customermodel.OutstockOrder;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockInDetailDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StockOutOrderConfirmOrderItemDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.StocksQtyDto;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.StockInOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.WmsInStockNotifyRequest;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instockconfirm.Data;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.StockOutOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.WmsOutStockNotifyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/wmsconfirm")
public class WmsController {

    private static Logger logger = LoggerFactory.getLogger(WmsController.class);

    @Reference
    WmsConfirmInboundService wmsConfirmInboundService;

    @Reference
    WmsConfirmOutboundService wmsConfirmOutboundService;

    @Autowired
    InstockDao instockDao;

    @Autowired
    OutstockDao outstockDao;



    @RequestMapping("/getInstocks")
    @ResponseBody
    public GongxiaoResult getInstocks(){
        GongxiaoResult result = new GongxiaoResult();

        List<InstockOrder> resultlist = instockDao.selectrecord();

        result.setReturnCode(0);
        result.setData(resultlist);

        return result;
    }

    @RequestMapping("/getOutstocks")
    @ResponseBody
    public GongxiaoResult getOutstocks(){
        GongxiaoResult result = new GongxiaoResult();

        List<OutstockOrder> resultlist = outstockDao.selectrecord();

        result.setReturnCode(0);
        result.setData(resultlist);

        return result;
    }


    @RequestMapping("/confirmInstock")
    @ResponseBody
    public GongxiaoResult confirmInstock(String instockInfo, HttpServletRequest request){
        logger.info("[IN][confirmInstock] params: instockInfo={} ",instockInfo);
        String traceId = DateTimeIdGenerator.nextId(BizNumberType.WMS_TRACE_ID);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "1", "WMS系统");
        GongxiaoResult result = new GongxiaoResult();
        Data inStockConfirmRequest = new Data();
        WmsInStockNotifyRequest record = JSON.parseObject(instockInfo,WmsInStockNotifyRequest.class);

        inStockConfirmRequest.setCkid(Integer.parseInt(record.getCkid()));
        inStockConfirmRequest.setWarehouseCode(record.getWarehouseCode());
        inStockConfirmRequest.setConfirmType(record.getOrderType());
        inStockConfirmRequest.setInboundBatchNo("6666666");
        inStockConfirmRequest.setMessageID(4645464);
        inStockConfirmRequest.setOrderNo(record.getOrderNo());
//        inStockConfirmRequest.setReceiveDate();
//        inStockConfirmRequest.setSender();
        inStockConfirmRequest.setSourceOrderNo(record.getSourceOrderNo());
        List<StockInDetailDto> stockInDetails = new ArrayList<>();
        for (StockInOrderItem stockInOrderItem : record.getOrderItemList()){
            StockInDetailDto stockInDetailDto = new StockInDetailDto();
            stockInDetailDto.setCargoCode(stockInOrderItem.getItemCode());
            stockInDetailDto.setIsCompleted(true);
//            stockInDetailDto.setOwnerCode();
            List<StocksQtyDto> stocksQty = new ArrayList<>();
            StocksQtyDto stocksQtyDto = new StocksQtyDto();
            stocksQtyDto.setInventoryType(1);
            stocksQtyDto.setQuantity(stockInOrderItem.getItemQuantity());
            stocksQty.add(stocksQtyDto);
            stockInDetailDto.setStocksQty(stocksQty);
            stockInDetails.add(stockInDetailDto);
        }
        inStockConfirmRequest.setStockInDetails(stockInDetails);

        wmsConfirmInboundService.confirmWmsInboundInfo(rpcHeader,inStockConfirmRequest);
        return result;
    }

    @RequestMapping("/confirmOutstock")
    @ResponseBody
    public GongxiaoResult confirmOutstock(String outstockInfo, HttpServletRequest request){
        logger.info("[IN][confirmOutstock] params: outstockInfo={} ",outstockInfo);

        GongxiaoResult result = new GongxiaoResult();
        com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data outStockConfirmRequest = new com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstockconfirm.Data();
        WmsOutStockNotifyRequest record = JSON.parseObject(outstockInfo,WmsOutStockNotifyRequest.class);

        outStockConfirmRequest.setCkid(Integer.parseInt(record.getCkid()));
        outStockConfirmRequest.setConfirmType(1);
        outStockConfirmRequest.setMessageID(6545645);
        outStockConfirmRequest.setOrderNo(record.getOrderNo());
        outStockConfirmRequest.setSourceOrderNo(record.getSourceOrderNo());
        outStockConfirmRequest.setOutBizCode(record.getWarehouseCode());
        List<StockOutOrderConfirmOrderItemDto> orderItems = new ArrayList<>();
        for (StockOutOrderItem stockOutOrderItem : record.getOrderItemList()){
            StockOutOrderConfirmOrderItemDto newrcord = new StockOutOrderConfirmOrderItemDto();
            newrcord.setCompleted(true);
            newrcord.setItemCode(stockOutOrderItem.getItemCode());
            List<StockOutOrderConfirmItemDto> list = new ArrayList<>();
            StockOutOrderConfirmItemDto stockOutOrderConfirmItemDto = new StockOutOrderConfirmItemDto();
            stockOutOrderConfirmItemDto.setInventoryType(1);
            stockOutOrderConfirmItemDto.setProduceCode(stockOutOrderItem.getItemCode());
            stockOutOrderConfirmItemDto.setQuantity(stockOutOrderItem.getItemQuantity());
            list.add(stockOutOrderConfirmItemDto);
            newrcord.setItems(list);
            orderItems.add(newrcord);
        }
        outStockConfirmRequest.setOrderItems(orderItems);
        outStockConfirmRequest.setOrderType(String.valueOf(record.getOrderType()));


        try {
            wmsConfirmOutboundService.confirmWmsOutboundInfo(outStockConfirmRequest);
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        return result;
    }
}
