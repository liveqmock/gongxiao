package com.yhglobal.gongxiao.util;


import com.yhglobal.gongxiao.constant.EasConstant;
import com.yhglobal.gongxiao.diaobo.*;
import com.yhglobal.gongxiao.diaobo.inboundorder.WSXPSMoveInWarehsBillFacadeSoapBindingStub;
import com.yhglobal.gongxiao.diaobo.order.WSTestStockTransferFacadeSoapBindingStub;
import com.yhglobal.gongxiao.diaobo.outboundorder.WSXPSMoveIssueBillFacadeSoapBindingStub;
import com.yhglobal.gongxiao.eas.model.OtherInWare;
import com.yhglobal.gongxiao.eas.model.OtherInWareItem;
import com.yhglobal.gongxiao.eas.model.OtherOutWare;
import com.yhglobal.gongxiao.eas.model.OtherOutWareItem;
import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrder;
import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrderInbound;
import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrderItem;
import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrderItemInbound;
import com.yhglobal.gongxiao.eas.model.SaleOrder;
import com.yhglobal.gongxiao.eas.model.SaleOrderItem;
import com.yhglobal.gongxiao.eas.model.SaleOutOrder;
import com.yhglobal.gongxiao.eas.model.SaleOutOrderItem;
import com.yhglobal.gongxiao.model.EASEnvironmentModel;
import com.yhglobal.gongxiao.other.inware.WSImportOtherInWaerhsBillFacadeSoapBindingStub;
import com.yhglobal.gongxiao.other.outware.WSWMSOtherIssueBillFacadeSoapBindingStub;
import com.yhglobal.gongxiao.purchase.purchase.WSWsPurOrderFacadeSoapBindingStub;
import com.yhglobal.gongxiao.purchase.purchaseinbound.WSWMSPurInWarehsBillFacadeSoapBindingStub;
import com.yhglobal.gongxiao.sale.order.WSSaleOrderFacadeSoapBindingStub;
import com.yhglobal.gongxiao.sale.out.WSImportSaleIssueFacadeSoapBindingStub;
import org.apache.axis.transport.http.HTTPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * EAS工具类
 *
 * @author: 陈浩
 **/
public class EasUtil {

    private static Logger logger = LoggerFactory.getLogger(EasUtil.class);

    static EASEnvironmentModel easEnvironmentModel=null;

    static {
        easEnvironmentModel = PropertyParseUtil.parseProperties();
    }

    /**
     * 发送采购单信息
     * @param purchaseBasicOrder 采购单信息
     * @param purchaseBasicOrderItemList  采购单货品信息列表
     * @return EAS返回数据
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static String sendPurchaseOrder2Eas(PurchaseBasicOrder purchaseBasicOrder, List<PurchaseBasicOrderItem> purchaseBasicOrderItemList) throws MalformedURLException, RemoteException {
        String json = EasBuildParameter.buildPurchase(purchaseBasicOrder,purchaseBasicOrderItemList);
        logger.info("============推送采购单========start====={}",json);
        org.apache.axis.client.Service service = AxisClientManager.getAxisClient();
        String purchaseUrl = easEnvironmentModel.getUrl()+EasConstant.PURCHASE_ORDER;
        WSWsPurOrderFacadeSoapBindingStub purOrder
                = new WSWsPurOrderFacadeSoapBindingStub(new java.net.URL(purchaseUrl), service);
        String importPurOrder = purOrder.importPurOrder(json);
        logger.info("============推送采购入库单========end=====return json = {}",importPurOrder);
        return importPurOrder;
    }

    /**
     * 发送采购入库单信息到EAS
     * @param purchaseBasicOrderInbound 单头
     * @param purchaseBasicOrderItemInboundList 单体
     * @return
     * @throws RemoteException
     * @throws MalformedURLException
     */
    public static String sendPurchaseInbound2Eas(PurchaseBasicOrderInbound purchaseBasicOrderInbound, List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList) throws RemoteException, MalformedURLException {
        String json = EasBuildParameter.buildPurchaseInbound(purchaseBasicOrderInbound,purchaseBasicOrderItemInboundList);
        logger.info("============推送采购入库单========start====={}",json);
        org.apache.axis.client.Service service = AxisClientManager.getAxisClient();
        String purchaseInboundUrl = easEnvironmentModel.getUrl()+EasConstant.PURCHASE_INBOUND;
        WSWMSPurInWarehsBillFacadeSoapBindingStub purchaseInboundOrder
                = new WSWMSPurInWarehsBillFacadeSoapBindingStub(new java.net.URL(purchaseInboundUrl), service);
        String importPurInboundOrder = purchaseInboundOrder.importData(json);
        logger.info("============推送采购入库单========end=====return json = {}",importPurInboundOrder);
        return importPurInboundOrder;
    }

