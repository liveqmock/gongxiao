package com.yhglobal.gongxiao.util;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.constant.EasConstant;
import com.yhglobal.gongxiao.diaobo.*;
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
import com.yhglobal.gongxiao.login.EASLoginSoapBindingStub;
import com.yhglobal.gongxiao.login.WSContext;
import com.yhglobal.gongxiao.model.EASEnvironmentModel;
import com.yhglobal.gongxiao.model.EasOtherInWareOrder;
import com.yhglobal.gongxiao.model.EasOtherInWareOrderItem;
import com.yhglobal.gongxiao.model.EasOtherOutWareOrder;
import com.yhglobal.gongxiao.model.EasOtherOutWareOrderItem;
import com.yhglobal.gongxiao.model.EasPurchaseInboundOrder;
import com.yhglobal.gongxiao.model.EasPurchaseInboundOrderItem;
import com.yhglobal.gongxiao.model.EasPurchaseOrder;
import com.yhglobal.gongxiao.model.EasPurchaseOrderItem;
import com.yhglobal.gongxiao.model.EasSaleOrder;
import com.yhglobal.gongxiao.model.EasSaleOrderItem;
import com.yhglobal.gongxiao.model.EasSaleOutOrder;
import com.yhglobal.gongxiao.model.EasSaleOutOrderItem;
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

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
        logger.info("登陆信息{}", easEnvironmentModel.toString());

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
        double totalTaxAmountHeader = 0;
        double amountHeader = 0;
        double discountAmountHeader = 0;
        double totalTaxHeader = 0;
        //设置单体信息
        for (PurchaseBasicOrderItem purchaseBasicOrderItem : purchaseBasicOrderItemList) {
            double taxPrice = ScaleUtils.getRoundUpValue(6,purchaseBasicOrderItem.getTaxPrice()) ; //含税单价
            double taxRate = ScaleUtils.getRoundUpValue(6,purchaseBasicOrderItem.getTaxRate()) ;  //税率
            double price = ScaleUtils.getRoundUpValue(6,taxPrice / (1 + taxRate / 100.0));  //不含税单价
            double discountRate = ScaleUtils.getRoundUpValue(6,purchaseBasicOrderItem.getDiscountRate() / 100);//折扣率
            double actualTxaPrice = ScaleUtils.getRoundUpValue(6,taxPrice * (1 - discountRate));    //实际含税单价
            double actualPrice = ScaleUtils.getRoundUpValue(6,price * (1 - discountRate)); //实际单价

            int number = purchaseBasicOrderItem.getNumber();
            double discountAmount = ScaleUtils.getRoundUpValue(6,taxPrice * number * (discountRate)); //折扣额 = 含税单价*数量*折扣
            double priceTaxAmountTotal = ScaleUtils.getRoundUpValue(6,taxPrice * number - discountAmount); //价税合计=含税单价*数量-折扣额
            double amount = ScaleUtils.getRoundUpValue(6,actualPrice * number); //金额=实际单价*数量
            double tax = ScaleUtils.getRoundUpValue(6,priceTaxAmountTotal - amount);//税额
            logger.info("价税合计={}金额={}税额={}", priceTaxAmountTotal, amount, tax);
            totalTaxAmountHeader += priceTaxAmountTotal;
            amountHeader += amount;
            discountAmountHeader += discountAmount;
            totalTaxHeader += tax;

            EasPurchaseOrderItem easPurchaseOrderItem = new EasPurchaseOrderItem();
            easPurchaseOrderItem.setPresent(false);
            easPurchaseOrderItem.setDemandQty(new BigDecimal(number));
            easPurchaseOrderItem.setQty(easPurchaseOrderItem.getDemandQty());
            easPurchaseOrderItem.setStorageOrgUnit(purchaseBasicOrder.getProjectId());
            easPurchaseOrderItem.setCompanyOrgUnit(purchaseBasicOrder.getProjectId().substring(0, 4)); //财务组织=项目的前四位
            easPurchaseOrderItem.setAssistQty(easPurchaseOrderItem.getDemandQty());
            easPurchaseOrderItem.setPrice(ScaleUtils.doubleToBigDecimal(6,price)); //单价=含税单价(1-税率)
            easPurchaseOrderItem.setDiscountRate(ScaleUtils.doubleToBigDecimal(6,purchaseBasicOrderItem.getDiscountRate()));
            easPurchaseOrderItem.setActualPrice(ScaleUtils.doubleToBigDecimal(6,actualPrice)); //
            easPurchaseOrderItem.setTaxRate(ScaleUtils.doubleToBigDecimal(6,taxRate));
            easPurchaseOrderItem.setTaxPrice(ScaleUtils.doubleToBigDecimal(6,taxPrice));
            easPurchaseOrderItem.setActualTaxPrice(ScaleUtils.doubleToBigDecimal(6,actualTxaPrice));
            easPurchaseOrderItem.setAmount(ScaleUtils.doubleToBigDecimal(6,actualPrice * number));
            easPurchaseOrderItem.setLocalAmount(easPurchaseOrderItem.getAmount());
            easPurchaseOrderItem.setTax(ScaleUtils.doubleToBigDecimal(6,tax));
            easPurchaseOrderItem.setTaxAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));
            easPurchaseOrderItem.setDiscountAmount(ScaleUtils.doubleToBigDecimal(6,discountAmount));
            easPurchaseOrderItem.setDeliveryDate(purchaseBasicOrderItem.getBizDate());//交货日期 = 业务日期,推EAS才会生效
            easPurchaseOrderItem.setBizDate(purchaseBasicOrderItem.getBizDate());
            easPurchaseOrderItem.setBaseQty(new BigDecimal(number));
            easPurchaseOrderItem.setLocalTaxAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));//本位币价税合计=本位币含税单价*数量
            easPurchaseOrderItem.setLocalTax(ScaleUtils.doubleToBigDecimal(6,tax));//本位币税额=本币价税合计-本币的金额
            easPurchaseOrderItem.setRequestOrgUnit(purchaseBasicOrder.getProjectId());
            easPurchaseOrderItem.setWareHouse(purchaseBasicOrderItem.getWarehouseId());
            easPurchaseOrderItem.setReqComEqlRecCom(true);
            easPurchaseOrderItem.setMaterial(purchaseBasicOrderItem.getMaterialId());
            easPurchaseOrderItem.setUnit("TAI");//默认单位为个
            easPurchaseOrderItem.setBaseStatus("4");
            easPurchaseOrderItem.setAssociateQty(new BigDecimal(number));
            easPurchaseOrderItem.setBaseUnit("TAI");
            easPurchaseOrderItem.setAssistUnit("TAI");
            easPurchaseOrderList.add(easPurchaseOrderItem);
        }
        easPurchaseOrder.setTotalAmount(ScaleUtils.doubleToBigDecimal(6,amountHeader));
        easPurchaseOrder.setTotalTax(ScaleUtils.doubleToBigDecimal(6,totalTaxHeader));
        easPurchaseOrder.setTotalTaxAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmountHeader));//价税合计
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
        double totalTaxAmount = ScaleUtils.getRoundUpValue(6,purchaseBasicOrderInbound.getTotalTaxAmount());//含税金额
        double totalTax = ScaleUtils.getRoundUpValue(6,purchaseBasicOrderInbound.getTotalTax()); //税额
        int number = purchaseBasicOrderInbound.getNumber();//入库数量

        purchaseInboundOrder.setSupplier(purchaseBasicOrderInbound.getSupplierCode());
        purchaseInboundOrder.setCurrency("CNY");
        purchaseInboundOrder.setExchangeRate(new BigDecimal(1));
        purchaseInboundOrder.setPaymentType("003");
        purchaseInboundOrder.setTotalLocalAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmount));
        purchaseInboundOrder.setPurchaseType("0");
        purchaseInboundOrder.setInTax(true);
        purchaseInboundOrder.setPriceInTax(true);
        purchaseInboundOrder.setDischargeType("0");
        purchaseInboundOrder.setGenBizAP(false);
        purchaseInboundOrder.setBillRelationOption("0");
        purchaseInboundOrder.setPriceSource("1");
        purchaseInboundOrder.setStorageOrgUnit(purchaseBasicOrderInbound.getProjectId());
        purchaseInboundOrder.setTotalQty(new BigDecimal(number));
        purchaseInboundOrder.setTotalAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmount - totalTax));
        purchaseInboundOrder.setTotalActualCost(ScaleUtils.doubleToBigDecimal(6,totalTaxAmount));
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
            double discount = ScaleUtils.getRoundUpValue(6,purchaseBasicOrderItemInbound.getDiscount() / 100);;
            double taxPrice = ScaleUtils.getRoundUpValue(6,purchaseBasicOrderItemInbound.getTaxPrice());  //含税单价
            double taxRate = ScaleUtils.getRoundUpValue(6,purchaseBasicOrderItemInbound.getTaxRate());    //税率
            double price = ScaleUtils.getRoundUpValue(6,taxPrice / (1 + taxRate / 100.0));    //不含税单价
            double actualTaxPrice = ScaleUtils.getRoundUpValue(6,taxPrice * (1 - discount)); //实际含税单价
            double actualPrice = ScaleUtils.getRoundUpValue(6,price * (1 - discount)); //实际单价

            int qty = purchaseBasicOrderItemInbound.getNumber();

            double discountAmount = ScaleUtils.getRoundUpValue(6,taxPrice * qty * (discount)); //折扣额 = 含税单价*数量*折扣
            double priceTaxAmountTotal = ScaleUtils.getRoundUpValue(6,taxPrice * qty - discountAmount); //价税合计=含税单价*数量-折扣额
            double amount =ScaleUtils.getRoundUpValue(6, actualPrice * qty); //金额=实际单价*数量
            double tax = ScaleUtils.getRoundUpValue(6, priceTaxAmountTotal - amount);//税额

            purchaseInboundOrderItem.setUnWriteOffQty(new BigDecimal(0));
            purchaseInboundOrderItem.setUnWriteOffAmount(new BigDecimal(0));
            purchaseInboundOrderItem.setPurOrderID(purchaseBasicOrderItemInbound.getPurchaseOrderId());
            purchaseInboundOrderItem.setPurOrderEntryID(purchaseBasicOrderItemInbound.getPurchaseOrderEntryId());
            purchaseInboundOrderItem.setTaxRate(ScaleUtils.doubleToBigDecimal(6,taxRate));
            purchaseInboundOrderItem.setTax(ScaleUtils.doubleToBigDecimal(6,tax));
            purchaseInboundOrderItem.setLocalTax(ScaleUtils.doubleToBigDecimal(6,tax));
            purchaseInboundOrderItem.setLocalPrice(ScaleUtils.doubleToBigDecimal(6,price));
            purchaseInboundOrderItem.setLocalAmount(ScaleUtils.doubleToBigDecimal(6,amount));
            purchaseInboundOrderItem.setUnWriteOffBaseQty(new BigDecimal(0));
            purchaseInboundOrderItem.setUnReturnedBaseQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setTaxPrice(ScaleUtils.doubleToBigDecimal(6,taxPrice));
            purchaseInboundOrderItem.setActualPrice(ScaleUtils.doubleToBigDecimal(6,actualPrice));
            purchaseInboundOrderItem.setPurchaseOrgUnit(projectId);
            purchaseInboundOrderItem.setCanDirectReqQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setCanDirectReqBaseQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setSupplierLotNo(supplierCode);
            purchaseInboundOrderItem.setReceiveStorageOrgUnit(projectId);
            purchaseInboundOrderItem.setPurchaseCost(ScaleUtils.doubleToBigDecimal(6,amount));
            purchaseInboundOrderItem.setUnitPurchaseCost(ScaleUtils.doubleToBigDecimal(6,actualPrice));
            purchaseInboundOrderItem.setTaxAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));
            purchaseInboundOrderItem.setLocalTaxAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));
            purchaseInboundOrderItem.setActualTaxPrice(ScaleUtils.doubleToBigDecimal(6,actualTaxPrice));
            purchaseInboundOrderItem.setDiscountRate(ScaleUtils.doubleToBigDecimal(6,purchaseBasicOrderInbound.getDiscount()));
            purchaseInboundOrderItem.setDiscountAmount(ScaleUtils.doubleToBigDecimal(6,discountAmount));
            purchaseInboundOrderItem.setPrice(ScaleUtils.doubleToBigDecimal(6,price));
            purchaseInboundOrderItem.setAmount(ScaleUtils.doubleToBigDecimal(6,amount));
            purchaseInboundOrderItem.setBizDate(purchaseBasicOrderInbound.getBusinessDate());
            purchaseInboundOrderItem.setStorageOrgUnit(projectId);
            purchaseInboundOrderItem.setCompanyOrgUnit(projectId.substring(0, 4));
            purchaseInboundOrderItem.setWarehouse(purchaseBasicOrderItemInbound.getWarehouseCode());
            purchaseInboundOrderItem.setLocation(purchaseBasicOrderItemInbound.getLocationId());
            purchaseInboundOrderItem.setLot(purchaseBasicOrderItemInbound.getLot());
            purchaseInboundOrderItem.setQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setAssistQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setBaseQty(new BigDecimal(qty));
            purchaseInboundOrderItem.setUnitActualCost(ScaleUtils.doubleToBigDecimal(6,price));
            purchaseInboundOrderItem.setPresent(false);
            purchaseInboundOrderItem.setMaterial(purchaseBasicOrderItemInbound.getMaterialId());
            purchaseInboundOrderItem.setUnit("TAI");
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
        double totalTaxOrder = ScaleUtils.getRoundUpValue(6,saleOrder.getTotalTax()); //税额
        double totalTaxAmountOrder = ScaleUtils.getRoundUpValue(6,saleOrder.getTotalTaxAmount()); //价税合计

        EasSaleOrder easSaleOrder = new EasSaleOrder();
        List<EasSaleOrderItem> easSaleOrderItemList = new ArrayList<>();

        easSaleOrder.setOrderCustomer(saleOrder.getCustomerId());
        easSaleOrder.setDeliveryType("SEND");
        easSaleOrder.setCurrency(currencyCode);
        easSaleOrder.setExchangeRate(new BigDecimal(1));
        easSaleOrder.setPaymentType("001");
        easSaleOrder.setSettlementType("002");
        easSaleOrder.setSaleOrgUnit(projectId);
        easSaleOrder.setTotalAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmountOrder - totalTaxOrder));
        easSaleOrder.setTotalTax(ScaleUtils.doubleToBigDecimal(6,totalTaxOrder));
        easSaleOrder.setTotalTaxAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmountOrder));
        easSaleOrder.setLocalTotalAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmountOrder - totalTaxOrder));
        easSaleOrder.setLocalTotalTaxAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmountOrder));
        easSaleOrder.setCompanyOrgUnit(projectId.substring(0, 4));
        easSaleOrder.setIsInTax(1);
        easSaleOrder.setStorageOrgUnit(projectId);
        easSaleOrder.setBaseStatus("4");//1 保存 4 已审核
        easSaleOrder.setBizType("210");
        easSaleOrder.setBillType("310");
        easSaleOrder.setBizDate(saleOrder.getBusinessDate());
        easSaleOrder.setCreator("user");
        for (SaleOrderItem saleOrderItem : saleOrderItemList) {
            String customerId = saleOrderItem.getCustomerId();
            double taxPrice = ScaleUtils.getRoundUpValue(6,saleOrderItem.getTaxPrice());  //含税单价
            double taxRate = ScaleUtils.getRoundUpValue(6,saleOrderItem.getTaxRate());    //税率
            double price = ScaleUtils.getRoundUpValue(6,taxPrice / (1 + taxRate / 100.0));    //不含税单价
            double discount = ScaleUtils.getRoundUpValue(6,saleOrderItem.getDiscount() / 100.0); //折扣率
            double actualTaxPrice = ScaleUtils.getRoundUpValue(6,taxPrice * (1 - discount)); //实际含税单价
            double actualPrice = ScaleUtils.getRoundUpValue(6,price * (1 - discount)); //实际单价

            int qty = saleOrderItem.getNumber();

            double discountAmount = ScaleUtils.getRoundUpValue(6,taxPrice * qty * (discount)); //折扣额 = 含税单价*数量*折扣
            double priceTaxAmountTotal = ScaleUtils.getRoundUpValue(6,taxPrice * qty - discountAmount); //价税合计=含税单价*数量-折扣额
            double amount = actualPrice * qty; //金额=实际单价*数量
            double tax = ScaleUtils.getRoundUpValue(6,priceTaxAmountTotal - amount);//税额

            EasSaleOrderItem easSaleOrderItem = new EasSaleOrderItem();
            easSaleOrderItem.setIsPresent(0);
            easSaleOrderItem.setBaseQty(new BigDecimal(qty));
            easSaleOrderItem.setQty(new BigDecimal(qty));
            easSaleOrderItem.setAssistQty(new BigDecimal(qty));
            easSaleOrderItem.setPrice(ScaleUtils.doubleToBigDecimal(6,price));
            easSaleOrderItem.setTaxPrice(ScaleUtils.doubleToBigDecimal(6,taxPrice));
            easSaleOrderItem.setAmount(ScaleUtils.doubleToBigDecimal(6,amount));
            easSaleOrderItem.setLocalAmount(ScaleUtils.doubleToBigDecimal(6,amount));
            easSaleOrderItem.setActualPrice(ScaleUtils.doubleToBigDecimal(6,actualPrice));
            easSaleOrderItem.setActualTaxPrice(ScaleUtils.doubleToBigDecimal(6,actualTaxPrice));
            easSaleOrderItem.setTaxRate(ScaleUtils.doubleToBigDecimal(6,taxRate));
            easSaleOrderItem.setTax(ScaleUtils.doubleToBigDecimal(6,tax));
            easSaleOrderItem.setTaxAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));
            easSaleOrderItem.setStorageOrgUnit(projectId);
            easSaleOrderItem.setCompanyOrgUnit(projectId.substring(0, 4));
            easSaleOrderItem.setWarehouse(saleOrderItem.getWarehouseId());
            easSaleOrderItem.setTotalIssueQty(new BigDecimal(0));
            easSaleOrderItem.setTotalIssueBaseQty(new BigDecimal(0));
            easSaleOrderItem.setTotalUnReturnBaseQty(new BigDecimal(qty));
            easSaleOrderItem.setTotalUnIssueBaseQty(new BigDecimal(qty));
            easSaleOrderItem.setTotalUnShipBaseQty(new BigDecimal(qty));
            easSaleOrderItem.setTotalUnIssueQty(new BigDecimal(qty));
            easSaleOrderItem.setLocalTax(ScaleUtils.doubleToBigDecimal(6,tax));
            easSaleOrderItem.setLocalTaxAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));
            easSaleOrderItem.setDeliveryCustomer(customerId);
            easSaleOrderItem.setPaymentCustomer(customerId);
            easSaleOrderItem.setReceiveCustomer(customerId);
            easSaleOrderItem.setSaleOrgUnit(projectId);
            easSaleOrderItem.setBizDate(saleOrder.getBusinessDate());
            easSaleOrderItem.setMaterial(saleOrderItem.getMaterialId());
            easSaleOrderItem.setUnit("TAI");
            easSaleOrderItem.setBaseUnit("TAI");
            easSaleOrderItem.setBaseStatus("4");
            easSaleOrderItem.setAssistQty(new BigDecimal(qty));
            easSaleOrderItem.setBaseUnit(saleOrderItem.getUnit());
            easSaleOrderItem.setAssistUnit(saleOrderItem.getUnit());
            easSaleOrderItem.setDeliveryDate(saleOrder.getBusinessDate());//交货日期 = 业务日期 推送EAS才不会有问题
            easSaleOrderItem.setSendDate(saleOrder.getBusinessDate());// 发货日期 = 业务日期
            easSaleOrderItem.setDiscountAmount(ScaleUtils.doubleToBigDecimal(6,discountAmount));
            easSaleOrderItem.setDiscount(ScaleUtils.doubleToBigDecimal(6,saleOrderItem.getDiscount()));
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
        double totalTaxAmountOrder = ScaleUtils.getRoundUpValue(6,saleOutOrder.getTotalTaxAmount());
        double taxAmountOrder = ScaleUtils.getRoundUpValue(6,saleOutOrder.getTaxAmount());
        int saleOutNumberOrder = saleOutOrder.getTotalQuantity();


        easSaleOutOrder.setCustomer(saleOutOrder.getCustomerId());
        easSaleOutOrder.setCurrency(saleOutOrder.getCurrencyId());
        easSaleOutOrder.setExchangeRate(new BigDecimal(1));
        easSaleOutOrder.setPaymentType("002");
        easSaleOutOrder.setBizDate(saleOutOrder.getBusinessDate());
        easSaleOutOrder.setInTax(true);
        easSaleOutOrder.setTotalLocalAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmountOrder));
        easSaleOutOrder.setPriceSource("1");
        easSaleOutOrder.setStorageOrgUnit(projectId);
        easSaleOutOrder.setTotalQty(ScaleUtils.doubleToBigDecimal(6,saleOutNumberOrder));
        easSaleOutOrder.setTotalAmount(ScaleUtils.doubleToBigDecimal(6,totalTaxAmountOrder - taxAmountOrder));
        easSaleOutOrder.setTransactionType("010");
        easSaleOutOrder.setBaseStatus("4");//1 保存 4 已审核
        easSaleOutOrder.setBizType("210");
        easSaleOutOrder.setSourceBillType("310");
        easSaleOutOrder.setBillType("102");
        easSaleOutOrder.setCreator("user");
        easSaleOutOrder.setCreateTime(saleOutOrder.getBusinessDate());
        easSaleOutOrder.setControlUnit("0");
        easSaleOutOrder.setSaleOrgUnitID(projectId);
        easSaleOutOrder.setId(saleOutOrder.getId());


        for (SaleOutOrderItem saleOutOrderItem : saleOutOrderItems) {
            EasSaleOutOrderItem easSaleOutOrderItem = new EasSaleOutOrderItem();

            String customerId = saleOutOrderItem.getCustomerId();
            double taxPrice = ScaleUtils.getRoundUpValue(6,saleOutOrderItem.getTaxPrice());  //含税单价
            double taxRate = ScaleUtils.getRoundUpValue(6,saleOutOrderItem.getTaxRate());    //税率
            double price = ScaleUtils.getRoundUpValue(6,taxPrice / (1 + taxRate / 100.0));    //不含税单价
            double discount = ScaleUtils.getRoundUpValue(6,saleOutOrderItem.getDiscount() / 100); //折扣率
            double actualTaxPrice = ScaleUtils.getRoundUpValue(6,taxPrice * (1 - discount)); //实际含税单价
            double actualPrice = ScaleUtils.getRoundUpValue(6,price * (1 - discount)); //实际单价

            int qty = saleOutOrderItem.getNumber();

            double discountAmount = ScaleUtils.getRoundUpValue(6,taxPrice * qty * (discount)); //折扣额 = 含税单价*数量*折扣
            double priceTaxAmountTotal = ScaleUtils.getRoundUpValue(6,taxPrice * qty - discountAmount); //价税合计=含税单价*数量-折扣额
            double amount = actualPrice * qty; //金额=实际单价*数量
            double tax = priceTaxAmountTotal - amount;//税额
            easSaleOutOrderItem.setUnWriteOffQty(new BigDecimal(0));
            easSaleOutOrderItem.setUnWriteOffAmount(new BigDecimal(0));
            easSaleOutOrderItem.setTaxRate(ScaleUtils.doubleToBigDecimal(6,taxRate));
            easSaleOutOrderItem.setTax(ScaleUtils.doubleToBigDecimal(6,tax));
            easSaleOutOrderItem.setLocalTax(ScaleUtils.doubleToBigDecimal(6,tax));
            easSaleOutOrderItem.setLocalPrice(ScaleUtils.doubleToBigDecimal(6,price));
            easSaleOutOrderItem.setUnWriteOffBaseQty(ScaleUtils.doubleToBigDecimal(6,qty));
            easSaleOutOrderItem.setUnReturnedBaseQty(ScaleUtils.doubleToBigDecimal(6,qty));
            easSaleOutOrderItem.setTaxPrice(ScaleUtils.doubleToBigDecimal(6,taxPrice));
            easSaleOutOrderItem.setActualPrice(ScaleUtils.doubleToBigDecimal(6,actualTaxPrice));
            easSaleOutOrderItem.setSaleOrgUnit(projectId);
            easSaleOutOrderItem.setUndeliverQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUndeliverBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnInQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnInBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setBalanceCustomer(saleOutOrderItem.getCustomerId());
            easSaleOutOrderItem.setBaseUnitActualcost(ScaleUtils.doubleToBigDecimal(6,price));
            easSaleOutOrderItem.setOrderCustomer(saleOutOrderItem.getCustomerId());
            easSaleOutOrderItem.setPaymentCustomer(saleOutOrderItem.getCustomerId());
            easSaleOutOrderItem.setAssociateBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setSalePrice(ScaleUtils.doubleToBigDecimal(6,price));
            easSaleOutOrderItem.setDiscountType("0");
            easSaleOutOrderItem.setDiscountAmount(ScaleUtils.doubleToBigDecimal(6,discountAmount));
            easSaleOutOrderItem.setDiscount(ScaleUtils.doubleToBigDecimal(6,saleOutOrderItem.getDiscount()));
            easSaleOutOrderItem.setPrice(ScaleUtils.doubleToBigDecimal(6,actualTaxPrice));
            easSaleOutOrderItem.setAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));
            easSaleOutOrderItem.setNonTaxAmount(ScaleUtils.doubleToBigDecimal(6,amount));
            easSaleOutOrderItem.setLocalNonTaxAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));
            easSaleOutOrderItem.setLocalAmount(ScaleUtils.doubleToBigDecimal(6,priceTaxAmountTotal));
            easSaleOutOrderItem.setUnSettleQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnSettleBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setBizDate(saleOutOrder.getBusinessDate());
            easSaleOutOrderItem.setFullWriteOff(false);
            easSaleOutOrderItem.setStorageOrgUnit(projectId);
            easSaleOutOrderItem.setCompanyOrgUnit(projectId.substring(0, 4));
            easSaleOutOrderItem.setWarehouse(saleOutOrderItem.getWarehouseId());
            easSaleOutOrderItem.setLocation(saleOutOrderItem.getLocationId());
            easSaleOutOrderItem.setLot(saleOutOrderItem.getLot());
            easSaleOutOrderItem.setQty(new BigDecimal(qty));
