package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 进销存台账
 */
public class InventoryLedger implements Serializable{

    private int id;                        //进销存台账id
    private Date dateTime;                  //台账日期
    private long projectId;                  //项目id
    private String productId;               //商品id
    private String productCode;
    private String productModel;            //商品型号
    private String productName;             //商品名称
    private String warehouseName;           //仓库名称
    private int beginTotalAmount;           //期初总量
    private int beginNonDefective;          //期初良品
    private int beginDefective;             //期初次品
    private int inStockTotalAmount;         //入库总量
    private int inStockNonDefectiveAmount;   //入库良品
    private int inStockDefectiveAmount;     //入库次品
    private int outStockTotalAmount;        //出库总量
    private int nonDefectiveSaleAmount;     //良品销售发货
    private int nonDefectiveOtherAmount;    //良品其他出库
    private int endTotalAmount;             //期末总量
    private int endNonDefectiveAmount;      //期末良品
    private int endDefectiveAmount;         //期末次品
    private int onPurchaseAmount;           //采购在途
    private int onTransferInAmount;         //调拨入库在途
    private int onTransferOutAmount;         //调拨出库在途
    private int nonDefectiveProfitkAmount;   //良品盘盈
    private int defectiveProfitAmount;       //次品盘盈
    private int defectiveOutAmount;         //次品出库
    private int nonDefectiveLossAmount;     //良品盘亏
    private int defectiveLossAmount;        //次品盘亏
    private int adjustmentAccountTotalOut;          //调账出库总量
    private int adjustmentAccountNonDefectiveOut;   //调账出库良品
    private int adjustmentAccountDefectiveOut;      //调账出库非良品
    private int modifyTotalAmountOut;               //调整出库总量
    private int modifyNonDefectiveAmountOut;        //调整出库良品
    private int modifyDefectiveAmountOut;           //调整出库非良品
    private int adjustmentAccountTotalIn;           //调账入库总量
    private int adjustmentAccountNonDefectiveIn;   //调账入库良品
    private int adjustmentAccountDefectiveIn;       //调账入库非良品
    private int modifyTotalAmountIn;                //调整入库总量
    private int modifyNonDefectiveAmountIn;         //调整入库良品
    private int modifyDefectiveAmountIn;            //调账入库非良品

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getBeginTotalAmount() {
        return beginTotalAmount;
    }

    public void setBeginTotalAmount(int beginTotalAmount) {
        this.beginTotalAmount = beginTotalAmount;
    }

    public int getBeginNonDefective() {
        return beginNonDefective;
    }

    public void setBeginNonDefective(int beginNonDefective) {
        this.beginNonDefective = beginNonDefective;
    }

    public int getBeginDefective() {
        return beginDefective;
    }

    public void setBeginDefective(int beginDefective) {
        this.beginDefective = beginDefective;
    }

    public int getInStockTotalAmount() {
        return inStockTotalAmount;
    }

    public void setInStockTotalAmount(int inStockTotalAmount) {
        this.inStockTotalAmount = inStockTotalAmount;
    }

    public int getInStockNonDefectiveAmount() {
        return inStockNonDefectiveAmount;
    }

    public void setInStockNonDefectiveAmount(int inStockNonDefectiveAmount) {
        this.inStockNonDefectiveAmount = inStockNonDefectiveAmount;
    }

    public int getInStockDefectiveAmount() {
        return inStockDefectiveAmount;
    }

    public void setInStockDefectiveAmount(int inStockDefectiveAmount) {
        this.inStockDefectiveAmount = inStockDefectiveAmount;
    }

    public int getOutStockTotalAmount() {
        return outStockTotalAmount;
    }

    public void setOutStockTotalAmount(int outStockTotalAmount) {
        this.outStockTotalAmount = outStockTotalAmount;
    }

    public int getNonDefectiveSaleAmount() {
        return nonDefectiveSaleAmount;
    }

    public void setNonDefectiveSaleAmount(int nonDefectiveSaleAmount) {
        this.nonDefectiveSaleAmount = nonDefectiveSaleAmount;
    }

    public int getNonDefectiveOtherAmount() {
        return nonDefectiveOtherAmount;
    }

    public void setNonDefectiveOtherAmount(int nonDefectiveOtherAmount) {
        this.nonDefectiveOtherAmount = nonDefectiveOtherAmount;
    }

    public int getEndTotalAmount() {
        return endTotalAmount;
    }

    public void setEndTotalAmount(int endTotalAmount) {
        this.endTotalAmount = endTotalAmount;
    }

    public int getEndNonDefectiveAmount() {
        return endNonDefectiveAmount;
    }

    public void setEndNonDefectiveAmount(int endNonDefectiveAmount) {
        this.endNonDefectiveAmount = endNonDefectiveAmount;
    }

    public int getEndDefectiveAmount() {
        return endDefectiveAmount;
    }

    public void setEndDefectiveAmount(int endDefectiveAmount) {
        this.endDefectiveAmount = endDefectiveAmount;
    }

    public int getOnPurchaseAmount() {
        return onPurchaseAmount;
    }

    public void setOnPurchaseAmount(int onPurchaseAmount) {
        this.onPurchaseAmount = onPurchaseAmount;
    }