    /**
     * 发送销售订单信息到EAS
     * @param saleOrder 单头
     * @param saleOrderItemList 单体
     * @return
     * @throws RemoteException
     * @throws MalformedURLException
     */
    public static String sendSaleOrder2Eas(SaleOrder saleOrder, List<SaleOrderItem> saleOrderItemList) throws RemoteException, MalformedURLException,SocketTimeoutException {
        try {
            String json = EasBuildParameter.buildSaleOrder(saleOrder,saleOrderItemList);
            logger.info("============推送销售单========start===== json={}",json);
            org.apache.axis.client.Service service = AxisClientManager.getAxisClient();
            String purchaseInboundUrl = easEnvironmentModel.getUrl()+EasConstant.SALE_ORDER;
            WSSaleOrderFacadeSoapBindingStub saleOrderStub
                    = new WSSaleOrderFacadeSoapBindingStub(new java.net.URL(purchaseInboundUrl), service);
            String importPurInboundOrder = saleOrderStub.importSaleOrderBill(json);
            logger.info("============推送销售单========end===== json={}",importPurInboundOrder);
            return importPurInboundOrder;
        }catch (Exception e){
            logger.error("#traceId # errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 发送销售出库数据到EAS
     * @param saleOutOrder 销售出库单
     * @param saleOutOrderItems 销售出库单明细
     * @return
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static String sendSaleOutOrder2Eas(SaleOutOrder saleOutOrder, List<SaleOutOrderItem> saleOutOrderItems) throws MalformedURLException, RemoteException {
        String json = EasBuildParameter.buildSaleOutOrder(saleOutOrder,saleOutOrderItems);
        logger.info("============推送销售出库单========start===== json={}",json);
        org.apache.axis.client.Service service = AxisClientManager.getAxisClient();
        String saleOutUrl = easEnvironmentModel.getUrl()+EasConstant.SALE_OUTBOUND;
        WSImportSaleIssueFacadeSoapBindingStub saleOutStub
                = new WSImportSaleIssueFacadeSoapBindingStub(new java.net.URL(saleOutUrl), service);
        String saleOutReturnJson = saleOutStub.importData(json);
        logger.info("============推送销售出库单========end===== json={}",saleOutReturnJson);
        return saleOutReturnJson;
    }

    /**
     * 其他入库
     * @param otherInWare 单头
     * @param otherInWareItemList 单体
     * @return
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static String sendOtherInWare2Eas(OtherInWare otherInWare, List<OtherInWareItem> otherInWareItemList) throws MalformedURLException, RemoteException {
        EASEnvironmentModel easEnvironmentModel = PropertyParseUtil.parseProperties();;
        System.out.println(easEnvironmentModel.toString());

        System.out.println(easEnvironmentModel.toString());

        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        String json = EasBuildParameter.buildOtherInWare(otherInWare,otherInWareItemList);
        System.out.println(json);
        //login
        EasBuildParameter.easLogin(service);
        //ware in

        String otherInWareUrl = easEnvironmentModel.getUrl()+EasConstant.OTHER_IN_WARE;
        WSImportOtherInWaerhsBillFacadeSoapBindingStub otherWareInStub
                = new WSImportOtherInWaerhsBillFacadeSoapBindingStub(new java.net.URL(otherInWareUrl), service);
        String otherWareInJson = otherWareInStub.importData(json);
        System.out.println(otherWareInJson);
        return otherWareInJson;
    }

    /**
     * 其他出库
     * @param otherOutWare 单头
     * @param otherOutWareItemList 单体
     * @return
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static String sendOtherOutWare2Eas(OtherOutWare otherOutWare, List<OtherOutWareItem> otherOutWareItemList) throws MalformedURLException, RemoteException {
        EASEnvironmentModel easEnvironmentModel = PropertyParseUtil.parseProperties();;
        System.out.println(easEnvironmentModel.toString());

        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        //ware out
        String json = EasBuildParameter.buildOtherOutWare(otherOutWare,otherOutWareItemList);
        System.out.println(json);
        //login
        EasBuildParameter.easLogin(service);

        String otherOutWareUrl = easEnvironmentModel.getUrl()+EasConstant.OTHER_OUT_WARE;
        WSWMSOtherIssueBillFacadeSoapBindingStub otherOutWareStub
                = new WSWMSOtherIssueBillFacadeSoapBindingStub(new java.net.URL(otherOutWareUrl), service);
        String otherOutWareJson = otherOutWareStub.importOtherIssueBill(json);
        System.out.println(otherOutWareJson);
        return otherOutWareJson;
    }

    /**
     * 调拨单详情
     * @param allocateEasBasicOrder 单头
     * @param allocateEasItemOrderList 单体
     * @return
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static String sendTransforWare2Eas(AllocateEasBasicOrder allocateEasBasicOrder, List<AllocateEasItemOrder> allocateEasItemOrderList) throws MalformedURLException, RemoteException {
        String json = EasBuildParameter.buildTransforOrder(allocateEasBasicOrder,allocateEasItemOrderList);
        logger.info("============推送调拨单========start===== json={}",json);
        org.apache.axis.client.Service service = AxisClientManager.getAxisClient();
        String saleOutUrl = easEnvironmentModel.getUrl()+EasConstant.TRANSEFOR_ORDER;
        WSTestStockTransferFacadeSoapBindingStub transforOrderStub
                = new WSTestStockTransferFacadeSoapBindingStub(new java.net.URL(saleOutUrl), service);
        transforOrderStub._setProperty(HTTPConstants.HEADER_TRANSFER_ENCODING_CHUNKED,Boolean.FALSE);
        String saleOutReturnJson = transforOrderStub.importDate(json);
        logger.info("============推送调拨单========end===== json={}",saleOutReturnJson);
        return saleOutReturnJson;
    }

    /**
     * 调拨入库单
     * @param allocateStockEasOrder 单头
     * @param allocateStockBrandEasOrderList 单体
     * @return
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static String sendTransforInBoundWare2Eas(AllocateStockEasOrder allocateStockEasOrder, List<AllocateStockBrandEasOrder> allocateStockBrandEasOrderList) throws MalformedURLException, RemoteException {
        String json = EasBuildParameter.buildTransforInboundOrder(allocateStockEasOrder,allocateStockBrandEasOrderList);
        logger.info("============推送调拨入库单========start===== json={}",json);
        org.apache.axis.client.Service service = AxisClientManager.getAxisClient();
        String saleOutUrl = easEnvironmentModel.getUrl()+EasConstant.TRANSEFOR_INBOUND;
        WSXPSMoveInWarehsBillFacadeSoapBindingStub tranforInboundStub
                = new WSXPSMoveInWarehsBillFacadeSoapBindingStub(new java.net.URL(saleOutUrl), service);
        String tranforInboundJson = tranforInboundStub.importData(json);
        logger.info("============推送调拨入库单========end===== json={}",tranforInboundJson);
        return tranforInboundJson;
    }

    /**
     * 调拨出库单
     * @param allocateStockEasOrder 单头
     * @param allocateStockBrandEasOrderList 单体
     * @return
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static String sendTransforOutBoundWare2Eas(AllocateStockEasOrder allocateStockEasOrder, List<AllocateStockBrandEasOrder> allocateStockBrandEasOrderList) throws MalformedURLException, RemoteException {
        String json = EasBuildParameter.buildTransforOutboundOrder(allocateStockEasOrder,allocateStockBrandEasOrderList);
        logger.info("============推送调拨出库单========start===== json={}",json);
        org.apache.axis.client.Service service = AxisClientManager.getAxisClient();
        String saleOutUrl = easEnvironmentModel.getUrl()+EasConstant.TRANSEFOR_OUTBOUND;
        WSXPSMoveIssueBillFacadeSoapBindingStub transforOutStub
                = new WSXPSMoveIssueBillFacadeSoapBindingStub(new java.net.URL(saleOutUrl), service);
        String transforOutJson = transforOutStub.importData(json);
        logger.info("============推送调拨出库单========end===== json={}",transforOutJson);
        return transforOutJson;
    }

}