//            easSaleOutOrderItem.setAssistQty(new BigDecimal(qty));
            easSaleOutOrderItem.setBaseQty(new BigDecimal(qty));
            easSaleOutOrderItem.setUnitActualCost(ScaleUtils.doubleToBigDecimal(6,actualPrice));
            easSaleOutOrderItem.setUnSettleQty(new BigDecimal(saleOutOrderItem.getNumber()));
            easSaleOutOrderItem.setUnSettleBaseQty(new BigDecimal(saleOutOrderItem.getNumber()));
            easSaleOutOrderItem.setIsPresent(0);
            easSaleOutOrderItem.setMaterial(saleOutOrderItem.getMaterialId());
            easSaleOutOrderItem.setUnit("TAI");
            easSaleOutOrderItem.setBaseUnit("TAI");
//            easSaleOutOrderItem.setAssistUnit("TAI");

            easSaleOutOrderItem.setSourceBill(saleOutOrder.getId());
            easSaleOutOrderItem.setBaseStatus("4");//1 保存 4 已审核
            easSaleOutOrderItem.setAssociateQty(new BigDecimal(qty));
            easSaleOutOrderItem.setSourceBillType("310");
            easSaleOutOrderItem.setUnitActualCost(ScaleUtils.doubleToBigDecimal(6,actualPrice));
            easSaleOutOrderItem.setActualCost(ScaleUtils.doubleToBigDecimal(6,amount));
            easSaleOutOrderItem.setActualPrice(ScaleUtils.doubleToBigDecimal(6,actualPrice));
