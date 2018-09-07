package com.yhglobal.gongxiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * eas方需要的采购订单明细
 *
 * @author: 陈浩
 **/
public class EasPurchaseOrderItem implements Serializable{
    private Boolean isPresent;//赠品
    private String supplierMaterialNumber;//供应商物料编码
    private String supplierMaterialName;//供应商物料名称
    private String supplierMaterialModel;//供应商物料规格型号
    private String assetNumber;//资产编号
    private BigDecimal demandQty;//需求数量
    private BigDecimal qty;//订货数量
    private String storageOrgUnit;//收货组织
    private String adminOrgUnit;//行政组织
    private String companyOrgUnit;//收货财务组织
    private BigDecimal assistQty;//辅助单位数量
    private BigDecimal price;//单价
    private BigDecimal discountRate;//折扣率
    private BigDecimal actualPrice;//实际单价
    private BigDecimal taxRate;//税率
    private BigDecimal taxPrice;//含税单价
    private BigDecimal actualTaxPrice;//实际含税单价
    private BigDecimal amount;//金额
    private BigDecimal localAmount;//本位币金额
    private BigDecimal tax;//税额
    private BigDecimal taxAmount;//价税合计
    private BigDecimal discountAmount;//折扣额
    private Date deliveryDate;//交货日期
    private BigDecimal receiveOverRate;//收货超收比率
    private BigDecimal receiveOwingRate;//收货欠收比率
    private BigDecimal deliverAdvanceDay;//交货可提前天数
    private BigDecimal deliverDeFerralDay;//交货可推后天数
    private String trackNumber;//追踪号
    private BigDecimal baseQty;//基本数量
    private BigDecimal totalReceiveQty;//累计收货数量
    private BigDecimal totalReceiptQty;//累计入库数量
    private BigDecimal totalReturnedQty;//累计退货申请数量
    private BigDecimal totalInvoicedQty;//累计应付数量
    private BigDecimal totalInvoicedAmount;//累计应付金额
    private BigDecimal totalReqPayAmt;//累计申请付款金额
    private BigDecimal totalPaidAmount;//累计付款金额
    private BigDecimal totalExpense;//应计费用本位币
    private String parent;//采购订单头
    private Boolean isQuantityUnCtrl;//不控制数量
    private Boolean isTimeUnCtrl;//不控制时间
    private BigDecimal totalReceiveBaseQty;//基本累计收货数量
    private BigDecimal totalReceiptBaseQty;//基本累计入库数量
    private BigDecimal totalReturnedBaseQty;//基本累计退货数量
    private BigDecimal totalInvoicedBaseQty;//基本累计应付数量
    private BigDecimal totalUnReturnBaseQty;//基本累计未退货数量
    private BigDecimal totalUnReceiveBaseQty;//基本累计入库未关联数量
    private BigDecimal totalUnReceiveQty;//累计入库未关联数量
    private String deliveryAddress;//交货地址
    private Date closeDate;//关闭日期
    private Boolean isSupInFo;//是否从供应商信息带过来的
    private BigDecimal curSeOrderQty;//当前销售订单下推的数量
    private BigDecimal localTax;//本位币税额
    private BigDecimal localTaxAmount;//价税合计本位币
    private String saleOrderNumber;//销售订单号
    private BigDecimal prepaidAmount;//已付预款金额
    private String requestOrgUnit;//申请组织
    private String requestCompanyOrgUnit;//申请财务组织
    private String reason;//原因
    private Boolean isRequestToReceived;//申请组织等于收货组织
    private BigDecimal totalMoveQty;//累计调拨数量
    private BigDecimal totalInvoicedAmt;//累计开票金额
    private BigDecimal totalPrePayAmt;//预付累计申请金额
    private BigDecimal prepayment;//预付款金额
    private BigDecimal preReceived;//已付预付款
    private BigDecimal unPrereceivedAm;//未付预付款金额
    private Integer version;//版本
    private String old;//旧ID
    private String oldStatus;//旧的状态值
    private BigDecimal canInvMoveQty;//可调拔数量
    private BigDecimal unOrderedQty;//未订货数量
    private Boolean isBetweenCompanyRec;//跨公司收货
    private String rowType;//行类型
    private String destinationType;//目的地类型
    private String wareHouse;//仓库
    private String materialName;//物料名称
    private Boolean isReqComEqlRecCom;//收货公司是否等于申请公司
    private BigDecimal planReceiveQty;//安排提货数量
    private BigDecimal totalCancelledStockQty;//累计退库数量
    private BigDecimal totalSupplyStockQty;//累计退库须补货数量
    private BigDecimal totalSupplyStockBaseQty;//累计退库须补货基本数量
    private Integer isReqPrePayGTprePay;//预付累计申请金额是否大于预付金额
    private String noNumMaterialModel;//规格型号
    private String qCStandard;//检验标准
    private String project;//项目号
    private String trackNo;//跟踪号
    private String purContract;//采购合同号
    private BigDecimal matchedAmount;//匹配金额
    private Date bizDate;//业务日期
    private String purchaseOrgUnit;//采购组织
    private String purRequest;//采购申请单ID
    private String purRequestEntryID;//采购申请单行ID
    private Boolean isManualClose;//是否手工关闭
    private String material;//物料
    private String assistProperty;//辅助属性
    private String unit;//计量单位
    private String sourceBillID;//源单据Id
    private String sourceBillNumber;//来源单据编号
    private String sourceBillEntryID;//来源单据分录的Id
    private Integer sourceBillEntrySeq;//来源单据分录序号
    private BigDecimal assCoeFFicient;//辅助计量单位换算系数
    private String baseStatus;//基本状态
    private BigDecimal associateQty;//未关联数量
    private String sourceBillType;//来源单据类型
    private String baseUnit;//基本计量单位
    private String assistUnit;//辅助计量单位
    private String remark;//备注
    private String reasonCode;//原因代码
    private Integer seq;//单据分录序列号

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }

    public String getSupplierMaterialNumber() {
        return supplierMaterialNumber;
    }

    public void setSupplierMaterialNumber(String supplierMaterialNumber) {
        this.supplierMaterialNumber = supplierMaterialNumber;
    }

    public String getSupplierMaterialName() {
        return supplierMaterialName;
    }

    public void setSupplierMaterialName(String supplierMaterialName) {
        this.supplierMaterialName = supplierMaterialName;
    }

    public String getSupplierMaterialModel() {
        return supplierMaterialModel;
    }

    public void setSupplierMaterialModel(String supplierMaterialModel) {
        this.supplierMaterialModel = supplierMaterialModel;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public BigDecimal getDemandQty() {
        return demandQty;
    }

    public void setDemandQty(BigDecimal demandQty) {
        this.demandQty = demandQty;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getStorageOrgUnit() {
        return storageOrgUnit;
    }

    public void setStorageOrgUnit(String storageOrgUnit) {
        this.storageOrgUnit = storageOrgUnit;
    }

    public String getAdminOrgUnit() {
        return adminOrgUnit;
    }

    public void setAdminOrgUnit(String adminOrgUnit) {
        this.adminOrgUnit = adminOrgUnit;
    }

    public String getCompanyOrgUnit() {
        return companyOrgUnit;
    }

    public void setCompanyOrgUnit(String companyOrgUnit) {
        this.companyOrgUnit = companyOrgUnit;
    }

    public BigDecimal getAssistQty() {
        return assistQty;
    }

    public void setAssistQty(BigDecimal assistQty) {
        this.assistQty = assistQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getActualTaxPrice() {
        return actualTaxPrice;
    }

    public void setActualTaxPrice(BigDecimal actualTaxPrice) {
        this.actualTaxPrice = actualTaxPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getReceiveOverRate() {
        return receiveOverRate;
    }

    public void setReceiveOverRate(BigDecimal receiveOverRate) {
        this.receiveOverRate = receiveOverRate;
    }

    public BigDecimal getReceiveOwingRate() {
        return receiveOwingRate;
    }

    public void setReceiveOwingRate(BigDecimal receiveOwingRate) {
        this.receiveOwingRate = receiveOwingRate;
    }

    public BigDecimal getDeliverAdvanceDay() {
        return deliverAdvanceDay;
    }

    public void setDeliverAdvanceDay(BigDecimal deliverAdvanceDay) {
        this.deliverAdvanceDay = deliverAdvanceDay;
    }

    public BigDecimal getDeliverDeFerralDay() {
        return deliverDeFerralDay;
    }

    public void setDeliverDeFerralDay(BigDecimal deliverDeFerralDay) {
        this.deliverDeFerralDay = deliverDeFerralDay;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public BigDecimal getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(BigDecimal baseQty) {
        this.baseQty = baseQty;
    }

    public BigDecimal getTotalReceiveQty() {
        return totalReceiveQty;
    }

    public void setTotalReceiveQty(BigDecimal totalReceiveQty) {
        this.totalReceiveQty = totalReceiveQty;
    }

    public BigDecimal getTotalReceiptQty() {
        return totalReceiptQty;
    }

    public void setTotalReceiptQty(BigDecimal totalReceiptQty) {
        this.totalReceiptQty = totalReceiptQty;
    }

    public BigDecimal getTotalReturnedQty() {
        return totalReturnedQty;
    }

    public void setTotalReturnedQty(BigDecimal totalReturnedQty) {
        this.totalReturnedQty = totalReturnedQty;
    }

    public BigDecimal getTotalInvoicedQty() {
        return totalInvoicedQty;
    }

    public void setTotalInvoicedQty(BigDecimal totalInvoicedQty) {
        this.totalInvoicedQty = totalInvoicedQty;
    }

    public BigDecimal getTotalInvoicedAmount() {
        return totalInvoicedAmount;
    }

    public void setTotalInvoicedAmount(BigDecimal totalInvoicedAmount) {
        this.totalInvoicedAmount = totalInvoicedAmount;
    }

    public BigDecimal getTotalReqPayAmt() {
        return totalReqPayAmt;
    }

    public void setTotalReqPayAmt(BigDecimal totalReqPayAmt) {
        this.totalReqPayAmt = totalReqPayAmt;
    }

    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Boolean getQuantityUnCtrl() {
        return isQuantityUnCtrl;
    }

    public void setQuantityUnCtrl(Boolean quantityUnCtrl) {
        isQuantityUnCtrl = quantityUnCtrl;
    }

    public Boolean getTimeUnCtrl() {
        return isTimeUnCtrl;
    }

    public void setTimeUnCtrl(Boolean timeUnCtrl) {
        isTimeUnCtrl = timeUnCtrl;
    }

    public BigDecimal getTotalReceiveBaseQty() {
        return totalReceiveBaseQty;
    }

    public void setTotalReceiveBaseQty(BigDecimal totalReceiveBaseQty) {
        this.totalReceiveBaseQty = totalReceiveBaseQty;
    }

    public BigDecimal getTotalReceiptBaseQty() {
        return totalReceiptBaseQty;
    }

    public void setTotalReceiptBaseQty(BigDecimal totalReceiptBaseQty) {
        this.totalReceiptBaseQty = totalReceiptBaseQty;
    }

    public BigDecimal getTotalReturnedBaseQty() {
        return totalReturnedBaseQty;
    }

    public void setTotalReturnedBaseQty(BigDecimal totalReturnedBaseQty) {
        this.totalReturnedBaseQty = totalReturnedBaseQty;
    }

    public BigDecimal getTotalInvoicedBaseQty() {
        return totalInvoicedBaseQty;
    }

    public void setTotalInvoicedBaseQty(BigDecimal totalInvoicedBaseQty) {
        this.totalInvoicedBaseQty = totalInvoicedBaseQty;
    }

    public BigDecimal getTotalUnReturnBaseQty() {
        return totalUnReturnBaseQty;
    }

    public void setTotalUnReturnBaseQty(BigDecimal totalUnReturnBaseQty) {
        this.totalUnReturnBaseQty = totalUnReturnBaseQty;
    }

    public BigDecimal getTotalUnReceiveBaseQty() {
        return totalUnReceiveBaseQty;
    }

    public void setTotalUnReceiveBaseQty(BigDecimal totalUnReceiveBaseQty) {
        this.totalUnReceiveBaseQty = totalUnReceiveBaseQty;
    }

    public BigDecimal getTotalUnReceiveQty() {
        return totalUnReceiveQty;
    }

    public void setTotalUnReceiveQty(BigDecimal totalUnReceiveQty) {
        this.totalUnReceiveQty = totalUnReceiveQty;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Boolean getSupInFo() {
        return isSupInFo;
    }

    public void setSupInFo(Boolean supInFo) {
        isSupInFo = supInFo;
    }

    public BigDecimal getCurSeOrderQty() {
        return curSeOrderQty;
    }

    public void setCurSeOrderQty(BigDecimal curSeOrderQty) {
        this.curSeOrderQty = curSeOrderQty;
    }

    public BigDecimal getLocalTax() {
        return localTax;
    }

    public void setLocalTax(BigDecimal localTax) {
        this.localTax = localTax;
    }

    public BigDecimal getLocalTaxAmount() {
        return localTaxAmount;
    }

    public void setLocalTaxAmount(BigDecimal localTaxAmount) {
        this.localTaxAmount = localTaxAmount;
    }

    public String getSaleOrderNumber() {
        return saleOrderNumber;
    }

    public void setSaleOrderNumber(String saleOrderNumber) {
        this.saleOrderNumber = saleOrderNumber;
    }

    public BigDecimal getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(BigDecimal prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public String getRequestOrgUnit() {
        return requestOrgUnit;
    }

    public void setRequestOrgUnit(String requestOrgUnit) {
        this.requestOrgUnit = requestOrgUnit;
    }

    public String getRequestCompanyOrgUnit() {
        return requestCompanyOrgUnit;
    }

    public void setRequestCompanyOrgUnit(String requestCompanyOrgUnit) {
        this.requestCompanyOrgUnit = requestCompanyOrgUnit;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getRequestToReceived() {
        return isRequestToReceived;
    }

    public void setRequestToReceived(Boolean requestToReceived) {
        isRequestToReceived = requestToReceived;
    }

    public BigDecimal getTotalMoveQty() {
        return totalMoveQty;
    }

    public void setTotalMoveQty(BigDecimal totalMoveQty) {
        this.totalMoveQty = totalMoveQty;
    }

    public BigDecimal getTotalInvoicedAmt() {
        return totalInvoicedAmt;
    }

    public void setTotalInvoicedAmt(BigDecimal totalInvoicedAmt) {
        this.totalInvoicedAmt = totalInvoicedAmt;
    }

    public BigDecimal getTotalPrePayAmt() {
        return totalPrePayAmt;
    }

    public void setTotalPrePayAmt(BigDecimal totalPrePayAmt) {
        this.totalPrePayAmt = totalPrePayAmt;
    }

    public BigDecimal getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(BigDecimal prepayment) {
        this.prepayment = prepayment;
    }

    public BigDecimal getPreReceived() {
        return preReceived;
    }

    public void setPreReceived(BigDecimal preReceived) {
        this.preReceived = preReceived;
    }

    public BigDecimal getUnPrereceivedAm() {
        return unPrereceivedAm;
    }

    public void setUnPrereceivedAm(BigDecimal unPrereceivedAm) {
        this.unPrereceivedAm = unPrereceivedAm;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public BigDecimal getCanInvMoveQty() {
        return canInvMoveQty;
    }

    public void setCanInvMoveQty(BigDecimal canInvMoveQty) {
        this.canInvMoveQty = canInvMoveQty;
    }

    public BigDecimal getUnOrderedQty() {
        return unOrderedQty;
    }

    public void setUnOrderedQty(BigDecimal unOrderedQty) {
        this.unOrderedQty = unOrderedQty;
    }

    public Boolean getBetweenCompanyRec() {
        return isBetweenCompanyRec;
    }

    public void setBetweenCompanyRec(Boolean betweenCompanyRec) {
        isBetweenCompanyRec = betweenCompanyRec;
    }

    public String getRowType() {
        return rowType;
    }

    public void setRowType(String rowType) {
        this.rowType = rowType;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Boolean getReqComEqlRecCom() {
        return isReqComEqlRecCom;
    }

    public void setReqComEqlRecCom(Boolean reqComEqlRecCom) {
        isReqComEqlRecCom = reqComEqlRecCom;
    }

    public BigDecimal getPlanReceiveQty() {
        return planReceiveQty;
    }

    public void setPlanReceiveQty(BigDecimal planReceiveQty) {
        this.planReceiveQty = planReceiveQty;
    }

    public BigDecimal getTotalCancelledStockQty() {
        return totalCancelledStockQty;
    }

    public void setTotalCancelledStockQty(BigDecimal totalCancelledStockQty) {
        this.totalCancelledStockQty = totalCancelledStockQty;
    }

    public BigDecimal getTotalSupplyStockQty() {
        return totalSupplyStockQty;
    }

    public void setTotalSupplyStockQty(BigDecimal totalSupplyStockQty) {
        this.totalSupplyStockQty = totalSupplyStockQty;
    }

    public BigDecimal getTotalSupplyStockBaseQty() {
        return totalSupplyStockBaseQty;
    }

    public void setTotalSupplyStockBaseQty(BigDecimal totalSupplyStockBaseQty) {
        this.totalSupplyStockBaseQty = totalSupplyStockBaseQty;
    }

    public Integer getIsReqPrePayGTprePay() {
        return isReqPrePayGTprePay;
    }

    public void setIsReqPrePayGTprePay(Integer isReqPrePayGTprePay) {
        this.isReqPrePayGTprePay = isReqPrePayGTprePay;
    }

    public String getNoNumMaterialModel() {
        return noNumMaterialModel;
    }

    public void setNoNumMaterialModel(String noNumMaterialModel) {
        this.noNumMaterialModel = noNumMaterialModel;
    }

    public String getqCStandard() {
        return qCStandard;
    }

    public void setqCStandard(String qCStandard) {
        this.qCStandard = qCStandard;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public String getPurContract() {
        return purContract;
    }

    public void setPurContract(String purContract) {
        this.purContract = purContract;
    }

    public BigDecimal getMatchedAmount() {
        return matchedAmount;
    }

    public void setMatchedAmount(BigDecimal matchedAmount) {
        this.matchedAmount = matchedAmount;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getPurchaseOrgUnit() {
        return purchaseOrgUnit;
    }

    public void setPurchaseOrgUnit(String purchaseOrgUnit) {
        this.purchaseOrgUnit = purchaseOrgUnit;
    }

    public String getPurRequest() {
        return purRequest;
    }

    public void setPurRequest(String purRequest) {
        this.purRequest = purRequest;
    }

    public String getPurRequestEntryID() {
        return purRequestEntryID;
    }

    public void setPurRequestEntryID(String purRequestEntryID) {
        this.purRequestEntryID = purRequestEntryID;
    }

    public Boolean getManualClose() {
        return isManualClose;
    }

    public void setManualClose(Boolean manualClose) {
        isManualClose = manualClose;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAssistProperty() {
        return assistProperty;
    }

    public void setAssistProperty(String assistProperty) {
        this.assistProperty = assistProperty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSourceBillID() {
        return sourceBillID;
    }

    public void setSourceBillID(String sourceBillID) {
        this.sourceBillID = sourceBillID;
    }

    public String getSourceBillNumber() {
        return sourceBillNumber;
    }

    public void setSourceBillNumber(String sourceBillNumber) {
        this.sourceBillNumber = sourceBillNumber;
    }

    public String getSourceBillEntryID() {
        return sourceBillEntryID;
    }

    public void setSourceBillEntryID(String sourceBillEntryID) {
        this.sourceBillEntryID = sourceBillEntryID;
    }

    public Integer getSourceBillEntrySeq() {
        return sourceBillEntrySeq;
    }

    public void setSourceBillEntrySeq(Integer sourceBillEntrySeq) {
        this.sourceBillEntrySeq = sourceBillEntrySeq;
    }

    public BigDecimal getAssCoeFFicient() {
        return assCoeFFicient;
    }

    public void setAssCoeFFicient(BigDecimal assCoeFFicient) {
        this.assCoeFFicient = assCoeFFicient;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public BigDecimal getAssociateQty() {
        return associateQty;
    }

    public void setAssociateQty(BigDecimal associateQty) {
        this.associateQty = associateQty;
    }

    public String getSourceBillType() {
        return sourceBillType;
    }

    public void setSourceBillType(String sourceBillType) {
        this.sourceBillType = sourceBillType;
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public String getAssistUnit() {
        return assistUnit;
    }

    public void setAssistUnit(String assistUnit) {
        this.assistUnit = assistUnit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
