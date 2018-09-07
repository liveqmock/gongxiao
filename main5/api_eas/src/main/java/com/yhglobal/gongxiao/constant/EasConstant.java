package com.yhglobal.gongxiao.constant;

import javax.swing.plaf.PanelUI;

/**
 * 常量
 *
 * @author: 陈浩
 **/
public class EasConstant {
    //eas url
//    public static final String URL = "http://192.168.100.67:6880";
    public static final String URL = "http://192.168.100.188:6888";
    public static final String LOGIN = "/ormrpc/services/EASLogin?wsdl";
    public static final String PURCHASE_ORDER = "/ormrpc/services/WSWsPurOrderFacade?wsdl";
    public static final String PURCHASE_INBOUND = "/ormrpc/services/WSWMSPurInWarehsBillFacade?wsdl";
    public static final String SALE_ORDER="/ormrpc/services/WSSaleOrderFacade?wsdl";
    public static final String SALE_OUTBOUND="/ormrpc/services/WSImportSaleIssueFacade?wsdl";
    public static final String OTHER_IN_WARE="/ormrpc/services/WSImportOtherInWaerhsBillFacade?wsdl";
    public static final String OTHER_OUT_WARE="/ormrpc/services/WSWMSOtherIssueBillFacade?wsdl";
    public static final String TRANSEFOR_ORDER="/ormrpc/services/WSTestStockTransferFacade?wsdl";
    public static final String TRANSEFOR_INBOUND="/ormrpc/services/WSXPSMoveInWarehsBillFacade?wsdl";
    public static final String TRANSEFOR_OUTBOUND="/ormrpc/services/WSXPSMoveIssueBillFacade?wsdl";

    //EAS LOGIN USER INFO
//    public static final String USER = "user";
    public static final String USER="InterfaceUser";
//    public static final String PWD = "qwe123";
    public static final String PWD = "InterfaceUser@yh123";
    public static final String SLNAME = "eas";
//    public static final String DCNAME = "eas82";
    public static final String DCNAME = "yheas75";
    public static final String LANGUAGE = "L2";
    public static final int DBTYPE = 2;

