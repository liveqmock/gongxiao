package com.yhglobal.gongxiao.warehouse.customer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.dao.InstockDao;
import com.yhglobal.gongxiao.foundation.channel.Channel;
import com.yhglobal.gongxiao.foundation.channel.ChannelDao;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.ProductBasicDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.warehouse.service.impl.OutboundShowServiceImpl;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.SenderInfo;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.StockInOrderItem;
import com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock.WmsInStockNotifyRequest;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstockServiceImpl implements InstockService{

    private Logger logger = LoggerFactory.getLogger(InstockServiceImpl.class);

    @Autowired
    InstockDao instockDao;

    @Autowired
    ChannelDao channelDao;

    @Autowired
    WarehouseDao warehouseDao;

    @Autowired
    ProductBasicDao productBasicDao;

    @Override
    public void createInstockRecor(String orderSourceNo, InboundOrder inboundOrder, List<InboundOrderItem> inboundOrderItemList) {
        logger.info("#traceId={}# [IN][createInstockRecor] params: orderSourceNo={},inboundOrder={},inboundOrderItemList={}", orderSourceNo,JSON.toJSONString(inboundOrder),JSON.toJSONString(inboundOrderItemList));
      try {
          WmsInStockNotifyRequest wmsInStockNotifyRequest = new WmsInStockNotifyRequest();
          wmsInStockNotifyRequest.setCkid(inboundOrder.getWarehouseId());                                                //仓库id  必选
//        String warehouseCode = warehouseDao.selectWarehouseCodeById(Long.parseLong(inboundOrder.getWarehouseId()));    //查询仓库编码
          Warehouse warehouse = warehouseDao.selectByPrimaryKey(Long.parseLong(inboundOrder.getWarehouseId()));
          wmsInStockNotifyRequest.setWarehouseCode(warehouse.getWmsWarehouseCode());                                       //仓库编码  必选
          wmsInStockNotifyRequest.setOrderNo(inboundOrder.getGongxiaoInboundOrderNo());                                  //订单号    必选

          Channel channel = channelDao.selectByChannelId(inboundOrder.getSourceChannel());
          wmsInStockNotifyRequest.setCustCode(channel.getCustCode());                                                     //客户代码  必选
          wmsInStockNotifyRequest.setOrderType(inboundOrder.getInboundType());                                            //操作类型/订单类型 必选
          wmsInStockNotifyRequest.setOrderSource(channel.getChannelName());                    //订单来源  必选
          wmsInStockNotifyRequest.setSourceOrderNo(orderSourceNo);    //来源单号  可选
//        wmsInStockNotifyRequest.setOrderCreateTime(inboundOrder.getCreateTime());          //订单创建时间 可选
//        wmsInStockNotifyRequest.setTmsServiceCode(inboundOrder.get);   //快递公司编码/车次号  可选
//        wmsInStockNotifyRequest.setTmsOrderCode();     //运单号/配送车牌  可选
//        wmsInStockNotifyRequest.setExpectStartTime();  //预期送达开始时间  可选
//        wmsInStockNotifyRequest.setBatchSendCtrlParam();//分批下发控制参数  可选
          ReceiverInfo receiverInfo = new ReceiverInfo();
          wmsInStockNotifyRequest.setReceiverInfo(receiverInfo);              //收货方信息  必选
          SenderInfo senderInfo = new SenderInfo();
          senderInfo.setSenderAddress(inboundOrder.getDeliverAddress());      //发货方地址   必选
          senderInfo.setSenderName(inboundOrder.getContactsPeople());
          wmsInStockNotifyRequest.setSenderInfo(senderInfo);                  //发货方信息   必选
          List<StockInOrderItem> orderItemList = new ArrayList<>();
          for (InboundOrderItem record : inboundOrderItemList) {
              StockInOrderItem stockInOrderItem = new StockInOrderItem();
              stockInOrderItem.setItemNo(record.getInboundOrderItemNo());         //行号
              ProductBasic productBasic = productBasicDao.selectByProductCode(record.getProductCode());
              stockInOrderItem.setItemCode(productBasic.getWMSCode());              //商品编码  必选
              stockInOrderItem.setItemName(productBasic.getProductName());              //商品名称
              stockInOrderItem.setInventoryType(record.getInventoryType());       //库存类型  必选
              stockInOrderItem.setItemQuantity(record.getTotalQuantity());        //商品数量  必选
              stockInOrderItem.setBatchCode(inboundOrder.getBatchNo());           //批次号
              orderItemList.add(stockInOrderItem);
          }
          wmsInStockNotifyRequest.setOrderItemList(orderItemList);                      //订单商品信息  必选
          wmsInStockNotifyRequest.setRemark(inboundOrder.getNote());                    //备注   可选
          instockDao.insertrecord(JSON.toJSONString(wmsInStockNotifyRequest));
      }catch (Exception e){
          logger.error("# errorMessage:" + e.getMessage(), e);
          throw e;
      }


        logger.info("#traceId={}# [OUT] get createInstockRecor success");
    }

}
