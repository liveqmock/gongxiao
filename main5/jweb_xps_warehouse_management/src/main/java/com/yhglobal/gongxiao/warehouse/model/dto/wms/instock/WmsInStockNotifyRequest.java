package com.yhglobal.gongxiao.warehouse.model.dto.wms.instock;


import com.yhglobal.gongxiao.warehouse.model.dto.wms.BatchSendCtrlParam;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.DriverInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.ReceiverInfo;
import com.yhglobal.gongxiao.warehouse.model.dto.wms.SenderInfo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * WMS入库通知信息
 * @author liukai
 */
@XmlRootElement(name="request")
public class WmsInStockNotifyRequest implements Serializable{
    private String warehouseCode;                   //仓库编码  必选
    private String orderNo;                         //订单号   必选
    private String custCode;                        //客户代码  必选
    private int orderType;                          //操作类型/订单类型 必选
    private String orderSource;                     //订单来源  必选
    private String sourceOrderNo;                   //来源单号  可选
    private String orderCreateTime;                 //订单创建时间 可选
    private String supplierCode;                    //供应商编码  可选
    private String supplierName;                    //供应商名称   可选
    private String tmsServiceCode;                  //快递公司编码/车次号  可选
    private String tmsOrderCode;                    //运单号/配送车牌  可选
    private String expectStartTime;                 //预期送达开始时间  可选
    private BatchSendCtrlParam batchSendCtrlParam;  //分批下发控制参数  可选
    private ReceiverInfo receiverInfo;              //收货方信息   必选
    private SenderInfo senderInfo;                  //发货方信息   必选
    private List<StockInOrderItem> orderItemList;   //订单商品信息  必选
    private DriverInfo driverInfo;                  //司机信息
    private String remark;                          //备注   可选

    private String ckid;                            //仓库id
    private String projectCode;          //项目编码

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getTmsServiceCode() {
        return tmsServiceCode;
    }

    public void setTmsServiceCode(String tmsServiceCode) {
        this.tmsServiceCode = tmsServiceCode;
    }

    public String getTmsOrderCode() {
        return tmsOrderCode;
    }

    public void setTmsOrderCode(String tmsOrderCode) {
        this.tmsOrderCode = tmsOrderCode;
    }

    public String getExpectStartTime() {
        return expectStartTime;
    }

    public void setExpectStartTime(String expectStartTime) {
        this.expectStartTime = expectStartTime;
    }

    public BatchSendCtrlParam getBatchSendCtrlParam() {
        return batchSendCtrlParam;
    }

    public void setBatchSendCtrlParam(BatchSendCtrlParam batchSendCtrlParam) {
        this.batchSendCtrlParam = batchSendCtrlParam;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public List<StockInOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<StockInOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