    //测试json
    public static final String OTHER_IN_WARE_JSON=
            "{\"creator\":\"user\",\"createTime\":\"2017-12-12\",\"bizDate\":\"2017-12-12\",\"bizType\":\"110\",\"wms\":\"123\",\"storageOrgUnit\":\"03019902\",\"adminOrgUnit\":\"\",\"stocker\":\"\",\"voucher\":\"\",\"totalQty\":\"1\",\"totalAmount\":\"100\",\"fiVouchered\":\"\",\"totalStandardCost\":\"100\",\"totalActualCost\":\"100\",\"isReversed\":\"\",\"transactionType\":\"\",\"isInitBill\":\"\",\"month\":\"12\",\"day\":\"12\",\"costCenterOrgUnit\":\"\",\"sourceBillType\":\"\",\"year\":\"2017\",\"period\":\"2017\",\"unitSource\":\"\",\"sourceBillId\":\"\",\"sourceFunction\":\"\",\"receiverName\":\"123\",\"receiverTel\":\"123\",\"receiverAddress\":\"123\",\"receiverCity\":\"13\",\"entries\":[{\"customer\":\"\",\"warehouse\":\"007\",\"lot\":\"123\",\"material\":\"04.01.01.01.010\",\"price\":\"100\",\"qty\":\"1\",\"remark\":\"测试\",\"stocker\":\"\",\"supplier\":\"\",\"unit\":\"TAI\",\"location\":\"011.01\",\"wmsEntryID\":\"\",\"costObject\":\"\",\"costItem\":\"\",\"costObjectSuite\":\"\",\"amount\":\"100\",\"bizDate\":\"2017-12-12\",\"companyOrgUnit\":\"\",\"assistQty\":\"100\",\"baseQty\":\"\",\"reverseQty\":\"\",\"returnsQty\":\"\",\"unitStandardCost\":\"\",\"standardCost\":\"\",\"unitActualCost\":\"\",\"actualCost\":\"\",\"mfg\":\"\",\"exp\":\"\",\"reverseBaseQty\":\"\",\"returnsBaseQty\":\"\",\"project\":\"\",\"trackNumber\":\"\",\"invUpdateType\":\"\",\"accountViewIn\":\"\",\"accountViewOut\":\"\",\"assistProperty\":\"\",\"sourceBillId\":\"\",\"sourceBillNumber\":\"\",\"sourceBillEntryId\":\"\",\"sourceBillEntrySeq\":\"\",\"assCoefficient\":\"\",\"baseStatus\":\"\",\"associateQty\":\"\",\"sourceBillType\":\"\",\"baseUnit\":\"\",\"assistUnit\":\"\",\"reasonCode\":\"\",\"reservationBillObjectId\":\"\",\"reservationId\":\"\"}]}";
    public static final String BILL_JSON = "{'comPany':'0202','payeeNumber':'1002.01.0202.01','description':'申请理由','paperBank':'招商银行深圳分行中央商务支行','asstActNumber':'YHSZ0075','entries':[{'amount':'123'},{'amount':'321'}],'usage':'付款理由','number':'fkd0002','payerAccountBank':'817681813110001','payeeType':'YH-002','bizDate':'2018-03-14','creator':'YHSZ0075'}";
    public static final String PURCHASE_INBOUND_JSON=
            "{\"supplier\":\"01.02.01.0012\",\"currency\":\"BB01\",\"receiptAmount\":\"100\",\"exchangeRate\":\"1\",\"paymentType\":\"004\",\"convertMode\":\"0\",\"isSysBill\":\"0\",\"isCentralBalance\":\"0\",\"totalLocalAmount\":\"100\",\"purchaseType\":\"1\",\"isInTax\":\"1\",\"isPriceInTax\":\"1\",\"storageOrgUnit\":\"02029925\",\"totalQty\":\"1\",\"totalAmount\":\"100\",\"totalStandardCost\":\"100\",\"totalActualCost\":\"100\",\"transactionType\":\"03019902\",\"month\":\"3\",\"day\":\"29\",\"costCenterOrgUnit\":\"\",\"auditTime\":\"\",\"baseStatus\":\"\",\"bizType\":\"03019902\",\"sourceBillType\":\"\",\"billTypeID\":\"\",\"year\":\"2018\",\"modifier\":\"\",\"modificationTime\":\"\",\"number\":\"123456789\",\"bizDate\":\"2017-03-16\",\"handlerID\":\"\",\"description\":\"测试\",\"hasEffected\":\"0\",\"auditorID\":\"\",\"sourceBillID\":\"\",\"sourceFunction\":\"\",\"creator\":\"user\",\"createTime\":\"\",\"lastUpdateUser\":\"\",\"lastUpdateTime\":\"\",\"controlUnit\":\"\",\"id\":\"wEQAAADuliYxcb+t\",\"entries\":[{\"id\":\"wEQAAADulicmBBzF\",\"isPresent\":\"0\",\"writtenOffQty\":\"1\",\"writtenOffAmount\":\"1\",\"unWriteOffQty\":\"1\",\"unWriteOffAmount\":\"100\",\"receiveAmount\":\"100\",\"purOrderNumber\":\"\",\"purOrderEntrySeq\":\"\",\"taxRate\":\"1\",\"tax\":\"1\",\"localTax\":\"1\",\"localPrice\":\"100\",\"localAmount\":\"100\",\"drewQty\":\"0\",\"imputedCost\":\"\",\"writtenOffBaseQty\":\"0\",\"unWriteOffBaseQty\":\"0\",\"purOrderID\":\"\",\"purOrderEntryID\":\"\",\"coreBillTypeID\":\"\",\"unReturnedBaseQty\":\"0\",\"orderPrice\":\"100\",\"taxPrice\":\"100\",\"actualPrice\":\"100\",\"purchaseOrgUnit\":\"\",\"purchaseGroup\":\"\",\"purchasePerson\":\"\",\"drewBaseQty\":\"0\",\"totalMoveQty\":\"0\",\"isRequestToReceived\":\"0\",\"isFullWriteOff\":\"0\",\"canDirectReqQty\":\"0\",\"canDirectReqBaseQty\":\"0\",\"balanceSupplier\":\"\",\"isCenterBalance\":\"0\",\"isBetweenCompanySend\":\"0\",\"hasSameCou\":\"0\",\"receiveStorageOrgUnit\":\"03019902\",\"purchaseCost\":\"100\",\"purchaseFee\":\"100\",\"unitPurchaseCost\":\"\",\"materialCost\":\"\",\"unitMaterialCost\":\"\",\"sCWrittenOffQty\":\"\",\"sCWrittenOffBaseQty\":\"\",\"sCUnWrittenOffQty\":\"\",\"sCUnWrittenOffBaseQty\":\"\",\"dosingType\":\"\",\"productTaskNumber\":\"\",\"supplierLotNo\":\"\",\"taxAmount\":\"\",\"localTaxAmount\":\"\",\"actualTaxPrice\":\"\",\"discountRate\":\"\",\"discountAmount\":\"\",\"contractNumber\":\"\",\"motherEntryID\":\"\",\"customer\":\"\",\"outWarehouse\":\"\",\"outLocation\":\"\",\"price\":\"100\",\"amount\":\"100\",\"bizDate\":\"2018-03-29\",\"storageOrgUnit\":\"\",\"companyOrgUnit\":\"0202\",\"warehouse\":\"007\",\"location\":\"\",\"stocker\":\"\",\"lot\":\"12345\",\"qty\":\"1\",\"assistQty\":\"1\",\"baseQty\":\"1\",\"reverseQty\":\"\",\"returnsQty\":\"\",\"unitStandardCost\":\"\",\"standardCost\":\"\",\"unitActualCost\":\"\",\"actualCost\":\"\",\"mfg\":\"\",\"exp\":\"\",\"reverseBaseQty\":\"\",\"returnBaseQty\":\"\",\"project\":\"\",\"trackNumber\":\"\",\"material\":\"04.01.01.01.010\",\"assistProperty\":\"\",\"unit\":\"TAI\",\"sourceBillID\":\"\",\"sourceBillNumber\":\"\",\"sourceBillEntryID\":\"\",\"sourceBillEntrySeq\":\"\",\"assCoefficient\":\"\",\"baseStatus\":\"\",\"associateQty\":\"0\",\"sourceBillType\":\"\",\"baseUnit\":\"\",\"assistUnit\":\"\",\"remark\":\"\",\"reasonCode\":\"\"}]}";
    public static final String PURCHASE_JSON = "{'purchaseOrgUnit':'03019902',"+
            "'purchaseGroup':'',"+
            "'purchasePerson':'',"+
            "'supplier':'01.02.01.0012',"+
            "'supplierAddress':'',"+
            "'supplierOrderNumber':'',"+
            "'saleOrgUnit':'',"+
            "'saleOrder':'',"+
            "'isDirectSend':'0',"+
            "'paymentType':'004',"+
            "'settlementType':'01',"+
            "'cashDiscount':'',"+
            "'currency':'BB01',"+
            "'exchangeRate':'1',"+
            "'prepaymentRate':'0',"+
            "'prepayment':'0',"+
            "'prepaid':'0',"+
            "'prepaymentDate':'',"+
            "'supplierConFirm':'0',"+
            "'invoicedAmount':'0',"+
            "'paidAmount':'0',"+
            "'isInnerSale':'0',"+
            "'adminOrgUnit':'',"+
            "'totalAmount':'72454.2',"+
            "'totalTax':'12317.21',"+
            "'totalTaxAmount':'84771.41',"+
            "'unPrepaidAmount':'0',"+
            "'isSysBill':'1',"+
            "'convertMode':'0',"+
            "'localTotalAmount':'0',"+
            "'localTotalTaxAmount':'0',"+
            "'isCentralBalance':'0',"+
            "'storageOrgUnit':'',"+
            "'companyOrgUnit':'0301',"+
            "'isInTax':'1',"+
            "'isQuicken':'0',"+
            "'alterPerson':'',"+
            "'version':'0',"+
            "'old':'',"+
            "'oldStatus':'',"+
            "'alterDate':'',"+
            "'isPriceInTax':'1',"+
            "'paymentCondition':'',"+
            "'warehouse':'007',"+
            "'isApprovedMaterial':'0',"+
            "'isMatched':'0',"+
            "'auditTime':'2014-06-25 14:38:52',"+
            "'baseStatus':'7',"+
            "'bizType':'110',"+
            "'sourceBillType':'',"+
            "'billType':'220',"+
            "'year':'2014',"+
            "'period':'6',"+
            "'modiFier':'liull',"+
            "'modiFicationTime':'2014-06-25 14:38:52',"+
            "'number':'*PIBJ2014060009',"+
            "'bizDate':'2014-06-16',"+
            "'handler':'',"+
            "'description':'',"+
            "'hasEFFected':'0',"+
            "'auditor':'liull',"+
            "'sourceBill':'',"+
            "'sourceFunction':'',"+
            "'creator':'liull',"+
            "'createTime':'2014-06-25 14:38:44',"+
            "'lastUpdateUser':'liull',"+
            "'lastUpdateTime':'2014-06-25 14:38:52',"+
            "'controlUnit':'0',"+
            "'entries':[{'isPresent':'0',"+
            "'supplierMaterialNumber':'',"+
            "'supplierMaterialName':'',"+
            "'supplierMaterialModel':'',"+
            "'assetNumber':'',"+
            "'demandQty':'96',"+
            "'qty':'96',"+
            "'storageOrgUnit':'03019902',"+
            "'adminOrgUnit':'',"+
            "'companyOrgUnit':'0301',"+
            "'assistQty':'0',"+
            "'price':'70.786325',"+
            "'discountRate':'1',"+
            "'actualPrice':'70.078438',"+
            "'taxRate':'17',"+
            "'taxPrice':'82.82',"+
            "'actualTaxPrice':'81.991771',"+
            "'amount':'6727.53',"+
            "'localAmount':'6727.53',"+
            "'tax':'1143.68',"+
            "'taxAmount':'7871.21',"+
            "'discountAmount':'79.51',"+
            "'deliveryDate':'2014-06-16',"+
            "'receiveOverRate':'0',"+
            "'receiveOwingRate':'0',"+
            "'deliverAdvanceDay':'0',"+
            "'deliverDeFerralDay':'0',"+
            "'trackNumber':'',"+
            "'baseQty':'96',"+
            "'totalReceiveQty':'0',"+
            "'totalReceiptQty':'96',"+
            "'totalReturnedQty':'0',"+
            "'totalInvoicedQty':'0',"+
            "'totalInvoicedAmount':'0',"+
            "'totalReqPayAmt':'0',"+
            "'totalPaidAmount':'0',"+
            "'totalExpense':'0',"+
            "'isQuantityUnCtrl':'0',"+
            "'isTimeUnCtrl':'0',"+
            "'totalReceiveBaseQty':'0',"+
            "'totalReceiptBaseQty':'96',"+
            "'totalReturnedBaseQty':'0',"+
            "'totalInvoicedBaseQty':'0',"+
            "'totalUnReturnBaseQty':'96',"+
            "'totalUnReceiveBaseQty':'96',"+
            "'totalUnReceiveQty':'96',"+
            "'deliveryAddress':'',"+
            "'closeDate':'2014-06-16',"+
            "'isSupInFo':'',"+
            "'curSeOrderQty':'0',"+
            "'localTax':'1143.68',"+
            "'localTaxAmount':'7871.21',"+
            "'saleOrderNumber':'',"+
            "'prepaidAmount':'0',"+
            "'requestOrgUnit':'03019902',"+
            "'requestCompanyOrgUnit':'',"+
            "'reason':'',"+
            "'isRequestToReceived':'',"+
            "'totalMoveQty':'0',"+
            "'totalInvoicedAmt':'0',"+
            "'totalPrePayAmt':'0',"+
            "'prepayment':'0',"+
            "'preReceived':'0',"+
            "'unPrereceivedAm':'0',"+
            "'version':'0',"+
            "'old':'',"+
            "'oldStatus':'',"+
            "'canInvMoveQty':'0',"+
            "'unOrderedQty':'0',"+
            "'isBetweenCompanyRec':'',"+
            "'rowType':'',"+
            "'destinationType':'',"+
            "'wareHouse':'007',"+
            "'materialName':'',"+
            "'isReqComEqlRecCom':'1',"+
            "'planReceiveQty':'0',"+
            "'totalCancelledStockQty':'0',"+
            "'totalSupplyStockQty':'0',"+
            "'totalSupplyStockBaseQty':'0',"+
            "'isReqPrePayGTprePay':'1',"+
            "'noNumMaterialModel':'',"+
            "'qCStandard':'',"+
            "'project':'',"+
            "'trackNo':'',"+
            "'purContract':'',"+
            "'matchedAmount':'0',"+
            "'bizDate':'2018-11-21 00:00:00',"+
            "'purchaseOrgUnit':'',"+
            "'purRequest':'',"+
            "'purRequestEntryID':'',"+
            "'isManualClose':'0',"+
            "'material':'04.01.01.01.010',"+
            "'assistProperty':'',"+
            "'unit':'TAI',"+
            "'sourceBillID':'',"+
            "'sourceBillNumber':'',"+
            "'sourceBillEntryID':'',"+
            "'sourceBillEntrySeq':'',"+
            "'assCoeFFicient':'0',"+
            "'baseStatus':'7',"+
            "'associateQty':'96',"+
            "'sourceBillType':'',"+
            "'baseUnit':'TAI',"+
            "'assistUnit':'',"+
            "'remark':'',"+
            "'reasonCode':'',"+
            "'seq':'1'}]}";



}
