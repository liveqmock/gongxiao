package com.yhglobal.gongxiao.warehouse.customer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.dao.OutstockDao;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.SenderInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.StockOutOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.outstock.WmsOutStockNotifyRequest;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class OutstockServiceImpl implements OutstockService {

    private static Logger logger = LoggerFactory.getLogger(OutstockServiceImpl.class);

    @Autowired
    OutstockDao outstockDao;
    @Autowired
    ChannelDao channelDao;
    @Autowired
    WarehouseDao warehouseDao;


    @Override
    public void createInstockRecor(OutboundOrder outboundOrder, List<OutboundOrderItem> outboundOrderItemList) {
        logger.info("[IN] createInstockRecor params: outboundOrder={},outboundOrderItemList={}",JSON.toJSONString(outboundOrder),JSON.toJSONString(outboundOrderItemList));
        try {
            Channel channel = channelDao.selectByChannelId(outboundOrder.getSourceChannel());
            WmsOutStockNotifyRequest wmsOutStockNotifyRequest = new WmsOutStockNotifyRequest();
            wmsOutStockNotifyRequest.setCustCode(channel.getCustCode());                       //客户代码  必选
            String warehouseCode = warehouseDao.selectWarehouseCodeById(Long.parseLong(outboundOrder.getWarehouseId()));
            wmsOutStockNotifyRequest.setCkid(outboundOrder.getWarehouseId());
            wmsOutStockNotifyRequest.setWarehouseCode(warehouseCode);                          //仓库编码  必选
            wmsOutStockNotifyRequest.setOrderNo(outboundOrder.getGongxiaoOutboundOrderNo());   //订单号    必选
            wmsOutStockNotifyRequest.setSourceOrderNo(outboundOrder.getSalesOrderNo());
            wmsOutStockNotifyRequest.setOrderSource(channel.getChannelName());                 //订单来源  必选
            wmsOutStockNotifyRequest.setOrderType(outboundOrder.getOutboundType());                            //操作类型/订单类型 必选
//        wmsOutStockNotifyRequest.setOrderFlage();                                                         //
            wmsOutStockNotifyRequest.setTransportMode(String.valueOf(outboundOrder.getShippingMode()));                //出库方式  可选
            wmsOutStockNotifyRequest.setOrderCreateTime(outboundOrder.getCreateTime());                                //订单创建时间  可选
//        wmsOutStockNotifyRequest.setExpectSendTime();                                 //要求出库时间  可选
//        wmsOutStockNotifyRequest.setDeliverRequirements();                            //配送要求     可选
//        wmsOutStockNotifyRequest.setPickerInfo();                                     //提货人信息   可选

            ReceiverInfo receiverInfo = new ReceiverInfo();
            receiverInfo.getReceiverAddress();
            receiverInfo.getReceiverName();
            SenderInfo senderInfo = new SenderInfo();
            senderInfo.getSenderAddress();
            senderInfo.getSenderName();
            wmsOutStockNotifyRequest.setReceiverInfo(receiverInfo);                                    //收货方信息   必选
            wmsOutStockNotifyRequest.setSenderInfo(senderInfo);                                        //发货方信息   必选

            List<StockOutOrderItem> orderItemList = new ArrayList<>();
            for (OutboundOrderItem record : outboundOrderItemList) {
                StockOutOrderItem stockOutOrderItem = new StockOutOrderItem();
                stockOutOrderItem.setItemCode(record.getProductCode());             //商品编码  必选
                stockOutOrderItem.setItemName(record.getProductName());             //商品名称
                stockOutOrderItem.setInventoryType(record.getInventoryType());      //库存类型  必选
                stockOutOrderItem.setItemQuantity(record.getTotalQuantity());       //商品数量  必选
//            stockInOrderItem.setItemPrice(record.get); //销售价格
//            stockInOrderItem.setTagPrice();            //吊牌价
//            stockInOrderItem.setPurchasePrice();       //采购价格
//            stockInOrderItem.setProduceDate();         //商品生产日期
//            stockInOrderItem.setExpireDate();          //商品过期日期
//            stockInOrderItem.setItemVersion();         //商品版本
                stockOutOrderItem.setBatchCode(record.getBatchNo());       //批次号
                orderItemList.add(stockOutOrderItem);
            }
            wmsOutStockNotifyRequest.setOrderItemList(orderItemList);                    //订单商品信息  必选
            wmsOutStockNotifyRequest.setRemark(outboundOrder.getNote());                 //备注   可选
            outstockDao.insertrecord(JSON.toJSONString(wmsOutStockNotifyRequest));
            logger.info("[OUT] get createInstockRecor success");
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
