package com.yhglobal.gongxiao.util;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.constant.EasConstant;
import com.yhglobal.gongxiao.eas.*;
import com.yhglobal.gongxiao.login.EASLoginSoapBindingStub;
import com.yhglobal.gongxiao.login.WSContext;
import com.yhglobal.gongxiao.model.*;
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class EasBuildParameter {

    private static Logger logger = LoggerFactory.getLogger(EasBuildParameter.class);

    /**
     * 登录
     *
     * @param service
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static void easLogin(Service service) throws MalformedURLException, RemoteException {
        logger.info("=============登录 start ========");
        EASEnvironmentModel easEnvironmentModel = PropertyParseUtil.parseProperties();
        String loginUrl = easEnvironmentModel.getUrl() + EasConstant.LOGIN;
        logger.info("登陆信息{}",easEnvironmentModel.toString());

        EASLoginSoapBindingStub stub = new EASLoginSoapBindingStub(new java.net.URL(loginUrl), service);
        WSContext login = stub.login(easEnvironmentModel.getUser(),
                easEnvironmentModel.getPwd(),
                easEnvironmentModel.getSlame(),
                easEnvironmentModel.getDcname(),
                easEnvironmentModel.getLanguage(),
                Integer.parseInt(easEnvironmentModel.getDbtype()));//调用ws提供的方法
        System.out.println(login.getSessionId());
        System.out.println(login.getUserName());
        logger.info("=============登录 end ========");

    }

    /**
     * 组装采购单数据
     *
     * @param purchaseBasicOrder         采购单单头
     * @param purchaseBasicOrderItemList 采购单单体
     * @return
     */
    public static String buildPurchase(PurchaseBasicOrder purchaseBasicOrder, List<PurchaseBasicOrderItem> purchaseBasicOrderItemList) {
        //设置单头信息
        EasPurchaseOrder easPurchaseOrder = new EasPurchaseOrder();
        easPurchaseOrder.setPurchaseOrgUnit(purchaseBasicOrder.getProjectId());
        easPurchaseOrder.setSupplier(purchaseBasicOrder.getSupplierCode());
        easPurchaseOrder.setPaymentType("003");
        easPurchaseOrder.setSettlementType("01");
        easPurchaseOrder.setCurrency("BB01");
        easPurchaseOrder.setExchangeRate(new BigDecimal(1));

        easPurchaseOrder.setCompanyOrgUnit(purchaseBasicOrder.getProjectId().substring(0, 4)); //财务组织
        easPurchaseOrder.setInTax(true);
        easPurchaseOrder.setPriceInTax(true);
        easPurchaseOrder.setBaseStatus("4");
        easPurchaseOrder.setBizType("110");
        easPurchaseOrder.setBillTypeID("220");
        easPurchaseOrder.setCreator("user");
        easPurchaseOrder.setControlUnit("0");
        easPurchaseOrder.setBizDate(purchaseBasicOrder.getBusinessDate());
        List<EasPurchaseOrderItem> easPurchaseOrderList = new ArrayList<>();
        double totalTaxAmountHeader=0;
        double amountHeader=0;
        double discountAmountHeader = 0;
        double totalTaxHeader=0;
        //设置单体信息
        for (PurchaseBasicOrderItem purchaseBasicOrderItem : purchaseBasicOrderItemList) {
            double taxPrice = purchaseBasicOrderItem.getTaxPrice(); //含税单价
            double taxRate = purchaseBasicOrderItem.getTaxRate();   //税率
            double price = taxPrice / (1 + taxRate / 100.0);  //不含税单价
            double discountRate = purchaseBasicOrderItem.getDiscountRate() / 100;//折扣率
            double actualTxaPrice = taxPrice * (1 - discountRate);    //实际含税单价
            double actualPrice = price * (1 - discountRate); //实际单价

            int number = purchaseBasicOrderItem.getNumber();
            double discountAmount = taxPrice * number * (discountRate); //折扣额 = 含税单价*数量*折扣
            double priceTaxAmountTotal = taxPrice * number - discountAmount; //价税合计=含税单价*数量-折扣额
            double amount = actualPrice * number; //金额=实际单价*数量
            double tax = priceTaxAmountTotal - amount;//税额
            logger.info("价税合计={}金额={}税额={}",priceTaxAmountTotal,amount,tax);
            totalTaxAmountHeader+=priceTaxAmountTotal;
            amountHeader+=amount;
            discountAmountHeader+=discountAmount;
            totalTaxHeader+=tax;

            EasPurchaseOrderItem easPurchaseOrderItem = new EasPurchaseOrderItem();
            easPurchaseOrderItem.setPresent(false);
            easPurchaseOrderItem.setDemandQty(new BigDecimal(number));
            easPurchaseOrderItem.setQty(easPurchaseOrderItem.getDemandQty());
            easPurchaseOrderItem.setStorageOrgUnit(purchaseBasicOrder.getProjectId());
            easPurchaseOrderItem.setCompanyOrgUnit(purchaseBasicOrder.getProjectId().substring(0, 4)); //财务组织=项目的前四位
            easPurchaseOrderItem.setAssistQty(easPurchaseOrderItem.getDemandQty());
            easPurchaseOrderItem.setPrice(new BigDecimal(price)); //单价=含税单价(1-税率)
            easPurchaseOrderItem.setDiscountRate(new BigDecimal(purchaseBasicOrderItem.getDiscountRate()));
            easPurchaseOrderItem.setActualPrice(new BigDecimal(actualPrice)); //
            easPurchaseOrderItem.setTaxRate(new BigDecimal(taxRate));
            easPurchaseOrderItem.setTaxPrice(new BigDecimal(taxPrice));
            easPurchaseOrderItem.setActualTaxPrice(new BigDecimal(actualTxaPrice));
            easPurchaseOrderItem.setAmount(new BigDecimal(actualPrice * number));
            easPurchaseOrderItem.setLocalAmount(easPurchaseOrderItem.getAmount());
            easPurchaseOrderItem.setTax(new BigDecimal(tax));
            easPurchaseOrderItem.setTaxAmount(new BigDecimal(priceTaxAmountTotal));
            easPurchaseOrderItem.setDiscountAmount(new BigDecimal(discountAmount));
            easPurchaseOrderItem.setDeliveryDate(purchaseBasicOrder.getRequireArrivalDate());
            easPurchaseOrderItem.setBizDate(purchaseBasicOrderItem.getBizDate());
            easPurchaseOrderItem.setBaseQty(new BigDecimal(number));
            easPurchaseOrderItem.setLocalTaxAmount(new BigDecimal(priceTaxAmountTotal));//本位币价税合计=本位币含税单价*数量
            easPurchaseOrderItem.setLocalTax(new BigDecimal(tax));//本位币税额=本币价税合计-本币的金额
            easPurchaseOrderItem.setRequestOrgUnit(purchaseBasicOrder.getProjectId());
            easPurchaseOrderItem.setWareHouse(purchaseBasicOrderItem.getWarehouseId());
            easPurchaseOrderItem.setReqComEqlRecCom(true);
            easPurchaseOrderItem.setMaterial(purchaseBasicOrderItem.getMaterialId());
            easPurchaseOrderItem.setUnit(purchaseBasicOrderItem.getUnit());//默认单位为个
            easPurchaseOrderItem.setBaseStatus("4");
            easPurchaseOrderItem.setAssociateQty(new BigDecimal(number));
            easPurchaseOrderItem.setBaseUnit(purchaseBasicOrderItem.getUnit());
            easPurchaseOrderList.add(easPurchaseOrderItem);
        }
        easPurchaseOrder.setTotalAmount(new BigDecimal(amountHeader));
        easPurchaseOrder.setTotalTax(new BigDecimal(totalTaxHeader));
        easPurchaseOrder.setTotalTaxAmount(new BigDecimal(totalTaxAmountHeader));//价税合计
        easPurchaseOrder.setLocalTotalAmount(easPurchaseOrder.getTotalAmount()); //本位币合计
        easPurchaseOrder.setLocalTotalTaxAmount(easPurchaseOrder.getTotalTaxAmount()); //本位币价税合计

        easPurchaseOrder.setEntries(easPurchaseOrderList);
        String purchaseOrderJson = JSON.toJSONString(easPurchaseOrder);
        return purchaseOrderJson;
    }

    /**
     * 组装采购入库单数据
     *
     * @param purchaseBasicOrderInbound         单头
     * @param purchaseBasicOrderItemInboundList 单体
     * @return
     */
    public static String buildPurchaseInbound(PurchaseBasicOrderInbound purchaseBasicOrderInbound,
                                              List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList) {
        EasPurchaseInboundOrder purchaseInboundOrder = new EasPurchaseInboundOrder();
        List<EasPurchaseInboundOrderItem> purchaseInboundOrderItemList = new ArrayList<>();
        String projectId = purchaseBasicOrderInbound.getProjectId();
        String supplierCode = purchaseBasicOrderInbound.getSupplierCode();
        double totalTaxAmount = purchaseBasicOrderInbound.getTotalTaxAmount();//含税金额
        double totalTax = purchaseBasicOrderInbound.getTotalTax(); //税额
        int number = purchaseBasicOrderInbound.getNumber();//入库数量

        purchaseInboundOrder.setSupplier(purchaseBasicOrderInbound.getSupplierCode());
        purchaseInboundOrder.setCurrency("CNY");
        purchaseInboundOrder.setExchangeRate(new BigDecimal(1));
        purchaseInboundOrder.setPaymentType("003");
        purchaseInboundOrder.setTotalLocalAmount(new BigDecimal(totalTaxAmount));
        purchaseInboundOrder.setPurchaseType("0");
        purchaseInboundOrder.setInTax(true);
        purchaseInboundOrder.setPriceInTax(true);
        purchaseInboundOrder.setDischargeType("0");
        purchaseInboundOrder.setGenBizAP(false);
        purchaseInboundOrder.setBillRelationOption("0");
        purchaseInboundOrder.setPriceSource("1");
        purchaseInboundOrder.setStorageOrgUnit(purchaseBasicOrderInbound.getProjectId());
        purchaseInboundOrder.setTotalQty(new BigDecimal(number));
        purchaseInboundOrder.setTotalAmount(new BigDecimal(totalTaxAmount - totalTax));
        purchaseInboundOrder.setTotalActualCost(new BigDecimal(totalTaxAmount));
        purchaseInboundOrder.setTransactionType("001");
        purchaseInboundOrder.setBaseStatus("4");//4 自动审核
        purchaseInboundOrder.setBizType("110");
        purchaseInboundOrder.setSourceBillType("220");
        purchaseInboundOrder.setBillTypeID("103");
        purchaseInboundOrder.setBizDate(purchaseBasicOrderInbound.getBusinessDate());
        purchaseInboundOrder.setCreator("user");
        purchaseInboundOrder.setControlUnit("0");
        purchaseInboundOrder.setId(purchaseBasicOrderInbound.getId());
        purchaseInboundOrder.setiD(purchaseBasicOrderInbound.getId());
        purchaseInboundOrder.setPaymentType("002"); //支付类型 赊销

        for (PurchaseBasicOrderItemInbound purchaseBasicOrderItemInbound : purchaseBasicOrderItemInboundList) {
            EasPurchaseInboundOrderItem purchaseInboundOrderItem = new EasPurchaseInboundOrderItem();
            double discount = purchaseBasicOrderItemInbound.getDiscount()/100;
            double taxPrice = purchaseBasicOrderItemInbound.getTaxPrice();  //含税单价
            double taxRate = purchaseBasicOrderItemInbound.getTaxRate();    //税率
            double price = taxPrice / (1 + taxRate / 100.0);    //不含税单价
            double actualTaxPrice = taxPrice * (1 - discount); //实际含税单价
            double actualPrice = price * (1 - discount); //实际单价

            int qty = purchaseBasicOrderItemInbound.getNumber();

            double discountAmount = taxPrice * qty * (discount); //折扣额 = 含税单价*数量*折扣
            double priceTaxAmountTotal = taxPrice * qty - discountAmount; //价税合计=含税单价*数量-折扣额
            double amount = actualPrice * qty; //金额=实际单价*数量
            double tax = priceTaxAmountTotal - amount;//税额

            purchaseInboundOrderItem.setUnWriteOffQty(new BigDecimal(0));
            purchaseInboundOrderItem.setUnWriteOffAmount(new BigDecimal(0));
            purchaseInboundOrderItem.setPurOrderID(purchaseBasicOrderItemInbound.getPurchaseOrderId());
            purchaseInboundOrderItem.setPurOrderEntryID(purchaseBasicOrderItemInbound.getPurchaseOrderEntryId());
            purchaseInboundOrderItem.setTaxRate(new BigDecimal(taxRate));
            purchaseInboundOrderItem.setTax(new BigDecimal(tax));
            purchaseInboundOrderItem.setLocalTax(new BigDecimal(tax));
            purchaseInboundOrderItem.setLocalPrice(new BigDecimal(price));
            purchaseInboundOrderItem.setLocalAmount(new BigDecimal(amount));
            purchaseInboundOrderItem.setUnWriteOffBaseQty(new BigDecimal(0));
            purchaseInboundOrderItem.setUnReturnedBaseQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setTaxPrice(new BigDecimal(taxPrice));
            purchaseInboundOrderItem.setActualPrice(new BigDecimal(actualPrice));
            purchaseInboundOrderItem.setPurchaseOrgUnit(projectId);
            purchaseInboundOrderItem.setCanDirectReqQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setCanDirectReqBaseQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setSupplierLotNo(supplierCode);
            purchaseInboundOrderItem.setReceiveStorageOrgUnit(projectId);
            purchaseInboundOrderItem.setPurchaseCost(new BigDecimal(amount));
            purchaseInboundOrderItem.setUnitPurchaseCost(new BigDecimal(actualPrice));
            purchaseInboundOrderItem.setTaxAmount(new BigDecimal(priceTaxAmountTotal));
            purchaseInboundOrderItem.setLocalTaxAmount(new BigDecimal(priceTaxAmountTotal));
            purchaseInboundOrderItem.setActualTaxPrice(new BigDecimal(actualTaxPrice));
            purchaseInboundOrderItem.setDiscountRate(new BigDecimal(purchaseBasicOrderInbound.getDiscount()));
            purchaseInboundOrderItem.setDiscountAmount(new BigDecimal(discountAmount));
            purchaseInboundOrderItem.setPrice(new BigDecimal(price));
            purchaseInboundOrderItem.setAmount(new BigDecimal(amount));
            purchaseInboundOrderItem.setBizDate(purchaseBasicOrderInbound.getRequireArrivalDate());
            purchaseInboundOrderItem.setStorageOrgUnit(projectId);
            purchaseInboundOrderItem.setCompanyOrgUnit(projectId.substring(0, 4));
            purchaseInboundOrderItem.setWarehouse(purchaseBasicOrderItemInbound.getWarehouseCode());
            purchaseInboundOrderItem.setLocation(purchaseBasicOrderItemInbound.getLocationId());
            purchaseInboundOrderItem.setLot(purchaseBasicOrderItemInbound.getLot());
            purchaseInboundOrderItem.setQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setAssistQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setBaseQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setUnitActualCost(new BigDecimal(price));
            purchaseInboundOrderItem.setPresent(false);
            purchaseInboundOrderItem.setMaterial(purchaseBasicOrderItemInbound.getMaterialId());
            purchaseInboundOrderItem.setUnit(purchaseBasicOrderItemInbound.getUnit());
            purchaseInboundOrderItem.setBaseStatus("4");
            purchaseInboundOrderItem.setAssociateQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setSourceBillType("220");
            purchaseInboundOrderItem.setId(purchaseBasicOrderItemInbound.getPurchaseOrderEntryId());
            purchaseInboundOrderItem.setiD(purchaseBasicOrderItemInbound.getPurchaseOrderEntryId());
            purchaseInboundOrderItem.setInvUpdateType("001");//更新类型 普通入库

            purchaseInboundOrderItemList.add(purchaseInboundOrderItem);
        }
        purchaseInboundOrder.setEntries(purchaseInboundOrderItemList);
        String purchaseOrderInboundJson = JSON.toJSONString(purchaseInboundOrder);
        return purchaseOrderInboundJson;
    }

    /**
     * 组装销售订单信息
     *
     * @param saleOrder         销售单头
     * @param saleOrderItemList 销售单体
     * @return
     */
    public static String buildSaleOrder(SaleOrder saleOrder, List<SaleOrderItem> saleOrderItemList) {
        String projectId = saleOrder.getProjectId();
        String currencyCode = saleOrder.getCurrencyCode();
        double totalTaxOrder = saleOrder.getTotalTax(); //税额
        double totalTaxAmountOrder = saleOrder.getTotalTaxAmount(); //价税合计

        EasSaleOrder easSaleOrder = new EasSaleOrder();
        List<EasSaleOrderItem> easSaleOrderItemList = new ArrayList<>();

        easSaleOrder.setOrderCustomer(saleOrder.getCustomerId());
        easSaleOrder.setDeliveryType("SEND");
        easSaleOrder.setCurrency(currencyCode);
        easSaleOrder.setExchangeRate(new BigDecimal(1));
        easSaleOrder.setPaymentType("001");
        easSaleOrder.setSettlementType("002");
        easSaleOrder.setSaleOrgUnit(projectId);
        easSaleOrder.setTotalAmount(new BigDecimal(totalTaxAmountOrder - totalTaxOrder));
        easSaleOrder.setTotalTax(new BigDecimal(totalTaxOrder));
        easSaleOrder.setTotalTaxAmount(new BigDecimal(totalTaxAmountOrder));
        easSaleOrder.setLocalTotalAmount(new BigDecimal(totalTaxAmountOrder - totalTaxOrder));
        easSaleOrder.setLocalTotalTaxAmount(new BigDecimal(totalTaxAmountOrder));
        easSaleOrder.setCompanyOrgUnit(projectId.substring(0, 4));
        easSaleOrder.setIsInTax(1);
        easSaleOrder.setStorageOrgUnit(projectId);
        easSaleOrder.setBaseStatus("4");//1 保存 4 已审核
        easSaleOrder.setBizType("210");
        easSaleOrder.setBillType("310");
        easSaleOrder.setBizDate(new Date());
        easSaleOrder.setCreator("user");
        for (SaleOrderItem saleOrderItem : saleOrderItemList) {
            String customerId = saleOrderItem.getCustomerId();
            double taxPrice = saleOrderItem.getTaxPrice();  //含税单价
            double taxRate = saleOrderItem.getTaxRate();    //税率
            double price = taxPrice / (1 + taxRate / 100.0);    //不含税单价
            double discount = saleOrderItem.getDiscount() / 100.0; //折扣率
            double actualTaxPrice = taxPrice * (1 - discount); //实际含税单价
            double actualPrice = price * (1 - discount); //实际单价

            int qty = saleOrderItem.getNumber();

            double discountAmount = taxPrice * qty * (discount); //折扣额 = 含税单价*数量*折扣
            double priceTaxAmountTotal = taxPrice * qty - discountAmount; //价税合计=含税单价*数量-折扣额
            double amount = actualPrice * qty; //金额=实际单价*数量
            double tax = priceTaxAmountTotal - amount;//税额

            EasSaleOrderItem easSaleOrderItem = new EasSaleOrderItem();
            easSaleOrderItem.setIsPresent(0);
            easSaleOrderItem.setBaseQty(new BigDecimal(qty));
            easSaleOrderItem.setQty(new BigDecimal(qty));
            easSaleOrderItem.setAssistQty(new BigDecimal(qty));
            easSaleOrderItem.setPrice(new BigDecimal(price));
            easSaleOrderItem.setTaxPrice(new BigDecimal(taxPrice));
            easSaleOrderItem.setAmount(new BigDecimal(amount));
            easSaleOrderItem.setLocalAmount(new BigDecimal(amount));
            easSaleOrderItem.setActualPrice(new BigDecimal(actualPrice));
            easSaleOrderItem.setActualTaxPrice(new BigDecimal(actualTaxPrice));
            easSaleOrderItem.setTaxRate(new BigDecimal(taxRate));
            easSaleOrderItem.setTax(new BigDecimal(tax));
            easSaleOrderItem.setTaxAmount(new BigDecimal(priceTaxAmountTotal));
            easSaleOrderItem.setStorageOrgUnit(projectId);
            easSaleOrderItem.setCompanyOrgUnit(projectId.substring(0, 4));
            easSaleOrderItem.setWarehouse(saleOrderItem.getWarehouseId());
            easSaleOrderItem.setTotalIssueQty(new BigDecimal(0));
            easSaleOrderItem.setTotalIssueBaseQty(new BigDecimal(0));
            easSaleOrderItem.setTotalUnReturnBaseQty(new BigDecimal(qty));
            easSaleOrderItem.setTotalUnIssueBaseQty(new BigDecimal(qty));
            easSaleOrderItem.setTotalUnShipBaseQty(new BigDecimal(qty));
            easSaleOrderItem.setTotalUnIssueQty(new BigDecimal(qty));
            easSaleOrderItem.setLocalTax(new BigDecimal(tax));
            easSaleOrderItem.setLocalTaxAmount(new BigDecimal(priceTaxAmountTotal));
            easSaleOrderItem.setDeliveryCustomer(customerId);
            easSaleOrderItem.setPaymentCustomer(customerId);
            easSaleOrderItem.setReceiveCustomer(customerId);
            easSaleOrderItem.setSaleOrgUnit(projectId);
            easSaleOrderItem.setBizDate(new Date());
            easSaleOrderItem.setMaterial(saleOrderItem.getMaterialId());
            easSaleOrderItem.setUnit("TAI");
            easSaleOrderItem.setBaseUnit("TAI");
            easSaleOrderItem.setBaseStatus("4");
            easSaleOrderItem.setAssistQty(new BigDecimal(qty));
            easSaleOrderItem.setBaseUnit(saleOrderItem.getUnit());
            easSaleOrderItem.setDeliveryDate(saleOrderItem.getDeliveryDate());
            easSaleOrderItem.setSendDate(saleOrderItem.getSendDate());
            easSaleOrderItem.setDiscountAmount(new BigDecimal(discountAmount));
            easSaleOrderItem.setDiscount(new BigDecimal(saleOrderItem.getDiscount()));
            easSaleOrderItem.setInvUpdateType("002");
            easSaleOrderItemList.add(easSaleOrderItem);
        }
        easSaleOrder.setEntries(easSaleOrderItemList);
        String saleOrderJson = JSON.toJSONString(easSaleOrder);
        return saleOrderJson;
    }

    /**
     * 拼装销售出库单数据
     *
     * @param saleOutOrder      销售出库单
     * @param saleOutOrderItems 销售出库单货品
     * @return
     */
    public static String buildSaleOutOrder(SaleOutOrder saleOutOrder, List<SaleOutOrderItem> saleOutOrderItems) {
        EasSaleOutOrder easSaleOutOrder = new EasSaleOutOrder();
        List<EasSaleOutOrderItem> easSaleOutOrderItemList = new ArrayList<>();

        String projectId = saleOutOrder.getProjectId();
        double totalTaxAmountOrder = saleOutOrder.getTotalTaxAmount();
        double taxAmountOrder = saleOutOrder.getTaxAmount();
        int saleOutNumberOrder = saleOutOrder.getTotalQuantity();


        easSaleOutOrder.setCustomer(saleOutOrder.getCustomerId());
        easSaleOutOrder.setCurrency(saleOutOrder.getCurrencyId());
        easSaleOutOrder.setExchangeRate(new BigDecimal(1));
        easSaleOutOrder.setPaymentType("002");
        easSaleOutOrder.setBizDate(new Date());
        easSaleOutOrder.setInTax(true);
        easSaleOutOrder.setTotalLocalAmount(new BigDecimal(totalTaxAmountOrder));
        easSaleOutOrder.setPriceSource("1");
        easSaleOutOrder.setStorageOrgUnit(projectId);
        easSaleOutOrder.setTotalQty(new BigDecimal(saleOutNumberOrder));
        easSaleOutOrder.setTotalAmount(new BigDecimal(totalTaxAmountOrder - taxAmountOrder));
        easSaleOutOrder.setTransactionType("010");
        easSaleOutOrder.setBaseStatus("4");//1 保存 4 已审核
        easSaleOutOrder.setBizType("210");
        easSaleOutOrder.setSourceBillType("310");
        easSaleOutOrder.setBillType("102");
        easSaleOutOrder.setCreator("user");
        easSaleOutOrder.setCreateTime(new Date());
        easSaleOutOrder.setControlUnit("0");
        easSaleOutOrder.setSaleOrgUnitID(projectId);
        easSaleOutOrder.setId(saleOutOrder.getId());

        for (SaleOutOrderItem saleOutOrderItem : saleOutOrderItems) {
            EasSaleOutOrderItem easSaleOutOrderItem = new EasSaleOutOrderItem();

            String customerId = saleOutOrderItem.getCustomerId();
            double taxPrice = saleOutOrderItem.getTaxPrice();  //含税单价
            double taxRate = saleOutOrderItem.getTaxRate();    //税率
            double price = taxPrice / (1 + taxRate / 100.0);    //不含税单价
            double discount = saleOutOrderItem.getDiscount() / 100; //折扣率
            double actualTaxPrice = taxPrice * (1 - discount); //实际含税单价
            double actualPrice = price * (1 - discount); //实际单价

            int qty = saleOutOrderItem.getNumber();

            double discountAmount = taxPrice * qty * (discount); //折扣额 = 含税单价*数量*折扣
            double priceTaxAmountTotal = taxPrice * qty - discountAmount; //价税合计=含税单价*数量-折扣额
            double amount = actualPrice * qty; //金额=实际单价*数量
            double tax = priceTaxAmountTotal - amount;//税额
            easSaleOutOrderItem.setUnWriteOffQty(new BigDecimal(0));
            easSaleOutOrderItem.setUnWriteOffAmount(new BigDecimal(0));
            easSaleOutOrderItem.setTaxRate(new BigDecimal(taxRate));
            easSaleOutOrderItem.setTax(new BigDecimal(tax));
            easSaleOutOrderItem.setLocalTax(new BigDecimal(tax));
            easSaleOutOrderItem.setLocalPrice(new BigDecimal(price));
            easSaleOutOrderItem.setUnWriteOffBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnReturnedBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setTaxPrice(new BigDecimal(taxPrice));
            easSaleOutOrderItem.setActualPrice(new BigDecimal(actualTaxPrice));
            easSaleOutOrderItem.setSaleOrgUnit(projectId);
            easSaleOutOrderItem.setUndeliverQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUndeliverBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnInQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnInBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setBalanceCustomer(saleOutOrderItem.getCustomerId());
            easSaleOutOrderItem.setBaseUnitActualcost(new BigDecimal(price));
            easSaleOutOrderItem.setOrderCustomer(saleOutOrderItem.getCustomerId());
            easSaleOutOrderItem.setPaymentCustomer(saleOutOrderItem.getCustomerId());
            easSaleOutOrderItem.setAssociateBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setSalePrice(new BigDecimal(price));
            easSaleOutOrderItem.setDiscountType("0");
            easSaleOutOrderItem.setDiscountAmount(new BigDecimal(discountAmount));
            easSaleOutOrderItem.setDiscount(new BigDecimal(saleOutOrderItem.getDiscount()));
            easSaleOutOrderItem.setPrice(new BigDecimal(actualTaxPrice));
            easSaleOutOrderItem.setAmount(new BigDecimal(priceTaxAmountTotal));
            easSaleOutOrderItem.setNonTaxAmount(new BigDecimal(amount));
            easSaleOutOrderItem.setLocalNonTaxAmount(new BigDecimal(priceTaxAmountTotal));
            easSaleOutOrderItem.setLocalAmount(new BigDecimal(priceTaxAmountTotal));
            easSaleOutOrderItem.setUnSettleQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnSettleBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setBizDate(new Date());
            easSaleOutOrderItem.setFullWriteOff(false);
            easSaleOutOrderItem.setStorageOrgUnit(projectId);
            easSaleOutOrderItem.setCompanyOrgUnit(projectId.substring(0, 4));
            easSaleOutOrderItem.setWarehouse(saleOutOrderItem.getWarehouseId());
            easSaleOutOrderItem.setLocation(saleOutOrderItem.getLocationId());
            easSaleOutOrderItem.setLot(saleOutOrderItem.getLot());
            easSaleOutOrderItem.setQty(new BigDecimal(qty));
            easSaleOutOrderItem.setAssistQty(new BigDecimal(qty));
            easSaleOutOrderItem.setBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnitActualCost(new BigDecimal(actualPrice));
            easSaleOutOrderItem.setUnSettleQty(new BigDecimal(saleOutOrderItem.getNumber()));
            easSaleOutOrderItem.setUnSettleBaseQty(new BigDecimal(saleOutOrderItem.getNumber()));
            easSaleOutOrderItem.setIsPresent(0);
            easSaleOutOrderItem.setMaterial(saleOutOrderItem.getMaterialId());
            easSaleOutOrderItem.setUnit(saleOutOrderItem.getUnit());
            easSaleOutOrderItem.setBaseUnit(saleOutOrderItem.getUnit());

            easSaleOutOrderItem.setSourceBill(saleOutOrderItem.getSourceBillId());
            easSaleOutOrderItem.setBaseStatus("4");//1 保存 4 已审核
            easSaleOutOrderItem.setAssociateQty(new BigDecimal(qty));
            easSaleOutOrderItem.setSourceBillType("310");

            easSaleOutOrderItem.setId(saleOutOrderItem.getSourceBillId());//来源单据ID
            easSaleOutOrderItem.setSourceBill(saleOutOrderItem.getSourceBillId());//来源单据ID
            easSaleOutOrderItem.setSaleOrder(saleOutOrderItem.getSourceBillId());//来源单据ID
            easSaleOutOrderItem.setSourceBillNumber(saleOutOrderItem.getSourceOrderNumber());//来源单据单号
            easSaleOutOrderItem.setSaleOrderNumber(saleOutOrderItem.getSourceOrderNumber());//来源单据单号
            easSaleOutOrderItem.setSourceBillEntry(saleOutOrderItem.getSourceItemBillId());//来源单据分录ID
            easSaleOutOrderItem.setSaleOrderEntry(saleOutOrderItem.getSourceItemBillId());//来源单据分录ID


            easSaleOutOrderItem.setUnitActualCost(new BigDecimal(actualPrice));
            easSaleOutOrderItem.setActualCost(new BigDecimal(amount));
            easSaleOutOrderItem.setActualPrice(new BigDecimal(actualPrice));
            easSaleOutOrderItem.setAssistQty(new BigDecimal(0));
            easSaleOutOrderItem.setInvUpdateType("002");
            easSaleOutOrderItemList.add(easSaleOutOrderItem);
        }
        easSaleOutOrder.setEntries(easSaleOutOrderItemList);
        String saleOutJson = JSON.toJSONString(easSaleOutOrder);
        System.out.println(saleOutJson);
        return saleOutJson;
    }

    /**
     * 组装其他入库数据
     *
     * @param otherInWare         单头
     * @param otherInWareItemList 单体
     * @return
     */
    public static String buildOtherInWare(OtherInWare otherInWare, List<OtherInWareItem> otherInWareItemList) {
        EasOtherInWareOrder easOtherInWareOrder = new EasOtherInWareOrder();
        List<EasOtherInWareOrderItem> easOtherInWareOrderItemList = new ArrayList<>();

        String projectId = otherInWare.getProjectId();
        double quantity = otherInWare.getNumber();
        double totalPriceAmount = otherInWare.getTotalTaxAmount();

        easOtherInWareOrder.setStorageOrgUnit(projectId);
        easOtherInWareOrder.setTotalQty(quantity);
        easOtherInWareOrder.setTotalAmount(totalPriceAmount);
        easOtherInWareOrder.setTransactionType("032");
        easOtherInWareOrder.setBaseStatus("1");
        easOtherInWareOrder.setBizType("500");
        easOtherInWareOrder.setBillType("109");
        easOtherInWareOrder.setBizDate(new Date());
        easOtherInWareOrder.setCreator(otherInWare.getCreatorId());
        easOtherInWareOrder.setCreateTime(new Date());
        easOtherInWareOrder.setCostCenterOrgUnit("0");
        for (OtherInWareItem otherInWareItem : otherInWareItemList) {
            double price = otherInWareItem.getTaxPrice();
            double inWareNumber = otherInWareItem.getNumber();
            String warehouseId = otherInWareItem.getWarehouseId();
            String locationId = otherInWareItem.getWarehouseId() + "." + otherInWareItem.getLocationId();
            String sourceBillId = otherInWareItem.getSourceBillId();
            String materialId = otherInWareItem.getMaterialId();

            EasOtherInWareOrderItem easOtherInWareOrderItem = new EasOtherInWareOrderItem();
            easOtherInWareOrderItem.setStoreTypeID("G");
            easOtherInWareOrderItem.setStoreStatusID("1");
            easOtherInWareOrderItem.setPrice(price);
            easOtherInWareOrderItem.setAmount(price * inWareNumber);
            easOtherInWareOrderItem.setBizDate(new Date());
            easOtherInWareOrderItem.setStorageOrgUnitID(projectId);
            easOtherInWareOrderItem.setCompanyOrgUnit(projectId.substring(0, 4));
            easOtherInWareOrderItem.setWarehouse(warehouseId);
            easOtherInWareOrderItem.setLocation(locationId);
            easOtherInWareOrderItem.setLot(otherInWareItem.getLot());
            easOtherInWareOrderItem.setQty(inWareNumber);
            easOtherInWareOrderItem.setAssistQty(inWareNumber);
            easOtherInWareOrderItem.setBaseQty(inWareNumber);
            easOtherInWareOrderItem.setUnitActualCost(price);
            easOtherInWareOrderItem.setActualCost(price * inWareNumber);
            easOtherInWareOrderItem.setPresent(false);
            easOtherInWareOrderItem.setMaterial(materialId);
            easOtherInWareOrderItem.setUnit("TAI");
            easOtherInWareOrderItem.setSourceBillId(sourceBillId);
            easOtherInWareOrderItem.setBaseStatus("1");
            easOtherInWareOrderItem.setAssistQty(inWareNumber);
            easOtherInWareOrderItem.setInvUpdateType(otherInWareItem.getInvUpdateType());
            easOtherInWareOrderItemList.add(easOtherInWareOrderItem);
        }
        easOtherInWareOrder.setEntries(easOtherInWareOrderItemList);
        String saleOutJson = JSON.toJSONString(easOtherInWareOrder);
        return saleOutJson;
    }

    /**
     * 组装其他出库数据
     *
     * @param otherOutWare         单头
     * @param otherOutWareItemList 单体
     * @return
     */
    public static String buildOtherOutWare(OtherOutWare otherOutWare, List<OtherOutWareItem> otherOutWareItemList) {
        EasOtherOutWareOrder easOtherOutWareOrder = new EasOtherOutWareOrder();
        List<EasOtherOutWareOrderItem> easOtherOutWareOrderItemList = new ArrayList<>();

        String projectId = otherOutWare.getProjectId();
        int qty = otherOutWare.getNumber();
        double totalAmount = otherOutWare.getTotalTaxAmount();

        easOtherOutWareOrder.setStorageOrgUnit(projectId);
        easOtherOutWareOrder.setTotalQty(qty);
        easOtherOutWareOrder.setTotalAmount(totalAmount);
        easOtherOutWareOrder.setTransactionType("029");
        easOtherOutWareOrder.setBaseStatus("1");
        easOtherOutWareOrder.setBizType("510");
        easOtherOutWareOrder.setBillType("108");
        easOtherOutWareOrder.setBizDate(new Date());
        easOtherOutWareOrder.setCreator(otherOutWare.getCreatorId());
        easOtherOutWareOrder.setCreateTime(new Date());
        easOtherOutWareOrder.setControlUnitID("0");

        for (OtherOutWareItem otherOutWareItem : otherOutWareItemList) {
            double number = otherOutWareItem.getNumber();
            double price = otherOutWareItem.getTaxPrice();
            int outWareNumber = otherOutWareItem.getNumber();
            String locationId = otherOutWareItem.getLocationId();
            String warehouseId = otherOutWareItem.getWarehouseId();
            String lot = otherOutWareItem.getLot();

            EasOtherOutWareOrderItem easOtherOutWareOrderItem = new EasOtherOutWareOrderItem();
            easOtherOutWareOrderItem.setStoreTypeID("G");
            easOtherOutWareOrderItem.setStoreStatusId("1");
            easOtherOutWareOrderItem.setBaseUnitActualcost(price);
            easOtherOutWareOrderItem.setPrice(price);
            easOtherOutWareOrderItem.setAmount(number * price);
            easOtherOutWareOrderItem.setBizDate(new Date());
            easOtherOutWareOrderItem.setStorageOrgUnit(projectId);
            easOtherOutWareOrderItem.setCompanyOrgUnit(projectId.substring(0, 4));
            easOtherOutWareOrderItem.setWarehouse(warehouseId);
            easOtherOutWareOrderItem.setLocation(locationId);
            easOtherOutWareOrderItem.setLot(lot);
            easOtherOutWareOrderItem.setQty(outWareNumber);
            easOtherOutWareOrderItem.setAssistQty(outWareNumber);
            easOtherOutWareOrderItem.setBaseQty(outWareNumber);
            easOtherOutWareOrderItem.setUnitActualCost(price);
            easOtherOutWareOrderItem.setActualCost(number * price);
            easOtherOutWareOrderItem.setPresent(false);
            easOtherOutWareOrderItem.setMaterial(otherOutWareItem.getMaterialId());
            easOtherOutWareOrderItem.setUnit(otherOutWareItem.getUnit());
            easOtherOutWareOrderItem.setSourceBillID(otherOutWareItem.getSourceBillId());
            easOtherOutWareOrderItem.setBaseStatus("1");
            easOtherOutWareOrderItem.setAssociateQty(outWareNumber);
            easOtherOutWareOrderItem.setBaseUnit("TAI");
            easOtherOutWareOrderItemList.add(easOtherOutWareOrderItem);
        }
        easOtherOutWareOrder.setEntries(easOtherOutWareOrderItemList);
        String saleOutJson = JSON.toJSONString(easOtherOutWareOrder);
        System.out.println(saleOutJson);
        return saleOutJson;
    }

}