//            easSaleOutOrderItem.setAssistQty(new BigDecimal(actualPrice));
            easSaleOutOrderItem.setInvUpdateType("002");

            easSaleOutOrderItem.setId(saleOutOrderItem.getSourceBillId());
            easSaleOutOrderItem.setSaleOrder(saleOutOrder.getId());
            easSaleOutOrderItem.setSourceBillNumber(saleOutOrder.getEasSalesOrderNumber());
            easSaleOutOrderItem.setSaleOrderNumber(saleOutOrder.getEasSalesOrderNumber());
            easSaleOutOrderItem.setSourceBillEntry(saleOutOrderItem.getSourceItemBillId());
            easSaleOutOrderItem.setSaleOrderEntry(saleOutOrderItem.getSourceItemBillId());

            //新增字段
            easSaleOutOrderItem.setUnSettleQty(easSaleOutOrderItem.getQty());
            easSaleOutOrderItem.setUnSettleBaseQty(easSaleOutOrderItem.getQty());
            easSaleOutOrderItem.setLocalPrice(ScaleUtils.doubleToBigDecimal(6,0.0));
            easSaleOutOrderItem.setOrderPrice(ScaleUtils.doubleToBigDecimal(6,0.0));
            easSaleOutOrderItem.setCoreBillType("310");

            easSaleOutOrderItemList.add(easSaleOutOrderItem);
        }
        easSaleOutOrder.setTotalQty(new BigDecimal("0.0"));

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


    /**
     * 组装调拨单数据
     *
     * @param allocateEasBasicOrder         单头
     * @param allocateEasItemOrderList 单体
     * @return
     */
    public static String buildTransforOrder(AllocateEasBasicOrder allocateEasBasicOrder, List<AllocateEasItemOrder> allocateEasItemOrderList) {
        TestStockTransferBillInfo testStockTransferBillInfo = new TestStockTransferBillInfo();
        List<TestStockTransferBillIEntryInfo> testStockTransferBillIEntryInfoList = new ArrayList<>();

        testStockTransferBillInfo.setWms(allocateEasBasicOrder.getAllocateNo());  //必传
        testStockTransferBillInfo.setCreatorID("user");    //必传
        testStockTransferBillInfo.setCreateTime(allocateEasBasicOrder.getCreateTime());   //必传
        testStockTransferBillInfo.setLastUpdateTime(allocateEasBasicOrder.getLastUpdateTime());  //必传
        testStockTransferBillInfo.setBizDate(allocateEasBasicOrder.getCreateTime());  //必传
        testStockTransferBillInfo.setHasEffected(1);      //必传
        testStockTransferBillInfo.setBizType("331");   //必传
        testStockTransferBillInfo.setIssueCompanyorgUnitID("0202");   //必传
        testStockTransferBillInfo.setReceIptCompanyorgUnitID("0202");   //必传
        testStockTransferBillInfo.setIssueStorageorgUnitID("02029901");  //必传
        testStockTransferBillInfo.setIssueAdminorgUnitID("02029901");
        testStockTransferBillInfo.setReceIptStorageorgUnitID("02029901"); //必传
        testStockTransferBillInfo.setIsShipment(0);        //必传
        testStockTransferBillInfo.setModificationTime(allocateEasBasicOrder.getLastUpdateTime());  //必传
        testStockTransferBillInfo.setCurrencyID("BB01");     //必传
        testStockTransferBillInfo.setExchangeRate(new BigDecimal(1));   //必传
        testStockTransferBillInfo.setIsIntax(0);      //必传
        /**
         * 非必传字段
         */
        testStockTransferBillInfo.setLastUpdateUserID("");
        testStockTransferBillInfo.setControlunitID("");
        testStockTransferBillInfo.setHandlerID("");
        testStockTransferBillInfo.setDescription("");
        testStockTransferBillInfo.setAuditorID("");
        testStockTransferBillInfo.setSourceBillID("");
        testStockTransferBillInfo.setSourceFunction("");
        testStockTransferBillInfo.setAuditTime(allocateEasBasicOrder.getLastUpdateTime());
        testStockTransferBillInfo.setBaseStatus(0);
        testStockTransferBillInfo.setSourceBillType("");
        testStockTransferBillInfo.setBillType("");
        testStockTransferBillInfo.setYear(0);
        testStockTransferBillInfo.setPeriod(0);
        testStockTransferBillInfo.setReceIptAdminorgUnitID("");
        testStockTransferBillInfo.setModifierID("");
        testStockTransferBillInfo.setIsInitBill(0);
        testStockTransferBillInfo.setUnitsource(0);

        for (AllocateEasItemOrder record : allocateEasItemOrderList) {
            TestStockTransferBillIEntryInfo testStockTransferBillIEntryInfo = new TestStockTransferBillIEntryInfo();

            testStockTransferBillIEntryInfo.setSeq(1);  //必传
            testStockTransferBillIEntryInfo.setAsscoefficient(new BigDecimal("0"));   //必传
            testStockTransferBillIEntryInfo.setAssociateQty(new BigDecimal(0));   //必传
            testStockTransferBillIEntryInfo.setAssistQty(new BigDecimal(0));    //必传
            testStockTransferBillIEntryInfo.setMaterialID(record.getMaterialID());  //必传
            testStockTransferBillIEntryInfo.setUnitID("TAI");     //必传
            testStockTransferBillIEntryInfo.setBaseUnitID("TAI");  //必传
            testStockTransferBillIEntryInfo.setIssueWarehouseID("002");  //必传
            testStockTransferBillIEntryInfo.setReceiptWarehouseID("001");  //必传
            testStockTransferBillIEntryInfo.setLot(record.getLot());  //必传
            testStockTransferBillIEntryInfo.setQty(new BigDecimal(record.getQty()));     //必传
            testStockTransferBillIEntryInfo.setBaseQty(new BigDecimal(record.getQty()));         //必传
            testStockTransferBillIEntryInfo.setReceiptPlanDate(record.getReceiptPlanDate());  //必传
            testStockTransferBillIEntryInfo.setPrice(new BigDecimal(record.getPrice()));    //必传
            testStockTransferBillIEntryInfo.setIsPresent(0);  //必传
            testStockTransferBillIEntryInfo.setTaxPrice(new BigDecimal(0));    //必传
            testStockTransferBillIEntryInfo.setTaxAmount(new BigDecimal(0));  //必传
            testStockTransferBillIEntryInfo.setPruinvoiceAmount(new BigDecimal(0));   //必传
            testStockTransferBillIEntryInfo.setSaleinvoiceAmount(new BigDecimal(0));  //必传
            testStockTransferBillIEntryInfo.setTotalAccountPayable(new BigDecimal(0));     //必传
            testStockTransferBillIEntryInfo.setTotalAccountReceivable(new BigDecimal(0));  //必传
            testStockTransferBillIEntryInfo.setIssueLocationID("002.01");  //必传
            testStockTransferBillIEntryInfo.setReceiptLocationID("001.01");  //必传
            testStockTransferBillIEntryInfo.setIssueStorageOrgUnitID("02029901");  //必传
            testStockTransferBillIEntryInfo.setIssueCompanyOrgUnitID("0202");    //必传
            testStockTransferBillIEntryInfo.setReceiveStorageOrgUnitID("02029901");  //必传
            testStockTransferBillIEntryInfo.setReceiveCompanyOrgUnitID("0202");  //必传
            testStockTransferBillIEntryInfo.setBalancecostPrice(new BigDecimal(record.getBalancecostPrice()));   //必传
            testStockTransferBillIEntryInfo.setBalancecostRate(new BigDecimal(0));      //必传
            testStockTransferBillIEntryInfo.setActualPrice(new BigDecimal(record.getActualPrice()));  //必传
            testStockTransferBillIEntryInfo.setDiscountType(0);     //必传
            testStockTransferBillIEntryInfo.setDiscountRate(new BigDecimal(0));     //必传
            testStockTransferBillIEntryInfo.setActualTaxPrice(new BigDecimal(0));   //必传
            testStockTransferBillIEntryInfo.setLocalAmount(new BigDecimal(0));   //必传
            testStockTransferBillIEntryInfo.setLocalTaxAmount(new BigDecimal(0));  //必传
            testStockTransferBillIEntryInfo.setDiscountAmoumt(new BigDecimal(0));   //必传
            testStockTransferBillIEntryInfo.setMfg(record.getMfg());  //必传
            testStockTransferBillIEntryInfo.setExp(record.getExp());  //必传
            testStockTransferBillIEntryInfo.setIssuePlanDate(record.getIssuePlanDate());  //必传
            testStockTransferBillIEntryInfo.setStoreTypeID("G");    //必传
            testStockTransferBillIEntryInfo.setIsmrpcal(0);  //必传
            /**
             * 非必传字段
             */
            testStockTransferBillIEntryInfo.setSourceBillID("");
            testStockTransferBillIEntryInfo.setSourceBillNumber("");
            testStockTransferBillIEntryInfo.setSourceBillEntryID("");
            testStockTransferBillIEntryInfo.setSourceBillEntrySeq("");
            testStockTransferBillIEntryInfo.setBaseStatus(0);
            testStockTransferBillIEntryInfo.setSourceBillTypeID("");
            testStockTransferBillIEntryInfo.setAssistpropertyID("");
            testStockTransferBillIEntryInfo.setAssistUnitID("");
            testStockTransferBillIEntryInfo.setReasonCodeID("");
            testStockTransferBillIEntryInfo.setParentID("");
            testStockTransferBillIEntryInfo.setAssistQty(new BigDecimal(1));
            testStockTransferBillIEntryInfo.setAmount(new BigDecimal(0));
            testStockTransferBillIEntryInfo.setIssueQty(record.getIssueQty());
            testStockTransferBillIEntryInfo.setRemark("");
            testStockTransferBillIEntryInfo.setTaxrate(new BigDecimal(0));
            testStockTransferBillIEntryInfo.setSaleinvoiccQty(0);
            testStockTransferBillIEntryInfo.setPurinvoiceQty(0);
            testStockTransferBillIEntryInfo.setTax(new BigDecimal(0));
            testStockTransferBillIEntryInfo.setIssueBaseQty(record.getIssueQty());
            testStockTransferBillIEntryInfo.setReceiptBaseQty(0);
            testStockTransferBillIEntryInfo.setSaleinvoiceBaseQty(0);
            testStockTransferBillIEntryInfo.setPurinvoiceBaseQty(0);
            testStockTransferBillIEntryInfo.setUnissueBaseQty(0);
            testStockTransferBillIEntryInfo.setReason("");
            testStockTransferBillIEntryInfo.setIssueSaleOrgUnitID("");
            testStockTransferBillIEntryInfo.setSupplyrelation("");
            testStockTransferBillIEntryInfo.setSupplySaleOrgUnitID("");
            testStockTransferBillIEntryInfo.setRequireSaleOrgUnitID("");
            testStockTransferBillIEntryInfo.setTotalIssueQty(record.getIssueQty());
            testStockTransferBillIEntryInfo.setLocalTax(new BigDecimal(0));
            testStockTransferBillIEntryInfo.setLocalDiscountAmount(new BigDecimal(0));
            testStockTransferBillIEntryInfo.setArassociateBaseQty(0);
            testStockTransferBillIEntryInfo.setApassociateBaseQty(0);
            testStockTransferBillIEntryInfo.setTotalPlandQty(0);
            testStockTransferBillIEntryInfo.setManuwarehouseID("");
            testStockTransferBillIEntryInfo.setQuarityUnctrl(0);
            testStockTransferBillIEntryInfo.setQuarityOverRate(new BigDecimal(0));
            testStockTransferBillIEntryInfo.setQuqrutyArreRate(new BigDecimal(0));
            testStockTransferBillIEntryInfo.setProjectID("");
            testStockTransferBillIEntryInfo.setTrackNumberID("");
            testStockTransferBillIEntryInfo.setBizDate(record.getReceiptPlanDate());
            testStockTransferBillIEntryInfo.setCustomerID("");
            testStockTransferBillIEntryInfo.setSupplierID("");
            testStockTransferBillIEntryInfo.setReservationBillObjectID("");
            testStockTransferBillIEntryInfo.setReservationID("");
            testStockTransferBillIEntryInfo.setStorestateID("1");
            testStockTransferBillIEntryInfo.setIsSalePrice(0);

            testStockTransferBillIEntryInfoList.add(testStockTransferBillIEntryInfo);
        }
        testStockTransferBillInfo.setEntrys(testStockTransferBillIEntryInfoList);
        String saleOutJson = JSON.toJSONString(testStockTransferBillInfo);
        System.out.println(saleOutJson);
        return saleOutJson;
    }

    /**
     * 组装调拨入库单数据
     *
     * @param allocateStockEasOrder         单头
     * @param allocateStockBrandEasOrderList 单体
     * @return
     */
    public static String buildTransforInboundOrder(AllocateStockEasOrder allocateStockEasOrder, List<AllocateStockBrandEasOrder> allocateStockBrandEasOrderList) {
        XPSMoveIssueBillInfo xpsMoveIssueBillInfo = new XPSMoveIssueBillInfo();
        List<XPSMoveIssueBillEntryInfo> xpsMoveIssueBillEntryInfoList = new ArrayList<>();

        xpsMoveIssueBillInfo.setWms(allocateStockEasOrder.getWms());
        xpsMoveIssueBillInfo.setCreatorID(allocateStockEasOrder.getCreatorID());
        xpsMoveIssueBillInfo.setBizType(allocateStockEasOrder.getBizType());
        xpsMoveIssueBillInfo.setBizDate(allocateStockEasOrder.getBizDate());
        xpsMoveIssueBillInfo.setCreateTime(allocateStockEasOrder.getCreateTime());
        xpsMoveIssueBillInfo.setSourceBillID(allocateStockEasOrder.getSourceBillID());
        xpsMoveIssueBillInfo.setTransactionTypeID(allocateStockEasOrder.getTransactionTypeID());
        xpsMoveIssueBillInfo.setIssueStorageOrgUnitID(allocateStockEasOrder.getIssueStorageOrgUnitID());
        xpsMoveIssueBillInfo.setReceiptStorageOrgUnitID(allocateStockEasOrder.getReceiptStorageOrgUnitID());
        xpsMoveIssueBillInfo.setIssueCompanyOrgUnitID(allocateStockEasOrder.getIssueCompanyOrgUnitID());
        xpsMoveIssueBillInfo.setReceiptCompanyOrgUnitID(allocateStockEasOrder.getReceiptCompanyOrgUnitID());
        xpsMoveIssueBillInfo.setAdminOrgUnitID("");
        xpsMoveIssueBillInfo.setLastUpdateUserID("");
        xpsMoveIssueBillInfo.setControlUnitID("");
        xpsMoveIssueBillInfo.setNumber("");
        xpsMoveIssueBillInfo.setHandlerID("");
        xpsMoveIssueBillInfo.setDescription("");
        xpsMoveIssueBillInfo.setHasEffected(0);
        xpsMoveIssueBillInfo.setAuditorID("");
        xpsMoveIssueBillInfo.setSourceFunction("");
        xpsMoveIssueBillInfo.setAuditTime("");
        xpsMoveIssueBillInfo.setBaseStatus("");
        xpsMoveIssueBillInfo.setSourceBillTypeID("");
        xpsMoveIssueBillInfo.setBillType(allocateStockEasOrder.getBizType());
        xpsMoveIssueBillInfo.setYear(0);
        xpsMoveIssueBillInfo.setPeriod(0);
        xpsMoveIssueBillInfo.setStockerID("");
        xpsMoveIssueBillInfo.setTotalQty(new BigDecimal(allocateStockEasOrder.getTotalQty()));
        xpsMoveIssueBillInfo.setTotalAmount(new BigDecimal(allocateStockEasOrder.getTotalAmount()));
        xpsMoveIssueBillInfo.setFivouchered(0);
        xpsMoveIssueBillInfo.setTotalStandardCost(new BigDecimal(0));
        xpsMoveIssueBillInfo.setTotalActualCost(new BigDecimal(0));
        xpsMoveIssueBillInfo.setIsreversed(0);
        xpsMoveIssueBillInfo.setIsinitBill(0);
        xpsMoveIssueBillInfo.setModificationTime("");
        xpsMoveIssueBillInfo.setModifierID("");
        xpsMoveIssueBillInfo.setStoreTypeID("");
        xpsMoveIssueBillInfo.setMonth(0);
        xpsMoveIssueBillInfo.setDay(0);
        xpsMoveIssueBillInfo.setIsSysBill(0);
        xpsMoveIssueBillInfo.setCostCenterOrgUnitID("");
        xpsMoveIssueBillInfo.setUnitSource("");

        for (AllocateStockBrandEasOrder record : allocateStockBrandEasOrderList) {
            XPSMoveIssueBillEntryInfo xpsMoveIssueBillEntryInfo = new XPSMoveIssueBillEntryInfo();
            xpsMoveIssueBillEntryInfo.setSeq(record.getSeq());
            xpsMoveIssueBillEntryInfo.setSourceBillID(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setSourceBillNumber(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setSourceBillEntryID(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setSourceBillEntrySeq(record.getSourceBillEntrySeq());
            xpsMoveIssueBillEntryInfo.setParentID(record.getLocationID());
            xpsMoveIssueBillEntryInfo.setStockTransferBillID(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setStockTransBillEntryID(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setStockTransferBillNum(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setStockTransferBillEntrySeq(record.getSourceBillEntrySeq());
            xpsMoveIssueBillEntryInfo.setInvUpdateTypeID(record.getInvUpdateTypeID());
            xpsMoveIssueBillEntryInfo.setMaterialID(record.getMaterialID());
            xpsMoveIssueBillEntryInfo.setLot(record.getLot());
            xpsMoveIssueBillEntryInfo.setUnitID(record.getUnitID());
            xpsMoveIssueBillEntryInfo.setQty(new BigDecimal(record.getQty()));
            xpsMoveIssueBillEntryInfo.setWarehouseID(record.getWarehouseID());
            xpsMoveIssueBillEntryInfo.setLocationID(record.getLocationID());
            /**非必填字段*/
            xpsMoveIssueBillEntryInfo.setAsscoEfficient(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setBaseStatus(0);
            xpsMoveIssueBillEntryInfo.setAssociateQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setSourceBillTypeID("");
            xpsMoveIssueBillEntryInfo.setAssistPropertyID("");
            xpsMoveIssueBillEntryInfo.setBaseUnitID("");
            xpsMoveIssueBillEntryInfo.setAssistUnitID("");
            xpsMoveIssueBillEntryInfo.setReasonCodeID("");
            xpsMoveIssueBillEntryInfo.setStorageOrgUnitID("");
            xpsMoveIssueBillEntryInfo.setCompanyOrgUnitID("");
            xpsMoveIssueBillEntryInfo.setStockerID("");
            xpsMoveIssueBillEntryInfo.setAssistQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setBaseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setReverseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setReturnsQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setPrice(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setAmount(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setUnitStandardCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setStandardCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setUnitActualCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setActualCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setIspresent(0);
            xpsMoveIssueBillEntryInfo.setRemark("");
            xpsMoveIssueBillEntryInfo.setReverseBaseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setReturnBaseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setBaseUnitActualCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setTotalInwareHsQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setTotalInwareHsQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setCanInwareHsBaseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setProjectID("");
            xpsMoveIssueBillEntryInfo.setTrackNumber("");
            xpsMoveIssueBillEntryInfo.setCustomerID("");
            xpsMoveIssueBillEntryInfo.setSupplierID("");
            xpsMoveIssueBillEntryInfo.setStoreTypeID("");
            xpsMoveIssueBillEntryInfo.setBizdate(allocateStockEasOrder.getBizDate());
            xpsMoveIssueBillEntryInfo.setExp("");
            xpsMoveIssueBillEntryInfo.setMfg("");
            xpsMoveIssueBillEntryInfo.setReservationBilObjectID("");
            xpsMoveIssueBillEntryInfo.setReservationID("");
            xpsMoveIssueBillEntryInfo.setAccountViewinID("");
            xpsMoveIssueBillEntryInfo.setAccountViewOutID("");
            xpsMoveIssueBillEntryInfoList.add(xpsMoveIssueBillEntryInfo);
        }
        xpsMoveIssueBillInfo.setEntrys(xpsMoveIssueBillEntryInfoList);
        String saleOutJson = JSON.toJSONString(xpsMoveIssueBillInfo);
        System.out.println(saleOutJson);
        return saleOutJson;
    }

    /**
     * 组装调拨出库单数据
     *
     * @param allocateStockEasOrder         单头
     * @param allocateStockBrandEasOrderList 单体
     * @return
     */
    public static String buildTransforOutboundOrder(AllocateStockEasOrder allocateStockEasOrder, List<AllocateStockBrandEasOrder> allocateStockBrandEasOrderList) {
        XPSMoveIssueBillInfo xpsMoveIssueBillInfo = new XPSMoveIssueBillInfo();
        List<XPSMoveIssueBillEntryInfo> xpsMoveIssueBillEntryInfoList = new ArrayList<>();

        xpsMoveIssueBillInfo.setWms(allocateStockEasOrder.getWms());
        xpsMoveIssueBillInfo.setCreatorID(allocateStockEasOrder.getCreatorID());
        xpsMoveIssueBillInfo.setBizType(allocateStockEasOrder.getBizType());
        xpsMoveIssueBillInfo.setBizDate(allocateStockEasOrder.getBizDate());
        xpsMoveIssueBillInfo.setCreateTime(allocateStockEasOrder.getCreateTime());
        xpsMoveIssueBillInfo.setSourceBillID(allocateStockEasOrder.getSourceBillID());
        xpsMoveIssueBillInfo.setTransactionTypeID(allocateStockEasOrder.getTransactionTypeID());
        xpsMoveIssueBillInfo.setIssueStorageOrgUnitID(allocateStockEasOrder.getIssueStorageOrgUnitID());
        xpsMoveIssueBillInfo.setReceiptStorageOrgUnitID(allocateStockEasOrder.getReceiptStorageOrgUnitID());
        xpsMoveIssueBillInfo.setIssueCompanyOrgUnitID(allocateStockEasOrder.getIssueCompanyOrgUnitID());
        xpsMoveIssueBillInfo.setReceiptCompanyOrgUnitID(allocateStockEasOrder.getReceiptCompanyOrgUnitID());
        xpsMoveIssueBillInfo.setAdminOrgUnitID("");
        xpsMoveIssueBillInfo.setLastUpdateUserID("");
        xpsMoveIssueBillInfo.setControlUnitID("");
        xpsMoveIssueBillInfo.setNumber("");
        xpsMoveIssueBillInfo.setHandlerID("");
        xpsMoveIssueBillInfo.setDescription("");
        xpsMoveIssueBillInfo.setHasEffected(0);
        xpsMoveIssueBillInfo.setAuditorID("");
        xpsMoveIssueBillInfo.setSourceFunction("");
        xpsMoveIssueBillInfo.setAuditTime("");
        xpsMoveIssueBillInfo.setBaseStatus("");
        xpsMoveIssueBillInfo.setSourceBillTypeID("");
        xpsMoveIssueBillInfo.setBillType(allocateStockEasOrder.getBizType());
        xpsMoveIssueBillInfo.setYear(0);
        xpsMoveIssueBillInfo.setPeriod(0);
        xpsMoveIssueBillInfo.setStockerID("");
        xpsMoveIssueBillInfo.setTotalQty(new BigDecimal(0));
        xpsMoveIssueBillInfo.setTotalAmount(new BigDecimal(0));
        xpsMoveIssueBillInfo.setFivouchered(0);
        xpsMoveIssueBillInfo.setTotalStandardCost(new BigDecimal(0));
        xpsMoveIssueBillInfo.setTotalActualCost(new BigDecimal(0));
        xpsMoveIssueBillInfo.setIsreversed(0);
        xpsMoveIssueBillInfo.setIsinitBill(0);
        xpsMoveIssueBillInfo.setModificationTime("");
        xpsMoveIssueBillInfo.setModifierID("");
        xpsMoveIssueBillInfo.setStoreTypeID("");
        xpsMoveIssueBillInfo.setMonth(0);
        xpsMoveIssueBillInfo.setDay(0);
        xpsMoveIssueBillInfo.setIsSysBill(0);
        xpsMoveIssueBillInfo.setCostCenterOrgUnitID("");
        xpsMoveIssueBillInfo.setUnitSource("");

        for (AllocateStockBrandEasOrder record : allocateStockBrandEasOrderList) {
            XPSMoveIssueBillEntryInfo xpsMoveIssueBillEntryInfo = new XPSMoveIssueBillEntryInfo();
            xpsMoveIssueBillEntryInfo.setSeq(record.getSeq());
            xpsMoveIssueBillEntryInfo.setSourceBillID(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setSourceBillNumber(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setSourceBillEntryID(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setSourceBillEntrySeq(record.getSourceBillEntrySeq());
            xpsMoveIssueBillEntryInfo.setParentID(record.getLocationID());
            xpsMoveIssueBillEntryInfo.setStockTransferBillID(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setStockTransBillEntryID(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setStockTransferBillNum(record.getSourceBillEntryID());
            xpsMoveIssueBillEntryInfo.setStockTransferBillEntrySeq(record.getSourceBillEntrySeq());
            xpsMoveIssueBillEntryInfo.setInvUpdateTypeID(record.getInvUpdateTypeID());
            xpsMoveIssueBillEntryInfo.setMaterialID(record.getMaterialID());
            xpsMoveIssueBillEntryInfo.setLot(record.getLot());
            xpsMoveIssueBillEntryInfo.setUnitID(record.getUnitID());
            xpsMoveIssueBillEntryInfo.setQty(new BigDecimal(record.getQty()));
            xpsMoveIssueBillEntryInfo.setWarehouseID(record.getWarehouseID());
            xpsMoveIssueBillEntryInfo.setLocationID(record.getLocationID());
            /**非必填字段*/
            xpsMoveIssueBillEntryInfo.setAsscoEfficient(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setBaseStatus(0);
            xpsMoveIssueBillEntryInfo.setAssociateQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setSourceBillTypeID("");
            xpsMoveIssueBillEntryInfo.setAssistPropertyID("");
            xpsMoveIssueBillEntryInfo.setBaseUnitID("");
            xpsMoveIssueBillEntryInfo.setAssistUnitID("");
            xpsMoveIssueBillEntryInfo.setReasonCodeID("");
            xpsMoveIssueBillEntryInfo.setStorageOrgUnitID("");
            xpsMoveIssueBillEntryInfo.setCompanyOrgUnitID("");
            xpsMoveIssueBillEntryInfo.setStockerID("");
            xpsMoveIssueBillEntryInfo.setAssistQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setBaseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setReverseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setReturnsQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setPrice(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setAmount(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setUnitStandardCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setStandardCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setUnitActualCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setActualCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setIspresent(0);
            xpsMoveIssueBillEntryInfo.setRemark("");
            xpsMoveIssueBillEntryInfo.setReverseBaseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setReturnBaseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setBaseUnitActualCost(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setTotalInwareHsQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setTotalInwareHsQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setCanInwareHsBaseQty(new BigDecimal(0));
            xpsMoveIssueBillEntryInfo.setProjectID("");
            xpsMoveIssueBillEntryInfo.setTrackNumber("");
            xpsMoveIssueBillEntryInfo.setCustomerID("");
            xpsMoveIssueBillEntryInfo.setSupplierID("");
            xpsMoveIssueBillEntryInfo.setStoreTypeID("");
            xpsMoveIssueBillEntryInfo.setBizdate(allocateStockEasOrder.getBizDate());
            xpsMoveIssueBillEntryInfo.setExp("");
            xpsMoveIssueBillEntryInfo.setMfg("");
            xpsMoveIssueBillEntryInfo.setReservationBilObjectID("");
            xpsMoveIssueBillEntryInfo.setReservationID("");
            xpsMoveIssueBillEntryInfo.setAccountViewinID("");
            xpsMoveIssueBillEntryInfo.setAccountViewOutID("");
            xpsMoveIssueBillEntryInfoList.add(xpsMoveIssueBillEntryInfo);
        }
        xpsMoveIssueBillInfo.setEntrys(xpsMoveIssueBillEntryInfoList);
        String saleOutJson = JSON.toJSONString(xpsMoveIssueBillInfo);
        System.out.println(xpsMoveIssueBillInfo);
        return saleOutJson;
    }
}