    public int getOnTransferInAmount() {
        return onTransferInAmount;
    }

    public void setOnTransferInAmount(int onTransferInAmount) {
        this.onTransferInAmount = onTransferInAmount;
    }

    public int getOnTransferOutAmount() {
        return onTransferOutAmount;
    }

    public void setOnTransferOutAmount(int onTransferOutAmount) {
        this.onTransferOutAmount = onTransferOutAmount;
    }

    public int getNonDefectiveProfitkAmount() {
        return nonDefectiveProfitkAmount;
    }

    public void setNonDefectiveProfitkAmount(int nonDefectiveProfitkAmount) {
        this.nonDefectiveProfitkAmount = nonDefectiveProfitkAmount;
    }

    public int getDefectiveProfitAmount() {
        return defectiveProfitAmount;
    }

    public void setDefectiveProfitAmount(int defectiveProfitAmount) {
        this.defectiveProfitAmount = defectiveProfitAmount;
    }

    public int getDefectiveOutAmount() {
        return defectiveOutAmount;
    }

    public void setDefectiveOutAmount(int defectiveOutAmount) {
        this.defectiveOutAmount = defectiveOutAmount;
    }

    public int getNonDefectiveLossAmount() {
        return nonDefectiveLossAmount;
    }

    public void setNonDefectiveLossAmount(int nonDefectiveLossAmount) {
        this.nonDefectiveLossAmount = nonDefectiveLossAmount;
    }

    public int getDefectiveLossAmount() {
        return defectiveLossAmount;
    }

    public void setDefectiveLossAmount(int defectiveLossAmount) {
        this.defectiveLossAmount = defectiveLossAmount;
    }

    public int getAdjustmentAccountTotalOut() {
        return adjustmentAccountTotalOut;
    }

    public void setAdjustmentAccountTotalOut(int adjustmentAccountTotalOut) {
        this.adjustmentAccountTotalOut = adjustmentAccountTotalOut;
    }

    public int getAdjustmentAccountNonDefectiveOut() {
        return adjustmentAccountNonDefectiveOut;
    }

    public void setAdjustmentAccountNonDefectiveOut(int adjustmentAccountNonDefectiveOut) {
        this.adjustmentAccountNonDefectiveOut = adjustmentAccountNonDefectiveOut;
    }

    public int getAdjustmentAccountDefectiveOut() {
        return adjustmentAccountDefectiveOut;
    }

    public void setAdjustmentAccountDefectiveOut(int adjustmentAccountDefectiveOut) {
        this.adjustmentAccountDefectiveOut = adjustmentAccountDefectiveOut;
    }

    public int getModifyTotalAmountOut() {
        return modifyTotalAmountOut;
    }

    public void setModifyTotalAmountOut(int modifyTotalAmountOut) {
        this.modifyTotalAmountOut = modifyTotalAmountOut;
    }

    public int getModifyNonDefectiveAmountOut() {
        return modifyNonDefectiveAmountOut;
    }

    public void setModifyNonDefectiveAmountOut(int modifyNonDefectiveAmountOut) {
        this.modifyNonDefectiveAmountOut = modifyNonDefectiveAmountOut;
    }

    public int getModifyDefectiveAmountOut() {
        return modifyDefectiveAmountOut;
    }

    public void setModifyDefectiveAmountOut(int modifyDefectiveAmountOut) {
        this.modifyDefectiveAmountOut = modifyDefectiveAmountOut;
    }

    public int getAdjustmentAccountTotalIn() {
        return adjustmentAccountTotalIn;
    }

    public void setAdjustmentAccountTotalIn(int adjustmentAccountTotalIn) {
        this.adjustmentAccountTotalIn = adjustmentAccountTotalIn;
    }

    public int getAdjustmentAccountNonDefectiveIn() {
        return adjustmentAccountNonDefectiveIn;
    }

    public void setAdjustmentAccountNonDefectiveIn(int adjustmentAccountNonDefectiveIn) {
        this.adjustmentAccountNonDefectiveIn = adjustmentAccountNonDefectiveIn;
    }

    public int getAdjustmentAccountDefectiveIn() {
        return adjustmentAccountDefectiveIn;
    }

    public void setAdjustmentAccountDefectiveIn(int adjustmentAccountDefectiveIn) {
        this.adjustmentAccountDefectiveIn = adjustmentAccountDefectiveIn;
    }

    public int getModifyTotalAmountIn() {
        return modifyTotalAmountIn;
    }

    public void setModifyTotalAmountIn(int modifyTotalAmountIn) {
        this.modifyTotalAmountIn = modifyTotalAmountIn;
    }

    public int getModifyNonDefectiveAmountIn() {
        return modifyNonDefectiveAmountIn;
    }

    public void setModifyNonDefectiveAmountIn(int modifyNonDefectiveAmountIn) {
        this.modifyNonDefectiveAmountIn = modifyNonDefectiveAmountIn;
    }

    public int getModifyDefectiveAmountIn() {
        return modifyDefectiveAmountIn;
    }

    public void setModifyDefectiveAmountIn(int modifyDefectiveAmountIn) {
        this.modifyDefectiveAmountIn = modifyDefectiveAmountIn;
    }
}
