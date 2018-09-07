package com.yhglobal.gongxiao.util;

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

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 *
 * @author: 陈浩
 **/
public class EasTestUtil {
    public static void purchaseOrder(){
        PurchaseBasicOrder purchaseBasicOrder = new PurchaseBasicOrder();
        List<PurchaseBasicOrderItem> purchaseBasicOrderItemList = new ArrayList<>();
        purchaseBasicOrder.setProjectId("02029901");
        purchaseBasicOrder.setSupplierCode("");

        PurchaseBasicOrderItem purchaseBasicOrderItem =  new PurchaseBasicOrderItem();
        purchaseBasicOrderItem.setTaxPrice(11.7);
        purchaseBasicOrderItem.setNumber(100);
        purchaseBasicOrderItem.setDiscountRate(0);
        purchaseBasicOrderItem.setTaxRate(17);
        purchaseBasicOrderItem.setDeliveryDate(new Date());
        purchaseBasicOrderItem.setWarehouseId("002");
        purchaseBasicOrderItem.setBizDate(new Date());
        purchaseBasicOrderItem.setMaterialId("04.01.01.01.025");
        purchaseBasicOrderItem.setUnit("GE");

        double totalTaxAmount = purchaseBasicOrderItem.getTaxPrice() * purchaseBasicOrderItem.getNumber();
        double rate = purchaseBasicOrderItem.getTaxRate() / 100.0;
        double totalTax = totalTaxAmount*(rate/(1+rate));
        purchaseBasicOrder.setTotalTax(totalTax);
        purchaseBasicOrder.setTotalTaxAmount(totalTaxAmount);
        purchaseBasicOrder.setNumber(100);

        purchaseBasicOrderItemList.add(purchaseBasicOrderItem);

        try {
            EasUtil.sendPurchaseOrder2Eas(purchaseBasicOrder,purchaseBasicOrderItemList);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    public static void purchaseInboundOrder(){
        PurchaseBasicOrderInbound purchaseBasicOrderInbound =new PurchaseBasicOrderInbound();
        List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList=new ArrayList<>();

        purchaseBasicOrderInbound.setProjectId("02029901");
        purchaseBasicOrderInbound.setSupplierCode("01.02.01.0012");
        purchaseBasicOrderInbound.setId("wEQAAADy/wgxcb+t");
        purchaseBasicOrderInbound.setPurchaseOrderNum("POSQ2018060120");

        PurchaseBasicOrderItemInbound purchaseBasicOrderItemInbound = new PurchaseBasicOrderItemInbound();
        purchaseBasicOrderItemInbound.setTaxRate(17);
        purchaseBasicOrderItemInbound.setTaxPrice(11.7);
        purchaseBasicOrderItemInbound.setWarehouseCode("002");
        purchaseBasicOrderItemInbound.setLocationId("01");
        purchaseBasicOrderItemInbound.setLot(new Date().getTime()+"");
        purchaseBasicOrderItemInbound.setNumber(100);
        purchaseBasicOrderItemInbound.setMaterialId("04.01.01.01.025");
        purchaseBasicOrderItemInbound.setPurchaseOrderId("wEQAAADy/wgxcb+t");
        purchaseBasicOrderItemInbound.setPurchaseOrderEntryId("wEQAAADy/wkmBBzF");
        purchaseBasicOrderItemInboundList.add(purchaseBasicOrderItemInbound);

        double totalTaxAmount = purchaseBasicOrderItemInbound.getTaxPrice() * purchaseBasicOrderItemInbound.getNumber();
        double rate = purchaseBasicOrderItemInbound.getTaxRate() / 100.0;
        double totalTax = totalTaxAmount*(rate/(1+rate));

        purchaseBasicOrderInbound.setNumber(100);
        purchaseBasicOrderInbound.setTotalTaxAmount(totalTaxAmount);
        purchaseBasicOrderInbound.setTotalTax(totalTax);
        try {
            EasUtil.sendPurchaseInbound2Eas(purchaseBasicOrderInbound,purchaseBasicOrderItemInboundList);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static void saleOrder() throws SocketTimeoutException {
        SaleOrder saleOrder = new SaleOrder();
        List<SaleOrderItem> saleOrderItemList = new ArrayList<>();

        saleOrder.setProjectId("02029901");
        saleOrder.setCustomerId("02.02.0132");
        saleOrder.setCurrencyCode("BB01");

        SaleOrderItem saleOrderItem = new SaleOrderItem();
        saleOrderItem.setCustomerId("02.02.0132");
        saleOrderItem.setNumber(100);
        saleOrderItem.setTaxPrice(11.7);
        saleOrderItem.setTaxRate(17);
        saleOrderItem.setWarehouseId("002");
        saleOrderItem.setMaterialId("04.01.01.01.025");
        saleOrderItem.setUnit("GE");
        saleOrderItem.setSendDate(new Date());
        saleOrderItem.setDeliveryDate(new Date());
        saleOrderItemList.add(saleOrderItem);

        double totalTaxAmount = saleOrderItem.getTaxPrice() * saleOrderItem.getNumber();
        double rate = saleOrderItem.getTaxRate() / 100.0;
        double totalTax = totalTaxAmount*(rate/(1+rate));
        saleOrder.setTotalTaxAmount(totalTaxAmount);
        saleOrder.setTotalTax(totalTax);
        saleOrder.setNumber(saleOrderItem.getNumber());

        try {
            String s = EasUtil.sendSaleOrder2Eas(saleOrder, saleOrderItemList);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static void saleOutOrder(){
        SaleOutOrder saleOutOrder = new SaleOutOrder();
        List<SaleOutOrderItem> saleOutOrderItems = new ArrayList<>();
        saleOutOrder.setProjectId("02029901");
        saleOutOrder.setCustomerId("02.02.0132");
        saleOutOrder.setCurrencyId("BB01");

        saleOutOrder.setTotalQuantity(100);
        saleOutOrder.setId("wEQAAADy/wzEikI6");

        SaleOutOrderItem saleOutOrderItem = new SaleOutOrderItem();
        saleOutOrderItem.setTaxRate(17);
        saleOutOrderItem.setTaxPrice(11.7);
        saleOutOrderItem.setNumber(100);
        saleOutOrderItem.setWarehouseId("002");
        saleOutOrderItem.setLocationId("01");//采购入库跟销售出库的库位
        saleOutOrderItem.setLot("1528110174867");//批次用采购入库的批次
        saleOutOrderItem.setSourceBillId("");
        saleOutOrderItem.setUnit("GE");
        saleOutOrderItem.setMaterialId("04.01.01.01.025");
        saleOutOrderItem.setCustomerId("02.02.0132");
        saleOutOrderItem.setSourceBillId("wEQAAADy/w2IiCpY");
        saleOutOrderItems.add(saleOutOrderItem);

        double totalTaxAmount = saleOutOrderItem.getTaxPrice() * saleOutOrderItem.getNumber();
        double rate = saleOutOrderItem.getTaxRate() / 100.0;
        double totalTax = totalTaxAmount*(rate/(1+rate));

        saleOutOrder.setTotalTaxAmount(totalTaxAmount);
        saleOutOrder.setTaxAmount(totalTax);
        saleOutOrder.setNumber(saleOutOrderItem.getNumber());
        try {
            EasUtil.sendSaleOutOrder2Eas(saleOutOrder,saleOutOrderItems);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public static void otherIn(){
        OtherInWare otherInWare = new OtherInWare();
        List<OtherInWareItem> otherInWareItemList = new ArrayList<>();
        otherInWare.setProjectId("02029901");
        otherInWare.setCreatorId("user");

        OtherInWareItem otherInWareItem = new OtherInWareItem();
        otherInWareItem.setTaxPrice(11.7);
        otherInWareItem.setNumber(100);
        otherInWareItem.setWarehouseId("002");
        otherInWareItem.setLocationId("01");
        otherInWareItem.setLot(new Date().getTime()+"");
        otherInWareItem.setMaterialId("04.01.01.01.025");
        otherInWareItem.setSourceBillId("wEQAAADv8e8xcb+t");
        otherInWareItem.setInvUpdateType("001");
        otherInWareItemList.add(otherInWareItem);

        double totalTaxAmount = otherInWareItem.getTaxPrice() * otherInWareItem.getNumber();

        otherInWare.setNumber(otherInWareItem.getNumber());
        otherInWare.setTotalTaxAmount(totalTaxAmount);

        try {
            EasUtil.sendOtherInWare2Eas(otherInWare,otherInWareItemList);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public static void otherOut(){
        OtherOutWare otherOutWare = new OtherOutWare();
        List<OtherOutWareItem> otherOutWareItemList = new ArrayList<>();

        otherOutWare.setProjectId("02029901");
        otherOutWare.setCreatorId("user");
        OtherOutWareItem otherOutWareItem = new OtherOutWareItem();
        otherOutWareItem.setTaxPrice(11.7);
        otherOutWareItem.setNumber(100);
        otherOutWareItem.setWarehouseId("002");
        otherOutWareItem.setLocationId("01");
        otherOutWareItem.setLot("1528112246098");
        otherOutWareItem.setMaterialId("04.01.01.01.025");
        otherOutWareItem.setUnit("GE");
        otherOutWareItem.setSourceBillId("wEQAAADv8e8xcb+t");
        otherOutWareItemList.add(otherOutWareItem);
        double totalTaxAmount = otherOutWareItem.getTaxPrice() * otherOutWareItem.getNumber();
        otherOutWare.setNumber(otherOutWareItem.getNumber());
        otherOutWare.setTotalTaxAmount(totalTaxAmount);

        try {
            EasUtil.sendOtherOutWare2Eas(otherOutWare,otherOutWareItemList);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketTimeoutException {
//        purchaseOrder();
//        purchaseInboundOrder();
        saleOrder();
//        saleOutOrder();
//          otherIn();
//        otherOut();
    }
}
